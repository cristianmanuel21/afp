package com.afp.app.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true,nullable = false)
	@NotEmpty(message ="no puede estar vacio")
	@Size(min = 8,max=8, message="El tamaño debe ser de 8 digitos")
	private String dni;
	
	@NotEmpty(message ="no puede estar vacio")
	private String nombre;
	
	@NotEmpty(message ="no puede estar vacio")
	private String apellido;
	
	@NotEmpty(message ="no puede estar vacio")
	private String telefono;
	
	
	@NotEmpty(message ="no puede estar vacio")
	@Email(message="no tiene el formato adecuado")
	private String correo;
	
	@NotNull(message ="no puede estar vacio")
	private Double monto_disponible;
	
	
	@NotNull(message ="no puede estar vacio")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="afp_id")
	@JsonIgnoreProperties("hibernateLazyInitializer")
	private Afp afp;
	
	public Cliente() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public Double getMonto_disponible() {
		return monto_disponible;
	}
	public void setMonto_disponible(Double monto_disponible) {
		this.monto_disponible = monto_disponible;
	}
	

	public Afp getAfp() {
		return afp;
	}

	public void setAfp(Afp afp) {
		this.afp = afp;
	}
	
	
	
	

}
