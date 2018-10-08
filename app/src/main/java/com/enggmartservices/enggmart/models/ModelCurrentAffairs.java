package com.enggmartservices.enggmart.models;

import java.io.Serializable;

public class ModelCurrentAffairs implements Serializable{
    private String itemDate = "";
    private String pdfFile = "";

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }
}
