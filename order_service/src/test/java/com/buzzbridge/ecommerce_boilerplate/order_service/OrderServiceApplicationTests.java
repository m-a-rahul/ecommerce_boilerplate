package com.buzzbridge.ecommerce_boilerplate.order_service;

import com.buzzbridge.ecommerce_boilerplate.order_service.stub.InventoryClientStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.shaded.org.hamcrest.Matcher;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {
	@LocalServerPort
	private Integer port;
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}
	@Test
	void testOrderPlacement() {
		String requestBody = """
					{"skuCode":"iphone_16","price":2000,"quantity":1,"userDetails":{"email":"test1user@gmail.com"}}
				""";
		InventoryClientStub.stubInventoryCall("iphone_16",1);
		String responseBody = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();
		assertThat(responseBody, Matchers.is("Order Placed Successfully"));
	}

	private void assertThat(String responseBody, Matcher<String> orderPlacedSuccessfully) {
	}

}
