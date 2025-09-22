package Player;

import Dice.IDice;

public interface IPlayer {
    String getName();
    int getCurrentPosition();
    void setCurrentPosition(int position);

    /**
     * Called when it's this player's turn.
     * Should return the dice roll value.
     * For human players, this may request input; for bots, it's automatic.
     */
    int rollDice(IDice dice);
}