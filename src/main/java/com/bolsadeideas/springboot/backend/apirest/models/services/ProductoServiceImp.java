package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.bolsadeideas.springboot.backend.apirest.models.dao.IProductoDao;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;

@Service
public class ProductoServiceImp implements IProductoService{
	
	@Autowired
	private IProductoDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
		
	}

	
	@Override
	public Producto save(Producto producto) {
		System.out.println("el producto enviado es: "+producto);
		return productoDao.save(producto);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}


}
