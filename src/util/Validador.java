package util;

/**
 * Utilidades simples de validación.
 */
public final class Validador {

    private Validador() {
    }

    public static boolean textoNoVacio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    public static boolean correoValido(String correo) {
        if (!textoNoVacio(correo)) {
            return false;
        }
        return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static int parseEntero(String valor, int valorPorDefecto) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return valorPorDefecto;
        }
    }
}
