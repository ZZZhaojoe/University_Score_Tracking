package com.example.university_score_tracking;

import java.util.ArrayList;

public class GPA_Calculator {
    private ArrayList<CourseStorage> courses;

    public GPA_Calculator() {
        courses = new ArrayList<>();

        courses.add(new CourseStorage("CPSC 231", 2.7, 3));
        courses.add(new CourseStorage("CPSC 233", 3.3, 3));
    }


    public double calculateGPA() {

        double totalPoint = 0;
        int totalUnit = 0;

        for (CourseStorage courseStorage: courses) {
            totalPoint += courseStorage.returnGrade() * courseStorage.returnUnit();
            totalUnit += courseStorage.returnUnit();
        }

        if (totalUnit == 0) {
            return 0;
        }

        return totalPoint/totalUnit;
    }
}
