/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.service.impl.ConfigAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.IConfigAccessSvc;

/**
 * Note that test mode parameters are hard coded for development so that a changed cfg file will
 * not screw things up.
 *
 * @author Paul.Allen
 */
public class Config implements Serializable
{
    private IConfigAccessSvc cfgSvc;
    private Collection<Configuration> configs;

    private final String cfgPath = "/etc/portal.cfg";

    private boolean valid = false;

    private String apiLoginId;
    private String clientKey;
    private String gatewayId;
    private String transId;
    private String mode;

    private String testApiLoginId;
    private String testTransId;
    private String testUrl;
    private String testManage;
    private String testAddPay;
    private String testAddShip;
    private String testEditPay;
    private String testEditShip;
    private String testPayment;
    private String liveUrl;
    private String liveManage;
    private String liveAddPay;
    private String liveAddShip;
    private String liveEditPay;
    private String liveEditShip;
    private String livePayment;
    private String accessMode;
    private String liveApiLoginId;
    private String liveTransId;
    private String pgaClientKey;

    private HashMap<String,String> configMap = new HashMap();


    /** Creates a new instance of Config
     * @param context */
    public Config()
    {
        super();
        valid = parseCfgFile();
    }

    /**
     * @return the liveApiLoginId
     */
    public String getLiveApiLoginId()
    {
        return liveApiLoginId;
    }

    /**
     * @return the liveTransId
     */
    public String getLiveTransId()
    {
        return liveTransId;
    }

    /**
     * @return the testPayment
     */
    public String getTestPayment()
    {
        return "https://test.authorize.net/payment/payment";
////        return testPayment;
    }

    /**
     * @return the livePayment
     */
    public String getLivePayment()
    {
        return livePayment;
    }

    /**
     *
     */
    public boolean checkForUpdates()
    {
        boolean result = false;

        if (!checkCfg())
        {
            updateCfg();
            result = true;
        }
        // Nothing to do - no changes indicated

        return result;
    }

    /**
     *
     */
    public void updateCfg()
    {
        valid = parseCfgFile();
    }

    /**
     * @return the testManage
     */
    public String getTestManage()
    {
        return "https://test.authorize.net/customer/manage";
////        return testManage;
    }

    /**
     * @return the testAddPay
     */
    public String getTestAddPay()
    {
        return "https://test.authorize.net/customer/addPayment";
////        return testAddPay;
    }

    /**
     * @return the testAddShip
     */
    public String getTestAddShip()
    {
        return "https://test.authorize.net/customer/addShipping";
////        return testAddShip;
    }

    /**
     * @return the testEditPay
     */
    public String getTestEditPay()
    {
        return "https://test.authorize.net/customer/editPayment";
////        return testEditPay;
    }

    /**
     * @return the testEditShip
     */
    public String getTestEditShip()
    {
        return "https://test.authorize.net/customer/editShipping";
////        return testEditShip;
    }

    /**
     * @return the testUrl
     */
    public String getTestUrl()
    {
        return "https://apitest.authorize.net/xml/v1/request.api";
////        return testUrl;
    }

    /**
     * @return the testApiLoginId
     */
    public String getTestApiLoginId()
    {
        return testApiLoginId;
    }

    /**
     * @return the testTransId
     */
    public String getTestTransId()
    {
        return testTransId;
    }

