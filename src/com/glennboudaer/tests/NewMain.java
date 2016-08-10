package com.glennboudaer.tests;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class NewMain {
	public static void main(String[] args) {
		try {
			/*
			 * using openssl on a linux machine openssl genrsa -out privkey.pem
			 * 2048 openssl pkcs8 -topk8 -in privkey.pem -inform PEM -nocrypt
			 * -outform DER -out privkey.der openssl rsa -in privkey.pem -out
			 * pubkey.der -pubout -outform DER
			 *
			 */
			NewMain nmain = new NewMain();
			nmain.writeLicense();
			nmain.verifyLicense();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyLicense() throws Exception 
	{
		Signature rsaSignature = Signature.getInstance("SHA1withRSA");
		PublicKey publicKey = null;
		byte[] publicKeyBytes = FileUtils.readFileToByteArray(new File("C:/license/pubkey.der"));
		if (publicKeyBytes != null) {
			X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
			publicKey = publicKeyFactory.generatePublic(publicSpec);
		}
		rsaSignature.initVerify(publicKey);
		rsaSignature.update(FileUtils.readFileToByteArray(new File("C:/license/lic.file")));
		boolean verifies = rsaSignature.verify(FileUtils.readFileToByteArray(new File("C:/license/signature.file")));
		System.out.println(" = " + verifies);
	}

	public void writeLicense() throws Exception {
		Signature rsaSignature = Signature.getInstance("SHA1withRSA");
		PrivateKey privateKey = null;
		byte[] privateKeyBytes = FileUtils.readFileToByteArray(new File("C:/license/privkey.der"));
		if (privateKeyBytes != null) {
			PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
			privateKey = privateKeyFactory.generatePrivate(privateSpec);
		}
		rsaSignature.initSign(privateKey);
		rsaSignature.update(FileUtils.readFileToByteArray(new File("C:/license/lic.file")));
		byte[] signature = rsaSignature.sign();
		FileUtils.writeByteArrayToFile(new File("C:/license/signature.file"), signature);
	}
}