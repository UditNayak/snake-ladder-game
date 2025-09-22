package Strategies.Starting;

public class AnyRollToStartStrategy implements IStartingStrategy {
    @Override
    public boolean canStart(int currentPosition, int diceRoll) {
        // Any roll allows the player to start moving
        return true;
    }
}