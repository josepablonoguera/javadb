package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.db.ConexionBD;
import model.entity.Usuario;

/**
 * DAO para encapsular operaciones CRUD de Usuario.
 */
public class UsuarioDAO {

    private final ConexionBD conexionBD;

    public UsuarioDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public boolean insertar(Usuario usuario) {
        final String sql = "INSERT INTO usuarios(nombre, correo) VALUES (?, ?)";

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listar() {
        final String sql = "SELECT id, nombre, correo FROM usuarios ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        final String sql = "SELECT id, nombre, correo FROM usuarios WHERE id = ?";

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Usuario usuario) {
        final String sql = "UPDATE usuarios SET nombre = ?, correo = ? WHERE id = ?";

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setInt(3, usuario.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        final String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
