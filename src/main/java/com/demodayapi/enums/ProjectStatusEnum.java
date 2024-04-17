package com.demodayapi.enums;

public enum StatusEnum {
    SUBMETIDO("SUBMETIDO"),
    EM_ANALISE("EM_ANALISE"),
    RECUSADO("RECUSADO"),
    ACEITO("ACEITO");
   

    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }


    StatusEnum(String typeCode) {
        this.typeCode = typeCode;
    }

    public static StatusEnum valueofStatusEnum(String typeCode){
        for(StatusEnum value: StatusEnum.values()){
            if (typeCode.equals(value.getTypeCode())) {
                return value;
        }   
        
        }   
    throw new IllegalArgumentException("Códito de status inválido.");
    }   
}
