package com.bitcamp.centro.estetico.utils;

public enum RegexConst {
    TEXT("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$"),
    PHONE("^(\\+\\d{1,3}[- ]?)?\\d{8,15}$"),
    MAIL("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
    EU_TIN("^[A-Z0-9]{16}$"),
    P_IVA("^\\d{11}$"),
    VAT("^\\d{1,3}(\\.\\d{1,2})?$"),
    ALPHANUMERIC("^[A-Za-z0-9\\s,.'-]{2,100}$"),
    PASSWORD("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"),
    IBAN("IT\\d{2}[ ][a-zA-Z]\\d{3}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{3}|IT\\d{2}[a-zA-Z]\\d{22}"),
    REA("^\\S{1,10}");


    private String type;

    private RegexConst(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
