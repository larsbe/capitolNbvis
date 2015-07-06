package de.unimuenster.wi.wfm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;


//LiabilityCaseReport lcr = new LiabilityCaseReport();
//lcr.setCustomerName("Max Mustermann");
//lcr.setCaseID("0000009");
//lcr.setDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.");
//lcr.setAmtOfLoss("2.000 EUR");
//lcr.addAttachment("http://capitol.jonasgerlach.de/resources/pdf/image.png");
//lcr.addAttachment("http://capitol.jonasgerlach.de/resources/pdf/image.png");
//lcr.addAttachment("http://capitol.jonasgerlach.de/resources/pdf/image.png");
//lcr.setEligible("yes");
//lcr.setCarsFairValue("50.000 EUR");
//lcr.setDeductions("500 EUR");
//lcr.setPayment("1.500 EUR");
//lcr.setNameReviser("Bernd Stromberg");
//
//String downloadUrl = lcr.generatePDF();


public class LiabilityCaseReport {

	private String customerName = "";
	private String caseID = "";
	private String description = "";
	private String amtOfLoss = "";
	private String attachments = "";
	private String eligible = "";
	private String carsFairValue = "";
	private String deductions = "";
	private String payment = "";
	private String nameReviser = "";
	
	
	public static String PDF_LIABILITY_CASE_REPORT_URL = "http://capitol.jonasgerlach.de/resources/pdf/LiabilityCaseReport.html";
	public static String PDF_LIABILITY_CASE_REPORT_CSS_URL = "http://capitol.jonasgerlach.de/resources/pdf/style.css";
	public static String UPLOAD_DIR_HTTP_URL = "http://capitol.jonasgerlach.de/resources/uploads/";

	public String generatePDF() {
		File file = GeneratePDF();
		FTPUpload.uploadFile(file);
		return UPLOAD_DIR_HTTP_URL + file.getName();
	}
	
	public void addAttachment(String imgUrl) {
		attachments += "<img alt=\"\" height=\"150\" src=\"" + imgUrl + "\"/>&nbsp;";
	}
	
	public String replacePlaceholders(String input) {
		String output = input;
		output = output.replace("#{CUSTOMER_NAME}", customerName);
		output = output.replace("#{CASE_ID}", caseID);
		output = output.replace("#{DESCRIPTION}", description);
		output = output.replace("#{AMOUNT_OF_LOSS}", amtOfLoss);
		output = output.replace("#{ATTACHMENTS}", attachments);
		output = output.replace("#{ELIGIBLE}", eligible);
		output = output.replace("#{CARS_FAIR_VALUE}", carsFairValue);
		output = output.replace("#{DEDUCTIONS}", deductions);
		output = output.replace("#{PAYMENT}", payment);
		output = output.replace("#{NAME_REVISER}", nameReviser);
		return output;
	}
	
	private File GeneratePDF() {
		
		try {
			
			// INPUT
		    String htmlstring = replacePlaceholders(GetWebContent(PDF_LIABILITY_CASE_REPORT_URL));
			InputStream is = new ByteArrayInputStream(htmlstring.getBytes());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// PDF
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			writer.setInitialLeading(12.5f);
			document.open();

			// HTML
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

			// CSS
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			InputStream csspathtest = new ByteArrayInputStream(GetWebContent(PDF_LIABILITY_CASE_REPORT_CSS_URL).getBytes());
			CssFile cssfiletest = XMLWorkerHelper.getCSS(csspathtest);
			cssResolver.addCss(cssfiletest);

			// HTML2PDF
			Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
					new HtmlPipeline(htmlContext, new PdfWriterPipeline(
							document, writer)));
			XMLWorker worker = new XMLWorker(pipeline, true);
			XMLParser p = new XMLParser(worker);
			p.parse(is);//new FileInputStream("results/demo2/walden.html"));

			// OUTPUT
			document.close();
			File tmpFile = File.createTempFile("CapitolReport", ".pdf");
			FileOutputStream fos = new FileOutputStream(tmpFile); 
			baos.writeTo(fos);
			return tmpFile;
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
	
	private static String GetWebContent(String url) {
		String content = null;
		URLConnection connection = null;
		try {
			connection = new URL(url).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			scanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return content;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmtOfLoss() {
		return amtOfLoss;
	}

	public void setAmtOfLoss(String amtOfLoss) {
		this.amtOfLoss = amtOfLoss;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getEligible() {
		return eligible;
	}

	public void setEligible(String eligible) {
		this.eligible = eligible;
	}

	public String getCarsFairValue() {
		return carsFairValue;
	}

	public void setCarsFairValue(String carsFairValue) {
		this.carsFairValue = carsFairValue;
	}

	public String getDeductions() {
		return deductions;
	}

	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getNameReviser() {
		return nameReviser;
	}

	public void setNameReviser(String nameReviser) {
		this.nameReviser = nameReviser;
	}
	
}
