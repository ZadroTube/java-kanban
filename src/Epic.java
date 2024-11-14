import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasks = new ArrayList<>();
    }

    @Override
    public boolean isStatusChangeAllowed() {
        return false;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", subtasksSize=" + subtasks.size() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
