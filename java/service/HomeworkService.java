package service;

import domain.Homework;
import domain.UnivYearStructure;
import repository.Repository;
import validator.HomeworkValidator;
import validator.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class HomeworkService {
    private Repository<String, Homework> repository;
    private HomeworkValidator validator;
    private UnivYearStructure univYearStructure;
    private int idCount;

    public HomeworkService(Repository<String, Homework> repository, HomeworkValidator validator, UnivYearStructure univYearStructure) {
        this.repository = repository;
        this.validator = validator;
        this.univYearStructure = univYearStructure;
        this.idCount = setIdCount();
    }

    private int setIdCount() {
        int max = 0;
        for (Homework homework :
                this.findAll()) {
            int idEntity = Integer.parseInt(homework.getId());
            if (idEntity > max) {
                max = idEntity;
            }
        }
        return max + 1;
    }


    public Homework findOne(String id) {
        return this.repository.findOne(id);
    }


    public int maxGrade(String id, int theWeekWhenTheStudentShowTheAssignment) {
        int currentWeek = theWeekWhenTheStudentShowTheAssignment;

        Homework homework = this.findOne(id);
        if (homework.getDeadlineWeek() >= currentWeek)
            return 10;
        if (homework.getDeadlineWeek() < currentWeek)
            return 10 - (currentWeek - homework.getDeadlineWeek());
        if ((currentWeek - homework.getDeadlineWeek()) > 2)
            return 1;

        return 1;
    }

    public Homework delete(String id) {
        return this.repository.delete(id);
    }

    public Iterable<Homework> findAll() {
        return this.repository.findAll();
    }

    public void save(String description, String deadlineWeek) throws ValidationException {//-----------startweek automate
        Homework homework = new Homework(String.valueOf(this.idCount), description, this.univYearStructure.getCurrentWeek(), Integer.parseInt(deadlineWeek));
        this.idCount++;
        this.validator.validate(homework);
        this.repository.save(homework);

    }

    public void update(String id, String description, String deadlineWeek) throws ValidationException {
        Homework oldHomework = this.repository.findOne(id);
        Homework newHomework = oldHomework;
        if (Integer.parseInt(deadlineWeek) < this.univYearStructure.getCurrentWeek()) {
            throw new ValidationException("The deadline is smaller than the curent week!\n The current week is ".concat(String.valueOf(this.univYearStructure.getCurrentWeek())).concat(" \nand the deadline you entered is ".concat(deadlineWeek)));
        }
        newHomework.setDescription(description);
        newHomework.setDeadlineWeek(Integer.parseInt(deadlineWeek));

        //-------to verify if the new deadlineWeek
        //-------is greater than actualCurrentWeek


        this.validator.validate(newHomework);
        this.repository.update(oldHomework, newHomework);

    }

    public List<Homework> findAllById(String id, List<Homework> homeworkList) {
        return homeworkList.stream()
                .filter(x -> x.getId().contains(id))
                .collect(Collectors.toList());
    }

    public List<Homework> findAllByDescription(String description, List<Homework> homeworkList) {
        return homeworkList.stream()
                .filter(x -> x.getDescription().contains(description))
                .collect(Collectors.toList());
    }

    public List<Homework> findAllByStartWeek(int startWeek, List<Homework> homeworkList) {
        return homeworkList.stream()
                .filter(x -> x.getStartWeek() == startWeek)
                .collect(Collectors.toList());
    }

    public List<Homework> findAllByDeadlineWeek(int deadlineWeek, List<Homework> homeworkList) {
        return homeworkList.stream()
                .filter(x -> x.getDeadlineWeek() == deadlineWeek)
                .collect(Collectors.toList());
    }


}
