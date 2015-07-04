package de.unimuenster.wi.wfm.util;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import de.unimuenster.wi.wfm.entitiy.CarInformation;

public class CarInformationService {

	private final static String HSN_SERVICE_URL = "http://www.hsn-tsn.de/";
	private final static String CLASS_SERVICE_URL = "http://www.autoampel.de/bmw-isetta-250-0005-301.html";
	private final static String CLASS_PREFIX = "IDontCare-";
	private final static String IMAGE_SERVICE_URL = "https://www.google.de/";
	private final static String IMAGE_PREFIX = "search?q=";
	private final static String IMAGE_SUFFIX = "+catalog&tbm=isch&tbs=isz:m";
	private final static String IMAGE_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
	private final static String SUFFIX = ".html";
	private final static String SEPERATOR = "-";
		
	private final static String XPATH_NAME = "body > table > tbody > tr:nth-child(2) > td:nth-child(2) > a";
	private final static String XPATH_ENGINE_POWER = "body > table > tbody > tr:nth-child(2) > td:nth-child(3)";
	private final static String XPATH_ENGINE_SIZE = "body > table > tbody > tr:nth-child(2) > td:nth-child(4)";
	private final static String XPATH_FUEL_TYPE = "body > table > tbody > tr:nth-child(2) > td:nth-child(5)";
	private final static String XPATH_CLASS_KH = "#tab_versicherung > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(3) > span";
	private final static String XPATH_CLASS_TK = "#tab_versicherung > table:nth-child(4) > tbody > tr:nth-child(2) > td:nth-child(3) > span";
	private final static String XPATH_CLASS_VK = "#tab_versicherung > table:nth-child(4) > tbody > tr:nth-child(3) > td:nth-child(3) > span";
	private final static String XPATH_IMAGE_FIRST = "#rg_s > div:nth-child(1) > a";
	
	public static CarInformation GetCarInformation(String pHSN, String pTSN, String pYear) {
	
		CarInformation carInfo = new CarInformation(pHSN, pTSN, pYear);
		carInfo = AddMasterData(carInfo);
		carInfo = AddClassificationData(carInfo);
		carInfo = AddImage(carInfo);
		
		return carInfo;
	}
	
	private static CarInformation AddImage(CarInformation carInfo) {
		Document doc = new Document("");
		try {
			String queryURL = IMAGE_SERVICE_URL + IMAGE_PREFIX + carInfo.getName() + " " + carInfo.getYear() + IMAGE_SUFFIX;
			doc = Jsoup.connect(queryURL.replaceAll("\\s","+")).userAgent(IMAGE_USER_AGENT).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String imgLinkUrl = GetXPathHref(doc, XPATH_IMAGE_FIRST);
		List<NameValuePair> paramsList;
		try {
			paramsList = URLEncodedUtils.parse(new URI(imgLinkUrl),"utf-8");
			for (NameValuePair parameter : paramsList)
			    if (parameter.getName().equals("imgurl")) {
			    	carInfo.setImageUrl(parameter.getValue());
			    }
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return carInfo;
	}
	
	private static CarInformation AddClassificationData(CarInformation carInfo) {		
		Document doc = new Document("");
		try {
			String queryURL = CLASS_SERVICE_URL + CLASS_PREFIX + carInfo.getHsn() + SEPERATOR + carInfo.getTsn() + SUFFIX;
			doc = Jsoup.connect(queryURL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		carInfo.setClassKH(GetXPathContent(doc, XPATH_CLASS_KH));
		carInfo.setClassTK(GetXPathContent(doc, XPATH_CLASS_TK));
		carInfo.setClassVK(GetXPathContent(doc, XPATH_CLASS_VK));

		return carInfo;
	}
	
	private static CarInformation AddMasterData(CarInformation carInfo) {		
		Document doc = new Document("");
		try {
			String queryURL = HSN_SERVICE_URL + carInfo.getHsn() + SEPERATOR + carInfo.getTsn() + SUFFIX;
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
	
	private static String GetXPathHref(Document doc, String xPath) {
		Elements elements = doc.select(xPath);
		if(!elements.isEmpty() && elements.hasAttr("href"))
			return elements.attr("href").toString();
		return "";
	}
	
	private static String GetXPathContent(Document doc, String xPath) {
		Elements elements = doc.select(xPath);
		if(!elements.isEmpty())
			return elements.text().toString();
		return "";
	}
	
}
