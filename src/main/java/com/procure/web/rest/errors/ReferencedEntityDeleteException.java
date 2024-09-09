package com.procure.web.rest.errors;

public class ReferencedEntityDeleteException extends RuntimeException {

    public ReferencedEntityDeleteException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
