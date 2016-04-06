package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.util.VersionComparer;
import java.util.regex.Pattern;

/**
 * This is a version comparer for straight numbers.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class NumberVersionComparer implements VersionComparer{
    /**
     * The version pattern used.
     */
    private String versionPattern = "^[0-9]+$";
    /**
     * The compiled pattern instance.
     */
    private Pattern pattern = Pattern.compile(versionPattern);
    
    /**
     * The version pattern used.
     *
     * @param versionPattern a {@link java.lang.String} object.
     */
    public void setVersionPattern(String versionPattern){
        this.versionPattern = versionPattern;
        pattern = Pattern.compile(versionPattern);
    }

    /** {@inheritDoc} */
    @Override
    public String getVersionPattern() {
        return versionPattern;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isValidVersion(String version) {
        return pattern.matcher(version).matches();
    }

    /** {@inheritDoc} */
    @Override
    public int compare(String versionA, String versionB) {
        return Long.valueOf(versionA).compareTo(Long.valueOf(versionB));
    }
}
