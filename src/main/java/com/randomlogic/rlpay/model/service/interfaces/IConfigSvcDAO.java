/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Component ("cfgDao")
public interface IConfigSvcDAO extends CrudRepository<Configuration, Integer>
{
    @Query ("SELECT c FROM Configuration c WHERE configKey = ?1")
    Configuration findByConfigKey (String configKey);
}
