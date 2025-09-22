import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BoardEntity.IBoardEntity;

public class Board {
    private final int size;
    private final int startCell;
    private final int endCell;
    private final Map<Integer, IBoardEntity> entities;

    public Board(int size) {
        this.size = size;
        this.startCell = 1;
        this.endCell = size * size;
        this.entities = new HashMap<>();
    }

    public void placeEntities(List<IBoardEntity> entityList) {
        // Validation and placement logic
        // 1. Check for overlapping start/end positions
        // 2. No entities on startCell or endCell
        // 3. No cycles (e.g., a ladder's top is a snake's head)
        // 4. No two entities start at the same position
        // ...
        for (IBoardEntity entity : entityList) {
            entities.put(entity.getStart(), entity);
        }
    }

    public IBoardEntity getEntity(int position) {
        return entities.get(position);
    }

    public int getEndCell() {
        return this.endCell;
    }
}