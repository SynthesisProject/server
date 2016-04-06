package coza.opencollab.synthesis.service.lms.sakai.impl;

import static coza.opencollab.synthesis.service.api.util.impl.DomHelper.*;
import coza.opencollab.synthesis.service.api.lms.LMSParser;
import coza.opencollab.synthesis.service.api.lms.converter.ModuleConverter;
import coza.opencollab.synthesis.service.lms.sakai.converter.impl.SakaiModuleConverter;
import coza.opencollab.synthesis.service.api.lms.filter.ModuleFilter;
import coza.opencollab.synthesis.service.api.lms.filter.impl.AllowAllModuleFilter;
import coza.opencollab.synthesis.shared.Module;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * The Sakai implementation of the parser.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class SakaiLMSParser implements LMSParser{
    /**
     * The module converter that will retrieve the modules from the data.
     */
    private ModuleConverter moduleConverter = new SakaiModuleConverter();
    /**
     * A filter to remove modules that cannot be handled
     */
    private ModuleFilter moduleFilter = new AllowAllModuleFilter();

    /**
     * The module handler that will retrieve the modules from the data.
     *
     * @param moduleConverter a {@link coza.opencollab.synthesis.service.api.lms.converter.ModuleConverter} object.
     */
    public void setModuleConverter(ModuleConverter moduleConverter) {
        this.moduleConverter = moduleConverter;
    }

    /**
     * A filter to remove modules that cannot be handled
     *
     * @param moduleFilter a {@link coza.opencollab.synthesis.service.api.lms.filter.ModuleFilter} object.
     */
    public void setModuleFilter(ModuleFilter moduleFilter) {
        this.moduleFilter = moduleFilter;
    }

    /** {@inheritDoc} */
    @Override
    public String parseSessionId(String data) {
        return data;
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, String> parseUserDetails(String data){
        Map<String, String> map = new HashMap<>();
        Document doc = createDocument(data);
        Element user = doc.getRootElement();
        List<Element> elements = user.elements();
        for(Element element: elements){
            map.put(element.getName(), element.getTextTrim());
        }
        return map;
    }

    /** {@inheritDoc} */
    @Override
    public List<Module> parseModules(String data) {
        return parseModules(data, moduleFilter);
    }

    /** {@inheritDoc} */
    @Override
    public List<Module> parseModules(String data, ModuleFilter moduleFilter) {
        Document doc = createDocument(data);
        List<Element> sites = getElements(doc, "/sites/site");
        List<Module> modules = new ArrayList<Module>();
        if (sites != null) {
            for (Element site: sites) {
                Module module = moduleConverter.getModule(site);
                if (moduleFilter == null || moduleFilter.allow(module)) {
                    modules.add(module);
                }
            }
        }
        return modules;
    }

}
