package mx.amib.sistemas.expediente.certificacion.service

import grails.converters.JSON
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusAutorizacionTypes
import mx.amib.sistemas.expediente.certificacion.model.catalog.StatusCertificacionTypes
import mx.amib.sistemas.expediente.service.SearchResult
import spock.lang.*

/**
 *
 */
class CertificacionServiceIntegrationTestSpec extends Specification {

	CertificacionService certificacionService
	
	CertificacionService.SearchResult testResult1
	
    def setup() {
		
    }

    def cleanup() {
		
    }

	
	/*
    void "Obtener certificación por matrícula especificando parámetros"() {
		given:
			Long idSC = StatusCertificacionTypes.CERTIFICADO
			Long idSA = StatusAutorizacionTypes.DICTAMEN_PREVIO
		when:
			testResult1 = certificacionService.findAllByStatusAutorizacionAndNumeroMatricula(10, 0, "id", "asc", 
				111977, idSC, idSA )
			println (testResult1 as JSON)
		then:
			testResult1.count > 0
    }
	
	void "Obtener certificación por folio especificando parámetros"() {
		given:
			Long idSC = StatusCertificacionTypes.CERTIFICADO
			Long idSA = StatusAutorizacionTypes.DICTAMEN_PREVIO
		when:
			testResult1 = certificacionService.findAllByStatusAutorizacionAndIdSustentante(10, 0, "id", "asc",
				83486, idSC, idSA )
			println (testResult1 as JSON)
		then:
			testResult1.count > 0
	}
	
	void "Obtener certificación especificando todos los parámetros"() {
		given:
			String nom = "Carlos"
			String ap1 = ""
			String ap2 = ""
			Long idfig = null
			Long idvarfig = null
			Long idSC = StatusCertificacionTypes.CERTIFICADO
			Long idSA = StatusAutorizacionTypes.DICTAMEN_PREVIO
		when:
			testResult1 = certificacionService.findAllByStatusAutorizacion(10, 0, "id", "asc", nom, ap1, ap2, idfig, idvarfig, idSC, idSA )
			println (testResult1 as JSON)
		then:
			testResult1.count == 2
	}
	
	
	void "Obtener certificación en dictamen previo por matrícula"() {
		when:
			testResult1 = certificacionService.findAllEnDictamenPrevioByMatricula(111977)
			println (testResult1 as JSON)
		then:
			testResult1.count > 0
	}
	
	void "Obtener certificación en dictamen previo por folio"() {
		when:
			testResult1 = certificacionService.findAllEnDictamenPrevioByFolio(83486)
			println (testResult1 as JSON)
		then:
			testResult1.count > 0
	}*/
	
	void "Obtener credencializabes"(){
		given:
			String nom = ""
			String ap1 = ""
			String ap2 = ""
			Long idfig = null
			Long idvarfig = null
		when:
			testResult1 = certificacionService.findAllAutorizadosConOSinPoderes(10, 0, "id", "asc", nom, ap1, ap2, idfig, idvarfig)
			println (testResult1 as JSON)
		then:
			testResult1.count > 0
	}
}
