package search;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    private static final long BASE = 256;
    private static final long MOD = 1_000_000_007L;

    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length(), m = pattern.length();

        if (m == 0 || m > n) return result;

        long patternHash = 0;
        long windowHash = 0;
        long highPower = 1;

        for (int i = 0; i < m - 1; i++) {
            highPower = (highPower * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % MOD;
            windowHash = (windowHash * BASE + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == windowHash && equalsAt(text, pattern, i)) {
                result.add(i);
            }

            if (i < n - m) {
                windowHash = (windowHash - text.charAt(i) * highPower) % MOD;
                if (windowHash < 0) windowHash += MOD;
                windowHash = (windowHash * BASE + text.charAt(i + m)) % MOD;
            }
        }
        return result;
    }

    private static boolean equalsAt(String text, String pattern, int start) {
        for (int j = 0; j < pattern.length(); j++) {
            if (text.charAt(start + j) != pattern.charAt(j)) return false;
        }
        return true;
    }
}
