package mx.amib.sistemas.expediente.service

import grails.transaction.Transactional
import mx.amib.sistemas.expediente.certificacion.model.Certificacion
import mx.amib.sistemas.expediente.persona.model.Sustentante

@Transactional
class SustentanteService {
	final String getIdCertificacionActualQuery = "SELECT * FROM [dbo].[f201_getcertactual](:idSust)"
	
	def sessionFactory
	
	List<Sustentante> getAll(List<Long> ids){
		return Sustentante.getAll().findAll{ it != null }
	}
	
	SearchResult findAll(Integer max, Integer offset, String sort, String order){
		//Evitar sql injection en campos sort y order
		if(sort == null || sort == ""){
			sort = "id"
		}
		else if(["id","numeroMatricula","nombre","primerApellido","segundoApellido","rfc","curp","fechaNacimiento","correoElectronico"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		def searchResult = new SearchResult()
		searchResult.count = Sustentante.executeQuery("select count(s) from Sustentante as s")[0]
		searchResult.list = Sustentante.findAll("from Sustentante as s order by s." + sort + " " + order,[max:max, offset:offset])
		return searchResult
	}
	
    SearchResult findAllByPalabraNombre(String palabraNombre, Integer max, Integer offset, String sort, String order) {
		List<String> hqlFilters = new ArrayList<String>()
		String whereKeyword = "where "
		Boolean whereKeywordNeeded = false
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
		else if(["id","numeroMatricula","nombre","primerApellido","segundoApellido","rfc","curp","fechaNacimiento","correoElectronico"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		if(palabraNombre != null && palabraNombre != ""){
			hqlFilters.add("s.nombre like :palabraNombre ")
			hqlFilters.add("s.primerApellido like :palabraNombre ")
			hqlFilters.add("s.segundoApellido like :palabraNombre ")
			whereKeywordNeeded = true
			namedParameters.put("palabraNombre",palabraNombre+"%")
		}
		
		strHqlCount.append("select count(s) from Sustentante as s ")
		sbHql.append("from Sustentante as s ")
		
		if(whereKeywordNeeded){
			sbHql.append(whereKeyword)
			strHqlCount.append(whereKeyword)
			hqlFilters.each{
				if(it != hqlFilters.last()){
					sbHql.append(it).append("or ")
					strHqlCount.append(it).append("or ")
				}
				else{
					sbHql.append(it)
					strHqlCount.append(it)
				}
			}
		}
		sbHql.append("order by s.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Sustentante.executeQuery(strHqlCount.toString(), namedParameters)[0]
		searchResult.list = Sustentante.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		return searchResult
    }
	
	SearchResult findAllAdvancedSearch(String nom, String ap1, String ap2, Integer max, Integer offset, String sort, String order) {
		List<String> hqlFilters = new ArrayList<String>()
		String whereKeyword = "where "
		Boolean whereKeywordNeeded = false
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
		else if(["id","numeroMatricula","nombre","primerApellido","segundoApellido","rfc","curp","fechaNacimiento","correoElectronico"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		if(nom != null && nom != ""){
			hqlFilters.add("s.nombre like :nom ")
			whereKeywordNeeded = true
			namedParameters.put("nom",nom+"%")
		}
		if(ap1 != null && ap1 != ""){
			hqlFilters.add("s.primerApellido like :ap1 ")
			whereKeywordNeeded = true
			namedParameters.put("ap1",ap1+"%")
		}
		if(ap2 != null && ap2 != ""){
			hqlFilters.add("s.segundoApellido like :ap2 ")
			whereKeywordNeeded = true
			namedParameters.put("ap2",ap2+"%")
		}

		strHqlCount.append("select count(s.id) from Sustentante as s ")
		sbHql.append("from Sustentante as s ")
		
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
		sbHql.append("order by s.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Sustentante.executeQuery(strHqlCount.toString(), namedParameters)[0]
		searchResult.list = Sustentante.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		
		return searchResult
	}
	
	SearchResult findAllAdvancedSearchWithCertificacion(String nom, String ap1, String ap2, Long idfig, Long idvarfig, Long stcert, Long staut, Integer max, Integer offset, String sort, String order) {
		List<String> hqlFilters = new ArrayList<String>()
		String whereKeyword = "where "
		Boolean whereKeywordNeeded = false
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
		else if(["id","numeroMatricula","nombre","primerApellido","segundoApellido","rfc","curp","fechaNacimiento","correoElectronico"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		if(nom != null && nom != ""){
			hqlFilters.add("s.nombre like :nom ")
			whereKeywordNeeded = true
			namedParameters.put("nom",nom+"%")
		}
		if(ap1 != null && ap1 != ""){
			hqlFilters.add("s.primerApellido like :ap1 ")
			whereKeywordNeeded = true
			namedParameters.put("ap1",ap1+"%")
		}
		if(ap2 != null && ap2 != ""){
			hqlFilters.add("s.segundoApellido like :ap2 ")
			whereKeywordNeeded = true
			namedParameters.put("nom",ap2+"%")
		}

		
		if(idvarfig == null || idvarfig <= 0){
			if(idfig != null && idfig > 0){
				hqlFilters.add("ca.varianteFigura.idFigura = :idfig ")
				whereKeywordNeeded = true
				namedParameters.put("idfig",idfig)
			}
		}
		else{
			hqlFilters.add("ca.varianteFigura.id = :idvarfig ")
			whereKeywordNeeded = true
			namedParameters.put("idvarfig",idvarfig)
		}
		
		if(stcert != null && stcert > 0){
			hqlFilters.add("ca.statusCertificacion.id = :stcert ")
			whereKeywordNeeded = true
			namedParameters.put("stcert",stcert)
		}
		if(staut != null && staut > 0){
			hqlFilters.add("ca.statusAutorizacion.id = :staut ")
			whereKeywordNeeded = true
			namedParameters.put("staut",staut)
		}
		
		strHqlCount.append("select count(distinct s.id) from Sustentante as s inner join s.certificaciones ca with ca.isUltima = true ")
		sbHql.append("select distinct s from Sustentante as s inner join s.certificaciones ca with ca.isUltima = true ")
		
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
		sbHql.append("order by s.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Sustentante.executeQuery(strHqlCount.toString(), namedParameters)[0]
		searchResult.list = Sustentante.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		
		return searchResult
	}
	
	//metodos de busqueda avanzada que incluyen la institucion financiera
	SearchResult findAllAdvancedSearchAndIns(String nom, String ap1, String ap2, Integer max, Integer offset, String sort, String order, Long idgrup, Long idfina) {
		List<String> hqlFilters = new ArrayList<String>()
		String whereKeyword = "where "
		Boolean whereKeywordNeeded = false
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
		else if(["id","numeroMatricula","nombre","primerApellido","segundoApellido","rfc","curp","fechaNacimiento","correoElectronico"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		if(nom != null && nom != ""){
			hqlFilters.add("s.nombre like :nom ")
			whereKeywordNeeded = true
			namedParameters.put("nom",nom+"%")
		}
		if(ap1 != null && ap1 != ""){
			hqlFilters.add("s.primerApellido like :ap1 ")
			whereKeywordNeeded = true
			namedParameters.put("ap1",ap1+"%")
		}
		if(ap2 != null && ap2 != ""){
			hqlFilters.add("s.segundoApellido like :ap2 ")
			whereKeywordNeeded = true
			namedParameters.put("ap2",ap2+"%")
		}

		strHqlCount.append("select count(s.id) from Sustentante as s ")
		strHqlCount.append(" inner join s.puestos pu with pu.idInstitucion = " +idfina+ " and pu.esActual = true  ")
		sbHql.append("from Sustentante as s ")
		sbHql.append(" inner join s.puestos pu with pu.idInstitucion = " +idfina+ " and pu.esActual = true  ")
		
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
		sbHql.append("order by s.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Sustentante.executeQuery(strHqlCount.toString(), namedParameters)[0]
		searchResult.list = Sustentante.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		
		return searchResult
	}
	
	
	
	SearchResult findAllAdvancedSearchWithCertificacionAndIns(String nom, String ap1, String ap2, Long idfig, Long idvarfig, Long stcert, Long staut, Integer max, Integer offset, String sort, String order, Long idgrup, Long idfina) {
		List<String> hqlFilters = new ArrayList<String>()
		String whereKeyword = "where "
		Boolean whereKeywordNeeded = false
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
		else if(["id","numeroMatricula","nombre","primerApellido","segundoApellido","rfc","curp","fechaNacimiento","correoElectronico"].find{ sort == it } == null){
			sort = "id"
		}
		if(order == null || order == ""){
			order = "asc"
		}
		else if(order != "desc" && order != "asc"){
			order = "asc"
		}
		
		if(nom != null && nom != ""){
			hqlFilters.add("s.nombre like :nom ")
			whereKeywordNeeded = true
			namedParameters.put("nom",nom+"%")
		}
		if(ap1 != null && ap1 != ""){
			hqlFilters.add("s.primerApellido like :ap1 ")
			whereKeywordNeeded = true
			namedParameters.put("ap1",ap1+"%")
		}
		if(ap2 != null && ap2 != ""){
			hqlFilters.add("s.segundoApellido like :ap2 ")
			whereKeywordNeeded = true
			namedParameters.put("nom",ap2+"%")
		}

		
		if(idvarfig == null || idvarfig <= 0){
			if(idfig != null && idfig > 0){
				hqlFilters.add("ca.varianteFigura.idFigura = :idfig ")
				whereKeywordNeeded = true
				namedParameters.put("idfig",idfig)
			}
		}
		else{
			hqlFilters.add("ca.varianteFigura.id = :idvarfig ")
			whereKeywordNeeded = true
			namedParameters.put("idvarfig",idvarfig)
		}
		
		if(stcert != null && stcert > 0){
			hqlFilters.add("ca.statusCertificacion.id = :stcert ")
			whereKeywordNeeded = true
			namedParameters.put("stcert",stcert)
		}
		if(staut != null && staut > 0){
			hqlFilters.add("ca.statusAutorizacion.id = :staut ")
			whereKeywordNeeded = true
			namedParameters.put("staut",staut)
		}
		
		strHqlCount.append("select count(distinct s.id) from Sustentante as s inner join s.certificaciones ca with ca.isUltima = true ")
		strHqlCount.append(" inner join s.puestos pu with pu.idInstitucion = " +idfina+ " and pu.esActual = true  ")
		sbHql.append("select distinct s from Sustentante as s inner join s.certificaciones ca with ca.isUltima = true ")
		sbHql.append(" inner join s.puestos pu with pu.idInstitucion = " +idfina+ " and pu.esActual = true  ")
		
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
		sbHql.append("order by s.").append(sort).append(" ").append(order)
		
		def searchResult = new SearchResult()
		searchResult.count = Sustentante.executeQuery(strHqlCount.toString(), namedParameters)[0]
		searchResult.list = Sustentante.executeQuery(sbHql.toString(),namedParameters,[max: max, offset: offset])
		
		return searchResult
	}
	//END metodos de busqueda avanzada que incluyen la institucion financiera
	
	Certificacion getCertificacionActual(Long idSust){
		def session = sessionFactory.currentSession
		String query = getIdCertificacionActualQuery
		def result = session.createSQLQuery(query).setLong('idSust', idSust).addEntity(Certificacion.class).list()
				
		if(result.size() > 0)
			return result[0]
		else
			return null
	}
}
