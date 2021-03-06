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
		else if(["id", "fechaInicio"].find{ sort == it } == null){
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
		else if(["id", "fechaInicio"].find{ sort == it } == null){
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
		else if(["id", "fechaInicio"].find{ sort == it } == null){
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
		else if(["id", "fechaInicio"].find{ sort == it } == null){
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
				[
					StatusCertificacionTypes.CERTIFICADO
				],
				[
					StatusAutorizacionTypes.DICTAMEN_PREVIO,
					StatusAutorizacionTypes.REVOCADA
				])
	}

	SearchResult findAllEnDictamenPrevioByFolio(Long idSustentante){
		return this.findAllByStatusAutorizacionAndIdSustentante(10, 0, "id", "asc", idSustentante,
				[
					StatusCertificacionTypes.CERTIFICADO
				],
				[
					StatusAutorizacionTypes.DICTAMEN_PREVIO,
					StatusAutorizacionTypes.REVOCADA
				])
	}

	SearchResult findAllEnDictamenPrevio(Integer max, Integer offset, String sort, String order,
			String nom, String ap1, String ap2,
			Long idfig, Long idvarfig){

		return this.findAllByStatusAutorizacion(max, offset, sort, order,
				nom, ap1, ap2, idfig, idvarfig,
				[
					StatusCertificacionTypes.CERTIFICADO
				],
				[
					StatusAutorizacionTypes.DICTAMEN_PREVIO,
					StatusAutorizacionTypes.REVOCADA
				])
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
				[
					StatusCertificacionTypes.CERTIFICADO
				],
				[
					StatusAutorizacionTypes.AUTORIZADO,
					StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES
				])
	}

	SearchResult findAllAutorizadosConOSinPoderesByFolio(Long idSustentante){
		return this.findAllByStatusAutorizacionAndIdSustentante(10, 0, "id", "asc", idSustentante,
				[
					StatusCertificacionTypes.CERTIFICADO
				],
				[
					StatusAutorizacionTypes.AUTORIZADO,
					StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES
				])
	}

	SearchResult findAllAutorizadosConOSinPoderes(Integer max, Integer offset, String sort, String order,
			String nom, String ap1, String ap2,
			Long idfig, Long idvarfig){

		return this.findAllByStatusAutorizacion(max, offset, sort, order,
				nom, ap1, ap2, idfig, idvarfig,
				[
					StatusCertificacionTypes.CERTIFICADO
				],
				[
					StatusAutorizacionTypes.AUTORIZADO,
					StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES
				])
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

		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams(hqlFilters, namedParameters, -1, -1, nom, ap1, ap2, idfig, idvarfig, idStatusCertificacion, idStatusAutorizacion);
		//si no hay parametros, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max,offset)
		return sr
	}

	SearchResult findAllByStatusAutorizacion(Integer max, Integer offset, String sort, String order,
			String nom, String ap1, String ap2,
			Long idfig, Long idvarfig,
			Collection<Long> idsStCert,
			Collection<Long> idsStAut){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		max = this.filterMax(max?:10)
		offset = this.filterOffset(offset?:0)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams(hqlFilters, namedParameters, -1, -1, nom, ap1, ap2, idfig, idvarfig, idsStCert, idsStAut);
		//si no hay parametros, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max,offset)
		return sr
	}

	/* Métodos find para buscar registros "candidatos" a procesos de autorización */
	//Candidato Actualizacion Autorizacion
	SearchResult findAllCandidatoActualizacionAutorizacion(int max, int offset, String sort, String order, String nom, String ap1, String ap2, long idfig, long idvarfig){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		max = this.filterMax(max)
		offset = this.filterOffset(offset)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)


		List<Long> autList = new ArrayList()
		autList.add(StatusAutorizacionTypes.AUTORIZADO)
		autList.add(StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES)
		autList.add(StatusAutorizacionTypes.REVOCADA)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, -1, -1, nom, ap1, ap2, idfig, idvarfig, StatusCertificacionTypes.CERTIFICADO, autList);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max,offset)
		return sr
	}
	SearchResult findAllCandidatoActualizacionAutorizacionByMatricula(int numeroMatricula){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'
		List<Long> autList = new ArrayList()
		autList.add(StatusAutorizacionTypes.AUTORIZADO)
		autList.add(StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES)
		autList.add(StatusAutorizacionTypes.REVOCADA)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, -1, numeroMatricula, '', '', '', -1, -1, StatusCertificacionTypes.CERTIFICADO, autList);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	SearchResult findAllCandidatoActualizacionAutorizacionByFolio(long idSustentante){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		List<Long> autList = new ArrayList()
		autList.add(StatusAutorizacionTypes.AUTORIZADO)
		autList.add(StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES)
		autList.add(StatusAutorizacionTypes.REVOCADA)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, idSustentante, -1, '', '', '', -1, -1, StatusCertificacionTypes.CERTIFICADO, autList);
		//si no hay parametros, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	//Candidato Reposicion Autorizacion
	SearchResult findAllCandidatoReposicionAutorizacion(int max, int offset, String sort, String order, String nom, String ap1, String ap2, long idfig, long idvarfig){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		max = this.filterMax(max)
		offset = this.filterOffset(offset)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, -1, -1, nom, ap1, ap2, idfig, idvarfig, -1, [
			StatusAutorizacionTypes.VENCIDA,
			StatusAutorizacionTypes.REVOCADA,
			StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES
		]);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max,offset)
		return sr
	}
	SearchResult findAllCandidatoReposicionAutorizacionByMatricula(int numeroMatricula){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, -1, numeroMatricula, '', '', '', -1, -1, -1, [
			StatusAutorizacionTypes.VENCIDA,
			StatusAutorizacionTypes.REVOCADA,
			StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES
		]);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	SearchResult findAllCandidatoReposicionAutorizacionByFolio(long idSustentante){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, idSustentante, -1, '', '', '', -1, -1, -1, [
			StatusAutorizacionTypes.VENCIDA,
			StatusAutorizacionTypes.REVOCADA,
			StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES
		]);
		//si no hay parametros, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	//Candidado Cambio Figura
	SearchResult findAllCandidatoCambioFigura(int max, int offset, String sort, String order, String nom, String ap1, String ap2, long idfig, long idvarfig){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		max = this.filterMax(max)
		offset = this.filterOffset(offset)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, -1, -1, nom, ap1, ap2, idfig, idvarfig, StatusCertificacionTypes.CERTIFICADO, [
			StatusAutorizacionTypes.AUTORIZADO,
			StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES,
			StatusAutorizacionTypes.REVOCADA
		]);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max,offset)
		return sr
	}
	SearchResult findAllCandidatoCambioFiguraByMatricula(int numeroMatricula){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, -1, numeroMatricula, '', '', '', -1, -1, StatusCertificacionTypes.CERTIFICADO, [
			StatusAutorizacionTypes.AUTORIZADO,
			StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES,
			StatusAutorizacionTypes.REVOCADA
		]);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	SearchResult findAllCandidatoCambioFiguraByFolio(long idSustentante){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams2(hqlFilters, namedParameters, idSustentante, -1, '', '', '', -1, -1, StatusCertificacionTypes.CERTIFICADO, [
			StatusAutorizacionTypes.AUTORIZADO,
			StatusAutorizacionTypes.AUTORIZADO_SIN_PODERES,
			StatusAutorizacionTypes.REVOCADA
		]);
		//si no hay parametros, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	//Candidato Credencializable
	SearchResult findAllCandidatoCredencial(int max, int offset, String sort, String order, String nom, String ap1, String ap2, long idfig, long idvarfig){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		max = this.filterMax(max)
		offset = this.filterOffset(offset)
		sort = this.filterSort(sort)
		order = this.filterOrder(order)

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams(hqlFilters, namedParameters, -1, -1, nom, ap1, ap2, idfig, idvarfig, StatusCertificacionTypes.CERTIFICADO, StatusAutorizacionTypes.AUTORIZADO);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max,offset)
		return sr
	}
	SearchResult findAllCandidatoCredencialByMatricula(int numeroMatricula){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams(hqlFilters, namedParameters, -1, numeroMatricula, '', '', '', -1, -1, StatusCertificacionTypes.CERTIFICADO, StatusAutorizacionTypes.AUTORIZADO);
		//si no hay parametrós, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
		return sr
	}
	SearchResult findAllCandidatoCredencialByFolio(long idSustentante){
		//Variables a ocupar en el método
		int countParams = 0
		SearchResult sr = new SearchResult()
		//Variables para construcción de query HQL dinámico
		List<String> hqlFilters = new ArrayList<String>();
		boolean whereKeywordNeeded = false;
		StringBuilder sbHql = new StringBuilder()
		StringBuilder strHqlCount = new StringBuilder()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		//formación de filtros
		int max = 10
		String sort = 'id'
		String order = 'asc'

		//preparación de parámetros
		countParams = this._addHqlFilterAndParams(hqlFilters, namedParameters, idSustentante, -1, '', '', '', -1, -1, StatusCertificacionTypes.CERTIFICADO, StatusAutorizacionTypes.AUTORIZADO);
		//si no hay parametros, entonces el query "va por todos"
		whereKeywordNeeded = (countParams > 0)

		//prepara los StringBuilder con los querys a ejecutar
		this._prepareQuery(sbHql, strHqlCount, hqlFilters, whereKeywordNeeded, sort, order)

		//ejecuta los querys e inserta el resultado en un SearchResult
		sr = this._execQuery(strHqlCount,sbHql,namedParameters,max)
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
		else if(["id", "fechaInicio"].find{ sort == it } == null){
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

	/**
	 * 	
	 * @param hqlFilters
	 * @param namedParameters
	 * @return true if "where" keyword is needed to be appended, otherwise false.
	 */
	private int _addHqlFilterAndParams(List<String> hqlFilters, Map<String,Object> namedParameters,
			long idSustentante, int numeroMatricula, String nom, String ap1, String ap2,
			long idfig, long idvarfig, Collection<Long> idsStCert, Collection<Long> idsStAut, boolean soloUltimas = true){

		int count = 0
		if(nom != null && nom.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.nombre like :nom ")
			count++
			namedParameters.put("nom",nom + '%')
		}
		if(ap1 != null && ap1.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.primerApellido like :ap1 ")
			count++
			namedParameters.put("ap1",ap1 + '%')
		}
		if(ap2 != null && ap2.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.segundoApellido like :ap2 ")
			count++
			namedParameters.put("ap2",ap2 + '%')
		}
		if(idvarfig > 0){
			hqlFilters.add("c.varianteFigura.id = :idVarFigura ")
			count++
			namedParameters.put("idVarFigura",idvarfig)
		}
		else if(idfig > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			count++
			namedParameters.put("idFigura",idfig)
		}
		if(idsStCert != null && idsStCert.size() > 0){
			hqlFilters.add("c.statusCertificacion.id in (:idSc) ")
			count++
			namedParameters.put("idSc",idsStCert)
		}
		if(idsStAut != null && idsStAut.size() > 0){
			hqlFilters.add("c.statusAutorizacion.id in (:idSa) ")
			count++
			namedParameters.put("idSa",idsStAut)
		}

		if(soloUltimas == true){
			hqlFilters.add("c.isUltima = true ")
			count++
		}

		return count
	}

	/**
	 *
	 * @param hqlFilters
	 * @param namedParameters
	 * @return true if "where" keyword is needed to be appended, otherwise false.
	 */
	private int _addHqlFilterAndParams(List<String> hqlFilters, Map<String,Object> namedParameters,
			long idSustentante, int numeroMatricula, String nom, String ap1, String ap2,
			long idfig, long idvarfig, long idStatusCertificacion, long idStatusAutorizacion, boolean soloUltimas = true){

		int count = 0

		if(idSustentante > 0){
			hqlFilters.add("c.sustentante.id = :idSustentante ")
			namedParameters.put("idSustentante", idSustentante)
			count++
		}
		if(numeroMatricula > 0){
			hqlFilters.add("c.sustentante.numeroMatricula = :numeroMatricula ")
			namedParameters.put("numeroMatricula", numeroMatricula)
			count++
		}

		if(nom != null && nom.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.nombre like :nom ")
			count++
			namedParameters.put("nom",nom + '%')
		}
		if(ap1 != null && ap1.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.primerApellido like :ap1 ")
			count++
			namedParameters.put("ap1",ap1 + '%')
		}
		if(ap2 != null && ap2.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.segundoApellido like :ap2 ")
			count++
			namedParameters.put("ap2",ap2 + '%')
		}
		if(idvarfig > 0){
			hqlFilters.add("c.varianteFigura.id = :idVarFigura ")
			count++
			namedParameters.put("idVarFigura",idvarfig)
		}
		else if(idfig > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			count++
			namedParameters.put("idFigura",idfig)
		}
		if(idStatusCertificacion > 0){
			hqlFilters.add("c.statusCertificacion.id = :idSc ")
			count++
			namedParameters.put("idSc",idStatusCertificacion)
		}
		if(idStatusAutorizacion > 0){
			hqlFilters.add("c.statusAutorizacion.id = :idSa ")
			count++
			namedParameters.put("idSa",idStatusAutorizacion)
		}

		if(soloUltimas == true){
			hqlFilters.add("c.isUltima = true ")
			count++
		}

		return count
	}

	/**
	 * se agrego para tener la opcion de 1 estatus de certificación y multiples estatus de autorización
	 * @param hqlFilters
	 * @param namedParameters
	 * @return true if "where" keyword is needed to be appended, otherwise false.
	 */
	private int _addHqlFilterAndParams2(List<String> hqlFilters, Map<String,Object> namedParameters,
			long idSustentante, int numeroMatricula, String nom, String ap1, String ap2,
			long idfig, long idvarfig, long idStatusCertificacion, Collection<Long> idsStAut, boolean soloUltimas = true){

		int count = 0

		if(idSustentante > 0){
			hqlFilters.add("c.sustentante.id = :idSustentante ")
			namedParameters.put("idSustentante", idSustentante)
			count++
		}
		if(numeroMatricula > 0){
			hqlFilters.add("c.sustentante.numeroMatricula = :numeroMatricula ")
			namedParameters.put("numeroMatricula", numeroMatricula)
			count++
		}

		if(nom != null && nom.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.nombre like :nom ")
			count++
			namedParameters.put("nom",nom + '%')
		}
		if(ap1 != null && ap1.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.primerApellido like :ap1 ")
			count++
			namedParameters.put("ap1",ap1 + '%')
		}
		if(ap2 != null && ap2.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.segundoApellido like :ap2 ")
			count++
			namedParameters.put("ap2",ap2 + '%')
		}
		if(idvarfig > 0){
			hqlFilters.add("c.varianteFigura.id = :idVarFigura ")
			count++
			namedParameters.put("idVarFigura",idvarfig)
		}
		else if(idfig > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			count++
			namedParameters.put("idFigura",idfig)
		}
		if(idStatusCertificacion > 0){
			hqlFilters.add("c.statusCertificacion.id = :idSc ")
			count++
			namedParameters.put("idSc",idStatusCertificacion)
		}
		if(idsStAut != null && idsStAut.size() > 0){
			hqlFilters.add("c.statusAutorizacion.id in (:idSa) ")
			count++
			namedParameters.put("idSa",idsStAut)
		}

		if(soloUltimas == true){
			hqlFilters.add("c.isUltima = true ")
			count++
		}

		return count
	}

	/**
	 *
	 * @param hqlFilters
	 * @param namedParameters
	 * @return true if "where" keyword is needed to be appended, otherwise false.
	 */
	private int _addHqlFilterAndParams3(List<String> hqlFilters, Map<String,Object> namedParameters,
			long idSustentante, int numeroMatricula, String nom, String ap1, String ap2,
			long idfig, long idvarfig, long idStatusCertificacion, long idStatusAutorizacion,
			Date entregaLow,  Date entregaTop, Date envioLow, Date envioTop
			, boolean soloUltimas = true){

		int count = 0

		if(idSustentante > 0){
			hqlFilters.add("c.sustentante.id = :idSustentante ")
			namedParameters.put("idSustentante", idSustentante)
			count++
		}
		if(numeroMatricula > 0){
			hqlFilters.add("c.sustentante.numeroMatricula = :numeroMatricula ")
			namedParameters.put("numeroMatricula", numeroMatricula)
			count++
		}

		if(nom != null && nom.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.nombre like :nom ")
			count++
			namedParameters.put("nom",nom + '%')
		}
		if(ap1 != null && ap1.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.primerApellido like :ap1 ")
			count++
			namedParameters.put("ap1",ap1 + '%')
		}
		if(ap2 != null && ap2.trim().compareTo("") != 0){
			hqlFilters.add("c.sustentante.segundoApellido like :ap2 ")
			count++
			namedParameters.put("ap2",ap2 + '%')
		}
		if(idvarfig > 0){
			hqlFilters.add("c.varianteFigura.id = :idVarFigura ")
			count++
			namedParameters.put("idVarFigura",idvarfig)
		}
		else if(idfig > 0){
			hqlFilters.add("c.varianteFigura.idFigura = :idFigura ")
			count++
			namedParameters.put("idFigura",idfig)
		}
		if(idStatusCertificacion > 0){
			hqlFilters.add("c.statusCertificacion.id = :idSc ")
			count++
			namedParameters.put("idSc",idStatusCertificacion)
		}
		if(idStatusAutorizacion > 0){
			hqlFilters.add("c.statusAutorizacion.id = :idSa ")
			count++
			namedParameters.put("idSa",idStatusAutorizacion)
		}

		//para fechas
		if(entregaLow != null && entregaTop!= null){
			hqlFilters.add("c.fechaEntregaRecepcion BETWEEN :entregaLow AND :entregaTop ")
			count++
			namedParameters.put("entregaLow",entregaLow)
			namedParameters.put("entregaTop",entregaTop)
		}
		if(envioLow != null && envioTop!= null){
			hqlFilters.add("c.fechaEnvioComision BETWEEN :envioLow AND :envioTop ")
			count++
			namedParameters.put("envioLow",envioLow)
			namedParameters.put("envioTop",envioTop)
		}
		//end fechas

		if(soloUltimas == true){
			hqlFilters.add("c.isUltima = true ")
			count++
		}

		return count
	}





	private void _prepareQuery(StringBuilder sbHql, StringBuilder strHqlCount, List<String> hqlFilters, boolean whereKeywordNeeded, String sort, String order){
		strHqlCount.append('select count(c.id) from Certificacion as c ')
		sbHql.append('from Certificacion as c ')
		if(whereKeywordNeeded){
			sbHql.append('where ')
			strHqlCount.append('where ')
			hqlFilters.each{
				if(it != hqlFilters.last()){
					sbHql.append(it).append('and ')
					strHqlCount.append(it).append('and ')
				}
				else{
					sbHql.append(it)
					strHqlCount.append(it)
				}
			}
		}
		sbHql.append('order by c.').append(sort).append(' ').append(order)
	}

	private SearchResult _execQuery(StringBuilder strHqlCount,StringBuilder sbHql,Map<String,Object> namedParameters,int max,int offset = 0){
		SearchResult sr = new SearchResult()
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



	private SearchResult findAllProximosDesautorizados(Integer numeroFolio,Integer numeroMatricula,Date fechaInicio,Date fechaFin,Integer offset,String order = "asc",Integer max = 10){
		SearchResult sr = new SearchResult()
		Map<String,Object> namedParameters = new HashMap<String,Object>()

		namedParameters.put("fechaInicio", fechaInicio)
		namedParameters.put("fechaFin", fechaFin)

		StringBuilder countProximos = new StringBuilder()
		StringBuilder queryProximos = new StringBuilder()

		countProximos.append(' select count(c.id) from Certificacion as c ')
		queryProximos.append(' from Certificacion as c ')

		countProximos.append(' where c.isUltima = 1 and c.fechaAutorizacionFin between :fechaInicio and :fechaFin ')
		queryProximos.append(' where c.isUltima = 1 and c.fechaAutorizacionFin between :fechaInicio and :fechaFin ')
		println("numeroMatricula"+numeroMatricula)
		if(numeroMatricula!= null && numeroMatricula > 0){
			namedParameters.put("numeroMatricula", numeroMatricula)
			countProximos.append(' and c.sustentante.numeroMatricula = :numeroMatricula  ')
			queryProximos.append(' and c.sustentante.numeroMatricula = :numeroMatricula  ')
		}
		queryProximos.append(' order by c.fechaAutorizacionFin ').append(order)
		try{
			println(countProximos.toString())
			println(queryProximos.toString())
			sr.count = Certificacion.executeQuery(countProximos.toString(),namedParameters)[0]
			sr.list = Certificacion.executeQuery(queryProximos.toString(),namedParameters,[max:max, offset: offset])
			sr.sustentantes = sr.list.collect{ it.sustentante }
		}
		catch(Exception e){
			sr.error = true
			sr.errorDetails = e.message
		}

		return sr
	}

}
