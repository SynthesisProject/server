package coza.opencollab.synthesis.util;



import coza.opencollab.synthesis.util.JsonParser;
import static coza.opencollab.synthesis.util.JsonParser.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test for the json parser
 * 
 * @author OpenCollab
 * @since 1.0.0
 */
public class JsonParserTest {
    
    public JsonParserTest() {}
    
    @Test
    public void parseJsonA() {
        SimplePojo pojo = new SimplePojo();
        pojo.setValueString("Hello");
        pojo.setValueInt(5);
        pojo.setValueDate(new Date());
        pojo.setValueBool(true);
        String body = JsonParser.writeJson(pojo);
        System.out.println(body);
//        String body = "{\"valueString\":\"Hello\",\"valueInt\":5,\"valueDate\":\"2013-10-04T13:08Z\",\"valueBool\":true}";

        pojo = parseJson(body, SimplePojo.class);
        assertEquals("Hello", pojo.getValueString());
        assertEquals(5, pojo.getValueInt());
        assertTrue(pojo.isValueBool());
    }
    
    @Test
    public void parseJsonB() {
        String body = "{\"password\":\"123\",\"deviceId\":\"987\"}";
        Map<String, ?> map = parseJsonToMap(body);
        assertEquals("123", map.get("password"));
        assertEquals("987", map.get("deviceId"));
    }
    
    @Test
    public void parseJsonC() {
        String body = "{\"password\":\"123\",\"deviceId\":\"987\"}";
        String value = parseJson(body, "notListed", "Great");
        assertEquals("Great", value);
    }
    
    @Test
    public void parseJsonD() {
        String body = "{\"password\":\"123\",\"deviceId\":\"987\"}";
        String value = parseJson(body, "deviceId");
        assertEquals("987", value);
    }
    
    @Test
    public void writeJson(){
        SimplePojo pojo = new SimplePojo();
        pojo.setValueString("Hello");
        pojo.setValueInt(5);
        Calendar cal = Calendar.getInstance();
        //have to clear seconds and millies since iso 8601 has none
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        pojo.setValueDate(date);
        pojo.setValueBool(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pojo", pojo);
        map.put("int", 7);
        String json = JsonParser.writeJson(map);
        map = parseJson(json, Map.class);
        assertEquals(2, map.size());
        assertEquals(pojo, JsonParser.parseJson(JsonParser.writeJson(map.get("pojo")), SimplePojo.class));
        assertEquals(7, map.get("int"));
    }
    
    @Test
    public void testList(){
        Map<String, List> map = new HashMap<String, List>();
        List<SimplePojo> list = new ArrayList<SimplePojo>();
        SimplePojo so = new SimplePojo();
        for(int i = 64; i < 75; i++){
            so.setValueInt(i);
            so.setValueString(String.valueOf(Character.forDigit(i, Character.MAX_RADIX)));
            list.add(so);
        }
        map.put("root", list);
        String json = JsonParser.writeJson(list);
        System.out.println("List: " + json);
        System.out.println(parseJsonToList(json));
        json = JsonParser.writeJson(map);
        System.out.println("Map: " + json);
        System.out.println(parseJsonToMap(json));
    }
    
    
}