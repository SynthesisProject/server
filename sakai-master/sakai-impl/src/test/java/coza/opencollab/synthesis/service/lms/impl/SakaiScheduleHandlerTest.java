package coza.opencollab.synthesis.service.lms.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.junit.Test;

import coza.opencollab.synthesis.service.api.util.impl.DomHelper;
import coza.opencollab.synthesis.service.lms.sakai.impl.SakaiScheduleHandler;
import coza.opencollab.synthesis.shared.Attachment;
import coza.opencollab.synthesis.shared.Schedule;
import org.junit.Ignore;

/**
 *
 * @author OpenCollab
 */
public class SakaiScheduleHandlerTest {
    private static final Logger LOG = Logger.getLogger(SakaiScheduleHandlerTest.class);
    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><list><item><id>3d2104ed-5520-44ec-90c1-94ba5dcaa535</id><creator/><modified_by>bb1ded9a-bb92-48b9-00ea-d6d7becb75af</modified_by><title>Assignment 1 for AFL1501</title><description>&lt;p&gt;&#xd; 	Assignment 1 for AFL1501&lt;/p&gt;&#xd;</description><range>20130208010000000]20130208105500000</range><start>1360285200000</start><end>1360320899999</end><frequency_description/><frequency/><recurrence_interval/><recurrence_count/><recurs_until/><all-day>false</all-day><type>Deadline</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1374603110000</create_date><modified_date>1374589279000</modified_date><attachments/></item><item><id>9b9bdb90-5550-4d4e-b9cc-581d0f06bf8d</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>Opens: test</title><description>This module opens today and closes Fri Aug 30 23:59:00 SAST 2013</description><range>20130803060000000</range><start>1375509600000</start><end>1375509600000</end><frequency_description/><frequency/><recurrence_interval/><recurrence_count/><recurs_until/><all-day>true</all-day><type>Deadline</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1375479104000</create_date><modified_date>1375479105000</modified_date><attachments/></item><item><id>6cecd5e2-44e4-4b38-9acf-a4048b949f66</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>Closes: test</title><description>This module closes today</description><range>20130830215900000</range><start>1377899940000</start><end>1377899940000</end><frequency_description/><frequency/><recurrence_interval/><recurrence_count/><recurs_until/><all-day>true</all-day><type>Deadline</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1375479959000</create_date><modified_date>1375479959000</modified_date><attachments/></item><item><id>90fd1a32-5ffc-4789-a213-d93713baa05b</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>testing 32</title><description>&lt;p&gt;&#xd; 	testing 32&lt;/p&gt;&#xd;</description><range>20130906060000000]20130906110000000</range><start>1378447200000</start><end>1378465199999</end><frequency_description/><frequency/><recurrence_interval/><recurrence_count/><recurs_until/><all-day>false</all-day><type>Activity</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1378380227000</create_date><modified_date>1378380227000</modified_date><attachments/></item><item><id>e58aeb8a-ae13-4043-bc61-e0a9365e9755</id><creator>Opencollab Instructor</creator><modified_by>Opencollab Admin</modified_by><title>Semester test 1</title><description>&lt;p&gt;&#xd; 	Semester test 1&lt;/p&gt;&#xd;</description><range>20130915070000000]20130915080000000</range><start>1379228400000</start><end>1379231999999</end><frequency_description/><frequency/><recurrence_interval/><recurrence_count/><recurs_until/><all-day>false</all-day><type>Exam</type><location>testestest</location><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1377790506000</create_date><modified_date>1378163326000</modified_date><attachments/></item><item><id>f1241fea-b376-4c14-8ed5-c746b21cf4cf</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>Exam One</title><description>&lt;p&gt;&#xd; 	New test for Exam. Check the priority background&lt;/p&gt;&#xd;</description><range>20130918070000000]20130918090000001</range><start>1379487600000</start><end>1379494800000</end><frequency_description>week(s)</frequency_description><frequency>week</frequency><recurrence_interval>1</recurrence_interval><recurrence_count>8</recurrence_count><recurs_until/><all-day>false</all-day><type>Exam</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1379361547000</create_date><modified_date>1379504518000</modified_date><attachments><attachment id=\"/attachment/AFL1501-13-S1-43T/Schedule/0ddd2ab4-8192-466b-9132-04ffcd9bd931/O7EIIrN.jpg\" name=\"O7EIIrN.jpg\" size=\"373581\" type=\"image/jpeg\" url=\"http://sakai.opencollab.co.za/access/content/attachment/AFL1501-13-S1-43T/Schedule/0ddd2ab4-8192-466b-9132-04ffcd9bd931/O7EIIrN.jpg\"/></attachments></item><item><id>0f69e703-c2f0-43ce-9eed-1dd6b3e2228a</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>Weekly ends on</title><description>&lt;p&gt;&#xd; 	Weekly ends on&lt;/p&gt;&#xd;</description><range>20130919000000000]20130919010000000</range><start>1379548800000</start><end>1379552399999</end><frequency_description>week(s)</frequency_description><frequency>week</frequency><recurrence_interval>1</recurrence_interval><recurrence_count/><recurs_until>1384898399999</recurs_until><all-day>false</all-day><type>Multidisciplinary Conference</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1379644902000</create_date><modified_date>1379644902000</modified_date><attachments/></item><item><id>e3908d76-40ca-4a7f-9ae2-6fe98cd1613f</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>Test daily</title><description>&lt;p&gt;&#xd; 	My daily event&lt;/p&gt;&#xd;</description><range>20130919070000000]20130919080000001</range><start>1379574000000</start><end>1379577600000</end><frequency_description>day(s)</frequency_description><frequency>day</frequency><recurrence_interval>1</recurrence_interval><recurrence_count>6</recurrence_count><recurs_until/><all-day>false</all-day><type>Special event</type><location>test locale</location><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1379636546000</create_date><modified_date>1379601768000</modified_date><attachments/></item><item><id>51a850ea-e740-454d-a3e4-987341375181</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>Test rec monthly</title><description>&lt;p&gt;&#xd; 	Test rec monthly&lt;/p&gt;&#xd;</description><range>20130919091500000]20130919101500000</range><start>1379582100000</start><end>1379585699999</end><frequency_description>month(s)</frequency_description><frequency>month</frequency><recurrence_interval>1</recurrence_interval><recurrence_count>4</recurrence_count><recurs_until/><all-day>false</all-day><type>Class section - Lecture</type><location>test loc</location><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1379615693000</create_date><modified_date>1379615693000</modified_date><attachments/></item><item><id>9f90796d-c4b7-48dd-b2db-06c583e231cc</id><creator>Opencollab Admin</creator><modified_by>Opencollab Admin</modified_by><title>MWF</title><description>&lt;p&gt;&#xd; 	MWF&lt;/p&gt;&#xd;</description><range>20130920030000000]20130920040000000</range><start>1379646000000</start><end>1379649599999</end><frequency_description>Mon/Wed/Friday</frequency_description><frequency>MWF</frequency><recurrence_interval>1</recurrence_interval><recurrence_count>10</recurrence_count><recurs_until/><all-day>false</all-day><type>Computer Session</type><location/><site>AFL1501-13-S1-43T</site><from_site>AFL1501-13-S1-43T</from_site><create_date>1379641280000</create_date><modified_date>1379641280000</modified_date><attachments/></item></list>";

