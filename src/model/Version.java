package model;

import java.io.Serializable;

public class Version implements Serializable {
    private String idNode;
    private Integer counter;
    
    public Version(String idNode, Integer counter) {
        this.idNode = idNode;
        this.counter = counter;
    }
    
    public String getIdNode() {
        return idNode;
    }
    
    public void setIdNode(String idNode) {
        this.idNode = idNode;
    }
    
    public Integer getCounter() {
        return counter;
    }
    
    public void setCounter(Integer counter) {
        this.counter = counter;
    }

}
