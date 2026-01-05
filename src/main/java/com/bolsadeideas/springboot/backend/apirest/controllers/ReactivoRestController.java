package com.bolsadeideas.springboot.backend.apirest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.*;
import com.bolsadeideas.springboot.backend.apirest.models.services.IFranquiciaService;
import com.bolsadeideas.springboot.backend.apirest.models.services.dto.ProductoSucursalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/reactiva")
public class ReactivoRestController {
    @Autowired
    private IFranquiciaService franquiciaService;

    // ---------------- FRANQUICIAS ----------------

    @PostMapping
    public Mono<ResponseEntity<Franquicia>> crearFranquicia(
            @RequestBody Franquicia franquicia) {

        return Mono.fromCallable(() -> franquiciaService.crearFranquicia(franquicia))
                .subscribeOn(Schedulers.elastic())
                .map(f -> ResponseEntity.status(HttpStatus.CREATED).body(f));
    }

    @PutMapping("/{id}/nombre")
    public Mono<ResponseEntity<Franquicia>> actualizarNombreFranquicia(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String nuevoNombre = body.get("nombre");

        return Mono.fromCallable(() ->
                        franquiciaService.actualizarNombreFranquicia(id, nuevoNombre))
                .subscribeOn(Schedulers.elastic())
                .map(ResponseEntity::ok);
    }

    @Transactional
    @GetMapping
    public Flux<Franquicia> obtenerFranquicias() {

        return Mono.fromCallable(() -> franquiciaService.obtenerFranquicias())
                .subscribeOn(Schedulers.elastic())
                .flatMapMany(Flux::fromIterable);
    }

   // @Transactional
//    public List<Franquicia> obtenerFranquiciasReactiva() {
//        List<Franquicia> lista = franquiciaService.obtenerFranquicias();
//        lista.forEach(f -> f.getSucursales().size());
//        return lista;
//    }


    // ---------------- SUCURSALES ----------------

    @PostMapping("/{franquiciaId}/sucursales")
    public Mono<ResponseEntity<Sucursales>> agregarSucursal(
            @PathVariable Long franquiciaId,
            @RequestBody Sucursales sucursal) {

        return Mono.fromCallable(() ->
                        franquiciaService.agregarSucursal(franquiciaId, sucursal))
                .subscribeOn(Schedulers.elastic())
                .map(s -> ResponseEntity.status(HttpStatus.CREATED).body(s));
    }

    @PutMapping("/sucursales/{sucursalId}/nombre")
    public Mono<ResponseEntity<Sucursales>> actualizarNombreSucursal(
            @PathVariable Long sucursalId,
            @RequestBody Map<String, String> body) {

        String nuevoNombre = body.get("nombre");

        return Mono.fromCallable(() ->
                        franquiciaService.actualizarNombreSucursal(sucursalId, nuevoNombre))
                .subscribeOn(Schedulers.elastic())
                .map(ResponseEntity::ok);
    }

    // ---------------- PRODUCTOS / INVENTARIO ----------------

    @PostMapping("/sucursales/{sucursalId}/productos")
    public Mono<ResponseEntity<Inventario>> agregarProductoASucursal(
            @PathVariable Long sucursalId,
            @RequestBody Map<String, Object> body) {

        Long productoId = Long.valueOf(String.valueOf(body.get("productoId")));
        Integer stock = body.get("stock") != null
                ? Integer.valueOf(String.valueOf(body.get("stock")))
                : 0;

        return Mono.fromCallable(() ->
                        franquiciaService.agregarProductoASucursal(
                                sucursalId, productoId, stock))
                .subscribeOn(Schedulers.elastic())
                .map(inv -> ResponseEntity.status(HttpStatus.CREATED).body(inv));
    }

    @DeleteMapping("/sucursales/{sucursalId}/productos/{productoId}")
    public Mono<ResponseEntity<Void>> eliminarProductoDeSucursal(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId) {

        return Mono.fromRunnable(() ->
                        franquiciaService.eliminarProductoDeSucursal(sucursalId, productoId))
                .subscribeOn(Schedulers.elastic())
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PatchMapping("/sucursales/{sucursalId}/productos/{productoId}/stock")
    public Mono<ResponseEntity<Inventario>> actualizarStock(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId,
            @RequestBody Map<String, Object> body) {

        Integer nuevoStock = Integer.valueOf(String.valueOf(body.get("stock")));

        return Mono.fromCallable(() ->
                        franquiciaService.actualizarStock(
                                sucursalId, productoId, nuevoStock))
                .subscribeOn(Schedulers.elastic())
                .map(ResponseEntity::ok);
    }

    // ---------------- CONSULTAS ----------------

    @GetMapping("/{franquiciaId}/max-stock")
    public Flux<ProductoSucursalDTO> productosMaxStockPorFranquicia(
            @PathVariable Long franquiciaId) {

        return Mono.fromCallable(() ->
                        franquiciaService.productosMaxStockPorFranquicia(franquiciaId))
                .subscribeOn(Schedulers.elastic())
                .flatMapMany(Flux::fromIterable);
    }

    @PutMapping("/productos/{productoId}/nombre")
    public Mono<ResponseEntity<Producto>> actualizarNombreProducto(
            @PathVariable Long productoId,
            @RequestBody Map<String, String> body) {

        String nuevoNombre = body.get("nombre");

        return Mono.fromCallable(() ->
                        franquiciaService.actualizarNombreProducto(
                                productoId, nuevoNombre))
                .subscribeOn(Schedulers.elastic())
                .map(ResponseEntity::ok);
    }

    // ---------------- REACTIVO EXPLÍCITO ----------------

    @GetMapping("/{franquiciaId}/max-stock-reactiva")
    public Flux<ProductoSucursalDTO> productosMaxStockPorFranquiciaReact(
            @PathVariable Long franquiciaId) {

        return Mono.fromCallable(() ->
                        franquiciaService.productosMaxStockPorFranquiciaReactivo(franquiciaId))
                .subscribeOn(Schedulers.elastic())
                .flatMapMany(Flux::fromIterable);
    }


}
