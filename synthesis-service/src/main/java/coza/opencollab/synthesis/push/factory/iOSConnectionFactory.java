package coza.opencollab.synthesis.push.factory;

import org.apache.commons.pool.PoolableObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import javax.net.ssl.*;
import java.security.KeyStore;

/**
 * Factory that creates a pool of connection to the Apple Push Notifications Service (APNS)
 * This Factory is use by the <code>iOSSendService</code> and is injected using spring configuration.
 * 
 * The configuration for this factory is injected via spring.
 * 
  * Available spring properties are:<br>
 * <table style="border-collapse:collapse; border: 1px solid;" border="1" cellpadding="3px">
 *  <caption>Configuration</caption>
 * 	<tr><th>Property</th><th>Purpose</th></tr>
 *  <tr><td><code>push.apple.host</code></td><td>APNS host name</td></tr>
 * 	<tr><td><code>push.apple.key.file</code></td><td>Path to the certificate for APNS</td></tr>
 * 	<tr><td><code>push.apple.key.passphrase</code></td><td>Password for the certificate</td></tr>
 * 	<tr><td><code>push.apple.port</code></td><td>Port connect to on the APNS</td></tr>
 * </table>
 */
public class iOSConnectionFactory implements PoolableObjectFactory<SSLSocket> {

	/**  APN host name. */
	@Value("${push.apple.host}")
	private String host = null;

	/**  APN host port. */
	@Value("${push.apple.port}")
	private int port;
	
	/**  Path to the cert for APN. */
	@Value("${push.apple.key.file}")
	private Resource certPath;

	/**  Password for the APN certificate. */
	@Value("${push.apple.key.passphrase}")
	private String certPassword;

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
	 */
	@Override
	public SSLSocket makeObject() throws Exception {
		SSLSocket socket = null;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(certPath.getInputStream(), certPassword.toCharArray());
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("sunx509");
		keyManagerFactory.init(keyStore, certPassword.toCharArray());
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("sunx509");
		trustManagerFactory.init(keyStore);
		SSLContext sslCtx = SSLContext.getInstance("TLS");
		sslCtx.init(keyManagerFactory.getKeyManagers(), null, null);
		SSLSocketFactory sslSocketFactory = sslCtx.getSocketFactory();
		socket = (SSLSocket)sslSocketFactory.createSocket(host, port);
		socket.startHandshake();
		return socket;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	@Override
	public void destroyObject(SSLSocket obj) throws Exception {
		if (obj == null){
			/* If an exception ocurred during the creation of an object
			 * we will receive an null object to destroy */
			return;
		}
		try{
			obj.close();
		}
		catch (Exception e) {
			// Don't worry - just try and close
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.Object)
	 */
	@Override
	public boolean validateObject(SSLSocket obj) {
		if (obj == null){
			return false; // null is obviously not valid
		}
		return obj.isConnected() && !obj.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#activateObject(java.lang.Object)
	 */
	@Override
	public void activateObject(SSLSocket obj) throws Exception {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#passivateObject(java.lang.Object)
	 */
	@Override
	public void passivateObject(SSLSocket obj) throws Exception {
		// Do nothing
	}
}
