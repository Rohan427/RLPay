/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Component ("logDao")
public interface ILogSvcDAO extends CrudRepository<Logs, Integer>
{
    @Query ("SELECT l FROM Logs l WHERE customerid LIKE '%:customerId%'"
            + "AND transactionid LIKE '%:transactionId%'"
            + "AND errorcode LIKE '%:errorCode%'"
            + "AND errormsg LIKE '%:errorMsg%'"
            + "AND amount LIKE '%:amount%'"
           )
    Logs findLogsWithParms (@Param("customerId") String customerId,
                            @Param("transactionId") String transactionId,
                            @Param("errorCode") String errorCode,
                            @Param("errorMsg") String errorMsg,
                            @Param("amount") String amount
                           );

    @Query ("SELECT l FROM Logs l WHERE customerid LIKE '%:customerId%'" +
            " AND transactionid LIKE '%:transactionId%'" +
            " AND errorcode LIKE '%:errorCode%'" +
            " AND errormsg LIKE '%:errorMsg%'" +
            " AND amount LIKE '%:amount%'" +
            " AND logdate >= ':begin'" +
            " AND logdate <= ':end'")
    List<Logs> searchLogsBetween (@Param("customerId") String customerId,
                                  @Param("transactionId") String transactionId,
                                  @Param("errorCode") String errorCode,
                                  @Param("errorMsg") String errorMsg,
                                  @Param("amount") String amount,
                                  @Param("begin") String begin,
                                  @Param("end") String end
                                 );

    @Query ("SELECT l FROM Logs l WHERE customerid LIKE '%:customerId%'" +
            " AND transactionid LIKE '%:transactionId%'" +
            " AND errorcode LIKE '%:errorCode%'" +
            " AND errormsg LIKE '%:errorMsg%'" +
            " AND amount LIKE '%:amount%'" +
            " AND logdate >= ':begin'" +
            " AND logdate <= ':end'")
    List<Logs> searchLogsByDate (@Param("customerId") String customerId,
                                 @Param("transactionId") String transactionId,
                                 @Param("errorCode") String errorCode,
                                 @Param("errorMsg") String errorMsg,
                                 @Param("amount") String amount,
                                 @Param("begin") String begin
                                );

    @Query ("SELECT l FROM Logs l WHERE customerid LIKE '%:customerId%'" +
            " AND transactionid LIKE '%:transactionId%'" +
            " AND errorcode LIKE '%:errorCode%'" +
            " AND errormsg LIKE '%:errorMsg%'" +
            " AND amount LIKE '%:amount%'")
    List<Logs> searchLogs (@Param("customerId") String customerId,
                           @Param("transactionId") String transactionId,
                           @Param("errorCode") String errorCode,
                           @Param("errorMsg") String errorMsg,
                           @Param("amount") String amount
                          );
}
