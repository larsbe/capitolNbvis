package de.unimuenster.wi.wfm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPUpload {

	public static void uploadFile(File file) {
		FTPClient client = new FTPClient();
		FileInputStream fis = null;

		try {
		    client.connect("capitol.jonasgerlach.de");
		    client.login("jonascapitol", "Test1234!");
		    client.setFileType(FTP.BINARY_FILE_TYPE);
		    client.setFileTransferMode(FTP.BINARY_FILE_TYPE);

		    //
		    // Create an InputStream of the file to be uploaded
		    //
		    fis = new FileInputStream(file);

		    //
		    // Store file to server
		    //
		    
		    System.out.println("Upload: " + client.storeFile("./web/resources/uploads/"+ file.getName(), fis));
		    client.logout();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (fis != null) {
		            fis.close();
		        }
		        client.disconnect();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
}
