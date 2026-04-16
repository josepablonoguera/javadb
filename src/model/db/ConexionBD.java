package model.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Administra la creación de conexiones JDBC a MySQL leyendo valores desde db.properties.
 */
public class ConexionBD {

    private static final String PROPERTIES_FILE = "db.properties";

    private final String url;
    private final String usuario;
    private final String password;

    public ConexionBD() {
        Properties properties = cargarPropiedades();
        this.url = properties.getProperty("db.url", "jdbc:mysql://localhost:3306/javadb");
        this.usuario = properties.getProperty("db.user", "root");
        this.password = properties.getProperty("db.password", "");
    }

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }

    private Properties cargarPropiedades() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalStateException("No se encontró el archivo " + PROPERTIES_FILE + " en resources/");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar la configuración de base de datos.", e);
        }

        return properties;
    }
}
