package com.grim.ecom.dto;

import com.grim.ecom.model.Address;

public record CustomerResponse(
         String id,
         String firstName,
         String lastName,
         String email,
         Address address
) {
}
