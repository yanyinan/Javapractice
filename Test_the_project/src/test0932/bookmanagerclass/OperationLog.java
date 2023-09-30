package test0932.bookmanagerclass;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class OperationLog {
    private Integer logId;
    private String description;
    private String operationTime;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }
}
