import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class FXMenu extends Application {

    Schedule schedule = new Schedule();

    String fileName = "Pschedule.txt";

    TableView<Date> dateTableView;

    Stage window;
    int foodRow = 12;
    int latestFoodY = 12;
    int foodID = 0;
    int taskRow = 13;
    int latestTaskY = 13;
    int taskID = 0;
    int endRow = 14;
    boolean editingActive = false;
    String currentDate;


    public static void main(String[]args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Window itself.
        window = stage;
        window.setTitle("Scheduler");

        // Left and right layouts for the root.
        VBox leftPane = new VBox();
        VBox rightPane = new VBox();

        // UI Buttons.
        Button buttonNew = new Button("New");
        Button buttonSettings = new Button("Settings");
        Button buttonExit = new Button("Exit");

        // Buttons for NEW command.
        Button buttonNewComplete = new Button("Complete");
        Button buttonNewCancel = new Button("Cancel");
        Button buttonAddFood = new Button("+");
        Button buttonRemoveFood = new Button("-");
        Button buttonAddTasks = new Button("+");
        Button buttonRemoveTasks = new Button("-");

        // Labels and TextFields for NEW command.
        Label labelDate = new Label("Date:");
        TextField inputDate = new TextField();
        Label dateText = new Label();
        Label labelWeight = new Label("Weight:");
        TextField inputWeight = new TextField();
        Label weightText = new Label();
        Label labelFood = new Label("Food:");
        ArrayList<TextField> foodFields = new ArrayList<>();
        foodFields.add(new TextField());
        Label foodText = new Label();
        Label labelTasks = new Label("Tasks:");
        ArrayList<TextField> taskFields = new ArrayList<>();
        taskFields.add(new TextField());
        Label tasksText = new Label();
        // Label for date of edit date
        Label editDate = new Label();

        // Buttons and Label for dateListButtons
        Button buttonDateDelete = new Button("Delete");
        Button buttonDateEdit = new Button("Edit");
        Label viewedDate = new Label("Current date: none");

        // Top bar layout used for New and Settings buttons.
        GridPane topBarLeft = new GridPane();
        // Layout used by the NEW command // Maybe can be used for the read date feature.
        GridPane newSchedule = new GridPane();
        // Layout used for the Exit button
        GridPane topBarRight = new GridPane();
        // Layout used for date list
        GridPane dateList = new GridPane();
        // Layout used for the label and buttons below list date
        BorderPane dateListInteraction = new BorderPane();
        // Layout used for the buttons in dateListInteraction
        HBox dateListButtons = new HBox();
        // Layout used for displaying dates
        GridPane dateDisplay = new GridPane();

        // Padding and gaps between grids
        setPaddingAndGaps(topBarLeft);
        setPaddingAndGaps(topBarRight);
        topBarRight.setAlignment(Pos.TOP_RIGHT);

        setPaddingAndGaps(newSchedule);

        // Sets up the list on the right
        setPaddingAndGaps(dateList);
        setPaddingAndGaps(dateDisplay);
        dateListButtons.setPadding(new Insets(0, 10, 10, 10));
        dateListButtons.setSpacing(10);
        dateListButtons.getChildren().addAll(buttonDateEdit, buttonDateDelete);
        viewedDate.setPadding(new Insets(3, 10, 10, 10));
        dateListInteraction.setLeft(viewedDate);
        dateListInteraction.setRight(dateListButtons);

        // x y positions of top bar buttons.
        GridPane.setConstraints(buttonNew, 0, 0);
        GridPane.setConstraints(buttonSettings,2, 0);
        GridPane.setConstraints(buttonExit, 0, 0);

        // x y positions of New, Display date and Edit command elements.
        GridPane.setConstraints(labelDate, 1, 10);
        GridPane.setConstraints(inputDate, 2, 10);
        GridPane.setConstraints(editDate, 2, 10);
        GridPane.setConstraints(labelWeight, 1, 11);
        GridPane.setConstraints(inputWeight, 2, 11);
        GridPane.setConstraints(labelFood, 1, foodRow);
        GridPane.setConstraints(foodFields.get(0), 2, foodRow);
        GridPane.setConstraints(buttonAddFood, 3, foodRow);
        GridPane.setConstraints(buttonRemoveFood, 4, foodRow);
        GridPane.setConstraints(labelTasks, 1, taskRow);
        GridPane.setConstraints(taskFields.get(0), 2, taskRow);
        GridPane.setConstraints(buttonAddTasks, 3, taskRow);
        GridPane.setConstraints(buttonRemoveTasks, 4, taskRow);
        GridPane.setConstraints(buttonNewComplete, 1, endRow);
        GridPane.setConstraints(buttonNewCancel, 2, endRow);

        // x y positions of displayDate

        GridPane.setConstraints(dateText, 2, 10);
        GridPane.setConstraints(weightText, 2, 11);
        GridPane.setConstraints(foodText, 2, foodRow);
        GridPane.setConstraints(tasksText, 2, taskRow);

        // Does list date things

        TableColumn<Date, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(75);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        TableColumn<Date, String> weightColumn = new TableColumn<>("Weight");
        weightColumn.setMinWidth(50);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("Weight"));

        TableColumn<Date, String> foodColumn = new TableColumn<>("Food");
        foodColumn.setMinWidth(25);
        foodColumn.setStyle("-fx-alignment: CENTER");
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("Food"));

        TableColumn<Date, String> tasksColumn = new TableColumn<>("Tasks");
        tasksColumn.setMinWidth(25);
        tasksColumn.setStyle("-fx-alignment: CENTER");
        tasksColumn.setCellValueFactory(new PropertyValueFactory<>("Tasks"));

        dateTableView = new TableView<>();
        dateTableView.setItems(getDate());
        dateTableView.setPrefWidth(244);
        dateTableView.getColumns().setAll(dateColumn, weightColumn, foodColumn, tasksColumn);

        // Adds buttons to top bar
        topBarLeft.getChildren().addAll(buttonNew, buttonSettings);
        // Adds exit button to top bar
        topBarRight.getChildren().addAll(buttonExit);
        // Adds dateTableView to date list
        dateList.getChildren().addAll(dateTableView);

        // Makes a new date when the NEW button is pressed
        commandNew(buttonNew, buttonExit, buttonNewComplete, buttonNewCancel,
                buttonAddFood, buttonRemoveFood, buttonAddTasks, buttonRemoveTasks,
                labelDate, inputDate, labelWeight, inputWeight, labelFood, foodFields,
                labelTasks, taskFields, newSchedule, dateTableView, dateDisplay);


        dateTableView.setOnMouseClicked( e -> {
            int dateIndex = dateTableView.getSelectionModel().getSelectedIndex() + 1;
            ArrayList<String> dateArray = schedule.getDateArray(fileName);
            String[] list = dateArray.get(dateIndex).split("Date: ");
            list = list[1].split("Weight:");
            currentDate = list[0].trim();

            viewedDate.setText("Current date: " + currentDate);
            buttonDateDelete.setOnAction(f -> {
                schedule.deleteDate(fileName, currentDate);
                dateTableView.getItems().clear();
                dateTableView.setItems(getDate());
                dateDisplay.getChildren().clear();
            });

            buttonDateEdit.setOnAction(g -> {
                // Values for dateInfo.get();
                // date   -> 0
                // weight -> 1
                // food   -> 2
                // tasks  -> 3
                ArrayList<String> dateInfo = schedule.extractDateValues(fileName, currentDate);

                editingActive = true;

                editDate.setText(currentDate);
                inputWeight.setText(dateInfo.get(1).trim());
                String[] foodList = dateInfo.get(2).split("\\[ \\] |\\[x\\] ");
                String[] tasksList = dateInfo.get(3).split("\\[ \\] |\\[x\\] ");
                if (!foodList[0].trim().equals("No food.")) {
                    for (int i = 1; i < foodList.length; i++) {
                        foodFields.get(i - 1).setText(foodList[i]);
                        buttonAddFood.fire();
                    }

                    buttonRemoveFood.fire();
                }
                if (!tasksList[0].trim().equals("No tasks.")) {
                    for (int i = 1; i < tasksList.length; i++) {
                        taskFields.get(i - 1).setText(tasksList[i]);
                        buttonAddTasks.fire();
                    }
                    buttonRemoveTasks.fire();
                }
                newSchedule.getChildren().addAll(labelDate, labelWeight,
                        labelFood, labelTasks, editDate, inputWeight, foodFields.get(0), taskFields.get(0),
                        buttonNewComplete, buttonNewCancel, buttonAddFood, buttonRemoveFood, buttonAddTasks,
                        buttonRemoveTasks);

                dateDisplay.getChildren().clear();
            });

            displayDate(dateDisplay, currentDate, labelDate, dateText, labelWeight,
                    weightText, labelFood, foodText, labelTasks, tasksText, buttonNewComplete,
                    buttonNewCancel, buttonAddFood, buttonRemoveFood, buttonAddTasks, buttonRemoveTasks,
                    inputDate, inputWeight, foodFields, taskFields, newSchedule);
            });

        // Adds secondary layouts to leftPane.
        leftPane.getChildren().addAll(topBarLeft, newSchedule, dateDisplay);
        rightPane.getChildren().addAll(topBarRight, dateList, dateListInteraction);

        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setRight(rightPane);

        // Sets layout, window size, scene and then displays the window.
        Scene scene = new Scene(root, 750, 500);
        window.setScene(scene);
        window.show();

    }

    // Gets all of the dates
    public ObservableList<Date> getDate(){
        ObservableList<Date> dates = FXCollections.observableArrayList();
        Button buttonDateDelete = new Button("Delete");
        Button buttonDateEdit = new Button("Edit");
        ArrayList<String> specificDateArray = schedule.getSpecificDateArray(fileName);
        for (int i = 0;  i < specificDateArray.size(); i++) {
            ArrayList<String> dateInfo = schedule.extractDateValues(fileName, specificDateArray.get(i));


            dates.add(new Date(dateInfo.get(0), dateInfo.get(1), makeDoneCount(dateInfo.get(2)), makeDoneCount(dateInfo.get(3)), buttonDateDelete, buttonDateEdit));
        }
        dates.add(new Date());
        return dates;
    }

    public String makeDoneCount(String input) {
        ArrayList<Boolean> stateOfCheck = schedule.getCheckValues(input);
        int finished = 0;
        int total = stateOfCheck.size();

        for (int i = 0; i < stateOfCheck.size(); i++) {
            if (stateOfCheck.get(i)) {
                finished = finished + 1;
            }
        }
        return finished + " | " + total;
    }

    public void displayDate(GridPane dateDisplay, String date,Label labelDate, Label dateText, Label labelWeight, Label weightText,
                            Label labelFood, Label foodText, Label labelTasks, Label tasksText, Button buttonNewComplete,
                            Button buttonNewCancel, Button buttonAddFood, Button buttonRemoveFood, Button buttonAddTasks,
                            Button buttonRemoveTasks, TextField inputDate, TextField inputWeight, ArrayList<TextField> foodFields,
                            ArrayList<TextField> taskFields, GridPane newSchedule) {

        resetNewCommand(buttonNewComplete, buttonNewCancel, buttonAddFood,
                buttonRemoveFood, buttonAddTasks, buttonRemoveTasks, inputDate,
                inputWeight, labelFood, foodFields, labelTasks, taskFields, newSchedule);

        dateDisplay.getChildren().clear();
        ArrayList<String> dateInfo = schedule.extractDateValues(fileName, date);
        dateText.setText(dateInfo.get(0));
        weightText.setText(dateInfo.get(1));
        foodText.setText(dateInfo.get(2));
        tasksText.setText(dateInfo.get(3).trim());
        dateDisplay.getChildren().addAll(labelDate, dateText, labelWeight, weightText, labelFood, foodText, labelTasks, tasksText);

    }



    public void setPaddingAndGaps(GridPane gridPane) {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(5);
    }




    private void commandNew(Button buttonNew, Button buttonExit, Button buttonNewComplete, Button buttonNewCancel,
                            Button buttonAddFood, Button buttonRemoveFood, Button buttonAddTasks, Button buttonRemoveTasks,
                            Label labelDate, TextField inputDate, Label labelWeight, TextField inputWeight, Label labelFood,
                            ArrayList<TextField> foodFields, Label labelTasks, ArrayList<TextField> taskFields, GridPane newSchedule,
                            TableView<Date> dateTableView, GridPane dateDisplay) {
        
        // Adds NEW command elements when the button is pressed.
        buttonNew.setOnAction(e -> {

            newSchedule.getChildren().addAll(labelDate, labelWeight,
                labelFood, labelTasks, inputDate, inputWeight, foodFields.get(0), taskFields.get(0),
                buttonNewComplete, buttonNewCancel, buttonAddFood, buttonRemoveFood, buttonAddTasks,
                buttonRemoveTasks);

            dateDisplay.getChildren().clear();
        });

        // When the button is pressed the input is checked, when it's true the NEW command ends and resets everything to default values.
        buttonNewComplete.setOnAction(e -> {

            boolean dateCheck = true;
            boolean weightCheck = true;

            String date = inputDate.getText();
            double weight = 0.0;

            if (!schedule.formatDate(inputDate.getText()).equals("INVALID")) {
                date = schedule.formatDate(inputDate.getText());
                inputDate.setStyle("");
            } else {
                inputDate.setStyle("-fx-text-box-border: #b22222; -fx-focus-color: #B22222;");
                dateCheck = false;
                if (editingActive) {
                    date = currentDate;
                    dateCheck = true;
                }
            }



            try {
                weight = Double.parseDouble(inputWeight.getText());
                if (Double.parseDouble(inputWeight.getText()) > 635) {
                    throw new NumberFormatException("");
                }
                inputWeight.setStyle("");
            } catch (NumberFormatException f) {
                inputWeight.setStyle("-fx-text-box-border: #b22222; -fx-focus-color: #B22222;");
                weightCheck = false;
            }
            if (dateCheck && weightCheck) {
                if (editingActive) {
                    schedule.deleteDate(fileName, date);
                }
                schedule.makeNewDayScheduleFX(fileName, date, weight, fieldsToString(foodFields), fieldsToString(taskFields));
                resetNewCommand(buttonNewComplete, buttonNewCancel, buttonAddFood,
                        buttonRemoveFood, buttonAddTasks, buttonRemoveTasks, inputDate,
                        inputWeight, labelFood, foodFields, labelTasks, taskFields, newSchedule);
            }

            editingActive = false;
            dateTableView.getItems().clear();
            dateTableView.setItems(getDate());
        });

        // Ends the NEW command and resets everything to default values.
        buttonNewCancel.setOnAction(e -> {
            resetNewCommand(buttonNewComplete, buttonNewCancel, buttonAddFood,
                    buttonRemoveFood, buttonAddTasks, buttonRemoveTasks, inputDate,
                    inputWeight, labelFood, foodFields, labelTasks, taskFields, newSchedule);
        });

        // Adds a new text field when the "+" button is clicked.
        buttonAddFood.setOnAction(e -> {
            taskRow = taskRow + 1;
            endRow = endRow + 1;
            latestFoodY = latestFoodY + 1;
            foodID = foodID + 1;

            foodFields.add(new TextField());
            GridPane.setConstraints(foodFields.get(foodID), 2, latestFoodY);
            newSchedule.getChildren().add(foodFields.get(foodID));

            GridPane.setConstraints(labelTasks, 1, taskRow);
            for (int i = 0; i < taskFields.size(); i++) {
                GridPane.setConstraints(taskFields.get(i), 2, taskRow + i);
            }
            latestTaskY = taskRow + taskFields.size() - 1;
            GridPane.setConstraints(buttonAddTasks, 3, taskRow);
            GridPane.setConstraints(buttonRemoveTasks, 4, taskRow);
            GridPane.setConstraints(buttonNewComplete, 1, latestTaskY + 1);
            GridPane.setConstraints(buttonNewCancel, 2, latestTaskY + 1);
        });

        // Removes a text field when the "-" button is clicked.
        buttonRemoveFood.setOnAction(e -> {
            if (foodFields.size() > 1) {
                taskRow = taskRow - 1;
                endRow = endRow - 1;
                latestFoodY = latestFoodY - 1;

                newSchedule.getChildren().remove(foodFields.get(foodID));
                foodFields.remove(foodID);

                foodID = foodID - 1;

                GridPane.setConstraints(labelTasks, 1, taskRow);
                GridPane.setConstraints(labelTasks, 1, taskRow);
                for (int i = 0; i < taskFields.size(); i++) {
                    GridPane.setConstraints(taskFields.get(i), 2, taskRow + i);
                }
                latestTaskY = taskRow + taskFields.size() - 1;
                GridPane.setConstraints(buttonAddTasks, 3, taskRow);
                GridPane.setConstraints(buttonRemoveTasks, 4, taskRow);
                GridPane.setConstraints(buttonNewComplete, 1, latestTaskY + 1);
                GridPane.setConstraints(buttonNewCancel, 2, latestTaskY + 1);
            }
        });

        // Adds a new text field when the "+" button is clicked.
        buttonAddTasks.setOnAction(e -> {
            endRow = endRow + 1;
            latestTaskY = latestTaskY + 1;
            taskID = taskID + 1;

            taskFields.add(new TextField());
            GridPane.setConstraints(taskFields.get(taskID), 2, latestTaskY);
            newSchedule.getChildren().add(taskFields.get(taskID));
            GridPane.setConstraints(labelTasks, 1, taskRow);
            GridPane.setConstraints(taskFields.get(0), 2, taskRow);
            GridPane.setConstraints(buttonAddTasks, 3, taskRow);
            GridPane.setConstraints(buttonRemoveTasks, 4, taskRow);
            GridPane.setConstraints(buttonNewComplete, 1, endRow);
            GridPane.setConstraints(buttonNewCancel, 2, endRow);
        });

        // Removes a text field when the "-" button is clicked.
        buttonRemoveTasks.setOnAction(e -> {
            if (taskFields.size() > 1) {
                endRow = endRow - 1;
                latestTaskY = latestTaskY - 1;

                newSchedule.getChildren().remove(taskFields.get(taskID));
                taskFields.remove(taskID);

                taskID = taskID - 1;

                GridPane.setConstraints(labelTasks, 1, taskRow);
                GridPane.setConstraints(taskFields.get(0), 2, taskRow);
                GridPane.setConstraints(buttonAddTasks, 3, taskRow);
                GridPane.setConstraints(buttonRemoveTasks, 4, taskRow);
                GridPane.setConstraints(buttonNewComplete, 1, endRow);
                GridPane.setConstraints(buttonNewCancel, 2, endRow);
            }
        });

        // Closes the window.
        buttonExit.setOnAction(e -> window.close());
    }


    private void resetNewCommand(Button buttonNewComplete, Button buttonNewCancel, Button buttonAddFood,
                                 Button buttonRemoveFood, Button buttonAddTasks, Button buttonRemoveTasks, TextField inputDate,
                                 TextField inputWeight, Label labelFood, ArrayList<TextField> foodFields, Label labelTasks,
                                 ArrayList<TextField> taskFields, GridPane newSchedule) {
        inputDate.setStyle("");
        inputWeight.setStyle("");
        newSchedule.getChildren().clear();
        inputDate.clear();
        inputWeight.clear();
        foodFields.clear();
        taskFields.clear();
        foodFields.add(new TextField());
        taskFields.add(new TextField());
        foodRow = 12;
        latestFoodY = 12;
        foodID = 0;
        taskRow = 13;
        latestTaskY = 13;
        taskID = 0;
        endRow = 14;
        GridPane.setConstraints(labelFood, 1, foodRow);
        GridPane.setConstraints(foodFields.get(0), 2, foodRow);
        GridPane.setConstraints(buttonAddFood, 3, foodRow);
        GridPane.setConstraints(buttonRemoveFood, 4, foodRow);
        GridPane.setConstraints(labelTasks, 1, taskRow);
        GridPane.setConstraints(taskFields.get(0), 2, taskRow);
        GridPane.setConstraints(buttonAddTasks, 3, taskRow);
        GridPane.setConstraints(buttonRemoveTasks, 4, taskRow);
        GridPane.setConstraints(buttonNewComplete, 1, endRow);
        GridPane.setConstraints(buttonNewCancel, 2, endRow);
    }

    // Takes text fields from either food or tasks and converts them into
    // a format that schedule can understand and process.
    public String fieldsToString(ArrayList<TextField> textFields) {
        String fieldsToString = "";
        if (!textFields.get(0).getText().equals("")) {
            for (int i = 0; i < textFields.size(); i++) {
                if (!textFields.get(i).getText().equals("")) {
                    if (!(i == textFields.size() - 1)) {
                        fieldsToString = fieldsToString + textFields.get(i).getText() + ", ";
                    } else {
                        fieldsToString = fieldsToString + textFields.get(i).getText();
                    }
                }
            }
        }
        return fieldsToString;
    }



}
