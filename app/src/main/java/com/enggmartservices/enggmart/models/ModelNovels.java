package com.enggmartservices.enggmart.models;

import java.io.Serializable;

public class ModelNovels implements Serializable {
    private String itemImage = "";
    private String pdfFile = "";

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }
}
