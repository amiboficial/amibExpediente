package mx.amib.sistemas.expediente.certificacion.service

import org.hibernate.criterion.*

import grails.gorm.DetachedCriteria
import grails.transaction.Transactional
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.certificacion.model.catalog.*
import mx.amib.sistemas.expediente.persona.model.Sustentante

@Transactional
class CertificacionService {

	public static class SearchResult{
		List<Certificacion> list
		List<Sustentante> sustentantes
		long count
		boolean error
		String errorDetails
	}
	
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
	
	SearchResult findAllEnDictamenPrevioByMatricula(Integer numeroMatricula){
		return this.findAllByStatusAutorizacionAndNumeroMatricula(10, 0, "id", "asc", numeroMatricula,
															StatusCertificacionTypes.CERTIFICADO, 
															StatusAutorizacionTypes.DICTAMEN_PREVIO)
	}
	
	SearchResult findAllEnDictamenPrevioByFolio(Long idSustentante){
		return this.findAllByStatusAutorizacionAndIdSustentante(10, 0, "id", "asc", idSustentante, 
															StatusCertificacionTypes.CERTIFICADO, 
															StatusAutorizacionTypes.DICTAMEN_PREVIO)
	}

	SearchResult findAllEnDictamenPrevio(Integer max, Integer offset, String sort, String order, 
											String nom, String ap1, String ap2, 
											Long idfig, Long idvarfig){
		
		return this.findAllByStatusAutorizacion(max, offset, sort, order, 
											nom, ap1, ap2, idfig, idvarfig, 
											StatusCertificacionTypes.CERTIFICADO, 
											StatusAutorizacionTypes.DICTAMEN_PREVIO)
	}
											
	SearchResult findAllEnAutorizacionByMatricula(Integer numeroMatricula){
		return this.findAllByStatusAutorizacionAndNumeroMatricula(10, 0, "id", "asc", numeroMatricula,
															StatusCertificacionTypes.CERTIFICADO,
															StatusAutorizacionTypes.EN_AUTORIZACION)
	}
	
	SearchResult findAllEnAutorizacionByFolio(Long idSustentante){
		return this.findAllByStatusAutorizacionAndIdSustentante(10, 0, "id", "asc", idSustentante,
															StatusCertificacionTypes.CERTIFICADO,
															StatusAutorizacionTypes.EN_AUTORIZACION)
	}

	SearchResult findAllEnAutorizacion(Integer max, Integer offset, String sort, String order,
											String nom, String ap1, String ap2,
											Long idfig, Long idvarfig){
		
		return this.findAllByStatusAutorizacion(max, offset, sort, order,
											nom, ap1, ap2, idfig, idvarfig,
											StatusCertificacionTypes.CERTIFICADO,
											StatusAutorizacionTypes.EN_AUTORIZACION)
	}
	
	SearchResult findAllAutorizadosConOSinPoderesByMatricula(Integer numeroMatricula){
		return this.findAllByStatusAutorizacionAndNumeroMatricula(10, 0, "id", "asc", numeroMatricula,
															[ StatusCertificacionTypes.CERTIFICADO ],
															[ StatusAutorizacionTypes.AUTORIZADO, StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES ])
	}
	
	SearchResult findAllAutorizadosConOSinPoderesByFolio(Long idSustentante){
		return this.findAllByStatusAutorizacionAndIdSustentante(10, 0, "id", "asc", idSustentante,
															[ StatusCertificacionTypes.CERTIFICADO ],
															[ StatusAutorizacionTypes.AUTORIZADO, StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES ])
	}
	
