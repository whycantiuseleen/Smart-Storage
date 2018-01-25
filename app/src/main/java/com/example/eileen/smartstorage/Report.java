package com.example.eileen.smartstorage;

/**
 * Created by Eileen on 6/12/2017.
 */

public class Report {
    private String StudentID;
    private String Date;
    private String Tool;
    private String Problem;

    public Report(){}

    public Report(String StudentID, String Date, String Tool, String Problem){
        this.StudentID = StudentID;
        this.Date = Date;
        this.Tool = Tool;
        this.Problem = Problem;

    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getTool() {
        return Tool;
    }

    public void setTool(String tool) {
        Tool = tool;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }
}
