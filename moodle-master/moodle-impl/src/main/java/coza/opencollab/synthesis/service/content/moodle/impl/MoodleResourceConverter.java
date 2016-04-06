package coza.opencollab.synthesis.service.content.moodle.impl;

import coza.opencollab.synthesis.service.api.content.AbstractResourceContentConverter;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.impl.ByteArrayEntry;
import coza.opencollab.synthesis.shared.Resource;
import coza.opencollab.synthesis.util.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * This convert the resources into storage entries.
 * 
 * @author OpenCollab
 */
public class MoodleResourceConverter extends AbstractResourceContentConverter<List<Resource>, Object> {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getStorageKey(){
        return "resources";
    }
    
    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(MoodleResourceConverter.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StorageEntry> convert(String moduleId, List<Resource> resources) {
        if(resources == null || resources.isEmpty()){
            return null;
        }
        //Sorting, so the collections/folders is before the files.
        sort(resources);
        List<StorageEntry> entries = new ArrayList<StorageEntry>();
        Map<String, Resource> data = new HashMap<String, Resource>();
        for(Resource resource: resources){
            data.put(resource.getId(), resource);
            if(!resource.isCollection() && resource.getContent() != null){
                resource.setDownloadKey(addExtraResource(moduleId, entries, resource.getName(), resource.getTreeParentId(), resource.getUrl()));
            }
            resource.setContent(null);
        }
        if(resources.size() > 0){
            ByteArrayEntry entry = new ByteArrayEntry("resources.json", "data", JsonParser.writeJsonBytes(data));
            entries.add(entry);
        }
        return entries;
    }

    /**
     * Sort the resources by key, to get the collections first before the files.
     */
    private void sort(List<Resource> resources) {
        Collections.sort(resources, new Comparator<Resource>(){

            @Override
            public int compare(Resource r1, Resource r2) {
                return r1.getId().compareTo(r2.getId());
            }
        });
    }
}
