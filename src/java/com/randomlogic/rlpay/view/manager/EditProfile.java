/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.manager;

import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.CreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade.AnetSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import com.randomlogic.rlpay.application.util.ServletParams;


/**
 * TODO: Login and Registration should check for current profile.
 *          If profile found, then open hosted manage page.
 *          If not found, Login gives error, and Registration opens initial
 *              profile creation page.
 */


/**
 *
 * @author Paul.Allen
 */
public class EditProfile extends HttpServlet
{
    /**
     * Test Profile Data:
     * First:           Paul
     * Last:            Allen
     * Account:         12345
     * Auth.Net Token:  1505611429 (1504903871)
     * Email:           pgallen@gmail.com
     * UID:             pgallen
     * CustID:          12345
     * CardNumber:      4111111111111111
     * ExpirationDate:  1220
     */

    private ServletParams params;

    private ICustomer customer = new CustomerProfile();
    private String resultPage = "/profile.jsp";
    private User user = null;

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
    public String getServletInfo ()
    {
        return "Short description";
    }// </editor-fold>

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param newrequest
     * @param newresponse
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest (HttpServletRequest newrequest, HttpServletResponse newresponse)
                                   throws ServletException, IOException
{
        params = new ServletParams (new ErrorBean(), getServletConfig().getServletContext(), newrequest, newresponse);

        user = (User)params.getRequest().getSession().getAttribute ("userBean");

        switch (params.getRequest().getParameter ("mode"))
        {
            case "get":
                getProfile();
                break;

            case "new":
                newProfile();
                break;

            case "manage":
                if (user.getAuthToken().equals ("0"))
                {
                    getProfile();
                }
                // else Do nothing, profile was already loaded

                manageProfile();
                break;

            // An invalid request will just reload the profile page
            default:
                break;
        }

        try
        {
            params.getContext().getRequestDispatcher (resultPage).forward (params.getRequest(), params.getResponse());
        }
        catch (ServletException | IOException ex)
        {
            Logger.log (0, EditProfile.class, ex, "EditProfile: Exception.\n\n");

            if (params.getError() != null)
            {
                params.getRequest().getSession().removeAttribute ("errorMsg");
                params.getRequest().getSession().setAttribute ("errorMsg", params.getError());
                params.getError().generateError (params.getContext(),
                                                 params.getRequest(),
                                                 params.getResponse(),
                                                 "Configuration",
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

    private void newProfile()
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        String account = params.getRequest().getParameter ("account");
        String expDate = params.getRequest().getParameter ("exp");
        String state = params.getRequest().getParameter ("state");
        String country = params.getRequest().getParameter ("country");
        String company = params.getRequest().getParameter ("company");
        IBilling billing;
        int environment = IPaymentSvc.ENV_SANDBOX;
        int mode = IPaymentSvc.TEST_MODE;
        String url = params.getCfg().getTestManage();
        boolean validate = true;

        expDate = expDate.replace ("/", "");

        // If no selection made, use the text input, if that is also empty, then input not valid
        state = state.equals ("") ? state = params.getRequest().getParameter ("prov") : state;

        if (state.equals (""))
        {
            validate = false;
        }
        // else Do nothing, input is valid

        if (validate)
        {
            // If no selection made, use the text input, if that is also empty, then input not valid
            country = country.equals ("") ? params.getRequest().getParameter ("countrynl") : country;

            if (country.equals (""))
            {
               validate = false;
            }
            // else Do nothing, input is valid

            if (validate)
            {
                paymentSvc.addCreditCard (new CreditCard (params.getRequest().getParameter ("number"),
                                                          expDate,
                                                          null, params.getRequest().getParameter ("ccv")
                                                         )
                                         );

                billing = paymentSvc.addBilling (params.getRequest().getParameter ("fname"),
                                                 params.getRequest().getParameter ("lname"),
                                                 company,
                                                 params.getRequest().getParameter ("add1") + " " +
                                                 params.getRequest().getParameter ("add2"),
                                                 params.getRequest().getParameter ("city"),
                                                 state,
                                                 params.getRequest().getParameter ("zip"),
                                                 country,
                                                 params.getRequest().getParameter ("phone"),
                                                 params.getRequest().getParameter ("fax")
                                                );

                if (company.equals (""))
                {
                    paymentSvc.addPayment (IPaymentSvc.CUST_INDIVIDUAL, billing);
                }
                else
                {
                    paymentSvc.addPayment (IPaymentSvc.CUST_BUSINESS, billing);
                }

                customer = paymentSvc.createCustomer (account,
                                                      params.getRequest().getParameter ("desc"),
                                                      params.getRequest().getParameter ("email"),
                                                      IPaymentSvc.TYPE_GUEST,
                                                      "testid"
                                                     );

                // Select the payment processor access mode - live or test - to use
                switch (params.getCfg().getAccessMode())
                {
                    case "LIVE":
                        environment = IPaymentSvc.ENV_PRODUCTION;
                        mode = IPaymentSvc.LIVE_MODE;
                        url = params.getCfg().getLiveManage();
                        break;

                    case "TEST":
                        environment = IPaymentSvc.ENV_SANDBOX;
                        mode = 3;
                        url = params.getCfg().getTestManage();
                        customer.setProfileType (IPaymentSvc.TYPE_GUEST);
                    default:
                        break;
                }

                paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
                paymentSvc.setTransactionKey (params.getCfg().getTestTransId());

                // TODO: Need to update to return an actual profile
                customer = paymentSvc.createCustomerProfile (customer,
                                                             mode,
                                                             environment
                                                            );
            }// END IF-ELSE: if (country.equals (""))
            else
            {
                ;//TODO: Handle the error - country
            }
        }// END IF_ELSE: if (state.equals (""))
        else
        {
            ;//TODO: Handle the error - state
        }
    }

    private void badRequest() throws IOException, ServletException
    {
        params.getContext().log ("EditProfile: Bad Request.\n\n" + params.getUri());

        if (params.getError() != null)
        {
            params.getRequest().getSession().removeAttribute ("errorMsg");
            params.getRequest().getSession().setAttribute ("errorMsg", params.getError());
            params.getError().generateError (params.getContext(),
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

    private void getProfile() throws ServletException, IOException
    {
        // TODO: Validate input
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        String custId = params.getRequest().getParameter ("account");

        int environment = IPaymentSvc.ENV_SANDBOX;
        String url = params.getCfg().getTestManage();

        // Select the payment processor access mode - live or test - to use
        switch (params.getCfg().getAccessMode())
        {
////            case "LIVE":
////                environment = IPaymentSvc.ENV_PRODUCTION;
////                url = params.getCfg().getLiveManage();
////                break;

            case "TEST":
            default:
                environment = IPaymentSvc.ENV_SANDBOX;
                url = params.getCfg().getTestManage();
                break;
        }

        paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
        paymentSvc.setTransactionKey (params.getCfg().getTestTransId());

        customer = paymentSvc.getCustomerProfile (null,
                                                  custId,
                                                  null,
                                                  "testid",//TODO: Make this a real refId - ref generated by system to track requests
                                                  true,
                                                  true,
                                                  environment, false
                                                 );

        resultPage = "/profile.jsp";
    }

    private void manageProfile()
    {

    }
}
