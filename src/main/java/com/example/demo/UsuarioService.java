package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        initSampleData();
    }

    private void initSampleData() {
        save(new Usuario("Juan Pérez", "juan@example.com", 30));
        save(new Usuario("María López", "maria@example.com", 25));
        save(new Usuario("Carlos Ruiz", "carlos@example.com", 40));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(String id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombreContaining(nombre);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.update(usuario);
    }

    public Usuario patch(String id, Map<String, Object> updates) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario != null) {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "nombre" -> usuario.setNombre((String) value);
                    case "email" -> usuario.setEmail((String) value);
                    case "edad" -> usuario.setEdad(((Number) value).intValue());
                    default -> {
                        // Ignore fields not supported by the partial update contract.
                    }
                }
            });
            return usuarioRepository.update(usuario);
        }
        return null;
    }

    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }
}
