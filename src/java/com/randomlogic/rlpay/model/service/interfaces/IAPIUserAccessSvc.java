/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;


import java.util.Collection;
import com.randomlogic.rlpay.model.domain.company.entity.User;
import com.randomlogic.rlpay.model.domain.interfaces.IAPIUser;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public interface IAPIUserAccessSvc extends Ipersist
{
    /**
     *
     * @param user
     * @param isUpdate
     * @return
     */
    public boolean updateUser (IAPIUser user, boolean isUpdate);

    /**
     *
     * @param idUser
     * @return
     */
    public User readUser (Integer idUser);

    /**
     *
     * @return
     */
    Collection<User> readUsers();

    /**
     *
     * @param user
     * @return
     */
    public boolean removeUser (IAPIUser user);
}
