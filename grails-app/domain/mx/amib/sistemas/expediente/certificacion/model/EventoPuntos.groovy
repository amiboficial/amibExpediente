package mx.amib.sistemas.expediente.certificacion.model

import java.util.Date

class EventoPuntos {

	Long idEvento
	Integer puntaje
	
	Date fechaCreacion
	Date fechaModificacion
	
	Validacion validacion
	
	static belongsTo = [Validacion]
	
	static mapping = {
		table 't206_t_eventopuntos'
		
		idEvento column:'id_f_evento'
		puntaje column:'nu_puntaje'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		validacion column:'id_207_validacion'
	
		version false
	}
    static constraints = {
		idEvento nullable:true
		puntaje nullable:true
    }
}
