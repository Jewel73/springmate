package com.springmate.framework.service;

import com.springmate.framework.core.Message;

public interface MessageService<T> {
    Message<T> process(Message<T> request);
}