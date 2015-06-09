package mx.amib.sistemas.expediente.certificacion.model

import java.util.Date

class EventoPuntos {

	Long idEvento
	Integer puntaje
	
	Date fechaCreacion
	Date fechaModificacion
	
	AplicacionAutorizacion aplicacionAutorizacion
	
	static belongsTo = [AplicacionAutorizacion]
	
	static mapping = {
		table 't206_t_eventopuntos'
		
		idEvento column:'id_f_evento'
		puntaje column:'nu_puntaje'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		aplicacionAutorizacion column:'id_207_aplautorizacion'
	
		version false
	}
    static constraints = {
		
    }
}
