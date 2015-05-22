package mx.amib.sistemas.expediente.persona.model.datasync

import java.util.Collection

import mx.amib.expediente.datasync.IModelSynchronizer
import mx.amib.sistemas.expediente.persona.model.catalog.EstadoCivil
import mx.amib.sistemas.expediente.catalogos.model.VersionCatalogo
import mx.amib.expediente.datasync.IModelSynchronizer
import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.JSONObject

import grails.plugins.rest.client.RestBuilder

class EstadoCivilModelSynchronizer implements IModelSynchronizer {

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
			def ids = EstadoCivil.executeQuery("select e.id from EstadoCivil e where e.numeroVersion <= :numeroVersion",[numeroVersion:localVersion])
			if(ids.size() > 0){
				List<Long> idsToDelete = this.getIdsToRemove(ids)
				if(idsToDelete != null && idsToDelete.size() > 0)
					EstadoCivil.executeUpdate("delete from EstadoCivil e where e.id in :ids",[ids:idsToDelete])
			}
		}
		
		//actualiza el numero de versión
		def vc = VersionCatalogo.findByNumeroCatalogo(109)
		vc.numeroVersion = remoteVersion
		vc.save(failOnError:true,flush:true)
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		EstadoCivil.executeUpdate("delete from EstadoCivil")
		def elements = this.downloadAllElements()
		elements.each{
			it.save(failOnError:true,flush:true)
		}
	}

	@Override
	public long checkLocalVersion() {
		Long numero = VersionCatalogo.findByNumeroCatalogo(109).numeroVersion
		return numero.value
	}

	@Override
	public long checkRemoteVersion() {
		long result = -1
		Long numeroAmibCatalogos = VersionCatalogo.findByNumeroCatalogo(109).numeroCatalogoForaneo
		
		//llamada rest a catalogo foreano
		String restUrl = _restUrlVersionControl + numeroAmibCatalogos
		def rest = new RestBuilder()
		def resp = rest.get(restUrl)
		
		if(resp.json != null && resp.json instanceof JSONObject){
			result = resp.json.numeroVersion
		}
		
		return result
	}

	@Override
	public Collection downloadAllElements() {
		List<EstadoCivil> allElements = new ArrayList<EstadoCivil>()
		
		//llamada rest a catalogo foreano
		String restUrl = _restUrlGetAllElements
		def rest = new RestBuilder()
		def resp = rest.get(restUrl)
		resp.json instanceof JSONObject
		
		resp.json.each{
			EstadoCivil x = new EstadoCivil()
			x.id = it.id
			x.numeroVersion = it.numeroVersion
			x.descripcion = it.descripcion
			x.vigente = it.vigente
			
			allElements.add(x)
		}
		
		return allElements
	}

	@Override
	public Collection downloadUpdatedElements(long version) {
		List<EstadoCivil> updatedElements = new ArrayList<EstadoCivil>()
		
		//llamada rest a catalogo foreano
		String restUrl = _restUrlGetUpdatedElements + version
		def rest = new RestBuilder()
		def resp = rest.get(restUrl)
		resp.json instanceof JSONObject
		
		resp.json.each{
			EstadoCivil x = new EstadoCivil()
			x.id = it.id
			x.numeroVersion = it.numeroVersion
			x.descripcion = it.descripcion
			x.vigente = it.vigente
			updatedElements.add(x)
		}
		
		return updatedElements
	}

	@Override
	public Collection<Long> getIdsToRemove(Collection<Long> currentIds) {
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
		
		return idsToRemove
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
