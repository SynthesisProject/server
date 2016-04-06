package coza.opencollab.synthesis.service.api.lms.filter.impl;

import coza.opencollab.synthesis.service.api.lms.filter.ModuleFilter;
import coza.opencollab.synthesis.shared.Module;
import java.util.Iterator;
import java.util.List;

/**
 * A module filter that will allow all modules as long as it is not null.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class AllowAllModuleFilter implements ModuleFilter{

    /**
     * Allow all non-null modules
     *
     * {@inheritDoc}
     */
    @Override
    public boolean allow(Module module) {
        return module!=null;
    }

    /**
     * Filter by iterating over the list calling {@link #allow(coza.opencollab.synthesis.shared.Module)}.
     *
     * {@inheritDoc}
     */
    @Override
    public void filter(List<Module> modules) {
        if(modules == null){
            return;
        }
        Iterator<Module> i = modules.iterator();
        while(i.hasNext()){
            Module m = i.next();
            if(!allow(m)){
                i.remove();
            }
        }
    }

}
