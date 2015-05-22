package mx.amib.sistemas.expediente.certificacion.controller.rest

import static org.springframework.http.HttpStatus.*
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacion
import grails.rest.RestfulController
import grails.transaction.Transactional
import grails.converters.JSON

class StatusCertificacionRestfulController extends RestfulController{
	static responseFormats = ['json', 'xml']
    StatusCertificacionRestfulController(){
		super(StatusCertificacion)
	}
}
