/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import java.util.Collection;
import java.util.Date;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.ILogs;

/**
 *
 * @author Paul.Allen
 */
public interface ILogsAccessSvc extends Ipersist
{
    Logs readLog (String customerId);

    Collection<Logs> searchLogs (ILogs object, Date begin, Date end);

    Collection<Logs> readLogs ();

    boolean removeLog (ILogs log);

    boolean updateLog (ILogs log, boolean isUpdate);
}
