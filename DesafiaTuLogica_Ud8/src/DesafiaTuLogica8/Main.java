package DesafiaTuLogica8;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {

		/*EL MENÚ PRINCIPAL ESTÁ CAPTURADO EN UN TRY-CATCH PARA QUE SI
		 EL USUARIO PULSA UN CARÁCTER NO NUMÉRICO LA EXCEPCIÓN SEA CONTROLADA */
		try {
			Scanner scanner = new Scanner (System.in);
			int opcion;
			PeliculasDAO peliculaDAO = new PeliculasDAO();
			SalasDAO salaDAO = new SalasDAO();
			CinesDAO cineDAO = new CinesDAO();

			/*MENÚ PARA ENTRADA DE BASE DE DATOS "TuCine"
			  ESTRUCTURA DE REPETICIÓN PARA QUE SE REPITA HASTA QUE SE CUMPLA UNA CONDICIÓN,
			  EN ESTE CASO QUE EL USUARIO NO PULSE EL 4 */

			do {
				System.out.println("** Menú Tu Cine **\r\n");
				System.out.println("1.Gestionar Películas.");
				System.out.println("2.Gestionar Salas.");
				System.out.println("3.Gestionar Cines.");
				System.out.println("4.Salir.");
				System.out.println("Introduzca una opción:\r\n");
				opcion = scanner.nextInt();

				/*ESTRUCTURA DE CONTROL PARA QUE UNA VEZ ELEGIDA LA OPCIÓN,
				  CONDUZCA A UN SUBMENÚ DONDE EL USUARIO VOLVERÁ A ELEGIR
				  QUE DESEA HACER O PARA SALIR DEL PROGRAMA */
				switch (opcion) {
				case 1:
					gestionarPeliculas(scanner , peliculaDAO);
					break;

				case 2:
					gestionarSalas(scanner, salaDAO);
					break;
				case 3:
					gestionarCines(scanner, cineDAO);
					break;
				case 4:
					System.out.println("Saliendo de menú.");
					break;
				default:
					System.out.println("Opción no válida. Introduzca una opción válida.");

				} 
			}while (opcion!=4);
			scanner.close();

		}catch (InputMismatchException e) {
			System.out.println ("Error al elegir la opción del menú, introduzca un número." + e.getMessage());
		}
	}

	//LÓGICA PARA GESTIONAR PELICULAS,SUBMENÚ UNA VEZ ELEGIDA LA PRIMERA OPCIÓN

	public static void gestionarPeliculas(Scanner scanner, PeliculasDAO peliculasDAO) throws SQLException {
		int opcion;
		do {
			System.out.println("\n **Menú Películas**");
			System.out.println("1.Listar todas las películas.");
			System.out.println("2.Agregar nueva película.");
			System.out.println("3.Actualizar película.");
			System.out.println("4.Eliminar película.");
			System.out.println("5.Buscar película por id.");
			System.out.println("6.Buscar película por título.");
			System.out.println("7.Volver al menú principal.");
			System.out.println("Seleccione una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA

			switch (opcion) {
			case 1:
				//MÉTODO PARA LISTAR PELICULAS
				listarPeliculas(peliculasDAO);
				break;
			case 2:
				//AGREGAR UNA NUEVA PELICULA
				System.out.println("**Insertar película.**\n");
				System.out.println("Introduzca el título de la película:\n");
				String titulo = scanner.nextLine();
				System.out.println("Introduzca la duración de la película en minutos:\n");
				int duracion = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				System.out.println("Introduzca el género de la película:\n");
				String genero = scanner.nextLine();
				System.out.println("Introduzca el director de la película:\n");
				String director = scanner.nextLine();
				System.out.println("Introduzca la edad recomendada para ver la película:\n");
				String clasificacionEdad = scanner.nextLine();
				System.out.println("Introduzca precio de la entrada:\n");
				double precioEntrada = scanner.nextDouble();
				//MÉTODO PARA AGREGAR PELÍCULA, PASANDOLE TODOS LOS PARÁMETROS INTRODUCIDOS POR EL USUARIO
				agregarPelicula (peliculasDAO, titulo, duracion, genero, director, clasificacionEdad, precioEntrada);
				break;
			case 3:
				//ACTUALIZAR PELICULA.
				System.out.println("**Actualizar película**");
				System.out.println("Introduzca el id de la película:\n");
				int id = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				/*LLAMO AL MÉTODO ObtenerNombrePeliculaPorId
				 PARA OBTENER EL NOMBRE DE LA PELÍCULA ANTES DE ACTUALIZARLA
				 Y ASÍ ASEGURARNOS QUE LA PELÍCULA EXISTE EN LA BASE DE DATOS.
				 ESTE MÉTODO USA LA ID APORTADA POR EL USUARIO PARA DEVOLVER EL TÍTULO*/
				String tituloPelicula = peliculasDAO.obtenerNombrePeliculaporId(id);
				if(tituloPelicula ==null) {
					System.out.println("El ID de la película no existe");
				}else {
					System.out.println("Vas a actualizar la película: " + tituloPelicula);

					System.out.println("Actualizar título:\n ");
					String nuevoTitulo = scanner.nextLine();
					System.out.println("Actualizar duración:\n");
					int nuevaDuracion = scanner.nextInt();
					scanner.nextLine();//CONSUMIR SALTO DE LÍNEA
					System.out.println("Actualizar género:\n");
					String nuevoGenero = scanner.nextLine();
					System.out.println("Actualizar director:\n");
					String nuevoDirector = scanner.nextLine();
					System.out.println("Actualizar clasficación edad:\n");
					String nuevaEdad = scanner.nextLine();
					System.out.println("Actualizar precio entrada:\n ");
					double nuevoPrecio = scanner.nextDouble();
					//MÉTODO PARA ACTUALIZAR LA PELICULA PASÁNDOLE TODOS LOS PARÁMETROS OBTENIDOS
					actualizarPelicula (peliculasDAO,id,nuevoTitulo,nuevaDuracion, nuevoGenero, nuevoDirector, nuevaEdad, nuevoPrecio);
				}
				break;
			case 4:
				//ELIMINAR PELICULA
				System.out.println("**Eliminar película**");
				System.out.println("Introduzca el id de la película:\n");
				int idEliminar = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				/*LLAMO AL MÉTODO ObtenerNombrePeliculaPorId
				//PARA OBTENER EL NOMBRE ANTES DE ELIMINAR LA PELÍCULA*/
				String eliminarTitulo = peliculasDAO.obtenerNombrePeliculaporId(idEliminar);
				if(eliminarTitulo ==null) {
					System.out.println("El ID de la película no existe");
				}else {
					System.out.println("Vas a eliminar la película: " + eliminarTitulo);
					eliminarPelicula (peliculasDAO,idEliminar);
				}
				break;
			case 5:
				//BUSCAR PELÍCULA INTRODUCIENDO EL ID
				System.out.println("**Buscar película**\n");
				System.out.println("Introduzca el id de la película que desea buscar:\n");
				id = scanner.nextInt();
				scanner.nextLine();
				buscarPeliculaId (peliculasDAO,id);
				break;
			case 6:
				//BUSCAR PELÍCULA INTRODUCIENDO EL TÍTULO
				System.out.println("**Buscar película\n**");
				System.out.println("Introducir el título de la película que desea buscar:\n");
				String tituloBuscar = scanner.nextLine().toLowerCase();
				buscarPeliculaTitulo (peliculasDAO,tituloBuscar);
				break;
			case 7: 
				//VOLVER AL MENÚ PRINCIPAL
				System.out.println("Volviendo al menú principal");

				break;
			default:
				System.out.println("Opción no válida. Seleccione una opción correcta.");

			}
		}while (opcion !=7);
	}


	//MÉTODOS PARA AGREGAR, ACTUALIZAR, ELIMINAR Y BUSCAR PELÍCULAS

	private static void listarPeliculas (PeliculasDAO peliculasDAO) {
		try {
			//MÉTODO PARA LISTAR PELÍCULAS
			List<Peliculas> listaPeliculas = peliculasDAO.obtenerTodasPeliculas();
			System.out.println("\nListado de películas: ");
			for (Peliculas peliculas : listaPeliculas) {
				System.out.println(peliculas);
			}
		}catch (SQLException e) {
			System.out.println( "Error al recuperar las películas: " + e.getMessage());
		}
	}

	//MÉTODO PARA AGREGAR PELÍCULA
	private static void agregarPelicula (PeliculasDAO peliculasDAO, String titulo,int duracion, String genero, String director, String clasificacionEdad, double precioEntrada) {
		try {
			Peliculas nuevaPelicula = new Peliculas (titulo, duracion, genero, director,clasificacionEdad,precioEntrada);
			//SE CREA UN OBJETO nuevaPelicula PARA PASARLE TODOS LOS PARÁMETROS AÑADIDOS POR EL USUARIO
			//MEDIANTE EL MÉTODO insertarPelícula LA AÑADIMOS A LA BASE DE DATOS
			peliculasDAO.insertarPelicula(nuevaPelicula);
			System.out.println("Pelicula agregada con éxito.");
		}catch (SQLException e){
			System.out.println("Ha ocurrido un error al introducir la pelicula" + e.getMessage());
		}
	}

	//ACTUALIZAR PELÍCULA
	private static void actualizarPelicula (PeliculasDAO peliculasDAO, int id,String nuevoTitulo, int nuevaDuracion, String nuevoGenero, String nuevoDirector, String nuevaEdad, double nuevoPrecio) {
		try {
			Peliculas peliculaActualizada = new Peliculas (id,nuevoTitulo, nuevaDuracion, nuevoGenero,nuevoDirector,nuevaEdad,nuevoPrecio);
			peliculasDAO.actualizarPelicula(peliculaActualizada);
			System.out.println("Pelicula actualizada correctamente.");
		}catch (SQLException e) {
			System.out.println("No se ha podido actualizar la película" + e.getMessage());
		}

	}

	//ELIMINAR PELÍCULA USANDO EL ID DE LA PELÍCULA A ELIMINAR
	private static void eliminarPelicula (PeliculasDAO peliculasDAO,int idEliminar ){
		try {
			//CREO OBJETO eliminarPelícula PASANDOLE COMO PARÁMETRO EL idEliminar INTRODUCIDO POR EL USUARIO
			Peliculas eliminarPelicula = new Peliculas (idEliminar);
			peliculasDAO.eliminarPelicula(eliminarPelicula);
			System.out.println("La pelicula ha sido eliminada correctamente.");

		}catch(SQLException e){
			System.out.println("Ha ocurrido un error, la película no ha sido eliminada." + e.getMessage());
		}

	}

	//BUSCAR PELÍCULA USANDO EL ID INTRODUCIDO POR EL USUARIO
	private static void buscarPeliculaId (PeliculasDAO peliculasDAO, int id){
		try {
			Peliculas peliculaEncontrada = peliculasDAO.buscarPeliculaId(id);
			/*SI EL OBJETO peliculaEncontrada NO ES NULL, DEVUELVE LOS DATOS DE LA PELÍCULA 
			  USANDO EL MÉTODO toString DE LA CLASE Peliculas*/
			if(peliculaEncontrada !=null) {
				System.out.println("Datos de la película con id: " + id);
				System.out.println(peliculaEncontrada.toString());
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	//BUSCAR PELÍCULA USANDO EL TÍTULO INTRODUCIDO POR EL USUARIO
	private static void buscarPeliculaTitulo (PeliculasDAO peliculasDAO, String buscarTitulo) {
		try {
			Peliculas peliculaTitulo = peliculasDAO.buscarPeliculaTitulo(buscarTitulo);
			/*SI EL TÍTULO NO ES NULO, DEVUELVE LOS DATOS DE LA PELÍCULA USANDO
			  EL MÉTODO toString DE LA CLASE Películas*/
			if(peliculaTitulo !=null) {
				System.out.println("Datos de la película con título: " + buscarTitulo);
				System.out.println(peliculaTitulo.toString());
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


	//LÓGICA PARA GESTIONAR SALAS, OPCIÓN 2 DEL MENÚ. ENTRAMOS EN EL SUBMENÚ DE SALAS

	public static void gestionarSalas(Scanner scanner, SalasDAO salasDAO) throws SQLException {
		int opcion;
		do {
			System.out.println("\n **Menú Salas**");
			System.out.println("1.Listar todas las salas.");
			System.out.println("2.Agregar nueva sala.");
			System.out.println("3.Actualizar sala.");
			System.out.println("4.Eliminar sala.");
			System.out.println("5.Buscar sala por id.");
			System.out.println("6.Volver al menú principal.");
			System.out.println("Seleccione una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA

			switch (opcion) {
			case 1:
				//LISTAR SALAS
				listarSalas(salasDAO);
				break;
			case 2:
				//AGREGAR UNA NUEVA SALA
				System.out.println("**Insertar sala.**\n");
				System.out.println("Introduzca la capacidad de la sala:\n");
				int capacidad = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				System.out.println("Introduzca los metros de la sala:\n");
				int metros = scanner.nextInt();
				scanner.nextLine();
				//PASO POR PARÁMETROS EL OBJETO salasDAO Y LOS PARÁMETROS INTRODUCIDOS POR EL USUARIO
				agregarSala (salasDAO,capacidad,metros);
				break;
			case 3:
				//ACTUALIZAR SALA
				System.out.println("**Actualizar sala**");
				System.out.println("Introduzca el id de la sala:\n");
				int id = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				System.out.println("Actualizar la capacidad:\n ");
				int nuevaCapacidad = scanner.nextInt();
				System.out.println("Actualizar los metros cuadrados:\n");
				int nuevaMedida = scanner.nextInt();
				scanner.nextLine();
				actualizarSala (salasDAO, id, nuevaCapacidad, nuevaMedida);
				break;
			case 4:
				//ELIMINAR SALA
				System.out.println("**Eliminar sala**");
				System.out.println("Introduzca el id de la sala:\n");
				int idEliminar = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				eliminarSala (salasDAO,idEliminar);
				break;
			case 5:
				//BUSCAR SALA INTRODUCIENDO EL ID DE LA SALA DESEADA
				System.out.println("**Buscar sala**\n");
				System.out.println("Introduzca el id de la sala que desea buscar:\n");
				id = scanner.nextInt();
				scanner.nextLine();//CONSUMIR SALTO DE LÍNEA
				buscarSalaId (salasDAO,id);
				break;
			case 6:
				//VOLVER AL MENÚ PRINCIPAL
				System.out.println("Volviendo al menú principal.");
			default:
				System.out.println("Opción no válida. Elija una opción correcta.");

			}
		}while(opcion!=6);
	}

	//IMPLEMENTA LOS MÉTODOS PARA AGREGAR, ACTUALIZAR, ELIMINAR Y BUSCAR SALAS

	//MÉTODO PARA LISTAR SALAS
	public static void listarSalas(SalasDAO salasDAO) {
		try {
			List<Salas> listaSalas = salasDAO.obtenerTodasSalas();
			System.out.println("\nListado de salas: ");
			//USO ESTRUCTURA DE CONTROL FOR-EATCH PARA IMPRIMIR LA LISTA DE SALAS
			for (Salas salas : listaSalas) {
				System.out.println(salas);
			}
		}catch (SQLException e) {
			System.out.println( "Error al recuperar las salas: " + e.getMessage());
		}
	}

	//AGREGAR SALA
	private static void agregarSala (SalasDAO salasDAO,int capacidad,int metros) {
		try {
			Salas nuevaSala = new Salas (capacidad,metros);
			salasDAO.insertarSala(nuevaSala);
			System.out.println("Sala agregada con éxito.");
		}catch (SQLException e){
			System.out.println("Ha ocurrido un error al agregar la sala" + e.getMessage());
		}
	}

	//ACTUALIZAR SALA
	private static void actualizarSala (SalasDAO salasDAO, int id, int nuevaCapacidad,int nuevaMedida) {
		try {
			Salas salaActualizada = new Salas (id,nuevaCapacidad, nuevaMedida);
			salasDAO.actualizarSala(salaActualizada);
			System.out.println("Sala actualizada correctamente.");
		}catch (SQLException e) {
			System.out.println("No se ha podido actualizar la sala" + e.getMessage());
		}

	}

	//ELIMINAR SALA USANDO ID INTRODUCIDO POR EL USUARIO
	private static void eliminarSala (SalasDAO salasDAO, int idEliminar) {
		try {
			Salas eliminarSala = new Salas (idEliminar);
			salasDAO.eliminarSala (eliminarSala);
			System.out.println("Sala eliminada");

		}catch (SQLException e) {
			System.out.println("No se ha podido eliminar la sala" + e.getMessage());
		}
	}

	// BUSCAR SALA INTRODUCIENDO ID DE LA SALA
	private static void buscarSalaId (SalasDAO salasDAO, int id){
		try {
			Salas salaEncontrada = salasDAO.buscarSala(id);
			if(salaEncontrada !=null) {
				System.out.println("Datos de la sala con id: " + id);
				System.out.println(salaEncontrada.toString());
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	//LÓGICA PARA GESTIONAR CINES, SUBMENÚ TRAS ELEGIR OPCIÓN 3 EN EL MENÚ PRINCIPAL.

	public static void gestionarCines(Scanner scanner, CinesDAO cinesDAO) throws SQLException {
		int opcion;
		/*ESTRUCTURA DE REPETICIÓN PARA QUE SE REPITA HASTA QUE SE CUMPLA UNA CONDICIÓN,
		   EN ESTE CASO QUE EL USUARIO NO PULSE EL 7 */
		do {
			System.out.println("\n **Menú Cines**");
			System.out.println("1.Listar todos los cines.");
			System.out.println("2.Agregar nuevo cine.");
			System.out.println("3.Actualizar cine.");
			System.out.println("4.Eliminar cine.");
			System.out.println("5.Buscar cine por id.");
			System.out.println("6.Buscar cine por nombre.");
			System.out.println("7.Volver al menú principal.");
			System.out.println("Seleccione una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA

			switch (opcion) {
			case 1:
				//LISTAR CINES
				listarCines(cinesDAO);
				break;
			case 2:
				//AGREGAR UN NUEVO CINE
				System.out.println("**Insertar cine.**\n");
				System.out.println("Introduzca el nombre del cine:\n");
				String nombreCine = scanner.nextLine();
				System.out.println("Introduzca la dirección del cine:\n");
				String direccionCine = scanner.nextLine();
				//SE PASA COMO PARÁMETROS LOS DATOS INTRODUCIDOS POR EL USUARIO Y EL OBJETO cinesDAO
				agregarCine (cinesDAO,nombreCine,direccionCine);
				break;
			case 3:
				//ACTUALIZAR CINE
				System.out.println("**Actualizar cine**");
				System.out.println("Introduzca el id del cine:\n");
				int id = scanner.nextInt();
				scanner.nextLine(); //CONSUMIR SALTO DE LÍNEA
				System.out.println("Actualizar el nombre:\n ");
				String nuevoNombre = scanner.nextLine();
				System.out.println("Actualizar la dirección:\n");
				String nuevaDireccion = scanner.nextLine();
				actualizarCine (cinesDAO, id, nuevoNombre, nuevaDireccion);
				break;
			case 4:
				//ELIMINAR CINE
				System.out.println("**Eliminar cine**");
				System.out.println("Introduzca el id del cine:\n");
				int idEliminar = scanner.nextInt();
				scanner.nextLine();  //CONSUMIR SALTO DE LÍNEA
				eliminarCine (cinesDAO,idEliminar);
				break;
			case 5:
				//BUSCAR CINE CON ID INTRODUCIDO POR EL USUARIO
				System.out.println("**Buscar cine con id**\n");
				System.out.println("Introduzca el id del que desea buscar:\n");
				id = scanner.nextInt();
				scanner.nextLine();
				buscarCineId (cinesDAO,id);
				break;
			case 6:
				//BUSCAR CINE POR NOMBRE INTRODUCIDO POR EL USUARIO
				System.out.println("**Buscar cine**\n");
				System.out.println("Introduzca el nombre del cine que desea buscar:\n");
				String nombre = scanner.nextLine();
				buscarCineNombre (cinesDAO,nombre);
				break;
			case 7:
				//VOLVER AL MENÚ PRINCIPAL
				System.out.println("Volviendo al menú principal.");
				break;
			default:
				System.out.println("Opción no válida. Elija una opción correcta.");

			}
		}while(opcion!=7);
	}
	//IMPLEMENTA MÉTODOS PARA AGREGAR, ACTUALIZAR, ELIMINAR Y BUSCAR CINES.

	//LISTAR CINES
	private static void listarCines (CinesDAO cinesDAO) {
		try {
			List<Cines> listaCines = cinesDAO.obtenerTodosCines();
			System.out.println("\nListado de cines: ");
			for (Cines cines : listaCines) {
				System.out.println(cines);
			}
		}catch (SQLException e) {
			System.out.println( "Error al recuperar los cines: " + e.getMessage());
		}
	}

	//AGREGAR CINE 
	private static void agregarCine (CinesDAO cinesDAO, String nombre,String direccion) {
		try {
			Cines nuevoCine = new Cines(nombre, direccion);
			cinesDAO.insertarCine(nuevoCine);
			System.out.println("Cine agregado con éxito.");
		}catch (SQLException e){
			System.out.println("Ha ocurrido un error al agregar el cine" + e.getMessage());
		}
	}

	//ACTUALIZAR CINE
	private static void actualizarCine (CinesDAO cinesDAO, int id,String nuevoNombre,String nuevaDireccion) {
		try {
			Cines cineActualizado = new Cines (id, nuevoNombre, nuevaDireccion);
			cinesDAO.actualizarCine(cineActualizado);
			System.out.println("Cine actualizado correctamente.");
		}catch (SQLException e) {
			System.out.println("No se ha podido actualizar el cine." + e.getMessage());
		}

	}

	//ELIMINAR CINE
	private static void eliminarCine (CinesDAO cinesDAO,int idEliminar ){
		try {

			Cines eliminarCine = new Cines (idEliminar);
			cinesDAO.eliminarCine(eliminarCine);

		}catch(SQLException e){
			System.out.println("Ha ocurrido un error, el cine no ha sido eliminado." + e.getMessage());
		}

	}

	//BUSCAR CINE PASANDO COMO PARÁMETRO ID INTRODUCIDO POR EL USUARIO
	private static void buscarCineId (CinesDAO cinesDAO, int id){
		try {
			Cines cineEncontrado = cinesDAO.buscarCineId(id);
			if(cineEncontrado !=null) {
				System.out.println("Datos del cine con id: " + id);
				//DEVUELVE UN OBJETO cineEncontrado MEDIANTE EL MÉTODO toString DE LA CLASE Cines
				System.out.println(cineEncontrado.toString());
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	//BUSCAR CINE POR NOMBRE INTRODUCIDO POR EL USUARIO
	private static void buscarCineNombre (CinesDAO cinesDAO, String buscarNombre) {
		try {
			Cines cineNombre = cinesDAO.buscarCines(buscarNombre);
			if(buscarNombre !=null) {
				System.out.println("Datos del cine con nombre: " + buscarNombre);
				System.out.println(cineNombre.toString());
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}






