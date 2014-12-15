package mx.amib.expediente.certificacion.model.catalog

class StatusAutorizacion {
	
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't205_c_stautorizacion'
		
		id generator: "assigned"
		
		descripcion column:'ds_staut'
		vigente column:'st_vigente'
		
		version false
	}
	
    static constraints = {
		descripcion maxSize:200
    }
}
