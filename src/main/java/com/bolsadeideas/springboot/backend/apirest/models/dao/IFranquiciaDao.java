package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;

public interface IFranquiciaDao extends JpaRepository<Franquicia, Long> {
}
