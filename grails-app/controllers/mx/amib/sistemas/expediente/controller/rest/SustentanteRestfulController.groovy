package mx.amib.sistemas.expediente.controller.rest

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.model.Sustentante
import grails.rest.RestfulController
import grails.transaction.Transactional

@Transactional(readOnly = false)
class SustentanteRestfulController extends RestfulController{

    static responseFormats = ['json', 'xml']
	
	SustentanteRestfulController() {
		super(Sustentante)
	}
	
}
