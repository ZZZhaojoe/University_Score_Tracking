package com.example.university_score_tracking;

import java.util.ArrayList;

public class DegreePlanner {

    private ArrayList<DegreeRequirement> requirements;

    public DegreePlanner() {
        requirements = new ArrayList<>();

        requirements.add(new DegreeRequirement("CPSC 231"));
        requirements.add(new DegreeRequirement("CPSC 233"));
        requirements.add(new DegreeRequirement("CPSC 251"));
        requirements.add(new DegreeRequirement("CPSC 331"));
        requirements.add(new DegreeRequirement("CPSC 351"));
        requirements.add(new DegreeRequirement("CPSC 355"));
    }

    public boolean markCompleted(String courseCode) {
        for (DegreeRequirement degreeRequirement:requirements) {
            if (degreeRequirement.getCourseCode().equals(courseCode)) {
                degreeRequirement.setCompleted(true);
                return true;
            }
        }
        return false;
    }

    public void resetRequirement() {
        for (DegreeRequirement degreeRequirement:requirements) {
            degreeRequirement.setCompleted(false);
        }
    }

    public int completedCourse() {
        int count = 0;
        for (DegreeRequirement degreeRequirement:requirements) {
            if (degreeRequirement.isCompleted()) {
                count ++;
            }
        }
        return count;
    }

    public ArrayList<DegreeRequirement> getRequirements() {
        return requirements;
    }
}
