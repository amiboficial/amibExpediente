package mx.amib.sistemas.expediente.certificacion.controller.rest

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date;

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.web.json.JSONObject

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.certificacion.model.EventoPuntos
import mx.amib.sistemas.expediente.certificacion.model.Validacion
import mx.amib.sistemas.expediente.persona.model.Sustentante
import mx.amib.sistemas.expediente.certificacion.model.catalog.*
import grails.rest.RestfulController
import grails.transaction.Transactional
import grails.converters.JSON

@Transactional(readOnly = false)
class CertificacionRestfulController extends RestfulController{

	static responseFormats = ['json', 'xml']
	
	def certificacionService
	def sustentanteService
	
	CertificacionRestfulController(){
		super(Certificacion)
	}
	
	def getWithSustentante(Long id){
		Map<String,Object> resultMap = new HashMap<String,Object>()
		
		try{
			Certificacion resultCertificacion = certificacionService.get(id)
			println("getwithsustentante lololo")
			println(resultCertificacion as JSON)
			Sustentante resultSustentante = resultCertificacion.sustentante
			resultMap.put("certificacion", resultCertificacion)
			resultMap.put("sustentante", resultSustentante)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond resultMap
	}
	
	//recibe en el request un JSON con todos los parametros
	def getAll(){
		Map<String,Object> resultMap = new HashMap<String,Object>()
		List<Long> idCertificacionList = null
		List<Certificacion> result = null
		List<Sustentante> resultSustentantes = null
		
		try{
			idCertificacionList = new ArrayList<Long>(request.JSON)
			result = certificacionService.getAll(idCertificacionList)
			resultSustentantes = result.collect{ it.sustentante }
			resultMap.put("certificaciones", result)
			resultMap.put("sustentantes", resultSustentantes)
		}
		catch(Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST)
		}
		
		respond resultMap
	}
	
	def searchByMatricula(){
		respond certificacionService.searchByMatricula(params)
	}
	
	def searchByFolio(){
		respond certificacionService.searchByFolio(params)
	}
	
	def searchByPalabraNombre(){
		respond certificacionService.searchByPalabraNombre(params)
	}
	
	def search(){
		respond certificacionService.search(params)
	}
	
