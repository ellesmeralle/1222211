public class Subtask extends Task {
    private int myEpicId;

    public Subtask(String name, String description, String status, int myEpicId) {
        super(name, description, status);
        this.myEpicId = myEpicId;
    }

    public int getMyEpicId() {
        return myEpicId;
    }
}
