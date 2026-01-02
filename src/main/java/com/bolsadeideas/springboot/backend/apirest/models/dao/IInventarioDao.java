package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Inventario;
import java.util.List;
import java.util.Optional;

public interface IInventarioDao extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySucursalId(Long sucursalId);

    //Inventario findTopBySucursalIdOrderByStockDesc(Long sucursalId);

    Optional<Inventario> findBySucursalIdAndProductoId(Long sucursalId, Long productoId);

   // Inventario findTopBySucursalesIdOrderByStockDesc(Long sucursalId);

    Inventario findTopBySucursal_IdOrderByStockDesc(Long sucursalId);


}