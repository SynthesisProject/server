package coza.opencollab.synthesis.service.lms.sakai.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.sakai.client.ResourcesService;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.service.api.lms.LMSContentService;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import coza.opencollab.synthesis.service.api.log.LogService;
import static coza.opencollab.synthesis.service.api.util.impl.DomHelper.*;
import coza.opencollab.synthesis.shared.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.axis.encoding.Base64;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Resource tool handler, service API handler
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public class SakaiResourceHandler implements LMSContentService, ToolContentService {
    /*
     * The Sakai Resources for a  module
     */

    @Autowired
    private ResourcesService resourcesService;
    /**
     * The log service.
     */
    @Autowired
    private LogService logService;

    /** {@inheritDoc} */
    @Override
    public Object getContent(String sessionId, String moduleId, Date fromDate) {
        try {
            Document document = createDocument(resourcesService.getResourcesForSite(sessionId, moduleId, fromDate));
            return getResources(moduleId, document, sessionId);
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the resources from Sakai (" + moduleId + ").", e);
        }
    }

    /**
     * <p>getResources.</p>
     *
     * @param moduleId a {@link java.lang.String} object.
     * @param document a {@link org.dom4j.Document} object.
     * @param sessionId a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<Resource> getResources(String moduleId, Document document, String sessionId) {
        List<Resource> resourceList = new ArrayList<>();
        List<Element> elements = getElements(document, "/list/resource");
        for (Element element : elements) {
            resourceList.add(getResource(moduleId, element, sessionId));
        }
        return resourceList;
    }

    /**
     * getResource receives a XML node which contains a Resource(s). It then
     * parses the node and returns an resource object(s).
     *
     * @return List of announcements
     * @param moduleId a {@link java.lang.String} object.
     * @param element a {@link org.dom4j.Element} object.
     * @param sessionId a {@link java.lang.String} object.
     */
    public Resource getResource(String moduleId, Element element, String sessionId) {
        Resource resource = new Resource();
        resource.setId(getAttribute(element, "id"));
        resource.setParentId(getAttribute(element, "parent-id"));
        resource.setTreeId(convertPath(moduleId, getAttribute(element, "id")));
        resource.setTreeParentId(convertPath(moduleId, getAttribute(element, "parent-id")));
        resource.setName(getAttribute(element, "name"));
        resource.setAccess(getAttribute(element, "access"));
        resource.setTitle(getAttribute(element, "title"));
        resource.setDescription(getAttribute(element, "description"));
        resource.setReference(getAttribute(element, "reference"));
        resource.setMimeType(getAttribute(element, "type"));
        resource.setResourceType(getAttribute(element, "resource-type"));
        resource.setCollection(getAttributeAsBoolean(element, "is-collection"));
        resource.setCreated(getAttributeAsDate(element, "created"));
        resource.setCreatedBy(getAttribute(element, "created-by"));
        resource.setCreatedByDisplayName(getAttribute(element, "created-by-display-name"));
        resource.setLastChanged(getAttributeAsDate(element, "last-changed"));
        resource.setLastChangedBy(getAttribute(element, "last-changed-by"));
        resource.setLastChangedByDisplayName(getAttribute(element, "last-changed-by-display-name"));
        resource.setReleaseDate(getAttributeAsDate(element, "release-date"));
        resource.setRetractDate(getAttributeAsDate(element, "retract-date"));
        resource.setSize(getAttributeAsLong(element, "size"));
        resource.setUrl(getAttribute(element, "url"));
        resource.setLinkURL(getAttribute(element, "linkURL"));
        resource.setStatus(getAttribute(element, "status"));
        if (!resource.isCollection() && !"DELETED".equals(resource.getStatus())) {
            resource.setContentURL(getResourceURL(sessionId, resource.getId()));
        }
        return resource;
    }

    /**
     * Convert the path to start at the given name.
     */
    private String convertPath(String startName, String path) {
        return path.substring(path.indexOf(startName));
    }

    /**
     * {@inheritDoc}
     *
     * Get the actual binary data data is wrapped as follows String(base64
     * encoded (byte[] )))
     */
    @Override
    public byte[] getResourceData(String sessionId, String resourceId) {
        try {
            Document document = createDocument(resourcesService.getResourceData(sessionId, resourceId));
            Element element = getElement(document, "/resource/content");
            return Base64.decode(element.getText());
        } catch (Exception e) {
            logService.log("Could not retrieve the resource data from Sakai (" + resourceId + ").", e, LogService.Priority.High);
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the resources from Sakai Ressource : (" + resourceId + ").", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Get the URL for direct download of resource
     */
    @Override
    public String getResourceURL(String sessionId, String resourceId) {
        try {
            Document document = createDocument(resourcesService.getResourceURL(sessionId, resourceId));
            Element element = getElement(document, "/resource/url");
            return element.getText() + "?sakai.session=" + sessionId;
        } catch (Exception e) {
            logService.log("Could not retrieve the resource URL from Sakai (" + resourceId + ").", e, LogService.Priority.High);
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the resource URL from Sakai Ressource : (" + resourceId + ").", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object getUserContent(String sessionId, String username, String moduleId, Date fromDate) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
