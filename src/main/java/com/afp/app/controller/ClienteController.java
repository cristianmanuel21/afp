package com.afp.app.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afp.app.models.entity.Cliente;
import com.afp.app.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(clienteService.findAll());
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<?> getCustomerByDni(@PathVariable String dni) {
		Optional<Cliente> o=clienteService.findByDni(dni);
		if(o.isPresent()) {
			return ResponseEntity.ok(o.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> addCustomer(@Valid @RequestBody Cliente cliente,BindingResult result){
		Cliente newCustomer=null;
		Map<String,Object> response=new HashMap<>();
		if(result.hasErrors()) {
			result.getFieldErrors().stream().map(k->{
				return response.put(k.getField(), "el campo "+k.getField()+" "+k.getDefaultMessage());
			}).collect(Collectors.toList());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		try {
			newCustomer=clienteService.save(cliente);
			
			
		}catch(DataAccessException e) {
			response.put("message", "Error al insertar en base de datos");
			response.put("error",e.getMessage().concat(e.getMostSpecificCause()+" "+e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("message", "Customer created successful");
		response.put("Customer", newCustomer);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/{dni}")
	public ResponseEntity<?> editCustomer(@Valid @RequestBody Cliente cliente,BindingResult result, @PathVariable String dni){
		
		if(result.hasErrors()) {
			Map<String,Object> errores=new HashMap<>();
			result.getFieldErrors().stream().map(k->{
				return errores.put(k.getField(), "el campo "+k.getField()+" "+k.getDefaultMessage());
			}).collect(Collectors.toList()); 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
		}
		try {
			Optional<Cliente> clienteFinal= clienteService.findByDni(dni);
			if(clienteFinal.isPresent()) {
				clienteFinal.get().setCorreo(cliente.getCorreo());
				clienteFinal.get().setMonto_disponible(cliente.getMonto_disponible());
				clienteFinal.get().setTelefono(cliente.getTelefono());
				return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteFinal.get()));
			}
		}catch(DataAccessException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Collections.singletonMap("message","Error en la comunicacion o en la insercion en la BD"));
		}
		
		return ResponseEntity.notFound().build();		
	}
	
	
	@DeleteMapping("/{dni}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String dni){
		Optional<Cliente> o=clienteService.findByDni(dni);
		if(o.isPresent()) {
			clienteService.deleteCliente(o.get());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
