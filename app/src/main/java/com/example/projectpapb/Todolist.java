package com.example.projectpapb;

public class Todolist {
    private String judul;
    private String deadline;
    private String status;

    public Todolist(){
    }

    public Todolist(String judul, String deadline, String status){
        this.judul = judul;
        this.deadline = deadline;
        this.status = status;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setDone(String status) {
        this.status = status;
    }
}
