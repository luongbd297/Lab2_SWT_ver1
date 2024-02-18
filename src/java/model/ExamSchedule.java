/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author 84354
 */
public class ExamSchedule {

    private String id, subject, date, location, start, end;

    public ExamSchedule() {
    }

    public ExamSchedule(String id, String subject, String date, String location, String start, String end) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.location = location;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ExamSchedule{" + "id=" + id + ", subject=" + subject + ", date=" + date + ", location=" + location + ", start=" + start + ", end=" + end + '}';
    }

}
