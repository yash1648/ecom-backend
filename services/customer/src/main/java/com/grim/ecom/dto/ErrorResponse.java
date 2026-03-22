package com.grim.ecom.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
