package mx.amib.expediente.certificacion.model.marshalling

import java.util.Date

import grails.converters.JSON
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.certificacion.model.EventoPuntos
import mx.amib.sistemas.expediente.certificacion.model.Validacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoValidacion
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
				isUltima: obj.isUltima,
				
				statusEntHistorialInforme: obj.statusEntHistorialInforme,
				obsEntHistorialInforme: obj.obsEntHistorialInforme,
				statusEntCartaRec: obj.statusEntCartaRec,
				obsEntCartaRec: obj.obsEntCartaRec,
				statusConstBolVal: obj.statusConstBolVal,
				obsConstBolVal: obj.obsConstBolVal,
				
				varianteFigura: obj.varianteFigura,
				statusAutorizacion: obj.statusAutorizacion,
				statusCertificacion: obj.statusCertificacion,
				idVarianteFigura: obj.varianteFigura?.id,
				idStatusAutorizacion: obj.statusAutorizacion?.id,
				idStatusCertificacion: obj.statusCertificacion?.id,
				
				validaciones: obj.validaciones,
								
				idSustentante: obj.sustentante?.id
				
				,fechaAutorizacionInicio: obj.fechaAutorizacionInicio
				,fechaAutorizacionFin: obj.fechaAutorizacionFin
				
				
				,fechaEntregaRecepcion: obj.fechaEntregaRecepcion
				,fechaEnvioComision: obj.fechaEnvioComision
			]
		}
	}
}

class ValidacionMarshalling{
	void register(){
		JSON.registerObjectMarshaller(Validacion){ Validacion obj ->
			return [
				id: obj.id,
				
				fechaAplicacion: obj.fechaAplicacion,
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				autorizadoPorUsuario: obj.autorizadoPorUsuario,
				eventosPuntos: obj.eventosPuntos,
				metodoValidacion: obj.metodoValidacion,
				idMetodoValidacion: obj.idMetodoValidacion,
				fechaModificacion: obj.fechaModificacion,
				
				idCertificacion: obj.certificacion?.id
			]
		}
	}
}


class MetodoValidacionMarshalling{
	void register(){
		JSON.registerObjectMarshaller(MetodoValidacion){ MetodoValidacion obj ->
			return [
				id: obj.id,
				descripcion: obj.descripcion,
				vigente: obj.vigente
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
								
				idValidacion: obj.validacion?.id,
				idCertificacion: obj.validacion?.certificacion?.id
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

