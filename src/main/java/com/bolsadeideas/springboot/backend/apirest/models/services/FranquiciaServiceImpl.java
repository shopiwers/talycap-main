package com.bolsadeideas.springboot.backend.apirest.models.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.bolsadeideas.springboot.backend.apirest.models.dao.IFranquiciaDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.ISucursalDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.IInventarioDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.IProductoDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursales;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Inventario;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;
import com.bolsadeideas.springboot.backend.apirest.models.services.dto.ProductoSucursalDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FranquiciaServiceImpl implements IFranquiciaService {

    @Autowired
    private IFranquiciaDao franquiciaDao;

    @Autowired
    private ISucursalDao sucursalDao;

    @Autowired
    private IInventarioDao inventarioDao;

    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional
    public Franquicia crearFranquicia(Franquicia franquicia) {
        return franquiciaDao.save(franquicia);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Franquicia> buscarFranquicia(Long id) {
        return franquiciaDao.findById(id);
    }

    @Override
    @Transactional
    public Sucursales agregarSucursal(Long franquiciaId, Sucursales sucursal) {
        Franquicia f = franquiciaDao.findById(franquiciaId)
            .orElseThrow(() -> new NoSuchElementException("Franquicia no encontrada"));
        sucursal.setFranquicia(f);
        return sucursalDao.save(sucursal);
    }

    @Override
    @Transactional
    public Sucursales actualizarNombreSucursal(Long sucursalId, String nuevoNombre) {
        Sucursales s = sucursalDao.findById(sucursalId)
            .orElseThrow(() -> new NoSuchElementException("Sucursal no encontrada"));
        s.setNombre(nuevoNombre);
        return sucursalDao.save(s);
    }

    @Override
    @Transactional
    public Franquicia actualizarNombreFranquicia(Long franquiciaId, String nuevoNombre) {
        Franquicia f = franquiciaDao.findById(franquiciaId)
            .orElseThrow(() -> new NoSuchElementException("Franquicia no encontrada"));
        f.setNombre(nuevoNombre);
        return franquiciaDao.save(f);
    }

    @Override
    @Transactional
    public Inventario agregarProductoASucursal(Long sucursalId, Long productoId, Integer stock) {
        Sucursales s = sucursalDao.findById(sucursalId)
            .orElseThrow(() -> new NoSuchElementException("Sucursal no encontrada"));
        Producto p = productoDao.findById(productoId)
            .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        Optional<Inventario> exist = inventarioDao.findBySucursalIdAndProductoId(sucursalId, productoId);
        if (exist.isPresent()) {
            Inventario inv = exist.get();
            inv.setStock(inv.getStock() + (stock != null ? stock : 0));
            return inventarioDao.save(inv);
        }

        Inventario inv = new Inventario();
        inv.setSucursal(s);
        inv.setProducto(p);
        inv.setStock(stock != null ? stock : 0);
        return inventarioDao.save(inv);
    }

    @Override
    @Transactional
    public void eliminarProductoDeSucursal(Long sucursalId, Long productoId) {
        Optional<Inventario> opt = inventarioDao.findBySucursalIdAndProductoId(sucursalId, productoId);
        opt.ifPresent(inventarioDao::delete);
    }

    @Override
    @Transactional
    public Inventario actualizarStock(Long sucursalId, Long productoId, Integer nuevoStock) {
        Inventario inv = inventarioDao.findBySucursalIdAndProductoId(sucursalId, productoId)
            .orElseThrow(() -> new NoSuchElementException("Producto en inventario no encontrado"));
        inv.setStock(nuevoStock);
        return inventarioDao.save(inv);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoSucursalDTO> productosMaxStockPorFranquicia(Long franquiciaId) {
        Franquicia f = franquiciaDao.findById(franquiciaId)
            .orElseThrow(() -> new NoSuchElementException("Franquicia no encontrada"));

        List<Sucursales> sucursales = sucursalDao.findByFranquiciaId(franquiciaId);

        List<ProductoSucursalDTO> resultado = new ArrayList<>();

        for (Sucursales s : sucursales) {
            List<Inventario> invs = inventarioDao.findBySucursalId(s.getId());
            if (invs.isEmpty()) continue;
            int max = invs.stream().mapToInt(Inventario::getStock).max().orElse(0);
            List<Inventario> maxInv = invs.stream().filter(i -> i.getStock() == max).collect(Collectors.toList());
            for (Inventario i : maxInv) {
                Producto p = i.getProducto();
                ProductoSucursalDTO dto = new ProductoSucursalDTO();
                dto.setSucursalId(s.getId());
                dto.setSucursalNombre(s.getNombre());
               // dto.setProductoId(p.getP());
                dto.setProductoNombre(p.getNombreProducto());
                dto.setStock(i.getStock());
                resultado.add(dto);
            }
        }
        return resultado;
    }

    @Transactional
    @Override
    public Producto actualizarNombreProducto(Long productoId, String nuevoNombre) {
        Producto p = productoDao.findById(productoId)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        p.setNombreProducto(nuevoNombre);
        return productoDao.save(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Franquicia> obtenerFranquicias() {
        return franquiciaDao.findAll();
    }



    @Override
    public List<ProductoSucursalDTO> productosMaxStockPorFranquiciaReactivo(Long franquiciaId) {
        Optional<Franquicia> opt = franquiciaDao.findById(franquiciaId);
        if (!opt.isPresent()) {
            return Collections.emptyList();
        }
        Franquicia franquicia = opt.get();
        List<ProductoSucursalDTO> result = new ArrayList<>();
        for (Sucursales suc : franquicia.getSucursales()) {
            Inventario top = inventarioDao.findTopBySucursal_IdOrderByStockDesc(suc.getId());
            if (top != null) {
                ProductoSucursalDTO dto = new ProductoSucursalDTO();
                dto.setProductoId(top.getProducto().getId());
                dto.setProductoNombre(top.getProducto().getNombreProducto());
                dto.setSucursalId(suc.getId());
                dto.setSucursalNombre(suc.getNombre());
                dto.setStock(top.getStock());
                result.add(dto);
            }
        }
        return result;
    }
}
