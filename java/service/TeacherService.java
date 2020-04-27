package service;

import domain.Teacher;
import repository.Repository;
import validator.TeacherValidator;
import validator.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherService {
    private Repository<String, Teacher> repository;
    private TeacherValidator validator;
    private int idCount; //at every start it will set at 0, but if we have already entities with id 0 in repo it will crash
    // I must set it at the high id present + 1

    public TeacherService(Repository<String, Teacher> repository, TeacherValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.idCount = setIdCount();
    }

    private int setIdCount() {
        int max = 0;
        for (Teacher teacher :
                this.findAll()) {
            int idEntity = Integer.parseInt(teacher.getId());
            if (idEntity > max) {
                max = idEntity;
            }
        }
        return max + 1;
    }

    public void save(String name, String firstName, String email) throws ValidationException {
        Teacher teacher = new Teacher("1", name, firstName, email);
        this.validator.validate(teacher);
        teacher.setId(String.valueOf(this.idCount));
        this.idCount++;
        this.repository.save(teacher);

    }

    public void update(String id, String name, String firstName, String email) {
        Teacher oldTeacher = this.repository.findOne(id);
        if (oldTeacher != null) {
            Teacher newTeacher = oldTeacher;
            newTeacher.setName(name);
            newTeacher.setFirstName(firstName);
            newTeacher.setEmail(email);
            this.repository.update(oldTeacher, newTeacher);
        }
    }

    public List<Teacher> findAllById(String id, List<Teacher> teacherList) {
        return teacherList.stream()
                .filter(x -> x.getId().contains(id))
                .collect(Collectors.toList());
    }

    public List<Teacher> findAllByName(String name, List<Teacher> teacherList) {
        return teacherList.stream()
                .filter(x -> x.getName().contains(name))
                .collect(Collectors.toList());
    }

    public List<Teacher> findAllByFirstName(String firstName, List<Teacher> teacherList) {
        return teacherList.stream()
                .filter(x -> x.getFirstName().contains(firstName))
                .collect(Collectors.toList());
    }

    public List<Teacher> findAllByEmail(String email, List<Teacher> teacherList) {
        return teacherList.stream()
                .filter(x -> x.getEmail().contains(email))
                .collect(Collectors.toList());
    }

    public Teacher findOne(String id) {
        return this.repository.findOne(id);
    }

    public Teacher delete(String id) {
        return this.repository.delete(id);
    }

    public Iterable<Teacher> findAll() {
        return this.repository.findAll();
    }

}
