package de.unimuenster.wi.wfm.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import de.unimuenster.wi.wfm.entitiy.CarInformation;

public class CarInformationService {

	private final static String SERVICE_URL = "http://www.hsn-tsn.de/";
	private final static String SUFFIX = ".html";
	private final static String SEPERATOR = "-";
	
	private final static String XPATH_NAME = "body > table > tbody > tr:nth-child(2) > td:nth-child(2) > a";
	private final static String XPATH_ENGINE_POWER = "body > table > tbody > tr:nth-child(2) > td:nth-child(3)";
	private final static String XPATH_ENGINE_SIZE = "body > table > tbody > tr:nth-child(2) > td:nth-child(4)";
	private final static String XPATH_FUEL_TYPE = "body > table > tbody > tr:nth-child(2) > td:nth-child(5)";
	
	public static CarInformation GetCarInformation(String pHSN, String pTSN) {
	
		CarInformation carInfo = new CarInformation(pHSN, pTSN);
		
		Document doc = new Document("");
		try {
			String queryURL = SERVICE_URL + carInfo.getHsn() + SEPERATOR + carInfo.getTsn() + SUFFIX;
			doc = Jsoup.connect(queryURL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		carInfo.setName(GetXPathContent(doc, XPATH_NAME));
		carInfo.setEnginePower(GetXPathContent(doc, XPATH_ENGINE_POWER));
		carInfo.setEngineSize(GetXPathContent(doc, XPATH_ENGINE_SIZE));
		carInfo.setFuelType(GetXPathContent(doc, XPATH_FUEL_TYPE));
		
		return carInfo;
	}
	
	private static String GetXPathContent(Document doc, String xPath) {
		Elements elements = doc.select(xPath);
		if(!elements.isEmpty())
			return elements.text().toString();
		return "";
	}
	
}
