/**
 * The type Start.
 */
public class Start {


    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[]args) {
            Schedule schedule = new Schedule();
            String fileName = "Pschedule.txt";
            schedule.makeFile(fileName);
            Menu menu = new Menu();
            System.out.println("Type Help to see a list of commands.");
            menu.startMenu(fileName);
        }

}
