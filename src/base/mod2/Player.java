package base.mod2;

import java.util.Random;

public class Player {

    VARIANTS variant;
    String name;

    public Player(VARIANTS variant, String name) {
        this.variant = variant;
        this.name = name;
    }

    public Player(){
        VARIANTS[] variants = VARIANTS.values(); //Получаем массив возможных значений
        Random random = new Random();
        this.variant = variants[random.nextInt(variants.length)];
        this.name = "bot";
    }

    public enum VARIANTS{
        ROCK,
        PAPER,
        SCISSORS
    }

    public String whoWins(Player player1, Player player2){
        VARIANTS p1 = player1.variant;
        VARIANTS p2 = player2.variant;

        String p1_str = switch (p1) {
            case SCISSORS -> "scissors";
            case PAPER -> "paper";
            case ROCK -> "rock";
        };

        String p2_str = switch (p2) {
            case SCISSORS -> "scissors";
            case PAPER -> "paper";
            case ROCK -> "rock";
        };

        if (p1 == p2) {
            return "\nYour move: " + p2_str + "\nBot move: " + p1_str + "\n\n" + "It's a draw!";
        } else if ((p1 == VARIANTS.ROCK && p2 == VARIANTS.SCISSORS) ||
                (p1 == VARIANTS.SCISSORS && p2 == VARIANTS.PAPER) ||
                (p1 == VARIANTS.PAPER && p2 == VARIANTS.ROCK)) {
            return "\nYour move: " + p2_str + "\nBot move: " + p1_str + "\n\n" + player1.name + " wins!";
        } else {
            return "\nYour move: " + p2_str + "\nBot move: " + p1_str + "\n\n" + player2.name + " wins!";
        }
    }
}

