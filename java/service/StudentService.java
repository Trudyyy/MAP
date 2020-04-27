package service;

import domain.Student;
import repository.Repository;
import validator.StudentValidator;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private Repository<String, Student> repository;
    private StudentValidator validator;
    private int idCount;

    public StudentService(Repository<String, Student> repository, StudentValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.idCount = setIdCount();
    }

    private int setIdCount() {
        int max = 0;
        for (Student student :
                this.findAll()) {
            int idEntity = Integer.parseInt(student.getId());
            if (idEntity > max) {
                max = idEntity;
            }
        }
        return max + 1;
    }


    public List<Student> allStudentsFromAGroup(String group) {
        List<Student> studentList = new ArrayList<Student>();
        for (Student student :
                this.repository.findAll()) {
            studentList.add(student);
        }
        return studentList
                .stream()
                .filter(x -> x.getGroup().equals(group))
                .collect(Collectors.toList());
    }

    public void save(String name, String firstName, String group,
                     String email, String cadruDidacticIndrumatorLab) throws ValidationException {
        Student student = new Student("1", name, firstName, group, email, cadruDidacticIndrumatorLab);
        this.validator.validate(student);
        student.setId(String.valueOf(this.idCount));
        this.idCount++;
        this.repository.save(student);

    }

    public Student findOne(String id) {
        return this.repository.findOne(id);
    }

    public Student delete(String id) {
        return this.repository.delete(id);
    }

    public Iterable<Student> findAll() {
        return this.repository.findAll();
    }

    public void update(String id, String name, String firstName, String group,
                       String email, String cadruDidacticIndrumatorLab) {
        Student oldStudent = this.repository.findOne(id);
        Student newStudent = oldStudent;
        newStudent.setName(name);
        newStudent.setFirstName(firstName);
        newStudent.setGroup(group);
        newStudent.setEmail(email);
        newStudent.setTeacherTrainingLab(cadruDidacticIndrumatorLab);
        try {
            this.validator.validate(newStudent);
            this.repository.update(oldStudent, newStudent);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public List<Student> findAllById(String studentId, List<Student> studentList) {
        return studentList.stream()
                .filter(x -> x.getId().contains(studentId))
                .collect(Collectors.toList());
    }

    public List<Student> findAllByName(String name, List<Student> studentList) {
        return studentList.stream()
                .filter(x -> x.getName().contains(name))
                .collect(Collectors.toList());
    }

    public List<Student> findAllByFirstName(String firstName, List<Student> studentList) {
        return studentList.stream()
                .filter(x -> x.getFirstName().contains(firstName))
                .collect(Collectors.toList());
    }

    public List<Student> findAllByEmail(String email, List<Student> studentList) {
        return studentList.stream()
                .filter(x -> x.getEmail().contains(email))
                .collect(Collectors.toList());
    }

    public List<Student> findAllByGroup(String group, List<Student> studentList) {
        return studentList.stream()
                .filter(x -> x.getGroup().contains(group))
                .collect(Collectors.toList());
    }

    public List<Student> findAllByTeacherTrainingLab(String teacherTrainingLab, List<Student> studentList) {
        return studentList.stream()
                .filter(x -> x.getTeacherTrainingLab().contains(teacherTrainingLab))
                .collect(Collectors.toList());
    }

}
