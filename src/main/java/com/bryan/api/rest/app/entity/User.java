package com.bryan.api.rest.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "usuario")
public class User implements Serializable {

	private static final long serialVersionUID = 3276759328509000797L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String nombre;

	private String clave;

	@Column(name = "mail", nullable = false, length = 50, unique = true)
	private String email;
	private boolean estado;

	private String cedulaPath;
	
	private String cedulaUrl;

	private String fotoPath;
	
	private String fotoUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getCedulaPath() {
		return cedulaPath;
	}

	public void setCedulaPath(String cedulaPath) {
		this.cedulaPath = cedulaPath;
	}

	public String getCedulaUrl() {
		return cedulaUrl;
	}

	public void setCedulaUrl(String cedulaUrl) {
		this.cedulaUrl = cedulaUrl;
	}

	public String getFotoPath() {
		return fotoPath;
	}

	public void setFotoPath(String fotoPath) {
		this.fotoPath = fotoPath;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

}
