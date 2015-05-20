package mx.amib.expediente.catalogos.datasync.service

import grails.transaction.Transactional
import mx.amib.expediente.datasync.IModelSynchronizer

@Transactional
class SyncCatalogosService {

	IModelSynchronizer[] sincronizadores
	
    def sincronizarCatalogos() {
		sincronizadores.each { modelsync ->
			modelsync.synchronize(true)
		}
    }
	
}
