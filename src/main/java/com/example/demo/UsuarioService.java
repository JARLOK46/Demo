package com.example.demo;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;

        //Inicializamos algunos datos  de ejemplo
        initSampleDate();
    }

    private void initSampleDate() {
        save(new Usuario("Juan Perez", "juan@example.com", 30));
        save(new Usuario("Maria Lopez", "maria@example.com", 25));
        save(new Usuario("Carlos Ruiz", "carlos@example.com", 40));
    }

    // Crear un nuevo usuario
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Obtener un usuario por ID
    public Usuario findeById(String id) {
        return usuarioRepository.findById(id);
    }

    //Listar todos los usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    //Buscar por nombre
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombreContaining(nombre);
    }

    //Actualizar un usuario
    public Usuario update(Usuario usuario) {
        return usuarioRepository.update(usuario);
    }

    // Actualizacion parcial
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

    // Eliminar un usuario
    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }
}
