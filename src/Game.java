import java.util.List;
import java.util.Map;
import java.util.Queue;

import Dice.IDice;
import Player.IPlayer;
import Strategies.Killing.IKillingStrategy;
import Strategies.MoveValidation.IMoveValidationStrategy;
import Strategies.Starting.IStartingStrategy;
import Strategies.Winning.IWinningStrategy;

public class Game {
    private final Board board;
    private final IDice dice;
    private final List<IPlayer> players;
    private final Queue<IPlayer> playerTurns;
    private final IWinningStrategy winningStrategy;
    private final IStartingStrategy startingStrategy;
    private final IKillingStrategy killingStrategy;
    private final IMoveValidationStrategy moveValidationStrategy;
    private final ISpecialRollStrategy specialRollStrategy;
    private final Map<String, Integer> consecutiveSpecialRolls; // Tracks consecutive special rolls per player

    // Constructor using Dependency Injection
    public Game(Board board, IDice dice, List<IPlayer> players,
                IWinningStrategy winStrat, IStartingStrategy startStrat,
                IKillingStrategy killStrat, IMoveValidationStrategy moveStrat,
                ISpecialRollStrategy specialRollStrat) {
        // Initialize all fields...
    }

    public void start() {
        // Main game loop
        while (!isGameOver()) {
            IPlayer currentPlayer = playerTurns.poll();

            int roll = currentPlayer.rollDice(dice);
            // Apply Starting Strategy
            // Apply Move Validation Strategy
            // Check for Board Entities (Snakes/Ladders)
            // Apply Killing Strategy

            // Handle special rolls
            if (specialRollStrategy.hasExtraTurn(roll, consecutiveSpecialRolls.getOrDefault(currentPlayer.getName(), 0))) {
                // Update consecutiveSpecialRolls for current player
                // Add current player back to the front of the queue
            } else {
                // Reset consecutiveSpecialRolls for current player
            }

            playerTurns.add(currentPlayer);
        }
    }

    private boolean isGameOver() {
        // Use winningStrategy to check if any player has won
    }
}