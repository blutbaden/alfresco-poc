package com.poc.service.dto;

public class FolderDTO {
    private String siteId;
    private String folderName;

    public FolderDTO(String siteId, String folderName) {
        this.siteId = siteId;
        this.folderName = folderName;
    }

    public String getSiteId() {

        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
