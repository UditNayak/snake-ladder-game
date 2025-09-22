import Dice.IDice;
import Dice.SixSidedDice;
import Player.IPlayer;
import Player.PlayerFactory;

public class Main {
    public static void main(String[] args) {
        IDice dice = SixSidedDice.getInstance();
        IPlayer humanPlayer = PlayerFactory.createPlayer("human", "Udit");
        humanPlayer.rollDice(dice);
    }
}