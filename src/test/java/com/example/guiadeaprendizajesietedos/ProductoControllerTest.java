package com.example.guiadeaprendizajesietedos;

import com.example.guiadeaprendizajesietedos.entities.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para ProductoController
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // para convertir objetos en JSON

    @Test
    void testCrearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setTitulo("Producto Test");
        producto.setNombre("Camiseta");
        producto.setDescripcion("Camiseta de prueba");
        producto.setPrecio(25000.0);
        producto.setImagen("imagen.png");

        mockMvc.perform(post("/api/producto/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Camiseta"));
    }

    @Test
    void testListarProductos() throws Exception {
        mockMvc.perform(get("/api/producto/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testObtenerProductoPorId() throws Exception {
        // Se asume que existe un producto con ID 1 en la base de datos
        mockMvc.perform(get("/api/producto/getById/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setTitulo("Producto Actualizado");
        producto.setNombre("Camiseta Actualizada");
        producto.setDescripcion("Nueva descripción");
        producto.setPrecio(30000.0);
        producto.setImagen("nueva.png");

        mockMvc.perform(put("/api/producto/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Camiseta Actualizada"));
    }

    @Test
    void testEliminarProducto() throws Exception {
        mockMvc.perform(delete("/api/producto/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
