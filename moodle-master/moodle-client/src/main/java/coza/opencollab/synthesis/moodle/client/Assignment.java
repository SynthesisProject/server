package coza.opencollab.synthesis.moodle.client;

/**
 * 
 * @author OpenCollab
 */
public class Assignment {

    private String baseUrl = "";
    private static String ASSIGNMENTSFUNCTION = "mod_assign_get_assignments";
    private String courseparams = "courseids[]={0}";

    public Assignment() {
        // TODO Auto-generated constructor stub
    }

    public void getAssignments() {

    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