	SearchResult findAllAutorizadosConOSinPoderes(Integer max, Integer offset, String sort, String order,
											String nom, String ap1, String ap2,
											Long idfig, Long idvarfig){
		
		return this.findAllByStatusAutorizacion(max, offset, sort, order,
											nom, ap1, ap2, idfig, idvarfig,
											[ StatusCertificacionTypes.CERTIFICADO ],
											[ StatusAutorizacionTypes.AUTORIZADO, StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES ])
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
			count = Certificacion.executeQuery("select count(c.id) from Certificacion as c where c.sustentante.numeroMatricula = :nm and c.statusAutorizacion.id = :idSA and c.statusCertificacion.id = :idSC",
										[ nm : numeroMatricula , idSA : idStatusAutorizacion, idSC : idStatusCertificacion ])[0]
			lc = Certificacion.findAll("from Certificacion as c where c.sustentante.numeroMatricula = :nm and c.statusAutorizacion.id = :idSA and c.statusCertificacion.id = :idSC order by " + sort + " " + order, 
										[ nm : numeroMatricula, idSA : idStatusAutorizacion, idSC : idStatusCertificacion ],
										[ max: max, offset: offset ])
		}
		catch(Exception e){
			error = true
			errorDetails = e.message
		}
		finally{
			sr.count = count
			sr.list = lc
			sr.sustentantes = sr.list.collect{ it.sustentante }
			sr.error = error
			sr.errorDetails = errorDetails
		}
		
