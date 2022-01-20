package com.poc.service;

import org.alfresco.event.sdk.handling.filter.EventFilter;
import org.alfresco.event.sdk.handling.filter.PropertyChangedFilter;
import org.alfresco.event.sdk.handling.handler.OnNodeUpdatedEventHandler;
import org.alfresco.event.sdk.model.v1.model.DataAttributes;
import org.alfresco.event.sdk.model.v1.model.NodeResource;
import org.alfresco.event.sdk.model.v1.model.RepoEvent;
import org.alfresco.event.sdk.model.v1.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Sample event handler to react to the update of a content in the repository.
 */
@Component
public class NodeUpdatedHandler implements OnNodeUpdatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeUpdatedHandler.class);

    public void handleEvent(final RepoEvent<DataAttributes<Resource>> repoEvent) {
        final NodeResource nodeResource = (NodeResource) repoEvent.getData().getResource();
        final NodeResource beforeNodeResource = (NodeResource) repoEvent.getData().getResourceBefore();
        final String name = nodeResource.getName();
        LOGGER.info("The node {} has been updated!", name);
    }

    public EventFilter getEventFilter() {
        return PropertyChangedFilter.of("cm:content");
    }
}
