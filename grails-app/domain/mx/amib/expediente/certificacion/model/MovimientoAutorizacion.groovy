package mx.amib.expediente.certificacion.model

class MovimientoAutorizacion {
	
	Date fechaAutorizacion
	String autorizadoPorUsuario
	
	Date fechaCreacion
	Date fechaModificacion
	
	Certificacion certificacion
	
	static belongsTo = [Certificacion]
	
	static mapping = {
		table 't207_t_movautorizacion'
		
		fechaAutorizacion column:'fh_autorizacion'
		autorizadoPorUsuario column:'tx_autorizadopor'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		certificacion column:'id_201_certificacion'
		
		version false
	}
	
    static constraints = {
		autorizadoPorUsuario maxSize:254
    }
}
