package model;

import manager.TaskStatus;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksIds = new ArrayList<>();

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void addSubtaskId(Integer subtasksId) {
        this.subtasksIds.add(subtasksId);
    }

    public void removeSubtaskById(Integer subtaskId) {
        subtasksIds.remove(subtaskId);
    }

    public void clearAllSubtasks() {
        subtasksIds.clear();
    }
}
