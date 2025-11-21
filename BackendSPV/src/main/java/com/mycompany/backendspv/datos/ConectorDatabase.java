package com.mycompany.backendspv.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConectorDatabase {
    private static final Dotenv dotenv = Dotenv.configure()
        .directory("./BackendSPV")  // Importante: ruta del backend
        .ignoreIfMissing()
        .load();

    private static final String URL = String.format(
        "jdbc:mysql://%s:%s/%s",
        dotenv.get("DB_HOST"),
        dotenv.get("DB_PORT"),
        dotenv.get("DB_NAME")
    );

    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

