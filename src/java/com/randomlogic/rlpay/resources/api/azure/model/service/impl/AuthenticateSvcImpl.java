/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.service.impl;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;
import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AuthResponse;
import static com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AzureAPICodes.*;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AzureError;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.StateData;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import com.randomlogic.rlpay.resources.api.azure.model.service.interfaces.IAuthenticateSvc;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Service
public class AuthenticateSvcImpl implements IAuthenticateSvc
{
    public static final String STATES = "states";
    public static final String STATE = "state";
    public static final Integer STATE_TTL = 3600;
    public static final String FAILED_TO_VALIDATE_MESSAGE = "Failed to validate data received from Authorization service - ";
    private String clientId = "";
    private String clientSecret = "";
    private String tenant = "";
    private String authority;
    private IAuthResponse response = new AuthResponse();
    private AuthParams params;
    private AzureError error = new AzureError();

    @Override
    public void init (IAuthParams params)
    {
        this.params = (AuthParams)params;
        clientId = params.getClientId();
        authority = params.getAuthority();
        tenant = params.getTenant();
        clientSecret = params.getSecretKey();
        response.setRequest (params.getRequest());
        response.setResponse (params.getResponse());
        error.setSource (AuthenticateSvcImpl.class);
    }

    @Override
    public IAuthResponse authenticate()
    {
        boolean authenticated = true;

        if (params.getRequest() instanceof HttpServletRequest)
        {
            try
            {
                String currentUri = response.getRequest().getRequestURL().toString();
                String queryStr = response.getRequest().getQueryString();
                String fullUrl = currentUri + (queryStr != null ? "?" + queryStr : "");

                // check if user has an AuthData in the session
                if (!AuthHelper.isAuthenticated (response.getRequest()))
                {
                    if (AuthHelper.containsAuthenticationData (params.getRequest()))
                    {
                        authenticated = processAuthenticationData (response.getRequest(), currentUri, fullUrl);
                    }
                    else
                    {
                        // not authenticated
                        authenticated = false;

                        try
                        {
                            generateAuthRedirect (response.getRequest(), response.getResponse());
                        }
                        catch (UnsupportedEncodingException ex)
                        {
                            error.setErrorCode (AZURE_URI_INVALID);
                            error.setMessage (AZMSG_URI_INVALID);
                            error.setException (ex);
                        }
                    }
                }
                // else do nothing

                if (authenticated)
                {
                    if (isAuthDataExpired (response.getRequest()))
                    {
                        updateAuthDataUsingRefreshToken (response.getRequest());
                    }
                    // else do nothing
                }
                // else do nothing
            }
            catch (AuthenticationException authException)
            {
                // something went wrong (like expiration or revocation of token)
                // we should invalidate AuthData stored in session and redirect to Authorization server
                removePrincipalFromSession (response.getRequest());
                response.setValid (false);

                try
                {
                    generateAuthRedirect (response.getRequest(), response.getResponse());
                }
                catch (UnsupportedEncodingException ex)
                {
                    error.setErrorCode (AZURE_URI_INVALID);
                    error.setMessage (AZMSG_URI_INVALID);
                    error.setException (ex);
                }
            }
            catch (Throwable exc)
            {
                response.getResponse().setStatus (500);
                response.getRequest().setAttribute ("error", exc.getMessage());

                error.setErrorCode (AZURE_AUTH_INTERNAL);
                error.setException ((Exception)exc);
                error.setMessage (exc.getMessage());

                response.setValid (false);
            }
        }

        response.setErrrorObject (error);
        return response;
    }

    private boolean isAuthDataExpired (HttpServletRequest httpRequest)
    {
        AuthenticationResult authData = AuthHelper.getAuthSessionObject (httpRequest);
        return authData.getExpiresOnDate().before (new Date());
    }

    private void updateAuthDataUsingRefreshToken (HttpServletRequest httpRequest) throws Throwable
    {
        AuthenticationResult authData = getAccessTokenFromRefreshToken (AuthHelper.getAuthSessionObject (httpRequest).getRefreshToken());
        setSessionPrincipal (httpRequest, authData);
    }

