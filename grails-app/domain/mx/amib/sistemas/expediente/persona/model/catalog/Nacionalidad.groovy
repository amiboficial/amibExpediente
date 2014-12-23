package mx.amib.sistemas.expediente.persona.model.catalog

class Nacionalidad {
	
	String descripcion
	Boolean vigente
	
	static mapping = {
		table 't107_cf_nacionalidad'
		
		id generator: "assigned"
		
		descripcion column:'ds_nacionalidad'
		vigente column:'st_vigente'
	}
	
    static constraints = {
		descripcion maxSize:50
    }
}
