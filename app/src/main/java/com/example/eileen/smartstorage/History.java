package com.example.eileen.smartstorage;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Eileen on 29/11/2017.
 */

public class History {
    private String Tools;
    private String Date;
    private String Status;

    public History(){}

    public History(String Tools, String Date, String Status){
        this.Tools = Tools;
        this.Date = Date;
        this.Status = Status;

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getTools() {
        return Tools;
    }

    public void setTools(String tools) {
        this.Tools = tools;
    }
}
