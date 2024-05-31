package DesafiaTuLogica8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalasDAO {

	private ConexionBD conexionBD;

	//CONSTRUCTOR
	public SalasDAO() {
		this.conexionBD = new ConexionBD ("jdbc:mysql://localhost:3306/tucine","root","root");
	}
	private void close (Connection connection) {
		if(connection !=null) {
			try {
				connection.close();
			}catch (SQLException e) {
				System.out.println("No ha sido posible cerrar la conexión");
			}
		}
	}

	//CERRAR CONEXIONES

	private void close (PreparedStatement prepast) {
		if (prepast !=null) {
			try {
				prepast.close();
			}catch (SQLException e) {
				//NO SE HACE NADA EN CASO DE ERROR
			}
		}
	}
	private void close (ResultSet rs) {
		if (rs!= null) {
			try {
				rs.close();
			}catch (SQLException e) {
				//NO SE HACE NADA EN CASO DE ERROR
			}
		}
	}

	//LISTAR SALAS

	public List<Salas> obtenerTodasSalas()throws SQLException{
		List<Salas> listaSalas = new ArrayList<Salas>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Salas";

		try {
			connection = conexionBD.getConnection(); //INICIAR CONEXIÓN
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idSala");
				int capacidad = rs.getInt ("capacidadSala");
				int metros = rs.getInt("metrosSala");

				Salas sala = new Salas (id, capacidad,metros);
				listaSalas.add(sala);
			}
		}finally {
			close (rs);
			close (pstmt);
			close (connection);
		}
		return listaSalas;
	}


	//INSERTAR SALA EN LA BD
	public void insertarSala(Salas sala) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO Salas (capacidadSala,metrosSala) VALUES (?, ?)";

		try {
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, sala.getCapacidadSala());
			pstmt.setInt(2, sala.getMetrosCuadrados());
			pstmt.executeUpdate();


		} finally {
			close(pstmt);
			close(connection);
		}

	}
	// MÉTODO PARA ACTUALIZAR LA SALA EN LA BD
	public void actualizarSala(Salas sala) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE Salas SET capacidadSala = ?, metrosSala = ? WHERE idSala = ?";
			connection = conexionBD.getConnection();

			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, sala.getCapacidadSala());
			pstmt.setInt(2, sala.getMetrosCuadrados());
			pstmt.setInt(3, sala.getIdSala());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	//Método para eliminar una sala.
	public void eliminarSala (Salas sala) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM Salas WHERE idSala =?";
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, sala.getIdSala());

			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println ("La sala ha sido eliminada con éxito");
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}

	//MÉTODO PARA BUSCAR UNA SALA POR id
	public Salas buscarSala (int id) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = conexionBD.getConnection();
			String sql = "SELECT * FROM salas WHERE idSala =?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,id);
			//EJECUTA LA SENTENCIA SQL Y CARGA EL RESULTSET
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				//LEER VALORES RESULTSET
				int idSala = rs.getInt("idSala");
				int capacidad = rs.getInt("capacidadSala");
				int metros = rs.getInt("metrosSala");
				return new Salas (idSala,capacidad,metros);

			}else {
				return null; //LA SALA NO EXISTE EN LA BD
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}



}
