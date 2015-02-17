package mx.amib.sistemas.expediente.persona.model

import mx.amib.sistemas.expediente.persona.model.catalog.TipoDocumentoSustentante

class DocumentoSustentante {

	String uuid
	Boolean vigente
	
	Sustentante sustentante
	
	TipoDocumentoSustentante tipoDocumentoSustentate
	
	static belongsTo = [Sustentante]
	
	static mapping = {
		table 't102_t_docsust'
		
		id generator: "identity"
		
		uuid column:'uuid_f_doc'
		vigente column:'st_vigente'
		
		sustentante column:'id_101_sustentante'
		
		tipoDocumentoSustentate column:'id_103f_tpdocsust'
		
		version false
	}
    static constraints = {
		uuid maxSize: 36
    }
}
