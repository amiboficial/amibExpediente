package mx.amib.expediente.datasync.test

import grails.converters.JSON
import grails.transaction.Transactional
import mx.amib.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.expediente.certificacion.model.datasync.VarianteFiguraSynchronizer
import mx.amib.expediente.datasync.IModelSynchronizer
import spock.lang.*

/**
 *
 */
class ModelSynchronizerSpec extends Specification {
	
	IModelSynchronizer modelSynchronizer
	static transactional = false //si es false, aplica los cambios a la base; si es true, hace rollback inmediatamente
	
    def setup() {
		println "Starting tests"
    }

    def cleanup() {
    }

    public void "testSomething"() {
		given:
			modelSynchronizer = new VarianteFiguraSynchronizer()
			modelSynchronizer.setRestUrlVersionControl("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/")
			modelSynchronizer.setRestUrlGetAllElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/list/")
			modelSynchronizer.setRestUrlGetUpdatedElements("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/findAllByVersion/")
			modelSynchronizer.setRestUrlGetExistingIds("http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/getExistingIds")
			long res = modelSynchronizer.checkRemoteVersion()
			println "+ show remote version"
			println res
			println "+ show results of download all elements"
			modelSynchronizer.downloadAllElements().each{
				println it.id+','+it.nombre
			}
			println "+ show results of download certain version elements"
			modelSynchronizer.downloadUpdatedElements(1).each{
				println it.id+','+it.nombre
			}
			println "+ show results of not-longer-existent elements"
			modelSynchronizer.getIdsToRemove([1,2,3,4,5,6,7,8,9,10]).each{
				println it
			}
			println "+ synchronizing catalog..."
			modelSynchronizer.synchronize(true)
		expect:
			res == 1
    }
}
