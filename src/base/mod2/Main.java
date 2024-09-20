package base.mod2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Создаем объекты

        Player bot = new Player();

        Scanner in = new Scanner(System.in);

        System.out.print("Input name: ");
        String name = in.nextLine();

        System.out.print("Input your move(scissors|paper|rock): ");
        String variant = in.nextLine();
        Player.VARIANTS move = switch (variant) {
            case "scissors" -> Player.VARIANTS.SCISSORS;
            case "paper" -> Player.VARIANTS.PAPER;
            case "rock" -> Player.VARIANTS.ROCK;
            default -> throw new IllegalStateException("Unexpected value: " + variant);
        };

        Player player = new Player(move, name);

// Получаем результат

        System.out.println(bot.whoWins(bot, player));

        //Вам необходимо создать класс Player, в котором должны быть реализованы следующие моменты:
        //перечисление для установки выбора: enum VARIANTS;
        //переменные для установки имени, а также определенного варианта из перечисления;
        //два конструктора. Первый ничего не принимает и устанавливает случайное значение из перечисления,
        //а также имя «Bot». Второй конструктор принимает определенный вариант из перечисления и имя для объекта;
        //функция whoWins, которая принимает два объекта и возвращает либо строку «Ничья», либо информацию про игрока, который победил.
    }
}

