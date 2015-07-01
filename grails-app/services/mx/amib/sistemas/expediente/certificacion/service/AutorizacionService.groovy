package mx.amib.sistemas.expediente.certificacion.service

import grails.transaction.Transactional
import mx.amib.sistemas.expediente.certificacion.model.*
import mx.amib.sistemas.expediente.certificacion.model.catalog.*


@Transactional
class AutorizacionService {

    def aprobarDictamen(List<Long> idCertificacionList) {
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.DICTAMEN_PREVIO){
				c.isAutorizado = false
				c.isApoderado = false
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.EN_AUTORIZACION)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def autorizar(List<Long> idCertificacionList) {
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.EN_AUTORIZACION){
				c.isAutorizado = true
				c.isApoderado = false
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def deshacerAutorizacionSinPoderes(List<Long> idCertificacionList){
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES){
				c.isAutorizado = false
				c.isApoderado = false
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.EN_AUTORIZACION)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def apoderar(List<Long> idCertificacionList) {
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES){
				c.isAutorizado = true
				c.isApoderado = true
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.AUTORIZADO)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def deshacerApoderar(List<Long> idCertificacionList){
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO){
				c.isAutorizado = true
				c.isApoderado = false
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def suspender(List<Long> idCertificacionList){
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO || 
				c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES){
				
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.SUSPENDIDA)
				c.isAutorizado = false
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def revocar(List<Long> idCertificacionList){
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO || 
				c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES){
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.REVOCADA)
				c.isAutorizado = false
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
	def deshacerRevocar(List<Long> idCertificacionList){
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.REVOCADA){
				//la autorización no debe estar vencida para cuando se quiera hacer la rectificación
				if( c.fechaFin <= Calendar.getInstance().getTime() ){
					c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.AUTORIZADO)
					c.isAutorizado = false
					c.save(flush:true)
					returnableIds.add(c.id)
				}
			}
		}
		return returnableIds
	}
	
	def expirar(List<Long> idCertificacionList){
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO || 
				c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES){
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.VENCIDA)
				c.isAutorizado = false
				c.save(flush:true)
				returnableIds.add(c.id)
			}
		}
		return returnableIds
	}
	
}
