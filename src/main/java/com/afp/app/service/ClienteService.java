package com.afp.app.service;

import java.util.List;
import java.util.Optional;

import com.afp.app.models.entity.Cliente;

public interface ClienteService{
	
	public List<Cliente> findAll();
	public Cliente save(Cliente cliente);
	public Optional<Cliente> findByDni(String dni);
	public void deleteCliente(Cliente cliente);

}
