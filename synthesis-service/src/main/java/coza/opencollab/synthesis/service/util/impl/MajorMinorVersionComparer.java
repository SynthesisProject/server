package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.util.VersionComparer;
import java.util.regex.Pattern;

/**
 * A version comparer for versions that consist of major and minor versions and on...
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class MajorMinorVersionComparer implements VersionComparer{
    /**
     * The version pattern used.
     */
    private static final String VERSION_PATTERN = "^([0-9]+\\.)+[0-9]+$";
    /**
     * The compiled pattern instance.
     */
    private static final Pattern PATTERN = Pattern.compile(VERSION_PATTERN);

    /** {@inheritDoc} */
    @Override
    public String getVersionPattern() {
        return VERSION_PATTERN;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isValidVersion(String version) {
        return PATTERN.matcher(version).matches();
    }

    /** {@inheritDoc} */
    @Override
    public int compare(String versionA, String versionB) {
        String[] a = versionA.split("\\.");
        String[] b = versionB.split("\\.");
        //step through the equal versions
        int i=0;
        while((i < a.length) && (i < b.length)
                && a[i].equals(b[i])) {
            i++;
        }
        if (i < a.length && i < b.length) {
            int diff = Integer.valueOf(a[i]).compareTo(Integer.valueOf(b[i]));
            return diff < 0? -1:(diff == 0? 0:1);
        }

        return a.length < b.length? -1:(a.length==b.length? 0:1);
    }
}
