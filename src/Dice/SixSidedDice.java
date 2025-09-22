package Dice;

/*
 * Singleton Six-sided dice
 * - private constructor with reflection guard
 * - static getInstance method
 * - DCL with volatile
 */
public class SixSidedDice implements IDice {
    private static SixSidedDice instance;

    private SixSidedDice() {
        if (instance != null) {
            throw new RuntimeException("Reflection is not allowed to create singleton instance");
        }
    }

    public static SixSidedDice getInstance() {
        if (instance == null) {
            synchronized (SixSidedDice.class) {
                if (instance == null) {
                    instance = new SixSidedDice();
                }
            }
        }
        return instance;
    }

    @Override
    public int roll() {
        return (int) (Math.random() * 6) + 1; // Generates a number between 1 and 6
    }
    
}
