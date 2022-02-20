/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.entity;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class AzureAPICodes
{
    public static final int AZURE_SUCCESS           = 0;
    public static final int AZURE_AUTH_FAIL         = 1;
    public static final int AZURE_AUTH_VALIDATE     = 2;
    public static final int AZURE_AUTH_NONCE        = 3;
    public static final int AZURE_URI_INVALID       = 4;
    public static final int AZURE_OAUTH_FAIL        = 5;
    public static final int AZURE_AUTH_MSG_FAIL     = 6;
    public static final int AZURE_AUTH_TOKEN_FAIL   = 7;

    public static final int AZURE_AUTH_REDIRECT     = 302;
    public static final int AZURE_AUTH_INTERNAL     = 500;

    public static final String AZMSG_SUCCESS            = "Success";
    public static final String AZMSG_AUTH_FAIL          = "Request for auth code failed.";
    public static final String AZMSG_AUTH_VALIDATE      = "Could not validate state.";
    public static final String AZMSG_AUTH_NONCE         = "Could not validate nonce.";
    public static final String AZMSG_URI_INVALID        = "Invalid URI.";
    public static final String AZMSG_OAUTH_PARSE        = "OAUTH parse failure.";
    public static final String AZMSG_AUTH_MSG_FAIL      = "Invalid set of aretifacts received.";
    public static final String AZMSG_AUTH_TOKEN_FAIL    = "Failed to retrieve access token.";
}
