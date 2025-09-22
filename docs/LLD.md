# Low-Level Design (Bottom-Up Approach)

## Dice
- **Interface**: `IDice`

    ```java
    public interface IDice {
        int roll();
    }
    ```
- **Implementations**:
    - `SixSidedDice`: Default 6-sided dice.
    - `CustomDice`: N-sided, weighted, or multiple dice.

### Design Pattern:
- **Singleton**: only one dice instance is usually needed per game
    - ensures only one source of randomness (avoids multiple inconsistent dice)
- **Strategy**: allows plugging in custom dice without changing game logic
    - The `Game` should be completely agnostic of dice implementation

### Future Extensions:
- **Factory**: to create different dice types based on configuration
    - encapsulates dice creation logic
    - For now, Since we have only one dice type, we can skip this.

## Board Entities
- **Interface**: `IBoardEntity`
    ```java
    public interface IBoardEntity {
        int getStart();
        int getEnd();
        int applyEffect(int currentPosition);
    }
    ```
- **Implementations**:
    - `Snake`: moves player down.
    - `Ladder`: moves player up.
    - Future: `Trap`, `Booster`, `BlackHole`, `Teleporter`, etc.

### Design Pattern:
- **Factory**: to create different board entities based on configuration
    - encapsulates entity creation logic
    - Easy to extend new entities without breaking core game engine.

## Player
- **Interface**: `IPlayer`
    ```java
    public interface IPlayer {
        String getName();
        int getCurrentPosition();
        void setCurrentPosition(int position);
        int rollDice(IDice dice);
    }
    ```
- **Implementations**:
    - `HumanPlayer`: `rollDice` might ask input from terminal or UI.
    - `BotPlayer`: `rollDice` will auto-roll.

### Design Pattern:
- **Strategy**: allows different player types (human, bot) without changing game logic
    - The `Game` should be completely agnostic of player implementation
- **Factory**: to create different player types based on configuration
    ```java
    IPlayer humanPlayer = PlayerFactory.createPlayer("human", "Udit");
    ```

#### Note:
- Never use Boolean flags to differentiate player types.
- Because boolean flags leads to `if-else` or `switch-case` statements, which violate OCP.
- Instead, use polymorphism to handle different player behaviors.

## Strategies / Rules
We have multiple configurable rules (Winning, MoveValidation, Starting, Killing, ConsecutiveSixes).
- **Approach**:
    - Each rule will be an interface with multiple implementations.
    - Strategy Pattern is a perfect fit: dynamically plug in rules.

**Examples**:

1. **Winning Strategy**:
    ```java
    public interface IWinningStrategy {
        boolean hasPlayerWon(IPlayer player, int finalCell);
    }
    ```
    - Implementations:
        - `ExactWinningStrategy`: must land exactly on last cell.
        - `OvershootWinningStrategy`: can overshoot and still win.

2. **Move Validation Strategy**:
    ```java
    public interface IMoveValidationStrategy {
        int validateMove(int currentPosition, int diceRoll, int boardSize);
    }  
    ```
    - Implementations:
        - `NoMoveOnOvershootStrategy`: stay if overshoot.
        - `AllowOvershootStrategy`: move forward anyway.

3. **Starting Strategy**:
    ```java
    public interface IStartingStrategy {
        boolean canStart(int currentPosition, int diceRoll);
    }
    ```
    - Implementations:
        - `AnyRollToStartStrategy`: start on any roll.
        - `SpecificRollToStartStrategy`: must roll a 1 or 6 to start.

4. **Killing Strategy**:
    ```java
    public interface IKillingStrategy {
        void handleCollision(IPlayer currentPlayer, IPlayer opponent);
    }
    ```
    - Implementations:
        - `SendOpponentBackStrategy`: opponent returns to start.
        - `NoEffectStrategy`: nothing happens.
        - `SelfSentBackStrategy`: current player returns to start.
        - `BothSentBackStrategy`: both players return to start.

5. **Special Roll Strategy**:
    ```java
    public interface ISpecialRollStrategy {
        boolean hasExtraTurn(int roll, int consecutiveSpecialRolls);
    }
    ```
    - Implementations:
        - `ClassicSpecialRollStrategy`: 6 is special, grants extra turn.
        - `NoSpecialRollStrategy`: no special rolls.

### Do not Hardcode the Default Strategies
We don’t want to hardcode everywhere. Better options:

- **Factory / Builder** Pattern: <br>
Have a GameConfig or GameFactory that knows the defaults.
```java
public class GameConfig {
    public static IWinningStrategy defaultWinningStrategy() {
        return new ExactWinningStrategy();
    }
    public static IMoveValidationStrategy defaultMoveValidation() {
        return new NoMoveOnOvershootStrategy();
    }
    // ... others
}
```

