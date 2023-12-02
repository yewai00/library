package com.sampleApp.library.exceptions;

import java.util.Map;

public class UploadPdfFileException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Map<String, String> details;

    public UploadPdfFileException(String message, Map<String, String> details) {
        super(message);
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return this.details;
    }
}
