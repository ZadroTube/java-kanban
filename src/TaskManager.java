import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int identificator = 0;
    private static final HashMap<Integer, Task> tasks = new HashMap<>();
    private static final HashMap<Integer, Epic> epics = new HashMap<>();
    private static final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private static int getNewId() {
        return ++identificator;
    }

    // Добавление задачи
    public static void addTask(Task task) {
        task.setId(getNewId());
        tasks.put(task.getId(), task);
    }

    // Добавление эпика
    public static void addEpic(Epic epic) {
        epic.setId(getNewId());
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    // Добавление подзадачи
    public static void addSubtask(Subtask subtask) {
        subtask.setId(getNewId());
        subtasks.put(subtask.getId(), subtask);
        Epic parentEpic = subtask.getParentEpic();
        parentEpic.subtasks.add(subtask);
        updateEpicStatus(parentEpic);
    }

    // Получение задачи по идентификатору
    public static Task getTaskById(int id) {
        return tasks.get(id);
    }

    public static Epic getEpicById(int id) {
        return epics.get(id);
    }

    public static Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    // Получение списка подзадач для определённого эпика
    public static ArrayList<Subtask> getSubtasksByEpic(int epicId) {
        Epic epic = epics.get(epicId);
        return (epic != null) ? epic.subtasks : new ArrayList<>(); // Упрощаем через тернарный
    }

    // Получение всех задач
    public static ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public static ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public static ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Удаление всех задач
    static void deleteAllTasks() {
        tasks.clear();
    }

    static void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    static void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.subtasks.clear();
            updateEpicStatus(epic);
        }
    }

    // Удаляем задачу по идентификатору
    static void deleteTaskById(int id) {
        tasks.remove(id);
    }

    // Удаляем пару ключ-значение и связанные с этим эпиком подзадачи
    static void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.subtasks) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    static void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic parentEpic = subtask.getParentEpic();
            parentEpic.subtasks.remove(subtask);
            updateEpicStatus(parentEpic);
        }
    }

    // Меняем статус и обновляем, передаем любой тип задачи так как в параметре родительский тип
    static void changeStatus(Task task, Status newStatus) {
        // Проверяем, позволяет ли тип задачи изменение статуса
        if (task.isStatusChangeAllowed()) {

            // Изменяем статус задачи
            task.status = newStatus;

            // Если это подзадача, обновляем статус эпика
            if (task instanceof Subtask) { // Проверяем является ли объект нужным типом
                Epic parentEpic = ((Subtask) task).getParentEpic(); // Явно приводим к типу для доступа к методу
                updateEpicStatus(parentEpic); // Обновляем статус эпика, которому принадлежит подзадача
            }

            // Обновляем задачу в коллекции
            update(task);
        }
    }

    // Обновляем задачу в коллекции с сохранением id и в зависимости от типа
    private static void update(Task task) {
        if (task instanceof Epic) {
            epics.put(task.getId(), (Epic) task);
        } else if (task instanceof Subtask) {
            subtasks.put(task.getId(), (Subtask) task);
        } else {
            tasks.put(task.getId(), task);
        }
    }

    // По условию задачи эпик без подзадач всегда NEW
    private static void updateEpicStatus(Epic epic) {
        if (epic.subtasks.isEmpty()) {
            epic.status = Status.NEW;
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        // Проверяем статус подзадач
        for (Subtask subtask : epic.subtasks) {
            if (subtask.status != Status.DONE) {
                allDone = false;
            }
            if (subtask.status != Status.NEW) {
                allNew = false;
            }
        }

        //Устанавливаем статус эпика согласно заданию
        if (allDone) {
            epic.status = Status.DONE;
        } else if (allNew) {
            epic.status = Status.NEW;
        } else {
            epic.status = Status.IN_PROGRESS;
        }
    }
}
