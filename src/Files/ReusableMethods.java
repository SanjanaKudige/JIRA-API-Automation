package Files;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {
	public static JsonPath rawToJSON(Response r) {
		String resp = r.asString();
		JsonPath x = new JsonPath(resp);
		return x;

	}

	static Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"/Users/balajianoopgupta/Documents/workspace/JiraAPI/src/Files/env.properties");

		prop.load(fis);
	}

	public static String getSessionKey() throws IOException {
		// FileInputStream fis = new FileInputStream(
		// "/Users/balajianoopgupta/Documents/workspace/JiraAPI/src/Files/env.properties");
		//
		// prop.load(fis);

		RestAssured.baseURI = "http://localhost:8080";

		Response res = given().header("Content-Type", "application/json")

				.body("{ \"username\": \"SanjanaKudige\", \"password\":\"Password@12345\" }").when()

				.post("/rest/auth/1/session").then().statusCode(200).extract().response();

		JsonPath jpath = ReusableMethods.rawToJSON(res);
		String sessionID = jpath.get("session.value");
		System.out.println("Session key being returned =" + sessionID);
		return sessionID;

	}
}
