package com.example.IngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.IngSoftware.model.Pedido;
import com.example.IngSoftware.repositories.PedidoRepository;
import com.example.IngSoftware.services.PedidoService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testGetPedidoById() {
        // Configuración del mock
        Pedido mockPedido = new Pedido();
        mockPedido.setId(1L);
        mockPedido.setFecha("2024-12-12");
        mockPedido.setEstado("Pendiente");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(mockPedido));

        // Ejecutar el método de prueba
        Pedido result = pedidoService.buscarPorId(1L);

        // Verificar que el resultado no sea nulo
        assertNotNull(result);
    }

    @Test
    void testGuardarPedido() {
        // Configuración del mock
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setFecha("2024-12-12");
        nuevoPedido.setEstado("En Proceso");

        when(pedidoRepository.save(nuevoPedido)).thenReturn(nuevoPedido);

        // Ejecutar el método de prueba
        Pedido result = pedidoService.crearPedido(nuevoPedido);

        // Verificar que el pedido guardado no sea nulo
        assertNotNull(result);
    }
}
