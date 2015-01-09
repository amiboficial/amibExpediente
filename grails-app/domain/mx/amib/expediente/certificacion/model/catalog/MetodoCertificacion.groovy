package mx.amib.expediente.certificacion.model.catalog

class MetodoCertificacion {
	
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't204_c_metodocert'
		
		id generator: "assigned"
		
		descripcion column:'ds_metodocert'
		vigente column:'st_vigente'
		
		version false
	}
	
	static constraints = {
		descripcion maxSize:100
	}
	
}
