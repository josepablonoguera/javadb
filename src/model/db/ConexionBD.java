package model.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        try (InputStream input = abrirArchivoConfiguracion()) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar la configuración de base de datos.", e);
        }

        return properties;
    }

    private InputStream abrirArchivoConfiguracion() throws IOException {
        InputStream classpathStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (classpathStream != null) {
            return classpathStream;
        }

        Path[] candidatos = {
            Paths.get("resources", PROPERTIES_FILE),
            Paths.get(PROPERTIES_FILE)
        };

        for (Path candidato : candidatos) {
            if (Files.exists(candidato)) {
                return Files.newInputStream(candidato);
            }
        }

        throw new IllegalStateException(
                "No se encontró " + PROPERTIES_FILE
                + " ni en el classpath ni en rutas locales: resources/" + PROPERTIES_FILE + " o ./" + PROPERTIES_FILE
        );
    }
}
