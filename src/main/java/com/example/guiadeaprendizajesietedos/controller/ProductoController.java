package com.example.guiadeaprendizajesietedos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.guiadeaprendizajesietedos.entities.Producto;
import com.example.guiadeaprendizajesietedos.service.ProductoService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar la entidad Producto.
 * Expone endpoints para realizar las operaciones CRUD (Crear, Leer, Actualizar y Eliminar).
 */
@RestController
@RequestMapping("/api/producto")
@CrossOrigin("*") // Permite llamadas desde cualquier origen (CORS habilitado)
public class ProductoController {

    @Autowired
    ProductoService service; // Servicio que contiene la lógica de negocio para Producto

    /**
     * Obtener todos los productos
     * Método GET → /api/producto/getAll
     * @return Lista de productos
     */
    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    List<Producto> getAll(){
      return service.getAll();
    }

    /**
     * Obtener un producto por su ID
     * Método GET → /api/producto/getById/{id}
     * @param id identificador del producto
     * @return Producto encontrado o vacío si no existe
     */
    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Producto> getById(@PathVariable int id){
        return service.getById(id);
    }

    /**
     * Guardar un nuevo producto
     * Método POST → /api/producto/save
     * @param producto objeto producto recibido en el cuerpo de la petición
     * @return Producto creado
     */
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(@RequestBody Producto producto){   
        return service.save(producto);
    }

    /**
     * Actualizar un producto existente
     * Método PUT → /api/producto/update/{id}
     * @param id identificador del producto a actualizar
     * @param producto objeto con los nuevos valores
     * @return Producto actualizado
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Producto> update(@PathVariable Integer id,@RequestBody Producto producto){
        Optional<Producto> op = service.getById(id);
        Producto update = op.get();

        if (!op.isEmpty()){
            update.setTitulo(producto.getTitulo());
            update.setImagen(producto.getImagen());
            update.setNombre(producto.getNombre());
            update.setPrecio(producto.getPrecio());
            update.setDescripcion(producto.getDescripcion());

            return Optional.ofNullable(service.save(update));
        }
        return Optional.ofNullable(producto);
    }

    /**
     * Eliminar un producto por su ID
     * Método DELETE → /api/producto/delete/{id}
     * @param id identificador del producto a eliminar
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

}
