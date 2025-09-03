package com.example.guiadeaprendizajesietedos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guiadeaprendizajesietedos.IRepository.UsuarioRepository;
import com.example.guiadeaprendizajesietedos.IService.IUsuarioService;
import com.example.guiadeaprendizajesietedos.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> getAll() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    public Optional<Usuario> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
