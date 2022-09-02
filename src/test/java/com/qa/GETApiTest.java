package com.qa;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.Timestamp.restClient.RestClient;
import com.qa.constants.Constants;

import org.testng.Assert;
//import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class GETApiTest {

	public static Response response;
	
  @Test
  public static void getAPITest() { 	
	  
	  String baseURI = "https://showcase.api.linx.twenty57.net";
	  String basePath = "/UnixTime/fromunixtimestamp";

	  Map<String, String> params = new HashMap<String, String>();
	  params.put("unixtimestamp", "1549892280");
	
	  response = RestClient.doGet("JSON", baseURI, basePath, params,null, true);
	  RestClient.getPrettyResponsePrint(response);
	  
	  System.out.println(response.prettyPrint());
  
  }
  
  @Test
  public static void responseTime() {
	  System.out.println(response.time());
	  Assert.assertTrue(response.time()>500, "Time Taken is greater than 500 ms" );
  }
  
  @Test
  public static void statusCode() {
	  System.out.println(response.getStatusCode());
	  Assert.assertEquals(RestClient.getStatusCode(response), Constants.HTTP_STATUS_CODE_200,"Status Code is not 200");
  }
  
  @Test
  public static void responseBody() {
	  System.out.println(response.prettyPrint());
	  Assert.assertTrue(response.prettyPrint().contains("Datetime"),"Response Body is missing out the text Datetime");
  }
  
  @Test
  public static void responseNotNull() {
	  Assert.assertFalse(response.prettyPrint().isEmpty(), "Response is Null");
  }
  
  @Test
  public static void responseText(){
	  Assert.assertTrue(response.prettyPrint().contains("2019-02-11 13:38:00"), "The response body does not match the expected String");
  }
  
  @Test
  public static void dateFormat() {
	  
	  String dt = "2019-02-11 13:38:00";
	  boolean isDate = false;
	  String datePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
	      
	  isDate = dt.matches(datePattern);
	  System.out.println("Date :"+ dt+": matches with the this date Pattern:"+datePattern+"Ans:"+isDate);
	        
  }
  
  
}
