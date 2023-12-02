package com.sampleApp.library.model.dto;

import java.util.Map;

public record ErrorResponseDTO(
        String error,
        String messages,
        Map<String,String> details
        ) {}
