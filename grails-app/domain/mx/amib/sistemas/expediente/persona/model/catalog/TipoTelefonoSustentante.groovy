package mx.amib.sistemas.expediente.persona.model.catalog

class TipoTelefonoSustentante {
	
	Long numeroVersion
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't105_cf_tptelsust'
		
		id generator: "assigned"
		numeroVersion column:'nu_version'
		descripcion column:'ds_tptelsust'
		vigente column:'st_vigente'
		version false
	}
	
	static constraints = {
		descripcion maxSize:100
	}
}
