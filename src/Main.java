import java.util.ArrayList;
public class Main {

    public static void main(String[] args) { // тесты методов и объектов
        Manager manager = new Manager();

        // создать объект Task
        Task task1 = new Task("1. Помыть посуду", "Использовать жёлтую губку", "NEW");
        Task task2 = new Task("2. Покормить рыбок", "Найти корм", "NEW");

        // тест метода createTask()
        manager.createTask(task1);
        manager.createTask(task2);
        System.out.println("Добавлена задача с ID " + task1.getId());
        System.out.println("Добавлена задача с ID " + task2.getId());

        // тест метода getTaskById()
        System.out.println("Задача по ID " + task1.getId() + ": " + manager.getTaskById(task1.getId()));

        // тест метода updateTask()
        Task updatedTask1 = new Task("1. Посуда в процессе мытья", "Жёлтая губка используется", "IN_PROGRESS");
        manager.updateTask(updatedTask1, task1.getId());
        System.out.println("Обновление задачи с ID " + task1.getId());

        // тест метода getListOfAllTasks()
        ArrayList<Task> allTasks = manager.getListOfAllTasks();
        for (Task task : allTasks) {
            System.out.println("Задача " + task.getName());
        }


        // создать объект Epic
        Epic epic1 = new Epic("3.Приготовить макароны по-флотски", "Спросить рецепт", "NEW");
        Epic epic2 = new Epic("4.Сходить в магазин", "Купить продукты", "NEW");

        // тест метода createEpic()
        int epicId = manager.createEpic(epic1);
        int epicId2 = manager.createEpic(epic2);
        System.out.println("Добавлен эпик с ID " + epicId);
        System.out.println("Добавлен эпик с ID " + epicId2);

        // тест метода getEpicById()
        System.out.println("Эпик по ID " + epicId + manager.getEpicById(epicId));

        // тест метода updateEpic()
        Epic updatedEpic1 = new Epic("Макароны в процессе готовки", "Кипит вода", "IN_PROGRESS");
        manager.updateEpic(updatedEpic1, epicId);
        System.out.println("Обновление эпика " + epicId + updatedEpic1.getName());

        // тест метода getListOfAllEpics()
        ArrayList<Epic> allEpics = manager.getListOfAllEpics();
        for (Epic epic : allEpics) {
            System.out.println("Название эпика: " + epic.getName());
        }


        // создать объект Subtask
        Subtask subtask1 = new Subtask("Купить макароны", "В магазине Пятёрочка", "NEW", 1);
        Subtask subtask2 = new Subtask("Купить фарш", "В магазине Магнит", "NEW", 1);

        // тест метода createSubtask()
        int subtaskId = manager.createSubtask(subtask1);
        int subtaskId2 = manager.createSubtask(subtask2);
        System.out.println("Создана подзадача с ID: " + subtaskId);
        System.out.println("Создана подзадача с ID: " + subtaskId2);

        // тест метода getSubtaskById()
        System.out.println("Подзадача по ID: " + subtask1.getName() + manager.getSubtaskById(subtaskId));

        // тест метода updateSubtask()
        Subtask updatedSubtask = new Subtask("Найти макароны в шкафу", "На самой верхней полке", "IN_PROGRESS", 1);
        manager.updateSubtask(updatedSubtask, subtaskId);
        System.out.println("Обновление подзадачи с ID: " + subtaskId);
    }
}