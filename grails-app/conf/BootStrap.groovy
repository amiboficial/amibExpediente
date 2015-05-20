//import groovyx.net.http.ParserRegistry

class BootStrap {
    def init = { servletContext -> 
		def springContext = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext( servletContext )
		springContext.getBean( "syncCatalogosService" ).sincronizarCatalogos()
		springContext.getBean( "customObjectMarshallers" ).register()
    }
    def destroy = {
    }
}
