package comparison;

public class SmithWaterman {
    public static class Result {
        public final int score;
        public final String localA;
        public final String localB;

        public Result(int score, String localA, String localB) {
            this.score = score;
            this.localA = localA;
            this.localB = localB;
        }
    }

    public static Result align(String a, String b, int match, int mismatch, int gap) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        int best = 0, bestI = 0, bestJ = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int diag = dp[i - 1][j - 1] +
                        (a.charAt(i - 1) == b.charAt(j - 1) ? match : mismatch);
                int up = dp[i - 1][j] + gap;
                int left = dp[i][j - 1] + gap;

                dp[i][j] = Math.max(0, Math.max(diag, Math.max(up, left)));

                if (dp[i][j] > best) {
                    best = dp[i][j];
                    bestI = i;
                    bestJ = j;
                }
            }
        }

        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        int i = bestI, j = bestJ;

        while (i > 0 && j > 0 && dp[i][j] > 0) {
            int score = a.charAt(i - 1) == b.charAt(j - 1) ? match : mismatch;
            if (dp[i][j] == dp[i - 1][j - 1] + score) {
                x.append(a.charAt(i - 1));
                y.append(b.charAt(j - 1));
                i--; j--;
            } else if (dp[i][j] == dp[i - 1][j] + gap) {
                x.append(a.charAt(i - 1));
                y.append('-');
                i--;
            } else {
                x.append('-');
                y.append(b.charAt(j - 1));
                j--;
            }
        }

        return new Result(best, x.reverse().toString(), y.reverse().toString());
    }
}
