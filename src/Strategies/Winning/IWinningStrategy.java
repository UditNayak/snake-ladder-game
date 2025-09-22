package Strategies.Winning;

import Player.IPlayer;

public interface IWinningStrategy {
    boolean hasPlayerWon(IPlayer player, int finalCell);
}