package mx.amib.sistemas.expediente.certificacion.model

import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoValidacion

class Validacion {
	
	Date fechaAplicacion
	Date fechaInicio
	Date fechaFin
	String autorizadoPorUsuario
	
	Date fechaCreacion
	Date fechaModificacion
	
	Certificacion certificacion
	
	MetodoValidacion metodoValidacion
	
	Set eventosPuntos
	
	static belongsTo = [ Certificacion ]
	
	static hasMany = [ eventosPuntos:EventoPuntos ]
	
	static mapping = {
		table 't207_t_validacion'
		
		id generator: "identity"
		
		fechaAplicacion column:'fh_aplicacion'
		fechaInicio column:'fh_inicio'
		fechaFin column:'fh_fin'
		autorizadoPorUsuario column:'tx_usuario'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		certificacion column:'id_201_certificacion'
		metodoValidacion column:'id_204_metodoval'
		
		version false
	}
	
    static constraints = {
		autorizadoPorUsuario maxSize:254
    }
}
