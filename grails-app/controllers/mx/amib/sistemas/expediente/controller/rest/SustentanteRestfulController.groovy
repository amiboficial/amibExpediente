package mx.amib.sistemas.expediente.controller.rest

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.persona.model.Sustentante;
import grails.rest.RestfulController
import grails.transaction.Transactional

@Transactional(readOnly = false)
class SustentanteRestfulController extends RestfulController{

    static responseFormats = ['json', 'xml']
	
	SustentanteRestfulController() {
		super(Sustentante)
	}
	
	def obtenerSustentantePorMatricula(long id) {
		respond Sustentante.findByNumeroMatricula(id)
	}
}
