package com.springmate.framework.service;

import com.springmate.framework.constants.MessageConstants;
import com.springmate.framework.core.Message;
import com.springmate.framework.exception.MessageFrameworkException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MessageServiceResolver {

    private final ApplicationContext applicationContext;
    private final Map<String, MessageService<?>> serviceCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Message<T> resolveAndProcess(Message<T> request) {
        String resourceType = request.getHeader().getResourceType();
        MessageService<T> service = (MessageService<T>) findService(resourceType);

        try {
            Message<T> response = service.process(request);
            setSuccessStatus(response);
            return response;
        } catch (Exception e) {
            return handleError(e);
        }
    }

    private MessageService<?> findService(String resourceType) {
        return serviceCache.computeIfAbsent(resourceType, this::lookupService);
    }

    private MessageService<?> lookupService(String resourceType) {
        String serviceName = resourceType.toLowerCase() + "MessageService";
        try {
            return applicationContext.getBean(serviceName, MessageService.class);
        } catch (Exception e) {
            throw new MessageFrameworkException("No service found for resource type: " + resourceType);
        }
    }

    private <T> void setSuccessStatus(Message<T> response) {
        Message.Status status = new Message.Status();
        status.setCode(MessageConstants.SUCCESS_CODE);
        status.setMessage(MessageConstants.SUCCESS_MESSAGE);
        response.setStatus(status);
    }

    private <T> Message<T> handleError(Exception e) {
        Message<T> errorResponse = new Message<>();
        Message.Status status = new Message.Status();
        status.setCode(MessageConstants.ERROR_CODE);
        status.setMessage(e.getMessage());
        errorResponse.setStatus(status);
        return errorResponse;
    }
}