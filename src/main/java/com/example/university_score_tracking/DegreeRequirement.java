package com.example.university_score_tracking;

public class DegreeRequirement {
    private String courseCode;
    private boolean completed;

    public DegreeRequirement(String courseCode) {
        this.courseCode = courseCode;
        completed = false;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
