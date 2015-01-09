package mx.amib.expediente.datasync

public interface IModelSynchronizer {
	void synchronize(boolean removeMissing)
	void refresh()
	long checkLocalVersion()
	long checkRemoteVersion()
	Collection downloadAllElements()
	Collection downloadUpdatedElements(long version)
	Collection<Long> getIdsToRemove(Collection<Long> ids)
	
	String getRestUrlVersionControl()
	void setRestUrlVersionControl(String restUrlVersionControl)
	String getRestUrlGetAllElements()
	void setRestUrlGetAllElements(String restUrlGetAllElements)
	String getRestUrlGetUpdatedElements()
	void setRestUrlGetUpdatedElements(String restUrlGetUpdatedElements)
	String getRestUrlGetExistingIds()
	void setRestUrlGetExistingIds(String restUrlGetRemovedIds)
}
