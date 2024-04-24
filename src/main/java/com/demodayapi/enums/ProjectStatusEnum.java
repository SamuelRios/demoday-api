package com.demodayapi.enums;

public enum ProjectStatusEnum {
    SUBMETIDO("SUBMETIDO"),
    EM_ANALISE("EM_ANALISE"),
    RECUSADO("RECUSADO"),
    ACEITO("ACEITO");
   

    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }


    ProjectStatusEnum(String typeCode) {
        this.typeCode = typeCode;
    }

    public static ProjectStatusEnum valueofStatusEnum(String typeCode){
        for(ProjectStatusEnum value: ProjectStatusEnum.values()){
            if (typeCode.equals(value.getTypeCode())) {
                return value;
        }   
        
        }   
    throw new IllegalArgumentException("Códito de status inválido.");
    }   
}
