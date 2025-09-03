package com.example.guiadeaprendizajesietedos.controller;

import com.example.guiadeaprendizajesietedos.entities.Usuario;
import com.example.guiadeaprendizajesietedos.IService.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    // GET: listar todos
    @GetMapping
    public List<Usuario> getAll() {
        return service.getAll();
    }

    // GET: buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Optional<Usuario> usuario = service.getById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: crear usuario
    @PostMapping
    public Usuario save(@RequestBody Usuario usuario) {
        return service.save(usuario);
    }

    // PUT: actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> existing = service.getById(id);
        if (existing.isPresent()) {
            usuario.setId(id);
            return ResponseEntity.ok(service.save(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Usuario> existing = service.getById(id);
        if (existing.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: buscar por username
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> findByUsername(@PathVariable String username) {
        Optional<Usuario> usuario = service.findByUsername(username);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
