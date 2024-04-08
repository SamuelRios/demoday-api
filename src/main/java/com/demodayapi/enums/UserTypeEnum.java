package com.demodayapi.enums;

public enum UserTypeEnum {
    STUDENT(0),
    PROFESSOR(1);

    private int typeCode;

    public int getTypeCode() {
        return typeCode;
    }

    UserTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public static UserTypeEnum valueOf(int typeCode){
        for(UserTypeEnum value: UserTypeEnum.values()){
            if(typeCode == value.getTypeCode())
                return value;
        }
        throw new IllegalArgumentException("Códito do Tipo de Usuário inválido.");
    }
}
