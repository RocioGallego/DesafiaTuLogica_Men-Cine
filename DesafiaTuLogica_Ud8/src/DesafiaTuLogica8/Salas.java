package DesafiaTuLogica8;

public class Salas {

	private int idSala;
	private int capacidadSala;
	private int metrosCuadrados;

	public Salas() {

	}

	public Salas(int idSala, int capacidadSala, int metrosCuadrados) {
		this.idSala = idSala;
		this.capacidadSala = capacidadSala;
		this.metrosCuadrados = metrosCuadrados;
	}

	//CONSTRUCTOR SIN ATRIBUTO ID PARA AGREGAR SALA
	public Salas( int capacidadSala, int metrosCuadrados) {
		this.capacidadSala = capacidadSala;
		this.metrosCuadrados = metrosCuadrados;
	}

	//CONSTRUCTOR CON ATRIBUTO ID PARA ELIMINAR LA SALA
	public Salas (int idSala) {
		this.idSala = idSala;
	}
	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public int getCapacidadSala() {
		return capacidadSala;
	}

	public void setCapacidadSala(int capacidadSala) {
		this.capacidadSala = capacidadSala;
	}

	public int getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setMetrosCuadrados(int metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	@Override
	public String toString() {
		return "Id Sala: " + idSala + "\nCapacidad sala: " + capacidadSala + "\nMetros cuadrados: " + metrosCuadrados;
	}




}
