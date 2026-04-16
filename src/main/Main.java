package main;

import controller.UsuarioController;
import model.dao.UsuarioDAO;
import model.db.ConexionBD;
import view.UsuarioView;

/**
 * Punto de entrada de la aplicación.
 */
public class Main {

    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
        UsuarioView usuarioView = new UsuarioView();
        UsuarioController usuarioController = new UsuarioController(usuarioDAO, usuarioView);

        usuarioController.iniciar();
    }
}
