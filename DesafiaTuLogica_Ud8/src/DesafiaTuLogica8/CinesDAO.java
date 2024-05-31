package DesafiaTuLogica8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinesDAO {

	private ConexionBD conexionBD;

	//CONSTRUCTOR.

	public CinesDAO() {
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

	//CERRAR SESIONES.

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
				//NO SE HACE NADA EN CASO DE ERROR.
			}
		}
	}
	//LISTAR LOS CINES CREANDO UNA LISTA COMO CONTENEDOR.

	public List <Cines> obtenerTodosCines() throws SQLException{
		List <Cines> listaCines = new ArrayList<Cines>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Cines";

		try {
			connection = conexionBD.getConnection(); //Iniciar la conexión.
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idCine");
				String nombre = rs.getString ("nombreCine");
				String direccion = rs.getString("direccionCine");
				Cines cine = new Cines (id, nombre,direccion);
				listaCines.add(cine);
			}
		}finally {
			close (rs);
			close (pstmt);
			close (connection);
		}
		return listaCines;
	}

	//INSERTAR CINE EN LA BASE DE DATOS.

	public void insertarCine(Cines cine) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO Cines (nombreCine, direccionCine) VALUES (?, ?)";

		try {
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, cine.getNombreCine());
			pstmt.setString(2, cine.getDireccion());
			pstmt.executeUpdate();

		} finally {
			close(pstmt);
			close(connection);
		}

	}

	//METODO PARA COMPROBAR QUE EL CINE EXISTE EN LA BASE DE DATOS.

	public String obtenerNombreCineId (int id) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt =  null;
		try {
			String sql = "SELECT nombreCine FROM cines WHERE idCine = ?";
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("nombreCine");
			}

		}finally {
			close (pstmt);
			close (connection);    
		}
		return null;
	}

	//MÉTODO PARA ACTUALIZAR LOS DETALLES DEL CINE EN LA BASE DE DATOS.
	public void actualizarCine(Cines cine) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE cines SET nombreCine = ?, direccionCine = ? WHERE idCine = ?";
			connection = conexionBD.getConnection();

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, cine.getNombreCine());
			pstmt.setString(2, cine.getDireccion());
			pstmt.setInt(3,cine.getIdCine());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	//MÉTODO PARA ELIMINAR UN CINE.
	public void eliminarCine (Cines cine) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM cines WHERE idCine =?";
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, cine.getIdCine());

			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println ("El cine ha sido eliminado con éxito");
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}
	//BUSCAR UN CINE POR ID.

	public Cines buscarCineId (int id) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = conexionBD.getConnection();
			String sql = "SELECT * FROM cines WHERE idCine =?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,id);
			//EJECUTA LA SENTENCIA SQL Y CARGA EL RESULTSET
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				//LEER LOS VALORES RESULTSET
				int idCine = rs.getInt("idCine");
				String nombre = rs.getString("nombreCine");
				String direccion = rs.getString("direccionCine");
				return new Cines (idCine, nombre, direccion);

			}else {
				return null; //EL CINE NO EXISTE EN LA BASE DE DATOS
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}

	//BUSCAR CINE POR NOMBRE
	public Cines buscarCines(String nombre) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = conexionBD.getConnection();
			String sql = "SELECT * FROM cines WHERE nombreCine = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nombre);
			//CARGAR RESULTSET.
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				//LEER LOS VALORES.
				int idCine = rs.getInt("idCine");
				String nombreCine = rs.getString("nombreCine");	
				String direccionCine = rs.getString ("direccionCine");
				return new Cines (idCine, nombreCine, direccionCine);

			}else {
				return null;//EL CINE NO EXISTE EN LA BASE DE DATOS, DEVUELVE NULO
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}


}
