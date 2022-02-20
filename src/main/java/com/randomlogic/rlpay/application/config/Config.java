/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.config;

import com.randomlogic.rlpay.application.monitor.LogData;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.service.impl.ConfigAccessSvcImpl;
import static com.randomlogic.rlpay.resources.paymentProcessors.config.PortalErrorCodes.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Note that test mode parameters are hard coded for development so that a changed cfg file will
 * not screw things up.
 *
 * @author Paul.Allen
 */
@Component ("cfgBean")
public class Config implements Serializable
{
    private String apiLoginId;

    @Autowired (required = true)
    private ConfigAccessSvcImpl cfgSvc;

    private String clientKey;
    private HashMap<String,String> configMap = new HashMap();
    private Collection<Configuration> configs;
    private String gatewayId;
    private String liveApiLoginId;
    private String mode;
    private String testApiLoginId;
    private String transId;
    private boolean valid = false;

    private boolean isCfgsValid()
    {
        boolean result = false;
        Configuration config;

        config = cfgSvc.readConfig ("valid");

        if (config == null)
        {
            cfgSvc.close();
            config = cfgSvc.readConfig ("valid");
        }
        // else do nothing

        if (config != null)
        {
            switch (config.getCfgvalue())
            {
                case "true":
                    if (valid)
                    {
                        result = true;
                    }

                    break;

                    // Ignore everything else - it's an invalid value
                default:
                    break;
            }
        }
        //else do notthing

        return result;
    }

    /**
     *
     */
    public boolean checkForUpdates()
    {
        boolean result = false;

        if (!isCfgsValid())
        {
            updateCfgs();
            result = true;
        }
        // Nothing to do - no changes indicated

        return result;
    }
    /**
     * Hard coded to test mode for development
     *
     * @return the accessMode
     */
    public String getAccessMode()
    {
        return "TEST";
////        return configMap.get ("mode");
    }
    /**
     * @return the apiLoginId
     */
    public String getApiLoginId()
    {
        return apiLoginId;
    }
    /**
     * @param apiLoginId the apiLoginId to set
     */
    public void setApiLoginId (String apiLoginId)
    {
    }

    /**
     *
     * @return the clientKey
     */
    public String getClientKey()
    {
        return clientKey;
    }

    /**
     * @param clientKey the clientKey to set
     */
    public void setClientKey (String clientKey)
    {
    }

    public String getConfig (String key)
    {
        return configMap.get (key);
    }

    /**
     * @return the configMap
     */
    public HashMap<String,String> getConfigMap()
    {
        return configMap;
    }

    /**
     * @param configMap the configMap to set
     */
    public void setConfigMap (HashMap<String,String> configMap)
    {
//        this.configMap = configMap;
    }

    /**
     * @return the gatewayId
     */
    public String getGatewayId()
    {
        return gatewayId;
    }

    /**
     * @param gatewayId the gatewayId to set
     */
    public void setGatewayId (String gatewayId)
    {
    }

    /**
     * @return the liveAddPay
     */
    public String getLiveAddPay()
    {
        return getConfig ("liveAddPay");
    }

    /**
     * @return the liveAddShip
     */
    public String getLiveAddShip()
    {
        return getConfig ("liveAddShip");
    }
    /**
     * @return the liveApiLoginId
     */
    public String getLiveApiLoginId()
    {
        return liveApiLoginId;
    }

    /**
     * @return the liveEditPay
     */
    public String getLiveEditPay()
    {
        return getConfig ("liveEditPay");
    }

    /**
     * @return the liveEditShip
     */
    public String getLiveEditShip()
    {
        return getConfig ("liveEditShip");
    }

    /**
     * @return the liveManage
     */
    public String getLiveManage()
    {
        return getConfig ("liveManage");
    }

    /**
     * @return the livePayment
     */
    public String getLivePayment()
    {
        return getConfig ("livePayment");
    }

    /**
     * @return the liveTransId
     */
    public String getLiveTransId()
    {
        return getConfig ("liveTransId");
    }

    /**
     * @return the liveUrl
     */
    public String getLiveUrl()
    {
        return getConfig ("liveUrl");
    }

