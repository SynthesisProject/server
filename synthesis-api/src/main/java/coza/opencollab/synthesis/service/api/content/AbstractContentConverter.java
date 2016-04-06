package coza.opencollab.synthesis.service.api.content;

import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.content.ContentConverter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Just a abstract implementation of the content converter
 * so that we can have default implementations.
 *
 * @author OpenCollab
 * @param <T>
 * @param <U>
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class AbstractContentConverter<T, U> implements ContentConverter<T, U>{

    /** {@inheritDoc} */
    @Override
    public List<StorageEntry> convertUserData(String moduleId, U data) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasExtraResources(String moduleId) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, File> getExtraResources(String moduleId) {
        return null;
    }
}
