package com.springmate.framework.core;

import lombok.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Message<T> implements Serializable {
    private Header header;
    private Body<T> body;
    private Status status;

    @Data
    public static class Header {
        private String messageId;
        private String resourceType;
        private String operation;
        private Map<String, String> metadata;

        public Header() {
            this.metadata = new HashMap<>();
        }
    }

    @Data
    public static class Body<T> {
        private T data;
        private FilterCriteria filterCriteria;
        private Pagination pagination;
    }

    @Data
    public static class FilterCriteria {
        private Map<String, Object> filters;
        private String sortBy;
        private String sortOrder;

        public FilterCriteria() {
            this.filters = new HashMap<>();
        }
    }

    @Data
    public static class Pagination {
        private Integer pageNumber;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;
    }

    @Data
    public static class Status {
        private String code;
        private String message;
        private Map<String, Object> additionalInfo;

        public Status() {
            this.additionalInfo = new HashMap<>();
        }
    }
}
