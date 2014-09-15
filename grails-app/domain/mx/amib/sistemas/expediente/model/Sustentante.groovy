package mx.amib.sistemas.expediente.model

class Sustentante {
	
	int numeroMatricula
	String nombre
	String primerApellido
	String segundoApellido
	String genero
	String rfc
	String curp
	Date fechaNacimiento
	
	Date fechaCreacion
	Date fechaModificacion
	
	static mapping = {
		table 't001_t_sustentante'
		
		id generator: "identity"
		
		numeroMatricula column:'nu_matricula'
		nombre column:'nb_nombre'
		primerApellido column:'nb_apellido1'
		segundoApellido column:'nb_apellido2'
		genero column:'st_genero'
		rfc column:'tx_rfc'
		curp column:'tx_curp'
		fechaNacimiento column:'fh_nacimiento'
		
		fechaCreacion column:'fh_creacion'
		fechaModificacion column:'fh_modificacion'
		
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
		
		fechaCreacion nullable: true
		fechaModificacion nullable: true
    }
}
