package mx.amib.sistemas.expediente.persona.model

import mx.amib.sistemas.expediente.persona.model.catalog.EstadoCivil
import mx.amib.sistemas.expediente.persona.model.catalog.Nacionalidad
import mx.amib.sistemas.expediente.persona.model.catalog.NivelEstudios
import mx.amib.sistemas.expediente.certificacion.model.Certificacion

class Sustentante {
	Integer numeroMatricula
	String nombre
	String primerApellido
	String segundoApellido
	String genero
	String rfc
	String curp
	Date fechaNacimiento
	String correoElectronico
	String calidadMigratoria
	String profesion
	
	String calle
	String numeroExterior
	String numeroInterior
	Long idSepomex
	
	Date fechaCreacion
	Date fechaModificacion
	
	Nacionalidad nacionalidad
	NivelEstudios nivelEstudios
	EstadoCivil estadoCivil
	Long idNacionalidad
	Long idNivelEstudios
	Long idEstadoCivil
	
	Set telefonos = []
	Set documentos = []
	Set puestos = []
	Set certificaciones = []
	
	static hasMany = [ telefonos:TelefonoSustentante , documentos:DocumentoSustentante, puestos:Puesto, certificaciones:Certificacion ]
	
	static transients = ['idNacionalidad','idNivelEstudios','idEstadoCivil']
		
	static mapping = {
		table 't101_t_sustentante'
		
		id generator: "assigned"
		
		numeroMatricula column:'nu_matricula'
		nombre column:'nb_nombre'
		primerApellido column:'nb_apellido1'
		segundoApellido column:'nb_apellido2'
		genero column:'st_genero'
		rfc column:'tx_rfc'
		curp column:'tx_curp'
		fechaNacimiento column:'fh_nacimiento'
		correoElectronico column:'tx_correoe'
		calidadMigratoria column:'tx_calmigratoria'
		profesion column:'tx_profesion'
		calle column:'tx_calle'
		numeroExterior column:'tx_numext'
		numeroInterior column:'tx_numint'
		idSepomex column:'id_f_sepomex'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		nacionalidad column:'id_107f_nacionalidad'
		nivelEstudios column:'id_108f_nivelestudios'
		estadoCivil column:'id_109f_edocivil'
		
		version false
	}
	
    static constraints = {
		id bindable: true
		
		idNacionalidad bindable: true
		idNivelEstudios bindable: true
		idEstadoCivil bindable: true
		
		numeroMatricula unique: true
		nombre maxSize: 100
		primerApellido maxSize: 80
		segundoApellido nullable: true, maxSize: 80
		genero maxSize: 1
		rfc nullable: true, maxSize: 13
		curp nullable: true, maxSize: 18
		fechaNacimiento nullable: true
		correoElectronico nullable: true, maxSize: 254
		
		nacionalidad nullable: true
		
		fechaCreacion nullable: true
		fechaModificacion nullable: true
    }
}
