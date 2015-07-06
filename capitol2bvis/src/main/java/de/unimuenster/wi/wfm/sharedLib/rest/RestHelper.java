package de.unimuenster.wi.wfm.sharedLib.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.impl.util.JsonUtil;

public class RestHelper {

	public static String escape(String str) {
		return StringEscapeUtils.escapeJson(str);
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	public static void SendMessage(String host, String msg) {
		try {

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(host);

			StringEntity input = new StringEntity(msg);
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			// no content
			if (response.getStatusLine().getStatusCode() == 204) {
				return;
			}

			// if content -> check 201
			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
