package mx.amib.sistemas.expediente.certificacion.model

import java.util.Date
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoCertificacion
import mx.amib.sistemas.expediente.persona.model.Sustentante


class Certificacion {
	
	Date fechaInicio
	Date fechaFin
	Date fechaObtencion
	String nombreUsuarioActualizo
	
	Boolean esLaActual
	Date fechaUltimoCambioStatusEsLaActual
	
	Date fechaCreacion
	Date fechaModificacion
	
	VarianteFigura varianteFigura
	StatusAutorizacion statusAutorizacion
	StatusCertificacion statusCertificacion
	MetodoCertificacion metodoCertificacion
	Long idVarianteFigura
	Long idStatusAutorizacion
	Long idStatusCertificacion
	Long idMetodoCertificacion
	
	Sustentante sustentante
	
	Set cambiosStatus = []
	Set eventosPuntos = []
	
	static belongsTo = [Sustentante]
	
	static hasMany = [ cambiosStatus:CambioStatus, eventosPuntos:EventoPuntos ]
	
	static transients = ['idVarianteFigura','idStatusAutorizacion','idStatusCertificacion','idMetodoCertificacion']
	
	static mapping = {
		table 't201_t_certificacion'
		
		id generator: "identity"
		
		fechaInicio column:'fh_inicio'
		fechaFin column:'fh_fin'
		fechaObtencion column:'fh_obtencion'
		nombreUsuarioActualizo column:'tx_usuarioactualizo'
		
		esLaActual column:'st_actual'
		fechaUltimoCambioStatusEsLaActual column:'fh_setstactual'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		varianteFigura column:'id_103f_varfigura'
		statusAutorizacion column:'id_205_stautorizacion'
		statusCertificacion column:'id_203_stcertificacion'
		metodoCertificacion column:'id_204_metodocert'
		
		sustentante column:'id_101_sustentante'
		
		version false
	}
	
    static constraints = {
		
		idVarianteFigura bindable: true
		idStatusAutorizacion bindable: true
		idStatusCertificacion bindable: true
		idMetodoCertificacion bindable: true
		
		nombreUsuarioActualizo maxSize:254
    }
}
