package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static manager.Managers.getDefaultHistory;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatedId = 1;
    private final HistoryManager historyManager = getDefaultHistory();

    @Override
    public void createTask(Task task) {
        task.setId(generatedId++);
        task.setStatus(TaskStatus.NEW);
        tasks.put(task.getId(), task);
    }
    
    @Override
    public int createEpic(Epic epic) {
        epic.setId(generatedId++);
        epic.setStatus(TaskStatus.NEW);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getMyEpicId())) {
            subtask.setId(generatedId++);
            subtask.setStatus(TaskStatus.NEW);
            subtasks.put(subtask.getId(), subtask);
            final Epic epic = epics.get(subtask.getMyEpicId());
            epic.addSubtaskId(subtask.getId());
            updateEpicStatus(epic);
        } else {
            System.out.println("Ошибка! Необходимо создать задачу");
        }
        return subtask.getId();
    }

    private void updateEpicStatus(Epic epic) {
        final ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        boolean allDone = true;
        boolean allNew = true;
        for (int i = 0; i < subtasksIds.size(); i++) {
            final Subtask subtask = subtasks.get(subtasksIds.get(i));
            if (!subtask.getStatus().equals(TaskStatus.DONE)) {
                allDone = false;
            }
            if (!subtask.getStatus().equals(TaskStatus.NEW)) {
                allNew = false;
            }
        }
        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        final Epic epic = epics.get(id);
        final ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        int subtaskId;
        for (int i = 0; i < subtasksIds.size(); i++) {
            subtaskId = subtasksIds.get(i);
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        final Subtask subtask = subtasks.get(id);
        final Epic epic = epics.get(subtask.getMyEpicId());
        epic.removeSubtaskById(id);
        subtasks.remove(id);
        updateEpicStatus(epic);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearAllSubtasks();
            updateEpicStatus(epic);
        }
    }

    @Override
    public ArrayList<Task> getListOfAllTasks() {
        if (tasks.isEmpty()) return null;
        final ArrayList<Task> listOfTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            listOfTasks.add(task);
        }
        return listOfTasks;
    }

    @Override
    public ArrayList<Epic> getListOfAllEpics() {
        if (epics.isEmpty()) return null;
        final ArrayList<Epic> listOfEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            listOfEpics.add(epic);
        }
        return listOfEpics;
    }

    @Override
    public ArrayList<Subtask> getListOfAllSubtasks() {
        if (subtasks.isEmpty()) return null;
        final ArrayList<Subtask> listOfSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            listOfSubtasks.add(subtask);
        }
        return listOfSubtasks;
    }

    @Override
    public Task getTaskById(int id) {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        final Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public void updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        } else {
            System.out.println("Невозможно обновить несуществующую задачу");
        }
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (epics.containsKey(id)) {
            epics.put(id, epic);
        } else {
            System.out.println("Невозможно обновить несуществующую задачу");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        if (subtasks.containsKey(id)) {
            subtasks.put(id, subtask);
            final Epic epic = getEpicById(subtask.getMyEpicId());
            updateEpicStatus(epic);
        } else {
            System.out.println("Невозможно обновить несуществующую подзадачу");
        }
    }

    @Override
    public ArrayList<Subtask> getListOfSubtasksByEpicId(int epicId) {
        final Epic epic = epics.get(epicId);
        final ArrayList<Subtask> listOfSubtasksByEpicId = new ArrayList<>();
        final ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        for (Integer id : subtasksIds) {
            listOfSubtasksByEpicId.add(subtasks.get(id));
        }
        return listOfSubtasksByEpicId;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
