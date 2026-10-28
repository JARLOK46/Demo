package com.example.demo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
@Repository
public class UsuarioRepository {

    private final Map<String, Usuario> baseDeDatos = new HashMap<>();

    // Guardar un usuario
    public Usuario save(Usuario usuario) {
        baseDeDatos.put(usuario.getId(), usuario);
        return usuario;
    }

    // Encontrar usuario por ID
    public Usuario findById(String id) {
        return baseDeDatos.get(id);
    }

    // Listar todos los usuarios
    public List<Usuario> findAll() {
        return new ArrayList<>(baseDeDatos.values());
    }

    // Filtrar por nombre
    public List<Usuario> findByNombreContaining(String nombre ) {
        return baseDeDatos.values().stream().filter(usuario -> usuario.getNombre().toLowerCase().contains(nombre.toLowerCase())).collect(Collectors.toList());
    }

    // Eliminar un usuario
    public void deleteById(String id) {
        baseDeDatos.remove(id);
    }

    //Actualizar usuario
    public Usuario update(Usuario usuario) {
        if (baseDeDatos.containsKey(usuario.getId())) {
            baseDeDatos.put(usuario.getId(), usuario);
            return usuario;
        }
        return null;
    }
}
