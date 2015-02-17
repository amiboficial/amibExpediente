package mx.amib.sistemas.expediente.certificacion.service

import grails.transaction.Transactional
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.service.SearchResult

@Transactional
class CertificacionService {
	
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
}
