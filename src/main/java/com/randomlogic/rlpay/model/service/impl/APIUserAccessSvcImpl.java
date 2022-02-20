/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.model.domain.company.entity.Users;
import com.randomlogic.rlpay.model.domain.interfaces.IAPIUser;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import com.randomlogic.rlpay.model.service.interfaces.IAPIUserAccessSvc;
import com.randomlogic.rlpay.model.service.interfaces.IApiUserSvcDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Service ("apiUsrSvc")
public class APIUserAccessSvcImpl implements IAPIUserAccessSvc
{
    @Autowired
    IApiUserSvcDAO apiUsrDao;

    /**
     *
     * @param type
     * @return
     */
    @Override
    public boolean delete (String type)
    {
        boolean result = false;

        return result;
    }

    @Override
    public IDomainObject find (IDomainObject object)
    {
        Users user;

        user = (Users)object;

        if (user != null)
        {
            try
            {
                user = (Users)apiUsrDao.findUserByUserId (user.getId().getUid());
            }
            catch (Exception e)
            {
                user = null;
                Logger.log (Logger.EXCEPTION, APIUserAccessSvcImpl.class, e);
            }
        }
        // else do nothing

        return user;
    }

    @Override
    public boolean persist (Collection<?> object)
    {
        boolean result = true;

        return result;
    }

    @Override
    public boolean save (Collection<?> object)
    {
        boolean result = true;
        Users newUser;
        Iterator iterator;
        Collection<Users> users;

        if (object != null)//validate
        {
            users = (Collection<Users>)object;

            try
            {
                iterator = users.iterator();

                while (iterator.hasNext() && result)
                {
                    newUser = (Users)iterator.next();
                    apiUsrDao.save (newUser);
                }
            }
            catch (Exception e)
            {
                result = false;

                Logger.log (Logger.EXCEPTION, APIUserAccessSvcImpl.class, e);
                }
            }
        else
        {
            Logger.log (APIUserAccessSvcImpl.class, "APIUser.save failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public Users readUser (Integer iduser)
    {
        return (Users)find (new Users (iduser));
    }

    @Override
    public Collection<Users> readUsers()
    {
        Collection<Users> users = new ArrayList<Users>();

        try
        {
            apiUsrDao.findAll ().forEach (users::add);
        }
        catch (Exception e)
        {
            Logger.log (Logger.EXCEPTION, APIUserAccessSvcImpl.class, e);
        }

        return users;
    }

    @Override
    public boolean removeUser (IAPIUser user)
    {
        return true;
    }

    @Override
    public boolean updateUser (IAPIUser user, boolean isUpdate)
    {
        boolean result = false;

        if (isUpdate)
        {
            if (apiUsrDao.existsById (user.getId().getIdUser()))
            {
                user = apiUsrDao.save ((Users)user);
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean merge (Collection<?> object)
    {
        boolean result = true;

        return result;
    }

    @Override
    public void close()
    {

    }
}
