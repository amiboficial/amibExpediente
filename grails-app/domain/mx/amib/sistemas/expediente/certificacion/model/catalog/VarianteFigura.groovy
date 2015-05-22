package mx.amib.sistemas.expediente.certificacion.model.catalog

class VarianteFigura {
	String nombre
	Boolean vigente
	Long numeroVersion
	Long idFigura
	String nombreFigura
	String nombreAcuseFigura
	Boolean esAutorizableFigura
	String tipoAutorizacionFigura
	String inicialesFigura
	
	
	static mapping = {
		table 't202_cf_varfigura'
		
		id generator: "assigned"
		numeroVersion column:'nu_version'
		
		nombre column:'nb_varfigura'
		
		idFigura column:'id_figura'
		nombreFigura column:'nb_figura'
		nombreAcuseFigura column:'nb_acuse'
		esAutorizableFigura column:'st_autorizable'
		tipoAutorizacionFigura column:'ds_tpautorizacion'
		inicialesFigura column:'nb_iniciales'
		
		vigente column:'st_vigente'
		version false
	}
	
    static constraints = {
		nombre maxSize:254
		nombreAcuseFigura nullable:true
		esAutorizableFigura nullable:true
		tipoAutorizacionFigura nullable:true
		inicialesFigura nullable:true
    }
}
