package BoardEntity;

public class Ladder implements IBoardEntity {
    private final int bottom; // start
    private final int top;    // end (top > bottom)

    public Ladder(int bottom, int top) {
        if (top <= bottom) throw new IllegalArgumentException("top must be > bottom for ladder");
        this.bottom = bottom;
        this.top = top;
    }

    @Override public int getStart() { return bottom; }
    @Override public int getEnd() { return top; }

    @Override
    public int applyEffect(int currentPosition) {
        return currentPosition == bottom ? top : currentPosition;
    }
}
