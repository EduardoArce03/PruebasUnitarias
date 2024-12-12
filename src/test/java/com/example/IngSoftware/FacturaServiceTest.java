package com.example.IngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.IngSoftware.model.Factura;
import com.example.IngSoftware.repositories.FacturaRepository;
import com.example.IngSoftware.services.FacturaService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    @Test
    void testGetFacturaById() {
        // Configuración del mock
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(new Factura()));

        // Ejecutar el método de prueba
        Factura result = facturaService.buscarPorId(1L);

        // Verificar que el resultado no sea nulo
        assertNotNull(result);
    }
    // Test para guardar una factura
}


