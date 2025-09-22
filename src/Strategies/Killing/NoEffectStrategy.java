package Strategies.Killing;

import Player.IPlayer;

public class NoEffectStrategy implements IKillingStrategy {
    @Override
    public void handleCollision(IPlayer currentPlayer, IPlayer opponent) {
        // No effect on collision
    }
}
