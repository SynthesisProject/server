package coza.opencollab.synthesis.service.content.moodle.impl;

import coza.opencollab.synthesis.service.api.content.AbstractResourceContentConverter;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.impl.ByteArrayEntry;
import coza.opencollab.synthesis.shared.Schedule;
import coza.opencollab.synthesis.util.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author OpenCollab
 */
public class MoodleCalendarConverter extends AbstractResourceContentConverter<List<Schedule>, Object> {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(MoodleCalendarConverter.class);

    @Override
    public String getStorageKey() {
        return "schedule";
    }

    @Override
    public List<StorageEntry> convert(String moduleId, List<Schedule> scheduleList) {
        if (scheduleList == null || scheduleList.isEmpty()) {
            return null;
        }
        List<StorageEntry> entries = new ArrayList<StorageEntry>();
        Map<String, Schedule> data = new HashMap<>();
        for (Schedule schedule : scheduleList) {
            //updateLinks(moduleId, schedule, entries);
            data.put(schedule.getId(), schedule);
        }
        if (data.size() > 0) {
            ByteArrayEntry entry = new ByteArrayEntry("schedule.json", "data", JsonParser.writeJsonBytes(data));
            entries.add(entry);
        }
        LOG.info("Schedule Converter executed");
        return entries;
    }
}
