package com.kesequl.app.model.Entity;

public interface DaoData {
    public String getNama();
    public int getResIdText();
    public int getResIdImage();
    
    default public String getImageLink() {
        return null;
    }
}
