package com.afp.app.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="afps")
public class Afp {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NotNull(message="Not Empty")
	private String name;
	
	/*@OneToOne(mappedBy="afp",fetch=FetchType.LAZY, 
			cascade=CascadeType.ALL)
	private Cliente cliente;*/
	
	public Afp() {}
	
	public Afp(Long id,String name) {
		this.id=id;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
