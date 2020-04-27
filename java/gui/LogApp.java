package gui;

import config.ApplicationContext;
import domain.SemesterStructure1;
import domain.SemesterStructure2;
import domain.UnivYearStructure;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

public class LogApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        SemesterStructure1 semesterStructure1 = new SemesterStructure1();
        SemesterStructure2 semesterStructure2 = new SemesterStructure2();
        UnivYearStructure univYearStructure = new UnivYearStructure(semesterStructure1, semesterStructure2);

        StudentValidator studentValidator = new StudentValidator();
        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(
                ApplicationContext.getPROPERTIES().getProperty("students")
        );
        StudentService studentService = new StudentService(studentXMLRepository, studentValidator);

        HomeworkValidator homeworkValidator = new HomeworkValidator();
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
        GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(
                ApplicationContext.getPROPERTIES().getProperty("grades")
        );
        GradeService gradeService = new GradeService(gradeXMLRepository, gradeValidator, univYearStructure, studentService, homeworkService, teacherService);


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/gui.fxml"));

        AnchorPane root = loader.load();
        Controller ctrl = loader.getController();


        ctrl.setServices(studentService, teacherService, homeworkService, gradeService);
        ctrl.setUnivYear(univYearStructure);
        ctrl.updateModels();
        primaryStage.setScene(new Scene(root, 1181, 874));
        primaryStage.setTitle("Hello World");
        primaryStage.setMinHeight(874);
        primaryStage.setMinWidth(984);
        primaryStage.show();

    }

}
