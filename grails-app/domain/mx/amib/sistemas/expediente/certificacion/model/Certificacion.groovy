package mx.amib.sistemas.expediente.certificacion.model

import java.util.Date
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoValidacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

class Certificacion {
	
	Date fechaInicio
	Date fechaFin
	Date fechaObtencion
	Boolean isAutorizado
	Boolean isApoderado
	Boolean isUltima
	
	Date fechaCreacion
	Date fechaModificacion
	
	VarianteFigura varianteFigura
	StatusAutorizacion statusAutorizacion
	StatusCertificacion statusCertificacion
	Long idVarianteFigura
	Long idStatusAutorizacion
	Long idStatusCertificacion
	
	Sustentante sustentante
	
	Set validaciones = []
	
	static belongsTo = [Sustentante]
	
	static hasMany = [ validaciones:Validacion ]
	
	static transients = ['idVarianteFigura','idStatusAutorizacion','idStatusCertificacion']
	
	static mapping = {
		table 't201_t_certificacion'
		
		id generator: "identity"
		
		fechaInicio column:'fh_inicio'
		fechaFin column:'fh_fin'
		fechaObtencion column:'fh_obtencion'
		isAutorizado column:'st_isautorizado'
		isApoderado column:'st_isapoderado'
		isUltima column:'st_ultima'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		varianteFigura column:'id_202f_varfigura'
		
		statusCertificacion column:'id_203_stcertificacion'
		statusAutorizacion column:'id_205_stautorizacion'
		
		sustentante column:'id_101_sustentante'
		
		version false
	}
	
    static constraints = {
		idVarianteFigura bindable:true
		idStatusAutorizacion bindable:true
		idStatusCertificacion bindable:true
    }
}
