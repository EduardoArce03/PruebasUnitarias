package com.example.IngSoftware;

import com.example.IngSoftware.model.Almacen;
import com.example.IngSoftware.repositories.AlmacenRepository;
import com.example.IngSoftware.services.AlmacenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlmacenServiceTest {
    @Mock
    private AlmacenRepository almacenRepository;
    @InjectMocks
    private AlmacenService almacenService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testListarAlmacenes(){
        List<Almacen> almacenesMock = Arrays.asList(
                new Almacen(1L, "Ubicacion 1", "Tipo A"),
                new Almacen(2L, "Ubicacion 2", "Tipo B")
        );
        when(almacenRepository.findAll()).thenReturn(almacenesMock);

        List<Almacen> resultado = almacenService.listarAlmacenes();
        assertEquals(2, resultado.size());
        assertEquals("Ubicacion 1", resultado.get(0).getUbicacion());
    }
    @Test
    void testBuscarPorId_Exitoso() {
        // Arrange
        Almacen almacenMock = new Almacen(1L, "Ubicación A", "Tipo A");
        when(almacenRepository.findById(1L)).thenReturn(Optional.of(almacenMock));

        // Act
        Almacen result = almacenService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Ubicación A", result.getUbicacion());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        // Arrange
        when(almacenRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> almacenService.buscarPorId(1L));
        assertEquals("Almacén no encontrado con ID: 1", exception.getMessage());
    }

    @Test
    void testCrearAlmacen() {
        // Arrange
        Almacen almacenMock = new Almacen(null, "Ubicación A", "Tipo A");
        Almacen almacenGuardado = new Almacen(1L, "Ubicación A", "Tipo A");
        when(almacenRepository.save(almacenMock)).thenReturn(almacenGuardado);

        // Act
        Almacen result = almacenService.crearAlmacen(almacenMock);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Ubicación A", result.getUbicacion());
    }

    @Test
    void testActualizarAlmacen() {
        // Arrange
        Almacen almacenExistente = new Almacen(1L, "Ubicación A", "Tipo A");
        Almacen almacenActualizado = new Almacen(1L, "Ubicación Actualizada", "Tipo B");
        when(almacenRepository.findById(1L)).thenReturn(Optional.of(almacenExistente));
        when(almacenRepository.save(almacenExistente)).thenReturn(almacenActualizado);

        // Act
        Almacen result = almacenService.actualizarAlmacen(1L, almacenActualizado);

        // Assert
        assertEquals("Ubicación Actualizada", result.getUbicacion());
        assertEquals("Tipo B", result.getTipo());
    }

    @Test
    void testEliminarAlmacen_Exitoso() {
        // Arrange
        when(almacenRepository.existsById(1L)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> almacenService.eliminarAlmacen(1L));

        // Verify
        verify(almacenRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarAlmacen_NoEncontrado() {
        // Arrange
        when(almacenRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> almacenService.eliminarAlmacen(1L));
        assertEquals("Almacén no encontrado con ID: 1", exception.getMessage());
    }
}
