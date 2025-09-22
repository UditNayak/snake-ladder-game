package BoardEntity;

public interface IBoardEntity {
    int getStart();
    int getEnd();
    /*
     * Apply effect: returns the new position after applying entity.
     * Usually either start->end mapping.
     */
    int applyEffect(int currentPosition);
}