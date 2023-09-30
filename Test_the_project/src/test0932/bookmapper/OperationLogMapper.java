package test0932.bookmapper;

import test0932.bookmanagerclass.OperationLog;
import test0932.utils.Resultmappable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class OperationLogMapper implements Resultmappable<OperationLog> {
    @Override
    public OperationLog mapper(ResultSet set) throws SQLException {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogId(set.getInt("log_id"));
        operationLog.setDescription(set.getString("description"));
        operationLog.setDescription(set.getString("operation_time"));
        return operationLog;
    }
}
