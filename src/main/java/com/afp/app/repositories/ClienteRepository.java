package com.afp.app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.afp.app.models.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
	
	public Optional<Cliente> findByDni(String dni);

}
