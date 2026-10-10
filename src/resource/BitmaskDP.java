package resource;

import java.util.ArrayList;
import java.util.List;

public class BitmaskDP {
    public static class Resource {
        public final String name;
        public final int cost;
        public final int value;

        public Resource(String name, int cost, int value) {
            this.name = name;
            this.cost = cost;
            this.value = value;
        }
    }

    public static class Result {
        public final int maxValue;
        public final int totalCost;
        public final List<String> selected;

        public Result(int maxValue, int totalCost, List<String> selected) {
            this.maxValue = maxValue;
            this.totalCost = totalCost;
            this.selected = selected;
        }
    }

    // Exhaustive bitmask DP/enumeration for a small resource-selection problem.
    public static Result select(Resource[] resources, int budget) {
        int n = resources.length;
        int totalMasks = 1 << n;
        int[] dp = new int[totalMasks];
        int bestMask = 0;
        int bestValue = 0;
        int bestCost = 0;

        for (int mask = 0; mask < totalMasks; mask++) {
            int cost = 0;
            int value = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    cost += resources[i].cost;
                    value += resources[i].value;
                }
            }

            if (cost <= budget && value > bestValue) {
                bestValue = value;
                bestCost = cost;
                bestMask = mask;
            }
            dp[mask] = value;
        }

        List<String> selected = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) selected.add(resources[i].name);
        }

        return new Result(bestValue, bestCost, selected);
    }
}
