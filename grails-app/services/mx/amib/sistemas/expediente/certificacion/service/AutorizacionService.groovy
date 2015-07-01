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
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
			c.save(flush:true)
		}
		return returnableIds
	}
	
	def apoderar(List<Long> idCertificacionList) {
		List<Certificacion> certificaciones = Certificacion.getAll(idCertificacionList)
		List<Long> returnableIds = new ArrayList<Long>()
		for(Certificacion c : certificaciones){
			if(c.statusAutorizacion.id.value == StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES){
				c.statusAutorizacion = StatusAutorizacion.get(StatusAutorizacionTypes.AUTORIZADO)
				c.save(flush:true)
				returnableIds.add(c.id)
			}
			c.save(flush:true)
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
				c.save(flush:true)
				returnableIds.add(c.id)
			}
			c.save(flush:true)
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
				c.save(flush:true)
				returnableIds.add(c.id)
			}
			c.save(flush:true)
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
				c.save(flush:true)
				returnableIds.add(c.id)
			}
			c.save(flush:true)
		}
		return returnableIds
	}
	
}
