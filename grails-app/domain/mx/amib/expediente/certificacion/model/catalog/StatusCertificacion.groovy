package mx.amib.expediente.certificacion.model.catalog

class StatusCertificacion {

	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't203_c_stcertificacion'
		
		id generator: "assigned"
		
		descripcion column:'ds_stcertificacion'
		vigente column:'st_vigente'
		
		version false
	}
	
    static constraints = {
		descripcion maxSize:100
    }
}
