package Strategies.Sixes;

public interface IConsecutiveSixesStrategy {
    /**
     * Determines if the player gets an extra turn based on consecutive sixes rolled.
     * @param consecutiveSixesCount Number of consecutive sixes rolled so far.
     * @return true if the player gets an extra turn, false otherwise.
     */
    boolean allowsExtraTurn(int consecutiveSixesCount);
}
