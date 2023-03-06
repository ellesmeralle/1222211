import manager.InMemoryTaskManager;
import manager.TaskStatus;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) { // тесты методов и объектов
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        // создать объект tasks.Task
        Task task1 = new Task("1. Помыть посуду", "Использовать жёлтую губку", TaskStatus.NEW);
        Task task2 = new Task("2. Покормить рыбок", "Найти корм", TaskStatus.NEW);

        // тест метода createTask()
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        System.out.println("Добавлена задача с ID " + task1.getId());
        System.out.println("Добавлена задача с ID " + task2.getId());

        // тест метода getTaskById()
        System.out.println("Задача по ID " + task1.getId() + ": " + inMemoryTaskManager.getTaskById(task1.getId()));

        // тест метода getHistory()
        inMemoryTaskManager.getHistory();

        // тест метода updateTask()
        Task updatedTask1 = new Task("1. Посуда в процессе мытья", "Жёлтая губка используется",
                TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(updatedTask1, task1.getId());
        System.out.println("Обновление задачи с ID " + task1.getId());

        // тест метода getListOfAllTasks()
        ArrayList<Task> allTasks = inMemoryTaskManager.getListOfAllTasks();
        for (Task task : allTasks) {
            System.out.println("Задача " + task.getName());
        }


        // создать объект tasks.Epic
        Epic epic1 = new Epic("3.Приготовить макароны по-флотски", "Спросить рецепт", TaskStatus.NEW);
        Epic epic2 = new Epic("4.Сходить в магазин", "Купить продукты", TaskStatus.NEW);

        // тест метода createEpic()
        int epicId = inMemoryTaskManager.createEpic(epic1);
        int epicId2 = inMemoryTaskManager.createEpic(epic2);
        System.out.println("Добавлен эпик с ID " + epicId);
        System.out.println("Добавлен эпик с ID " + epicId2);

        // тест метода getEpicById()
        System.out.println("Эпик по ID " + epicId + " " + inMemoryTaskManager.getEpicById(epicId));

        // тест метода updateEpic()
        Epic updatedEpic1 = new Epic("Макароны в процессе готовки", "Кипит вода", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateEpic(updatedEpic1, epicId);
        System.out.println("Обновление эпика " + epicId + " " + updatedEpic1.getName());

        // тест метода getListOfAllEpics()
        ArrayList<Epic> allEpics = inMemoryTaskManager.getListOfAllEpics();
        for (Epic epic : allEpics) {
            System.out.println("Название эпика: " + epic.getName());
        }


        // создать объект tasks.Subtask
        Subtask subtask1 = new Subtask("Купить макароны", "В магазине Пятёрочка",
                TaskStatus.NEW, 1);
        Subtask subtask2 = new Subtask("Купить фарш", "В магазине Магнит", TaskStatus.NEW, 1);

        // тест метода createSubtask()
        int subtaskId = inMemoryTaskManager.createSubtask(subtask1);
        int subtaskId2 = inMemoryTaskManager.createSubtask(subtask2);
        System.out.println("Создана подзадача с ID: " + subtaskId);
        System.out.println("Создана подзадача с ID: " + subtaskId2);

        // тест метода getSubtaskById()
        System.out.println("Подзадача по ID: " + subtask1.getName() + inMemoryTaskManager.getSubtaskById(subtaskId));

        // тест метода getHistory()
        inMemoryTaskManager.getHistory();

        // тест метода updateSubtask()
        Subtask updatedSubtask = new Subtask("Найти макароны в шкафу", "На самой верхней полке",
                TaskStatus.IN_PROGRESS, 1);
        inMemoryTaskManager.updateSubtask(updatedSubtask, subtaskId);
        System.out.println("Обновление подзадачи с ID: " + subtaskId);
    }
}