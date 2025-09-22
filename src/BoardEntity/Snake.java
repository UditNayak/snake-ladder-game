package BoardEntity;

public class Snake implements IBoardEntity {
    private final int head; // start
    private final int tail; // end (tail < head)

    public Snake(int head, int tail) {
        if (tail >= head) throw new IllegalArgumentException("tail must be < head for snake");
        this.head = head;
        this.tail = tail;
    }

    @Override public int getStart() { return head; }
    @Override public int getEnd() { return tail; }

    @Override
    public int applyEffect(int currentPosition) {
        return currentPosition == head ? tail : currentPosition;
    }
}