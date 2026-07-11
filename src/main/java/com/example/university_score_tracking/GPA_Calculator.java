package com.example.university_score_tracking;

import java.util.ArrayList;

public class GPA_Calculator {
    private ArrayList<CourseStorage> courses;

    public GPA_Calculator() {
        courses = new ArrayList<>();
    }

    public double calculateGPA() {

        double totalPoint = 0;
        int totalUnit = 0;

        for (CourseStorage courseStorage: courses) {
            totalPoint += courseStorage.getGrade() * courseStorage.getUnit();
            totalUnit += courseStorage.getUnit();
        }

        if (totalUnit == 0) {
            return 0;
        }

        return totalPoint/totalUnit;
    }

    public void addCourse(CourseStorage courseStorage) {
        courses.add(courseStorage);
    }

    public void removeCourse(CourseStorage courseStorage) {
            courses.remove(courseStorage);
    }

    public ArrayList<CourseStorage> returnCourses() {
        return courses;
    }

    public void clearCourses() {
        courses.clear();
    }

}
