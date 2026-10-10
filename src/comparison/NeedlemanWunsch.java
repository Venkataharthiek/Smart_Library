package comparison;

public class NeedlemanWunsch {
    public static class Result {
        public final int score;
        public final String alignedA;
        public final String alignedB;

        public Result(int score, String alignedA, String alignedB) {
            this.score = score;
            this.alignedA = alignedA;
            this.alignedB = alignedB;
        }
    }

    public static Result align(String a, String b, int match, int mismatch, int gap) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) dp[i][0] = dp[i - 1][0] + gap;
        for (int j = 1; j <= m; j++) dp[0][j] = dp[0][j - 1] + gap;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int diag = dp[i - 1][j - 1] +
                        (a.charAt(i - 1) == b.charAt(j - 1) ? match : mismatch);
                int up = dp[i - 1][j] + gap;
                int left = dp[i][j - 1] + gap;
                dp[i][j] = Math.max(diag, Math.max(up, left));
            }
        }

        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        int i = n, j = m;

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0) {
                int score = a.charAt(i - 1) == b.charAt(j - 1) ? match : mismatch;
                if (dp[i][j] == dp[i - 1][j - 1] + score) {
                    x.append(a.charAt(i - 1));
                    y.append(b.charAt(j - 1));
                    i--; j--;
                    continue;
                }
            }
            if (i > 0 && dp[i][j] == dp[i - 1][j] + gap) {
                x.append(a.charAt(i - 1));
                y.append('-');
                i--;
            } else {
                x.append('-');
                y.append(b.charAt(j - 1));
                j--;
            }
        }

        return new Result(dp[n][m], x.reverse().toString(), y.reverse().toString());
    }
}
