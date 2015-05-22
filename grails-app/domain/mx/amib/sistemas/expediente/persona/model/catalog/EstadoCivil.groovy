package mx.amib.sistemas.expediente.persona.model.catalog

class EstadoCivil {
	Long numeroVersion
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't109_cf_edocivil'
		
		id generator: "assigned"
		numeroVersion column:'nu_version'
		descripcion column:'ds_edocivil'
		vigente column:'st_vigente'
		
		version false
	}
	
	static constraints = {
		descripcion maxSize:50
	}
}
