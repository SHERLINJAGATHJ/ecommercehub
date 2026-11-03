package com.sherlin.ecommercehub.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {

}