    /**
     *
     * @return the pgaClientKey
     */
    public String getPgaClientKey()
    {
        return pgaClientKey;
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

        cfgSvc = new ConfigAccessSvcImpl();
        configs = cfgSvc.readConfigs();
//        cfgSvc.close();

        if (configs.isEmpty())
        {
            configs = cfgSvc.readConfigs();
//            cfgSvc.close();
        }

        if (!configs.isEmpty())
        {
            cfgItr = configs.iterator();

            do
            {
                config = (Configuration)cfgItr.next();
                value = config.getValue();
                key = config.getId().getConfigKey();

                getConfigMap().put (key, value);

                switch (key)
                {
                    case "testApiLoginId":
                        testApiLoginId = value;
                        break;

                    case "testTransId":
                        testTransId = value;
                        break;

                    case "liveApiLoginId":
                        liveApiLoginId = value;
                        break;

                    case "liveTransId":
                        liveTransId = value;
                        break;

                    case "testUrl":
                        testUrl = value;
                        break;

                    case "testManage":
                        testManage = value;
                        break;

                    case "testAddPay":
                        testAddPay = value;
                        break;

                    case "testAddShip":
                        testAddShip = value;
                        break;

                    case "testEditPay":
                        testEditPay = value;
                        break;

                    case "testEditShip":
                        testEditShip = value;
                        break;

                    case "accessMode":
                        accessMode = value;
                        break;

                    case "liveUrl":
                        liveUrl = value;
                        break;

                    case "liveManage":
                        liveManage = value;
                        break;

                    case "liveAddPay":
                        liveAddPay = value;
                        break;

                    case "liveAddShip":
                        liveAddShip = value;
                        break;

                    case "liveEditPay":
                        liveEditPay = value;
                        break;

                    case "liveEditShip":
                        liveEditShip = value;
                        break;

                    case "testPayment":
                        testPayment = value;
                        break;

                    case "livePayment":
                        livePayment = value;
                        break;

                    case "pgaClientKey":
                        pgaClientKey = value;
                        break;

                    case "clientKey":
                        setClientKey (value);
                        break;

                    /* If parameter is "valid", then set it to "true" in the database
                     * to indicate we updated the configs in memory.
                     */
                    case "valid":
                        config.setValue ("true");

                        {
                            Collection<Configuration> newConfigs = new ArrayList<Configuration>();
                            newConfigs.add (config);
                            result = cfgSvc.persist (configs);

                            if (!result)
                            {
//                                cfgSvc.close();
                                cfgSvc.persist (configs);
                                cfgSvc.close();
                            }
                            //else do nothing
                        }

                        break;

                    // Ignore everything else - it's an invlad parameter
                    default:
                        break;
                }
            } while (cfgItr.hasNext());
        }
        //else do nothing

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

        return result;
    }

    private boolean checkCfg()
    {
        boolean result = false;
        Configuration config;

        config = cfgSvc.readConfig ("valid");

        if (config == null)
        {
            cfgSvc.close();
            config = cfgSvc.readConfig ("valid");
        }

        if (config != null)
        {
            switch (config.getValue())
            {
                case "true":
                    result = true;
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
     * Hard coded to test mode for development
     *
     * @return the accessMode
     */
    public String getAccessMode()
    {
        return "TEST";
////        return accessMode;
    }

    /**
     * @return the liveUrl
     */
    public String getLiveUrl()
    {
        return liveUrl;
    }

    /**
     * @return the liveManage
     */
    public String getLiveManage()
    {
        return liveManage;
    }

    /**
     * @return the liveAddPay
     */
    public String getLiveAddPay()
    {
        return liveAddPay;
    }

    /**
     * @return the liveAddShip
     */
    public String getLiveAddShip()
    {
        return liveAddShip;
    }

    /**
     * @return the liveEditPay
     */
    public String getLiveEditPay()
    {
        return liveEditPay;
    }

    /**
     * @return the liveEditShip
     */
    public String getLiveEditShip()
    {
        return liveEditShip;
    }

    public String getConfig (String key)
    {
        return getConfigMap ().get (key);
    }

    /**
     * @return the apiLoginId
     */
    public String getApiLoginId ()
    {
        return apiLoginId;
    }

    /**
     * @param apiLoginId the apiLoginId to set
     */
    public void setApiLoginId (String apiLoginId)
    {
        this.apiLoginId = apiLoginId;
    }

    /**
     * @param clientKey the clientKey to set
     */
    public void setClientKey (String clientKey)
    {
        this.clientKey = clientKey;
    }

    /**
     * @return the gatewayId
     */
    public String getGatewayId ()
    {
        return gatewayId;
    }

    /**
     * @param gatewayId the gatewayId to set
     */
    public void setGatewayId (String gatewayId)
    {
        this.gatewayId = gatewayId;
    }

    /**
     * @return the transId
     */
    public String getTransId ()
    {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId (String transId)
    {
        this.transId = transId;
    }

    /**
     * @return the mode
     */
    public String getMode ()
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
     * @return the configMap
     */
    public HashMap<String,String> getConfigMap ()
    {
        return configMap;
    }

    /**
     * @param configMap the configMap to set
     */
    public void setConfigMap (
            HashMap<String,String> configMap)
    {
        this.configMap = configMap;
    }
}