- **Builder Pattern Example**:
```java
Game game = new GameBuilder()
                .withWinningStrategy(new ExactWinningStrategy())
                .withMoveValidationStrategy(new NoMoveOnOvershootStrategy())
                .build();
```

This way we get default strategies but can override any.

### Design Pattern:
- **Strategy Pattern** → pick which rule to use.
- **Factory/Builder** → provide defaults.

### Future Extensions: Decorator Pattern
The **Decorator Pattern** allows us to **combine or extend rules dynamically** without making one huge class.

#### Example: Combining Killing Strategies
Suppose you want:
- Send opponent back AND log the event.
Instead of creating a new `SendOpponentBackWithLoggingStrategy`, we can decorate:

```java
public class LoggingKillingDecorator implements IKillingStrategy {
    private final IKillingStrategy wrapped;

    public LoggingKillingDecorator(IKillingStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void handleCollision(IPlayer currentPlayer, IPlayer otherPlayer) {
        System.out.println(currentPlayer.getName() + " collided with " + otherPlayer.getName());
        wrapped.handleCollision(currentPlayer, otherPlayer);
    }
}
```

Usage:
```java
IKillingStrategy strategy = new LoggingKillingDecorator(new SendOpponentBackStrategy());
```

---

#### Example: Stacking Consecutive Sixes Rules
Imagine tomorrow someone wants classic rule + log when player rolls three sixes:
```java
IConsecutiveSixesStrategy strategy = 
    new LoggingConsecutiveSixesDecorator(new ClassicConsecutiveSixesStrategy());
```

This is why Decorator is powerful:
- Keeps each rule small and focused.
- Lets us mix & match rules at runtime.

## Board
- **Interface**: `IBoard`
    ```java
    public interface IBoard {
        int getSize();
        List<IBoardEntity> getEntities();
        int applyEntities(int position);
    }
    ```
- **Implementation**:
    - `StandardBoard`: predefined snakes/ladders.
    - `CustomBoard`: custom entities placement.

### Design Pattern:
- **Factory**: A `BoardFactory` can create different types of boards:
    - Classic 10x10 with standard snakes/ladders.
    - Custom boards with traps, boosters, etc.

## Game (Orchestrator)
```java
public class Game {
    private final IBoard board;
    private final IDice dice;
    private final List<IPlayer> players;
    
    // Strategies
    private final IWinningStrategy winningStrategy;
    private final IMoveValidationStrategy moveValidationStrategy;
    private final IStartingStrategy startingStrategy;
    private final IKillingStrategy killingStrategy;
    private final IConsecutiveSixesStrategy consecutiveSixesStrategy;

    private int currentPlayerIndex;

    public void startGame();
    private void playTurn(IPlayer player);
}
```

### Design Pattern:
- **Facade**: Simplifies interaction with all components.

### Future Extensions:
- **Observer** (optional): Notify UI or logs on game events (player moves, wins, collisions).

## Game Builder / Config
To avoid a constructor with too many parameters, we can use the Builder Pattern.
Also We don’t want to hardcode defaults in Game. Use a Builder.

```java
public class GameBuilder {
    private IBoard board;
    private IDice dice = DiceFactory.defaultDice();
    private List<IPlayer> players = new ArrayList<>();

    private IWinningStrategy winningStrategy = GameConfig.defaultWinningStrategy();
    private IMoveValidationStrategy moveValidationStrategy = GameConfig.defaultMoveValidation();
    private IStartingStrategy startingStrategy = GameConfig.defaultStartingStrategy();
    private IKillingStrategy killingStrategy = GameConfig.defaultKillingStrategy();
    private IConsecutiveSixesStrategy consecutiveSixesStrategy = GameConfig.defaultConsecutiveSixesStrategy();

    public GameBuilder withBoard(IBoard board) { this.board = board; return this; }
    public GameBuilder withDice(IDice dice) { this.dice = dice; return this; }
    public GameBuilder withPlayers(List<IPlayer> players) { this.players = players; return this; }
    public GameBuilder withWinningStrategy(IWinningStrategy strategy) { this.winningStrategy = strategy; return this; }
    // ... similar for others

    public Game build() {
        return new Game(board, dice, players, winningStrategy,
                        moveValidationStrategy, startingStrategy,
                        killingStrategy, consecutiveSixesStrategy);
    }
}
```

## Game Stats
```java
public class GameStats {
    private String gameId;
    private List<IPlayer> players;
    private IPlayer winner;
}
```
