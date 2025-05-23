package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	public static Connection getConnection() {
		try {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			return DriverManager.getConnection(url, props); // Cria uma nova conexão a cada vez
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close(); // Fecha a conexão passada como parâmetro
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	/* Abre o arquivo db.properties e guarda no obj Properties */
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs); /* Armazena os dados */
			return props; /* Retorna os dados */
		} catch (IOException e) {
			throw new DbException(e.getMessage()); /* Lança a exceção personalizada */
		}
	}

	/* Funções para tratar as exceções no programa principal */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
