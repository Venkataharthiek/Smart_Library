package management;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Document> documents = new ArrayList<>();

    public void addDocument(Document document) {
        documents.add(document);
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void displayDocuments() {
        if (documents.isEmpty()) {
            System.out.println("No documents available.");
            return;
        }
        for (Document d : documents) {
            System.out.println(d);
        }
    }

    public Document findById(int id) {
        for (Document d : documents) {
            if (d.getId() == id) return d;
        }
        return null;
    }
}
