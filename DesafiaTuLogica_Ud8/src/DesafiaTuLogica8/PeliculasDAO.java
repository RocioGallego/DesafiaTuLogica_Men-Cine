package DesafiaTuLogica8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculasDAO {

	private ConexionBD conexionBD;

	//CONSTRUCTOR

	public PeliculasDAO() {
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
	//MÉTODO PARA LISTAR PELÍCULAS DE LA BASE DE DATOS

	public List <Peliculas> obtenerTodasPeliculas() throws SQLException{
		List <Peliculas> listaPeliculas = new ArrayList<Peliculas>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Peliculas";

		try {
			connection = conexionBD.getConnection(); //INICIAR LA CONEXIÓN
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			//USO DE BUCLE WHILE PARA QUE SIGA OBTENIENDO DATOS HASTA QUE NO HAYA NINGUNO MÁS EN LA BASE DE DATOS
			while (rs.next()) {
				int id = rs.getInt("idPeli");
				String titulo = rs.getString ("tituloPeli");
				int duracion = rs.getInt ("duracionPeli");
				String genero = rs.getString("generoPeli");
				String director = rs.getString("directorPeli");
				String clasificacion = rs.getString("clasficacionPeli");
				double precio = rs.getDouble ("precioPeli");

				Peliculas pelicula = new Peliculas (id,titulo, duracion, genero, director, clasificacion, precio);
				listaPeliculas.add(pelicula);

			}
		}finally {
			close (rs);
			close (pstmt);
			close (connection);
		}
		return listaPeliculas;
	}


	//INSERTAR PELICULA EN LA BASE DE DATOS
	public void insertarPelicula(Peliculas pelicula) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO Peliculas (tituloPeli, duracionPeli, generoPeli, directorPeli, clasficacionPeli, precioPeli) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, pelicula.getTituloPelicula());
			pstmt.setInt(2, pelicula.getDuracionMinutos());
			pstmt.setString(3, pelicula.getGeneroPelicula());
			pstmt.setString(4, pelicula.getDirector());
			pstmt.setString(5, pelicula.getClasificacionEdad());
			pstmt.setDouble(6, pelicula.getPrecioEntrada());
			pstmt.executeUpdate();


		} finally {
			close(pstmt);
			close(connection);
		}

	}
	//MÉTODO PARA COMPROBAR QUE LA PELÍCULA EXISTE EN LA BD, PASANDO COMO PARÁMETRO LA id

	public String obtenerNombrePeliculaporId (int id) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt =  null;
		try {
			String sql = "SELECT tituloPeli FROM Peliculas WHERE idPeli = ?";
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("tituloPeli");
			}

		}finally {
			close (pstmt);
			close (connection);    
		}
		return null;
	}

	//MÉTODO PARA ACTUALIZAR LOS DETALLES DE LA PELÍCULA EN LA BD
	public void actualizarPelicula(Peliculas pelicula) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE Peliculas SET tituloPeli = ?, duracionPeli = ?, generoPeli = ?, directorPeli = ?, clasficacionPeli = ?, precioPeli = ? WHERE idPeli = ?";
			connection = conexionBD.getConnection();

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, pelicula.getTituloPelicula());
			pstmt.setInt(2, pelicula.getDuracionMinutos());
			pstmt.setString(3, pelicula.getGeneroPelicula());
			pstmt.setString(4, pelicula.getDirector());
			pstmt.setString(5, pelicula.getClasificacionEdad());
			pstmt.setDouble(6, pelicula.getPrecioEntrada());
			pstmt.setInt(7, pelicula.getIdPelicula());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	//MÉTODO PARA ELIMINAR UNA PELÍCULA DE LA BD
	public void eliminarPelicula (Peliculas pelicula) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM Peliculas WHERE idPeli =?";
			connection = conexionBD.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pelicula.getIdPelicula());

			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println ("La película ha sido eliminada con éxito");
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}

	//MÉTODO PARA BUSCAR LA PELÍCULA POR id
	public Peliculas buscarPeliculaId (int id) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = conexionBD.getConnection();
			String sql = "SELECT * FROM peliculas WHERE idPeli =?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,id);
			//EJECUTA LA SENTENCIA SQL Y CARGA EL RESULTSET
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				//LEER VALORES RESULTSET
				int idPeli = rs.getInt("idPeli");
				String titulo = rs.getString("tituloPeli");
				int duracion = rs.getInt("duracionPeli");
				String genero = rs.getString("generoPeli");
				String director = rs.getString ("directorPeli");
				String clasificacion = rs.getString("clasficacionPeli");
				double precio = rs.getDouble("precioPeli");
				return new Peliculas (idPeli,titulo,duracion,genero,director,clasificacion,precio);

			}else {
				return null; //LA PELÍCULA NO EXISTE EN LA BASE DE DATOS
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}

	//MÉTODO BUSCAR PELÍCULAS POR TÍTULO
	public Peliculas buscarPeliculaTitulo(String titulo) throws SQLException{
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = conexionBD.getConnection();
			String sql = "SELECT * FROM peliculas WHERE tituloPeli = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, titulo);
			//CARGAR RESULTSET
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				//LEER LOS VALORES
				int id = rs.getInt("idPeli");
				String tituloPelicula = rs.getString("tituloPeli");	
				int duracion = rs.getInt("duracionPeli");
				String genero = rs.getString("generoPeli");
				String director = rs.getString ("directorPeli");
				String clasificacion = rs.getString("clasficacionPeli");
				double precio = rs.getDouble("precioPeli");
				return new Peliculas (id,tituloPelicula,duracion,genero,director,clasificacion,precio);

			}else {
				return null;//LA PELÍCULA NO EXISTE EN LA BD
			}
		}finally {
			close (pstmt);
			close (connection);
		}
	}

}
