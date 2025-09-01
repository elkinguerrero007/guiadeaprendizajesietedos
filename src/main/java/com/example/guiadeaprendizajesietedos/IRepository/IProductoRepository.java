package com.example.guiadeaprendizajesietedos.IRepository;

import org.springframework.data.repository.CrudRepository;

import com.example.guiadeaprendizajesietedos.entities.Producto;


public interface IProductoRepository extends CrudRepository<Producto, Integer> {

}
