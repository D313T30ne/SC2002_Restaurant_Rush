package restaurantrush.control;

import restaurantrush.entity.customer.CriticCustomer;
import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.RegularCustomer;
import restaurantrush.entity.customer.VIPCustomer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Holds the numbers that define one game setting (brief section 2.1).
 * Immutable: once created, nothing can change it, so every run with the
 * same config is reproducible.
 */
public class GameConfig {

    private final int totalTurns;
    private final int tableCount;
    private final List<Integer> arrivalTurns;
    private final List<IntFunction<Customer>> arrivalPlan;
    private final long seed;

    private GameConfig(int totalTurns, int tableCount, List<Integer> arrivalTurns, List<IntFunction<Customer>> arrivalPlan, long seed) {
        if (arrivalPlan.size() != arrivalTurns.size()) {
            throw new IllegalArgumentException(
                    "Arrival plan needs one entry per arrival turn: expected "
                            + arrivalTurns.size() + " but got " + arrivalPlan.size());
        }
        this.arrivalPlan = Collections.unmodifiableList(new ArrayList<>(arrivalPlan));
        this.totalTurns = totalTurns;
        this.tableCount = tableCount;
        this.arrivalTurns = Collections.unmodifiableList(new ArrayList<>(arrivalTurns));
        this.seed = seed;
    }

    /** The required base setting: 18 turns, 2 tables, arrivals on 1, 4, 7, 10, 13, 16. */
//    public static GameConfig baseSetting() {
//        return new GameConfig(18, 2, Arrays.asList(1, 4, 7, 10, 13, 16), 2002L);
//    }

    /** Stage 2 - Config **/
    public static GameConfig baseSetting() {
        return new GameConfig(18, 2, Arrays.asList(1, 4, 7, 10, 13, 16),
                Arrays.<IntFunction<Customer>>asList(
                        RegularCustomer::new,   // turn 1
                        VIPCustomer::new,       // turn 4
                        RegularCustomer::new,   // turn 7
                        CriticCustomer::new,    // turn 10
                        RegularCustomer::new,   // turn 13
                        RegularCustomer::new),  // turn 16
                2002L);
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

    /** One customer factory per arrival turn, in the same order as getArrivalTurns(). */
    public List<IntFunction<Customer>> getArrivalPlan() {
        return arrivalPlan;
    }

    public long getSeed() {
        return seed;
    }
}
