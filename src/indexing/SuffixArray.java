package indexing;

import java.util.Arrays;

public class SuffixArray {
    // Simple suffix-array construction for a Review-1 demonstration.
    // It sorts suffix starting positions directly.
    public static int[] build(String text) {
        Integer[] suffixes = new Integer[text.length()];
        for (int i = 0; i < text.length(); i++) suffixes[i] = i;

        Arrays.sort(suffixes, (a, b) -> {
            int i = a, j = b;
            while (i < text.length() && j < text.length()) {
                char ca = text.charAt(i);
                char cb = text.charAt(j);
                if (ca != cb) return Character.compare(ca, cb);
                i++;
                j++;
            }
            return Integer.compare(text.length() - a, text.length() - b);
        });

        int[] sa = new int[text.length()];
        for (int i = 0; i < suffixes.length; i++) sa[i] = suffixes[i];
        return sa;
    }

    public static String suffix(String text, int start) {
        return text.substring(start);
    }
}
