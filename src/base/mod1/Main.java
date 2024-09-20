package base.mod1;

public class Main {
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        System.out.println("Напишите программу на Java, которая будет подсчитывать общую сумму чисел от 0 до 1000.\n" +
                "Суммировать необходимо лишь те числа, которые кратны 3 или 5.");
        int sum = task1.getSum();
        System.out.println(sum);

        System.out.println("Создайте двумерный массив, в котором найдите минимальный элемент среди\n" +
                "всех элементов массива. Важно учесть, что нахождение минимального массива должен " +
                "производиться при помощи циклов. Массив:\n" +
                "int[][] x = { {20, 34, 2}, {9, 12, 18}, {3, 4, 5} };");
        int min = task1.getMin();
        System.out.println(min);
    }
}