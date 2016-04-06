package coza.opencollab.synthesis.service.creator.impl;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.junit.Test;

/**
 *
 * @author OpenCollab
 */
public class ZipFileReaderTest {
    private static final File zip = new File("src/test/tools/my.tool-101.zip");
    
    public ZipFileReaderTest() {
    }
    
    @Test
    public void readZip() throws IOException{
        ZipFile zipFile = new ZipFile(zip);
        Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
        while(zipEntries.hasMoreElements()){
            print(zipEntries.nextElement());
        }
        zipFile.close();
    }

    private void print(ZipEntry entry) {
//        print("Comment", entry.getComment());
        print("CSize", entry.getCompressedSize());
//        print("Crc", entry.getCrc());
//        print("Extra", entry.getExtra());
//        print("Method", entry.getMethod());
        print("Name", entry.getName());
        print("Size", entry.getSize());
        print("Time", entry.getTime());
    }
    
    private void print(String name, long value){
        print(name, String.valueOf(value));
    }
    
    private void print(String name, String value){
        System.out.println(name + ": " + value);
    }
}