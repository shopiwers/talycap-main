package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import java.util.List;

public interface IFranquiciaDao extends JpaRepository<Franquicia, Long> {

    @Query("SELECT f FROM Franquicia f LEFT JOIN FETCH f.sucursales")
    List<Franquicia> findAllWithSucursales();
}
