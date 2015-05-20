package mx.amib.sistemas.expediente.persona.model.catalog

class TipoDocumentoSustentante {
	
	Long numeroVersion
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't103_cf_tpdocsust'
		
		id generator: "assigned"
		numeroVersion column:'nu_version'
		descripcion column:'ds_tpdocsust'
		vigente column:'st_vigente'
		version false
	}
	
	static constraints = {
		descripcion maxSize:100
	}
}
