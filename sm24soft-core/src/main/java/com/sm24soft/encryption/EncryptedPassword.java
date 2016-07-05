package com.sm24soft.encryption;

import com.sm24soft.common.crypto.SHA512Encrypt;

public class EncryptedPassword extends Password {

	private final static SHA512Encrypt encryptor = new SHA512Encrypt();
	private String encryptedPassword;

	private EncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public static EncryptedPassword fromAlreadyEncryptedPassword(String encryptedPassword) {
		return new EncryptedPassword(encryptedPassword);
	}

	public static EncryptedPassword fromPlaintextPassword(String plaintextPassword) {
		return new EncryptedPassword(encryptor.encrypt(plaintextPassword));
	}

	@Override
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	
}
