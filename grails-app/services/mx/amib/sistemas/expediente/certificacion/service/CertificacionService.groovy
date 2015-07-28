package mx.amib.sistemas.expediente.certificacion.service

import grails.gorm.DetachedCriteria
import grails.transaction.Transactional
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.service.SearchResult
import mx.amib.sistemas.expediente.certificacion.model.catalog.*

@Transactional
class CertificacionService {

	List<Certificacion> getAll(List<Long> ids){
		return Certificacion.getAll(ids).findAll{ it != null }
	}
	
	def get(Long id){
		return Certificacion.get(id)
	}
		
	def searchByMatricula(def params){
		Integer matricula = params."matricula".toInteger()
		Long idFigura = Long.parseLong(params?."idFigura")
		Boolean vigente = Boolean.parseBoolean(params?."vigente")
		Integer max = params?."max"?.toInteger()
		Integer offset = params?."offset"?.toInteger()
		String sort = params."sort"
		String order = params."order"
		
		List<String> hqlFilters = new ArrayList<String>();
		String whereKeyword = "where ";
		Boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()
		
		if(max == null || max <= 0){
			max = 10
		}
		if(offset == null || offset <= 0){
			offset = 0
		}
		if(sort == null || sort == ""){
			sort = "id"
		}
		else if(sort == "numeroMatricula"){
			sort = "sustentante.numeroMatricula"
		}
		else if(sort == "folio"){
			sort = "sustentante.folio"
		}
		else if(["id","fechaInicio"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		
		if(matricula != null && matricula > 0){
			hqlFilters.add("c.sustentante.numeroMatricula = :matricula ")
			whereKeywordNeeded = true
			namedParameters.put("matricula",matricula)
		}
		if(idFigura != null && idFigura > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idFigura",idFigura)
		}
		if(vigente != null && vigente == true){
			hqlFilters.add("c.fechaInicio <= :fechaHoy ")
			hqlFilters.add("c.fechaFin >= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		else if(vigente != null && vigente == false){
			hqlFilters.add("c.fechaInicio >= :fechaHoy ")
			hqlFilters.add("c.fechaFin <= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		
		strHqlCount.append("select count(c.id) from Certificacion as c ")
		sbHql.append("from Certificacion as c ")
		if(whereKeywordNeeded){
			sbHql.append(whereKeyword)
			strHqlCount.append(whereKeyword)
			hqlFilters.each{
				if(it != hqlFilters.last()){
					sbHql.append(it).append("and ")
					strHqlCount.append(it).append("and ")
				}	
				else{
					sbHql.append(it)
					strHqlCount.append(it)
				}
			}
		}
		
		sbHql.append("order by c.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Certificacion.executeQuery(strHqlCount.toString(),namedParameters)[0]
		searchResult.list = Certificacion.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		return searchResult
	}
	
	def searchByFolio(def params){
		Long folio = Long.parseLong(params."folio")
		Long idFigura = Long.parseLong(params?."idFigura")
		Boolean vigente = Boolean.parseBoolean(params?."vigente")
		Integer max = params?."max"?.toInteger()
		Integer offset = params?."offset"?.toInteger()
		String sort = params."sort"
		String order = params."order"
		
		List<String> hqlFilters = new ArrayList<String>();
		String whereKeyword = "where ";
		Boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()
		
		if(max == null || max <= 0){
			max = 10
		}
		if(offset == null || offset <= 0){
			offset = 0
		}
		if(sort == null || sort == ""){
			sort = "id"
		}
		else if(sort == "numeroMatricula"){
			sort = "sustentante.numeroMatricula"
		}
		else if(sort == "folio"){
			sort = "sustentante.folio"
		}
		else if(["id","fechaInicio"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		/*Integer matricula = params."matricula"
		Integer idFigura = params."idFigura"
		Boolean vigente = params."vigente"*/
		if(folio != null && folio > 0){
			hqlFilters.add("c.sustentante.id = :folio ")
			whereKeywordNeeded = true
			namedParameters.put("folio",folio)
		}
		if(idFigura != null && idFigura > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idFigura",idFigura)
		}
		if(vigente != null && vigente == true){
			hqlFilters.add("c.fechaInicio <= :fechaHoy ")
			hqlFilters.add("c.fechaFin >= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		else if(vigente != null && vigente == false){
			hqlFilters.add("c.fechaInicio >= :fechaHoy ")
			hqlFilters.add("c.fechaFin <= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		
		strHqlCount.append("select count(c.id) from Certificacion as c ")
		sbHql.append("from Certificacion as c ")
		if(whereKeywordNeeded){
			sbHql.append(whereKeyword)
			strHqlCount.append(whereKeyword)
			hqlFilters.each{
				if(it != hqlFilters.last()){
					sbHql.append(it).append("and ")
					strHqlCount.append(it).append("and ")
				}
				else{
					sbHql.append(it)
					strHqlCount.append(it)
				}
			}
		}
		
		sbHql.append("order by c.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Certificacion.executeQuery(strHqlCount.toString(),namedParameters)[0]
		searchResult.list = Certificacion.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		return searchResult
	}
	
	def searchByPalabraNombre(def params){
		String palabra = params."palabra"
		Long idFigura = Long.parseLong(params?."idFigura")
		Boolean vigente = Boolean.parseBoolean(params?."vigente")
		Integer max = params?."max"?.toInteger()
		Integer offset = params?."offset"?.toInteger()
		String sort = params."sort"
		String order = params."order"
		
		List<String> hqlFilters = new ArrayList<String>();
		String whereKeyword = "where ";
		Boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()
		
		if(max == null || max <= 0){
			max = 10
		}
		if(offset == null || offset <= 0){
			offset = 0
		}
		if(sort == null || sort == ""){
			sort = "id"
		}
		else if(sort == "numeroMatricula"){
			sort = "sustentante.numeroMatricula"
		}
		else if(sort == "folio"){
			sort = "sustentante.folio"
		}
		else if(["id","fechaInicio"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		
		if(palabra != null && palabra != ""){
			hqlFilters.add("(c.sustentante.nombre like :palabra or c.sustentante.primerApellido like :palabra or c.sustentante.segundoApellido like :palabra) ")
			whereKeywordNeeded = true
			namedParameters.put("palabra",palabra+"%")
		}
		if(idFigura != null && idFigura > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idFigura",idFigura)
		}
		if(vigente != null && vigente == true){
			hqlFilters.add("c.fechaInicio <= :fechaHoy ")
			hqlFilters.add("c.fechaFin >= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		else if(vigente != null && vigente == false){
			hqlFilters.add("c.fechaInicio >= :fechaHoy ")
			hqlFilters.add("c.fechaFin <= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		
		strHqlCount.append("select count(c.id) from Certificacion as c ")
		sbHql.append("from Certificacion as c ")
		if(whereKeywordNeeded){
			sbHql.append(whereKeyword)
			strHqlCount.append(whereKeyword)
			hqlFilters.each{
				if(it != hqlFilters.last()){
					sbHql.append(it).append("and ")
					strHqlCount.append(it).append("and ")
				}
				else{
					sbHql.append(it)
					strHqlCount.append(it)
				}
			}
		}
		
		sbHql.append("order by c.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Certificacion.executeQuery(strHqlCount.toString(),namedParameters)[0]
		searchResult.list = Certificacion.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		return searchResult
	}
	
	def search(def params){
		Long idFigura = Long.parseLong(params?."idFigura")
		Boolean vigente = Boolean.parseBoolean(params?."vigente")
		Integer max = params?."max"?.toInteger()
		Integer offset = params?."offset"?.toInteger()
		String sort = params."sort"
		String order = params."order"
		
		List<String> hqlFilters = new ArrayList<String>();
		String whereKeyword = "where ";
		Boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()
		
		if(max == null || max <= 0){
			max = 10
		}
		if(offset == null || offset <= 0){
			offset = 0
		}
		if(sort == null || sort == ""){
			sort = "id"
		}
		else if(sort == "numeroMatricula"){
			sort = "sustentante.numeroMatricula"
		}
		else if(sort == "folio"){
			sort = "sustentante.folio"
		}
		else if(["id","fechaInicio"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		if(idFigura != null && idFigura > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idFigura",idFigura)
		}
		if(vigente != null && vigente == true){
			hqlFilters.add("c.fechaInicio <= :fechaHoy ")
			hqlFilters.add("c.fechaFin >= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		else if(vigente != null && vigente == false){
			hqlFilters.add("c.fechaInicio >= :fechaHoy ")
			hqlFilters.add("c.fechaFin <= :fechaHoy ")
			whereKeywordNeeded = true
			namedParameters.put("fechaHoy",new Date())
		}
		
		strHqlCount.append("select count(c.id) from Certificacion as c ")
		sbHql.append("from Certificacion as c ")
		if(whereKeywordNeeded){
			sbHql.append(whereKeyword)
			strHqlCount.append(whereKeyword)
			hqlFilters.each{
				if(it != hqlFilters.last()){
					sbHql.append(it).append("and ")
					strHqlCount.append(it).append("and ")
				}
				else{
					sbHql.append(it)
					strHqlCount.append(it)
				}
			}
		}
		
		sbHql.append("order by c.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Certificacion.executeQuery(strHqlCount.toString(),namedParameters)[0]
		searchResult.list = Certificacion.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		return searchResult
	}
	
	SearchResult findAllEnDictamenPrevioByMatricula(Integer max, Integer offset, String sort, String order, Integer numeroMatricula){
		this.findAllByStatusAutorizacionAndNumeroMatricula(max, offset, sort, order, numeroMatricula,
															StatusCertificacionTypes.CERTIFICADO, 
															StatusAutorizacionTypes.DICTAMEN_PREVIO)
	}
	
	SearchResult findAllEnDictamenPrevioByFolio(Integer max, Integer offset, String sort, String order, Integer idSustentante){
		this.findAllByStatusAutorizacionAndIdSustentante(max, offset, sort, order, idSustentante, 
															StatusCertificacionTypes.CERTIFICADO, 
															StatusAutorizacionTypes.DICTAMEN_PREVIO)
	}

	SearchResult findAllEnDictamenPrevio(Integer max, Integer offset, String sort, String order, 
											String nom, String ap1, String ap2, 
											Long idfig, Long idvarfig){
		
		this.findAllByStatusAutorizacion(max, offset, sort, order, 
											nom, ap1, ap2, idfig, idvarfig, 
											StatusCertificacionTypes.CERTIFICADO, 
											StatusAutorizacionTypes.DICTAMEN_PREVIO)
	}
	
	SearchResult findAllByStatusAutorizacionAndNumeroMatricula(Integer max, Integer offset, String sort, String order, 
																Integer numeroMatricula, Long idStatusCertificacion, Long idStatusAutorizacion){
	
		long count = 0 
		List<Certificacion> lc = new ArrayList<Certificacion>()
		boolean error = false
		String errorDetails = ""
		SearchResult sr = new SearchResult()
		
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		try{
			count = Certificacion.executeQuery("select count(c.id) from Certificacion as c where c.sustentante.numeroMatricula = :nm and c.statusAutorizacion.id = :idSA and c.statusCertificacion = :idSC",
										[ nm : numeroMatricula , idSA : idStatusAutorizacion, idCA : idStatusCertificacion ])[0]
			lc = Certificacion.findAll("from Certificacion as c where c.sustentante.numeroMatricula = :nm and c.statusAutorizacion.id = :idSA and c.statusCertificacion = :idSC order by " + sort + " " + order, 
										[ nm : numeroMatricula, idSA : idStatusAutorizacion, idCA : idStatusCertificacion ],
										[ max: max, offset: offset ])
		}
		catch(Exception e){
			error = true
			errorDetails = e.message
		}
		finally{
			sr.count = count
			sr.list = lc
			sr.error = error
			sr.errorDetails = errorDetails
		}
		
		return sr
	
	}
	
	SearchResult findAllByStatusAutorizacionAndIdSustentante(Integer max, Integer offset, String sort, String order, 
																Long idSustentante, Long idStatusCertificacion, Long idStatusAutorizacion){
		
		long count = 0
		List<Certificacion> lc = new ArrayList<Certificacion>()
		boolean error = false
		String errorDetails = ""
		SearchResult sr = new SearchResult()
		
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		try{
			count = Certificacion.executeQuery("select count(c.id) from Certificacion as c where c.sustentante.id = :id and c.statusAutorizacion.id = :idSA and c.statusCertificacion = :idSC",
										[ id : idSustentante , idSA : idStatusAutorizacion, idCA : idStatusCertificacion ])[0]
			lc = Certificacion.findAll("from Certificacion as c where c.sustentante.id = :id and c.statusAutorizacion.id = :idSA and c.statusCertificacion = :idSC order by " + sort + " " + order,
										[ id : idSustentante , idSA : idStatusAutorizacion, idCA : idStatusCertificacion ],
										[ max: max, offset: offset ])
		}
		catch(Exception e){
			error = true
			errorDetails = e.message
		}
		finally{
			sr.count = count
			sr.list = lc
			sr.error = error
			sr.errorDetails = errorDetails
		}
		
		return sr
	}
									
	SearchResult findAllByStatusAutorizacion(Integer max, Integer offset, String sort, String order, 
												String nom, String ap1, String ap2, 
												Long idfig, Long idvarfig, 
												Long idStatusCertificacion, 
												Long idStatusAutorizacion){
		
		long count = 0
		List<Certificacion> lc = new ArrayList<Certificacion>()
		boolean error = false
		String errorDetails = ""
		SearchResult sr = new SearchResult()
		
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)
		
		def whereCriteria = new DetachedCriteria(Certificacion).build {
			if(nom != null && nom.trim().compareTo("") != 0){
				ilike("sustentante.nombre",nom + '%')
			}
			if(ap1 != null && ap1.trim().compareTo("") != 0){
				ilike("sustentante.primerApellido",ap1 + '%')
			}
			if(ap2 != null && ap2.trim().compareTo("") != 0){
				ilike("sustentante.segundoApellido",ap2 + "%")
			}
			if(idvarfig != null && idvarfig > 0){
				eq("sustentante.varianteFigura.id",idvarfig)
			}
			else if(idfig != null && idfig > 0){
				eq("sustentante.varianteFigura.idFigura",idfig)
			}
			eq("sustentante.statusCertificacion.id", idStatusCertificacion)
			eq("sustentante.statusAutorizacion.id", idStatusAutorizacion)
		}
		
		try{
			sr.list = whereCriteria.list(max:max, offset: offset, sort: sort, order: order)
			sr.count = whereCriteria.count()
			sr.error = false
		}
		catch(Exception e){
			sr.error = true
			sr.errorDetails = e.message
		}
				
		return sr
											
	}
											
	private int filterMax(int max){
		if(max <= 0){
			max = 10
		}
		return max
	}
	private int filterOffset(int offset){
		if(offset <= 0){
			offset = 0
		}
		return offset
	}
	private String filterSort(String sort){
		if(sort == null || sort == ""){
			sort = "id"
		}
		else if(sort == "numeroMatricula"){
			sort = "sustentante.numeroMatricula"
		}
		else if(sort == "folio"){
			sort = "sustentante.id"
		}
		else if(sort == "idFigura"){
			sort = "sustentante.varianteFigura.idFigura"
		}
		else if(["id","fechaInicio"].find{ sort == it } == null){
			sort = "id"
		}
		return sort
	}
	private String filterOrder(String order){
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		return order
	}
	
	
}
