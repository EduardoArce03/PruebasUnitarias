package com.example.IngSoftware;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.IngSoftware.model.Inventario;
import com.example.IngSoftware.model.Producto;
import com.example.IngSoftware.repositories.InventarioRepository;
import com.example.IngSoftware.services.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarInventario() {
        // Arrange
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto A");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto B");

        List<Inventario> inventarioMock = Arrays.asList(
                new Inventario(1L, producto1, 10),
                new Inventario(2L, producto2, 5)
        );

        when(inventarioRepository.findAll()).thenReturn(inventarioMock);

        // Act
        List<Inventario> result = inventarioService.listarInventario();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Producto A", result.get(0).getProducto().getNombre());
        assertEquals(10, result.get(0).getCantidad());
    }


    @Test
    void testAgregarProducto() {
        // Arrange
        Producto producto = new Producto(1L, "Producto A", 100.0, 50);
        Inventario nuevoInventario = new Inventario(null, producto, 15);
        Inventario inventarioGuardado = new Inventario(1L, producto, 15);
        when(inventarioRepository.save(nuevoInventario)).thenReturn(inventarioGuardado);

        // Act
        Inventario result = inventarioService.agregarProducto(nuevoInventario);

        // Assert
        assertNotNull(result.getId());
        assertEquals(15, result.getCantidad());
        assertEquals("Producto A", result.getProducto().getNombre());
    }


    @Test
    void testActualizarExistencias_Exitoso() {
        // Arrange
        Producto producto = new Producto(1L, "Producto A", 100.0, 50);
        Inventario inventarioExistente = new Inventario(1L, producto, 10);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioExistente));
        when(inventarioRepository.save(inventarioExistente)).thenReturn(inventarioExistente);

        // Act
        Inventario result = inventarioService.actualizarExistencias(1L, 20);

        // Assert
        assertEquals(20, result.getCantidad());
        assertEquals("Producto A", result.getProducto().getNombre());
    }


    @Test
    void testActualizarExistencias_NoEncontrado() {
        // Arrange
        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> inventarioService.actualizarExistencias(1L, 20));
        assertEquals("Producto no encontrado con ID: 1", exception.getMessage());
    }

    @Test
    void testEliminarProducto_Exitoso() {
        // Arrange
        when(inventarioRepository.existsById(1L)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> inventarioService.eliminarProducto(1L));

        // Verify
        verify(inventarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarProducto_NoEncontrado() {
        // Arrange
        when(inventarioRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> inventarioService.eliminarProducto(1L));
        assertEquals("Producto no encontrado con ID: 1", exception.getMessage());
    }
}
