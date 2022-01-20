package com.poc.service;

import com.poc.service.dto.FolderDTO;
import com.poc.service.dto.NodeDTO;
import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Service class for managing users.
 */
@Service
public class AlfrescoService {

    static final Logger LOGGER = LoggerFactory.getLogger(AlfrescoService.class);

    private final NodesApi nodesApi;
    private final SitesApi sitesApi;

    public AlfrescoService(NodesApi nodesApi, SitesApi sitesApi) {
        this.nodesApi = nodesApi;
        this.sitesApi = sitesApi;
    }

    public Site createSite(String siteId) {
        Site site = Objects.requireNonNull(sitesApi.createSite(
                new SiteBodyCreate()
                        .id(siteId)
                        .title("title-" + siteId)
                        .description("description-" + siteId)
                        .visibility(SiteBodyCreate.VisibilityEnum.PUBLIC),
                null, null, null).getBody()).getEntry();
        LOGGER.info("Created site: {}", site);
        return site;
    }

    public void createNode(NodeDTO nodeDTO) {
        // Get the parent folder where file should be stored
        Node parentFolderNode = Objects.requireNonNull(nodesApi.getNode(nodeDTO.getParentId(), null, null,
                null).getBody()).getEntry();
        LOGGER.info("Got parent folder node: {}", parentFolderNode.toString());

        // Create the file node metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("cmis:description", Instant.now().toString());
        Node fileNode = Objects.requireNonNull(nodesApi.createNode(parentFolderNode.getId(),
                new NodeBodyCreate().nodeType("cm:content").name(nodeDTO.getFileName()).properties(metadata),
                null, null, null, null, null).getBody()).getEntry();

        // Add the file node content
        Node updatedFileNode = Objects.requireNonNull(nodesApi.updateNodeContent(fileNode.getId(),
                nodeDTO.getContent(), true, null, null,
                null, null).getBody()).getEntry();

        LOGGER.info("Created file with content: {}", updatedFileNode.toString());
    }

    public void createFolder(FolderDTO folderDTO) {
        SiteContainer docLibContainer = Objects.requireNonNull(sitesApi.getSiteContainer(folderDTO.getSiteId(),
                "documentLibrary", null).getBody()).getEntry();
        LOGGER.info("Creating folder in site DocumentLibrary folder Node ID: {}", docLibContainer.getId());

        Node folderNode = Objects.requireNonNull(nodesApi.createNode(docLibContainer.getId(),
                new NodeBodyCreate()
                        .nodeType("cm:folder")
                        .name(folderDTO.getFolderName()),
                null, null, null, null, null).getBody()).getEntry();

        LOGGER.info("Created folder: {}", folderNode.toString());
    }
}
