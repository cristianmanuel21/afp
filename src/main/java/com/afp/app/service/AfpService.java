package com.afp.app.service;

import java.util.List;
import java.util.Optional;

import com.afp.app.models.entity.Afp;


public interface AfpService {
	
	public List<Afp> findAll();
	public Optional<Afp> findbyId(Long id);
	public Afp saveAfp(Afp afp);

}
