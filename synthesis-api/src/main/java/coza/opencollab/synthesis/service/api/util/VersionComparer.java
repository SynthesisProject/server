package coza.opencollab.synthesis.service.api.util;

/**
 * A api for comparing versions.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface VersionComparer {
    /**
     * Return the pattern that the version must confirm too.
     *
     * @return A pattern that the version must follow.
     */
    public String getVersionPattern();
    /**
     * Checks if the version is valid for this comparer.
     *
     * @param version The version to test.
     * @return true of the version is valid, otherwise false.
     */
    public boolean isValidVersion(String version);
    /**
     * Compares the versions.
     *
     * @param versionA a {@link java.lang.String} object.
     * @param versionB a {@link java.lang.String} object.
     * @return -1 if versionA is less then versionB, 0 if they are equal
     * and +1 if versionA is greater then versionB.
     */
    public int compare(String versionA, String versionB);
}
