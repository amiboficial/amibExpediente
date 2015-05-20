package mx.amib.sistemas.expediente.persona.model

class Puesto {

	Long idInstitucion
	Date fechaInicio
	Date fechaFin
	String nombrePuesto
	Boolean esActual
	
	Date fechaCreacion
	Date fechaModificacion
	
	Sustentante sustentante
	
	static belongsTo = [Sustentante]
	
	static mapping = {
		table 't106_t_puesto'
		
		id generator: "identity"
		
		idInstitucion column:'id_f_institucion'
		fechaInicio column:'fh_inicio'
		fechaFin column:'fh_termino'
		nombrePuesto column:'tx_puesto'
		esActual column:'st_isactual'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		sustentante column:'id_101_sustentante'
		
		version false
	}
	
    static constraints = {
		nombrePuesto maxSize:100
		
		fechaCreacion nullable: true
		fechaModificacion nullable: true
    }
}
