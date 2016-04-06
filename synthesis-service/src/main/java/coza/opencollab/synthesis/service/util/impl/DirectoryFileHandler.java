package coza.opencollab.synthesis.service.util.impl;

import static coza.opencollab.synthesis.service.api.ErrorCodes.FILE_MANIPULATION;
import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.StorageFileHandler;
import coza.opencollab.synthesis.service.api.util.StorageFileReader;
import coza.opencollab.synthesis.service.api.util.StorageFileWriter;
import coza.opencollab.synthesis.service.api.util.StorageMemoryWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

/**
 * A file handler for normal directories.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DirectoryFileHandler implements StorageFileHandler{

    /** {@inheritDoc} */
    @Override
    public String getExtention() {
        return "";
    }
    
    /** {@inheritDoc} */
    @Override
    public String getMimeType(){
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean canHandle(File source) {
        return source != null && source.isDirectory();
    }

    /** {@inheritDoc} */
    @Override
    public String getDestinationName(String baseName) {
        return baseName;
    }

    /** {@inheritDoc} */
    @Override
    public String getFileContents(File root, String fileName) throws FileNotFoundException, IOException {
        File about = new File(root, fileName);
        if(!about.exists()){
            throw new FileNotFoundException("The 'about.json' file does not exist.");
        }
        return FileCopyUtils.copyToString(new FileReader(about));
    }
    
    /** {@inheritDoc} */
    @Override
    public String getFileContents(File root, String... fileNames) throws FileNotFoundException, IOException{
        FileNotFoundException ex = null;
        for(String fileName: fileNames){
            try{
                return getFileContents(root, fileName);
            }catch(FileNotFoundException e){
                ex = e;
            }
        }
        throw ex;
    }

    /** {@inheritDoc} */
    @Override
    public void writeFromDirectory(File source, File destination) throws IOException {
        FileSystemUtils.copyRecursively(source, destination);
    }

    /** {@inheritDoc} */
    @Override
    public void writeToDirectory(File source, File destination) throws IOException {
        FileSystemUtils.copyRecursively(source, destination);
    }

    /** {@inheritDoc} */
    @Override
    public StorageFileReader getStorageFileReader(File source) {
        return new DirectoryFileReader(source);
    }

    /** {@inheritDoc} */
    @Override
    public StorageFileWriter getStorageFileWriter(File destination){
        return new DirectoryFileWriter(destination);
    }

    /** {@inheritDoc} */
    @Override
    public StorageMemoryWriter getMemoryWriter(){
        throw new UnsupportedOperationException("This handler cannot write to memory.");
    }

    /**
     * Helper method for the entry constructor.
     */
    private String getRelDir(File root, File file) {
        return file.getAbsolutePath().substring(
                root.getAbsolutePath().length(),
                file.getAbsolutePath().length() - file.getName().length());
    }
    
    // Inne Classes -----------------------------------------------------------------------------
    
    /**
     * The StorageFileWriter for normal directories.
     */
    private static class DirectoryFileWriter implements StorageFileWriter{
        /**
         * The root file object
         */
        private File root;
        
        /**
         * Constructor setting the root.
         */
        DirectoryFileWriter(File root){
            this.root = root;
        }

        /**
         * {@inheritDoc} 
         */
        @Override
        public void write(StorageEntry entry) {
            write(Collections.singletonList(entry));
        }

        /**
         * {@inheritDoc} 
         */
        @Override
        public void write(List<StorageEntry> entries) {
            try { 
                for(StorageEntry entry: entries){
                    File file = new File(root, entry.getRelativePath());
                    if(entry.isDirectory()){
                        file.mkdirs();
                    }else{
                        if(file.exists()){
                            file.delete();
                        }else{
                            file.getParentFile().mkdirs();
                        }
                        FileCopyUtils.copy(entry.getContents(), file);
                    }
                }
            } catch (IOException e) {
                throw new SynthesisException(FILE_MANIPULATION, "Could not write file.", e);
            }
        }

        /**
         * {@inheritDoc} 
         */
        @Override
        public void close() {
            root = null;
        }
    }
    
    /**
     * The StorageFileReader for normal directories.
     */
    private class DirectoryFileReader implements StorageFileReader{
        /**
         * The root file object
         */
        private File root;
        
        /**
         * Constructor setting the root.
         */
        DirectoryFileReader(File root){
            this.root = root;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public List<StorageEntry> getEntries(){
            List<StorageEntry> entries = new ArrayList<StorageEntry>();
            entries.addAll(getEntries(root));
            return entries;
        }
        
        /**
         * Recursive method to return all StorageFileEntry's in a directory.
         */
        private List<StorageEntry> getEntries(File directory){
            List<StorageEntry> entries = new ArrayList<StorageEntry>();
            File[] files = directory.listFiles();
            for(File f: files){
                if(f.isDirectory()){
                    entries.addAll(getEntries(f));
                }else{
                    entries.add(new FileStorageFileEntry(root, f));
                }
            }
            return entries;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void close(){
            root = null;
        }
    }
    /**
     * The directory entries, files and directories.
     */
    private class FileStorageFileEntry extends StorageEntry{
        /**
         * The file this represents.
         */
        private File file;
        
        /**
         * Constructor setting the file
         */
        FileStorageFileEntry(File root, File file){
            super(file.getName(), getRelDir(root, file), file.isDirectory());
            this.file = file;
        }
    
        /**
         * {@inheritDoc}
         */
        @Override
        public byte[] getContents(){
            try {
                return FileCopyUtils.copyToByteArray(file);
            } catch (IOException e) {
                throw new SynthesisException(FILE_MANIPULATION, "Could not read the file (" + file.getAbsolutePath() + ").", e);
            }
        }
    }
}
