import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatedId = 1;

    public void createTask(Task task) {
        task.setId(generatedId++);
        task.setStatus("NEW");
        tasks.put(task.getId(), task);
    }
    public int createEpic(Epic epic) {
        epic.setId(generatedId++);
        epic.setStatus("NEW");
        epics.put(epic.getId(), epic);
        return epic.getId();
    }
    public int createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getMyEpicId())) {
            subtask.setId(generatedId++);
            subtask.setStatus("NEW");
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getMyEpicId());
            epic.setSubtasksIds(subtask.getId());
            updateEpicStatus(epic);
        } else {
            System.out.println("Ошибка! Необходимо создать задачу");
        }
        return subtask.getId();
    }
    private void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        boolean allDone = true;
        boolean allNew = true;
        for (int i = 0; i < subtasksIds.size(); i++) {
            Subtask subtask = subtasks.get(subtasksIds.get(i));
            if (!subtask.getStatus().equals("DONE")) {
                allDone = false;
            }
            if (!subtask.getStatus().equals("NEW")) {
                allNew = false;
            }
        }
        if (allDone) {
            epic.setStatus("DONE");
        } else if (allNew) {
            epic.setStatus("NEW");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        int subtaskId;
        for (int i = 0; i < subtasksIds.size(); i++) {
            subtaskId = subtasksIds.get(i);
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = epics.get(subtask.getMyEpicId());
        epic.removeSubtaskById(id);
        subtasks.remove(id);
        updateEpicStatus(epic);
    }
    public void deleteAllTasks() {
        tasks.clear();
    }
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
            for (int subtaskId : subtasksIds) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearAllSubtasks();
            updateEpicStatus(epic);
        }
    }
    public ArrayList<Task> getListOfAllTasks() {
        if (tasks.isEmpty()) return null;
        ArrayList<Task> listOfTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            listOfTasks.add(task);
        }
        return listOfTasks;
    }
    public ArrayList<Epic> getListOfAllEpics() {
        if (epics.isEmpty()) return null;
        ArrayList<Epic> listOfEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            listOfEpics.add(epic);
        }
        return listOfEpics;
    }
    public ArrayList<Subtask> getListOfAllSubtasks() {
        if (subtasks.isEmpty()) return null;
        ArrayList<Subtask> listOfSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            listOfSubtasks.add(subtask);
        }
        return listOfSubtasks;
    }
    public Task getTaskById(int id) {
        return tasks.get(id);
    }
    public Epic getEpicById(int id) {
        return epics.get(id);
    }
    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }
    public void updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        } else {
            System.out.println("Невозможно обновить несуществующую задачу");
        }
    }
    public void updateEpic(Epic epic, int id) {
        if (epics.containsKey(id)) {
            epics.put(id, epic);
        } else {
            System.out.println("Невозможно обновить несуществующую задачу");
        }
    }
    public void updateSubtask(Subtask subtask, int id) {
        if (subtasks.containsKey(id)) {
            subtasks.put(id, subtask);
            Epic epic = getEpicById(subtask.getMyEpicId());
            updateEpicStatus(epic);
        } else {
            System.out.println("Невозможно обновить несуществующую подзадачу");
        }
    }
    public ArrayList<Subtask> getListOfSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> listOfSubtasksByEpicId = new ArrayList<>();
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        for(Integer id : subtasksIds) {
            listOfSubtasksByEpicId.add(subtasks.get(id));
        }
        return listOfSubtasksByEpicId;
    }
}
