package com.migration.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("M", "남자"),
    FEMALE("F", "여자");

    private String code;
    private String name;

    Gender(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Gender enumOf(String code) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.getCode().equals(code))
                .findAny().orElse(null);
    }
}