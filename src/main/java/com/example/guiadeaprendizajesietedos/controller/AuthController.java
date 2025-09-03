package com.example.guiadeaprendizajesietedos.controller;

import com.example.guiadeaprendizajesietedos.entities.Usuario;
import com.example.guiadeaprendizajesietedos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para autenticación de usuarios.
 * Expone endpoints para registro e inicio de sesión.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*") // Permite llamadas desde cualquier origen
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registro de un nuevo usuario
     * Método POST → /api/auth/register
     * @param usuario objeto con username y password
     * @return usuario registrado
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        // Verificamos si ya existe el username
        Optional<Usuario> existente = usuarioService.findByUsername(usuario.getUsername());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre de usuario ya está en uso.");
        }

        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /**
     * Inicio de sesión
     * Método POST → /api/auth/login
     * @param usuario objeto con username y password
     * @return mensaje de éxito o error
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioService.findByUsername(usuario.getUsername());

        if (existente.isPresent() && existente.get().getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.ok("✅ Autenticación satisfactoria");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ Error en la autenticación");
        }
    }
}
