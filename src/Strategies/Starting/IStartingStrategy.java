package Strategies.Starting;

public interface IStartingStrategy {
    /**
     * If player is at start (0), can they start moving with this roll?
     */
    boolean canStart(int currentPosition, int diceRoll);
}
