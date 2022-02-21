var portalMode = "test";

function setPortalMode (newMode)
{
    portalMode = newMode;
}

// The result of the transaction processing will be returned from the processing
// script as a JSON object. Parse the object to determine success or failure,
// and alert the user.
function messageFunc (msg)
{
    description = document.getElementById ('description').value;

    switch (msg)
    {
        case "CONNECT_FAILURE":
            myAmt = "0.00";
            transId = "";
            errorMsg = "API Connection Error";
            eorrCode = "404";
            window.location = "../../../profile/account-detail/failure?amount=" + myAmt + "&transId=" + transId + "&errorMsg=" + errorMsg + "&errorCode=" + errorCode;
            break;

        default:
            switch (description)
            {
                case "Total Outstanding":
                    myAmt = document.getElementById ('total_outstanding').value;
                    myAccount = document.getElementById ('accountnumber').value;
                    openAmt = myAmt;
                    break;

                case "Balance Due":
                    myAmt = document.getElementById ('balance_due').value;
                    myAccount = document.getElementById ('accountnumber').value;
                    openAmt = myAmt;
                    break;

                case "Specific Amount":
                    myAmt = document.getElementById ('amount_to_pay').value;
                    myAccount = document.getElementById ('accountnumber').value;
                    openAmt = document.getElementById ('total_outstanding').value;
                    break;

                default:
                    break;
            }

            try
            {
                errorCode = msg.getElementsByTagName ("errorCode")[0].childNodes[0].nodeValue;
                errorMsg = msg.getElementsByTagName ("errorMsg")[0].childNodes[0].nodeValue;
                refId = msg.getElementsByTagName ("guid")[0].childNodes[0].nodeValue;
                oldRefId = document.getElementById ("refid").value;
                transId = msg.getElementsByTagName ("transactionId")[0].childNodes[0].nodeValue;

                if ((refId !== oldRefId ) || (errorCode === "1") || errorCode === "3")
                {
                    window.location = "../../../payment-error?code=3&errorMessage=Communication%20with%20payment%20system%20interrupted%20contact%20customer%20support";
                }
                else
                {
                    if (errorCode === "0")
                    {
                        window.location = "../../../profile/account-detail/success?amount=" + myAmt + "&transId=" + transId + "&errorMsg=" + errorMsg;
                    }
                    else
                    {

                         window.location = "../../../profile/account-detail/failure?amount=" + myAmt + "&transId=" + transId + "&errorMsg=" + errorMsg + "&errorCode=" + errorCode;
                    }
                }
            }
            catch (error)
            {
                console.log ("Couldn't parse result string");
                message = "Error.";
            }

            break;
    }// END SWITCH
}

function getParams()
{
    var urlParams = new URLSearchParams (window.location.search);

    return urlParams;
}

// Do an AJAX call to submit the transaction data and the payment nonce to a
// separate page to do the actual transaction processing.
function createTransact (response)
{
    var description, totalAmt, myAmt, myAccount, openAmt, textStatus;

    dataObj = response.opaqueData;
    // Determine the type of payment that's being made. Based upon this
    // select the correct elements from the page to populate the transaction
    // elements.
    description = document.getElementById ('description').value;

    switch (description)
    {
        case "Total Outstanding":
            totalAmt = document.getElementById ('total_outstanding').value;
            myAmt = document.getElementById ('total_outstanding').value;

            // Remove probable invalid characters from user entry
            myAmt = myAmt.replace ("$", "");
            myAmt = myAmt.replace (",", "");

            myAccount = document.getElementById ('accountnumber').value;
            openAmt = totalAmt;
            break;

        case "Balance Due":
            myAmt = document.getElementById ('balance_due').value;
            myAccount = document.getElementById ('accountnumber').value;

            // Remove probable invalid characters from user entry
            myAmt = myAmt.replace ("$", "");
            myAmt = myAmt.replace (",", "");

            openAmt = myAmt;
            totalAmt = myAmt;
            break;

        case "Specific Amount":
            myAmt = document.getElementById ('amount_to_pay').value;

            // Remove probable invalid characters from user entry
            myAmt = myAmt.replace ("$", "");
            myAmt = myAmt.replace (",", "");

            // Validate user entry
            if (isNaN (myAmt))
            {
                myAmt = "0";
            }
            else
            {
                myAmt = Number (myAmt);
            }

            myAccount = document.getElementById ('accountnumber').value;
            openAmt = document.getElementById ('total_outstanding').value;
            totalAmt = openAmt;
            break;

        default:
            break;
    }

    // Remove invalid characters from form amounts
    openAmt = openAmt.replace ("$", "");
    openAmt = Number (openAmt.replace (",", ""));

    totalAmt = totalAmt.replace ("$", "");
    totalAmt = Number (totalAmt.replace (",", ""));

    if (myAmt > 0 && myAmt <= totalAmt)
    {
        $.ajax
        (
            {
                cache: false,
                url: acceptJSCaller ('url'),
                data:
                "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> " +
                    "<Header/>" +
                    "<Body>" +
                        "<payment xmlns=\"http://facade.payment.portal.rlpay.randomlogic.com/\">" +
                            "<request xmlns=\"\">" +
                                "<account>" +
                                    "<accountNo>" + myAccount + "</accountNo>" +
                                    "<amount>" + myAmt + "</amount>" +
                                    "<items>" +
                                        "<description>" + description + "</description>" +
                                        "<itemNo>Payment</itemNo>" +
                                        "<openAmount>" + myAmt  + "</openAmount>" +
                                        "<qty>" + document.getElementById ('qty').value + "</qty>" +
                                    "</items>" +
                                    "<statementNo>N/A</statementNo>" +
                                "</account>" +
                                "<clientData>" +
                                    "<client>" +
                                        "<clientUID></clientUID>" +
                                        "<deviceId>" + acceptJSCaller ('key') + "</deviceId>" +
                                    "</client>" +
                                    "<custAccount>" + myAccount + "</custAccount>" +
                                    "<email>" + document.getElementById ('email').value + "</email>" +
                                    "<guid>" + document.getElementById ('refid').value + "</guid>" +
                                "</clientData>" +
                                "<paymentNonce>" +
                                    "<description>" + dataObj.dataDescriptor + "</description>" +
                                    "<nonce>" + dataObj.dataValue + "</nonce>" +
                                "</paymentNonce>" +
                            "</request>" +
                        "</payment>" +
                    "</Body>" +
                "</Envelope>",
                contentType: 'text/xml',
                method: 'POST',
                timeout: 5000
            }
        ).done
        (
            function (data)
            {
                console.log ('Success');
            }
        ).fail
        (
            function()
            {
                console.log ('Error');
                textStatus = "CONNECT_FAILURE";
            }
        ).always
        (
            function (textStatus)
            {
                console.log (textStatus);
                messageFunc (textStatus);
            }
        )
    }
    else
    {
        alert ("Invalid payment amount supplied. Make sure it's less that the Total Outstanding, and contains only numbers and a decimal point.");
    }
}

