package model;

public class Version {
    private Integer idNode;
    private Integer counter;
    
    public Version(Integer idNode, Integer counter) {
        this.idNode = idNode;
        this.counter = counter;
    }
    
    public Integer getIdNode() {
        return idNode;
    }
    
    public void setIdNode(Integer idNode) {
        this.idNode = idNode;
    }
    
    public Integer getCounter() {
        return counter;
    }
    
    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}