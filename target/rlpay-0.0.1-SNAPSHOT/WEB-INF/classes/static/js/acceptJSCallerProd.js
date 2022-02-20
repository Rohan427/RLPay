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
            errorCode = "404";
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
                    myAmt = '$' + document.getElementById ('amount_to_pay').value;
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

                if (portalMode !== "dev")
                {
                    data = {
                            "accountid" : myAccount,
                            "amount" : myAmt,
                            "authcode" : "",
                            "clientip" : "0.0.0.0",
                            "customerid" : myAccount,
                            "errorcode" : errorCode,
                            "errormsg" : errorMsg,
                            "errorsource" : "MASKED",
                            "errortype" : "MASKED",
                            "logtext" : errorMsg,
                            "transactionid" : transId,
                            "users" : "MASKED"
                           };

                    createLogEntry (data);
                }
                // else do nothing
            }
            catch (error)
            {
                console.log ("Couldn't parse result string");
                message = "Error.";
            }

            break;
    }// END SWITCH
}

function parseLog (msg)
{
    description = document.getElementById ('description').value;
    historyElement = document.getElementById ('payHistory');

    try
    {
        errorCode = msg.getElementsByTagName ("errorCode")[0].childNodes[0].nodeValue;
        errorMsg = msg.getElementsByTagName ("errorMsg")[0].childNodes[0].nodeValue;
        refId = msg.getElementsByTagName ("guid")[0].childNodes[0].nodeValue;
        logList = msg.getElementsByTagName ("logList");

        oldRefId = document.getElementById ("ref_id").value;

        if ((refId !== oldRefId ) || (errorCode === "1") || errorCode === "3")
        {
            console.log (errorMsg);
        }
        else
        {
            if (errorCode === "0")
            {
                console.log ("SUCCESS");

                for (var i = 0; i < logList.length; i++)
                {
                    log = logList[i];
                    row = historyElement.insertRow (-1);

                    cell = row.insertCell (0);
                    cell.innerHTML = log.childNodes[9].childNodes[0].nodeValue;// date

                    cell = row.insertCell (1);
                    cell.innerHTML = log.childNodes[4].childNodes[0].nodeValue;// error code

                    cell = row.insertCell (2);
                    cell.innerHTML = log.childNodes[5].childNodes[0].nodeValue;// error message

                    cell = row.insertCell (3);
                    cell.innerHTML = log.childNodes[11].childNodes[0].nodeValue;// transaction id

                    cell = row.insertCell (4);
                    cell.innerHTML = log.childNodes[0].childNodes[0].nodeValue;// amount

                    if (i >= 10)
                    {
                        break;
                    }
                }
            }
            else
            {
                 console.log ("FAILURE");
            }
        }
        // else do nothing
    }
    catch (error)
    {
        console.log ("Couldn't parse result string");
    }
}

function getParams()
{
    var urlParams = new URLSearchParams (window.location.search);

    return urlParams;
}

function getLogs (callback)
{
    var account = null, beginDate = null, endDate = null;

    account = document.getElementById ('accountnumber').value;

    if (account !== null || account !== "")
    {
        $.ajax
        (
            {
                cache: false,
                url: acceptJSCaller ('url'),
                data:
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<soap:Body>" +
                        "<ns1:log xmlns:ns1=\"http://service.portal/\">" +
                            "<request xmlns=\"\">" +
                                "<beginDate>" + beginDate + "</beginDate>" +
                                "<client>" +
                                    "<authToken></authToken>" +
                                    "<clientUID>" + acceptJSCaller ('user') + "</clientUID>" +
                                    "<deviceId>" + acceptJSCaller ('key') + "</deviceId>" +
                                "</client>" +
                                "<guid>" + document.getElementById ('refid').value + "</guid>" +
                                "<endDate>" + endDate + "</endDate>" +
                                "<log>" +
                                    "<amount></amount>" +
                                    "<authCode></authCode>" +
                                    "<customerId>" + account + "</customerId>" +
                                    "<errorCode></errorCode>" +
                                    "<errorMsg></errorMsg>" +
                                    "<errorType></errorType>" +
                                    "<logDate></logDate>" +
                                    "<logText></logText>" +
                                    "<transactionId></transactionId>" +
                                "</log>" +
                            "</request>" +
                        "</ns1:log>" +
                    "</soap:Body>" +
                "</soap:Envelope>",
                contentType: 'text/xml',
                method: 'POST',
                timeout: 5000
            }
        ).done
        (
            function (data)
            {
//                console.log ('Success');
            }
        ).success
        (
            function (data)
            {
                console.log (data);
                callback (data);
            }
        )
        .fail
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
            }
        )
    }
}

// Do an AJAX call to submit the transaction data and the payment nonce to a
// separate PHP page to do the actual transaction processing.
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
                    "<Body>" +
                        "<payment xmlns=\"http://facade.payment.portal.rlpay,randomlogic.com/\">" +
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
//                console.log ('Success');
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
                returnValue = '26dc23ztUjxEVcK6EsrZCV72UKmhwnj4TD6zMRG4wvWBU57LX6kBAE2jXxss75u2';
            }
            else if (portalMode === "dev")
            {
                returnValue = '26dc23ztUjxEVcK6EsrZCV72UKmhwnj4TD6zMRG4wvWBU57LX6kBAE2jXxss75u2';
            }
            else if (portalMode === "prod")
            {
                returnValue = '2GtC4w7UXT4EMg37sszKTxjm8hAWC5jTAwKhUTz8skyvqS72HpLvpBp32y92yfGE';
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
                returnValue = 'https://telgar.randomlogic.net:7102/RLPAY/Sandbox';
            }
            else if (portalMode === "dev")
            {
                returnValue = 'https://telgar.randomlogic.net:8102/RLPAY/Sandbox';
            }
            else if (portalMode === "prod")
            {
                returnValue = 'https://telgar.randomlogic.net:8102/RLPAY/Access';
            }
            else
            {
                returnValue = "none";
            }

            break;

        case 'user':
            if (portalMode === "test")
            {
                returnValue = 'portaltest';
            }
            else if (portalMode === "dev")
            {
                returnValue = 'portaltest';
            }
            else if (portalMode === "prod")
            {
                returnValue = 'portaluser';
            }
            else
            {
                returnValue = "none";
            }

        default:
            break;
    }

    return returnValue;
}

function createLogEntry (data)
{
    // create account record
    Xrm.WebApi.online.createRecord ("payment_transaction_log", data).then
    (
        function success (result)
        {
            console.log ("Transaction Log created with ID: " + result.id);
            // perform operations on record creation
        },

        function (error)
        {
            console.log (error.message);
            // handle error conditions
        }
    );
}

var acceptLoaded = {};