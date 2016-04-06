package coza.opencollab.synthesis.service.lms.moodle.converter.impl;

import coza.opencollab.synthesis.service.api.lms.converter.ModuleConverter;
import coza.opencollab.synthesis.shared.Module;
import org.dom4j.Element;

/**
 * The moodle implementation of the module converter.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class MoodleModuleConverter implements ModuleConverter {

    /**
     * {@inheritDoc} This method except a 'site' Element as parameter.
     */
    @Override
    public Module getModule(Object moduleData) {
        Element site = (Element) moduleData;
        Module module = new Module();
        module.setId(site.elementTextTrim("id"));
        module.setName(site.elementTextTrim("title"));
        module.setDescription(site.elementTextTrim("shortDescription"));
        module.setCreatedDate(site.elementTextTrim("createdDate"));
        return module;
    }
}
