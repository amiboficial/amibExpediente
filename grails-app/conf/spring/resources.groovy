// Place your Spring DSL code here
beans = {
	
	varianteFiguraSynchronizer( mx.amib.expediente.certificacion.model.datasync.VarianteFiguraSynchronizer ){
		restUrlVersionControl = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/"
		restUrlGetAllElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/list/"
		restUrlGetUpdatedElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/findAllByNumeroVersion/"
		restUrlGetExistingIds = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/varianteFiguraRestful/getExistingIds"
	}
	
	nacionalidadModelSynchronizer(mx.amib.sistemas.expediente.persona.model.datasync.NacionalidadModelSynchronizer){
		restUrlVersionControl = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/"
		restUrlGetAllElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nacionalidadRestful/list/"
		restUrlGetUpdatedElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nacionalidadRestful/findAllByNumeroVersion/"
		restUrlGetExistingIds = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nacionalidadRestful/getExistingIds"
	}
	
	nivelEstudiosModelSynchronizer(mx.amib.sistemas.expediente.persona.model.datasync.NivelEstudiosModelSynchronizer){
		restUrlVersionControl = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/"
		restUrlGetAllElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nivelEstudiosRestful/list/"
		restUrlGetUpdatedElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nivelEstudiosRestful/findAllByNumeroVersion/"
		restUrlGetExistingIds = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/nivelEstudiosRestful/getExistingIds"
	}
	
	estadoCivilModelSynchronizer(mx.amib.sistemas.expediente.persona.model.datasync.EstadoCivilModelSynchronizer){
		restUrlVersionControl = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/"
		restUrlGetAllElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/estadoCivilRestful/list/"
		restUrlGetUpdatedElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/estadoCivilRestful/findAllByNumeroVersion/"
		restUrlGetExistingIds = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/estadoCivilRestful/getExistingIds"
	}
	
	tipoDocumentoSustentanteModelSynchronizer(mx.amib.sistemas.expediente.persona.model.datasync.TipoDocumentoSustentanteModelSynchronizer){
		restUrlVersionControl = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/"
		restUrlGetAllElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoDocumentoSustentanteRestful/list/"
		restUrlGetUpdatedElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoDocumentoSustentanteRestful/findAllByNumeroVersion/"
		restUrlGetExistingIds = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoDocumentoSustentanteRestful/getExistingIds"
	}
	
	tipoTelefonoSustentanteModelSynchronizer(mx.amib.sistemas.expediente.persona.model.datasync.TipoTelefonoSustentanteModelSynchronizer){
		restUrlVersionControl = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/versionCatalogoRestful/obtenerUltimaVersion/"
		restUrlGetAllElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoTelefonoRestful/list/"
		restUrlGetUpdatedElements = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoTelefonoRestful/findAllByNumeroVersion/"
		restUrlGetExistingIds = "http://bimalatrop.no-ip.biz:8080/amibCatalogos-0.1/tipoTelefonoRestful/getExistingIds"
	}
	
	syncCatalogosService( mx.amib.expediente.catalogos.datasync.service.SyncCatalogosService ){
		sincronizadores = [
			varianteFiguraSynchronizer,
			nacionalidadModelSynchronizer,
			nivelEstudiosModelSynchronizer,
			estadoCivilModelSynchronizer,
			tipoDocumentoSustentanteModelSynchronizer,
			tipoTelefonoSustentanteModelSynchronizer
		]
	}
	
	customObjectMarshallers( mx.amib.sistemas.expediente.marshalling.CustomObjectMarshallers ) {
		marshallers = [
			new mx.amib.expediente.persona.model.marshalling.SustentanteMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.TelefonoSustentanteMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.DocumentoSustentanteMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.PuestoMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.NacionalidadMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.NivelEstudiosMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.TipoDocumentoSustentateMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.TipoTelefonoSustentanteMarshalling(),
			new mx.amib.expediente.certificacion.model.marshalling.CertificacionMarshalling(),
			new mx.amib.expediente.certificacion.model.marshalling.ValidacionMarshalling(),
			new mx.amib.expediente.certificacion.model.marshalling.EventoPuntosMarshalling(),
			new mx.amib.expediente.certificacion.model.marshalling.VarianteFiguraMarshalling()
		]
	}
}
