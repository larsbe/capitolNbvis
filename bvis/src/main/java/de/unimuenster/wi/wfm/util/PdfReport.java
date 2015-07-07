package de.unimuenster.wi.wfm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;


public class PdfReport {

	
	private static String replacePlaceholders(String input, Map<String, String> params) {
		String output = input;
		for(Entry<String, String> entry : params.entrySet()) {
			output = output.replace("$["+entry.getKey()+"]", entry.getValue());
		}
		return output;
	}
	
	public static File GeneratePDF(String templateHTML, String outputPath, Map<String, String> params) {
		
		try {
			
			// INPUT
		    String htmlstring = replacePlaceholders(templateHTML, params);
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
			Pipeline<?> pipeline = new HtmlPipeline(htmlContext, new PdfWriterPipeline(
							document, writer));
			XMLWorker worker = new XMLWorker(pipeline, true);
			XMLParser p = new XMLParser(worker);
			p.parse(is);//new FileInputStream("results/demo2/walden.html"));

			// OUTPUT
			document.close();
			File tmpFile = new File(outputPath);
			FileOutputStream fos = new FileOutputStream(tmpFile); 
			baos.writeTo(fos);
			return tmpFile;
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
	
	
	
}
