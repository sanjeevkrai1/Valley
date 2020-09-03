package com.valley.cws.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.valley.cws.bean.PolicyResBean;

public class ApacheHttpClientPost {

	public static PolicyResBean getAd(String macAddress) {
		PolicyResBean resBean = null; 
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			String PapUrl = CommonUtils.propValue("get_Ad_Details");
			HttpPost postRequest = new HttpPost(PapUrl);

			StringEntity input = new StringEntity("{\"macAddress\":\"" + macAddress.toUpperCase() + "\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			StringBuffer stringBuffer = new StringBuffer();
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				stringBuffer.append(output);
			}

			httpClient.getConnectionManager().shutdown();
			System.out.println(stringBuffer.toString());

			resBean = (PolicyResBean) Parser.JsonToObject(stringBuffer.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resBean;
	}

}