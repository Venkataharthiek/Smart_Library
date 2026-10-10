import comparison.Levenshtein;
import comparison.NeedlemanWunsch;
import comparison.SmithWaterman;
import indexing.KasaiLCP;
import indexing.SuffixArray;
import management.Document;
import management.Library;
import resource.BitmaskDP;
import search.AhoCorasick;
import search.KMP;
import search.NaiveSearch;
import search.RabinKarp;
import search.ZAlgorithm;

import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        seedDocuments();

        while (true) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> library.displayDocuments();
                case 2 -> addDocument();
                case 3 -> stringSearchMenu();
                case 4 -> ahoCorasickDemo();
                case 5 -> suffixDemo();
                case 6 -> dpComparisonMenu();
                case 7 -> bitmaskDemo();
                case 0 -> {
                    System.out.println("Exiting SmartLibrary.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== SMART LIBRARY ==========");
        System.out.println("1. Display Documents");
        System.out.println("2. Add Document");
        System.out.println("3. Single Keyword Search");
        System.out.println("4. Aho-Corasick Multiple Keyword Search");
        System.out.println("5. Suffix Array + LCP");
        System.out.println("6. Document/String Comparison (DP)");
        System.out.println("7. Bitmask Resource Selection");
        System.out.println("0. Exit");
    }

    private static void seedDocuments() {
        library.addDocument(new Document(
            1, "Research Paper", "Introduction to Machine Learning",
            "A. Kumar",
            "Machine learning is a branch of artificial intelligence. " +
            "Machine learning algorithms are widely used in research."
        ));
        library.addDocument(new Document(
            2, "Journal", "Data Structures for Research",
            "B. Rao",
            "Efficient data structures and algorithms improve document search and indexing."
        ));
        library.addDocument(new Document(
            3, "Book", "Algorithms in Java",
            "C. Sharma",
            "String algorithms, dynamic programming and graph algorithms are important topics."
        ));
    }

    private static void addDocument() {
        int id = readInt("ID: ");
        System.out.print("Type: ");
        String type = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Content: ");
        String content = sc.nextLine();

        library.addDocument(new Document(id, type, title, author, content));
        System.out.println("Document added.");
    }

    private static String getDocumentContent() {
        int id = readInt("Enter document ID: ");
        Document d = library.findById(id);
        if (d == null) {
            System.out.println("Document not found.");
            return null;
        }
        return d.getContent();
    }

    private static void stringSearchMenu() {
        String text = getDocumentContent();
        if (text == null) return;

        System.out.print("Enter keyword: ");
        String pattern = sc.nextLine();

        System.out.println("1. Naive");
        System.out.println("2. KMP");
        System.out.println("3. Z Algorithm");
        System.out.println("4. Rabin-Karp");
        int choice = readInt("Algorithm: ");

        List<Integer> result;
        switch (choice) {
            case 1 -> result = NaiveSearch.search(text.toLowerCase(), pattern.toLowerCase());
            case 2 -> result = KMP.search(text.toLowerCase(), pattern.toLowerCase());
            case 3 -> result = ZAlgorithm.search(text.toLowerCase(), pattern.toLowerCase());
            case 4 -> result = RabinKarp.search(text.toLowerCase(), pattern.toLowerCase());
            default -> {
                System.out.println("Invalid algorithm.");
                return;
            }
        }

        System.out.println("Match positions (0-based): " + result);
        System.out.println("Number of matches: " + result.size());
    }

    private static void ahoCorasickDemo() {
        String text = getDocumentContent();
        if (text == null) return;

        System.out.print("Enter keywords separated by comma: ");
        String[] parts = sc.nextLine().toLowerCase().split(",");
        List<String> patterns = new ArrayList<>();

        for (String p : parts) {
            p = p.trim();
            if (!p.isEmpty()) patterns.add(p);
        }

        AhoCorasick ac = new AhoCorasick(patterns);
        Map<String, List<Integer>> result = ac.search(text.toLowerCase());

        for (Map.Entry<String, List<Integer>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    private static void suffixDemo() {
        String text = getDocumentContent();
        if (text == null) return;

        System.out.print("Enter short text for suffix demonstration: ");
        String input = sc.nextLine();
        if (input.isEmpty()) input = text;

        int[] sa = SuffixArray.build(input);
        int[] lcp = KasaiLCP.build(input, sa);

        System.out.println("Suffix Array:");
        for (int i = 0; i < sa.length; i++) {
            System.out.printf("%d -> %s%n", sa[i], SuffixArray.suffix(input, sa[i]));
        }

        System.out.println("LCP Array: " + Arrays.toString(lcp));
    }

    private static void dpComparisonMenu() {
        System.out.print("String/Document A: ");
        String a = sc.nextLine();
        System.out.print("String/Document B: ");
        String b = sc.nextLine();

        System.out.println("Levenshtein Distance = " +
                Levenshtein.distance(a, b));

        NeedlemanWunsch.Result nw =
                NeedlemanWunsch.align(a, b, 2, -1, -2);
        System.out.println("\nNeedleman-Wunsch score = " + nw.score);
        System.out.println(nw.alignedA);
        System.out.println(nw.alignedB);

        SmithWaterman.Result sw =
                SmithWaterman.align(a, b, 2, -1, -2);
        System.out.println("\nSmith-Waterman local score = " + sw.score);
        System.out.println(sw.localA);
        System.out.println(sw.localB);
    }

    private static void bitmaskDemo() {
        BitmaskDP.Resource[] resources = {
            new BitmaskDP.Resource("Research Paper A", 4, 8),
            new BitmaskDP.Resource("Journal B", 5, 9),
            new BitmaskDP.Resource("Book C", 3, 6),
            new BitmaskDP.Resource("Research Paper D", 2, 4)
        };

        int budget = 8;
        BitmaskDP.Result result = BitmaskDP.select(resources, budget);

        System.out.println("Budget = " + budget);
        System.out.println("Maximum value = " + result.maxValue);
        System.out.println("Total cost = " + result.totalCost);
        System.out.println("Selected resources = " + result.selected);
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }
}
