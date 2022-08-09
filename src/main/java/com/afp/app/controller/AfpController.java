package com.afp.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afp.app.models.entity.Afp;
import com.afp.app.service.AfpService;

@RestController
@RequestMapping("/afp")
public class AfpController {
	
	@Autowired
	private AfpService afpService;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(afpService.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<Afp> o=afpService.findbyId(id);
		if(o.isPresent()) {
			return ResponseEntity.ok(o.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> saveAfp(@Valid @RequestBody  Afp afp, BindingResult result){
		if(result.hasErrors()) {
			Map<String,String> errores=new HashMap<>();
			result.getFieldErrors().forEach(error->{
				errores.put(error.getField(), "El campo "+error.getField()+" "+error.getDefaultMessage());
			});
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
			
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(afpService.saveAfp(afp));
	}
	
	
	

}
