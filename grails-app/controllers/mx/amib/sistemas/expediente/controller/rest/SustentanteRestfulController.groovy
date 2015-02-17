package mx.amib.sistemas.expediente.controller.rest

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.persona.model.Sustentante
import grails.rest.RestfulController
import grails.transaction.Transactional
import groovy.json.JsonParser
import grails.converters.JSON

@Transactional(readOnly = false)
class SustentanteRestfulController extends RestfulController{

	def sustenanteService
	
    static responseFormats = ['json', 'xml']
	
	SustentanteRestfulController() {
		super(Sustentante)
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
		
		respond sustenanteService.findAllByPalabraNombre(pnom,max,offset,sort,order)
	}
	
	def findAllAdvancedSearch(){
		String nom = params.nom?:""
		String ap1 = params.ap1?:""
		String ap2 = params.ap2?:""
		Integer idfig = Integer.parseInt(params.idfig?:"-1")
		Integer stcert = Integer.parseInt(params.stcert?:"-1")
		Integer staut = Integer.parseInt(params.staut?:"-1")
		
		Integer max = Math.min(Integer.parseInt(params.max?:'10'), 100)
		Integer offset = Integer.parseInt(params.offset?:'0')
		String sort = params.sort?:"id"
		String order = params.order?:"desc"
		
		
	}
}
