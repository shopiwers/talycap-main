
package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "inventarios")
public class Inventario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @JsonIgnore
    private Sucursales sucursal;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer stock;

    public Inventario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Sucursales getSucursal() { return sucursal; }
    public void setSucursal(Sucursales sucursal) { this.sucursal = sucursal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    private static final long serialVersionUID = 1L;
}