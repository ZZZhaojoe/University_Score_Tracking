package com.example.university_score_tracking;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class IO {
    private String fileName = "Courses.txt";

    public void loadFile(GPA_Calculator gpaCalculator) {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                return;
            }

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(",");

                CourseStorage courseStorage = new CourseStorage(
                        data[0],
                        Double.parseDouble(data[1]),
                        Integer.parseInt(data[2])
                );
                gpaCalculator.addCourse(courseStorage);
            }
            input.close();

        } catch (Exception e) {
            return;
        }
    }

    public void saveFile(GPA_Calculator gpaCalculator) {
        try {
            FileWriter output = new FileWriter(fileName);
            for (CourseStorage courseStorage: gpaCalculator.returnCourses()) {

                output.write(String.valueOf(courseStorage.returnCourse()));
                output.write(",");
                output.write(String.valueOf(courseStorage.returnGrade()));
                output.write(",");
                output.write(String.valueOf(courseStorage.returnUnit()));
                output.write("\n");
            }
            output.close();

        } catch (Exception e) {
            return;
        }
    }
}
