package com.demodayapi.enums;

public enum UserTypeEnum {
    STUDENT("estudante"),
    PROFESSOR("professor");

    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }

    UserTypeEnum(String typeCode) {
        this.typeCode = typeCode;
    }
}
