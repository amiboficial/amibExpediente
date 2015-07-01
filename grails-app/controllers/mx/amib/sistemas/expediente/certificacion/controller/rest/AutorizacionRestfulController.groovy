package mx.amib.sistemas.expediente.certificacion.controller.rest

import javax.servlet.http.HttpServletResponse

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
