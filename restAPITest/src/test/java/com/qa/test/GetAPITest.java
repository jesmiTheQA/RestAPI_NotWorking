package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.qa.Util.TestUtil;
import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class GetAPITest extends TestBase {

	TestBase testbase;
	String url1;
	String url2;
	String url;
	RestClient obj;
	CloseableHttpResponse HttpResponse;

	@BeforeMethod
	public void SetUp() {

		testbase = new TestBase();
		url1 = prop.getProperty("URL");
		url2 = prop.getProperty("ServiceURL");
		url = url1 + url2;

	}

	@Test
	public void getTest() throws ClientProtocolException, IOException, JSONException {

		obj = new RestClient();
		HttpResponse = obj.getAPI(url);

		// to get the status
		int stausCode = HttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code: " + stausCode);
		Assert.assertEquals(stausCode, RESPONSE_STATUS_CODE_200, "sTATUS CODE IS NOT 200");

		// geting the response string
		String ResponseString = EntityUtils.toString(HttpResponse.getEntity(), "UTF-8");

		// converting response string to json response
		JSONObject responsejson = new JSONObject(ResponseString);
		System.out.println("Json response: " + responsejson);

		// single value assertion:
		// per_page:
		String perPageValue = TestUtil.getValueByJPath(responsejson, "/per_page");
		System.out.println("value of per page is-->" + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		// total:
		String totalValue = TestUtil.getValueByJPath(responsejson, "/total");
		System.out.println("value of total is-->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJPath(responsejson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responsejson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responsejson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responsejson, "/data[0]/first_name");

		System.out.println("id: "+id);
		System.out.println("lastName: "+lastName);
		System.out.println("firstName: "+firstName);
		System.out.println("avatar: "+avatar);
		
		Assert.assertEquals(Integer.parseInt(id), 1);
		Assert.assertEquals(lastName, "Bluth");
		Assert.assertEquals(firstName, "George");
		Assert.assertEquals(avatar, "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
		

		// to get header
		Header[] headerArray = HttpResponse.getAllHeaders();
		HashMap<String, String> Headers = new HashMap<String, String>();
		for (Header header : headerArray) {
			Headers.put(header.getName(), header.getValue());
		}
		System.out.println("Headers: " + Headers);
	}
}
