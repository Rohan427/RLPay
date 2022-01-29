/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.util.Date;
import com.randomlogic.rlpay.model.domain.company.entity.UserId;

/**
 *
 * @author Paul.Allen
 */
public interface IAPIUser extends IDomainObject
{
////    Set getAuthorizations ();

    Date getCreation ();

    String getEmail ();

    UserId getId ();

    String getPassword ();

    String getToken ();

    byte getValid ();

////    void setAuthorizations (Set authorizations);

    void setCreation (Date creation);

    void setEmail (String email);

    void setId (UserId id);

    void setPassword (String password);

    void setToken (String token);

    void setValid (byte valid);

    int getEncLevel();

    void setEncLevel (int encLevel);

    String getEncType();

    void setEncType (String encType);
}
