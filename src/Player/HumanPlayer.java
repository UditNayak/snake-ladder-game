package Player;

import java.util.Scanner;

import Dice.IDice;

class HumanPlayer implements IPlayer {
    private final String name;
    private int currentPosition;
    private final Scanner scanner;

    public HumanPlayer(String name) {
        this.name = name;
        this.currentPosition = 0; // Start position
        this.scanner = new Scanner(System.in);
    }

    @Override public String getName() { return name; }
    @Override public int getCurrentPosition() { return currentPosition; }
    @Override public void setCurrentPosition(int position) { this.currentPosition = position; }

    @Override
    public int rollDice(IDice dice) {
        System.out.print(name + ", press Enter to roll the dice...");
        scanner.nextLine();
        int roll = dice.roll();
        System.out.println(name + " rolled: " + roll);
        return roll;
    }
}
