/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import java.util.Collection;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;

/**
 * Persistence interface
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public interface Ipersist extends IService
{
    /**
     *
     * @param object
     * @return
     */
    boolean persist (Collection<?> object);

    /**
     *
     * @param object
     * @return
     */
    public IDomainObject find (IDomainObject object);

    public boolean delete (String type);

    public void close();

    public boolean save (Collection<?> object);

    public boolean merge (Collection<?> object);
}
