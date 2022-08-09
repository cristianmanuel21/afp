package com.afp.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.afp.app.models.entity.Afp;
import com.afp.app.models.entity.Cliente;
import com.afp.app.service.AfpService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {
	
	@Autowired
	private WebTestClient client;
	
	@Autowired
	private AfpService service;
	
	ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }
	

	//@Test
	void getAll() {
		client.get().uri("/cliente")
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(Cliente.class)
		.consumeWith(response->{
			List<Cliente> c=response.getResponseBody();
			assertNotNull(c);
			assertEquals(5, c.size());
		});
	}
	
	@Test 
	@Order(1)
	void getCustomerByDni(){
		client.get().uri("/cliente/43020102")
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$.nombre", "Paul");
		jsonPath("$.apellido", "Llanos");
		jsonPath("$.monto_disponible", 37800.0);
	}
	
	
	//@Test
	void saveCustomer() {
		//given
		Optional<Afp> afp=service.findbyId(1L);
		Cliente clienteN=new Cliente();
		clienteN.setAfp(afp.get());
		clienteN.setNombre("Gabriela");
		clienteN.setApellido("Quispe");
		clienteN.setCorreo("gabrielina@hotmail.com");
		clienteN.setDni("22556495");
		clienteN.setMonto_disponible(550000.0);
		clienteN.setTelefono("939055140");
		
		//when
		client.post().uri("/cliente")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(clienteN)
		.exchange()
		
		
		
		//then
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(Cliente.class)
		.consumeWith(response->{
			Cliente c1=response.getResponseBody();
			assertNotNull(c1);
			assertEquals( c1.getNombre(),"Gabriela");
			assertEquals(c1.getDni(),"22556495" );
			assertEquals(c1.getDni(), 550000.0);
		});
	}
	
	@Test
	@Order(2)
	void saveCustomerFinal() throws JsonProcessingException{
		//given
		Optional<Afp> afp=service.findbyId(1L);
		Cliente clienteN=new Cliente();
		clienteN.setAfp(afp.get());
		clienteN.setNombre("Alexis");
		clienteN.setApellido("Rojas");
		clienteN.setCorreo("cabezon@hotmail.com");
		clienteN.setDni("22557401");
		clienteN.setMonto_disponible(750000.0);
		clienteN.setTelefono("951000141");
		
		Map<String,Object> response=new HashMap<>();
		response.put("message", "Customer created successful");
		response.put("Customer", clienteN);
		
		//when
		client.post().uri("/cliente")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(clienteN	)
		.exchange()
		
		
		
		//then
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.consumeWith(respuesta->{
			try {
				JsonNode json = objectMapper.readTree(respuesta.getResponseBody());
				assertEquals("Customer created successful", json.path("message").asText());
				assertEquals("22557401", json.path("Customer").path("dni").asText());
				assertEquals("Alexis",json.path("Customer").path("nombre").asText());
				assertEquals(750000.0,json.path("Customer").path("monto_disponible").asDouble());
			}catch (IOException e) {
                e.printStackTrace();
            }
			
		})
		.jsonPath("$.message").isEqualTo("Customer created successful");
	}
	
	
	@Test
	@Order(3)
	void deleteByDni() {
		client.get().uri("/cliente/22557401")
		.exchange()
		.expectStatus().isOk();
		
		client.delete().uri("/cliente/22557401")
		.exchange()
		.expectStatus().isNoContent()
		.expectBody().isEmpty();
		
		client.get().uri("/cliente/22557401")
		.exchange()
		.expectStatus().isNotFound()
		.expectBody().isEmpty();
		
	}
	
	

}
