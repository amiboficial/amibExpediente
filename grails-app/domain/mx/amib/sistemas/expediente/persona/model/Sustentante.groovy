package mx.amib.sistemas.expediente.persona.model

import mx.amib.sistemas.expediente.persona.model.catalog.Nacionalidad
import mx.amib.sistemas.expediente.persona.model.catalog.NivelEstudios
import mx.amib.expediente.certificacion.model.Certificacion

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
	
	Date fechaCreacion
	Date fechaModificacion
	
	Nacionalidad nacionalidad
	NivelEstudios nivelEstudios
	
	static hasMany = [ telefonos:TelefonoSustentante , documentos:DocumentoSustentante, puestos:Puesto, certificaciones:Certificacion ]
	
	static mapping = {
		table 't101_t_sustentante'
		
		id generator: "identity"
		
		numeroMatricula column:'nu_matricula'
		nombre column:'nb_nombre'
		primerApellido column:'nb_apellido1'
		segundoApellido column:'nb_apellido2'
		genero column:'st_genero'
		rfc column:'tx_rfc'
		curp column:'tx_curp'
		fechaNacimiento column:'fh_nacimiento'
		correoElectronico column:'tx_correoe'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
		nacionalidad column:'id_107f_nacionalidad'
		nivelEstudios column:'id_108f_nivelestudios'
		
		version false
	}
	
    static constraints = {
		numeroMatricula unique: true
		nombre maxSize: 100
		primerApellido maxSize: 80
		segundoApellido nullable: true, maxSize: 80
		genero maxSize: 1
		rfc nullable: true, maxSize: 13
		curp nullable: true, maxSize: 18
		fechaNacimiento nullable: true
		correoElectronico nullable: true, maxSize: 254
		
		fechaCreacion nullable: true
		fechaModificacion nullable: true
    }
}
