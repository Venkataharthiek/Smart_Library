package management;

public class Document {
    private final int id;
    private final String type;
    private final String title;
    private final String author;
    private final String content;

    public Document(int id, String type, String title, String author, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return "[" + id + "] " + type + " | " + title + " | " + author;
    }
}
