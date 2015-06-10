package mx.amib.sistemas.expediente.marshalling

import java.util.List;

class CustomObjectMarshallers {
	List marshallers = []
	
	def register() {
		
		marshallers.each{ m ->
			println "REGISTRANDO MARSHALLER: " + m.toString(); 
			m.register();
		}
	}
}
