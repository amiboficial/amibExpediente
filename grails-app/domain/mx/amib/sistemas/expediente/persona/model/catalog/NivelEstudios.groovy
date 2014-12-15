package mx.amib.sistemas.expediente.persona.model.catalog

class NivelEstudios {

	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't108_cf_nivelestudios'
		
		id generator: "assigned"
		
		descripcion column:'ds_nivelestudios'
		vigente column:'st_vigente'
		
		version false
	}
	
    static constraints = {
		descripcion maxSize:50
    }
}
