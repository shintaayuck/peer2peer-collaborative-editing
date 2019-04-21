package model;

public class CRDT {
    private String idSite;
    private Boolean isInsert;
    private Character value;
    private Float[] positions;
    private Integer counter;
    
    public CRDT(String idSite, Boolean isInsert, Character value, Float[] positions, Integer counter) {
        this.idSite = idSite;
        this.isInsert = isInsert;
        this.value = value;
        this.positions = positions;
        this.counter = counter;
    }
    
    public String getIdSite() {
        return idSite;
    }
    
    public void setIdSite(String idSite) {
        this.idSite = idSite;
    }
    
    public Boolean getInsert() {
        return isInsert;
    }
    
    public void setInsert(Boolean insert) {
        isInsert = insert;
    }
    
    public Character getValue() {
        return value;
    }
    
    public void setValue(Character value) {
        this.value = value;
    }
    
    public Float[] getPositions() {
        return positions;
    }
    
    public void setPositions(Float[] positions) {
        this.positions = positions;
    }
    
    public Integer getCounter() {
        return counter;
    }
    
    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
