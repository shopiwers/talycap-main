package com.bolsadeideas.springboot.backend.apirest.models.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductoTest {

    @Test
    void testProductoCreation() {
        // Arrange & Act
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombreProducto("Test Producto");

        // Assert
        assertThat(producto.getId()).isEqualTo(1L);
        assertThat(producto.getNombreProducto()).isEqualTo("Test Producto");
    }

    @Test
    void testProductoDefaultConstructor() {
        // Arrange & Act
        Producto producto = new Producto();

        // Assert
        assertThat(producto.getId()).isNull();
        assertThat(producto.getNombreProducto()).isNull();
    }
}