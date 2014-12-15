// Place your Spring DSL code here
beans = {
	customObjectMarshallers( mx.amib.sistemas.expediente.marshalling.CustomObjectMarshallers ) {
		marshallers = [
			new mx.amib.expediente.persona.model.marshalling.SustentanteMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.TelefonoSustentanteMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.DocumentoSustentanteMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.PuestoMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.NacionalidadMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.NivelEstudiosMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.TipoDocumentoSustentateMarshalling(),
			new mx.amib.expediente.persona.model.marshalling.TipoTelefonoSustentanteMarshalling()
		]
	}
}
