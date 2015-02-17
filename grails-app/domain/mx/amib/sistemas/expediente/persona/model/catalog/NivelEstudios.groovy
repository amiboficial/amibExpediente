package mx.amib.sistemas.expediente.persona.model.catalog

class NivelEstudios {

	Long numeroVersion
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't108_cf_nivelestudios'
		
		id generator: "assigned"
		numeroVersion column:'nu_version'
		descripcion column:'ds_nivelestudios'
		vigente column:'st_vigente'
		version false
	}
	
    static constraints = {
		descripcion maxSize:50
    }
}
