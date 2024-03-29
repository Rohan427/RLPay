/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IInternalPaymentProfile;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
public class CustomerProfile implements ICustomer, Serializable
{
    /** Implementor Customer ID used as MerchantCustomerID for Auth.Net */
    private String customerId = "0";

    /** Auth.Net Customer ID for referencing Auth.Net Customer profile */
    private String profileToken = "0";

    /** Auth.Net Customer profile Description field */
    private String description = "INVALID";

    /** Customer e-mail address used by implementor and Auth.Net */
    private String email = "nobody@nowhere.com";

    /** Unique transaction reference ID generated by implementor for tracking multiple clients */
    private String refId = null;

    /** Auth.Net payment profile list */
    private Collection<IInternalPaymentProfile> paymentProfiles = new ArrayList<IInternalPaymentProfile>();

    // TODO: This needs to be generic to decouple from Auth.Net API
    /** Auth.Net transaction response object. Object type is transaction dependent */
    Object errorResponse = null;

    /** Indicates whether a profile is a guest profile. One of "guest" or "regular" */
    private int profileType = 0;

    /** Host page token valid after requests for a hosted page. */
    private String token = null;

    public CustomerProfile()
    {

    }

    /**
     * @return the profileType
     */
    @Override
    public int getProfileType()
    {
        return profileType;
    }

    /**
     * @param profileType the profileType to set
     */
    @Override
    public void setProfileType (int profileType)
    {
        this.profileType = profileType;
    }

    /**
     * @return the token
     */
    @Override
    public String getToken()
    {
        return token;
    }

    /**
     * @param token the token to set
     */
    @Override
    public void setToken (String token)
    {
        this.token = token;
    }

    @Override
    public Collection<IInternalPaymentProfile> getPaymentProfiles()
    {
        return this.paymentProfiles;
    }

    @Override
    public void setPaymentProfiles (Collection<IInternalPaymentProfile> paymentProfiles)
    {
        this.paymentProfiles = paymentProfiles;
    }

    @Override
    public boolean addPaymentProfile (IInternalPaymentProfile profile)
    {
        return paymentProfiles.add (profile);
    }

    @Override
    public boolean removePaymentProfile (IInternalPaymentProfile profile)
    {
        return paymentProfiles.remove (profile);
    }

    @Override
    public String getRefId()
    {
        return this.refId;
    }

    @Override
    public void setRefId (String refId)
    {
        this.refId = refId;
    }

    @Override
    public Object getErrorResponse()
    {
        return this.errorResponse;
    }

    @Override
    public void setErrorReponse (Object anetResponse)
    {
        this.errorResponse = anetResponse;
    }

    @Override
    public String getEmail()
    {
        return this.email;
    }

    @Override
    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String getCustomerId()
    {
        return this.customerId;
    }

    @Override
    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    @Override
    public String getProfileToken()
    {
        return this.profileToken;
    }

    @Override
    public void setProfileToken (String profileToken)
    {
        this.profileToken = profileToken;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }

    @Override
    public void setDescription (String description)
    {
        this.description = description;
    }

    @Override
    @XmlAnyElement
    public IInternalPaymentProfile getDefaultPaymentProfile()
    {
        IInternalPaymentProfile profile = null;
        Iterator profileIter = paymentProfiles.iterator();
        InternalPaymentProfile[] profileList = new InternalPaymentProfile[paymentProfiles.size()];

        do
        {
            profile = (IInternalPaymentProfile)profileIter.next();
        } while (profileIter.hasNext() && !profile.isDefaultProfile());

        // If no default found, return the first
        if (!profile.isDefaultProfile())
        {
            profileList = paymentProfiles.toArray (profileList);
            profile = profileList[0];
        }
        else
        {
            ;//Do nothing, profile already selected
        }

        return profile;
    }

    @Override
    public boolean setDefaultPaymentProfile (IInternalPaymentProfile profile)
    {
        boolean result = false;
        IInternalPaymentProfile profileToSet = null;
        IInternalPaymentProfile currentProfile = null;
        Iterator profileIter = paymentProfiles.iterator();
        String profileId = profile.getProfileId();

        if (profileId != null)
        {
            // Find the profile to set
            do
            {
                currentProfile = (IInternalPaymentProfile)profileIter.next();

                if (currentProfile.getProfileId().equals (profileId))
                {
                    profileToSet = currentProfile;
                }
                else
                {
                    ;//Do nothing
                }
            } while (profileIter.hasNext() && profileToSet != null);

            if (profileToSet != null)
            {
                // if Found, set profile to default and reset all others
                profileToSet.setDefaultProfile (true);
                profileIter = paymentProfiles.iterator();

                do
                {
                    ((IInternalPaymentProfile)profileIter.next()).setDefaultProfile (false);
                } while (profileIter.hasNext());

                result = true;
            }
            else
            {
                ;//Do nothing
            }
        }
        else
        {
            ;// Do nothing, result is already false
        }

        return result;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof CustomerProfile)
        {
            if (hashCode() == ((CustomerProfile) other).hashCode())
            {
                result = true;
            }
            else
            {

            }
        }
        else
        {
            ;
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode (this.customerId);
        hash = 53 * hash + Objects.hashCode (this.email);
        hash = 53 * hash + Objects.hashCode (this.description);
        return hash;
    }

    @Override
    public String toString()
    {
        return "Customer: " + this.customerId + ", " + this.description + ", " + this.email;
    }
}
