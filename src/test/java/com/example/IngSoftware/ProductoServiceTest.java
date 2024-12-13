package com.example.IngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.IngSoftware.model.Producto;
import com.example.IngSoftware.repositories.ProductoRepository;
import com.example.IngSoftware.services.ProductoService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testGetProductoById() {
        // Configuración del mock
        Producto mockProducto = new Producto();
        mockProducto.setId(1L);
        mockProducto.setNombre("Producto A");
        mockProducto.setPrecio(99.99);
        mockProducto.setStock(100);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(mockProducto));

        // Ejecutar el método de prueba
        Producto result = productoService.buscarPorId(1L);

        // Verificar que el resultado no sea nulo
        assertNotNull(result);
    }

    @Test
    void testGuardarProducto() {
        // Configuración del mock
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Producto B");
        nuevoProducto.setPrecio(149.99);
        nuevoProducto.setStock(200);

        when(productoRepository.save(nuevoProducto)).thenReturn(nuevoProducto);

        // Ejecutar el método de prueba
        Producto result = productoService.crearProducto(nuevoProducto);

        // Verificar que el producto guardado no sea nulo
        assertNotNull(result);
    }
}
