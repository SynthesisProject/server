package coza.opencollab.synthesis.shared;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Internal Status Message class, unrelated to the LMSs
 *
 * @author OpenCollab
 */
public class StatusMessage {
    
    public final String FAILURE = "Failure";
    public final String SUCCESS = "Success";
    public final String UNCLEAR = "Unclear Completion";
    /**
     * The class which is sending/returning the message
     */
    private String sender;
    /**
     * What the goal of the operation is/was
     */
    private String activity;
    /**
     * Status of the activity i.e. success || failure || unclear completion
     */
    private String status;
    /**
     * An extended explanation of the status if it is required
     */
    private String statusMessage;
    /**
     * If there are multiple operations per activity e.g. multiple tools must be
     * deleted, then a message for each tool will be added. e.g.
     * <"Delete Tool Resource for Module 1" , "Deleted">,
     * <"Delete Tool Schedule for Module 1", "No such tool Module 1"> ...
     */
    private Map<String, String> itemMessage;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getItemMessage() {
        return itemMessage;
    }

    public void setItemMessage(Map<String, String> itemMessage) {
        this.itemMessage = itemMessage;
    }

    @Override
    public String toString() {
        return "StatusMessage{" + "sender=" + sender + ", activity=" + activity + ", status=" + status + ", statusMessage=" + statusMessage + ", itemMessage=" + itemMessage + "[ "+getItemMessages().toString() + " ]"+'}';
    }

    private StringBuilder getItemMessages() {
        StringBuilder sb = new StringBuilder();
        if (null != this.itemMessage) {
            for (Map.Entry<String, String> entry : this.itemMessage.entrySet()) {
                sb.append(entry.getKey() + " Value : " + entry.getValue()+",");
            }
        }
        return sb;
    }
}
