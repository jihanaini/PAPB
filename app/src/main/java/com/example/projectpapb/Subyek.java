package com.example.projectpapb;

public class Subyek {
    private String id;
    private String subyek;

    public Subyek(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subyek(String subyek){
        this.subyek = subyek; this.id = id;
    }

    public String getSubyek(){
        return subyek;
    }

    public void setSubyek(String subyek){
        this.subyek = subyek;
    }
}
