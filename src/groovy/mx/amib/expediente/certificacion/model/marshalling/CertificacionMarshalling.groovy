package mx.amib.expediente.certificacion.model.marshalling

import java.util.Date

import grails.converters.JSON
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.certificacion.model.EventoPuntos
import mx.amib.sistemas.expediente.certificacion.model.CambioStatus
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoCertificacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

class CertificacionMarshalling {
	void register(){
		JSON.registerObjectMarshaller(Certificacion){ Certificacion obj ->
			return [
				id: obj.id,
				
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				fechaObtencion: obj.fechaObtencion,
				nombreUsuarioActualizo: obj.nombreUsuarioActualizo,

				varianteFigura: obj.varianteFigura,
				statusAutorizacion: obj.statusAutorizacion?.descripcion,
				statusCertificacion: obj.statusCertificacion?.descripcion,
				metodoCertificacion: obj.metodoCertificacion?.descripcion,
				idStatusAutorizacion: obj.statusAutorizacion?.id,
				idStatusCertificacion: obj.statusCertificacion?.id,
				idMetodoCertificacion: obj.metodoCertificacion?.id,
				
				cambiosStatus: obj.cambiosStatus,
				eventosPuntos: obj.eventosPuntos,
				
				idSustentante: obj.sustentante?.id
			]
		}
	}
}

class CambioStatusMarshalling{
	void register(){
		JSON.registerObjectMarshaller(CambioStatus){ CambioStatus obj ->
			return [
				id: obj.id,
				
				fechaAutorizacion: obj.fechaAutorizacion,
				autorizadoPorUsuario: obj.autorizadoPorUsuario,
				
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
								
				idCertificacion: obj.certificacion?.id
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

