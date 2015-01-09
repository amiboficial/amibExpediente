package mx.amib.expediente.certificacion.model.marshalling

import java.util.Date

import grails.converters.JSON
import mx.amib.expediente.certificacion.model.Certificacion
import mx.amib.expediente.certificacion.model.EventoPuntos
import mx.amib.expediente.certificacion.model.CambioStatus
import mx.amib.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.expediente.certificacion.model.catalog.MetodoCertificacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

class CertificacionMarshalling {
	void register(){
		JSON.registerObjectMarshaller(Certificacion){ Certificacion obj ->
			return [
				id: obj.id,
				
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				ultimaActualizacionStatusCertficacion: obj.fechaObtencion,
				nombreUsuarioActualizo: obj.nombreUsuarioActualizo,

				figura: obj.varianteFigura,
				statusAutorizacion: obj.statusAutorizacion.descripcion,
				statusCertificacion: obj.statusCertificacion.descripcion,
				tipoActualizacionCertificacion: obj.metodoCertificacion.descripcion,
				
				movimientosAutorizacion: obj.movimientosAutorizacion,
				eventosPuntos: obj.eventosPuntos,
				
				idSustentante: obj.sustentante.id
			]
		}
	}
}

class CambioStatusMarshalling{
	void register(){
		JSON.registerObjectMarshaller(CambioStatus){ CambioStatus obj ->
			return [
				fechaAutorizacion: obj.fechaAutorizacion,
				autorizadoPorUsuario: obj.autorizadoPorUsuario,
				
				idCertificacion: obj.certificacion.id
			]
		}
	}
}

class EventoPuntosMarshalling{
	void register(){
		JSON.registerObjectMarshaller(EventoPuntos){ EventoPuntos obj ->
			return [
				idEvento: obj.idEvento,
				puntaje: obj.puntaje,
								
				idCertificacion: obj.certificacion.id
			]
		}
	}
}

class VarianteFiguraMarshalling {
	void register(){
		JSON.registerObjectMarshaller(VarianteFigura){ VarianteFigura obj ->
			return [
				id: obj.id,
				descripcion: obj.nombre,
				tipoAutorizacion: obj.tipoAutorizacionFigura,
				iniciales: obj.inicialesFigura,
				vigente: obj.vigente
			]
		}
	}
}

