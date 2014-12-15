package mx.amib.expediente.certificacion.model.catalog

class Figura {
	String descripcion
	String tipoAutorizacion
	String iniciales
	Boolean vigente
	
	static mapping = {
		table 't202_cf_figura'
		
		id generator: "assigned"
		
		descripcion column:'ds_figura'
		tipoAutorizacion column:'tx_tpautorizacion'
		iniciales column:'tx_iniciales'
		vigente column:'st_vigente'
		
		version false
	}
	
    static constraints = {
		descripcion maxSize:254
    }
}
