package domain;

import java.time.LocalDateTime;

public class Grade extends Entity<String> {
    //    Nota: ID, data, profesor, unde ID=ID_Student+ID_Tema (un student la o anumita tema are o
//            singura nota)
    private int value;
    private LocalDateTime localDateTime;
    private String teacherId;
    private String studentId;
    private String homeworkId;

    public Grade(String studentId, String homeworkId, int value, LocalDateTime localDateTime, String teacherId) {
        super(studentId + " " + homeworkId);
        this.value = value;
        this.localDateTime = localDateTime;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.homeworkId = homeworkId;
    }


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getHomeworkId() {
        return this.homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Grade{" +
                " value=" + this.value +
                ", id='" + this.studentId + " " + this.homeworkId + '\'' +
                ", localDateTime=" + this.localDateTime +
                ", teacher='" + this.teacherId + '\'' +
                '}';

    }
}
