package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;


public interface IProductoService {
	
	public List<Producto> findAll();

	public Producto save(Producto cliente);

	public void delete(Long id);

}
