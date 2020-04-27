package service;

import config.ApplicationContext;
import domain.Grade;
import domain.Homework;
import domain.UnivYearStructure;
import repository.Repository;
import validator.GradeValidator;
import validator.ValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradeService {
    private Repository<String, Grade> repository;
    private GradeValidator validator;
    private UnivYearStructure univYearStructure;
    private StudentService studentService;
    private HomeworkService homeworkService;
    private TeacherService teacherService;


    public GradeService(Repository<String, Grade> repository, GradeValidator validator, UnivYearStructure univYearStructure,
                        StudentService studentService, HomeworkService homeworkService, TeacherService teacherService) {
        this.repository = repository;
        this.validator = validator;
        this.univYearStructure = univYearStructure;
        this.studentService = studentService;
        this.homeworkService = homeworkService;
        this.teacherService = teacherService;
    }

    public boolean isTeacherLate(Homework homework) {
        return this.univYearStructure.getCurrentWeek() > homework.getDeadlineWeek();
    }

    public List<Grade> allTheStudentsWithGradeAtAHomework(String idHomework) {
        List<Grade> gradeList = new ArrayList<Grade>();
        for (Grade grade :
                this.repository.findAll()) {
            gradeList.add(grade);
        }
        return gradeList
                .stream()
                .filter(x -> x.getHomeworkId().equals(idHomework))
                .collect(Collectors.toList());
    }

    public List<Grade> allTheStudentsWithAGradeAtAHomeworkAtATeacher(String idHomework, String teacher) {
        List<Grade> gradeList = new ArrayList<Grade>();
        for (Grade grade :
                this.repository.findAll()) {
            gradeList.add(grade);
        }

        return gradeList
                .stream()
                .filter(x -> x.getHomeworkId().equals(idHomework) && x.getTeacherId().equals(teacher))
                .collect(Collectors.toList());
    }

    public List<Grade> allTheGradesAtAHomeworkFromAGivenWeek(String idHomework, String week) {
        List<Grade> gradeList = (List<Grade>) this.repository.findAll();
        Predicate<Grade> gradePredicate = new Predicate<Grade>() {
            @Override
            public boolean test(Grade grade) {
                return grade.getHomeworkId().equals(idHomework) && (String.valueOf(univYearStructure.getCurrentWeekFromLocalDateTime(grade.getLocalDateTime())).equals(week));
            }
        };
        return gradeList
                .stream()
                .filter(x -> gradePredicate.test(x))
                .collect(Collectors.toList());
    }

    public void save(String studentId, String homeworkId, String value, String teacherId) {
        Grade grade = new Grade(studentId, homeworkId, Integer.parseInt(value), this.univYearStructure.getCurrentDateTime(), teacherId);
        try {
            this.validator.validate(grade);
            this.repository.save(grade);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void update(String studentId, String homeworkId, String value, String teacherId) {
        Grade oldGrade = this.repository.findOne(studentId + " " + homeworkId);
        if (oldGrade != null) {
            Grade newGrade = oldGrade;
            newGrade.setValue(Integer.parseInt(value));
            newGrade.setTeacherId(teacherId);
            newGrade.setLocalDateTime(this.univYearStructure.getCurrentDateTime());
            this.repository.update(oldGrade, newGrade);
        }

    }

    public Grade delete(String id) {
        return this.repository.delete(id);
    }

    public Grade findOne(String id) {//id = studentId + " " + homeworkId
        return this.repository.findOne(id);
    }

    public List<Grade> findALlByStudentId(String studentId, List<Grade> gradeList) {
        return gradeList.stream()
                .filter(x -> x.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Grade> findAllByHomeworkId(String homeworkId, List<Grade> gradeList) {
        return gradeList.stream()
                .filter(x -> x.getHomeworkId().contains(homeworkId))
                .collect(Collectors.toList());
    }

    public List<Grade> findAllByTeacherId(String teacherId, List<Grade> gradeList) {
        return gradeList.stream()
                .filter(x -> x.getTeacherId().contains(teacherId))
                .collect(Collectors.toList());
    }

    public List<Grade> findAllByValue(int value, List<Grade> gradeList) {
        return gradeList.stream()
                .filter(x -> x.getValue() == value)
                .collect(Collectors.toList());
    }

    public Iterable<Grade> findAll() {
        return this.repository.findAll();
    }

    public void saveToTxtFile(String studentName, String idHomework, String value, int theWeeKWhenTheStudentShowTheAssignment, int deadlineWeek, String feedback) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(
                ApplicationContext.getPROPERTIES().getProperty("database.catalog.gradesTXT") + studentName + ".txt"
        ))) {
            String line = "";
            line = "Homework: " + idHomework +
                    "Grade: " + value +
                    "The week when the student show the homework: " + theWeeKWhenTheStudentShowTheAssignment +
                    "Deadline:" + deadlineWeek +
                    "Feedback: " + feedback;
            printWriter.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
