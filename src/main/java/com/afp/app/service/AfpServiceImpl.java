package com.afp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afp.app.models.entity.Afp;
import com.afp.app.repositories.AfpRepository;

@Service
public class AfpServiceImpl implements AfpService{
	
	@Autowired
	private AfpRepository afpRepository; 

	

	@Override
	@Transactional
	public Afp saveAfp(Afp afp) {
		return afpRepository.save(afp);
	}



	@Override
	@Transactional(readOnly = true)
	public Optional<Afp> findbyId(Long id) {
		return afpRepository.findById(id);
	}



	@Override
	@Transactional(readOnly = true)
	public List<Afp> findAll() {
		return (List<Afp>) afpRepository.findAll();
	}

}
