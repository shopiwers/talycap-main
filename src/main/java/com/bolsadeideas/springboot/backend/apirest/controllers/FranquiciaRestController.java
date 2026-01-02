package com.bolsadeideas.springboot.backend.apirest.controllers;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.bolsadeideas.springboot.backend.apirest.models.services.IFranquiciaService;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursales;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Inventario;
import com.bolsadeideas.springboot.backend.apirest.models.services.dto.ProductoSucursalDTO;

import java.util.List;
import java.util.Map;


import reactor.core.scheduler.Schedulers;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;



@RestController
@RequestMapping("/api/franquicias")
public class FranquiciaRestController {

    @Autowired
    private IFranquiciaService franquiciaService;

    @PostMapping
    public ResponseEntity<Franquicia> crearFranquicia(@RequestBody Franquicia franquicia) {
        Franquicia f = franquiciaService.crearFranquicia(franquicia);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/nombre")
    public ResponseEntity<Franquicia> actualizarNombreFranquicia(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoNombre = body.get("nombre");
        Franquicia f = franquiciaService.actualizarNombreFranquicia(id, nuevoNombre);
        return ResponseEntity.ok(f);
    }

    @PostMapping("/{franquiciaId}/sucursales")
    public ResponseEntity<Sucursales> agregarSucursal(@PathVariable Long franquiciaId, @RequestBody Sucursales sucursal) {
        Sucursales s = franquiciaService.agregarSucursal(franquiciaId, sucursal);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @PutMapping("/sucursales/{sucursalId}/nombre")
    public ResponseEntity<Sucursales> actualizarNombreSucursal(@PathVariable Long sucursalId, @RequestBody Map<String, String> body) {
        String nuevoNombre = body.get("nombre");
        Sucursales s = franquiciaService.actualizarNombreSucursal(sucursalId, nuevoNombre);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/sucursales/{sucursalId}/productos")
    public ResponseEntity<Inventario> agregarProductoASucursal(
            @PathVariable Long sucursalId,
            @RequestBody Map<String, Object> body) {
        Long productoId = Long.valueOf(String.valueOf(body.get("productoId")));
        Integer stock = body.get("stock") != null ? Integer.valueOf(String.valueOf(body.get("stock"))) : 0;
        Inventario inv = franquiciaService.agregarProductoASucursal(sucursalId, productoId, stock);
        return new ResponseEntity<>(inv, HttpStatus.CREATED);
    }

    @DeleteMapping("/sucursales/{sucursalId}/productos/{productoId}")
    public ResponseEntity<Void> eliminarProductoDeSucursal(@PathVariable Long sucursalId, @PathVariable Long productoId) {
        franquiciaService.eliminarProductoDeSucursal(sucursalId, productoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/sucursales/{sucursalId}/productos/{productoId}/stock")
    public ResponseEntity<Inventario> actualizarStock(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId,
            @RequestBody Map<String, Object> body) {
        Integer nuevoStock = Integer.valueOf(String.valueOf(body.get("stock")));
        Inventario inv = franquiciaService.actualizarStock(sucursalId, productoId, nuevoStock);
        return ResponseEntity.ok(inv);
    }

    @GetMapping("/{franquiciaId}/max-stock")
    public ResponseEntity<List<ProductoSucursalDTO>> productosMaxStockPorFranquicia(@PathVariable Long franquiciaId) {
        List<ProductoSucursalDTO> lista = franquiciaService.productosMaxStockPorFranquicia(franquiciaId);
        return ResponseEntity.ok(lista);
    }


    @PutMapping("/productos/{productoId}/nombre")
    public ResponseEntity<Producto> actualizarNombreProducto(
            @PathVariable Long productoId,
            @RequestBody java.util.Map<String, String> body) {
        String nuevoNombre = body.get("nombre");
        Producto p = franquiciaService.actualizarNombreProducto(productoId, nuevoNombre);
        return ResponseEntity.ok(p);
    }


    @GetMapping
    public ResponseEntity<List<Franquicia>> obtenerFranquicias() {
        List<Franquicia> lista = franquiciaService.obtenerFranquicias();
        return ResponseEntity.ok(lista);
    }



    @GetMapping("/{franquiciaId}/max-stock-reactiva")
    public ResponseEntity<List<ProductoSucursalDTO>> productosMaxStockPorFranquiciaReact(
            @PathVariable Long franquiciaId) {

        List<ProductoSucursalDTO> lista =
                franquiciaService.productosMaxStockPorFranquiciaReactivo(franquiciaId);

        return ResponseEntity.ok(lista);
    }




}
