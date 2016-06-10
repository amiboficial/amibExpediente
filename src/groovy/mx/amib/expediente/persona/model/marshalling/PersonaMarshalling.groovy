package mx.amib.expediente.persona.model.marshalling

import java.util.Date;

import grails.converters.JSON
import mx.amib.sistemas.expediente.persona.model.DocumentoSustentante
import mx.amib.sistemas.expediente.persona.model.Puesto
import mx.amib.sistemas.expediente.persona.model.Sustentante
import mx.amib.sistemas.expediente.persona.model.TelefonoSustentante
import mx.amib.sistemas.expediente.persona.model.catalog.EstadoCivil
import mx.amib.sistemas.expediente.persona.model.catalog.Nacionalidad
import mx.amib.sistemas.expediente.persona.model.catalog.NivelEstudios
import mx.amib.sistemas.expediente.persona.model.catalog.TipoDocumentoSustentante
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
				calidadMigratoria: obj.calidadMigratoria,
				profesion: obj.profesion,
				calle: obj.calle,
				numeroExterior: obj.numeroExterior,
				numeroInterior: obj.numeroInterior,
				idSepomex: obj.idSepomex,
				
				nacionalidad: obj.nacionalidad,
				nivelEstudios: obj.nivelEstudios,
				estadoCivil: obj.estadoCivil,
				idNacionalidad: obj.nacionalidad?.id,
				idNivelEstudios: obj.nivelEstudios?.id,
				idEstadoCivil: obj.estadoCivil?.id,
				
				telefonos:obj.telefonos, 
				documentos:obj.documentos, 
				puestos:obj.puestos, 
				certificaciones:obj.certificaciones,
				asentamientoOtro: obj.asentamientoOtro
			]
		}
	}
}

class TelefonoSustentanteMarshalling {
	void register(){
		JSON.registerObjectMarshaller(TelefonoSustentante){ TelefonoSustentante obj ->
			return [
				id: obj.id,
				
				lada: obj.lada,
				telefono: obj.telefono,
				extension: obj.extension,
			
				tipoTelefonoSustentante: obj.tipoTelefonoSustentante,
				idTipoTelefonoSustentante: obj.tipoTelefonoSustentante?.id
			]
		}
	}
}

class DocumentoSustentanteMarshalling {
	void register(){
		JSON.registerObjectMarshaller(DocumentoSustentante){ DocumentoSustentante obj ->
			return [
				id: obj.id,
				
				uuid: obj.uuid,
				vigente: obj.vigente,
				
				tipoDocumentoSustentate: obj.tipoDocumentoSustentate,
				idTipoDocumentoSustentate: obj.tipoDocumentoSustentate.id
			]
		}
	}
}

class PuestoMarshalling{
	void register(){
		JSON.registerObjectMarshaller(Puesto){ Puesto obj ->
			return [
				id: obj.id,
				
				idInstitucion: obj.idInstitucion,
				fechaInicio: obj.fechaInicio,
				fechaFin: obj.fechaFin,
				nombrePuesto: obj.nombrePuesto,
				esActual: obj.esActual,
				statusEntManifProtesta: obj.statusEntManifProtesta,
				obsEntManifProtesta: obj.obsEntManifProtesta,
				statusEntCartaInter: obj.statusEntCartaInter,
				obsEntCartaInter: obj.obsEntCartaInter
			]
		}
	}
}

class NacionalidadMarshalling{
	void register(){
		JSON.registerObjectMarshaller(Nacionalidad){ Nacionalidad obj ->
			return [
				id: obj.id,
				
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
				id: obj.id,
				
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}

class EstadoCivilMarshalling{
	void register(){
		JSON.registerObjectMarshaller(EstadoCivil){ EstadoCivil obj ->
			return [
				id: obj.id,
				
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}

class TipoDocumentoSustentateMarshalling{
	void register(){
		JSON.registerObjectMarshaller(TipoDocumentoSustentante){ TipoDocumentoSustentante obj ->
			return [
				id: obj.id,
				
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
				id: obj.id,
				
				descripcion: obj.descripcion,
				vigente: obj.vigente
			]
		}
	}
}