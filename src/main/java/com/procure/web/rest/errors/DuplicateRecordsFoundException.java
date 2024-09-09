package com.procure.web.rest.errors;

public class DuplicateRecordsFoundException extends RuntimeException {

    public DuplicateRecordsFoundException(String errorMessage) {
        super(errorMessage);
    }
}
