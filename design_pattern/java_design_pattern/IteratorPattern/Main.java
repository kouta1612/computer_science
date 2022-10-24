package IteratorPattern;

public class Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf();
        bookShelf.appendBook(new Book("apple"));
        bookShelf.appendBook(new Book("banana"));
        bookShelf.appendBook(new Book("orange"));
        Iterator it = new BookShelfIterator(bookShelf);
        while (it.hasNext()) {
            Book book = (Book) it.next();
            System.out.println(book.getName());
        }
    }
}