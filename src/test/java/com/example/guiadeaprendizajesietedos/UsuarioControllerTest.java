package com.example.guiadeaprendizajesietedos;

import com.example.guiadeaprendizajesietedos.controller.UsuarioController;
import com.example.guiadeaprendizajesietedos.entities.Usuario;
import com.example.guiadeaprendizajesietedos.IService.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private IUsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario createUsuario(Long id, String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername(username);
        usuario.setPassword(password);
        return usuario;
    }

    @Test
    void testGetAll() {
        Usuario usuario1 = createUsuario(1L, "user1", "pass1");
        Usuario usuario2 = createUsuario(2L, "user2", "pass2");

        when(usuarioService.getAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> result = usuarioController.getAll();

        assertEquals(2, result.size());
        verify(usuarioService, times(1)).getAll();
    }

    @Test
    void testGetByIdFound() {
        Usuario usuario = createUsuario(1L, "user1", "pass1");
        when(usuarioService.getById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.getById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("user1", response.getBody().getUsername());
    }

    @Test
    void testGetByIdNotFound() {
        when(usuarioService.getById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.getById(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testSave() {
        Usuario usuario = createUsuario(1L, "user1", "pass1");
        when(usuarioService.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioController.save(usuario);

        assertEquals("user1", result.getUsername());
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    void testUpdateFound() {
        Usuario usuario = createUsuario(1L, "user1", "pass1");
        when(usuarioService.getById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioService.save(usuario)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.update(1L, usuario);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    void testUpdateNotFound() {
        Usuario usuario = createUsuario(1L, "user1", "pass1");
        when(usuarioService.getById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.update(1L, usuario);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(usuarioService, times(0)).save(usuario);
    }

    @Test
    void testDeleteFound() {
        Usuario usuario = createUsuario(1L, "user1", "pass1");
        when(usuarioService.getById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioService).delete(1L);

        ResponseEntity<Void> response = usuarioController.delete(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(usuarioService, times(1)).delete(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(usuarioService.getById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = usuarioController.delete(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(usuarioService, times(0)).delete(1L);
    }

    @Test
    void testFindByUsernameFound() {
        Usuario usuario = createUsuario(1L, "user1", "pass1");
        when(usuarioService.findByUsername("user1")).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.findByUsername("user1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("user1", response.getBody().getUsername());
    }

    @Test
    void testFindByUsernameNotFound() {
        when(usuarioService.findByUsername("user1")).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.findByUsername("user1");

        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
