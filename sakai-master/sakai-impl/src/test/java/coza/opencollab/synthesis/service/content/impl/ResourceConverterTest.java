package coza.opencollab.synthesis.service.content.impl;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @author OpenCollab
 */
public class ResourceConverterTest {

    private static final Logger LOG = Logger.getLogger(ResourceConverterTest.class);
    
    @Test
    public void sort(){
        Map<String, Integer> map = new TreeMap<String, Integer>();
        map.put("/group/AFL1501-13-S1-43T/media files/www.youtube.com_wa20111020113257.URL", Integer.SIZE);
        map.put("/group/AFL1501-13-S1-43T/media files/", Integer.SIZE);
        map.put("/group/AFL1501-13-S1-43T/Unit 4/Assignment 07 Table.doc", Integer.SIZE);
        map.put("/group/AFL1501-13-S1-43T/Unit 4/Written AssignRubricAFL-Ass07.pdf", Integer.SIZE);
        map.put("/group/AFL1501-13-S1-43T/Unit 4/DiscForumRubricAFL..pdf", Integer.SIZE);
        map.put("/group/AFL1501-13-S1-43T/Unit 4/8ba54ab7-994c-4770-8dca-6c116ae3cf5c.gif", Integer.SIZE);
        map.put("/group/AFL1501-13-S1-43T/Unit 4/", Integer.SIZE);
        LOG.info(map.toString());
    }

    /**
     * Test of convert method, of class ResourceConverter.
     */
//    @Test
//    public void testConvert() {
//        System.out.println("convert");
//        String xml = "";
//        SakaiResourceHandler instance = new SakaiResourceHandler();
//        int expResult = 1;
//        instance.createDocument(xml);
//        List<Announcement> result = instance.getre();
//        ResourceConverter instance = new ResourceConverter();
//        List expResult = null;
//        List result = instance.convert(data);
//        assertEquals(expResult, result);
//    }
}