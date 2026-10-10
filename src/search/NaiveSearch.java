package search;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {
    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern.length() == 0) return result;

        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length() && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) result.add(i);
        }
        return result;
    }
}
