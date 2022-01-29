/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

/**
 *
 * @author Paul.Allen
 */
public class PortalErrorCodes
{
    //General error codes
    public static final int TRANSACTION_FAILED          = -1;
    public static final int TRANSACTION_SUCCESS         = 0;
    public static final int TRANSACTION_NOGUID          = 1;
    public static final int TRANSACTION_NOAUTH          = 2;
    public static final int TRANSACTION_GUID_ERR        = 3;
    public static final int TRANSACTION_RESP_UKNWN      = 4;
    public static final int API_SERVER_CLIENT_AUTH      = 5;
    public static final int API_SERVER_CLIENT_CONN      = 6;
    public static final int API_SERVER_CLIENT_RESP      = 7;
    public static final int API_SERVER_RESOURCE         = 8;
    public static final int API_SERVER_OUT_ERROR        = 9;
    public static final int API_SERVER_OUT_EXCEP        = 10;
    public static final int API_TRANSACTION_CANCELED    = 11;

    // ANet error codes
    public static final int ANET_CONNECTION_FAILURE = 500;
    public static final int ANET_PAYMENT_PAGE_LOAD  = 1000;
    public static final int ANET_PROFILE_LOAD       = 1001;
    public static final int ANET_PROFILE_CREATE     = 1002;
    public static final int ANET_PROFILE_DELETE     = 1003;
    public static final int ANET_PAYMENT_REQUIRED   = 1004;
    public static final int ANET_PROFILE_PAGE_LOAD  = 1005;
    public static final int ANET_ACCOUNT_EXPECTED   = 1006;
    public static final int ANET_NO_SETTING_NAME    = 1007;
    public static final int ANET_NO_SETTING_VALUE   = 1008;
    public static final int ANET_INVALID_SETTING_NAME    = 1009;
    public static final int ANET_INVALID_SETTING_VALUE   = 1010;
    public static final int ANET_PAYMENT_FAILED          = 1011;
    public static final int ANET_REPORT_FAILED     = 2000;

    // Error message constants
    public static final String TRANS_MSG_SUCCESS    = "Success";
    public static final String TRANS_NOT_AUTH       = "Client not authorized";
    public static final String TRANS_AUTH_NO_PROFILE        = "Client authorized, no profile found";
    public static final String TRANS_GUID_MISMATCH          = "GUID mismatch";
    public static final String TRANS_PAGE_LOAD_FAILURE      = "Page load failure";
    public static final String TRANS_PROF_CREATE_FAILURE    = "Profile creation failure";
    public static final String TRANS_PROF_PROFILE_FAILURE   = "Profile load failure";
    public static final String TRANS_NO_GUID        = "No GUID provided";
    public static final String TRANS_DELETE_FAILED  = "Failed to delete payment profile";
    public static final String TRANS_PAYMENT_RQD    = "At least one payment profile required";
    public static final String TRANS_ACCOUNT_RQD    = "Customer account information required";
    public static final String TRANS_SETTING_NAME   = "Page setting sent, but setting name missing";
    public static final String TRANS_SETTING_VALUE  = "Page setting sent, but setting value missing";
    public static final String TRANS_INVALID_NAME   = "Page setting sent, but setting name invalid";
    public static final String TRANS_INVALID_VALUE  = "Page setting sent, but setting value invalid";
    public static final String TRANS_INVALID_PARAM  = "Missing or invalid parameter";
    public static final String TRANS_PROF_EXISTS    = "Profile already exists";
    public static final String TRANS_PAY_FAILED     = "Payment transaction failed";
    public static final String TRANS_CLIENT_OPTION  = "Client OPTIONS Request";
    public static final String TRANS_CLIENT_TRANS   = "Client Transaction Request";
    public static final String TRANS_PROC_CONN_FAIL = "Payment Processor Connection Failure";
    public static final String API_CONNECT_EXCEPT   = "API connection exception";
    public static final String API_RESOURCE_ERROR   = "Resource processing error";
    public static final String TRANS_AMT_INVALID    = "Invalid payment amount, payment amount not a number";
    public static final String PAY_PROCESSING_FAIL  = "Payment system could not process transaction";
    public static final String REPORT_DETAILS_FAIL  = "Failed to get transaction detail report";

    // Portal error types
    public static final String ANET_TRANS_ERROR     = "ANETTRANS";
    public static final String TRANS_REQUEST        = "TRANSREQ";
    public static final String INTERNAL_ERROR       = "INTERNAL";
    public static final String ANET_CONN_ERROR      = "ANETCONN";
    public static final String ANET_REPORT_ERROR    = "ANETRPT";
}
