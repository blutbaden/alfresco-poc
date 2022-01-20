package com.poc.web.rest;

import com.poc.service.AlfrescoService;
import com.poc.service.dto.FolderDTO;
import com.poc.service.dto.NodeDTO;
import org.alfresco.core.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AlfrescoResource {

    private final Logger log = LoggerFactory.getLogger(AlfrescoResource.class);
    private final AlfrescoService alfrescoService;

    public AlfrescoResource(AlfrescoService alfrescoService) {
        this.alfrescoService = alfrescoService;
    }

    /**
     * {@code POST /alfresco/site/create} : create a site.
     *
     * @param siteId
     * @return
     */
    @PostMapping(path = "/alfresco/site/create")
    public Site createSite(@RequestBody String siteId) {
        return alfrescoService.createSite(siteId);
    }

    /**
     * {@code POST /alfresco/folder/create} : create a folder.
     *
     * @param folderDTO
     */
    @PostMapping(path = "/alfresco/folder/create")
    public void createFolder(@RequestBody FolderDTO folderDTO) {
        alfrescoService.createFolder(folderDTO);
    }

    /**
     * {@code POST /alfresco/node/create} : create a node.
     *
     * @param nodeDTO
     */
    @PostMapping(path = "/alfresco/node/create")
        public void createNode(@RequestBody NodeDTO nodeDTO) {
        alfrescoService.createNode(nodeDTO);
    }
}
