package coza.opencollab.synthesis.moodle.client.impl;

import com.google.gson.Gson;
import coza.opencollab.synthesis.moodle.client.dao.UserSiteInfo;
import coza.opencollab.synthesis.service.api.ModuleService;
import java.util.Map;

/**
 * Implements the module service for Moodle
 * @author OpenCollab
 */
public class ModuleServiceMoodleImpl extends AbstractServiceImpl implements ModuleService {

    private Map<String, String> modNameMap;
    private static final String getUserInfoFromSession = "core_webservice_get_site_info";
    private static final String getUserEnrolledSites = "core_enrol_get_users_courses";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSitesUserCanAccess(String sessionId, String userId) throws Exception {
        String json = callService(sessionId, getUserInfoFromSession, "");
        UserSiteInfo usi = new Gson().fromJson(json, UserSiteInfo.class);
        Integer moodleUserId = usi.getUserid();
        String moduleJson = callService(sessionId, getUserEnrolledSites, "userid=" + moodleUserId );
        return moduleJson;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSitesUserCanAccess(String sessionId) throws Exception {
        throw new UnsupportedOperationException("Moodle does not support access without token.");
    }


    public Map<String, String> getModNameMap() {
        return modNameMap;
    }

    public void setModNameMap(Map<String, String> modNameMap) {
        this.modNameMap = modNameMap;
    }
}