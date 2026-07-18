package com.example.university_score_tracking;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class IO {
    private String fileName = "Courses.txt";

    public boolean loadFile(GPA_Calculator gpaCalculator) {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                return false;
            }

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(",");

                String semester = data.length >= 5
                        ? data[4]
                        : "Unassigned";

                CourseStorage courseStorage = new CourseStorage(
                        data[0],
                        data[1],
                        Double.parseDouble(data[2]),
                        Integer.parseInt(data[3]),
                        semester
                );
                gpaCalculator.addCourse(courseStorage);
            }
            input.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveFile(GPA_Calculator gpaCalculator) {
        try {
            FileWriter output = new FileWriter(fileName);
            for (CourseStorage courseStorage: gpaCalculator.returnCourses()) {

                output.write(String.valueOf(courseStorage.getCourseName()));
                output.write(",");
                output.write(courseStorage.getLetterGrade());
                output.write(",");
                output.write(String.valueOf(courseStorage.getGrade()));
                output.write(",");
                output.write(String.valueOf(courseStorage.getUnit()));
                output.write(",");
                output.write(String.valueOf(courseStorage.getSemester()));
                output.write("\n");
            }
            output.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
