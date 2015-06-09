package mx.amib.expediente.datasync.test

import grails.converters.JSON
import grails.transaction.Transactional
import mx.amib.sistemas.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.expediente.certificacion.model.datasync.VarianteFiguraSynchronizer
import mx.amib.expediente.datasync.IModelSynchronizer
import mx.amib.sistemas.expediente.persona.model.datasync.EstadoCivilModelSynchronizer
import mx.amib.sistemas.expediente.persona.model.datasync.NacionalidadModelSynchronizer
import mx.amib.sistemas.expediente.persona.model.datasync.NivelEstudiosModelSynchronizer
import mx.amib.sistemas.expediente.persona.model.datasync.TipoDocumentoSustentanteModelSynchronizer
import mx.amib.sistemas.expediente.persona.model.datasync.TipoTelefonoSustentanteModelSynchronizer
import spock.lang.*

/**
 *
 */
class ModelSynchronizerSpec extends Specification {
	
	IModelSynchronizer modelSynchronizer
	def sustentanteService
	
	static transactional = false //si es false, aplica los cambios a la base; si es true, hace rollback inmediatamente
	
    def setup() {
		println "Starting tests"
    }

    def cleanup() {
    }

    public void "testSomething"() {
		given:
			/*
			modelSynchronizer = new VarianteFiguraSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/findAllByNumeroVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/getExistingIds")*/
		
			modelSynchronizer = new EstadoCivilModelSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/estadoCivilRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/estadoCivilRestful/findAllByNumeroVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/estadoCivilRestful/getExistingIds")
		
			long res = modelSynchronizer.checkRemoteVersion()
			println "+ show remote version"
			println res
			println "+ show results of download all elements"
			modelSynchronizer.downloadAllElements().each{
				println it.id+','+it.descripcion //nombre
			}
			println "+ show results of download certain version elements"
			modelSynchronizer.downloadUpdatedElements(1).each{
				println it.id+','+it.descripcion //nombre
			}
			println "+ show results of not-longer-existent elements"
			modelSynchronizer.getIdsToRemove([1,2,3,4,5,6,7,8,9,10]).each{
				println it
			}
			println "+ synchronizing catalog..."
			modelSynchronizer.synchronize(true)
		
			/*
			modelSynchronizer = new NacionalidadModelSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nacionalidadRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nacionalidadRestful/findAllByNumeroVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nacionalidadRestful/getExistingIds")
			*/
		
		/*
			modelSynchronizer = new NivelEstudiosModelSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nivelEstudiosRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nivelEstudiosRestful/findAllByNumeroVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nivelEstudiosRestful/getExistingIds")
		*/
		/*
			modelSynchronizer = new TipoDocumentoSustentanteModelSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoDocumentoSustentanteRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoDocumentoSustentanteRestful/findAllByNumeroVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoDocumentoSustentanteRestful/getExistingIds")
		*/
		/*
			modelSynchronizer = new TipoTelefonoSustentanteModelSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoTelefonoRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoTelefonoRestful/findAllByNumeroVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoTelefonoRestful/getExistingIds")
			long res = modelSynchronizer.checkRemoteVersion()
			println "+ show remote version"
			println res
			println "+ show results of download all elements"
			modelSynchronizer.downloadAllElements().each{
				println it.id+','+it.descripcion
			}
			println "+ show results of download certain version elements"
			modelSynchronizer.downloadUpdatedElements(1).each{
				println it.id+','+it.descripcion
			}
			println "+ show results of non-longer-existent elements"
			modelSynchronizer.getIdsToRemove([1,2,3,4,5,6,7,8,9,10]).each{
				println it
			}
			println "+ synchronizing catalog..."
			modelSynchronizer.synchronize(true)
		*/
		/*println (sustentanteService.findAllAdvancedSearch(null,null,null,-1,3,-1,10,0,"id","asc") as JSON)*/
		
		expect:
			1 == 1
    }
}
