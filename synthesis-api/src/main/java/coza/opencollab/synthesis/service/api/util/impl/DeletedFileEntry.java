package coza.opencollab.synthesis.service.api.util.impl;

import coza.opencollab.synthesis.service.api.Defaults;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import java.io.File;
import java.io.FileFilter;

/**
 * This represents a file that was deleted
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class DeletedFileEntry extends StorageEntry {
    private static final String DELETE_MARKER = "DEL_";

    /**
     * Return a file filter that filter for the marker files.
     *
     * @return a {@link java.io.FileFilter} object.
     */
    public static FileFilter getFileFilter() {
        return new FileFilter(){

            /**
             * Accept only files or directories that are markers for deleted content.
             */
            @Override
            public boolean accept(File file) {
                return file.getName().startsWith(DELETE_MARKER);
            }
        };
    }

    /**
     * Returns the absolute file of the file/directory that was deleted.
     *
     * @param deletedFile a {@link java.io.File} object.
     * @return a {@link java.io.File} object.
     */
    public static File getRealFile(File deletedFile) {
        return new File(deletedFile.getParentFile(), deletedFile.getName().substring(DELETE_MARKER.length()));
    }
    
    /**
     * Constructor setting the deleted file or directory name.
     *
     * @param name a {@link java.lang.String} object.
     * @param directory a {@link java.lang.String} object.
     * @param isDirectory a boolean.
     */
    public DeletedFileEntry(String name, String directory, boolean isDirectory){
        super(DELETE_MARKER + name, directory, isDirectory);
    }
    
    /**
     * Always return null.
     * {@inheritDoc}
     */
    @Override
    public byte[] getContents(){
        return isDirectory()?null:"".getBytes(Defaults.UTF8);
    }
}
