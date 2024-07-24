package com.migration.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence raw) {
        return raw.toString();
    }

    @Override
    public boolean matches(CharSequence raw, String encoded) {
        return raw.toString().equals(encoded);
    }
}
