package com.migration.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum UserType {
    SUPERUSER("01", "시청관리자"),
    ADMIN("02", "시설관리자"),
    USER("03", "일반사용자");

    private String code;
    private String name;

    UserType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserType enumOf(String code) {
        return Arrays.stream(UserType.values())
                .filter(u -> u.getCode().equals(code))
                .findAny().orElse(null);
    }
}