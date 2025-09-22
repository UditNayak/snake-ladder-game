package Strategies.Killing;

import Player.IPlayer;

public class SendOpponentBackStrategy implements IKillingStrategy {
    @Override
    public void handleCollision(IPlayer currentPlayer, IPlayer opponent) {
        // Send opponent back to start (position 0)
        opponent.setCurrentPosition(0);
    }
}