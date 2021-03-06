import javafx.scene.control.Button;

public class Date {

    private String date;
    private String weight;
    private String food;
    private String tasks;
    private Button buttonDateDelete;
    private Button buttonDateEdit;

    public Date() {
        this.date = "";
        this.weight = "";
        this.food = "";
        this.tasks = "";
    }

    public Date(String date, String weight, String food, String tasks) {
        this.date = date;
        this.weight = weight;
        this.food = food;
        this.tasks = tasks;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

}
