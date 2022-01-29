/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public interface IDAOService extends Serializable
{
    public Session getSession();

    public void loadService();

    abstract SessionFactory getFactory();

    public void initSession();
}
