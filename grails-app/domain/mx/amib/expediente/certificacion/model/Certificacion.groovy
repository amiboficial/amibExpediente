package mx.amib.expediente.certificacion.model

import java.util.Date
import mx.amib.expediente.certificacion.model.catalog.Figura
import mx.amib.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.expediente.certificacion.model.catalog.TipoActualizacionCertificacion
import mx.amib.sistemas.expediente.persona.model.Sustentante


class Certificacion {
	
	Date fechaInicio
	Date fechaFin
	Date ultimaActualizacionStatusCertficacion
	String nombreUsuarioActualizo
	
	Date fechaCreacion
	Date fechaModificacion
	
	Figura figura
	StatusAutorizacion statusAutorizacion
	StatusCertificacion statusCertificacion
	TipoActualizacionCertificacion tipoActualizacionCertificacion
	
	Sustentante sustentante
	
	static belongsTo = [Sustentante]
	
	static hasMany = [ movimientosAutorizacion:MovimientoAutorizacion, eventosPuntos:EventoPuntos ]
	
	static mapping = {
		table 't201_t_certificacion'
		
		fechaInicio column:'fh_inicio'
		fechaFin column:'fh_fin'
		ultimaActualizacionStatusCertficacion column:'fh_actualcert'
		nombreUsuarioActualizo column:'tx_usuarioactualizo'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		figura column:'id_103f_figura'
		statusAutorizacion column:'id_205_stautorizacion'
		statusCertificacion column:'id_203_stcertificacion'
		tipoActualizacionCertificacion column:'id_204_tpactualcert'
		
		sustentante column:'id_101_sustentante'
		
		version false
	}
	
    static constraints = {
		nombreUsuarioActualizo maxSize:254
    }
}
