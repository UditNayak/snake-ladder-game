package Strategies.MoveValidation;

public interface IMoveValidationStrategy {
    int validateMove(int currentPosition, int diceRoll, int boardSize);
}
