package com.example.guiadeaprendizajesietedos.IService;

import java.util.List;
import java.util.Optional;

import com.example.guiadeaprendizajesietedos.entities.Usuario;

public interface IUsuarioService {

    List<Usuario> getAll();

    Optional<Usuario> getById(Long id);

    Usuario save(Usuario usuario);

    void delete(Long id);

    // Método adicional para login
    Optional<Usuario> findByUsername(String username);
}