// Process the response from Authorize.Net to retrieve the payment nonce.
// If the data looks correct, record the OpaqueData to the console and call the
// transaction processing function.
function paymentHandler (response)
{
    if (response.messages.resultCode === 'Error')
    {
        description = document.getElementById ('description').value;

        switch (description)
        {
            case "Total Outstanding":
                myAmt = document.getElementById ('total_outstanding').value;
                myAccount = document.getElementById ('accountnumber').value;
                openAmt = myAmt;
                break;

            case "Balance Due":
                myAmt = document.getElementById ('balance_due').value;
                myAccount = document.getElementById ('accountnumber').value;
                openAmt = myAmt;
                break;

            case "Specific Amount":
                myAmt = document.getElementById ('amount_to_pay').value;
                myAccount = document.getElementById ('accountnumber').value;
                openAmt = document.getElementById ('total_outstanding').value;
                break;

            default:
                break;
        }

        for (var i = 0; i < response.messages.message.length; i++)
        {
            console.log (response.messages.message[i].code + ':' + response.messages.message[i].text);

            try
            {
                transId = "None";
                errorCode = response.messages.message[0].code;
                errorMsg = response.messages.message[0].text;

                window.location = "../../../profile/account-detail/failure?amount=" + myAmt + "&transId=" + transId + "&errorMsg=" + errorMsg + "&errorCode=" + errorCode;
            }
            catch (error)
            {
                console.log ("Couldn't parse result string");
                window.location = "../../../profile/account-detail/failure?amount=" + myAmt + "&transId=" + transId + "&errorMsg=Unknown%20credit%20card%20processing%20error.%20Contact%20customer%20support&errorCode=4";
            }
        }
    }
    else
    {
        createTransact (response);
    }
}

function acceptJSCaller (value)
{
    returnValue = "none";

    switch (value)
    {
        case 'key':
            if (portalMode === "test")
            {
                returnValue = '879ud8nGm3DqUWe3e4JRqncqthj8mp52bBw3Dw7LCqfQ3TJnsaw46Gt9V6sRcYZ2';
            }
            else if (portalMode === "dev")
            {
                returnValue = '879ud8nGm3DqUWe3e4JRqncqthj8mp52bBw3Dw7LCqfQ3TJnsaw46Gt9V6sRcYZ2';
            }
            else if (portalMode === "prod")
            {
                returnValue = '879ud8nGm3DqUWe3e4JRqncqthj8mp52bBw3Dw7LCqfQ3TJnsaw46Gt9V6sRcYZ2';
            }
            else
            {
                returnValue = "none";
            }

            break;

        case 'apiId':
            if (portalMode === "test")
            {
                returnValue = '9qtv37RBr';
            }
            else if (portalMode === "dev")
            {
                returnValue = '9qtv37RBr';
            }
            else if (portalMode === "prod")
            {
                returnValue = '9qtv37RBr';
            }
            else
            {
                returnValue = "none";
            }

            break;

        case 'url':
            if (portalMode === "test")
            {
                returnValue = 'http://randomlogic.net:8100/RLPAY/Sandbox';
            }
            else if (portalMode === "dev")
            {
                returnValue = 'http://randomlogic.net:8100/RLPAY/Sandbox';
            }
            else if (portalMode === "prod")
            {
                returnValue = 'https://randomlogic.net:8102/RLPAY/Access';
            }
            else
            {
                returnValue = "none";
            }

            break;

        default:
            break;
    }

    return returnValue;
}

var acceptLoaded = {};