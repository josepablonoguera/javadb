package view;

import java.util.List;
import java.util.Scanner;
import model.entity.Usuario;
import util.Validador;

/**
 * Vista de consola para interacción con el usuario final.
 */
public class UsuarioView {

    private final Scanner scanner;

    public UsuarioView() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenuYLeerOpcion() {
        System.out.println("\n===== MENÚ USUARIOS =====");
        System.out.println("1. Crear usuario");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Buscar usuario por ID");
        System.out.println("4. Actualizar usuario");
        System.out.println("5. Eliminar usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        String entrada = scanner.nextLine();
        return Validador.parseEntero(entrada, -1);
    }

    public Usuario solicitarDatosNuevoUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();

        if (!Validador.textoNoVacio(nombre) || !Validador.correoValido(correo)) {
            return null;
        }

        return new Usuario(nombre, correo);
    }

    public Usuario solicitarDatosActualizacion(Usuario usuarioActual) {
        System.out.println("Actualizando usuario: " + usuarioActual);

        System.out.print("Nuevo nombre (enter para mantener actual): ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Nuevo correo (enter para mantener actual): ");
        String correo = scanner.nextLine().trim();

        String nombreFinal = nombre.isEmpty() ? usuarioActual.getNombre() : nombre;
        String correoFinal = correo.isEmpty() ? usuarioActual.getCorreo() : correo;

        if (!Validador.textoNoVacio(nombreFinal) || !Validador.correoValido(correoFinal)) {
            return null;
        }

        return new Usuario(usuarioActual.getId(), nombreFinal, correoFinal);
    }

    public int solicitarId(String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        return Validador.parseEntero(entrada, -1);
    }

    public void mostrarListaUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("\n--- Lista de usuarios ---");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    public void mostrarUsuario(Usuario usuario) {
        System.out.println("Usuario encontrado: " + usuario);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }

    public void cerrar() {
        scanner.close();
    }
}