    /**
     * @return the mode
     */
    public String getMode()
    {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode (String mode)
    {
        this.mode = mode;
    }

    /**
     *
     * @return the pgaClientKey
     */
    public String getPgaClientKey()
    {
        return getConfig ("pgaClientKey");
    }

    /**
     * @return the testAddPay
     */
    public String getTestAddPay()
    {
        return "https://test.authorize.net/customer/addPayment";
////        return getConfig ("testAddPay");
    }

    /**
     * @return the testAddShip
     */
    public String getTestAddShip()
    {
        return "https://test.authorize.net/customer/addShipping";
////        return getConfig ("testAddShip");
    }

    /**
     * @return the testApiLoginId
     */
    public String getTestApiLoginId()
    {
        return testApiLoginId;
    }

    /**
     * @return the testEditPay
     */
    public String getTestEditPay()
    {
        return "https://test.authorize.net/customer/editPayment";
////        return getConfig ("testEditPay");
    }

    /**
     * @return the testEditShip
     */
    public String getTestEditShip()
    {
        return "https://test.authorize.net/customer/editShipping";
////        return getConfig ("testEditShip");
    }

    /**
     * @return the testManage
     */
    public String getTestManage()
    {
        return "https://test.authorize.net/customer/manage";
////        return getConfig ("testManage");
    }

    /**
     * @return the testPayment
     */
    public String getTestPayment()
    {
        return "https://test.authorize.net/payment/payment";
////        return getConfig ("testPayment");
    }

    /**
     * @return the testTransId
     */
    public String getTestTransId()
    {
        return getConfig ("testTransId");
    }

    /**
     * @return the testUrl
     */
    public String getTestUrl()
    {
        return "https://apitest.authorize.net/xml/v1/request.api";
////        return getConfig ("testUrl");
    }

    /**
     * @return the transId
     */
    public String getTransId()
    {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId (String transId)
    {
    }

    /**
     * @return the valid
     */
    public boolean isValid()
    {
        return valid;
    }

    private boolean parseCfgFile()
    {
        boolean result = false;
        Iterator cfgItr;
        Configuration config;
        String value;
        String key;

        configs = cfgSvc.readConfigs();

    if (configs.isEmpty())
    {
        configs = cfgSvc.readConfigs();
    }
    // else do nothing

    if (!configs.isEmpty())
    {
        cfgItr = configs.iterator();

        do
        {
            config = (Configuration)cfgItr.next();
            value = config.getCfgvalue();
            key = config.getId().getConfigKey();

            configMap.put (key, value);
        } while (cfgItr.hasNext());

        // Set specific configs for old style JSP pages
        switch (configMap.get ("mode"))
        {
            case "prod":
                apiLoginId = configMap.get ("apiLoginId");
                transId = configMap.get ("transId");
                clientKey = configMap.get ("clientKey");
                gatewayId = configMap.get ("gatewayId");
                break;

            case "dev":
                apiLoginId = configMap.get ("devApiLoginId");
                transId = configMap.get ("devTransId");
                clientKey = configMap.get ("devClientKey");
                gatewayId = configMap.get ("devGatewayId");
                break;

            case "test":
            default:
                apiLoginId = configMap.get ("testApiLoginId");
                transId = configMap.get ("testTransId");
                clientKey = configMap.get ("testClientKey");
                gatewayId = configMap.get ("testGatewayId");
                break;
        }

        result = true;
    }
    else
    {
        //TODO: Handle the error
        Logger.log (Config.class, Logger.ERROR, "SEVERE: Failed to read configs");
    }

    return result;
}

    public void setConfig (String key, String value)
    {
        if (configMap.containsKey (key))
        {
            configMap.replace (key, value);
        }
        else
        {
            configMap.put (key, value);
        }
    }
    /**
     *
     */
    private void updateCfgs()
    {
        valid = parseCfgFile();

        if (valid)
        {
            writeConfig ("valid", "true");
        }
        // else do nothing
    }
    private void writeConfig (String cfg, String value)
    {
        Configuration config = cfgSvc.readConfig (cfg);

        if (config == null)
        {
            config = new Configuration();
        }
        // else do nothing

        // Only write if it's an update or new config
        if (!config.getCfgvalue().equals (value))
        {
            config.setCfgvalue (cfg);
            config.getId().setConfigKey (cfg);
            config.setCfgvalue (value);

            // TODO: Add security

            Collection<Configuration> configs = new ArrayList<>();

            configs.add (config);

            if (!cfgSvc.save (configs))
            {
                LogData logData = new LogData();

                logData.setCommand (CMD_LOAD);
                logData.setUsers (1);
                logData.setTransactionId ("0");
                logData.setAuthCode ("none");
                logData.setAmount ("0.00");
                logData.setCustomerId ("0");
                logData.setErrorCode ("" + API_INTERNAL_DB_ERROR);
                logData.setErrorType (TRANS_REQUEST);
                logData.setErrorMsg (DB_CONF_WRITE_FAIL);
                logData.setLogText ("Failed to write config " + cfg + " with value " + value);
                logData.setErrorSource (Config.class.toString());
                logData.setLogDate (Calendar.getInstance().getTime());

                Logger.log (Logger.ERROR, Config.class, new Logs (logData), true);
            }
            else
            {
                LogData logData = new LogData();

                logData.setCommand (CMD_LOAD);
                logData.setUsers (1);
                logData.setTransactionId ("0");
                logData.setAuthCode ("none");
                logData.setAmount ("0.00");
                logData.setCustomerId ("0");
                logData.setErrorCode ("" + API_INTERNAL_DB_SUCCESS);
                logData.setErrorType (INTERNAL_DB_ACCESS);
                logData.setErrorMsg (DB_CONF_WRITE_SUCCESS);
                logData.setLogText ("Wrote config " + cfg + " with value " + value);
                logData.setErrorSource (Config.class.toString());
                logData.setLogDate (Calendar.getInstance().getTime());

                Logger.log (Logger.INFO, Config.class, new Logs (logData), true);
            }
        }
    }
}
