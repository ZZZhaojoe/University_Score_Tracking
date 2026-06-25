package com.example.university_score_tracking;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        GPA_Calculator calc = new GPA_Calculator();
        System.out.println(calc.calculateGPA());

        Application.launch(HelloApplication.class, args);
    }
}
