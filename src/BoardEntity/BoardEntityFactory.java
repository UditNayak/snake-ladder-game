package BoardEntity;

public class BoardEntityFactory {
    public static IBoardEntity createSnake(int head, int tail) {
        return new Snake(head, tail);
    }

    public static IBoardEntity createLadder(int bottom, int top) {
        return new Ladder(bottom, top);
    }

    // Future: could add more entity types here
    public static IBoardEntity createOtherEntity(/* parameters */) {
        // return new OtherEntity(...);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    
}
