package Strategies.Killing;

import Player.IPlayer;

public interface IKillingStrategy {
    /**
     * Resolves collision when currentPlayer lands on a cell occupied by opponent.
     * Implementations should mutate positions if needed.
     */
    void handleCollision(IPlayer currentPlayer, IPlayer opponent);
}