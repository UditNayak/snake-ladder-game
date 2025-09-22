package Player;

public class PlayerFactory {
    public static IPlayer createPlayer(String type, String name) {
        switch (type.toLowerCase()) {
            case "human":
                return new HumanPlayer(name);
            case "bot":
                return new BotPlayer(name);
            default:
                throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }
}