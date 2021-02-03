package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;


public class RestClient {
	
	
	
	//Get Method without headers
	public CloseableHttpResponse getAPI(String url) throws ClientProtocolException, IOException, JSONException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet= new HttpGet(url);
		CloseableHttpResponse HttpResponse= httpClient.execute(httpGet);
		return HttpResponse;
		
	}
	
	//Get Method with headers
	public CloseableHttpResponse getAPI(String url, HashMap<String,String> headerMap) throws ClientProtocolException, IOException, JSONException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet= new HttpGet(url);
		//CloseableHttpResponse HttpResponse= httpClient.execute(httpGet);
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpGet); //hit the GET URL
		return closebaleHttpResponse;
		
	}
}
