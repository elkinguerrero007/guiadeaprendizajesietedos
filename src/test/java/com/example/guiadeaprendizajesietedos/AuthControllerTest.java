package com.example.guiadeaprendizajesietedos;

import com.example.guiadeaprendizajesietedos.controller.AuthController;
import com.example.guiadeaprendizajesietedos.entities.Usuario;
import com.example.guiadeaprendizajesietedos.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthController authController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
        usuario.setPassword("1234");
    }

    @Test
    void testRegisterSuccess() {
        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.empty());
        when(usuarioService.save(usuario)).thenReturn(usuario);

        ResponseEntity<?> response = authController.register(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    void testRegisterUserAlreadyExists() {
        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        ResponseEntity<?> response = authController.register(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El nombre de usuario ya está en uso.", response.getBody());
        verify(usuarioService, never()).save(usuario);
    }

    @Test
    void testLoginSuccess() {
        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        ResponseEntity<?> response = authController.login(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("✅ Autenticación satisfactoria", response.getBody());
    }

    @Test
    void testLoginInvalidPassword() {
        Usuario wrongPasswordUser = new Usuario();
        wrongPasswordUser.setUsername("testuser");
        wrongPasswordUser.setPassword("wrong");

        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        ResponseEntity<?> response = authController.login(wrongPasswordUser);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("❌ Error en la autenticación", response.getBody());
    }

    @Test
    void testLoginUserNotFound() {
        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.login(usuario);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("❌ Error en la autenticación", response.getBody());
    }
}
