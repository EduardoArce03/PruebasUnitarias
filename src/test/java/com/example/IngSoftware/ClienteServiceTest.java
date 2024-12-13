package com.example.IngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.IngSoftware.model.Cliente;
import com.example.IngSoftware.repositories.ClienteRepository;
import com.example.IngSoftware.services.ClienteService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testGetClienteById() {
        // Configuración del mock
        Cliente mockCliente = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setNombre("Juan Hidalgo Pérez");
        mockCliente.setDireccion("Calle Ficticia 98765432");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(mockCliente));

        // Ejecutar el método de prueba
        Cliente result = clienteService.buscarPorId(1L);

        // Verificar que el resultado no sea nulo
        assertNotNull(result, "El cliente no debería ser nulo");

        // Verificar atributos del cliente
        assertEquals(1L, result.getId(), "El ID del cliente no coincide");
        assertEquals("Juan Hidalgo Pérez", result.getNombre(), "El nombre del cliente no coincide");
        assertEquals("Calle Ficticia 98765432", result.getDireccion(), "La dirección del cliente no coincide");
    }

    @Test
    void testGuardarCliente() {
        // Configuración del mock
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre("María Eduarda Lopez");
        nuevoCliente.setDireccion("Avenida Ejemplo 456123456");

        when(clienteRepository.save(nuevoCliente)).thenReturn(nuevoCliente);

        // Ejecutar el método de prueba
        Cliente result = clienteService.crearCliente(nuevoCliente);

        // Verificar que el cliente guardado no sea nulo
        assertNotNull(result, "El cliente guardado no debería ser nulo");

        // Verificar atributos del cliente guardado
        assertEquals("María Eduarda Lopez", result.getNombre(), "El nombre del cliente guardado no coincide");
        assertEquals("Avenida Ejemplo 456123456", result.getDireccion(), "La dirección del cliente guardado no coincide");
    }
}
