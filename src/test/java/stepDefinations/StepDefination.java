package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils{
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
	 	 res=given().spec(requestSpecification())
		.body(data.addPlacePayLoad(name,language,address));

	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	 	//Constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		 resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		 if(method.equalsIgnoreCase("POST"))
			 response = res.when().post(resourceAPI.getResource());
		 else if(method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
		 else if(method.equalsIgnoreCase("DELETE"))
			 response = res.when().delete(resourceAPI.getResource());
		 
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
	    assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		String resp = response.asString();
		JsonPath js=new JsonPath(resp);
		assertEquals(js.get(keyValue).toString(), expectedValue);
	}
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String string, String string2) {

		
	}
}
