package mx.amib.expediente.certificacion.model.datasync

import java.util.Collection;

import mx.amib.sistemas.expediente.catalogos.model.VersionCatalogo
import mx.amib.expediente.certificacion.model.catalog.VarianteFigura
import mx.amib.expediente.datasync.IModelSynchronizer

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder


import org.codehaus.groovy.grails.web.json.JSONObject

class VarianteFiguraSynchronizer implements IModelSynchronizer {

	private String _restUrlVersionControl
	private String _restUrlGetAllElements
	private String _restUrlGetUpdatedElements
	private String _restUrlGetExistingIds
	

	@Override
	public void synchronize(boolean removeMissing) {
		long localVersion = this.checkLocalVersion()
		long remoteVersion = this.checkRemoteVersion()
		
		if(localVersion < remoteVersion){
			for(int i = localVersion+1; i<=remoteVersion; i++){
				def variantesFigura = this.downloadUpdatedElements(i)
				variantesFigura.each{
					println "insertando " + it.id
					it.save(failOnError:true,flush:true)
				}
			}
		}
		
		if(removeMissing){
			//elimina aquellos que hayan sido elminados en el catalogo amibCatalogos
			def ids = VarianteFigura.executeQuery("select vf.id from VarianteFigura vf where vf.version <= :version",[version:localVersion])
			if(ids.size() > 0){
				List<Long> idsToDelete = this.getIdsToRemove(ids)
				if(idsToDelete != null && idsToDelete.size() > 0)
					VarianteFigura.executeUpdate("delete from VarianteFigura vf where vf.id in :ids",[ids:idsToDelete])
			}
		}
		
		//actualiza el numero de versión
		def vc = VersionCatalogo.findByNumeroCatalogo(202)
		vc.numeroVersion = remoteVersion
		vc.save(failOnError:true,flush:true)
	}

	
	@Override
	public void refresh() {
		VarianteFigura.executeUpdate("delete from VarianteFigura")
		def variantesFigura = this.downloadAllElements()
		variantesFigura.each{
			it.save(failOnError:true,flush:true)
		}
	}

	@Override
	public long checkLocalVersion() {
		Long numero = VersionCatalogo.findByNumeroCatalogo(202).numeroVersion
		return numero.value
	}

	@Override
	public long checkRemoteVersion() {
		long result = -1
		Long numeroAmibCatalogos = VersionCatalogo.findByNumeroCatalogo(202).numeroCatalogoForaneo
		
		//llamada rest a catalogo foreano
		String restUrl = _restUrlVersionControl + numeroAmibCatalogos
		def rest = new RestBuilder()
		def resp = rest.get(restUrl)
		resp.json instanceof JSONObject
		if(resp.json != null){
			result = resp.json.numeroVersion
		}
		
		return result
	}

	@Override
	public Collection downloadAllElements() {
		List<VarianteFigura> allVariantesFigura = new ArrayList<VarianteFigura>()
		
		//llamada rest a catalogo foreano
		String restUrl = _restUrlGetAllElements
		def rest = new RestBuilder()
		def resp = rest.get(restUrl)
		resp.json instanceof JSONObject
		
		resp.json.each{
			VarianteFigura vf = new VarianteFigura()
			vf.id = it.id
			vf.version = it.version
			vf.nombre = it.nombre
			vf.vigente = it.vigente
			vf.idFigura = it.idFigura
			vf.nombreFigura = it.nombreFigura
			vf.nombreAcuseFigura = it.nombreAcuseFigura
			vf.esAutorizableFigura = it.esAutorizableFigura
			vf.tipoAutorizacionFigura = it.tipoAutorizacion
			vf.inicialesFigura = it.inicialesFigura
			vf.nombreAcuseFigura = (vf.nombreAcuseFigura == "null")?null:vf.nombreAcuseFigura
			vf.esAutorizableFigura = (vf.esAutorizableFigura == "null")?null:vf.esAutorizableFigura
			vf.tipoAutorizacionFigura = (vf.tipoAutorizacionFigura == "null")?null:vf.tipoAutorizacionFigura
			vf.inicialesFigura = (vf.inicialesFigura == "null")?null:vf.inicialesFigura
			
			allVariantesFigura.add(vf)
		}
		
		return allVariantesFigura
	}

