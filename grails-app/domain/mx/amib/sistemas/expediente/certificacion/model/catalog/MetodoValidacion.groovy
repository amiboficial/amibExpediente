package mx.amib.sistemas.expediente.certificacion.model.catalog

class MetodoValidacion {
	
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't204_c_metodoval'
		
		id generator: "assigned"
		
		descripcion column:'ds_metodoval'
		vigente column:'st_vigente'
		
		version false
	}
	
	static constraints = {
		descripcion maxSize:100
	}
	
}
