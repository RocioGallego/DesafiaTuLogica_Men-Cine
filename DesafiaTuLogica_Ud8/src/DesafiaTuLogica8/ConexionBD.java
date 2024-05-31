package DesafiaTuLogica8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

	//Atributos para guardar los datos de la conexión.

	private String jdbcUrl;
	private String user;
	private String password;

	//Constructor, recibe los datos de conexión y los almacena en sus atributos.

	public ConexionBD (String jdbcUrl, String user, String password) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;

	}

	//Método getter retorna el objetivo de tipo conexión.

	public Connection getConnection () throws SQLException{
		//Establecer la conexión con la clase DriverManager.

		Connection connection = DriverManager.getConnection(this.jdbcUrl, this.user, this.password);
		return connection;

	}
}
