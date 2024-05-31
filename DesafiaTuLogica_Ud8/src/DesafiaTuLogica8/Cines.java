package DesafiaTuLogica8;

public class Cines {

	//ATRIBUTOS
	private int idCine;
	private String nombreCine;
	private String direccion;

	//CONSTRUCTORES
	public Cines() {

	}
	public Cines(int idCine, String nombreCine, String direccion) {
		this.idCine = idCine;
		this.nombreCine = nombreCine;
		this.direccion = direccion;
	}

	public Cines (int idCine) {
		this.idCine = idCine;
	}

	public Cines ( String nombreCine, String direccion) {
		this.nombreCine = nombreCine;
		this.direccion = direccion;
	}
	
	//MÉTODOS
	public int getIdCine() {
		return idCine;
	}
	public void setIdCine(int idCine) {
		this.idCine = idCine;
	}
	public String getNombreCine() {
		return nombreCine;
	}
	public void setNombreCine(String nombreCine) {
		this.nombreCine = nombreCine;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	@Override
	public String toString() {
		return "Id cine: " + idCine + "\nNombre cine: " + nombreCine + "\nDireccion: " + direccion;
	}

	
	
	

}
