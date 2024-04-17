package com.demodayapi.enums;

public enum SubmitProjectEnum {
    IC("IC"),
    TCC("TCC"),
    DISC("DISC"),
    MSC("MSC"),
    PHD("PHD");

    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }

    SubmitProjectEnum(String typeCode) {
        this.typeCode = typeCode;
    }

    public static SubmitProjectEnum valueofProjectEnum(String typeCode){
        for(SubmitProjectEnum value: SubmitProjectEnum.values()){
            if (typeCode.equals(value.getTypeCode())) {
                return value;
        }   
    }        
   throw new IllegalArgumentException("Categoria inválida.");
}
}
