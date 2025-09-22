package Player;

import Dice.IDice;

class BotPlayer implements IPlayer {
    private final String name;
    private int currentPosition;

    public BotPlayer(String name) {
        this.name = name;
        this.currentPosition = 0; // Start position
    }

    @Override public String getName() { return name; }
    @Override public int getCurrentPosition() { return currentPosition; }
    @Override public void setCurrentPosition(int position) { this.currentPosition = position; }

    @Override
    public int rollDice(IDice dice) {
        int roll = dice.roll();
        System.out.println(name + " (Bot) rolled: " + roll);
        return roll;
    }
    
}
