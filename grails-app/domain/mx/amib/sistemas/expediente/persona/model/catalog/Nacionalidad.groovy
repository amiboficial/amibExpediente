package mx.amib.sistemas.expediente.persona.model.catalog

class Nacionalidad {
	
	Long numeroVersion
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't107_cf_nacionalidad'
		
		id generator: "assigned"
		numeroVersion column:'nu_version'
		descripcion column:'ds_nacionalidad'
		vigente column:'st_vigente'
		version false
	}
	
    static constraints = {
		descripcion maxSize:50
    }
}
