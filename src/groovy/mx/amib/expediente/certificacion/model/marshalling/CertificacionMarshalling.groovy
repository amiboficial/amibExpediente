package mx.amib.expediente.certificacion.model.marshalling

import java.util.Date

import grails.converters.JSON
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.certificacion.model.EventoPuntos
import mx.amib.sistemas.expediente.certificacion.model.AplicacionAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoValidacionAutorizacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

class CertificacionMarshalling {
	void register(){
		JSON.registerObjectMarshaller(Certificacion){ Certificacion obj ->
			return [
				id: obj.id,
				
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				fechaObtencion: obj.fechaObtencion,
				isAutorizado: obj.isAutorizado,
				isApoderado: obj.isApoderado,

				varianteFigura: obj.varianteFigura,
				statusAutorizacion: obj.statusAutorizacion,
				statusCertificacion: obj.statusCertificacion,
				idVarianteFigura: obj.varianteFigura?.id,
				idStatusAutorizacion: obj.statusAutorizacion?.id,
				idStatusCertificacion: obj.statusCertificacion?.id,
				
				aplicacionesAutorizacion: obj.aplicacionesAutorizacion,
								
				idSustentante: obj.sustentante?.id
			]
		}
	}
}

class AplicacionAutorizacionMarshalling{
	void register(){
		JSON.registerObjectMarshaller(AplicacionAutorizacion){ AplicacionAutorizacion obj ->
			return [
				id: obj.id,
				
				fechaAplicacion: obj.fechaAplicacion,
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				autorizadoPorUsuario: obj.autorizadoPorUsuario,
				eventosPuntos: obj.eventosPuntos,
				
				idCertificacion: obj.certificacion?.id
			]
		}
	}
}

class EventoPuntosMarshalling{
	void register(){
		JSON.registerObjectMarshaller(EventoPuntos){ EventoPuntos obj ->
			return [
				id: obj.id,
				
				idEvento: obj.idEvento,
				puntaje: obj.puntaje,
								
				idAplicacionAutorizacion: obj.aplicacionAutorizacion?.id,
				idCertificacion: obj.aplicacionAutorizacion?.certificacion?.id
			]
		}
	}
}

class VarianteFiguraMarshalling {
	void register(){
		JSON.registerObjectMarshaller(VarianteFigura){ VarianteFigura obj ->
			return [
				id: obj.id,
				nombre: obj.nombre,
				vigente: obj.vigente,
				numeroVersion: obj.numeroVersion,
				idFigura: obj.idFigura,
				nombreFigura: obj.nombreFigura,
				nombreAcuseFigura: obj.nombreAcuseFigura,
				esAutorizableFigura: obj.esAutorizableFigura,
				tipoAutorizacionFigura: obj.tipoAutorizacionFigura,
				inicialesFigura: obj.inicialesFigura
			]
		}
	}
}

