package com.glennboudaer.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Date;

import com.glennboudaer.enums.LicenseType;
import com.glennboudaer.license.EncryptionManager;
import com.glennboudaer.license.LicenseManager;

public class Main {

	public static void main(String[] args) 
	{
		LicenseManager manager = LicenseManager.getLicenseManager();
		
		String key = "000000017f390000259b00000101988fd521007a0001b94b8a00af00d7f53b";
		
		//manager.createLicense("Glenn Boudaer", "glenn.boudaer@hotmail.com", key, LicenseType.TRIAL, new Date(), "1", "/Users/glennboudaer/Documents/");
		
		byte[] dataToSign = key.getBytes();
		
		try 
		{
			EncryptionManager encryption = EncryptionManager.getEncryptionManager();
			
			encryption.sign(dataToSign, "/Users/glennboudaer/Documents", "/Users/glennboudaer/Documents");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		manager.readLicenseFile("/Users/glennboudaer/Documents", "/Users/glennboudaer/Documents", "/Users/glennboudaer/Documents");
	}

}
