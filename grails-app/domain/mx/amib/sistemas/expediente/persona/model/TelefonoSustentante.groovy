package mx.amib.sistemas.expediente.persona.model

import mx.amib.sistemas.expediente.persona.model.catalog.TipoTelefonoSustentante

class TelefonoSustentante {

	String lada
	String telefono
	String extension

	TipoTelefonoSustentante tipoTelefonoSustentante
	Long idTipoTelefonoSustentante
	
	Sustentante sustentante
	
	static belongsTo = [Sustentante]
	
	static transients = ['idTipoTelefonoSustentante']
	
	static mapping = {
		table 't104_t_telsust'
		
		id generator: "identity"
		
		lada column:'nu_lada'
		telefono column:'nu_telefono'
		extension column:'nu_extension'
		
		tipoTelefonoSustentante column:'id_105f_tptelsust'
		
		sustentante column:'id_101_sustentante'
		
		version false
	}
	
    static constraints = {
		idTipoTelefonoSustentante bindable: true
		
		lada maxSize:16
		telefono maxSize:50
		extension maxSize:6
    }
}
