package coza.opencollab.synthesis.service.auth.impl;

import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.auth.AuthenticationService;
import coza.opencollab.synthesis.service.auth.so.Login;
import coza.opencollab.synthesis.service.api.lms.LMSClient;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The default authentication service. Authentication is done against Sakai, registration saved in database.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultAuthenticationService implements AuthenticationService {
    /**
     * The lms client to connect to
     */
    @Autowired
    private LMSClient lmsClient;    
    /**
     * Algorithm to use for encryption
     */
    private String encryptionAlgorithm;
    
    /**
     * Key used for encryption
     */
    private String encryptionKey;
    
    /**
     * Transformation to use for encryption
     */
    private String encryptionTransformation;
    
    /** {@inheritDoc} */
	@Override
	public Login loginWithToken(String username, String authToken) {
		String password = this.decryptPassword(authToken);
		return this.login(username, password);
	}

    /** {@inheritDoc} */
    @Override
    public Login login(String username, String password) {
        //Events: Will be logged by the service.
        Login login = new Login(ServiceCallStatus.SUCCESS);
        //login against the LMS
        String sessionId = lmsClient.login(username, password);
        if(sessionId == null){
            login.setStatus(ServiceCallStatus.ERROR);
            login.setErrorCode(INVALID_CREDENTIALS);
            login.setMessage("Not a valid login.");
        }else{
            login.setSessionId(sessionId);
            login.setUsername(username);
            login.setAuthToken(encryptPassword(password));
        }
        return login;
    }
    
    /** {@inheritDoc} */
    @Override
    public Login logout(Login login){
        if(login != null && login.getSessionId() != null && lmsClient.logout(login.getUsername())){
            login.setSessionId(null);
        }
        return login;
    }
    
	/** {@inheritDoc} */
	@Override
	public String encryptPassword(String password) {
		try{
	        SecretKeySpec skeySpec = new SecretKeySpec(this.encryptionKey.getBytes("UTF-8"),this.encryptionAlgorithm);
	        Cipher cipher = Cipher.getInstance(this.encryptionTransformation);
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        byte[] encrypted = cipher.doFinal(password.getBytes());
	        return Hex.encodeHexString(encrypted);
		}catch(Exception e){
			throw new SynthesisException(INCORRECT_CONFIG, "Exception while trying to encrypt password", e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String decryptPassword(String encryptedPassword) {
		try {
		    SecretKeySpec skeySpec = new SecretKeySpec(this.encryptionKey.getBytes("UTF-8"), this.encryptionAlgorithm);
		    Cipher cipher = Cipher.getInstance(this.encryptionTransformation);
		    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		    byte[] original = cipher.doFinal(Hex.decodeHex(encryptedPassword.toCharArray()));
		    return new String(original);
		} catch (Exception e) {
			throw new SynthesisException(INVALID_CREDENTIALS, "Exception while trying to decrypt password", e);
		}
	}

	/**
	 * <p>Setter for the field <code>encryptionAlgorithm</code>.</p>
	 *
	 * @param encryptionAlgorithm the encryptionAlgorithm to set
	 */
	public void setEncryptionAlgorithm(String encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	/**
	 * <p>Setter for the field <code>encryptionKey</code>.</p>
	 *
	 * @param encryptionKey the encryptionKey to set
	 */
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	/**
	 * <p>Setter for the field <code>encryptionTransformation</code>.</p>
	 *
	 * @param encryptionTransformation the encryptionTransformation to set
	 */
	public void setEncryptionTransformation(String encryptionTransformation) {
		this.encryptionTransformation = encryptionTransformation;
	}

	
}