    private boolean processAuthenticationData (HttpServletRequest httpRequest, String currentUri, String fullUrl)
    {
        HashMap<String, String> authParams = new HashMap<>();

        for (String key : httpRequest.getParameterMap().keySet())
        {
            authParams.put (key, httpRequest.getParameterMap().get (key)[0]);
        }

        // validate that state in response equals to state in request
        StateData stateData = validateState (httpRequest.getSession(), authParams.get (STATE));

        if (stateData != null)
        {
            AuthenticationResponse authResponse;

            try
            {
                authResponse = AuthenticationResponseParser.parse (new URI (fullUrl), authParams);

                if (AuthHelper.isAuthenticationSuccessful (authResponse))
                {
                    AuthenticationSuccessResponse oidcResponse = (AuthenticationSuccessResponse) authResponse;

                    try
                    {
                        // validate that OIDC Auth Response matches Code Flow (contains only requested artifacts)
                        validateAuthRespMatchesCodeFlow (oidcResponse);

                        AuthenticationResult authData;

                        try
                        {
                            authData = getAccessToken (oidcResponse.getAuthorizationCode(), currentUri);

                            // validate nonce to prevent reply attacks (code maybe substituted to one with broader access)
                            validateNonce (stateData, getClaimValueFromIdToken (authData.getIdToken(), "nonce"));

                            setSessionPrincipal (httpRequest, authData);
                            response.setValid (true);
                        }
                        catch (Throwable ex)
                        {
                            error.setErrorCode (AZURE_AUTH_TOKEN_FAIL);
                            error.setMessage (AZMSG_AUTH_TOKEN_FAIL);
                            error.setException ((Exception)ex);
                        }
                    }
                    catch (Exception ex)
                    {
                        error.setErrorCode (AZURE_AUTH_MSG_FAIL);
                        error.setMessage (AZMSG_AUTH_MSG_FAIL);
                        error.setException (ex);
                    }
                }
                else
                {
                    AuthenticationErrorResponse oidcResponse = (AuthenticationErrorResponse) authResponse;

                    error.setErrorCode (AZURE_AUTH_FAIL);
                    error.setMessage (AZMSG_AUTH_FAIL);
                    error.setException (new Exception (String.format ("Request for auth code failed: %s - %s",
                                        oidcResponse.getErrorObject().getCode(),
                                        oidcResponse.getErrorObject().getDescription())
                                       ));
                }
            }
            catch (URISyntaxException ex)
            {
                error.setErrorCode (AZURE_URI_INVALID);
                error.setMessage (AZMSG_URI_INVALID);
                error.setException (ex);
            }
            catch (com.nimbusds.oauth2.sdk.ParseException ex)
            {
                error.setErrorCode (AZURE_AUTH_FAIL);
                error.setMessage (AZMSG_OAUTH_PARSE);
                error.setException (ex);
            }
        }// ENDIF: if (stateData != null)
        //else do nothing

        return response.isValid();
    }

    private void validateNonce (StateData stateData, String nonce) throws Exception
    {
        if (StringUtils.isEmpty (nonce) || !nonce.equals (stateData.getNonce()))
        {
            throw new Exception (FAILED_TO_VALIDATE_MESSAGE + "could not validate nonce");
        }
    }

    private String getClaimValueFromIdToken (String idToken, String claimKey) throws ParseException
    {
        return (String) JWTParser.parse (idToken).getJWTClaimsSet().getClaim (claimKey);
    }

