package coza.opencollab.synthesis.service.util.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

/**
 * Any zip file related utils
 *
 * @author OpenCollab
 * @since 1.0.1
 * @version 1.0.1
 */
public class ZipUtil {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(ZipFileHandler.class);

    /**
     * Unzip the file and return the binary
     *
     * @param zipData the byte array of the zip file
     * @return String
     * @throws java.io.IOException if any.
     */
    public static Map getUnZippedBinary(byte[] zipData) throws IOException {
        try {
            Map fileDetail = new HashMap<String, String>();
            ZipInputStream zipContent = new ZipInputStream(new ByteArrayInputStream(zipData));
            ZipEntry entry;
            while ((entry = zipContent.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    fileDetail.put("name", entry.getName());                
                    fileDetail.put("data", FileCopyUtils.copyToByteArray(zipContent));                    
                    return fileDetail;
                }
            }
            return null;
        } catch (IOException ex) {
            LOG.error("Error extracting zip content to String", ex);
            throw ex;
        }
    }

}
