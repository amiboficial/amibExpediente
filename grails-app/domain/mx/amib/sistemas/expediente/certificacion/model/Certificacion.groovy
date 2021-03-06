package mx.amib.sistemas.expediente.certificacion.model

import java.util.Date
import groovy.transform.AutoClone
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoValidacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

@AutoClone
class Certificacion {
	
	
	
	Date fechaInicio
	Date fechaFin
	Date fechaObtencion
	Boolean isAutorizado
	Boolean isApoderado
	Boolean isUltima
	
	Long statusEntHistorialInforme
	String  obsEntHistorialInforme
	Long statusEntCartaRec
	String obsEntCartaRec
	Long statusConstBolVal
	String obsConstBolVal
	
	Date fechaCreacion
	Date fechaModificacion
	
	VarianteFigura varianteFigura
	StatusAutorizacion statusAutorizacion
	StatusCertificacion statusCertificacion
	Long idVarianteFigura
	Long idStatusAutorizacion
	Long idStatusCertificacion
	
	Sustentante sustentante
	
	Date fechaAutorizacionInicio
	Date fechaAutorizacionFin
	
	Date fechaEntregaRecepcion	
	Date fechaEnvioComision
	
	String usuarioCreacion
	String usuarioModificacion
	
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
		
		statusEntHistorialInforme column:'id_301_sthistinfo'
		obsEntHistorialInforme column:'tx_obshistinfo'
		statusEntCartaRec column:'id_301_stcartarec'
		obsEntCartaRec column:'tx_obscartarec'
		statusConstBolVal column:'id_301_stconstbolv'
		obsConstBolVal column:'tx_constbolv'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		varianteFigura column:'id_202f_varfigura'
		
		statusCertificacion column:'id_203_stcertificacion'
		statusAutorizacion column:'id_205_stautorizacion'
		
		sustentante column:'id_101_sustentante'
		
		fechaAutorizacionInicio column:'fh_autinicio'
		fechaAutorizacionFin column:'fh_autfin'
		
		fechaEntregaRecepcion column:'fh_entrega'
		fechaEnvioComision column:'fh_envio'
		
		usuarioCreacion column:'tx_usuario'
		usuarioModificacion column:'tx_ultusuariomod'
		
		version false
	}
	
    static constraints = {
		idVarianteFigura bindable:true
		idStatusAutorizacion bindable:true
		idStatusCertificacion bindable:true
		
		obsEntHistorialInforme nullable: true
		obsEntCartaRec nullable: true
		obsConstBolVal nullable: true
		fechaAutorizacionInicio nullable :true
		fechaAutorizacionFin nullable:true
		fechaEntregaRecepcion nullable :true
		fechaEnvioComision nullable:true
		usuarioCreacion nullable :true
		usuarioModificacion nullable:true
    }
}
