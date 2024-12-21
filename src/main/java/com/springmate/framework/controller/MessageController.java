package com.springmate.framework.controller;


import com.springmate.framework.core.Message;
import com.springmate.framework.exception.MessageFrameworkException;
import com.springmate.framework.service.MessageServiceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageServiceResolver serviceResolver;

    @PostMapping("/process")
    public ResponseEntity<Message<?>> processRequest(@RequestBody Message<?> request) {
        validateRequest(request);
        return ResponseEntity.ok(serviceResolver.resolveAndProcess(request));
    }

    @GetMapping("/resource/{resourceType}")
    public ResponseEntity<Message<?>> getResource(
            @PathVariable String resourceType,
            @RequestParam(required = false) Map<String, String> queryParams) {
        Message<?> request = buildGetRequest(resourceType, queryParams);
        return ResponseEntity.ok(serviceResolver.resolveAndProcess(request));
    }

    private void validateRequest(Message<?> request) {
        if (request.getHeader() == null || request.getHeader().getResourceType() == null) {
            throw new MessageFrameworkException("Invalid request: Header or ResourceType missing");
        }
    }

    private Message<?> buildGetRequest(String resourceType, Map<String, String> queryParams) {
        Message<?> request = new Message<>();
        Message.Header header = new Message.Header();
        header.setResourceType(resourceType);
        header.setOperation("READ");
        request.setHeader(header);

        Message.Body body = new Message.Body<>();
        Message.FilterCriteria filterCriteria = new Message.FilterCriteria();
        filterCriteria.setFilters(convertQueryParamsToFilters(queryParams));
        body.setFilterCriteria(filterCriteria);
        request.setBody(body);

        return request;
    }

    private Map<String, Object> convertQueryParamsToFilters(Map<String, String> queryParams) {
        return new HashMap<>(queryParams);
    }
}
