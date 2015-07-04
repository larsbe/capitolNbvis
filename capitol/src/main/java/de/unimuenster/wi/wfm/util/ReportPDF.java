package de.unimuenster.wi.wfm.util;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.RandomAccess;

import javax.imageio.ImageIO;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class ReportPDF {

	public void generatePDF() {
		String outputFileName = "CapitolReport.pdf";

        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);

        PDRectangle rect = page1.getMediaBox();
        document.addPage(page1);

        // Create a new font object selecting one of the PDF base fonts
        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos;
		try {
			
			  	
	    	BufferedImage awtImage = ImageIO.read(downloadFile("http://capitol.jonasgerlach.de/captiolResources/pdf/capitol_logo.jpg"));
	    	File tmpLogo = File.createTempFile("pdfImg", ".tmp");
	    	ImageIO.write(awtImage, "jpg", tmpLogo);
	    	
	    	
	    	
	    	
	    	
	   
	        PDJpeg ximage = new PDJpeg(document, ImageIO.read(tmpLogo));
	        
			
			cos = new PDPageContentStream(document, page1);
			int line = 0;
			
			cos.drawImage(ximage, 100, 400);
			
			
			
			cos.beginText();
	        cos.setFont(fontPlain, 12);
	        cos.moveTextPositionByAmount(100, rect.getHeight() - 50*(++line));
	        cos.drawString("Hello World");
	        cos.endText();
	      
	        
	        
    
	        cos.close();
	        
	        File tmpFile = File.createTempFile("pdf_test", ".pdf");
	        
	        document.save(tmpFile);
	        document.close();
	        
	        uploadFile(tmpFile);	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		       
	}
	
	private File downloadFile(String pUrl) {
		URL url;
		File output = null;
		try {
			url = new URL(pUrl);
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
			output = File.createTempFile("download", ".tmp");
			FileOutputStream fos = new FileOutputStream(output);
			byte[] buf = new byte[512];
			while (true) {
			    int len = in.read(buf);
			    if (len == -1) {
			        break;
			    }
			    fos.write(buf, 0, len);
			}
			in.close();
			fos.flush();
			fos.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	private void uploadFile(File file) {
		FTPClient client = new FTPClient();
		FileInputStream fis = null;

		try {
		    client.connect("capitol.jonasgerlach.de");
		    client.login("jonascapitol", "Test1234!");

		    //
		    // Create an InputStream of the file to be uploaded
		    //
		    fis = new FileInputStream(file);

		    //
		    // Store file to server
		    //
		    
		    System.out.println("Upload: " + client.storeFile("./web/"+ file.getName(), fis));
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
