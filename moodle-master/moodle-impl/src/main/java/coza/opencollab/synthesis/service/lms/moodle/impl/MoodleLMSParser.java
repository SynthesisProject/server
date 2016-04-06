package coza.opencollab.synthesis.service.lms.moodle.impl;

import coza.opencollab.synthesis.moodle.client.dao.Course;
import coza.opencollab.synthesis.service.api.lms.LMSParser;
import coza.opencollab.synthesis.service.api.lms.filter.ModuleFilter;
import coza.opencollab.synthesis.service.api.lms.filter.impl.AllowAllModuleFilter;
import coza.opencollab.synthesis.shared.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The Sakai implementation of the parser.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class MoodleLMSParser implements LMSParser {

    /**
     * A filter to remove modules that cannot be handled
     */
    private ModuleFilter moduleFilter = new AllowAllModuleFilter();

    /**
     * A filter to remove modules that cannot be handled
     */
    public void setModuleFilter(ModuleFilter moduleFilter) {
        this.moduleFilter = moduleFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseSessionId(String data) {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> parseUserDetails(String data) {
        Map<String, String> map = new HashMap<String, String>();
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Module> parseModules(String data) {
        return parseModules(data, moduleFilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Module> parseModules(String data, ModuleFilter moduleFilter) {
        List<Module> modules = new ArrayList<Module>();
        
        List<Course> enrolledCourses = new Gson().fromJson(data, new TypeToken<List<Course>>() {}.getType());
        
        for(Course c : enrolledCourses){
        	Module mod = new Module();
            mod.setId(String.valueOf(c.getId()));
            mod.setName(c.getShortname());
            mod.setDescription(c.getFullname());
            if(moduleFilter.allow(mod)) {
            	modules.add(mod);
            }
        }        
        return modules;
    }

}
