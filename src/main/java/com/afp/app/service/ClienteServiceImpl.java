package com.afp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afp.app.models.entity.Cliente;
import com.afp.app.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> findByDni(String dni) {
		return clienteRepository.findByDni(dni);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteCliente(Cliente cliente) {
		clienteRepository.delete(cliente);
		
	}

}
