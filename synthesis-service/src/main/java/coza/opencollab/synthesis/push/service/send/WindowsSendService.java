package coza.opencollab.synthesis.push.service.send;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;
import coza.opencollab.synthesis.push.service.PushDeviceTupleService;
import coza.opencollab.synthesis.push.service.SendService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An implementation of the SendService for Windows Mobile.
 */
public class WindowsSendService implements SendService {
	
	/**  A reference to this class' logger. */
	private static final Logger LOG = LoggerFactory.getLogger(AndroidSendService.class);

	/** Maximum number of registration IDs that can be sent in a batch. */
	public static final int BATCH_SIZE = 1;

	/**  Semaphore used to limit the max allowed number of active connections. */
	private final Semaphore connectionLimit = new Semaphore(10, true);
	
	/** The push device tuple service. */
	@Autowired
	private PushDeviceTupleService pushDeviceTupleService;

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.SendService#sendPush(java.util.List)
	 */
	public void sendPush(List<PushDeviceTuple> tuples) {
		if (tuples.size() > 1) {
			throw new IllegalArgumentException("You are attempting to send to more devices in bulk than allowed");
		}
		if (tuples.size() == 0) {
			LOG.info("No devices in tuple list, skipping..");
			return;
		}
		executePush(tuples);
	}
	/**
	 * Attempts to send a push notification to the specified registration IDs.
	 * If the connection limit is currently reached, the call to this method will cause the 
	 * current thread to block until a connection is available.
	 *
	 * @param tuples the tuples
	 */
	private void executePush(List<PushDeviceTuple> tuples) {
		if (tuples.size() > 1) {
			LOG.warn("Batch size bigger than 1! Discarding additional messages!");
		}
		PushDeviceTuple tuple = (PushDeviceTuple)tuples.get(0);
		HttpsURLConnection conn = null;
		try {
			this.connectionLimit.acquire();
			conn = createConnection(tuple.getDevice(), tuple.getPush());
			String request = buildXMLMessage(tuple.getPush());
			String responseData = sendToMS(conn, request);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Response from GCM : " + responseData);
			}
			handleResult(tuple, true);
		}
		catch (Exception e){
			LOG.error("Exception while trying to send notification", e);
			handleResult(tuple, false);
		}
		finally {
			this.connectionLimit.release();
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * Creates a connection to the server.
	 *
	 * @return The connection to the erver
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private HttpsURLConnection createConnection(Device device, Push push) throws IOException {
		HttpsURLConnection conn = (HttpsURLConnection)new URL(device.getRegId()).openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("X-MessageID", push.getPushId().toString());
		conn.setRequestProperty("X-WindowsPhone-Target", "toast");
		conn.setRequestProperty("X-NotificationClass", "2");

		return conn;
	}

	/**
	 * Builds a json formatted message string using the push message and device specified.
	 *
	 * @param push The push to build xml for
	 * @return the XML text
	 */
	private static String buildXMLMessage(Push push) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element root = doc.createElementNS("WPNotification", "Notification");
		root.setPrefix("wp");
		doc.appendChild(root);

		Element tile = doc.createElementNS("WPNotification", "Toast");
		tile.setPrefix("wp");

		Element text1 = doc.createElementNS("WPNotification", "Text1");
		text1.setPrefix("wp");
		text1.setTextContent(push.getTitle());
		tile.appendChild(text1);

		Element text2 = doc.createElementNS("WPNotification", "Text2");
		text2.setPrefix("wp");
		text2.setTextContent(push.getMessage());
		tile.appendChild(text2);

		root.appendChild(tile);

		return getStringFromDocument(doc);
	}

	/**
	 * Create an XML string from a Java XML document.
	 * @param doc The document to parse to string.
	 * @return String of the document.
	 */
	public static String getStringFromDocument(Document doc){
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		}
		catch (TransformerException ex) {
			LOG.warn("Exception while trying to convert xml to text", ex);
		}
		return null;
	}

	/**
	 * Send the XML data to MPNS over the connection
	 * @param conn Connection to use
	 * @param data XML data string to send
	 * @return Response from the service
	 * @throws IOException Exception when something goes wrong
	 */
	private static String sendToMS(HttpsURLConnection conn, String data) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Sending Data");
			LOG.debug(data);
		}
		byte[] dataBytes = data.getBytes();
		conn.setFixedLengthStreamingMode(dataBytes.length);
		OutputStream out = null;
		String response = null;
		try {
			out = conn.getOutputStream();
			out.write(dataBytes);
			out.flush();
			response = readResponse(conn);
		}
		catch (IOException e) {
			LOG.warn("Exception while trying to write data to MPNS", e);
			throw e;
		}
		finally {
			IOUtils.closeQuietly(out);
			conn.disconnect();
		}
		return response;
	}

	/**
	 * Reads the response from the connection.
	 * @param conn Connection to read the response from
	 * @return The String received.
	 * @throws IOException Exception when something goes wrong
	 */
	private static String readResponse(HttpsURLConnection conn) throws IOException {
		return IOUtils.toString(conn.getInputStream());
	}

	/**
	 * Handle the result of attempting to send the push notification
	 * @param tuple The tuple that was attempted to be sent
	 * @param success
	 */
	private void handleResult(PushDeviceTuple tuple, boolean success) {
		if (success) {
			tuple.setStatus(1);
			tuple = this.pushDeviceTupleService.saveTuple(tuple);
		}
		else {
			int retries = tuple.getRetries();
			if (retries >= 3) {
				LOG.warn("Max number of retries reached for a message, marking as failed");
				tuple.setStatus(3);
				tuple = this.pushDeviceTupleService.saveTuple(tuple);
			}
			else {
				retries++;
				tuple.setStatus(2);
				tuple.setRetries(retries);
				tuple.setNextAttempt(new Timestamp(System.currentTimeMillis() + retries * 60000));
				tuple = this.pushDeviceTupleService.saveTuple(tuple);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.SendService#getBulkSize()
	 */
	public int getBulkSize() {
		return 1;
	}
}
