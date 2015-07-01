package de.unimuenster.wi.wfm.application;

import java.awt.Color;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

//import org.zefer.pd4ml.PD4Constants;
//import org.zefer.pd4ml.PD4ML;
//import org.zefer.pd4ml.PD4PageMark;

public class PdfGenerator {
	protected int topValue = 10;
	protected int leftValue = 20;
	protected int rightValue = 10;
	protected int bottomValue = 10;
	protected int userSpaceWidth = 1300;

	public static void main(String[] args) {
		try {
			PdfGenerator jt = new PdfGenerator();
			jt.doConversion("http://pd4ml.com/dynsample.htm", "c:/_WfM/pd4ml.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doConversion(String url, String outputPath)
			throws InvalidParameterException, MalformedURLException,
			IOException {
//		File output = new File(outputPath);
//		java.io.FileOutputStream fos = new java.io.FileOutputStream(output);
//
//		PD4ML pd4ml = new PD4ML();
//		pd4ml.setHtmlWidth(userSpaceWidth);
//		pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
//		pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue,
//				rightValue));
//		pd4ml.useTTF("c:/windows/fonts", true);
//
//		PD4PageMark footer = new PD4PageMark();
//		footer.setPageNumberTemplate("page $[page] of $[total]");
//		footer.setTitleTemplate("Title: $[title]");
//		footer.setTitleAlignment(PD4PageMark.LEFT_ALIGN);
//		footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
//		footer.setColor(new Color(0x008000));
//		footer.setFontSize(14);
//		footer.setAreaHeight(18);
//		pd4ml.setPageFooter(footer);
//
//		Map m = new HashMap();
//		m.put("product", "DJVU to PDF eBook Converter");
//		m.put("free", "$9.99");
//		pd4ml.setDynamicParams(m);
//
//		pd4ml.render(new URL(url), fos);
//		fos.close();
//
//		System.out.println(outputPath + "\ndone.");
	}
}
