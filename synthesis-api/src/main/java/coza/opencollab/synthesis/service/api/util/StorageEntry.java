package coza.opencollab.synthesis.service.api.util;

import static coza.opencollab.synthesis.service.api.ErrorCodes.FILE_MANIPULATION;
import coza.opencollab.synthesis.SynthesisException;

/**
 * Represents a single entry, file of directory.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class StorageEntry {

    /**
     * The file/directory name.
     */
    private String name;
    /**
     * The file directory relative to the root.
     */
    private String relativeDirectory;
    /**
     * Whether the deleted file was a directory.
     */
    private boolean isDirectory;
    
    /**
     * Constructor setting the entry properties.
     *
     * @param name The file/directory name.
     * @param relativeDirectory The file directory relative to the root.
     */
    public StorageEntry(String name, String relativeDirectory){
        this(name, relativeDirectory, false);
    }
    
    /**
     * Constructor setting the entry properties.
     *
     * @param name The file/directory name.
     * @param relativeDirectory The file directory relative to the root.
     * @param isDirectory Whether the deleted file was a directory.
     */
    public StorageEntry(String name, String relativeDirectory, boolean isDirectory){
        setName(name);
        setRelativeDirectory(relativeDirectory);
        this.isDirectory = isDirectory;
    }

    /**
     * Returns the name of the file or directory.
     * <p>
     * Note this is only the file or directory name, not the whole path.
     *
     * @return The file or directory name.
     */
    public final String getName(){
        return name;
    }
    
    /**
     * Sets the name of the file or directory. 
     * <p>
     * Note this is only the file or directory name, not the whole path.
     * Any leading or trailing '/' gets removed.
     *
     * @return The file or directory name.
     */
    private void setName(String name){
        this.name = name;
        if(this.name == null){
            throw new SynthesisException(FILE_MANIPULATION, "Entry name cannot be null.");
        }
        if(this.name.startsWith("/")){
            this.name = this.name.substring(1);
        }
        if(this.name.endsWith("/")){
            this.name = this.name.substring(0, this.name.length() -1);
        }
    }

    /**
     * Returns the relative directory (excluding the file/directory name given by
     * <code>getName</code>) from the root file.
     * <p>
     * This does not contain a leading or trailing '/'.
     *
     * @return The relative directory. If this file or directory is in the root the method should return a empty string,
     * never null.
     */
    public final String getRelativeDirectory(){
        return relativeDirectory;
    }
    
    /**
     * Sets the relative directory (excluding the file/directory name given by
     * <code>getName</code>) from the root file. 
     * <p>
     * Any leading or trailing '/' gets removed.
     *
     * @return The relative directory. If this file or directory is in the root the method should return a empty string,
     * never null.
     */
    private void setRelativeDirectory(String relativeDirectory){
        this.relativeDirectory = relativeDirectory;
        if(this.relativeDirectory == null){
            this.relativeDirectory = "";
        }
        if(this.relativeDirectory.startsWith("/")){
            this.relativeDirectory = this.relativeDirectory.substring(1);
        }
        if(this.relativeDirectory.endsWith("/")){
            this.relativeDirectory = this.relativeDirectory.substring(0, this.relativeDirectory.length() -1);
        }
    }

    /**
     * Returns the full relative path (including the name) for this entry.
     * <p>
     * This is the combination of
     * <code>getRelativeDirectory</code> and
     * <code>getName</code>
     *
     * @return a {@link java.lang.String} object.
     */
    public final String getRelativePath() {
        return getRelativeDirectory()
                + (getRelativeDirectory().length() == 0 ? "" : "/")
                + getName();
    }

    /**
     * A check to see it this is a directory
     *
     * @return true if it is a directory, otherwise false.
     */
    public final boolean isDirectory(){
        return isDirectory;
    }

    /**
     * Return the contents of the file.
     *
     * @return The byte array contents.
     */
    public abstract byte[] getContents();

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
        return getRelativeDirectory().hashCode() + getName().hashCode();
    }

    /**
     * Check if the filename and directory is the same.
     * <p>
     * Note this is only the file or directory name relative to the root, not the whole path.
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!StorageEntry.class.isInstance(obj)) {
            return false;
        }
        StorageEntry other = (StorageEntry)obj;
        return getRelativeDirectory().equals(other.getRelativeDirectory())
                && getName().equals(other.getName());
    }
}
