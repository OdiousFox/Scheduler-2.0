import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class YesNo {

    private static Schedule schedule = new Schedule();


    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[]args) {

//        for(int i = 0; i < 100; i++){
//            if (Math.random() >= 0.5) {
//                System.out.println("Yes");
//            } else {
//                System.out.println("No");
//            }
//        }


//        ArrayList<String> arrayList = schedule.getDateArray("schedule.txt");
//        for (int i = 0; i < arrayList.size(); i++) {
//            arrayList.set(i, arrayList.get(i).trim());
//            System.out.println(arrayList.get(i));
//            String[] arrOfText = arrayList.get(i).split("Date: ");
//            arrOfText = arrOfText[1].split("\n\nWeight:");
//            arrOfText = arrOfText[0].split(".");

//        }





        String dateText = "\n" +
                "Date: 02.04.2021\n" +
                "\n" +
                "Weight: 85.7 Kg\n" +
                "\n" +
                "Food:\n" +
                "[ ] Sandwich\n" +
                "[ ] Hamburger\n" +
                "\n" +
                "\n" +
                "Tasks:\n" +
                "[ ] Relax\n" +
                "[x] Programming\n" +
                "[x] Make food\n" +
                "\n";

//        String[] arrOfText = dateText.split("Date: ");
//        arrOfText = arrOfText[1].split("\n\nWeight:");
//        String date = arrOfText[0];
//        arrOfText = arrOfText[1].split(" Kg");
//        double weight = Double.parseDouble(arrOfText[0]);
//        arrOfText = arrOfText[1].split("Food:\n");
//        arrOfText = arrOfText[1].split("\n\n\nTasks:\n");
//        String food = arrOfText[0];
//        String tasks = arrOfText[1];
//        System.out.println(date + "\n\n" + weight + "\n\n" + food + "\n\n" + tasks);
//        System.out.println(schedule.getCheckValues(dateText));
//        String[] arrOfText = dateText.split("Date: ");
//        arrOfText = arrOfText[1].split("\n\nWeight:");
//        arrOfText = arrOfText[0].split("\\.");
//        System.out.println(arrOfText[0]);






    }

}
