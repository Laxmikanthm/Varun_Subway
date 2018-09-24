package pojos;

import utils.Logz;

public class Login {

    private String dataSource;
    private String sSOItem;
    private String userId;
    private String litigationStore;
    private String nonLitigationStore;
    private String storeToSelect;
    private String storeWithDocuments;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSSOItem() {
        return sSOItem;
    }

    public void setSSOItem(String sSOItem) {
        this.sSOItem = sSOItem;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLitigationStore() {
        return litigationStore;
    }

    public void setLitigationStore(String litigationStore) {
        this.litigationStore = litigationStore;
    }

    public String getNonLitigationStore() {
Logz.step("Store:::" + nonLitigationStore);
        return nonLitigationStore;
    }

    public void setNonLitigationStore(String nonLitigationStore) {
        this.nonLitigationStore = nonLitigationStore;
    }

    public String getStoreToSelect() {
        return storeToSelect;
    }

    public void setStoreToSelect(String storeToSelect) {
        this.storeToSelect = storeToSelect;
    }

	public String getStoreWithDocuments() {
		return storeWithDocuments;
	}

	public void setStoreWithDocuments(String storeWithDocuments) {
        this.storeWithDocuments = storeWithDocuments;
	}
}
