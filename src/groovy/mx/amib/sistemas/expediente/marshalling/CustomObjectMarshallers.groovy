package mx.amib.sistemas.expediente.marshalling

import java.util.List;

class CustomObjectMarshallers {
	List marshallers = []
	
	def register() {
		marshallers.each{ it.register() }
	}
}