    /**
     * Test of parseScheduleItems method, of class SakaiScheduleHandler.
     */
    @Ignore
    @Test
    public void testParseScheduleItems() {
        LOG.info("parseScheduleItems");
        SakaiScheduleHandler instance = new SakaiScheduleHandler();
//        instance.setResourcesHandler(new SakaiResourceHandler(){
//
//            @Override
//            public byte[] getResourceData(String resourceId, String sessionId) {
//                return "hello".getBytes();
//            }
//        });
        Document doc = DomHelper.createDocument(xml);
        String moduleId = "AFL1501-13-S1-43T";
        List<Schedule> result = instance.getScheduleItems(doc, "", moduleId);
        Schedule schedule = (Schedule) result.get(1);
        //if this tests fails either the data has been changed above or the type of the ID field has been.
        LOG.info("Testing creator name");
        assertEquals("Opencollab Admin", schedule.getCreator());
        schedule = (Schedule) result.get(0);
        LOG.info("Testing ID");
        assertEquals("3d2104ed-5520-44ec-90c1-94ba5dcaa535", schedule.getId());
        schedule = (Schedule) result.get(result.size()-1);
        assertEquals("10", schedule.getRecurrenceCount());
        //testing attachment path
        schedule = (Schedule) result.get(5);
        List<Attachment> attachments = schedule.getAttachments();
        LOG.info("path : "+attachments.get(0).getPath());
    }
}