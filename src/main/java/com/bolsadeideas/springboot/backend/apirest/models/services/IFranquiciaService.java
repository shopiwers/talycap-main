// java
package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursales;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Inventario;
import com.bolsadeideas.springboot.backend.apirest.models.services.dto.ProductoSucursalDTO;
import java.util.List;
import java.util.Optional;

public interface IFranquiciaService {
    Franquicia crearFranquicia(Franquicia franquicia);
    Optional<Franquicia> buscarFranquicia(Long id);
    Sucursales agregarSucursal(Long franquiciaId, Sucursales sucursal);
    Sucursales actualizarNombreSucursal(Long sucursalId, String nuevoNombre);
    Franquicia actualizarNombreFranquicia(Long franquiciaId, String nuevoNombre);
    Inventario agregarProductoASucursal(Long sucursalId, Long productoId, Integer stock);
    void eliminarProductoDeSucursal(Long sucursalId, Long productoId);
    Inventario actualizarStock(Long sucursalId, Long productoId, Integer nuevoStock);
    List<ProductoSucursalDTO> productosMaxStockPorFranquicia(Long franquiciaId);
    List<ProductoSucursalDTO> productosMaxStockPorFranquiciaReactivo(Long franquiciaId);

    Producto actualizarNombreProducto(Long productoId, String nuevoNombre);
    List<Franquicia> obtenerFranquicias();


}