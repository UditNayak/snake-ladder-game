package Strategies.Starting;

public class SpecificRollToStartStrategy implements IStartingStrategy {
    private final int requiredRoll;

    public SpecificRollToStartStrategy(int requiredRoll) {
        this.requiredRoll = requiredRoll;
    }

    @Override
    public boolean canStart(int currentPosition, int diceRoll) {
        // Player can start moving only if they roll the required number
        return (currentPosition != 0) || (diceRoll == requiredRoll);
    }
    
}