	@Override
	public Collection downloadUpdatedElements(long version) {
		List<VarianteFigura> updatedVariantesFigura = new ArrayList<VarianteFigura>()
		
		//llamada rest a catalogo foreano
		String restUrl = _restUrlGetUpdatedElements + version
		def rest = new RestBuilder()
		def resp = rest.get(restUrl)
		resp.json instanceof JSONObject
		
		resp.json.each{
			VarianteFigura vf = new VarianteFigura()
			vf.id = it.id
			vf.version = it.version
			vf.nombre = it.nombre
			vf.vigente = it.vigente
			vf.idFigura = it.idFigura
			vf.nombreFigura = it.nombreFigura
			vf.nombreAcuseFigura = it.nombreAcuseFigura
			vf.esAutorizableFigura = it.esAutorizableFigura
			vf.tipoAutorizacionFigura = it.tipoAutorizacion
			vf.inicialesFigura = it.inicialesFigura
			vf.nombreAcuseFigura = (vf.nombreAcuseFigura == "null")?null:vf.nombreAcuseFigura
			vf.esAutorizableFigura = (vf.esAutorizableFigura == "null")?null:vf.esAutorizableFigura
			vf.tipoAutorizacionFigura = (vf.tipoAutorizacionFigura == "null")?null:vf.tipoAutorizacionFigura
			vf.inicialesFigura = (vf.inicialesFigura == "null")?null:vf.inicialesFigura
			updatedVariantesFigura.add(vf)
		}
		
		return updatedVariantesFigura
	}

	@Override
	public Collection<Long> getIdsToRemove(Collection<Long> currentIds) {
		// TODO Auto-generated method stub
		List<Long> idsStillThere = new ArrayList<Long>()
		List<Long> idsToRemove = new ArrayList<Long>()
		
		String restUrl = _restUrlGetExistingIds
		def rest = new RestBuilder()
		def resp = rest.post(restUrl){
			contentType "application/x-www-form-urlencoded" 
			ids = (currentIds as JSON).toString()
		}

		resp.json instanceof JSONObject
		resp.json.each{
			idsStillThere.add(Long.valueOf(it))
		}
		
		currentIds.each{ idCurrentInLocalCatalog ->
			if(idsStillThere.find{ idCurrentInRemCatalog -> idCurrentInLocalCatalog == idCurrentInRemCatalog } == null ){
				idsToRemove.add(idCurrentInLocalCatalog)
			}
		}
		
		return idsToRemove;
	}

	
	@Override
	public String getRestUrlVersionControl() {
		return _restUrlVersionControl
	}

	@Override
	public void setRestUrlVersionControl(String restUrlVersionControl) {
		_restUrlVersionControl = restUrlVersionControl
		return null;
	}

	@Override
	public String getRestUrlGetAllElements() {
		return _restUrlGetAllElements
	}

	@Override
	public void setRestUrlGetAllElements(String restUrlGetAllElements) {
		_restUrlGetAllElements = restUrlGetAllElements
	}
	
	@Override
	public String getRestUrlGetUpdatedElements() {
		return _restUrlGetUpdatedElements
	}

	@Override
	public void setRestUrlGetUpdatedElements(String restUrlGetUpdatedElements) {
		_restUrlGetUpdatedElements = restUrlGetUpdatedElements
	}
	
	@Override
	public String getRestUrlGetExistingIds() {
		return _restUrlGetExistingIds
	}

	@Override
	public void setRestUrlGetExistingIds(String restUrlGetExistingIds) {
		_restUrlGetExistingIds = restUrlGetExistingIds
	}

}
