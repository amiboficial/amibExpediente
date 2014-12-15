package mx.amib.expediente.persona.model.marshalling

import java.util.Date;

import grails.converters.JSON
import mx.amib.sistemas.expediente.persona.model.DocumentoSustentante
import mx.amib.sistemas.expediente.persona.model.Puesto
import mx.amib.sistemas.expediente.persona.model.Sustentante
import mx.amib.sistemas.expediente.persona.model.TelefonoSustentante
import mx.amib.sistemas.expediente.persona.model.catalog.Nacionalidad
import mx.amib.sistemas.expediente.persona.model.catalog.NivelEstudios
import mx.amib.sistemas.expediente.persona.model.catalog.TipoDocumentoSustentate
import mx.amib.sistemas.expediente.persona.model.catalog.TipoTelefonoSustentante

class SustentanteMarshalling {
	void register(){
		JSON.registerObjectMarshaller(Sustentante){ Sustentante obj ->
			return [
				id: obj.id,
				
				numeroMatricula: obj.numeroMatricula,
				nombre: obj.nombre,
				primerApellido: obj.primerApellido,
				segundoApellido: obj.segundoApellido,
				genero: obj.genero,
				rfc: obj.rfc,
				curp: obj.curp,
				fechaNacimiento: obj.fechaNacimiento,
				correoElectronico: obj.correoElectronico,
				
				nacionalidad: obj.nacionalidad,
				nivelEstudios: obj.nivelEstudios,
				
				telefonos:obj.telefonos, 
				documentos:obj.documentos, 
				puestos:obj.puestos, 
				certificaciones:obj.certificaciones
			]
		}
	}
}

class TelefonoSustentanteMarshalling {
	void register(){
		JSON.registerObjectMarshaller(TelefonoSustentante){ TelefonoSustentante obj ->
			return [
				lada: obj.lada,
				telefono: obj.telefono,
				extension: obj.extension,
			
				tipoTelefonoSustentante: obj.tipoTelefonoSustentante,
			]
		}
	}
}

class DocumentoSustentanteMarshalling {
	void register(){
		JSON.registerObjectMarshaller(DocumentoSustentante){ DocumentoSustentante obj ->
			return [
				uuid: obj.uuid,
				vigente: obj.vigente,
				
				tipoDocumentoSustentate: obj.tipoDocumentoSustentate
			]
		}
	}
}

class PuestoMarshalling{
	void register(){
		JSON.registerObjectMarshaller(Puesto){ Puesto obj ->
			return [
				empresa: obj.empresa,
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				nombrePuesto: obj.nombrePuesto,
				esActual: obj.esActual
			]
		}
	}
}

class NacionalidadMarshalling{
	void register(){
		JSON.registerObjectMarshaller(Nacionalidad){ Nacionalidad obj ->
			return [
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}

class NivelEstudiosMarshalling{
	void register(){
		JSON.registerObjectMarshaller(NivelEstudios){ NivelEstudios obj ->
			return [
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}

class TipoDocumentoSustentateMarshalling{
	void register(){
		JSON.registerObjectMarshaller(TipoDocumentoSustentate){ TipoDocumentoSustentate obj ->
			return [
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}

class TipoTelefonoSustentanteMarshalling{
	void register(){
		JSON.registerObjectMarshaller(TipoTelefonoSustentante){ TipoTelefonoSustentante obj ->
			return [
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}