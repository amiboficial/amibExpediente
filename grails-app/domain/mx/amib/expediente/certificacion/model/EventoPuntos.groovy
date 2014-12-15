package mx.amib.expediente.certificacion.model

import java.util.Date

class EventoPuntos {

	Long idEvento
	Integer puntaje
	
	Date fechaCreacion
	Date fechaModificacion
	
	Certificacion certificacion
	
	static belongsTo = [Certificacion]
	
	static mapping = {
		table 't206_t_eventopuntos'
		
		idEvento column:'id_f_evento'
		puntaje column:'nu_puntaje'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		certificacion column:'id_201_certificacion'
	
		version false
	}
    static constraints = {
		
    }
}
