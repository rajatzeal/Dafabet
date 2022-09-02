package com.qa.api.Timestamp.restClient;

import java.util.List;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class is having all http methods which will call the apis and having
 * generic methods for getting the response and fetch the values from response.
 * 
 * @author RajatSharma
 *
 */

public class RestClient {

	// HTTP Methods: GET POST PUT DELETE

	/**
	 * This method is used to call GET API
	 * 
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param params
	 * @param log
	 * @return this method is returning response from the GET call
	 */
	
	public static Response doGet(String contentType, String baseURI, String basePath, Map params,String token, boolean log) {
		RestClient.setBaseURI(baseURI);
		RequestSpecification request = RestClient.createRequest(contentType, params, token, log);
		return RestClient.executeAPI("GET", request, basePath);
	}

	private static boolean setBaseURI(String baseURI) {

		if (baseURI == null || baseURI.isEmpty()) {
			System.out.println("Please pass the correct base URI....either it is null or blank/empty...");
			return false;
		}
		try {
			RestAssured.baseURI = baseURI;
			return true;
		} catch (Exception e) {
			System.out.println("some exception got occurred while assiging the base URI with Rest Assured....");
			return false;
		}
	}

	public static RequestSpecification createRequest(String contentType, Map params, String token, boolean log) {
		RequestSpecification request;
		if (log) {
			request = RestAssured.given().log().all();
		} else {
			request = RestAssured.given();
		}
		if (token != null) {
			request.header("Authorization", "Bearer " + token);
		}
		
		if (!(params == null)) {
			request.queryParams(params);
		}
		
		if (contentType.equalsIgnoreCase("JSON")) {
			request.contentType(ContentType.JSON);
		} else if (contentType.equalsIgnoreCase("XML")) {
			request.contentType(ContentType.XML);
		} else if (contentType.equalsIgnoreCase("TEXT")) {
			request.contentType(ContentType.TEXT);
		}
		return request;
	}

	private static Response executeAPI(String httpMethod, RequestSpecification request, String basePath) {
		Response response = null;
		switch (httpMethod) {
		case "GET":
			response = request.get(basePath);
			break;
		case "POST":
			response = request.post(basePath);
			break;
		case "PUT":
			response = request.put(basePath);
			break;
		case "DELETE":
			response = request.delete(basePath);
			break;

		default:
			System.out.println("Please pass the corrent http method....");
			break;
		}

		return response;
	}

	public static JsonPath getJsonPath(Response response) {
		return response.jsonPath();
	}

	public static int getStatusCode(Response response) {
		return response.getStatusCode();
	}

	public static String getHeaderValue(Response response, String headerName) {
		return response.getHeader(headerName);
	}

	public static int getResponseHeaderCount(Response response) {
		Headers headers = response.getHeaders();
		return headers.size();
	}

	public static List<Header> getResponseHeaders(Response response) {
		Headers headers = response.getHeaders();
		List<Header> headerList = headers.asList();
		return headerList;
	}

	public static void getPrettyResponsePrint(Response response) {
		System.out.println("========response String in pretty format======");
		response.prettyPrint();
	}

	
}
