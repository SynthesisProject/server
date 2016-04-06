package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.Defaults;
import static coza.opencollab.synthesis.service.api.ErrorCodes.FILE_MANIPULATION;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.StorageFileHandler;
import coza.opencollab.synthesis.service.api.util.StorageFileReader;
import coza.opencollab.synthesis.service.api.util.StorageFileWriter;
import coza.opencollab.synthesis.service.api.util.StorageMemoryWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

/**
 * A compressed file handler for zip files.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class ZipFileHandler implements StorageFileHandler{
    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(ZipFileHandler.class);
    
    /** {@inheritDoc} */
    @Override
    public String getExtention(){
        return "zip";
    }
    
    /** {@inheritDoc} */
    @Override
    public String getMimeType(){
        return "application/zip";
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean canHandle(File source) {
        if(source == null || !source.exists() || source.isDirectory()){
            return false;
        }
        String name = source.getName();
        return getExtention().equals(name.substring(name.lastIndexOf('.')+1));
    }

    /** {@inheritDoc} */
    @Override
    public String getDestinationName(String baseName) {
        return baseName + "." + getExtention();
    }

    /** {@inheritDoc} */
    @Override
    public String getFileContents(File root, String fileName) throws FileNotFoundException, IOException{
        ZipFile zip = null;
        try{
            zip = new ZipFile(root);
            ZipEntry about = zip.getEntry(fileName);
            return FileCopyUtils.copyToString(new InputStreamReader(zip.getInputStream(about), Defaults.UTF8));
        }catch(NullPointerException e){
            //this is thrown in zip.getInputStream(about)
            throw new FileNotFoundException("The 'about.json' file does not exist.");
        }finally{
            try{
                if(zip != null){
                    zip.close();
                }
            }catch(Exception e){
                LOG.warn("Could not close zip file.", e);
            }
        }
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
        FileOutputStream fos = null;
        CZipOutputStream zos = null;
        try{
            fos = new FileOutputStream(destination);
            zos = new CZipOutputStream(fos);
            writeZip(zos, source, source.getAbsolutePath().length() + 1);
        }finally{
            try{
                if(zos != null){
                    zos.trueClose();
                }
            }catch(Exception e){
                LOG.warn("Could not close zip output stream.", e);
            }
            try{
                if(fos != null){
                    fos.close();
                }
            }catch(Exception e){
                LOG.warn("Could not close file output stream.", e);
            }
        }
    }
    
    /**
     * Write the source files to a zip stream.
     */
    private void writeZip(ZipOutputStream zos, File source, int pathLenght) throws IOException{
        for(File f: source.listFiles()){
            if(f.isFile()){
                String zipFileName = f.getAbsolutePath().substring(pathLenght);
                zos.putNextEntry(new ZipEntry(zipFileName));
                FileCopyUtils.copy(FileCopyUtils.copyToByteArray(f), zos);
            }else{
                writeZip(zos, f, pathLenght);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void writeToDirectory(File source, File destination) throws IOException{
        ZipFile zip = null;
        try{
            zip = new ZipFile(source);
            Enumeration<? extends ZipEntry> e = zip.entries();
            while(e.hasMoreElements()){
                ZipEntry entry = e.nextElement();
                File entryFile = new File(destination, entry.getName());
                if(entry.isDirectory()){
                    entryFile.mkdirs();
                    continue;
                }
                entryFile.getParentFile().mkdirs();
                entryFile.createNewFile();
                FileCopyUtils.copy(FileCopyUtils.copyToByteArray(zip.getInputStream(entry)), entryFile);
            }
        }finally{
            try{
                if(zip != null){
                    zip.close();
                }
            }catch(Exception e){
                LOG.warn("Could not close zip file.", e);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public StorageFileReader getStorageFileReader(File source) {
        return new ZipFileReader(source);
    }

    /** {@inheritDoc} */
    @Override
    public StorageFileWriter getStorageFileWriter(File destination){
        return new ZipFileWriter(destination);
    }

    /** {@inheritDoc} */
    @Override
    public StorageMemoryWriter getMemoryWriter(){
        return new ZipMemoryWriter();
    }
    
    /**
     * Helper to get the name of a entry.
     */
    private String getEntryName(ZipEntry zipEntry){
        String name = zipEntry.getName();
        if(zipEntry.isDirectory() && name.endsWith("/")){
            //the directories return a name ending on a '/'.
            name = name.substring(0, zipEntry.getName().length()-1);
        }
        int index = name.lastIndexOf('/');
        if(index < 0){
            return name;
        }else{
            return name.substring(index + 1);
        }
    }
    
    /**
     * Helper to get the directory of a entry.
     */
    private String getEntryDir(ZipEntry zipEntry){
        String name = zipEntry.getName();
        if(zipEntry.isDirectory() && name.endsWith("/")){
            //the directories return a name ending on a '/'.
            name = name.substring(0, zipEntry.getName().length()-1);
        }
        int index = name.lastIndexOf('/');
        if(index < 0){
            return "";
        }else{
            return name.substring(0, index);
        }
    }
    
    // Inne Classes -----------------------------------------------------------------------------
    /**
     * The StorageMemoryWriter for zip.
     */
    private static class ZipMemoryWriter implements StorageMemoryWriter{
        /**
         * The actual content.
         */
        private byte[] content;
    
        /**
         * {@inheritDoc}
         */
        @Override
        public String getMimeType(){
            return "application/zip";
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
            ByteArrayOutputStream bos = null;
            CZipOutputStream zos = null;
            try {
                bos = new ByteArrayOutputStream();
                zos = new CZipOutputStream(bos);
                for(StorageEntry entry: entries){
                    if(!entry.isDirectory()){
                        zos.putNextEntry(new ZipEntry(entry.getRelativePath()));
                        FileCopyUtils.copy(entry.getContents(), zos);
                    }
                }
            } catch (Exception ex) {
                throw new SynthesisException(FILE_MANIPULATION, "Could not write zip memory.", ex);
            } finally {
                try {
                    if (zos != null) {
                        zos.trueClose();
                    }
                } catch (Exception e) {
                    LOG.warn("Could not close zip output stream.", e);
                }
                try {
                    if (bos != null) {
                        bos.close();
                    }
                } catch (Exception e) {
                    LOG.warn("Could not close zip file output stream.", e);
                }
            }
            if(bos != null){
                content = bos.toByteArray();
            }
        }

        /**
         * {@inheritDoc} 
         */
        @Override
        public byte[] getContent() {
            return content;
        }
        
    }
    
    /**
     * The StorageFileWriter for zip.
     */
    private static class ZipFileWriter implements StorageFileWriter{
        /**
         * The root file object
         */
        private File root;
        
        /**
         * Constructor setting the root.
         */
        ZipFileWriter(File root){
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
            FileOutputStream fos = null;
            CZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(root);
                zos = new CZipOutputStream(fos);
                for(StorageEntry entry: entries){
                    if(!entry.isDirectory()){
                        zos.putNextEntry(new ZipEntry(entry.getRelativePath()));
                        FileCopyUtils.copy(entry.getContents(), zos);
                    }
                }
            } catch (Exception ex) {
                throw new SynthesisException(FILE_MANIPULATION, "Could not write zip file (" + root.getAbsolutePath() + ").", ex);
            } finally {
                try {
                    if (zos != null) {
                        zos.trueClose();
                    }
                } catch (Exception e) {
                    LOG.warn("Could not close zip output stream.", e);
                }
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    LOG.warn("Could not close zip file output stream.", e);
                }
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
     * The CodeFileReader for zip files.
     */
    private class ZipFileReader implements StorageFileReader{
        /**
         * The zip file object
         */
        private ZipFile zip;
        
        /**
         * Constructor setting the zip file.
         */
        ZipFileReader(File zip){
            try {
                this.zip = new ZipFile(zip);
            } catch (Exception e) {
                throw new SynthesisException(FILE_MANIPULATION, "Cannot create the zip file (" + zip.getAbsolutePath() + ")", e);
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public List<StorageEntry> getEntries(){
            List<StorageEntry> entries = new ArrayList<StorageEntry>();
            Enumeration<? extends ZipEntry> zipEntries = zip.entries();
            while(zipEntries.hasMoreElements()){
                entries.add(new ZipFileEntry(zip, zipEntries.nextElement()));
            }
            return entries;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void close(){
            try{
                zip.close();
            } catch (Exception e) {
                throw new SynthesisException(FILE_MANIPULATION, "Cannot close the zip file (" + zip.getName() + ")", e);
            }
        }
    }
    /**
     * The zip entries, files and directories.
     */
    private class ZipFileEntry extends StorageEntry{
        /**
         * The zip file object
         */
        private ZipFile zip;
        /**
         * The zip entry this represents.
         */
        private ZipEntry zipEntry;
        
        /**
         * Constructor setting the zip entry.
         */
        ZipFileEntry(ZipFile zip, ZipEntry zipEntry){
            super(getEntryName(zipEntry), getEntryDir(zipEntry), zipEntry.isDirectory());
            this.zip = zip;
            this.zipEntry = zipEntry;
        }
    
        /**
         * {@inheritDoc}
         */
        @Override
        public byte[] getContents(){
            try {
                return FileCopyUtils.copyToByteArray(zip.getInputStream(zipEntry));
            } catch (IOException e) {
                throw new SynthesisException(FILE_MANIPULATION, "Could not read the zip file (" + zip.getName() + ").", e);
            }
        }
    }

    /**
     * A extention of ZipOutputStream to manage the close method
     */
    private static class CZipOutputStream extends ZipOutputStream {

        /**
         * Creates a new CZIP output stream.
         *
         * @param out the actual output stream
         */
        public CZipOutputStream(OutputStream out) {
            super(out);
        }

        /**
         * We don't close the stream here. This is because we use the Spring FileCopyUtil class that calls this method
         * when it should call closeEntry, so that is what we do.
         */
        @Override
        public void close() throws IOException {
            closeEntry();
        }

        /**
         * We call this method to close the stream.
         */
        public void trueClose() throws IOException {
            super.close();
        }
    }
}
