package mx.amib.sistemas.expediente.controller.rest

import grails.rest.RestfulController;
import mx.amib.sistemas.expediente.persona.model.DocumentoSustentante
import mx.amib.sistemas.expediente.persona.model.Sustentante;
import mx.amib.sistemas.expediente.persona.model.catalog.TipoDocumentoSustentante;

class DocumentoSustentanteRestfulController extends RestfulController<DocumentoSustentante>{

    static responseFormats = ['json', 'xml']
	
	DocumentoSustentanteRestfulController(){
		super(DocumentoSustentante)
	}

	def updateDocumentosDeSustentante(long id){
		//DATA BINDING
		long idSustentante = id
		List<DocumentoSustentante> listDocs = new ArrayList<DocumentoSustentante>()
		List<DocumentoSustentante> currentlistDocs = new ArrayList<DocumentoSustentante>()
		def documentos = request.JSON
		
		println("request.JSON")
		printn(request.JSON)
		
		documentos.each{ x ->
			DocumentoSustentante ds = new DocumentoSustentante()
			ds.uuid = x.'uuid'
			ds.vigente = x.'vigente'
			ds.sustentante = DocumentoSustentante.get(idSustentante)
			ds.tipoDocumentoSustentate = TipoDocumentoSustentante.get(x.'idTipoDocumentoSustentate')
			listDocs.add(ds)
		}
		
		Sustentante s = Sustentante.get(idSustentante)
		
		s.documentos.each{ x -> 
			currentlistDocs.add(x)
		}
		currentlistDocs.each{ x->
			s.removeFromDocumentos( x )
			x.delete()
		}
		
		s = s.save(flush:true, failOnError:true)
		
		listDocs.each{ x ->
			s.addToDocumentos(x);
		}
		s.save(flush:true, failOnError:true)
		
		render(status: 201) //CREATED
	}
	
	
}
