package com.grim.product.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
