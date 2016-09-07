package mx.amib.sistemas.expediente.certificacion.controller.rest

import javax.servlet.http.HttpServletResponse
import groovy.json.JsonSlurper

import java.text.DateFormat
import java.text.SimpleDateFormat


class AutorizacionRestfulController {

	static responseFormats = ['json', 'xml']
	
	def autorizacionService
	
	def aprobarDictamen() {
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.aprobarDictamen(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST) 
		}
		
		respond idCertificacionListMod
	}
	
	def autorizar() {
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.autorizar(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def autorizarAltaOficio() {
		List<Long> idCertificacionList = null
		Date oficioDate = null
		List<Long> idCertificacionListMod = null
		try{
			println("autorizarAltaOficio"+request.JSON)
			
			def parseddat = request.JSON
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
			
			if(parseddat.alta.dateOficio!=null) oficioDate = df.parse(parseddat.alta.dateOficio.substring(0,10))
			idCertificacionList = (List<Long>) parseddat.alta.ids
			println("oficioDate")
			println(oficioDate)
			
			println("idCertificacionList")
			println(idCertificacionList)
			idCertificacionListMod = autorizacionService.autorizarNuevoOficio(idCertificacionList, oficioDate)
		}
		catch(Exception e){
			e.printStackTrace()
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def deshacerAutorizacionSinPoderes() {
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.deshacerAutorizacionSinPoderes(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def apoderar() {
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.apoderar(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def deshacerApoderar() {
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.deshacerApoderar(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def suspender(){
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.suspender(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def revocar(){
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.revocar(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def deshacerRevocar(){
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.deshacerRevocar(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}
	
	def expirar(){
		List<Long> idCertificacionList = null
		List<Long> idCertificacionListMod = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			idCertificacionListMod = autorizacionService.expirar(idCertificacionList)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond idCertificacionListMod
	}

}
