package com.migration.jwt.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String secretKey;
}
