package com.example.eileen.smartstorage;

/**
 * Created by Eileen on 6/12/2017.
 */

public class HistoryAdmin {
    private String StudentID;
    private String Date;
    private String Status;
    private String Slot;

    public HistoryAdmin(){}

    public HistoryAdmin(String StudentID, String Date, String Status, String Slot){
        this.StudentID = StudentID;
        this.Date = Date;
        this.Status = Status;
        this.Slot = Slot;

    }

    public String getSlot() {
        return Slot;
    }

    public void setSlot(String slot) {
        Slot = slot;
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

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }
}
