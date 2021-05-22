package classes;

public class Course {
    String courseName ;
    String courseCode ; 
    String teacher ;
    String Field ;
    int Duration ;
    int fees ;

    public Course() {
    }

    public Course(String courseName, String courseCode, String teacher, String field, int duration, int fees) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.teacher = teacher;
        Field = field;
        Duration = duration;
        this.fees = fees;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }
}
