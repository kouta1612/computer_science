package IteratorPattern;

public class BookShelfIterator implements Iterator {
    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    public boolean hasNext() {
        if (this.index < this.bookShelf.getLength()) {
            return true;
        }
        return false;
    }

    public Object next() {
        Book book = this.bookShelf.getBook(this.index);
        this.index++;
        return book;
    }
}