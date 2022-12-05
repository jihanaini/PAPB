package com.example.projectpapb;

public class Todolist {
    private String judul;
    private String deadline;
    private boolean isDone;

    public Todolist(){
    }

    public Todolist(String judul, String deadline, boolean isDone){
        this.judul = judul;
        this.deadline = deadline;
        this.isDone = isDone;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
