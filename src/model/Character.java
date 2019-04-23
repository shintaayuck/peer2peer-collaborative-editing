package model;

import java.io.Serializable;

public class Character implements Serializable {
    private char value;
    private Float position;
    private Boolean isInsert;
    private Version insertVersion;
    private Version deleteVersion;
    
    public Character(char value, Float position, Version insertVersion) {
        this.value = value;
        this.position = position;
        this.isInsert = true;
        this.insertVersion = insertVersion;
        this.deleteVersion = null;
    }
    
    public char getValue() {
        return value;
    }
    
    public Float getPosition() {
        return position;
    }
    
    public Boolean getInsert() {
        return isInsert;
    }
    
    public Version getInsertVersion() {
        return insertVersion;
    }
    
    public Version getDeleteVersion() {
        return deleteVersion;
    }
    
    public void delete(Version version) {
        isInsert = false;
        this.deleteVersion = version;
    }
}
