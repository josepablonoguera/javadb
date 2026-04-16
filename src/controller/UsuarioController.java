package controller;

import java.util.List;
import model.dao.UsuarioDAO;
import model.entity.Usuario;
import view.UsuarioView;

/**
 * Controla el flujo entre la vista de usuario y la capa de acceso a datos.
 */
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final UsuarioView usuarioView;

    public UsuarioController(UsuarioDAO usuarioDAO, UsuarioView usuarioView) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioView = usuarioView;
    }

    public void iniciar() {
        int opcion;

        do {
            opcion = usuarioView.mostrarMenuYLeerOpcion();

            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    buscarUsuarioPorId();
                    break;
                case 4:
                    actualizarUsuario();
                    break;
                case 5:
                    eliminarUsuario();
                    break;
                case 0:
                    usuarioView.mostrarMensaje("Saliendo del sistema...");
                    break;
                default:
                    usuarioView.mostrarError("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);

        usuarioView.cerrar();
    }

    private void crearUsuario() {
        Usuario usuario = usuarioView.solicitarDatosNuevoUsuario();

        if (usuario == null) {
            usuarioView.mostrarError("Datos inválidos. No se pudo crear el usuario.");
            return;
        }

        boolean creado = usuarioDAO.insertar(usuario);
        if (creado) {
            usuarioView.mostrarMensaje("Usuario creado exitosamente.");
        } else {
            usuarioView.mostrarError("No se pudo crear el usuario.");
        }
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listar();
        usuarioView.mostrarListaUsuarios(usuarios);
    }

    private void buscarUsuarioPorId() {
        int id = usuarioView.solicitarId("Ingrese el ID del usuario a buscar: ");
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario != null) {
            usuarioView.mostrarUsuario(usuario);
        } else {
            usuarioView.mostrarError("No existe un usuario con ID " + id + ".");
        }
    }

    private void actualizarUsuario() {
        int id = usuarioView.solicitarId("Ingrese el ID del usuario a actualizar: ");
        Usuario existente = usuarioDAO.buscarPorId(id);

        if (existente == null) {
            usuarioView.mostrarError("No existe un usuario con ID " + id + ".");
            return;
        }

        Usuario actualizado = usuarioView.solicitarDatosActualizacion(existente);
        if (actualizado == null) {
            usuarioView.mostrarError("Datos inválidos. No se actualizó el usuario.");
            return;
        }

        boolean ok = usuarioDAO.actualizar(actualizado);
        if (ok) {
            usuarioView.mostrarMensaje("Usuario actualizado correctamente.");
        } else {
            usuarioView.mostrarError("No se pudo actualizar el usuario.");
        }
    }

    private void eliminarUsuario() {
        int id = usuarioView.solicitarId("Ingrese el ID del usuario a eliminar: ");
        boolean eliminado = usuarioDAO.eliminar(id);

        if (eliminado) {
            usuarioView.mostrarMensaje("Usuario eliminado correctamente.");
        } else {
            usuarioView.mostrarError("No se pudo eliminar el usuario o no existe.");
        }
    }
}
