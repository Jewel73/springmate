package com.springmate.framework.service;

import com.springmate.framework.core.Message;
import com.springmate.framework.core.SpecificationBuilder;
import com.springmate.framework.core.repository.BaseRepository;
import com.springmate.framework.exception.MessageFrameworkException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class BaseDataService<T, ID> {

    private final BaseRepository<T, ID> repository;
    private final SpecificationBuilder<T> specificationBuilder;

    @Transactional(readOnly = true)
    public Page<T> findAll(Message.FilterCriteria filterCriteria, Message.Pagination pagination) {
        Specification<T> spec = specificationBuilder.buildSpecification(filterCriteria);
        PageRequest pageRequest = specificationBuilder.buildPageRequest(pagination, filterCriteria);
        return repository.findAll(spec, pageRequest);
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new MessageFrameworkException("Entity not found with id: " + id));
    }
}

