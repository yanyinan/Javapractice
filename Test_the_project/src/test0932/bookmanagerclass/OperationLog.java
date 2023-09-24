package test0932.bookmanagerclass;

import java.sql.Time;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class OperationLog {
    private Integer log_id;
    private String description;
    private String operation_time;

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperation_time() {
        return operation_time;
    }

    public void setOperation_time(String operation_time) {
        this.operation_time = operation_time;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "log_id=" + log_id +
                ", description='" + description + '\'' +
                ", operation_time='" + operation_time + '\'' +
                '}';
    }
}