    private void generateAuthRedirect (HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws UnsupportedEncodingException
    {
        httpResponse.setStatus (302);

        // use state parameter to validate response from Authorization server
        String state = UUID.randomUUID().toString();

        // use nonce parameter to validate idToken
        String nonce = UUID.randomUUID().toString();

        storeStateInSession (httpRequest.getSession(), state, nonce);

        String currentUri = httpRequest.getRequestURL().toString();
        response.setRedirectUrl (getRedirectUrl (currentUri, state, nonce));
        error.setErrorCode (AZURE_AUTH_REDIRECT);
    }

    /**
     * make sure that state is stored in the session,
     * delete it from session - should be used only once
     *
     * @param session
     * @param state
     * @throws Exception
     */
    private StateData validateState (HttpSession session, String state)
    {
        StateData stateDataInSession = null;

        if (StringUtils.isNotEmpty (state))
        {
            stateDataInSession = removeStateFromSession (session, state);

            if (stateDataInSession == null)
            {
                error.setErrorCode (AZURE_AUTH_VALIDATE);
                error.setMessage (AZMSG_AUTH_VALIDATE);
                error.setException (new Exception (FAILED_TO_VALIDATE_MESSAGE + "could not validate state"));
            }
            //else do nothing
        }

        return stateDataInSession;
    }

    private void validateAuthRespMatchesCodeFlow (AuthenticationSuccessResponse oidcResponse) throws Exception
    {
        if (oidcResponse.getIDToken() != null
            || oidcResponse.getAccessToken() != null
            || oidcResponse.getAuthorizationCode() == null
           )
        {
            throw new Exception (FAILED_TO_VALIDATE_MESSAGE + "unexpected set of artifacts received");
        }
    }

    @SuppressWarnings ("unchecked")
    private StateData removeStateFromSession (HttpSession session, String state)
    {
        Map<String, StateData> states = (Map<String, StateData>) session.getAttribute (STATES);

        if (states != null)
        {
            eliminateExpiredStates (states);
            StateData stateData = states.get (state);

            if (stateData != null)
            {
                states.remove (state);
                return stateData;
            }
        }

        return null;
    }

    @SuppressWarnings ("unchecked")
    private void storeStateInSession (HttpSession session, String state, String nonce)
    {
        if (session.getAttribute (STATES) == null)
        {
            session.setAttribute (STATES, new HashMap<String, StateData>());
        }

        ((Map<String, StateData>) session.getAttribute (STATES)).put (state, new StateData (nonce, new Date()));
    }

    private void eliminateExpiredStates (Map<String, StateData> map)
    {
        Iterator<Map.Entry<String, StateData >> it = map.entrySet().iterator();
        Date currTime = new Date();

        while (it.hasNext())
        {
            Map.Entry<String, StateData> entry = it.next();
            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds (currTime.getTime() - entry.getValue().getExpirationDate().getTime());

            if (diffInSeconds > STATE_TTL)
            {
                it.remove();
            }
            // else do nothing
        }
    }

    private AuthenticationResult getAccessTokenFromRefreshToken (String refreshToken) throws Throwable
    {
        AuthenticationContext context;
        AuthenticationResult result = null;
        ExecutorService service = null;

        try
        {
            service = Executors.newFixedThreadPool (1);
            context = new AuthenticationContext (authority + tenant + "/",
                                                 true,
                                                 service
                                                );
            Future<AuthenticationResult> future = context.acquireTokenByRefreshToken (refreshToken, new ClientCredential (clientId, clientSecret), null, null);
            result = future.get();
        }
        catch (ExecutionException e)
        {
            throw e.getCause();
        }
        finally
        {
            service.shutdown();
        }

        if (result == null)
        {
            throw new ServiceUnavailableException ("authentication result was null");
        }

        return result;
    }

    private AuthenticationResult getAccessToken (AuthorizationCode authorizationCode, String currentUri)
            throws Throwable
    {
        String authCode = authorizationCode.getValue();
        ClientCredential credential = new ClientCredential (clientId, clientSecret);
        AuthenticationContext context;
        AuthenticationResult result = null;
        ExecutorService service = null;

        try
        {
            service = Executors.newFixedThreadPool (1);
            context = new AuthenticationContext (authority + tenant + "/", true, service);
            Future<AuthenticationResult> future = context.acquireTokenByAuthorizationCode (authCode, new URI (currentUri), credential, null);
            result = future.get();
        }
        catch (ExecutionException e)
        {
            throw e.getCause();
        }
        finally
        {
            if (service != null)
            {
                service.shutdown();
            }
            // else do nothing
        }

        if (result == null)
        {
            throw new ServiceUnavailableException ("authentication result was null");
        }

        return result;
    }

    private void setSessionPrincipal (HttpServletRequest httpRequest, AuthenticationResult result)
    {
        httpRequest.getSession().setAttribute (AuthHelper.PRINCIPAL_SESSION_NAME, result);
    }

    private void removePrincipalFromSession (HttpServletRequest httpRequest)
    {
        httpRequest.getSession().removeAttribute (AuthHelper.PRINCIPAL_SESSION_NAME);
    }

    private String getRedirectUrl (String currentUri, String state, String nonce)
            throws UnsupportedEncodingException
    {
        String redirectUrl = authority +
                             this.tenant +
                             "/oauth2/authorize?response_type=code&scope=directory.read.all&response_mode=form_post&redirect_uri=" +
                             URLEncoder.encode (currentUri, "UTF-8") + "&client_id=" +
                             clientId + "&resource=https%3a%2f%2fgraph.microsoft.com" +
                             "&state=" + state +
                             "&nonce=" + nonce;

        return redirectUrl;
    }
}
