package search;

import java.util.*;

public class AhoCorasick {
    private static class Node {
        Map<Character, Integer> next = new HashMap<>();
        int fail = 0;
        List<String> output = new ArrayList<>();
    }

    private final List<Node> trie = new ArrayList<>();

    public AhoCorasick(List<String> patterns) {
        trie.add(new Node());
        for (String pattern : patterns) insert(pattern);
        buildFailureLinks();
    }

    private void insert(String pattern) {
        int node = 0;
        for (char c : pattern.toCharArray()) {
            Integer child = trie.get(node).next.get(c);
            if (child == null) {
                child = trie.size();
                trie.get(node).next.put(c, child);
                trie.add(new Node());
            }
            node = child;
        }
        trie.get(node).output.add(pattern);
    }

    private void buildFailureLinks() {
        Queue<Integer> queue = new ArrayDeque<>();

        for (int child : trie.get(0).next.values()) {
            trie.get(child).fail = 0;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            int current = queue.remove();

            for (Map.Entry<Character, Integer> entry : trie.get(current).next.entrySet()) {
                char c = entry.getKey();
                int child = entry.getValue();
                queue.add(child);

                int fallback = trie.get(current).fail;
                while (fallback != 0 && !trie.get(fallback).next.containsKey(c)) {
                    fallback = trie.get(fallback).fail;
                }

                if (trie.get(fallback).next.containsKey(c) &&
                    trie.get(fallback).next.get(c) != child) {
                    trie.get(child).fail = trie.get(fallback).next.get(c);
                } else {
                    trie.get(child).fail = 0;
                }

                trie.get(child).output.addAll(trie.get(trie.get(child).fail).output);
            }
        }
    }

    public Map<String, List<Integer>> search(String text) {
        Map<String, List<Integer>> result = new LinkedHashMap<>();
        int state = 0;

        for (String p : allPatterns()) result.put(p, new ArrayList<>());

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (state != 0 && !trie.get(state).next.containsKey(c)) {
                state = trie.get(state).fail;
            }

            if (trie.get(state).next.containsKey(c)) {
                state = trie.get(state).next.get(c);
            }

            for (String pattern : trie.get(state).output) {
                result.computeIfAbsent(pattern, k -> new ArrayList<>())
                      .add(i - pattern.length() + 1);
            }
        }
        return result;
    }

    private List<String> allPatterns() {
        Set<String> set = new LinkedHashSet<>();
        for (Node node : trie) set.addAll(node.output);
        return new ArrayList<>(set);
    }
}
