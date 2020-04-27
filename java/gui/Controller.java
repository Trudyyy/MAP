package gui;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import service.TeacherService;
import validator.ValidationException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    ChoiceBox<Integer> latePublicationOfGradesChoiceBoxFromGradesTab;

    @FXML
    Button clearFilterFieldsFromButtonStudentsTab;

    @FXML
    Button clearFieldsButtonFromStudetsTab;

    @FXML
    Button clearFilterFieldsButtonFromHomeworksTab;

    @FXML
    Button clearFieldsButtonFromHomeworksTab;

    @FXML
    Button clearFilterFieldsButtonFromGradesTab;

    @FXML
    Button clearFieldsButtonFromGradesTab;

    @FXML
    CheckBox latePublicationOfGradesCheckBoxFromGradesTab;

    private ObservableList<Student> studentsModel = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachersModel = FXCollections.observableArrayList();
    private ObservableList<Grade> gradesModel = FXCollections.observableArrayList();
    private ObservableList<Homework> homeworksModel = FXCollections.observableArrayList();
    private ObservableList<String> groupChoiceBoxModelFromStudentsTab = FXCollections.observableArrayList();
    private ObservableList<Integer> valueChoiceBoxModelFromGradesTab = FXCollections.observableArrayList();
    private ObservableList<Integer> latePublicationOfGradesChoiceBoxModelFromGradesTab = FXCollections.observableArrayList();

    private ObservableList<Integer> startWeekChoiceBoxModelFromHomeworksTab = FXCollections.observableArrayList();
    private ObservableList<Integer> deadlineWeekChoiceBoxModelFromHomeworksTab = FXCollections.observableArrayList();
    private StudentService studentService;
    private TeacherService teacherService;
    private HomeworkService homeworkService;
    private GradeService gradeService;
    private UnivYearStructure univYearStructure;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    //---------------------------HOMEWORK TAB ATRIBUTES
    @FXML
    private TableView<Homework> tableFromHomeworksTab;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnId;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnDescription;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnStartWeek;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnDeadlineWeek;


    @FXML
    private TextField descriptionTextFieldFromHomeworksTab;

    @FXML
    private Button addButtonFromHomeworksTab;

    @FXML
    private Button deleteButtonFromHomeworksTab;

    @FXML
    private Button updateButtonFromHomeworksTab;

    @FXML
    private ChoiceBox<Integer> deadlineWeekChoiceBoxFromHomeworksTab;

    @FXML
    private TextField idFilterTextFieldFromHomeworksTab;

    @FXML
    private TextField descriptionFilterTextFieldFromHomeworksTab;

    @FXML
    private TextField startWeekFilterTextFieldFromHomeworksTab;

    @FXML
    private TextField deadlineWeekFilterTextFieldFromHomeworksTab;


    //----------------------------------STUDENT TAB ATRIBUTION
    @FXML
    private TableView<Student> tableFromStudentsTab;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnId;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnName;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnFirstName;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnGroup;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnEmail;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnTeacherTrainingLab;


    @FXML
    private TextField nameTextFieldFromStudentsTab;

    @FXML
    private TextField firstNameTextFieldFromStudentsTab;

    @FXML
    private Button addButtonFromStudentsTab;

    @FXML
    private Button deleteButtonFromStudentsTab;

    @FXML
    private Button updateButtonFromStudentsTab;

    @FXML
    private ChoiceBox<String> groupChoiceBoxFromStudentsTab;

    @FXML
    private TextField emailTextFieldFromStudentsTab;

    @FXML
    private TextField teacherIdTextFieldFromStudentsTab;

    @FXML
    private TextField idFilterTextFieldFromStudentsTab;

    @FXML
    private TextField nameFilterTextFieldFromStudentsTab;

    @FXML
    private TextField firstNameFilterTextFieldFromStudentsTab;

    @FXML
    private TextField groupFilterTextFieldFromStudentsTab;

    @FXML
    private TextField emailFilterTextFieldFromStudentsTab;

    @FXML
    private TextField teacherIdFilterTextFieldFromStudentsTab;


    //------------------------------GRADES TAB ATRIBUTION
    @FXML
    private TableView<Grade> tableFromGradesTab;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnStudentId;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnHomeworkId;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnValue;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnTeacherId;


    @FXML
    private TextField studentIdTextFieldFromGradesTab;

    @FXML
    private TextField homeworkIdTextFieldFromGradesTab;

    @FXML
    private Button addButtonFromGradesTab;

    @FXML
    private Button deleteButtonFromGradesTab;

    @FXML
    private Button updateButtonFromGradesTab;

    @FXML
    private ChoiceBox<Integer> valueChoiceBoxFromGradesTab;

    @FXML
    private TextField teacherIdTextFieldFromGradesTab;

    @FXML
    private CheckBox motivationCheckBoxFromGradesTab;

    @FXML
    private TextField studentIdFilterTextFieldFromGradesTab;

    @FXML
    private TextField homeworkIdFilterTextFieldFromGradesTab;

    @FXML
    private TextField valueFilterTextFieldFromGradesTab;

    @FXML
    private TextField teacherIdFilterTextFieldFromGradesTab;


    //-----------METHOD

    @FXML
    public void handleNameFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        keyEvent.consume();
        List<Student> initList = new ArrayList<Student>();

        for (Student student :
                this.studentService.findAll()) {
            initList.add(student);
        }


        List<Student> studentList = this.studentService.findAllById(
                idFilterTextFieldFromStudentsTab.getText(),
                this.studentService.findAllByName(
                        nameFilterTextFieldFromStudentsTab.getText(),
                        this.studentService.findAllByFirstName(
                                firstNameFilterTextFieldFromStudentsTab.getText(),
                                this.studentService.findAllByGroup(
                                        groupFilterTextFieldFromStudentsTab.getText(),
                                        this.studentService.findAllByEmail(
                                                emailFilterTextFieldFromStudentsTab.getText(),
                                                this.studentService.findAllByTeacherTrainingLab(
                                                        teacherIdFilterTextFieldFromStudentsTab.getText(),
                                                        initList
                                                )
                                        )
                                )
                        )
                )

        );
        studentsModel.setAll(studentList);
        tableFromStudentsTab.setItems(studentsModel);
    }

    @FXML
    public void handleFirstNameFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleTeacherIdFilterTextFieldFromStudentsTab1(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleStudentIdFilterTextFieldFromGradesTab(KeyEvent keyEvent) {
        keyEvent.consume();
        List<Grade> initList = new ArrayList<Grade>();

        for (Grade grade :
                this.gradeService.findAll()) {
            initList.add(grade);
        }

        int value = 0;
        if (!valueFilterTextFieldFromGradesTab.getText().equals("")) {
            value = Integer.parseInt(valueFilterTextFieldFromGradesTab.getText());
            List<Grade> gradeList = this.gradeService.findAllByHomeworkId(
                    homeworkIdFilterTextFieldFromGradesTab.getText(),
                    this.gradeService.findALlByStudentId(
                            studentIdFilterTextFieldFromGradesTab.getText(),
                            this.gradeService.findAllByTeacherId(
                                    teacherIdFilterTextFieldFromGradesTab.getText(),
                                    this.gradeService.findAllByValue(
                                            value,
                                            initList
                                    )
                            )
                    )
            );
            gradesModel.setAll(gradeList);
            tableFromGradesTab.setItems(gradesModel);
        } else {
            List<Grade> gradeList = this.gradeService.findAllByHomeworkId(
                    homeworkIdFilterTextFieldFromGradesTab.getText(),
                    this.gradeService.findALlByStudentId(
                            studentIdFilterTextFieldFromGradesTab.getText(),
                            this.gradeService.findAllByTeacherId(
                                    teacherIdFilterTextFieldFromGradesTab.getText(),
                                    initList
                            )
                    )
            );
            gradesModel.setAll(gradeList);
            tableFromGradesTab.setItems(gradesModel);
        }

    }

    @FXML
    public void handleValueFilterTextFieldFromGradesTab(KeyEvent keyEvent) {
        handleStudentIdFilterTextFieldFromGradesTab(keyEvent);
    }

    @FXML
    public void handleHomeworkIdFilterTextFieldFromGradesTab(KeyEvent keyEvent) {
        handleStudentIdFilterTextFieldFromGradesTab(keyEvent);
    }

    @FXML
    public void handleMotivationCheckBoxFromGradesTab(ActionEvent actionEvent) {
        this.valueChoiceBoxFromGradesTab.setValue(this.valueChoiceBoxFromGradesTab.getValue() + 1);
    }

    @FXML
    public void handleUpdateButtonFromGradesTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleDeleteButtonFromGradesTab(ActionEvent actionEvent) {
        if (tableFromGradesTab.getSelectionModel().getSelectedItem() != null) {
            this.gradeService.delete(tableFromGradesTab.getSelectionModel().getSelectedItem().getStudentId() + " " + tableFromGradesTab.getSelectionModel().getSelectedItem().getHomeworkId());
        } else {
            MessageAlert.showErrorMessage(null, "You didn't select any grade!\n Select the grade you want to delete\n and then try again!");
        }
    }

    @FXML
    public void handleAddButtonFromGradesTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleTeacherIdFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleEmailFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleGroupFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleIdFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleUpdateButtonFromStudentsTab(ActionEvent actionEvent) {
        if (tableFromStudentsTab.getSelectionModel().getSelectedItem() != null) {
            this.studentService.update(tableFromStudentsTab.getSelectionModel().getSelectedItem().getId(),
                    nameTextFieldFromStudentsTab.getText(),
                    firstNameTextFieldFromStudentsTab.getText(),
                    groupChoiceBoxFromStudentsTab.getValue(),
                    emailTextFieldFromStudentsTab.getText(),
                    teacherIdTextFieldFromStudentsTab.getText());
        } else {
            MessageAlert.showErrorMessage(null, "You didn't select any student! \n Select the student you want to update\n and try again!");
        }
    }

    @FXML
    public void handleDeleteButtonFromStudentsTab(ActionEvent actionEvent) {
        if (tableFromStudentsTab.getSelectionModel().getSelectedItem() != null) {
            Student student = tableFromStudentsTab.getSelectionModel().getSelectedItem();
            this.studentService.delete(student.getId());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "The student has been deleted!", "The student with the id ".concat(student.getId()).concat("  has been deleted!"));
            updateStudentsModel();
        } else {
            MessageAlert.showErrorMessage(null, "You didn't selected any student! \nSelect the student you want to delete \nand try again!");
        }
    }

    @FXML
    public void handleAddButtonFromStudentsTab(ActionEvent actionEvent) {
        try {
            this.studentService.save(
                    nameTextFieldFromStudentsTab.getText(),
                    firstNameTextFieldFromStudentsTab.getText(),
                    groupChoiceBoxFromStudentsTab.getValue(),
                    emailTextFieldFromStudentsTab.getText(),
                    teacherIdTextFieldFromStudentsTab.getText()
            );
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

        updateStudentsModel();
    }

    @FXML
    public void handleDeadlineWeekFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        handleIdFilterTextFieldFromHomeworksTab(keyEvent);
    }

    @FXML
    public void handleStartWeekFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        handleIdFilterTextFieldFromHomeworksTab(keyEvent);
    }

    @FXML
    public void handleDescriptionFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        handleIdFilterTextFieldFromHomeworksTab(keyEvent);
    }

    @FXML
    public void handleIdFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        keyEvent.consume();
        List<Homework> initList = new ArrayList<Homework>();
        List<Homework> homeworkList = null;
        for (Homework homework :
                this.homeworkService.findAll()
        ) {
            initList.add(homework);
        }
        if (startWeekFilterTextFieldFromHomeworksTab.getText().equals("")) {
            if (deadlineWeekFilterTextFieldFromHomeworksTab.getText().equals("")) {
                homeworkList = this.homeworkService.findAllById(
                        idFilterTextFieldFromHomeworksTab.getText(),
                        this.homeworkService.findAllByDescription(
                                descriptionFilterTextFieldFromHomeworksTab.getText(),
                                initList
                        )
                );
                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);
            } else {
                int deadlineWeek = Integer.parseInt(deadlineWeekFilterTextFieldFromHomeworksTab.getText());
                homeworkList = this.homeworkService.findAllByDeadlineWeek(
                        deadlineWeek,
                        this.homeworkService.findAllById(
                                idFilterTextFieldFromHomeworksTab.getText(),
                                this.homeworkService.findAllByDescription(
                                        descriptionFilterTextFieldFromHomeworksTab.getText(),
                                        initList
                                )
                        )
                );

                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);

            }
        }

        if (deadlineWeekFilterTextFieldFromHomeworksTab.equals("")) {
            if (startWeekFilterTextFieldFromHomeworksTab.getText().equals("")) {
                homeworkList = this.homeworkService.findAllById(
                        idFilterTextFieldFromHomeworksTab.getText(),
                        this.homeworkService.findAllByDescription(
                                descriptionFilterTextFieldFromHomeworksTab.getText(),
                                initList
                        )
                );
                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);
            } else {
                int startWeek = Integer.parseInt(startWeekFilterTextFieldFromHomeworksTab.getText());
                homeworkList = this.homeworkService.findAllByStartWeek(
                        startWeek,
                        this.homeworkService.findAllById(
                                idFilterTextFieldFromHomeworksTab.getText(),
                                this.homeworkService.findAllByDescription(
                                        descriptionFilterTextFieldFromHomeworksTab.getText(),
                                        initList
                                ))
                );
                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);
            }
        }
        if ((!startWeekFilterTextFieldFromHomeworksTab.getText().equals(""))
                && (!deadlineWeekFilterTextFieldFromHomeworksTab.equals(""))) {
            int deadlineWeek = Integer.parseInt(startWeekFilterTextFieldFromHomeworksTab.getText());
            int startWeek = Integer.parseInt(startWeekFilterTextFieldFromHomeworksTab.getText());
            homeworkList = this.homeworkService.findAllByDeadlineWeek(
                    deadlineWeek,
                    this.homeworkService.findAllByStartWeek(
                            startWeek,
                            this.homeworkService.findAllById(
                                    idFilterTextFieldFromHomeworksTab.getText(),
                                    this.homeworkService.findAllByDescription(
                                            descriptionFilterTextFieldFromHomeworksTab.getText(),
                                            initList
                                    ))
                    )
            );

        }
        homeworksModel.setAll(homeworkList);
        tableFromHomeworksTab.setItems(homeworksModel);


    }

    @FXML
    public void handleAddButtonFromHomeworksTab(ActionEvent actionEvent) {
        try {
            this.homeworkService.save(descriptionTextFieldFromHomeworksTab.getText(),
                    String.valueOf(deadlineWeekChoiceBoxFromHomeworksTab.getValue())
            );
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Homework added!", "The homework has been added!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        updateHomeworksModel();
    }

    @FXML
    public void handleDeleteButtonFromHomeworksTab(ActionEvent actionEvent) {
        if (tableFromHomeworksTab.getSelectionModel().getSelectedItem() != null) {
            this.homeworkService.delete(tableFromHomeworksTab.getSelectionModel().getSelectedItem().getId());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Homework deleted!", "You deleted the homework with id ".concat(tableFromHomeworksTab.getSelectionModel().getSelectedItem().getId()));
        } else {
            MessageAlert.showErrorMessage(null, "You didn't selected any homework!\n Select a homework and \n try again!");
        }
        updateHomeworksModel();
    }

    @FXML
    public void handleUpdateButtonFromHomeworksTab(ActionEvent actionEvent) {
        if (tableFromHomeworksTab.getSelectionModel().getSelectedItem() != null) {
            try {
                this.homeworkService.update(
                        tableFromHomeworksTab.getSelectionModel().getSelectedItem().getId(),
                        descriptionTextFieldFromHomeworksTab.getText(),
                        String.valueOf(deadlineWeekChoiceBoxFromHomeworksTab.getValue())
                );
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Homework updated!", "The homework has been updated!");
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        } else {
            MessageAlert.showErrorMessage(null, "You didn't select any homework!\n Select a homework you want to update\n and try again!");
        }
        updateHomeworksModel();
    }


    @FXML
    public void clearFilterFieldsFromStudentsTab() {
        idFilterTextFieldFromStudentsTab.setText("");
        nameFilterTextFieldFromStudentsTab.setText("");
        firstNameFilterTextFieldFromStudentsTab.setText("");
        groupFilterTextFieldFromStudentsTab.setText("");
        emailFilterTextFieldFromStudentsTab.setText("");
        teacherIdTextFieldFromStudentsTab.setText("");
        this.updateStudentsModel();
    }

    @FXML
    public void clearFilterFieldsFromHomeworksTab() {
        idFilterTextFieldFromHomeworksTab.setText("");
        descriptionFilterTextFieldFromHomeworksTab.setText("");
        startWeekFilterTextFieldFromHomeworksTab.setText("");
        deadlineWeekFilterTextFieldFromHomeworksTab.setText("");
        this.updateHomeworksModel();
    }

    @FXML
    public void clearFilterFieldsFromGradesTab() {
        studentIdFilterTextFieldFromGradesTab.setText("");
        homeworkIdFilterTextFieldFromGradesTab.setText("");
        valueFilterTextFieldFromGradesTab.setText("");
        teacherIdFilterTextFieldFromGradesTab.setText("");
        this.updateGradesModel();
    }


    @FXML
    public void clearFieldsFromStudentsTab() {
        nameTextFieldFromStudentsTab.setText("");
        firstNameTextFieldFromStudentsTab.setText("");
        emailTextFieldFromStudentsTab.setText("");
        groupChoiceBoxFromStudentsTab.getSelectionModel().clearSelection();
        teacherIdTextFieldFromStudentsTab.setText("");
    }

    @FXML
    public void clearFieldsFromHomeworksTab() {
        descriptionTextFieldFromHomeworksTab.setText("");
        deadlineWeekChoiceBoxFromHomeworksTab.getSelectionModel().clearSelection();
        deadlineWeekChoiceBoxFromHomeworksTab.setValue(this.univYearStructure.getCurrentWeek());
    }

    @FXML
    public void clearFieldsFromGradesTab() {
        studentIdTextFieldFromGradesTab.setText("");
        homeworkIdTextFieldFromGradesTab.setText("");
        valueChoiceBoxFromGradesTab.getSelectionModel().clearSelection();
        valueChoiceBoxFromGradesTab.setValue(10);
        motivationCheckBoxFromGradesTab.setSelected(false);
        latePublicationOfGradesCheckBoxFromGradesTab.setSelected(false);
        teacherIdTextFieldFromGradesTab.setText("");
    }


    @FXML
    public void updateStudentsModel() {
        List<Student> studentsList = new ArrayList<>();
        studentService.findAll().forEach(studentsList::add);
        studentsModel.setAll(studentsList);
        tableFromStudentsTab.setItems(studentsModel);
    }


    @FXML
    public void updateTeachersModel() {
        List<Teacher> teachersList = new ArrayList<>();
        for (Teacher teacher :
                teacherService.findAll()) {
            teachersList.add(teacher);
        }

        teachersModel.setAll(teachersList);
        //tableFromTeachersTab.setItems(teachersModel);
    }

    @FXML
    public void updateGradesModel() {
        List<Grade> gradeList = new ArrayList<>();
        gradeService.findAll().forEach(gradeList::add);
        gradesModel.setAll(gradeList);
        tableFromGradesTab.setItems(gradesModel);
    }

    @FXML
    public void updateHomeworksModel() {
        List<Homework> homeworksList = new ArrayList<>();
        homeworkService.findAll().forEach(homeworksList::add);
        homeworksModel.setAll(homeworksList);
        tableFromHomeworksTab.setItems(homeworksModel);
    }

    @FXML
    void initialize() {

        tableFromStudentsTabColumnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        tableFromStudentsTabColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        tableFromStudentsTabColumnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        tableFromStudentsTabColumnGroup.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        tableFromStudentsTabColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableFromStudentsTabColumnTeacherTrainingLab.setCellValueFactory(new PropertyValueFactory<Student, String>("teacherTrainingLab"));

        tableFromHomeworksTabColumnId.setCellValueFactory(new PropertyValueFactory<Homework, String>("id"));
        tableFromHomeworksTabColumnStartWeek.setCellValueFactory(new PropertyValueFactory<Homework, String>("startWeek"));
        tableFromHomeworksTabColumnDeadlineWeek.setCellValueFactory(new PropertyValueFactory<Homework, String>("deadlineWeek"));
        tableFromHomeworksTabColumnDescription.setCellValueFactory(new PropertyValueFactory<Homework, String>("description"));

        tableFromGradesTabColumnHomeworkId.setCellValueFactory(new PropertyValueFactory<Grade, String>("homeworkId"));
        tableFromGradesTabColumnStudentId.setCellValueFactory(new PropertyValueFactory<Grade, String>("studentId"));
        tableFromGradesTabColumnTeacherId.setCellValueFactory(new PropertyValueFactory<Grade, String>("teacherId"));
        tableFromGradesTabColumnValue.setCellValueFactory(new PropertyValueFactory<Grade, String>("value"));

        tableFromStudentsTab.setItems(this.studentsModel);
        tableFromGradesTab.setItems(this.gradesModel);
        tableFromHomeworksTab.setItems(this.homeworksModel);


        /*
         *       the services didn't set yet so it will crash if I update models here
         *
         * */
        //updateModels();


        groupChoiceBoxModelFromStudentsTab.addAll("213", "223", "233", "214", "224", "234", "215", "225", "235");
        groupChoiceBoxFromStudentsTab.setItems(groupChoiceBoxModelFromStudentsTab);
        valueChoiceBoxModelFromGradesTab.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        valueChoiceBoxFromGradesTab.setItems(valueChoiceBoxModelFromGradesTab);

        latePublicationOfGradesChoiceBoxModelFromGradesTab.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        latePublicationOfGradesChoiceBoxFromGradesTab.setItems(latePublicationOfGradesChoiceBoxModelFromGradesTab);
        latePublicationOfGradesChoiceBoxFromGradesTab.setDisable(true);
        valueChoiceBoxFromGradesTab.setValue(10);
        startWeekChoiceBoxModelFromHomeworksTab.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        deadlineWeekChoiceBoxModelFromHomeworksTab.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        deadlineWeekChoiceBoxFromHomeworksTab.setItems(deadlineWeekChoiceBoxModelFromHomeworksTab);

    }

    public void updateModels() {
        updateGradesModel();
        updateHomeworksModel();
        updateStudentsModel();
        updateTeachersModel();
    }


    public void setServices(StudentService studentService, TeacherService teacherService,
                            HomeworkService homeworkService, GradeService gradeService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        this.homeworkService = homeworkService;
    }


    @FXML
    public void handleTableViewSelectionFromStudentsTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Student student = tableFromStudentsTab.getSelectionModel().getSelectedItem();
        if (student != null) {
            clearFieldsFromStudentsTab();
            nameTextFieldFromStudentsTab.setText(student.getName());
            firstNameTextFieldFromStudentsTab.setText(student.getFirstName());
            emailTextFieldFromStudentsTab.setText(student.getEmail());
            teacherIdTextFieldFromStudentsTab.setText(student.getTeacherTrainingLab());
            groupChoiceBoxFromStudentsTab.setValue(student.getGroup());
        }

    }

    @FXML
    public void handleTableViewSelectionFromGradesTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Grade grade = tableFromGradesTab.getSelectionModel().getSelectedItem();
        if (grade != null) {
            clearFieldsFromGradesTab();
            Student student = this.studentService.findOne(grade.getStudentId());
            studentIdTextFieldFromGradesTab.setText(student.getName() + " " + student.getFirstName());
            Homework homework = this.homeworkService.findOne(grade.getHomeworkId());
            homeworkIdTextFieldFromGradesTab.setText(homework.getDescription());
            Teacher teacher = this.teacherService.findOne(grade.getTeacherId());
            teacherIdTextFieldFromGradesTab.setText(teacher.getName() + " " + teacher.getFirstName());
        }
    }

    @FXML
    public void handleTableViewSelectionFromHomeworksTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Homework homework = this.tableFromHomeworksTab.getSelectionModel().getSelectedItem();
        if (homework != null) {
            clearFieldsFromHomeworksTab();
            descriptionTextFieldFromHomeworksTab.setText(homework.getDescription());
            deadlineWeekChoiceBoxFromHomeworksTab.setValue(homework.getDeadlineWeek());
        }
    }

    @FXML
    public void handleLatePublicationOfGradesCheckBoxFromGradesTab(ActionEvent actionEvent) {
        actionEvent.consume();
        latePublicationOfGradesChoiceBoxFromGradesTab.setDisable(false);
        //when is selected to do enable the choice box and when is deselected to disable it

    }

    public void setUnivYear(UnivYearStructure univYearStructure) {
        this.univYearStructure = univYearStructure;
        deadlineWeekChoiceBoxFromHomeworksTab.setValue(this.univYearStructure.getCurrentWeek());
    }


    @FXML
    public void handleHomeworkIdTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        Homework homework = this.homeworkService.findOne(homeworkIdTextFieldFromGradesTab.getText());
        if (homework != null) {
            System.out.println(this.homeworkService.maxGrade(homework.getId(),
                    this.univYearStructure.getCurrentWeekFromLocalDateTime(LocalDateTime.now())));
            valueChoiceBoxFromGradesTab.setValue(
                    this.homeworkService.maxGrade(homework.getId(),
                            this.univYearStructure.getCurrentWeekFromLocalDateTime(LocalDateTime.now()))
            );
        }
    }
}