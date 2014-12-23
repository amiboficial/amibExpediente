package mx.amib.sistemas.expediente.catalogos.model

class VersionCatalogo {

	Integer numeroCatalogo
	Integer numeroCatalogoForaneo
	Integer numeroVersion
	
	static mapping = {
		table 't001_d_versioncat'
		
		id generator: "assigned"
		
		numeroCatalogo column:'nu_cat'
		numeroCatalogoForaneo column:'nu_catforaneo'
		numeroVersion column:'nu_version'
		
		version false
	}
	
    static constraints = {
    }
}
