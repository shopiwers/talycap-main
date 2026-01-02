package com.bolsadeideas.springboot.backend.apirest.models.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IProductoDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private IProductoDao productoDao;

    @InjectMocks
    private ProductoServiceImp productoService;

    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombreProducto("Producto 1");

        producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombreProducto("Producto 2");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Producto> productos = Arrays.asList(producto1, producto2);
        when(productoDao.findAll()).thenReturn(productos);

        // Act
        List<Producto> result = productoService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Producto 1", result.get(0).getNombreProducto());
        assertEquals("Producto 2", result.get(1).getNombreProducto());
        verify(productoDao, times(1)).findAll();
    }

    @Test
    void testSave() {
        // Arrange
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombreProducto("Nuevo Producto");
        when(productoDao.save(nuevoProducto)).thenReturn(nuevoProducto);

        // Act
        Producto result = productoService.save(nuevoProducto);

        // Assert
        assertNotNull(result);
        assertEquals("Nuevo Producto", result.getNombreProducto());
        verify(productoDao, times(1)).save(nuevoProducto);
    }
}