
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * The type Schedule.
 */
public class Schedule {


    /**
     * Format date string.
     *
     * @param date the date
     * @return the string
     */
    public String formatDate(String date) {

        String[] listDate = date.split("\\.");
        try {
            if (Integer.parseInt(listDate[0]) > 31 || Integer.parseInt(listDate[0]) <= 0
                    || Integer.parseInt(listDate[1]) > 12 || Integer.parseInt(listDate[1]) <= 0
                    || Integer.parseInt(listDate[2]) > 9999 || Integer.parseInt(listDate[2]) <= 1000) {

                listDate[0] = "INVALID";
            } else {

                if (!listDate.equals("INVALID") && Integer.parseInt(listDate[0]) < 10 && !listDate[0].startsWith("0")) {
                    listDate[0] = "0" + listDate[0];
                }

                if (!listDate.equals("INVALID") && Integer.parseInt(listDate[1]) < 10 && !listDate[1].startsWith("0")) {
                    listDate[1] = "0" + listDate[1];
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            listDate[0] = "INVALID";
        }

        String formatedDate;
        if (!listDate[0].equals("INVALID")) {
            formatedDate = listDate[0] + "." + listDate[1] + "." + listDate[2];
        } else {
            formatedDate = "INVALID";
        }

        return formatedDate;

    }



    /**
     * Write to file.
     *
     * @param fileName the file name
     * @param text     the text
     * @param append   the append
     */
    public void writeToFile(String fileName, String text, boolean append) {

        try {

            FileWriter fileWriter = new FileWriter(fileName, append);
            fileWriter.write(text);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read file string.
     *
     * @param fileName the file name
     * @return the string
     */
    public String readFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            String text = "";
            while (scanner.hasNextLine()) {
                text = text + "\n" + scanner.nextLine();
            }
            return text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "ERROR";
        }

    }

    /**
     * Make check list string.
     *
     * @param text the text
     * @return the string
     */
    public String makeCheckList(String text) {

        String[] list = text.split(" , |, | ,|,");
        String checkList = "";
        if (!(list[0].equals(""))) {
            for (String s : list) {
                checkList = checkList + "[ ] " + s + "\n";
            }
        } else {
            checkList = "none";
        }

        return checkList;

    }

    public ArrayList<Boolean> getCheckValues(String text) {

        ArrayList<Boolean> states = new ArrayList();
        String[] list = text.split("\n");

        for(int i = 0; i < list.length; i++) {
            String check = list[i].substring(0, 3);
            if (check.equals("[ ]")) {
                states.add(false);
            } else if (check.equals("[x]")) {
                states.add(true);
            }
//            System.out.println(states.get(i));
        }
        return states;
    }

    public String makeCheckList(String[] list) {

        list = removeLineBreak(list);
        String checkList = "";
        if (list.length > 0) {
            for (String s : list) {
                if (s.equals("")) { } else {
                    checkList = checkList + "[ ] " + s + "\n";
                }
            }
        } else {
            checkList = "none";
        }

        return checkList;

    }
    public String makeCheckList(String[] list, ArrayList<Boolean> checkStates) {

        list = removeLineBreak(list);
        String checkList = "";
        if (list.length > 0) {
            for(int i = 1; i < list.length; i++) {
                if (checkStates.get(i-1)) {
                    checkList = checkList + "[x] " + list[i] + "\n";
                } else {
                    checkList = checkList + "[ ] " + list[i] + "\n";
                }
            }
        }
        return checkList;
    }

    public String checkToNumber(String text) {
        String[] list = text.split("\\[ \\] |\\[x\\] ");
        String numberList = "";
        for (int i = 1; i < list.length; i++) {
            numberList = numberList + i + " - " + list[i];
        }
        return numberList;
    }

    public  String addNumberToCheck(String text) {
        String[] list = text.split("\n");
        String numberList = "";
        for (int i = 0; i < list.length; i++) {
            numberList = numberList + i + " - " + list[i] + "\n";
        }
        return numberList;
    }

    public String[] removeLineBreak(String[] list) {
        for (int i = 0; i < list.length; i++) {
            list[i] = list[i].replace("\n", "").replace("\r", "");
        }
        return list;
    }

    /**
     * Gets date array.
     *
     * @param fileName the file name
     * @return the date array
     */
    public ArrayList<String> getDateArray(String fileName) {

        ArrayList<String> dateArray = new ArrayList<>();
        String[] text = readFile(fileName).split("(?=Date: )");
        Collections.addAll(dateArray, text);
        return dateArray;

    }

    public ArrayList<String> getSpecificDateArray(String fileName) {

        ArrayList<String> dateArray = getDateArray(fileName);
        ArrayList<String> specificDateArray = new ArrayList<>();
        for (int i = 1; i < dateArray.size(); i++) {
            String[] splitter = dateArray.get(i).split("Date: ");
            splitter = splitter[1].split("Weight: ");
            String date = splitter[0].trim();
            specificDateArray.add(date);
        }
        return specificDateArray;
    }

    /**
     * Date array to string string.
     *
     * @param dateArray the date array
     * @return the string
     */
    public String dateArrayToString(ArrayList<String> dateArray) {

        String text = "";
        for (String s : dateArray) {
            text = text + s;
        }
        return text;

    }

    /**
     * Clear file.
     *
     * @param fileName the file name
     */
    public void clearFile(String fileName) {
        writeToFile(fileName, "", false);
    }

    /**
     * Delete date.
     *
     * @param fileName the file name
     * @param date     the date
     */
    public void deleteDate(String fileName, String date) {

        String text;
        ArrayList<String> dateArray = getDateArray(fileName);
        for (int i = 0; i < dateArray.size(); i++) {
            if (dateArray.get(i).contains("Date: " + date)) {
                dateArray.remove(i);
            }
        }
        text = dateArrayToString(dateArray);
        writeToFile(fileName, text, false);

    }

    /**
     * Gets date.
     *
     * @param fileName the file name
     * @param date     the date
     * @return the date
     */
    public String getDate(String fileName, String date) {

        ArrayList<String> dateArray = getDateArray(fileName);
        for (int i = 0; i < dateArray.size(); i++) {
            if (dateArray.get(i).contains("Date: " + date)) {
                return dateArray.get(i);
            }
        }
        return "NOTDATE";

    }

    public ArrayList<Boolean> invertBoolean(ArrayList<Boolean> checkStates, int index) {
        if (checkStates.get(index)) {
            checkStates.set(index, false);
        } else {
            checkStates.set(index, true);
        }
        return checkStates;
    }

    public void sort(String fileName) {
        ArrayList<String> arrayList = getDateArray(fileName);
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, arrayList.get(i).trim());
        }

        for (int i = 1; i < arrayList.size(); i++) {
            for (int k = i + 1; k < arrayList.size(); k++) {

                String[] cDateList = arrayList.get(i).split("Date: ");
                cDateList = cDateList[1].split("\n\nWeight:");
                cDateList = cDateList[0].split("\\.");
                int currentDate = Integer.parseInt(cDateList[2]) * 10000 + Integer.parseInt(cDateList[1]) * 100 + Integer.parseInt(cDateList[0]);

                String[] nDateList = arrayList.get(k).split("Date: ");
                nDateList = nDateList[1].split("\n\nWeight:");
                nDateList = nDateList[0].split("\\.");
                int nextDate = Integer.parseInt(nDateList[2]) * 10000 + Integer.parseInt(nDateList[1]) * 100 + Integer.parseInt(nDateList[0]);

                if (nextDate > currentDate) {
                    String temp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(k));
                    arrayList.set(k, temp);
                    k = i + 1;
                }
            }
        }
        clearFile(fileName);
        for (int i = 1; i < arrayList.size(); i++) {
            writeToFile(fileName, arrayList.get(i) + "\n\n\n", true);
        }

    }


    public void makeNewDayScheduleFX(String fileName, String date, double weight, String food, String tasks) {


        // Food input formatting into a checklist
        food = makeCheckList(food);
        if (food.equals("none")) {
            food = "No food.";
        }

        // Task input formatting into a checklist
        tasks = makeCheckList(tasks);
        if (tasks.equals("none")) {
            tasks = "No tasks.";
        }

        // The schedule being made
        String schedule = "\n" + "\n" + "Date: " + date + "\n\n" +
                "Weight: " + weight + " Kg" + "\n\n" +
                "Food:" + "\n" + food + "\n" + "Tasks:" + "\n" + tasks;

        // Printing out the schedule for debugging and also writing it into the file
        System.out.println(schedule);
        writeToFile(fileName, schedule, true);
        sort(fileName);
    }

    public void makeNewDayScheduleFX(String fileName, String date, double weight, String food, String tasks, ArrayList<Boolean> foodCheckBoxValues, ArrayList<Boolean> tasksCheckBoxValues) {

        food = makeCheckList(food);
        tasks = makeCheckList(tasks);

        ArrayList<Boolean> outFoodCheckBoxValues = getCheckValues(food);
        ArrayList<Boolean> outTasksCheckBoxValues = getCheckValues(tasks);
        if (outFoodCheckBoxValues.size() <= foodCheckBoxValues.size()) {
            for (int i = 0; i < outFoodCheckBoxValues.size(); i++) {
                outFoodCheckBoxValues.set(i, foodCheckBoxValues.get(i));
            }
        } else {
            for (int i = 0; i < foodCheckBoxValues.size(); i++) {
                outFoodCheckBoxValues.set(i, foodCheckBoxValues.get(i));
            }
        }

        if (outTasksCheckBoxValues.size() <= tasksCheckBoxValues.size()) {
            for (int i = 0; i < outTasksCheckBoxValues.size(); i++) {
                outTasksCheckBoxValues.set(i, tasksCheckBoxValues.get(i));
            }
        } else {
            for (int i = 0; i < tasksCheckBoxValues.size(); i++) {
                outTasksCheckBoxValues.set(i, tasksCheckBoxValues.get(i));
            }
        }
        food = makeCheckList(food.split("\\[ \\] |\\[x\\] "), outFoodCheckBoxValues);
        tasks = makeCheckList(tasks.split("\\[ \\] |\\[x\\] "), outTasksCheckBoxValues);

        if (food.equals("")) {
            food = "No food.";
        }
        if (tasks.equals("")) {
            tasks = "No tasks.";
        }

        // The schedule being made
        String schedule = "\n" + "\n" + "Date: " + date + "\n\n" +
                "Weight: " + weight + " Kg" + "\n\n" +
                "Food:" + "\n" + food + "\n" + "Tasks:" + "\n" + tasks;

        // Printing out the schedule for debugging and also writing it into the file
        System.out.println(schedule);
        writeToFile(fileName, schedule, true);
        sort(fileName);
    }

    public ArrayList<String> makeDateListInfo(ArrayList<String> dateArray) {
        ArrayList<String> outputList = new ArrayList<>();
        for(int i = 1; i < dateArray.size(); i++) {
            String[] arrOfText = dateArray.get(i).split("Date: ");
            arrOfText = arrOfText[1].split("\n\nWeight:");
            String date = arrOfText[0];
            arrOfText = arrOfText[1].split(" Kg|Kg");
            String weight = arrOfText[0];
            outputList.add("Date: " + date +  "    Weight: " + weight);
        }
        return outputList;
    }

    public ArrayList<String> extractDateValues(String fileName, String inputDate) {
        ArrayList<String> outputList = new ArrayList<>();
        String dateText = getDate(fileName, inputDate);



        String[] arrOfText = dateText.split("Date: ");
        arrOfText = arrOfText[1].split("\n\nWeight:");
        String date = arrOfText[0];
        arrOfText = arrOfText[1].split(" Kg|Kg");
        String weight = arrOfText[0];
        arrOfText = arrOfText[1].split("Food:\n");
        arrOfText = arrOfText[1].split("\nTasks:\n");
        String food = arrOfText[0];
        String tasks = arrOfText[1];

        outputList.add(date); // 0
        outputList.add(weight); // 1
        outputList.add(food); // 2
        outputList.add(tasks); // 3

        return outputList;
    }




}





























