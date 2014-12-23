package mx.amib.sistemas.expediente.persona.model.catalog

class TipoTelefonoSustentante {
	
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't105_cf_tptelsust'
		
		id generator: "assigned"
		
		descripcion column:'ds_tptelsust'
		vigente column:'st_vigente'
	}
	
	static constraints = {
		descripcion maxSize:100
	}
}
