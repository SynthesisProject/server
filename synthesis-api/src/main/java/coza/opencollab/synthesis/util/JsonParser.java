package coza.opencollab.synthesis.util;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.ErrorCodes;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

/**
 * A json parser utility class.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public final class JsonParser{

    /**
     * The logger.
     */
    private static final Logger log = Logger.getLogger(JsonParser.class);
    /**
     * The JSON mapper
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    /**
     * The JSON writer
     */
//    private static final ObjectWriter JSON_WRITER = JSON_MAPPER.writer();
    /**
     * The JSON pretty writer
     */
//    private static final ObjectWriter JSON_WRITER = JSON_MAPPER.writerWithDefaultPrettyPrinter();
    /**
     * Time zone for iso 8601
     */
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    /**
     * Date format for iso 8601
     */
    private static final DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    static{
        ISO8601_DATE_FORMAT.setTimeZone(TIME_ZONE);
        JSON_MAPPER.setDateFormat(ISO8601_DATE_FORMAT);
    }
    /**
     * The JSON iso 8601 writer
     * TODO fix concurrency
     */
//    private static final ObjectWriter JSON_WRITER = JSON_MAPPER.writer(iso8601DateFormat);
    /**
     * The JSON pretty iso 8601 writer
     */
    private static final ObjectWriter JSON_WRITER = JSON_MAPPER.writer(ISO8601_DATE_FORMAT).withDefaultPrettyPrinter();
    
    /**
     * Hiding the constructor since this is a utility class
     */
    private JsonParser(){}

    /**
     * Parse the json string and return the value for the key.
     *
     * @param json The json body
     * @param key The name of the value to return
     * @param defualt The default value to return if the value is null.
     * @return The value.
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static String parseJson(String json, String key, String defualt) throws SynthesisException {
        String value = parseJson(json, key);
        if (value == null) {
            value = defualt;
        }
        return value;
    }

    /**
     * Parse the json string and return the value for the key.
     *
     * @param json The json body
     * @param key The name of the value to return
     * @return The value.
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static String parseJson(String json, String key) throws SynthesisException {
        return (String) parseJsonToMap(json).get(key);
    }

    /**
     * Parse the json string and return all the values in a map.
     *
     * @param json The json body
     * @return The map containing the value names in the keys with there respective values.
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static Map<String, ?> parseJsonToMap(String json) throws SynthesisException {
        return parseJson(json, Map.class);
    }

    /**
     * Parse the json string and return all the values in a list.
     *
     * @param json The json body
     * @return The list containing the values.
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static List<?> parseJsonToList(String json) throws SynthesisException {
        return parseJson(json, List.class);
    }

    /**
     * Parse the json string and return all the values in a map.
     *
     * @param json The json body
     * @param clazz The class type to return
     * @return The class instance containing the values from the json.
     * @param <T> a T object.
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static <T> T parseJson(String json, Class<T> clazz) throws SynthesisException {
        String errMsg;
        Exception e;
        try {
            return JSON_MAPPER.readValue(json, clazz);
        } catch (UnsupportedEncodingException ex) {
            errMsg = "Character encoding is not supported.";
            e = ex;
        } catch (JsonMappingException ex) {
            errMsg = "Invalid content mapping.";
            e = ex;
        } catch (JsonParseException ex) {
            errMsg = "Could not parse the content.";
            e = ex;
        } catch (IOException ex) {
            errMsg = "Could not write the object.";
            e = ex;
        }
        log.debug(errMsg, e);
        throw new SynthesisException(ErrorCodes.JSON_READ, errMsg, json, e);
    }
    
    /**
     * Merge two json packages into one class.
     * <b>Note:</b> This does not do a deep merge!
     *
     * @param jsonBase The base json. This forms the base of the object.
     * @param jsonNew The new json to add to the object, this will override the data from jsonBase.
     * @param clazz The class to convert too.
     * @return The new merged object.
     * @param <T> a T object.
     */
    public static <T> T merge(String jsonBase, String jsonNew, Class<T> clazz){
        T t = parseJson(jsonBase, clazz);
        try {
            ObjectReader updater = JSON_MAPPER.readerForUpdating(t);
            return (T)updater.readValue(jsonNew);
        } catch (IOException e) {
            log.debug("Could not write the object.", e);
            throw new SynthesisException(ErrorCodes.JSON_READ, "Could not write the object.", jsonNew, e);
        }
    }
    
    /**
     * Merge two json packages into one json.
     *
     * @param jsonBase The base json. This forms the base of the object.
     * @param jsonNew The new json to add to the object, this will override the data from jsonBase.
     * @return The new merged json.
     */
    public static String merge(String jsonBase, String jsonNew){
        try {
            JsonNode nodeBase = JSON_MAPPER.readTree(jsonBase);
            JsonNode nodeNew = JSON_MAPPER.readTree(jsonNew);
            JsonNode node = merge(nodeBase, nodeNew);
            return JSON_MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            log.debug("Could not read the object.", e);
            throw new SynthesisException(ErrorCodes.JSON_READ, "Could not write the object.", jsonNew, e);
        } catch (IOException e) {
            log.debug("Could not read the object.", e);
            throw new SynthesisException(ErrorCodes.JSON_READ, "Could not write the object.", jsonNew, e);
        }
    }

    /**
     * Merge the to nodes together.
     * This is the main node method and will check the node types to
     * call the appropiate merge method.
     * 
     * @param baseNode The base node, forms the base and the other node is added to this one, overwriting this values.
     * @param newNode The node that will overwrite values in the base node.
     * @return The merged node.
     */
    private static JsonNode merge(JsonNode baseNode, JsonNode newNode) {
        if(checkNode(baseNode, newNode)){
            return newNode;
        }
        if(checkNode(newNode, baseNode)){
            return baseNode;
        }
        if(baseNode.isValueNode()){
            //always return the new node even if it is a container node then.
            return newNode;
        }
        if(baseNode.isObject() && newNode.isObject()){
            return merge((ObjectNode)baseNode, (ObjectNode)newNode);
        }
        if(baseNode.isArray() && newNode.isArray()){
            //we cannot merge items that we don't understand...aka we don't have a key
            return newNode;
        }
        throw new SynthesisException(ErrorCodes.JSON_READ, "Incompatable types for merge. Below is the baseNode and newNode;\n"
                            + baseNode.asText() + "\n" + newNode.asText());
    }
    
    private static boolean checkNode(JsonNode checkThis, JsonNode printThis){
        if(checkThis == null){
            return true;
        }else if("null".equals(checkThis.asText())){
            log.warn("Found a node that has 'null' value. Other node: " + (printThis==null?"It is Null":printThis.asText()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Merge the to nodes together.
     * 
     * @param baseNode The base node, forms the base and the other node is added to this one, overwriting this values.
     * @param newNode The node that will overwrite values in the base node.
     * @return The merged node.
     */
    private static JsonNode merge(ObjectNode baseNode, ObjectNode newNode) {
        Iterator<String> fieldNames = newNode.getFieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode baseField = baseNode.get(fieldName);
            if (baseField != null && baseField.isContainerNode()) {
                baseNode.put(fieldName, merge(baseField, newNode.get(fieldName)));
            } else {
                // Overwrite field
                baseNode.put(fieldName, newNode.get(fieldName));
            }
        }
        return baseNode;
    }
    
    /**
     * Find the difference (What is new in jsonNew) between the two json packages.
     *
     * @param jsonBase The base json.
     * @param jsonNew The new json.
     * @return The difference (whats new) json.
     */
    public static String diff(String jsonBase, String jsonNew){
        try {
            JsonNode nodeBase = JSON_MAPPER.readTree(jsonBase);
            JsonNode nodeNew = JSON_MAPPER.readTree(jsonNew);
            JsonNode node = diff(nodeBase, nodeNew);
            return JSON_MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            log.debug("Could not read the object.", e);
            throw new SynthesisException(ErrorCodes.JSON_READ, "Could not write the object.", jsonNew, e);
        } catch (IOException e) {
            log.debug("Could not read the object.", e);
            throw new SynthesisException(ErrorCodes.JSON_READ, "Could not write the object.", jsonNew, e);
        }
    }

    /**
     * Find the difference (What is new in jsonNew) between the two json nodes.
     * This is the main node method and will check the node types to
     * call the appropiate diff method.
     * 
     * @param baseNode The base node.
     * @param newNode The new node.
     * @return The diff node.
     */
    private static JsonNode diff(JsonNode baseNode, JsonNode newNode) {
        if(newNode == null){
            return null;
        }
        if(baseNode == null){
            return newNode;
        }
        if(baseNode.isValueNode()){
            //always return the new node even if it is a container node then, if different.
            return baseNode.asText().equals(newNode.asText())?null:newNode;
        }
        if(baseNode.isObject() && newNode.isObject()){
            return diff((ObjectNode)baseNode, (ObjectNode)newNode);
        }
        if(baseNode.isArray()&& newNode.isArray()){
            //we cannot diff items that we don't understand...aka we don't have a key
            return newNode;
        }
        throw new SynthesisException(ErrorCodes.JSON_READ, "Incompatable types for diff. Below is the baseNode and newNode;\n"
                            + baseNode.asText() + "\n" + newNode.asText());
    }

    /**
     * Find the difference (What is new in jsonNew) between the two json nodes.
     * 
     * @param baseNode The base node.
     * @param newNode The new node.
     * @return The diff node.
     */
    private static JsonNode diff(ObjectNode baseNode, ObjectNode newNode) {
        Iterator<String> fieldNames = newNode.getFieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode baseField = baseNode.get(fieldName);
            JsonNode node = diff(baseField, newNode.get(fieldName));
            if(node == null){
                newNode.remove(fieldName);
            }else{
                newNode.put(fieldName, node);
            }
        }
        return newNode;
    }

    /**
     * This method write a object to a JSon String.
     *
     * @param obj Any Object
     * @return The Json String representing the object
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static String writeJson(Object obj) throws SynthesisException {
        String errMsg;
        Exception e;
        try {
            return JSON_WRITER.writeValueAsString(obj);
        } catch (JsonMappingException ex) {
            errMsg = "Invalid content mapping.";
            e = ex;
        } catch (JsonGenerationException ex) {
            errMsg = "Could not write content.";
            e = ex;
        } catch (IOException ex) {
            errMsg = "Could not write the object.";
            e = ex;
        }
        log.debug(errMsg, e);
        throw new SynthesisException(ErrorCodes.JSON_WRITE, errMsg, obj, e);
    }

    /**
     * This method write a object to a JSon bytes.
     *
     * @param obj Any Object
     * @return The Json in bytes representing the object
     * @throws coza.opencollab.synthesis.SynthesisException if any.
     */
    public static byte[] writeJsonBytes(Object obj) throws SynthesisException {
        String errMsg;
        Exception e;
        try {
            return JSON_WRITER.writeValueAsBytes(obj);
        } catch (JsonMappingException ex) {
            errMsg = "Invalid content mapping.";
            e = ex;
        } catch (JsonGenerationException ex) {
            errMsg = "Could not write content.";
            e = ex;
        } catch (IOException ex) {
            errMsg = "Could not write the object.";
            e = ex;
        }
        log.debug(errMsg, e);
        throw new SynthesisException(ErrorCodes.JSON_WRITE, errMsg, obj, e);
    }
}
