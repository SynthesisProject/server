package coza.opencollab.synthesis.service.api.lms.filter.impl;

import coza.opencollab.synthesis.service.api.lms.filter.ToolFilter;
import coza.opencollab.synthesis.shared.Tool;
import java.util.Iterator;
import java.util.List;

/**
 * A tool filter that will allow all tool as long as it is not null.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class AllowAllToolFilter implements ToolFilter{
    /**
     * Allow all non-null tools
     *
     * {@inheritDoc}
     */
    @Override
    public boolean allow(Tool tool) {
        return tool!=null;
    }

    /**
     * Filter by iterating over the list calling {@link #allow(coza.opencollab.synthesis.shared.Tool)}.
     *
     * {@inheritDoc}
     */
    @Override
    public void filter(List<Tool> tools) {
        if(tools == null){
            return;
        }
        Iterator<Tool> i = tools.iterator();
        while(i.hasNext()){
            Tool t = i.next();
            if(!allow(t)){
                i.remove();
            }
        }
    }

}
