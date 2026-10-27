package restaurantrush.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Holds the numbers that define one game setting (brief section 2.1).
 * Immutable: once created, nothing can change it, so every run with the
 * same config is reproducible.
 */
public class GameConfig {

    private final int totalTurns;
    private final int tableCount;
    private final List<Integer> arrivalTurns;
    private final long seed;

    private GameConfig(int totalTurns, int tableCount, List<Integer> arrivalTurns, long seed) {
        this.totalTurns = totalTurns;
        this.tableCount = tableCount;
        this.arrivalTurns = Collections.unmodifiableList(new ArrayList<>(arrivalTurns));
        this.seed = seed;
    }

    /** The required base setting: 18 turns, 2 tables, arrivals on 1, 4, 7, 10, 13, 16. */
    public static GameConfig baseSetting() {
        return new GameConfig(18, 2, Arrays.asList(1, 4, 7, 10, 13, 16), 2002L);
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public int getTableCount() {
        return tableCount;
    }

    public List<Integer> getArrivalTurns() {
        return arrivalTurns;
    }

    public long getSeed() {
        return seed;
    }
}
