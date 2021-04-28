import ss.utils.TextIO;

/**
 * The type Menu.
 */
public class Menu {

    private String date;

    /**
     * The Schedule.
     */
    Schedule schedule = new Schedule();


    /**
     * Start menu.
     * This runs the menu and processes all commands.
     * @param fileName the file name
     */
    public void startMenu(String fileName)  {
        boolean end = false;
            while (!end) {
                String[] command = TextIO.getlnString().split(" ");
                switch (command[0]) {

                    case "New":
                        schedule.makeNewDaySchedule(fileName);
                        break;

                    case "Read":
                        if (command.length == 1) {
                            schedule.sort(fileName);
                            System.out.println(schedule.readFile(fileName));
                        } else {
                            date = schedule.formatDate(command[1]);

                            if (date.equals("INVALID") || schedule.getDate(fileName, date).equals("NOTDATE")) {
                                System.out.println("Invalid or non-existent date, try again.");
                            } else {
                                System.out.println(schedule.getDate(fileName, date));
                            }
                        }
                        break;

                    case "Remove":
                        if (command.length == 1) {
                            System.out.println("Remove needs a date.");
                        } else {
                            date = schedule.formatDate(command[1]);

                            if (date.equals("INVALID") || schedule.getDate(fileName, date).equals("NOTDATE")) {
                                System.out.println("Invalid or non-existent date, try again.");
                            } else {
                                System.out.println("Date " + date + " has been removed.");
                                schedule.deleteDate(fileName, date);
                            }
                        }
                        break;

                    case "Edit":

                        if (command.length == 1) {
                            System.out.println("Edit needs a date.");
                        } else if (command.length == 2){
                            System.out.println("You must specify what you want to edit Eg. weight, food, tasks");
                        } else {

                            date = schedule.formatDate(command[1]);
                            if (date.equals("INVALID") || schedule.getDate(fileName, date).equals("NOTDATE")) {
                                System.out.println("Invalid or non-existent date, try again.");
                            } else if (!command[2].equals("weight") && !command[2].equals("food") && !command[2].equals("tasks")) {
                                System.out.println("The third command bust be one of these: weight, food, tasks.");
                            } else {
                                schedule.editDate(fileName, command[1], command[2]);
                            }

                        }

                        break;

                    case "Check":
                        if (command.length == 1) {
                            System.out.println("Check needs a date.");
                        } else if (command.length == 2){
                            System.out.println("You must specify what you want to check Eg. food, tasks");
                        } else {
                            date = schedule.formatDate(command[1]);
                            if (date.equals("INVALID") || schedule.getDate(fileName, date).equals("NOTDATE")) {
                                System.out.println("Invalid or non-existent date, try again.");
                            } else if (!command[2].equals("food") && !command[2].equals("tasks")) {
                                System.out.println("The third command bust be one of these: food, tasks.");
                            } else {
                                schedule.editDateCheck(fileName, command[1], command[2]);
                            }
                        }

                        break;

                    case "Clear":
                        System.out.println("The file has been cleared.");
                        schedule.clearFile(fileName);
                        break;

                    case "Help":
                        System.out.println("The commands are as follows: \n" +
                                "New - Makes a new schedule." + "\n" +
                                "Read - Read the the entire schedule." + "\n" +
                                "Read [date here] - Read a specific date." + "\n" +
                                "Remove [date here] - Removes a specific date." + "\n" +
                                "Edit [date here] [weight/food/tasks] - Edit a date." + "\n" +
                                "Check [date here] [food/tasks] - Lets you add a checkmark when you complete something." + "\n" +
                                "Clear - Clears the entire schedule." + "\n" +
                                "Help - Shows a menu of commands." + "\n" +
                                "Exit - Ends the program. " + "\n");
                        break;

                    case "Exit":
                        System.out.println("Goodbye!");
                        end = true;
                        break;
                }


            }
    }

}
