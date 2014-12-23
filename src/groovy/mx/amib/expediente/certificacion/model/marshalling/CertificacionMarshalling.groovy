package mx.amib.expediente.certificacion.model.marshalling

import java.util.Date

import grails.converters.JSON
import mx.amib.expediente.certificacion.model.Certificacion
import mx.amib.expediente.certificacion.model.EventoPuntos
import mx.amib.expediente.certificacion.model.MovimientoAutorizacion
import mx.amib.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.expediente.certificacion.model.catalog.TipoActualizacionCertificacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

class CertificacionMarshalling {
	void register(){
		JSON.registerObjectMarshaller(Certificacion){ Certificacion obj ->
			return [
				id: obj.id,
				
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				ultimaActualizacionStatusCertficacion: obj.ultimaActualizacionStatusCertficacion,
				nombreUsuarioActualizo: obj.nombreUsuarioActualizo,

				figura: obj.figura,
				statusAutorizacion: obj.statusAutorizacion.descripcion,
				statusCertificacion: obj.statusCertificacion.descripcion,
				tipoActualizacionCertificacion: obj.tipoActualizacionCertificacion.descripcion,
				
				movimientosAutorizacion: obj.movimientosAutorizacion,
				eventosPuntos: obj.eventosPuntos,
				
				idSustentante: obj.sustentante.id
			]
		}
	}
}

class MovimientoAutorizacionMarshalling{
	void register(){
		JSON.registerObjectMarshaller(MovimientoAutorizacion){ MovimientoAutorizacion obj ->
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

class FiguraMarshalling {
	void register(){
		JSON.registerObjectMarshaller(VarianteFigura){ VarianteFigura obj ->
			return [
				descripcion: obj.descripcion,
				tipoAutorizacion: obj.tipoAutorizacion,
				iniciales: obj.iniciales,
				vigente: obj.vigente
			]
		}
	}
}

