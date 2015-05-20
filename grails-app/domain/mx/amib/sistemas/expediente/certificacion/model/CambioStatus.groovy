package mx.amib.sistemas.expediente.certificacion.model

class CambioStatus {
	
	Date fechaAutorizacion
	String autorizadoPorUsuario
	
	Date fechaCreacion
	Date fechaModificacion
	
	Certificacion certificacion
	
	static belongsTo = [Certificacion]
	
	static mapping = {
		table 't207_t_cambiostatus'
		
		fechaAutorizacion column:'fh_cambiostatus'
		autorizadoPorUsuario column:'tx_usuario'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		certificacion column:'id_201_certificacion'
		
		version false
	}
	
    static constraints = {
		autorizadoPorUsuario maxSize:254
    }
}
