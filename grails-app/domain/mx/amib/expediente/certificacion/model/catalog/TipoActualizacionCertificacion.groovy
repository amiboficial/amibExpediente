package mx.amib.expediente.certificacion.model.catalog

class TipoActualizacionCertificacion {
	
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't204_c_tpactualcert'
		
		id generator: "assigned"
		
		descripcion column:'ds_tpactualizacion'
		vigente column:'st_vigente'
		
		version false
	}
	
	static constraints = {
		descripcion maxSize:100
	}
	
}
