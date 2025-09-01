package com.example.guiadeaprendizajesietedos.IService;

import java.util.List;
import java.util.Optional;

import com.example.guiadeaprendizajesietedos.entities.Producto;

public interface IProductService {

    public List<Producto> getAll();

    public Optional<Producto> getById(Integer id);

    public Producto save(Producto producto);
    public void delete(Integer id);



}
