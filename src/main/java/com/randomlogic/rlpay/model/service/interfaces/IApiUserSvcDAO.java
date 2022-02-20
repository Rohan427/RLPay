/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import com.randomlogic.rlpay.model.domain.company.entity.Users;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Service ("apiUsrDao")
public interface IApiUserSvcDAO extends CrudRepository<Users, Integer>
{
    @Query ("SELECT u FROM Users u WHERE userId = ?1 AND deviceId = ?2")
    IDomainObject findUserByUserIdAndDeviceId (String userId, String deviceId);

    @Query ("SELECT u FROM Users u WHERE userId = ?1")
    IDomainObject findUserByUserId (String userId);
}