	def findAllByFolioInList(){
		String foliosJson = params.'ids'
		def ids = JSON.parse(foliosJson)
		respond Certificacion.findAllByIdInList(ids)
	}
	def findAllEnDictamenPrevioByMatricula(Integer id){
		Integer numeroMatricula = id
		
		respond certificacionService.findAllEnDictamenPrevioByMatricula(numeroMatricula)
	}
	def findAllEnDictamenPrevioByFolio(Long id){
		Long idSustentante = id
		
		respond certificacionService.findAllEnDictamenPrevioByFolio(idSustentante)
	}
	def findAllEnDictamenPrevio(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		Long idfig = Long.parseLong(params.idfig?:"-1")
		Long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllEnDictamenPrevio(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	def findAllEnAutorizacionByMatricula(Integer id){
		Integer numeroMatricula = id
		respond certificacionService.findAllEnAutorizacionByMatricula(numeroMatricula)
	}
	def findAllEnAutorizacionByFolio(Long id){		
		Long idSustentante = id
		respond certificacionService.findAllEnAutorizacionByFolio(idSustentante)
	}
	def findAllEnAutorizacion(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		Long idfig = Long.parseLong(params.idfig?:"-1")
		Long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllEnAutorizacion(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	def findAllAutorizadosConOSinPoderesByMatricula(Integer id){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		Integer numeroMatricula = id
		
		respond certificacionService.findAllAutorizadosConOSinPoderesByMatricula(max, offset, sort, order, numeroMatricula)
	}
	def findAllAutorizadosConOSinPoderesByFolio(Long id){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		Long idSustentante = id
		
		respond certificacionService.findAllAutorizadosConOSinPoderesByFolio(max, offset, sort, order, idSustentante)
	}
	def findAllAutorizadosConOSinPoderes(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		Long idfig = Long.parseLong(params.idfig?:"-1")
		Long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllAutorizadosConOSinPoderes(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	
	def findAllCandidatoActualizacionAutorizacion(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		long idfig = Long.parseLong(params.idfig?:"-1")
		long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllCandidatoActualizacionAutorizacion(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	def findAllCandidatoActualizacionAutorizacionByMatricula(int id){
		int numeroMatricula = id
		respond certificacionService.findAllCandidatoActualizacionAutorizacionByMatricula(numeroMatricula)
	}
	def findAllCandidatoActualizacionAutorizacionByFolio(long id){
		long idSustentante = id
		respond certificacionService.findAllCandidatoActualizacionAutorizacionByFolio(idSustentante)
	}
	def findAllCandidatoReposicionAutorizacion(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		long idfig = Long.parseLong(params.idfig?:"-1")
		long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllCandidatoReposicionAutorizacion(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	def findAllCandidatoReposicionAutorizacionByMatricula(int id){
		int numeroMatricula = id
		respond certificacionService.findAllCandidatoReposicionAutorizacionByMatricula(numeroMatricula)
	}
	def findAllCandidatoReposicionAutorizacionByFolio(long id){
		long idSustentante = id
		respond certificacionService.findAllCandidatoReposicionAutorizacionByFolio(idSustentante)
	}
	def findAllCandidatoCambioFigura(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		long idfig = Long.parseLong(params.idfig?:"-1")
		long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllCandidatoCambioFigura(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	def findAllCandidatoCambioFiguraByMatricula(int id){
		int numeroMatricula = id
		respond certificacionService.findAllCandidatoCambioFiguraByMatricula(numeroMatricula)
	}
	def findAllCandidatoCambioFiguraByFolio(long id){
		long idSustentante = id
		respond certificacionService.findAllCandidatoCambioFiguraByFolio(idSustentante)
	}
	
	def findAllCandidatoCredencial(){
		int max = Integer.parseInt(params.max?:"10")
		int offset = Integer.parseInt(params.offset?:"0")
		String sort = params.sort?:"id"
		String order = params.order?:"asc"
		
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		long idfig = Long.parseLong(params.idfig?:"-1")
		long idvarfig = Long.parseLong(params.idvarfig?:"-1")
		
		respond certificacionService.findAllCandidatoCredencial(max, offset, sort, order, nom, ap1, ap2, idfig, idvarfig)
	}
	def findAllCandidatoCredencialByMatricula(int id){
		int numeroMatricula = id
		respond certificacionService.findAllCandidatoCredencialByMatricula(numeroMatricula)
	}
	def findAllCandidatoCredencialByFolio(long id){
		long idSustentante = id
		respond certificacionService.findAllCandidatoCredencialByFolio(idSustentante)
	}
	
	def updateDatosParaAprobarDictamen(){
		
		def newData = request.JSON
		Long id = newData.'id'
		Certificacion cert = Certificacion.get(id)
		
		if(cert == null){
			render status: NOT_FOUND
		}
		else{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
			
			cert.fechaObtencion = df.parse(newData.'fechaObtencion'.substring(0,10))
			cert.fechaInicio = df.parse(newData.'fechaInicio'.substring(0,10))
			cert.fechaFin = df.parse(newData.'fechaFin'.substring(0,10))
			
			cert.statusEntHistorialInforme = newData.'statusEntHistorialInforme'
			if(!JSONObject.NULL.equals(newData.'obsEntHistorialInforme')) cert.obsEntHistorialInforme = newData.'obsEntHistorialInforme'
			
			cert.statusEntCartaRec = newData.'statusEntCartaRec'
			if(!JSONObject.NULL.equals(newData.'obsEntCartaRec')) cert.obsEntCartaRec = newData.'obsEntCartaRec'
			
			cert.statusConstBolVal = newData.'statusConstBolVal'
			if(!JSONObject.NULL.equals(newData.'obsConstBolVal')) cert.obsConstBolVal = newData.'obsConstBolVal'
						
			cert = cert.save(flush: true)
		}
		
		respond cert
	}
	def updateDatosParaActualizarAutorizacion(){
		println("entro a updateDatosParaActualizarAutorizacion de certificacion")
		println("request")
		println(request.JSON	)
		def newData = request.JSON
		long id = newData.'certificacion'.'id'
		def certJson = newData.'certificacion'
		def valiJson = newData.'validacion'
		long metodoVali = -1
		
		Certificacion cert = Certificacion.get(id)
		Validacion val = new Validacion()
		
		if(cert == null){
			render status: NOT_FOUND
		}
		else{
			try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
			
			cert.fechaObtencion = df.parse(certJson.'fechaObtencion'.substring(0,10))
			cert.fechaInicio = df.parse(certJson.'fechaInicio'.substring(0,10))
			cert.fechaFin = df.parse(certJson.'fechaFin'.substring(0,10))
			
			//para la actualizacion de la autorizacion
			if(!JSONObject.NULL.equals(certJson.'fechaAutorizacionInicio')) cert.fechaAutorizacionInicio = df.parse(certJson.'fechaAutorizacionInicio'.substring(0,10))
			if(!JSONObject.NULL.equals(certJson.'fechaAutorizacionFin')) cert.fechaAutorizacionFin = df.parse(certJson.'fechaAutorizacionFin'.substring(0,10))
			
			//para el plazo de atencion del tramite
			if(!JSONObject.NULL.equals(certJson.'fechaEntregaRecepcion')) cert.fechaEntregaRecepcion = df.parse(certJson.'fechaEntregaRecepcion'.substring(0,10))
			if(!JSONObject.NULL.equals(certJson.'fechaEnvioComision')) cert.fechaEnvioComision = df.parse(certJson.'fechaEnvioComision'.substring(0,10))
			
			cert.statusEntHistorialInforme = certJson.'statusEntHistorialInforme'
			if(!JSONObject.NULL.equals(certJson.'obsEntHistorialInforme')) cert.obsEntHistorialInforme = certJson.'obsEntHistorialInforme'
			
			cert.statusEntCartaRec = certJson.'statusEntCartaRec'
			if(!JSONObject.NULL.equals(certJson.'obsEntCartaRec')) cert.obsEntCartaRec = certJson.'obsEntCartaRec'
			
			cert.statusConstBolVal = certJson.'statusConstBolVal'
			if(!JSONObject.NULL.equals(certJson.'obsConstBolVal')) cert.obsConstBolVal = certJson.'obsConstBolVal'
			
			if(!JSONObject.NULL.equals(valiJson.'fechaAplicacion')){ 
				val.fechaAplicacion = df.parse(valiJson.'fechaAplicacion'.substring(0,10))
			}else{
			val.fechaAplicacion = new Date()
			}
			if(!JSONObject.NULL.equals(valiJson.'fechaInicio')) val.fechaInicio = df.parse(valiJson.'fechaInicio'.substring(0,10))
			if(!JSONObject.NULL.equals(valiJson.'fechaFin')) val.fechaFin = df.parse(valiJson.'fechaFin'.substring(0,10))
			
			val.autorizadoPorUsuario = valiJson.'autorizadoPorUsuario'
			
			val.fechaCreacion = new Date()
			val.fechaModificacion = new Date()
			
			metodoVali = Long.parseLong(valiJson.'idMetodoValidacion'.toString())
			val.metodoValidacion = MetodoValidacion.get( metodoVali )
			if(val.metodoValidacion.descripcion == "Exámen"){
				//TODO: PASAR ID DE RESERVACION DE EXAMEN
			}
			else if(val.metodoValidacion.descripcion == "Puntos"){
				val.eventosPuntos = new ArrayList<EventoPuntos>()
				valiJson.'eventosPuntos'.each{ x ->
					EventoPuntos ep = new EventoPuntos()
					ep.puntaje = x.'puntaje'
					
					ep.fechaCreacion = new Date()
					ep.fechaModificacion = new Date()
					
					ep.validacion = val
					val.eventosPuntos.add(ep)
				}
			}
			
			val.certificacion = cert
			cert.validaciones.add(val)
			
			cert = cert.save(flush: true, failOnError : true)
		} catch (Exception e) {
		println("exception explained 333333")
			println(e?.message)
		}
		}
		
		respond cert
	}
	def createReponerAutorizacion(){
		def newData = request.JSON
		long id = newData.'certificacion'.'id'
		def certJson = newData.'certificacion'
		def valiJson = newData.'validacion'
		println("valiJson")
		println(valiJson)
		long metodoVali = -1
		
		Certificacion cert = Certificacion.get(id)
		Validacion val = new Validacion()
		
		if(cert == null){
			render status: NOT_FOUND
		}
		else{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
			
			//Setea parametros de certificación sobre la cual se "repondrá" la autorización
			//al "reponerse" una autorización se crea una nueva instancia de certificación
			//dado que el sustentante se "re-certifica".
			cert.isUltima = false
			cert.statusAutorizacion = StatusAutorizacion.get( StatusAutorizacionTypes.VENCIDA )
			cert.statusCertificacion = StatusCertificacion.get( StatusCertificacionTypes.CERTIFICADO_VENCIDO )
			cert.save(flush: true, failOnError : true)
			
			cert = cert.clone()
			//Se creará una nueva instancia (ya que se está obteniendo una nueva certificación)
			cert.id = null 
			
			cert.fechaObtencion = df.parse(certJson.'fechaObtencion'.substring(0,10))
			cert.fechaInicio = df.parse(certJson.'fechaInicio'.substring(0,10))
			cert.fechaFin = df.parse(certJson.'fechaFin'.substring(0,10))
			
			
			//para la actualizacion de la autorizacion
			if(!JSONObject.NULL.equals(certJson.'fechaAutorizacionInicio')) cert.fechaAutorizacionInicio = df.parse(certJson.'fechaAutorizacionInicio'.substring(0,10))
			if(!JSONObject.NULL.equals(certJson.'fechaAutorizacionFin')) cert.fechaAutorizacionFin = df.parse(certJson.'fechaAutorizacionFin'.substring(0,10))
			
			//para el plazo de atencion del tramite
			if(!JSONObject.NULL.equals(certJson.'fechaEntregaRecepcion')) cert.fechaEntregaRecepcion = df.parse(certJson.'fechaEntregaRecepcion'.substring(0,10))
			if(!JSONObject.NULL.equals(certJson.'fechaEnvioComision')) cert.fechaEnvioComision = df.parse(certJson.'fechaEnvioComision'.substring(0,10))
			
			
			cert.isUltima = true
			cert.statusAutorizacion = StatusAutorizacion.get( StatusAutorizacionTypes.DICTAMEN_PREVIO )
			cert.statusCertificacion = StatusCertificacion.get( StatusCertificacionTypes.CERTIFICADO )
			
			cert.statusEntHistorialInforme = certJson.'statusEntHistorialInforme'
			if(!JSONObject.NULL.equals(certJson.'obsEntHistorialInforme')) cert.obsEntHistorialInforme = certJson.'obsEntHistorialInforme'
			
			cert.statusEntCartaRec = certJson.'statusEntCartaRec'
			if(!JSONObject.NULL.equals(certJson.'obsEntCartaRec')) cert.obsEntCartaRec = certJson.'obsEntCartaRec'
			
			cert.statusConstBolVal = certJson.'statusConstBolVal'
			if(!JSONObject.NULL.equals(certJson.'obsConstBolVal')) cert.obsConstBolVal = certJson.'obsConstBolVal'
			
			if(!JSONObject.NULL.equals(valiJson.'fechaAplicacion')) val.fechaAplicacion = df.parse(valiJson.'fechaAplicacion'.substring(0,10))
			if(!JSONObject.NULL.equals(valiJson.'fechaInicio')) val.fechaInicio = df.parse(valiJson.'fechaInicio'.substring(0,10))
			if(!JSONObject.NULL.equals(valiJson.'fechaFin')) val.fechaFin = df.parse(valiJson.'fechaFin'.substring(0,10))
			
			val.autorizadoPorUsuario = valiJson.'autorizadoPorUsuario'
			
			val.fechaCreacion = new Date()
			val.fechaModificacion = new Date()
			
			val.metodoValidacion = MetodoValidacion.get( MetodosValidacionTypes.EXAMEN )
			//TODO: PASAR ID DE RESERVACION DE EXAMEN
			
			val.certificacion = cert
			cert.validaciones = new HashSet<Validacion>()
			cert.validaciones.add(val)
			
			cert = cert.save(flush: true, failOnError : true)
		}
		
		respond cert
	}
	def createCambioFigura(){
		def newData = request.JSON
		long id = newData.'certificacion'.'id'
		def certJson = newData.'certificacion'
		def valiJson = newData.'validacion'
		long metodoVali = -1
		
		Certificacion cert = Certificacion.get(id)
		Validacion val = new Validacion()
		
		if(cert == null){
			render status: NOT_FOUND
		}
		else{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
			
			//Setea parametros de certificación sobre la cual se "repondrá" la autorización
			//al "reponerse" una autorización se crea una nueva instancia de certificación
			//dado que el sustentante se "re-certifica".
			cert.isUltima = false
			cert.statusAutorizacion = StatusAutorizacion.get( StatusAutorizacionTypes.VENCIDA )
			cert.statusCertificacion = StatusCertificacion.get( StatusCertificacionTypes.CERTIFICADO_VENCIDO )
			cert.save(flush: true, failOnError : true)
			
			cert = cert.clone()
			//Se creará una nueva instancia (ya que se está obteniendo una nueva certificación)
			cert.id = null
			
			cert.varianteFigura = VarianteFigura.get( certJson.'idVarianteFigura' )
			
			cert.fechaObtencion = df.parse(certJson.'fechaObtencion'.substring(0,10))
			cert.fechaInicio = df.parse(certJson.'fechaInicio'.substring(0,10))
			cert.fechaFin = df.parse(certJson.'fechaFin'.substring(0,10))
			
			cert.isUltima = true
			cert.statusAutorizacion = StatusAutorizacion.get( StatusAutorizacionTypes.DICTAMEN_PREVIO )
			cert.statusCertificacion = StatusCertificacion.get( StatusCertificacionTypes.CERTIFICADO )
			
			
			//para la actualizacion de la autorizacion
			if(!JSONObject.NULL.equals(certJson.'fechaAutorizacionInicio')) cert.fechaAutorizacionInicio = df.parse(certJson.'fechaAutorizacionInicio'.substring(0,10))
			if(!JSONObject.NULL.equals(certJson.'fechaAutorizacionFin')) cert.fechaAutorizacionFin = df.parse(certJson.'fechaAutorizacionFin'.substring(0,10))
			
			//para el plazo de atencion del tramite
			if(!JSONObject.NULL.equals(certJson.'fechaEntregaRecepcion')) cert.fechaEntregaRecepcion = df.parse(certJson.'fechaEntregaRecepcion'.substring(0,10))
			if(!JSONObject.NULL.equals(certJson.'fechaEnvioComision')) cert.fechaEnvioComision = df.parse(certJson.'fechaEnvioComision'.substring(0,10))
			
			
			cert.statusEntHistorialInforme = certJson.'statusEntHistorialInforme'
			if(!JSONObject.NULL.equals(certJson.'obsEntHistorialInforme')) cert.obsEntHistorialInforme = certJson.'obsEntHistorialInforme'
			
			cert.statusEntCartaRec = certJson.'statusEntCartaRec'
			if(!JSONObject.NULL.equals(certJson.'obsEntCartaRec')) cert.obsEntCartaRec = certJson.'obsEntCartaRec'
			
			cert.statusConstBolVal = certJson.'statusConstBolVal'
			if(!JSONObject.NULL.equals(certJson.'obsConstBolVal')) cert.obsConstBolVal = certJson.'obsConstBolVal'
			
			if(!JSONObject.NULL.equals(valiJson.'fechaAplicacion')) val.fechaAplicacion = df.parse(valiJson.'fechaAplicacion'.substring(0,10))
			if(!JSONObject.NULL.equals(valiJson.'fechaInicio')) val.fechaInicio = df.parse(valiJson.'fechaInicio'.substring(0,10))
			if(!JSONObject.NULL.equals(valiJson.'fechaFin')) val.fechaFin = df.parse(valiJson.'fechaFin'.substring(0,10))
			
			val.autorizadoPorUsuario = valiJson.'autorizadoPorUsuario'
			
			val.fechaCreacion = new Date()
			val.fechaModificacion = new Date()
			
			val.metodoValidacion = MetodoValidacion.get( MetodosValidacionTypes.EXAMEN )
			//TODO: PASAR ID DE RESERVACION DE EXAMEN
			
			val.certificacion = cert
			cert.validaciones = new HashSet<Validacion>()
			cert.validaciones.add(val)
			
			cert = cert.save(flush: true, failOnError : true)
		}
		
		respond cert
	}
	
}
