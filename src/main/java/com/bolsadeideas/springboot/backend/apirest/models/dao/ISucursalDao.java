package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursales;
import java.util.List;

public interface ISucursalDao extends JpaRepository<Sucursales, Long> {
    List<Sucursales> findByFranquiciaId(Long franquiciaId);
}
