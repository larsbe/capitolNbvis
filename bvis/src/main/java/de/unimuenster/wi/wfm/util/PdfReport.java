package de.unimuenster.wi.wfm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

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
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class PdfReport {

	private static String replacePlaceholders(String input,
			Map<String, String> params) {
		String output = input;
		for (Entry<String, String> entry : params.entrySet()) {
			output = output.replace("$[" + entry.getKey() + "]",
					entry.getValue());
		}
		return output;
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
	
	public static String UPLOAD_DIR_HTTP_URL = "http://capitol.jonasgerlach.de/resources/uploads/";
	public static String PDF_REPORT_URL = "http://capitol.jonasgerlach.de/resources/pdf/bvisTemplate.html";
	public static String PDF_REPORT_CSS_URL = "http://capitol.jonasgerlach.de/resources/pdf/bvisStyle.css";
	
	public static String generatePDF(String templateHTML, String outputPath,
			Map<String, String> params) {
		File file = GeneratePDF(templateHTML, outputPath, params);
		FTPUpload.uploadFile(file);
		return UPLOAD_DIR_HTTP_URL + file.getName();
	}

	public static File GeneratePDF(String templateHTML, String outputPath,
			Map<String, String> params) {

		try {

			// INPUT
			String htmlstring = replacePlaceholders(
					GetWebContent(PDF_REPORT_URL), params);
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

			// HTML2PDF
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			InputStream csspathtest = new ByteArrayInputStream(GetWebContent(
					PDF_REPORT_CSS_URL).getBytes());
			CssFile cssfiletest = XMLWorkerHelper.getCSS(csspathtest);
			cssResolver.addCss(cssfiletest);

			// OUTPUT
			// OUTPUT
			document.close();
			File tmpFile = File.createTempFile("BvisReport", ".pdf");
			FileOutputStream fos = new FileOutputStream(tmpFile);
			baos.writeTo(fos);
			return tmpFile;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
