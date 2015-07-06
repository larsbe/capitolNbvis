package de.unimuenster.wi.wfm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.inject.Inject;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;

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

import de.unimuenster.wi.wfm.entitiy.ImageAttachment;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

public class LiabilityCaseReport {

	private String customerName = "";
	private String customerCompany = "";
	private String customerAddress = "";
	private String customerEMail = "";
	private String caseID = "";
	private String description = "";
	private String amtOfLoss = "";
	private String attachments = "";
	private String eligible = "";
	private String carsFairValue = "";
	private String deductions = "";
	private String payment = "";
	private String nameReviser = "";
	private String date = "";
	private String decisionNote = "";
	
	
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
	
	public static LiabilityCaseReport CreatePaymentReport(LiabilityCase claim) {
		LiabilityCaseReport report = new LiabilityCaseReport();
		report.setCaseID(String.valueOf(claim.getId()));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		report.setDate(sdf.format(date));
		report.setCustomerCompany(claim.getInsuranceContract().getCustomer().getCompany());
		report.setCustomerName(claim.getInsuranceContract().getCustomer().getName());
		report.setCustomerAddress(claim.getInsuranceContract().getCustomer().getAddress());
		report.setCustomerEMail(claim.getInsuranceContract().getCustomer().getEmail());
		report.setAmtOfLoss(claim.getEstimateOfCosts().toString() + " EUR");
		report.setDescription(claim.getClaimDetails());
		for(ImageAttachment i : claim.getImages())
			report.addAttachment(i.getFilePath());
		report.setEligible("Yes");
		report.setCarsFairValue(claim.getCarsFairValue().toString());
		Double deduction = claim.getEstimateOfCosts() - claim.getInsuranceSum();
		if(deduction < 0)
			deduction = 0.0;
		report.setDeductions(deduction.toString());
		report.setPayment(claim.getInsuranceSum() + " EUR");
		report.setNameReviser("Bernd Stromberg / CEO");
		report.setDecisionNote(claim.getDecisionNote());
		return report;
	}
	
	
	public static LiabilityCaseReport CreateRejection(LiabilityCase claim) {
		LiabilityCaseReport report = new LiabilityCaseReport();
		report.setCaseID(String.valueOf(claim.getId()));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		report.setDate(sdf.format(date));
		report.setCustomerCompany(claim.getInsuranceContract().getCustomer().getCompany());
		report.setCustomerName(claim.getInsuranceContract().getCustomer().getName());
		report.setCustomerAddress(claim.getInsuranceContract().getCustomer().getAddress());
		report.setCustomerEMail(claim.getInsuranceContract().getCustomer().getEmail());
		report.setAmtOfLoss(claim.getEstimateOfCosts().toString() + " EUR");
		report.setDescription(claim.getClaimDetails());
		for(ImageAttachment i : claim.getImages())
			report.addAttachment(i.getFilePath());
		report.setEligible("No (Rejected)");
		report.setCarsFairValue("/");
		report.setDeductions("/");
		report.setPayment("0.00 EUR");
		report.setNameReviser("Bernd Stromberg / CEO");
		report.setDecisionNote(claim.getDecisionNote());
		return report;
	}
	
	public String replacePlaceholders(String input) {
		String output = input;
		output = output.replace("#{DATE}", date);
		output = output.replace("#{CUSTOMER_COMPANY}", customerCompany);
		output = output.replace("#{CUSTOMER_NAME}", customerName);
		output = output.replace("#{CUSTOMER_ADDRESS}", customerAddress);
		output = output.replace("#{CUSTOMER_EMAIL}", customerEMail);
		output = output.replace("#{CASE_ID}", caseID);
		output = output.replace("#{DESCRIPTION}", description);
		output = output.replace("#{AMOUNT_OF_LOSS}", amtOfLoss);
		output = output.replace("#{ATTACHMENTS}", attachments);
		output = output.replace("#{ELIGIBLE}", eligible);
		output = output.replace("#{CARS_FAIR_VALUE}", carsFairValue);
		output = output.replace("#{DEDUCTIONS}", deductions);
		output = output.replace("#{PAYMENT}", payment);
		output = output.replace("#{NAME_REVISER}", nameReviser);
		output = output.replace("#{DECISION_NOTE}", decisionNote);
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

	public String getCustomerCompany() {
		return customerCompany;
	}

	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEMail() {
		return customerEMail;
	}

	public void setCustomerEMail(String customerEMail) {
		this.customerEMail = customerEMail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDecisionNote() {
		return decisionNote;
	}

	public void setDecisionNote(String decisionNote) {
		this.decisionNote = decisionNote;
	}
	
	
	
}
