package DesafiaTuLogica8;


public class Peliculas {

	private int idPelicula;
	private String tituloPelicula;
	private int duracionMinutos;
	private String generoPelicula;
	private String director;
	private String clasificacionEdad;
	private double precioEntrada;

	//CONSTRUCTORES.
	public Peliculas () {
	}

	public Peliculas (int idPelicula,String tituloPelicula, int duracionMinutos, 
			String generoPelicula, String director, String clasificacionEdad, double precioEntrada) {
		this.idPelicula = idPelicula;
		this.tituloPelicula = tituloPelicula;
		this.duracionMinutos = duracionMinutos;
		this.generoPelicula = generoPelicula;
		this.director = director;
		this.clasificacionEdad = clasificacionEdad;
		this.precioEntrada = precioEntrada;
	}

	//CONSTRUCTOR SIN ATRIBUTO IdPelicula
	public Peliculas (String tituloPelicula, int duracionMinutos, 
			String generoPelicula, String director, String clasificacionEdad, double precioEntrada) {
		this.tituloPelicula = tituloPelicula;
		this.duracionMinutos = duracionMinutos;
		this.generoPelicula = generoPelicula;
		this.director = director;
		this.clasificacionEdad = clasificacionEdad;
		this.precioEntrada = precioEntrada;
	}

	//CONSTRUCTOR CON ATRIBUTO IdPelicula
	public Peliculas (int idPelicula) {
		this.idPelicula = idPelicula;
	}


	//MÉTODOS GETTERS Y SETTERS 
	public int getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}

	public String getTituloPelicula() {
		return tituloPelicula;
	}

	public void setTituloPelicula(String tituloPelicula) {
		this.tituloPelicula = tituloPelicula;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public String getGeneroPelicula() {
		return generoPelicula;
	}

	public void setGeneroPelicula(String generoPelicula) {
		this.generoPelicula = generoPelicula;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getClasificacionEdad() {
		return clasificacionEdad;
	}

	public void setClasificacionEdad(String clasificacionEdad) {
		this.clasificacionEdad = clasificacionEdad;
	}

	public double getPrecioEntrada() {
		return precioEntrada;
	}

	public void setPrecioEntrada(double precioEntrada) {
		this.precioEntrada = precioEntrada;
	}

	@Override
	public String toString() {
		return "Id película: " + idPelicula + "\nTitulo pelicula: " + tituloPelicula + "\nDuración en minutos: "
				+ duracionMinutos + "\nGénero: " + generoPelicula + "\nDirector: " + director
				+ "\nClasificacion edad: " + clasificacionEdad + "\nPrecio de entrada: " + precioEntrada;
	}




}
