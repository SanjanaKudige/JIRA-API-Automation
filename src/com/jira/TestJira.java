package com.jira;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestJira {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"/Users/balajianoopgupta/Documents/workspace/JiraAPI/src/Files/env.properties");
		prop.load(fis);
	}

	//
	// @Test
	// public void createIssue() throws IOException {
	//
	// RestAssured.baseURI = prop.getProperty("JIRAHOST");
	//
	// Response res = given().header("Content-Type", "application/json")
	// .header("Cookie", "JSESSIONID=" + ReusableMethods.getSessionKey())
	// .body(GenerateStringFromResource(
	// "/Users/balajianoopgupta/Documents/workspace/JiraAPI/src/Files/data.json"))
	// .when().post(resources.placePostData()).then().statusCode(201).extract().response();
	//
	// JsonPath js = ReusableMethods.rawToJSON(res);
	// String id = js.get("id");
	// System.out.println(id);
	//
	// }
	//
	public static String GenerateStringFromResource(String path) throws IOException {

		String st = new String(Files.readAllBytes(Paths.get(path)));
		System.out.println(st);
		return st;

	}

	@Test
	public void JiraAPICreateIssue() throws IOException {
		// Creating Issue/Defect

		RestAssured.baseURI = "http://localhost:8080";
		Response res = given().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + ReusableMethods.getSessionKey())
				.body("{" + "\"fields\": {" + "\"project\":{" + "\"key\": \"SOF\"" + "},"
						+ "\"summary\": \"Issue 5 for adding comment\","
						+ "\"description\": \"Creating my second bug\"," + "\"issuetype\": {" + "\"name\": \"Buggie\""
						+ "}" + "}}")
				.when().post("/rest/api/2/issue").then().statusCode(201).extract().response();

		JsonPath js = ReusableMethods.rawToJSON(res);
		String id = js.get("id");
		System.out.println(id);

	}
	
	
	//to update a comment
	@Test
	public void JiraAPIUpdate() throws IOException {
		// Creating Issue/Defect

		RestAssured.baseURI = "http://localhost:8080";
		Response res = given().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + ReusableMethods.getSessionKey()).pathParams("commentid", "10103").

				body("{ \"body\": \"Updating comment from the automation code\"," + "\"visibility\": {"
						+ "\"type\": \"role\"," + "\"value\": \"Administrators\" }" + "}")
				.when().put("/rest/api/2/issue/10207/comment/{commentid}").then().statusCode(200).extract().response();

	}
}
