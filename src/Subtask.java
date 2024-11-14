public class Subtask extends Task {
    private final Epic parentEpic;

    public Subtask(String name, String description, Epic parentEpic) {
        super(name, description);
        this.parentEpic = parentEpic;
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", parentEpic=" + parentEpic +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
