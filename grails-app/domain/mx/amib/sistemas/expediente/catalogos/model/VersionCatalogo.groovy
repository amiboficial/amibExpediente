package mx.amib.sistemas.expediente.catalogos.model

class VersionCatalogo {

	Integer numeroCatalogo
	Integer numeroVersion
	
	static mapping = {
		table 't001_d_versioncat'
		
		id generator: "assigned"
		
		numeroCatalogo column:'nu_catalogo'
		numeroVersion column:'nu_version'
		
		version false
	}
	
    static constraints = {
    }
}
