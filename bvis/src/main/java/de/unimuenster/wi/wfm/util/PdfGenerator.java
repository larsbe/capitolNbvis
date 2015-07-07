package de.unimuenster.wi.wfm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

public class PdfGenerator {

	public static void main(String[] args) {
		try {
			PdfGenerator jt = new PdfGenerator();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("rentalAgreementID", "00000000123");

			jt.doConversion("pdfTemplates\\TemplateContract.htm",
					"c:/_WfM/pd4ml.pdf", params);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void doConversion(String templateHTML, String outputPath,
			Map<String, String> params) throws FileNotFoundException {

		String content = new Scanner(new File(templateHTML)).useDelimiter("\\Z").next();

		PdfReport.GeneratePDF(content, outputPath, params);

		// // input
		// java.io.InputStreamReader isr = new java.io.InputStreamReader( new
		// java.io.FileInputStream(templateHTML));
		//
		// // output
		// File output = new File(outputPath);
		// java.io.FileOutputStream fos = new java.io.FileOutputStream(output);
		//
		// PD4ML pd4ml = new PD4ML();
		// pd4ml.setPageSize(PD4Constants.A4);
		//
		// pd4ml.setDynamicParams(params);
		// pd4ml.enableDebugInfo();
		// pd4ml.render(isr, fos);
		// fos.close();

		System.out.println(outputPath + "\ndone.");
	}
}
