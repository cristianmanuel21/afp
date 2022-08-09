package com.afp.app;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.afp.app.models.entity.Afp;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AfpControllerTests {
	
	@Autowired
	private WebTestClient client;

	@Test
	@Order(1)
	void getAllAfp() {
		
		client.get().uri("/afp")
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$").value(hasSize(4));
	}
	
	@Test
	@Order(2)
	void getAllAfp2() {
		client.get().uri("/afp")
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(Afp.class)
		.consumeWith(response->{
			List<Afp> lista=response.getResponseBody();
			assertEquals(lista.size(), 4);
			assertNotNull(lista);
			
		});
	}
	
	@Test
	@Order(3)
	void getIdAFfp() {
		client.get().uri("/afp/1")
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$.nombre", "PRIMA");
	}
	
	
	//@Test
	@Order(4)
	void saveAfp() {
		//given
		Afp afp1=new Afp();
		afp1.setName("HABITAT");
		
		//when
		client.post().uri("/afp")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(afp1)
		.exchange()
		//then
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(Afp.class)
		.consumeWith(response->{
			Afp afpN=response.getResponseBody();
			assertNotNull(afpN);
			assertEquals(afpN.getName(), "HABITAT");
		});
	}
	
	//@Test
	@Order(5)
	void deleteAfp() {
		// no lo implemente
	}
	
	
	
	
	

}
