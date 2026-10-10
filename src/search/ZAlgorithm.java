package search;

import java.util.ArrayList;
import java.util.List;

public class ZAlgorithm {
    public static int[] buildZ(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);

            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern.length() == 0) return result;

        char separator = '\u0000';
        String combined = pattern + separator + text;
        int[] z = buildZ(combined);

        for (int i = pattern.length() + 1; i < combined.length(); i++) {
            if (z[i] >= pattern.length()) {
                result.add(i - pattern.length() - 1);
            }
        }
        return result;
    }
}
