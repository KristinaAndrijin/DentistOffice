package com.project.dentistoffice.dto;

public class StringCodeDTO {
    private String str;
    private boolean code;

    public StringCodeDTO() {
    }

    public StringCodeDTO(String str, boolean code) {
        this.str = str;
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }
}
