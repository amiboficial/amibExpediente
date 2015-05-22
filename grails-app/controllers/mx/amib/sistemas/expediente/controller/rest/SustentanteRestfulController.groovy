package mx.amib.sistemas.expediente.controller.rest

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.certificacion.model.catalog.MetodoCertificacion;
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion;
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion;
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura;
import mx.amib.sistemas.expediente.persona.model.Sustentante
import mx.amib.sistemas.expediente.service.SustentanteService
import mx.amib.sistemas.expediente.persona.model.catalog.*
import grails.rest.RestfulController
import grails.transaction.Transactional
import groovy.json.JsonParser
import grails.converters.JSON

@Transactional(readOnly = false)
class SustentanteRestfulController extends RestfulController<Sustentante>{

    static responseFormats = ['json', 'xml']
	
	def sustentanteService
	
	SustentanteRestfulController(){
		super(Sustentante)
	}
	
	
	@Override
	protected Sustentante createResource() {
		Sustentante s = super.createResource()
		if (s.certificaciones) {
			s.certificaciones.each {c ->
				c.sustentante = s
				s.addToCertificaciones(c)
				if(c.idVarianteFigura != null && c.idVarianteFigura > 0)
					c.varianteFigura = VarianteFigura.get(c.idVarianteFigura)
			
				if(c.idStatusAutorizacion != null && c.idStatusAutorizacion > 0)
					c.statusAutorizacion = StatusAutorizacion.get(c.idStatusAutorizacion)
				
				if(c.idStatusCertificacion != null && c.idStatusCertificacion > 0)
					c.statusCertificacion = StatusCertificacion.get(c.idStatusCertificacion)
				
				if(c.idMetodoCertificacion != null && c.idMetodoCertificacion > 0)
					c.metodoCertificacion = MetodoCertificacion.get(c.idMetodoCertificacion)
		  }
		}
		if(s.telefonos){
			s.telefonos.each { t ->
			  
				t.sustentante = s
				s.addToTelefonos(t)
			  
				if(t.idTipoTelefonoSustentante != null && t.idTipoTelefonoSustentante > 0)
					t.tipoTelefonoSustentante = TipoTelefonoSustentante.get(t.idTipoTelefonoSustentante)
			}
		}
		if(s.puestos){
			s.puestos.each { p ->
				p.sustentante = s
				s.addToPuestos(p)
			}
		}
		if(s.idNacionalidad != null && s.idNacionalidad > 0){
			
		}
		if(s.idNivelEstudios != null && s.idNivelEstudios > 0){
			s.nivelEstudios = NivelEstudios.get(s.idNivelEstudios)
		}
		if(s.idEstadoCivil != null && s.idEstadoCivil > 0){
			s.estadoCivil = EstadoCivil.get(s.idEstadoCivil)
		}
		s
	}
	
	//TODO: Eliminar este método y estandarizar nombres
	def obtenerSustentantePorMatricula(long id) {
		respond Sustentante.findByNumeroMatricula(id)
	}
	
	def findByMatricula(long id) {
		respond Sustentante.findByNumeroMatricula(id)
	}
	
	//TODO: Eliminar este método y estandarizar nombres
	def obtenerSustentantesPorIds()
	{
		String idsArrayJson = params.'ids'
		def ids = JSON.parse(idsArrayJson)
		respond Sustentante.findAllByIdInList(ids)
	}
	
	def findAllByIds(){
		String idsArrayJson = params.'ids'
		def ids = JSON.parse(idsArrayJson)
		respond Sustentante.findAllByIdInList(ids)
	}
	
	def findAllByPalabraNombre(){
		String pnom = params.pnom?:"" // Palabra/Nombre
		
		Integer max = Math.min(Integer.parseInt(params.max?:'10'), 100)
		Integer offset = Integer.parseInt(params.offset?:'0')
		String sort = params.sort?:"id"
		String order = params.order?:"desc"
		
		respond sustentanteService.findAllByPalabraNombre(pnom,max,offset,sort,order)
	}
	
	def findAllAdvancedSearch(){
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		Long idfig = Long.parseLong(params.idfig?:"-1")
		Long stcert = Long.parseLong(params.stcert?:"-1")
		Long staut = Long.parseLong(params.staut?:"-1")
		
		Integer max = Math.min(Integer.parseInt(params.max?:'10'), 100)
		Integer offset = Integer.parseInt(params.offset?:'0')
		String sort = params.sort?:"id"
		String order = params.order?:"desc"
		
		respond sustentanteService.findAllAdvancedSearch(nom, ap1, ap2, idfig, stcert, staut,max,offset,sort,order)
	}
}
