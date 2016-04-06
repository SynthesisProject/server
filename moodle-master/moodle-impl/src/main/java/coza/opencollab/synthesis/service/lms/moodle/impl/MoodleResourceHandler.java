package coza.opencollab.synthesis.service.lms.moodle.impl;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.moodle.client.ResourcesService;
import coza.opencollab.synthesis.moodle.client.dao.CourseBlock;
import static coza.opencollab.synthesis.service.api.ErrorCodes.LMS_CONTENT;
import coza.opencollab.synthesis.service.api.lms.LMSContentService;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import coza.opencollab.synthesis.shared.Resource;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author OpenCollab
 */
public class MoodleResourceHandler implements LMSContentService, ToolContentService {

    /**
     * Synthesis Desktop handles folders based on the folder type This value is
     * injected via spring in moodle-lms-services.xmls
     */
    private String folderType;
    /**
     * Synthesis Desktop handles files based on the file type This value is
     * injected via spring in moodle-lms-services.xmls
     */
    private String fileType;
    /*
     * The Moodle announcements for a module
     */
    @Autowired
    private ResourcesService resourcesService;

    private final MimetypesFileTypeMap mimeTypesMap;
    private static final Logger log = Logger.getLogger(MoodleResourceHandler.class.getName());

    public MoodleResourceHandler() {
        mimeTypesMap = new MimetypesFileTypeMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getContent(String sessionId, String moduleId, Date fromDate) {
        //get resources for a site
        List<Resource> resources = new ArrayList<Resource>();
        try {
            resources = getResources(resourcesService.getResourcesForSite("json", sessionId, Integer.parseInt(moduleId), fromDate), sessionId, moduleId, fromDate);
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the resources from Moodle (" + moduleId + ").", e);
        }
        return resources;
    }

    /**
     * Accept json from the the moodle LMS. Process the data returning Synthesis
     * resource objects.
     *
     * @param json
     * @param token
     * @param moduleId
     * @return
     */
    private List<Resource> getResources(String json, String token, String moduleId, Date fromDate) {
        List<Resource> resources = new ArrayList<Resource>();
        List<CourseBlock> courseBlocks = new ArrayList<CourseBlock>();
        courseBlocks = new Gson().fromJson(json, new TypeToken<List<CourseBlock>>() {
        }.getType());
        for (CourseBlock cb : courseBlocks) {
            List<Object> mods = cb.getModules();
            for (Object mmo : mods) {
                getModuleResources(token, moduleId, fromDate, mmo, resources);
            }
        }
        return resources;
    }

    /**
     * Given Moodle Module Objects , parse over them extracting resources.
     * Return the resources to be written to the file system.
     *
     * Moodle Folders: They are a top level tool, but the contents of a folder
     * module are actually flat.
     *
     *
     * @param token
     * @param moduleId
     * @param moodleMduleObject
     * @param resources
     */
    private void getModuleResources(String token, String moduleId, Date fromDate, Object moodleMduleObject, List<Resource> resources) {
        Map<String, String> map = (LinkedTreeMap<String, String>) moodleMduleObject;
        Map<String, String> folderMap = new TreeMap<String, String>();
        if (map.get("modname").equals("folder")) {
            //create folder then parse through contents
            Object contentsObj = map.get("contents");
            List<LinkedTreeMap> contents = (ArrayList<LinkedTreeMap>) contentsObj;
            for (LinkedTreeMap ltm : contents) {
                Date tcDate = new Date(new Float(Float.parseFloat(ltm.get("timecreated").toString())).longValue() * 1000);
                Date tuDate = new Date(new Float(Float.parseFloat(ltm.get("timemodified").toString())).longValue() * 1000);
                if (fromDate == null || tcDate.after(fromDate) || tuDate.after(fromDate)) {
                    createFolderResource(resources, moduleId);
                    createModuleFolder(resources, moduleId, map.get("name"), null);
                    Resource res = new Resource();
                    String modulePath = moduleId + "/" + String.valueOf(map.get("name"));
                    if (!ltm.get("filepath").equals("/")) {
                        if (!folderMap.containsKey(String.valueOf(ltm.get("filepath")))) {
                            String filePath = String.valueOf(ltm.get("filepath"));
                            String[] folders = filePath.split("/");
                            createModuleFolder(
                                    resources,
                                    modulePath,
                                    filePath.substring(1, filePath.length() - 1),
                                    getCorrectParentPath(folders, filePath));
                            modulePath = modulePath + filePath.substring(0, filePath.length() - 1);
                            folderMap.put(String.valueOf(ltm.get("filepath")), folders[folders.length - 1]);
                        }
                    }
                    createResource(res, ltm, modulePath, token, null);
                    resources.add(res);
                }
            }
        }
        if (map.get("modname").equals("resource")) {
            // the only problem is that course modules do not have date meta data.
            //else just create the resources
            Object contentsObj = map.get("contents");
            List<LinkedTreeMap> contents = (ArrayList<LinkedTreeMap>) contentsObj;
            for (LinkedTreeMap ltm : contents) {
                Date tcDate = new Date(new Float(Float.parseFloat(ltm.get("timecreated").toString())).longValue() * 1000);
                Date tuDate = new Date(new Float(Float.parseFloat(ltm.get("timemodified").toString())).longValue() * 1000);
                if (fromDate == null || tcDate.after(fromDate) || tuDate.after(fromDate)) {
                    createFolderResource(resources, moduleId);
                    createModuleFolder(resources, moduleId, map.get("name"), null);
                    Resource res = new Resource();
                    String modulePath = "";
                    if (!moduleId.contains(String.valueOf(map.get("name")))) {
                        modulePath = moduleId + "/" + String.valueOf(map.get("name"));
                    } else {
                        modulePath = moduleId;
                    }
                    
                    if (!ltm.get("filepath").equals("/")) {
                        if (!folderMap.containsKey(String.valueOf(ltm.get("filepath")))) {
                            String filePath = String.valueOf(ltm.get("filepath"));
                            String[] folders = filePath.split("/");
                            createModuleFolder(
                                    resources,
                                    modulePath,
                                    filePath.substring(1, filePath.length() - 1),
                                    getCorrectParentPath(folders, filePath));
                            modulePath = modulePath + filePath.substring(0, filePath.length() - 1);
                            folderMap.put(String.valueOf(ltm.get("filepath")), folders[folders.length - 1]);
                        }
                    }
                    createResource(res, ltm, moduleId, token, String.valueOf(map.get("name")));
                    resources.add(res);
                }
            }
        }
    }

    /**
     * Get the actual binary data data is wrapped as follows String(base64
     * encoded (byte[] )))
     *
     * @param sessionId
     * @param resourceId
     *
     * TODO : this method accepts does not conform to the contract. The resource
     * id cannot be used in place of a url.
     */
    @Override
    public byte[] getResourceData(String sessionId, String resourceId) {
        try {
            URL url = new URL(resourceId);
            ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
            FileCopyUtils.copy(url.openConnection().getInputStream(), os);
            return os.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object getUserContent(String sessionId, String username, String moduleId, Date fromDate) {
        return null;
    }

    @Override
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResourcesService getResourcesService() {
        return resourcesService;
    }

    public void setResourcesService(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    /**
     * If the resource is a top level resource that is not within a folder
     * create a site folder to contain it. Moodle does not deal with folders in
     * the same manner as Sakai
     */
    private void createFolderResource(List<Resource> resources, String moduleId) {
        for (Resource resource : resources) {
            //only add the folder resource if it was not added before
            if (resource.getId().equals(moduleId)) {
                return;
            }
        }
        Resource res = new Resource();
        res.setId(moduleId);
        res.setTreeId(moduleId + "/");
        res.setTreeParentId(moduleId);
        res.setName(moduleId);
        res.setTitle(moduleId);
        res.setDescription("");
        res.setReference("/content/group/" + moduleId + "/");
        res.setResourceType(getFolderType());
        res.setAccess("site");
        res.setCreatedByDisplayName("");
        res.setLastChangedBy("");
        res.setSize(5);
        res.setUrl("");
        res.setDownloadKey("");
        res.setLastChangedByDisplayName("");
        res.setParentId(moduleId);
        res.setMimeType("resource/collection");
        res.setCollection(true);
        res.setCreated(new Date());
        res.setCreatedBy("");
        //adds the folder resource at the start to reduce seektime when looking for it
        resources.add(0, res);
    }

    /**
     * The moodle folder tool uses different rules compared to the resource tool
     * Below we create the moodle module folder resource and child folders using
     * these rules Rule 1: include pathing Rule 2: parent IDs need to be altered
     *
     * @param resources
     * @param moduleId
     * @param folderName
     * @param parentId
     */
    private void createModuleFolder(List<Resource> resources, String moduleId, String folderName, String parentId) {
        Resource res = new Resource();
        res.setId(folderName);
        res.setTreeId(moduleId + "/" + folderName + "/");
        res.setTreeParentId((parentId != null) ? moduleId + parentId : moduleId + "/");
        res.setName(folderName);
        String[] folderNameArr = folderName.split("/");
        if(folderNameArr.length > 0){
            res.setTitle(folderNameArr[folderNameArr.length - 1]);
        }else{
            res.setTitle(folderName);
        }
        res.setDescription("");
        res.setReference("/content/group/" + moduleId + "/" + folderName + "/");
        res.setResourceType(getFolderType());
        res.setAccess("site");
        res.setCreatedByDisplayName("");
        res.setLastChangedBy("");
        res.setSize(5);
        res.setUrl("");
        res.setDownloadKey("");
        res.setLastChangedByDisplayName("");
        res.setParentId((parentId != null) ? moduleId + parentId : moduleId + "/");
        res.setMimeType("resource/collection");
        res.setCollection(true);
        res.setCreated(new Date());
        res.setCreatedBy("");
        resources.add(res);
    }

    /**
     * Creates a synthesis resource based on the data received from Moodle Some
     * customizations needed to be done to the data in order for them to create
     * a proper structure within the Synthesis desktop tool (Sakai-ified).
     *
     * @param res
     * @param ltm
     * @param moduleId
     * @param token
     */
    private void createResource(Resource res, LinkedTreeMap ltm, String moduleId, String token, String containerName) {
        if (null != containerName && !moduleId.contains(containerName)) {
            containerName = "/" + containerName;
        } else {
            containerName = "";
        }
        if (!moduleId.contains(String.valueOf(ltm.get("filepath")).substring(String.valueOf(ltm.get("filepath")).length() - 1, String.valueOf(ltm.get("filepath")).length()))) {
            moduleId = moduleId + containerName + (String) ltm.get("filepath");
        } else {
            moduleId = moduleId + containerName + "/";
        }
        res.setName((String) ltm.get("filename"));
        res.setId("/group/" + moduleId + (String) ltm.get("filename"));
        res.setCollection(!(ltm.get("type").equals("file")));
        Float fl = Float.parseFloat(ltm.get("timecreated").toString());
        res.setCreated(new Date(fl.longValue() * 1000));
        fl = Float.parseFloat(ltm.get("timemodified").toString());
        res.setLastChanged(new Date(fl.longValue() * 1000));
        res.setCreatedBy(String.valueOf(ltm.get("author")));
        fl = Float.parseFloat(String.valueOf(ltm.get("filesize")));
        res.setSize(fl.intValue());
        res.setDescription("");
        res.setResourceType(getFileType());
        res.setAccess("site");
        res.setParentId("/group/" + moduleId);
        if (!res.getParentId().substring(res.getParentId().length() - 1, res.getParentId().length()).equals("/")) {
            res.setParentId("/group/" + moduleId + "/");
        }
        res.setTreeId(moduleId + res.getName());
        res.setTreeParentId(moduleId);
        res.setReference("/content" + res.getId());

        res.setMimeType(mimeTypesMap.getContentType(String.valueOf(ltm.get("filename"))));
        res.setCreatedByDisplayName(String.valueOf(ltm.get("author")));
        res.setTitle(String.valueOf(ltm.get("filename")));
        String fixURL = String.valueOf(ltm.get("fileurl"));
        fixURL = fixURL.replace("pluginfile.php", "pluginfile.php?file=").replace("?forcedownload", "&forcedownload");
        res.setUrl(fixURL + "&token=" + token);
        res.setContent(getResourceData(token, res.getUrl()));
    }

    /**
     * Correct the parent path, the current parent path needs to have its last
     * trailing / character trimmed if it is a multi level path
     *
     * @param folders
     * @param filePath
     * @return
     */
    private String getCorrectParentPath(String[] folders, String filePath) {
        if (folders.length > 1) {
            return filePath.replace(folders[folders.length - 1] + "/", "");
        }
        return null;
    }

    public String getFolderType() {
        return folderType;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * required by the synthesis API, for the Sakai LMS implementation, but not
     * needed with Moodle as it returns the URL in the rest response.
     *
     * @param sessionId
     * @param resourceId
     * @return
     */
    @Override
    public String getResourceURL(String sessionId, String resourceId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
