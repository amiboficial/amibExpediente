package mx.amib.sistemas.expediente.certificacion.model

import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoValidacionAutorizacion

class AplicacionAutorizacion {
	
	Date fechaAplicacion
	Date fechaInicio
	Date fechaFin
	String autorizadoPorUsuario
	
	Date fechaCreacion
	Date fechaModificacion
	
	Certificacion certificacion
	
	MetodoValidacionAutorizacion metodoValidacionAutorizacion
	
	Set eventosPuntos
	
	static belongsTo = [ Certificacion ]
	
	static hasMany = [ eventosPuntos:EventoPuntos ]
	
	static mapping = {
		table 't207_t_aplautorizacion'
		
		id generator: "identity"
		
		fechaAplicacion column:'fh_aplicacion'
		fechaInicio column:'fh_inicio'
		fechaFin column:'fh_fin'
		autorizadoPorUsuario column:'tx_usuario'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		certificacion column:'id_201_certificacion'
		metodoValidacionAutorizacion column:'id_204_metodoval'
		
		version false
	}
	
    static constraints = {
		autorizadoPorUsuario maxSize:254
    }
}
