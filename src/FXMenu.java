import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class FXMenu extends Application {

    Schedule schedule = new Schedule();

    Stage window;
    int foodRow = 12;
    int latestFoodY = 12;
    int foodID = 0;
    int taskRow = 13;
    int latestTaskY = 13;
    int taskID = 0;
    int endRow = 14;


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
        Label labelWeight = new Label("Weight:");
        TextField inputWeight = new TextField();
        Label labelFood = new Label("Food:");
        ArrayList<TextField> foodFields = new ArrayList<>();
        foodFields.add(new TextField());
        Label labelTasks = new Label("Tasks:");
        ArrayList<TextField> taskFields = new ArrayList<>();
        taskFields.add(new TextField());

        // ListView for the list of dates
        ListView<String> dateListView = new ListView<>();

        // Top bar layout used for New and Settings buttons.
        GridPane topBarLeft = new GridPane();
        // Layout used by the NEW command // Maybe can be used for the read date feature.
        GridPane newSchedule = new GridPane();
        // Layout used for the Exit button
        GridPane topBarRight = new GridPane();
        // Layout used for list date
        GridPane dateList = new GridPane();
        // Layout used for displaying dates
        GridPane dateDisplay = new GridPane();

        // Padding and gaps between grids
        setPaddingAndGaps(topBarLeft);
        setPaddingAndGaps(topBarRight);
        topBarRight.setAlignment(Pos.TOP_RIGHT);

        setPaddingAndGaps(newSchedule);

        setPaddingAndGaps(dateList);
        setPaddingAndGaps(dateDisplay);

        // x y positions of top bar buttons.
        GridPane.setConstraints(buttonNew, 0, 0);
        GridPane.setConstraints(buttonSettings,2, 0);
        GridPane.setConstraints(buttonExit, 0, 0);

        // x y positions of NEW command elements.
        GridPane.setConstraints(labelDate, 1, 10);
        GridPane.setConstraints(inputDate, 2, 10);
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

        // To be implemented

        // x y positions of date list
        GridPane.setConstraints(dateListView, 0, 0);

        // Adds buttons to top bar
        topBarLeft.getChildren().addAll(buttonNew, buttonSettings);
        // Adds exit button to top bar
        topBarRight.getChildren().addAll(buttonExit);
        // Adds dateListView to date list
        dateList.getChildren().addAll(dateListView);

        // Makes a new date when the NEW button is pressed
        commandNew(buttonNew, buttonExit, buttonNewComplete, buttonNewCancel,
                buttonAddFood, buttonRemoveFood, buttonAddTasks, buttonRemoveTasks,
                labelDate, inputDate, labelWeight, inputWeight, labelFood, foodFields,
                labelTasks, taskFields, newSchedule, dateListView);

        // Does list date things

        dateListView.getItems().addAll(schedule.makeDateListInfo(schedule.getDateArray("Pschedule.txt")));
        dateListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String[] currentDate = dateListView.getSelectionModel().getSelectedItem().split("Date: ");
                currentDate = currentDate[1].split("Weight:");

                displayDate(dateDisplay, currentDate[0].trim());
            }
        });

        // Adds secondary layouts to leftPane.
        leftPane.getChildren().addAll(topBarLeft, newSchedule, dateDisplay);
        rightPane.getChildren().addAll(topBarRight, dateList);

        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setRight(rightPane);

        // Sets layout, window size, scene and then displays the window.
        Scene scene = new Scene(root, 750, 500);
        window.setScene(scene);
        window.show();

    }


    public void displayDate(GridPane dateDisplay, String date) {

        // To be implemented

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
                            ListView dateListView) {
        // Adds NEW command elements when the button is pressed.
        buttonNew.setOnAction(e -> newSchedule.getChildren().addAll(labelDate, labelWeight,
                labelFood, labelTasks, inputDate, inputWeight, foodFields.get(0), taskFields.get(0),
                buttonNewComplete, buttonNewCancel, buttonAddFood, buttonRemoveFood, buttonAddTasks,
                buttonRemoveTasks));

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
            }

            try {
                weight = Double.parseDouble(inputWeight.getText());
                inputWeight.setStyle("");
            } catch (NumberFormatException f) {
                inputWeight.setStyle("-fx-text-box-border: #b22222; -fx-focus-color: #B22222;");
                weightCheck = false;
            }
            if (dateCheck && weightCheck) {
                schedule.makeNewDayScheduleFX("Pschedule.txt", date, weight, fieldsToString(foodFields), fieldsToString(taskFields));

                resetNewCommand(buttonNewComplete, buttonNewCancel, buttonAddFood,
                        buttonRemoveFood, buttonAddTasks, buttonRemoveTasks, inputDate,
                        inputWeight, labelFood, foodFields, labelTasks, taskFields, newSchedule);
            }
            dateListView.getItems().clear();
            dateListView.getItems().addAll(schedule.makeDateListInfo(schedule.getDateArray("Pschedule.txt")));
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
