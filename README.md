# Proyecto Base Java + Ant + MVC + DAO + MySQL

Proyecto base académico/profesional para construir aplicaciones Java de consola con arquitectura **MVC**, acceso a datos con **DAO**, conexión a **MySQL** con **JDBC** y automatización de compilación con **Apache Ant**.

## Estructura del proyecto

```text
.
├── build.xml
├── lib/
├── resources/
│   ├── db.properties
│   └── schema.sql
├── src/
│   ├── controller/
│   │   └── UsuarioController.java
│   ├── main/
│   │   └── Main.java
│   ├── model/
│   │   ├── dao/
│   │   │   └── UsuarioDAO.java
│   │   ├── db/
│   │   │   └── ConexionBD.java
│   │   └── entity/
│   │       └── Usuario.java
│   ├── util/
│   │   └── Validador.java
│   └── view/
│       └── UsuarioView.java
└── test/
```

## Requisitos

- JDK 11 o superior
- Apache Ant 1.10 o superior
- MySQL Server 8.x (o compatible)
- Driver JDBC `mysql-connector-j` (archivo JAR)

## Agregar mysql-connector-j.jar en `lib/`

1. Descarga `mysql-connector-j` desde la web oficial de MySQL.
2. Copia el archivo `.jar` dentro de la carpeta `lib/`.
3. Verifica que exista, por ejemplo:
   - `lib/mysql-connector-j.jar`

> El `build.xml` ya está preparado para incluir automáticamente todos los `.jar` dentro de `lib/`.

## Configuración de conexión

Edita `resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/javadb?useSSL=false&serverTimezone=UTC
db.user=root
db.password=1234
```

La clase `ConexionBD` primero busca `db.properties` en el classpath y, si no está disponible (por ejemplo, ciertas ejecuciones directas desde IDE), hace fallback a `resources/db.properties` o `./db.properties`.

## Crear base de datos

Ejecuta el script `resources/schema.sql` en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS javadb;
USE javadb;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE
);
```

## Compilar con Ant

```bash
ant clean compile
```

## Generar JAR

```bash
ant jar
```

El JAR quedará en:

- `build/jar/javadb-base.jar`

## Ejecutar

```bash
ant run
```

## Arquitectura y responsabilidades

- **Model (`model.entity`)**: representa los datos del dominio (`Usuario`).
- **DAO (`model.dao`)**: encapsula SQL y operaciones CRUD con JDBC (`UsuarioDAO`).
- **DB (`model.db`)**: centraliza la creación de conexiones (`ConexionBD`).
- **View (`view`)**: maneja la interacción por consola (`UsuarioView`).
- **Controller (`controller`)**: coordina flujo entre vista y DAO (`UsuarioController`).
- **Util (`util`)**: validaciones reutilizables (`Validador`).

Esta separación facilita mantenimiento, pruebas y evolución del proyecto.
