package mx.amib.sistemas.expediente.certificacion.controller.rest

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacion
import grails.rest.RestfulController
import grails.transaction.Transactional
import grails.converters.JSON

class StatusAutorizacionRestfulController extends RestfulController{
	static responseFormats = ['json', 'xml']
    StatusAutorizacionRestfulController(){
		super(StatusAutorizacion)
	}
}
