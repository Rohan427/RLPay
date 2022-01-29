/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.manager;

import com.rlencrypt.symmetric.SecureGCM;
import com.rlsecurity.facade.RLSecurity;
import com.randomlogic.rlpay.application.monitor.ErrorBean;
import java.io.IOException;
import javax.crypto.SecretKey;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.portal.payment.domain.ConfigBean;
import com.randomlogic.rlpay.application.util.ServletParams;

/**
 *
 * @author Paul.Allen
 */
public class Security extends HttpServlet
{
    private ServletParams params;
    private String resultPage = null;
    private User user = null;
    private Client client;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest (request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest (request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param newrequest  servlet request
     * @param newresponse servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest (HttpServletRequest newrequest, HttpServletResponse newresponse)
            throws ServletException, IOException
    {
        params = new ServletParams (new ErrorBean(), getServletConfig().getServletContext(), newrequest, newresponse);
        String mode = params.getRequest().getParameter ("mode");

        switch (mode)
        {
            // Here when generating a new key
            case "keyGen":
                keyGen();
                break;

            // Here when initializing the system to use a generated key
            case "initKey":
                initServer();
                break;

            case "testEcrypt":
                encrypt();
                break;

            case "testDecrypt":
                decrypt();
                break;

            case "setMode":
                setMode();
                break;

            // Any other requests resuolt in an error report
            default:
                badRequest();
                break;
        }

        try
        {
            ServletContext context;
            context = params.getContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher (resultPage);

            dispatcher.forward (params.getRequest(), params.getResponse());
        }
        catch (ServletException | IOException ex)
        {
            params.getContext().log ("EditProfile: Exception.\n\n", ex);

            if (params.getError() != null)
            {
                params.getRequest().getSession().removeAttribute ("errorMsg");
                params.getRequest().getSession().setAttribute ("errorMsg", params.getError());
                params.getError().generateError (params.getContext(),
                                                 params.getRequest(),
                                                 params.getResponse(),
                                                 "KeyGen",
                                                 params.getUri(),
                                                 ErrorBean.ERROR_GENERAL_ERROR,
                                                 "Requested page not found",
                                                 "/genError.jsp",
                                                 ex.getMessage(),
                                                 ex, null
                                                );
            }
        }
    }

    private void setMode()
    {
        String svrMode = params.getRequest().getParameter ("svrMode");
        ConfigBean cfgBean = null;

        switch (svrMode)
        {
            case "test":
                cfgBean = (ConfigBean)params.getContext().getAttribute ("cfgBean");
                cfgBean.setMode ("test");
                params.getContext().setAttribute ("cfgBean", cfgBean);
                break;

            case "dev":
                cfgBean = (ConfigBean)params.getContext().getAttribute ("cfgBean");
                cfgBean.setMode ("dev");
                params.getContext().setAttribute ("cfgBean", cfgBean);
                break;

            case "prod":
                cfgBean = (ConfigBean)params.getContext().getAttribute ("cfgBean");
                cfgBean.setMode ("prod");
                params.getContext().setAttribute ("cfgBean", cfgBean);
                break;

            default:
                cfgBean = (ConfigBean)params.getContext().getAttribute ("cfgBean");
                cfgBean.setMode ("dev");
                params.getContext().setAttribute ("cfgBean", cfgBean);
                break;
        }
    }

    private void keyGen()
    {
        String key;
        RLSecurity secModule = new RLSecurity ("jca");

        resultPage = "/admin/keyGenResult.jsp";
        key = secModule.base64KeyGen (params.getRequest().getParameter ("length"), params.getRequest().getParameter ("algorithm"));
        params.getRequest().setAttribute ("aesKey", key);
    }

    private void initServer()
    {
        SecretKey key = null;

        RLSecurity secModule = new RLSecurity ("jca");
        resultPage = "/admin/initResult.jsp";

        key = secModule.restoreKey (params.getRequest().getParameter ("algorithm"),
                                    params.getRequest().getParameter ("length"),
                                    params.getRequest().getParameter ("key")
                                   );
        params.getContext().setAttribute ("siteName" + params.getRequest().getParameter ("keyLevel"), key);
    }

    private void encrypt()
    {
        RLSecurity secModule = new RLSecurity ("jca");
        SecretKey key = (SecretKey)params.getContext().getAttribute ("siteName1");
        String message = params.getRequest().getParameter ("message");
        String aadData = "testAAD";

////        key = secModule.keyGen ("256", "AES");
////
////        String cipher = secModule.encrypt ("AESGCM", key, message, aadData);
////        params.getRequest().setAttribute ("cipher", cipher);

////        String plaintext = new String (secModule.decrypt ("AESGCM", key, cipher, aadData));

////        String plainText = new String (secModule.decrypt ("AES/GCM/PKCS5Padding",
////                                                          cipher.getBytes(),
////                                                          key,
////                                                          secModule.getParameterSpec(),
////                                                          aadData.getBytes()
////                                                         )
////                                      );
////
////        resultPage = "/admin/encryptResult.jsp";

        SecureGCM gcm = SecureGCM.getInstance();

        key = secModule.keyGen ("256", "AES");

        gcm.testCipher (null, null, key, aadData.getBytes());

        resultPage = "/admin/encryptResult.jsp";
    }

    private void decrypt()
    {
        RLSecurity secModule = new RLSecurity ("jca");
        SecretKey key = (SecretKey)params.getContext().getAttribute ("siteName1");
        String cipher = params.getRequest().getParameter ("cipher");
        String aadData = "testAAD";

        String plaintext = new String (secModule.decrypt ("AESGCM", key, cipher, aadData));
        resultPage = "/admin/decryptResult.jsp";
    }

    private void badRequest() throws ServletException, IOException
    {
        params.getContext().log ("Authenticate: Bad Request.\n\n" + params.getUri());

        if (params.getErrorBean() != null)
        {
            params.getRequest().getSession().removeAttribute ("errorMsg");
            params.getRequest().getSession().setAttribute ("errorMsg", params.getErrorBean());
            params.getErrorBean().generateError (params.getContext(),
                                                 params.getRequest(),
                                                 params.getResponse(),
                                                 "Request",
                                                 params.getUri(),
                                                 ErrorBean.ERROR_INVALID_URI,
                                                 "Requested page not found",
                                                 "/genError.jsp",
                                                 null,
                                                 null, null
                                                );
        }
    }
}
