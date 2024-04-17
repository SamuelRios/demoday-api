package com.demodayapi.enums;

public enum ProjectTypeEnum {
    IC("IC"),
    TCC("TCC"),
    DISC("DISC"),
    MSC("MSC"),
    PHD("PHD");

    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }

    ProjectTypeEnum(String typeCode) {
        this.typeCode = typeCode;
    }

    public static ProjectTypeEnum valueofProjectEnum(String typeCode){
        for(ProjectTypeEnum value: ProjectTypeEnum.values()){
            if (typeCode.equals(value.getTypeCode())) {
                return value;
        }   
    }        
   throw new IllegalArgumentException("Categoria inválida.");
}
}