		return sr
	
	}
	
	SearchResult findAllByStatusAutorizacionAndNumeroMatricula(Integer max, Integer offset, String sort, String order, 
																Integer numeroMatricula, Collection<Long> idsStCert, Collection<Long> idsStAut){
		
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
			count = Certificacion.executeQuery("select count(c.id) from Certificacion as c where c.sustentante.numeroMatricula = :numeroMatricula and c.statusAutorizacion.id in (:idSA) and c.statusCertificacion.id in (:idSC)",
										[ numeroMatricula : numeroMatricula , idSA : idsStAut, idSC : idsStCert ])[0]
			lc = Certificacion.findAll("from Certificacion as c where c.sustentante.numeroMatricula = :numeroMatricula and c.statusAutorizacion.id in (:idSA) and c.statusCertificacion.id in (:idSC) order by " + sort + " " + order,
										[ numeroMatricula : numeroMatricula , idSA : idsStAut, idSC : idsStCert ],
										[ max: max, offset: offset ])
		}
		catch(Exception e){
			error = true
			errorDetails = e.message
		}
		finally{
			sr.count = count
			sr.list = lc
			sr.sustentantes = sr.list.collect{ it.sustentante }
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
			count = Certificacion.executeQuery("select count(c.id) from Certificacion as c where c.sustentante.id = :id and c.statusAutorizacion.id = :idSA and c.statusCertificacion.id = :idSC",
										[ id : idSustentante , idSA : idStatusAutorizacion, idSC : idStatusCertificacion ])[0]
			lc = Certificacion.findAll("from Certificacion as c where c.sustentante.id = :id and c.statusAutorizacion.id = :idSA and c.statusCertificacion.id = :idSC order by " + sort + " " + order,
										[ id : idSustentante , idSA : idStatusAutorizacion, idSC : idStatusCertificacion ],
										[ max: max, offset: offset ])
		}
		catch(Exception e){
			error = true
			errorDetails = e.message
		}
		finally{
			sr.count = count
			sr.list = lc
			sr.sustentantes = sr.list.collect{ it.sustentante }
			sr.error = error
			sr.errorDetails = errorDetails
		}
		
		return sr
	}

	SearchResult findAllByStatusAutorizacionAndIdSustentante(Integer max, Integer offset, String sort, String order, 
																Long idSustentante, Collection<Long> idsStCert, Collection<Long> idsStAut){
													
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
			count = Certificacion.executeQuery("select count(c.id) from Certificacion as c where c.sustentante.id = :id and c.statusAutorizacion.id in (:idSA) and c.statusCertificacion.id in (:idSC)",
										[ id : idSustentante , idSA : idsStAut, idSC : idsStCert ])[0]
			lc = Certificacion.findAll("from Certificacion as c where c.sustentante.id = :id and c.statusAutorizacion.id in (:idSA) and c.statusCertificacion.id in (:idSC) order by " + sort + " " + order,
										[ id : idSustentante , idSA : idsStAut, idSC : idsStCert ],
										[ max: max, offset: offset ])
		}
		catch(Exception e){
			error = true
			errorDetails = e.message
		}
		finally{
			sr.count = count
			sr.list = lc
			sr.sustentantes = sr.list.collect{ it.sustentante }
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
		
		List<String> hqlFilters = new ArrayList<String>();
		String whereKeyword = "where ";
		Boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()
		
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)
		
		if(nom != null && nom.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.nombre like :nom ")
			whereKeywordNeeded = true
			namedParameters.put("nom",nom + '%')
		}
		if(ap1 != null && ap1.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.primerApellido like :ap1 ")
			whereKeywordNeeded = true
			namedParameters.put("ap1",ap1 + '%')
		}
		if(ap2 != null && ap2.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.segundoApellido like :ap2 ")
			whereKeywordNeeded = true
			namedParameters.put("ap2",ap2 + '%')
		}
		if(idvarfig != null && idvarfig > 0){
			hqlFilters.add("c.varianteFigura.id = :idVarFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idVarFigura",idvarfig)
		}
		else if(idfig != null && idfig > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idFigura",idfig)
		}
		if(idStatusCertificacion != null && idStatusCertificacion > 0){
			hqlFilters.add("c.statusCertificacion.id = :idSc ")
			whereKeywordNeeded = true
			namedParameters.put("idSc",idStatusCertificacion)
		}
		if(idStatusAutorizacion != null && idStatusAutorizacion > 0){
			hqlFilters.add("c.statusAutorizacion.id = :idSa ")
			whereKeywordNeeded = true
			namedParameters.put("idSa",idStatusAutorizacion)
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
		
		try{
			sr.count = Certificacion.executeQuery(strHqlCount.toString(),namedParameters)[0]
			sr.list = Certificacion.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
			sr.sustentantes = sr.list.collect{ it.sustentante }
		}
		catch(Exception e){
			sr.error = true
			sr.errorDetails = e.message
		}
		return sr
							
	}
	
	SearchResult findAllByStatusAutorizacion(Integer max, Integer offset, String sort, String order, 
												String nom, String ap1, String ap2, 
												Long idfig, Long idvarfig, 
												Collection<Long> idsStCert, 
												Collection<Long> idsStAut){
		long count = 0
		List<Certificacion> lc = new ArrayList<Certificacion>()
		boolean error = false
		String errorDetails = ""
		SearchResult sr = new SearchResult()
		
		List<String> hqlFilters = new ArrayList<String>();
		String whereKeyword = "where ";
		Boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()
		
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)
		
		if(nom != null && nom.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.nombre like :nom ")
			whereKeywordNeeded = true
			namedParameters.put("nom",nom + '%')
		}
		if(ap1 != null && ap1.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.primerApellido like :ap1 ")
			whereKeywordNeeded = true
			namedParameters.put("ap1",ap1 + '%')
		}
		if(ap2 != null && ap2.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.segundoApellido like :ap2 ")
			whereKeywordNeeded = true
			namedParameters.put("ap2",ap2 + '%')
		}
		if(idvarfig != null && idvarfig > 0){
			hqlFilters.add("c.varianteFigura.id = :idVarFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idVarFigura",idvarfig)
		}
		else if(idfig != null && idfig > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			whereKeywordNeeded = true
			namedParameters.put("idFigura",idfig)
		}
		if(idsStCert != null && idsStCert.size() > 0){
			hqlFilters.add("c.statusCertificacion.id in (:idSc) ")
			whereKeywordNeeded = true
			namedParameters.put("idSc",idsStCert)
		}
		if(idsStAut != null && idsStAut.size() > 0){
			hqlFilters.add("c.statusAutorizacion.id in (:idSa) ")
			whereKeywordNeeded = true
			namedParameters.put("idSa",idsStAut)
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
		
		try{
			sr.count = Certificacion.executeQuery(strHqlCount.toString(),namedParameters)[0]
			sr.list = Certificacion.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
			sr.sustentantes = sr.list.collect{ it.sustentante }
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
		else if(sort == "nombre"){
			sort = "sustentante.nombre"
		}
		else if(sort == "primerApellido"){
			sort = "sustentante.primerApellido"
		}
		else if(sort == "segundoApellido"){
			sort = "sustentante.segundoApellido"
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
