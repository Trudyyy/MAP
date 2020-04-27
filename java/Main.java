import config.ApplicationContext;
import domain.SemesterStructure1;
import domain.SemesterStructure2;
import domain.UnivYearStructure;
import interactive.UI;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import repository.TeacherXMLRepository;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import service.TeacherService;
import validator.GradeValidator;
import validator.HomeworkValidator;
import validator.StudentValidator;
import validator.TeacherValidator;

public class Main {
    public static void main(String[] args) {
        SemesterStructure1 semesterStructure1 = new SemesterStructure1();
        SemesterStructure2 semesterStructure2 = new SemesterStructure2();
        UnivYearStructure univYearStructure = new UnivYearStructure(semesterStructure1, semesterStructure2);

        StudentValidator studentValidator = new StudentValidator();

        //StudentRepository studentRepository = new StudentRepository();
        //StudentService studentService = new StudentService(studentRepository, studentValidator);

        //StudentFileRepository studentFileRepository = new StudentFileRepository(
        //        ApplicationContext.getPROPERTIES().getProperty("students")
        //);
        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(
                ApplicationContext.getPROPERTIES().getProperty("students")
        );
        StudentService studentService = new StudentService(studentXMLRepository, studentValidator);


        HomeworkValidator homeworkValidator = new HomeworkValidator();
        //HomeworkRepository homeworkRepository = new HomeworkRepository();
        HomeworkXMLRepository homeworkXMLRepository = new HomeworkXMLRepository(
                ApplicationContext.getPROPERTIES().getProperty("homeworks")
        );
        HomeworkService homeworkService = new HomeworkService(homeworkXMLRepository, homeworkValidator, univYearStructure);


        TeacherValidator teacherValidator = new TeacherValidator();
        //TeacherRepository teacherRepository = new TeacherRepository();
        TeacherXMLRepository teacherXMLRepository = new TeacherXMLRepository(
                ApplicationContext.getPROPERTIES().getProperty("teachers")
        );
        TeacherService teacherService = new TeacherService(teacherXMLRepository, teacherValidator);


        GradeValidator gradeValidator = new GradeValidator();
        //GradeRepository gradeRepository = new GradeRepository();
        GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(
                ApplicationContext.getPROPERTIES().getProperty("grades")
        );
        GradeService gradeService = new GradeService(gradeXMLRepository, gradeValidator, univYearStructure, studentService, homeworkService, teacherService);

        UI ui = new UI(studentService, homeworkService, gradeService, teacherService, univYearStructure);
        ui.run();

        //implement the dates from StuctureSemester must have reading from a file (config.propriets)

        //implement Persistenta datelor: in fisier XML folosind DOM parser (vezi cursul 6)
        //de facut repo cu XML pe fiecare entity


    }
}
