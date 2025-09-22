# Requirements Gathering

## Board
- N x N square grid (e.g., 8x8, 10x10)
- Configurable size (minimum 10×10, no fixed upper limit)
- First cell = **Start**, last cell = **End**.

## Board Entities
- **Snake**: moves player down.
- **Ladder**: moves player up.
- Possible future extensions: traps, boosts, black holes, teleporters, etc.

## Board Entity Placement
- Snake & Ladder cannot overlap at same start/end.
- No cycles (e.g., ladder → snake → ladder back).
- No two snakes or ladders start at the same position.
- No horizontal snakes/ladders (only vertical and diagonal allowed).
- For simplicity, no snakes/ladders on start (1) or end (N×N).
- For Now, the computer will place the snakes and ladders as per the above rules. In future, we can allow custom placement.

## Players
- Support **2+ players**.
- Players can be human or bot.

## Dice
- Default: 6-sided random dice.
- Extendable: custom dice (e.g., 8-sided, weighted, multiple dice).

## Strategies / Rules
1. **Winning Strategy**:
    - Winner is first to **reach/cross** last cell.
    - Two modes:
        - **Exact end required**: must land exactly on last cell. (classic, default)
        - **Overshoot allowed**: can exceed last cell to win.

2. **Move Validation Strategy**:
    - If overshoot occurs:
        - **No move**: stay at current position. (classic, default)
        - **Exceed the end**: move forward anyway and win.

3. **Starting Strategy**:
    - Initial position: all players start at cell 0. (classic, default)
    - To start moving from cell 0:
        - **Any roll**: start on any number. (classic, default)
        - **Specific roll**: must roll a 1 or 6 to start.

4. **Killing Strategy**:
    - Landing on occupied cell:
        - **Send opponent back**: opponent returns to start.
        - **No effect**: nothing happens. (classic, default)
        - **Self sent back**: current player returns to start.
        - **Both sent back**: both players return to start.

5. **Special Rolls Strategy**:
    This strategy defines what happens when a player rolls certain **special dice values** (e.g., 6 in the classic version, or 1).
    - **Extra turn**: player gets another roll. (classic, default)
    - **No extra turn**: treat special roll as a normal roll, no extra chance.

5. **Consecutive Sixes Strategy**:

    This strategy has **two layers of rules**:

    a. **First Six Rule**:  
    Decides what happens when a player rolls a six:  
    - **Extra turn**: player gets another roll. (classic, default)  
    - **No extra turn**: treat six as a normal roll, no extra chance.  

    b. **Third Six Rule**:  
    If a player rolls **three consecutive sixes** (only applicable if the "Extra turn" rule is enabled), decide what happens on the third roll:  
    - **Cancel third move**: the third 6 is ignored, player does not move, and the turn ends. (classic, default)  
    - **Treat as normal**: allow the third 6 as a normal move, and possibly grant another turn.  
    - **Lose next turn**: player’s upcoming turn is skipped.  
    - **Backtrack**: player moves back to the position where the sequence of sixes started.  
    - **Sent back to start**: player is sent back to the starting cell (0).  

    ### Notes:
    - The **First Six Rule** always applies whenever a 6 is rolled.  
    - The **Third Six Rule** only applies when a player rolls three consecutive sixes in a single turn.  
    - Both rules are **pluggable** (can be swapped/extended), so we can easily support custom behavior beyond the classic version.



### Assumptions:

There might be multiple priority orders. <br>
But in the classic version, we will follow this order:

1. **Starting Strategy** → Check if player is allowed to move (e.g., needs a 6).
2. **Dice Roll** → Get the number.
3. **Move Validation Strategy** → Decide how to handle overshoot.
4. **Apply Movement** → Move player to new position.
5. **Entity Strategy** → Apply ladder/snake effects.
6. **Winning Strategy** → Check if player has won.
7. **Killing Strategy** → (ignored in classic, but could go here).
8. **Consecutive Sixes Strategy** → Handle extra turns / cancel turn.

## Game Stats
- For Simplicity, we are storing the stats in-memory in the following format:
    - Game ID
    - Players List
    - Winner
- Future: can extend to use database or file storage.

## User Journey

1. **Game Setup**
    - User chooses board size (default: 10×10).
    - Snakes and ladders are auto-placed (future: custom placement).
    - Players are added (minimum 2). Each player selects type (human or bot).

2. **Game Start**
    - Players begin at cell 0.
    - Turn order is decided randomly (future: customizable).

3. **Player Turn**
    - Active player rolls the dice.
    - Apply **Starting Strategy** (e.g., require a 6 to begin).
    - Apply **Move Validation Strategy** (e.g., check overshoot).
    - Player moves to new position.
    - Apply **Entity Strategy** (e.g., snake → down, ladder → up).
    - Apply **Winning Strategy** (check if player reached/crossed end).
    - Apply **Killing Strategy** (if another player occupies the same cell).
    - Apply **Consecutive Sixes Strategy** (e.g., extra roll or skip).

4. **Turn Transition**
    - If an extra turn is granted (by rolling a 6), the same player rolls again.
    - Otherwise, control passes to the next player.

5. **Game End**
    - First player to meet the **Winning Strategy** condition is declared the winner.
    - Game stats (Game ID, Players List, Winner, Moves History) are recorded.
    - Future: support replay, analytics, or persistent storage.
