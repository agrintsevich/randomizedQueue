import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;


    public Deque() {
        this.first = null;
        this.last = null;
    }

    private class Node {
        Item value;
        Node prev;
        Node next;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(final Item item) {
        validateArgument(item);
        if (isEmpty()) {
            this.first = new Node();
            this.first.value = item;
            this.last = this.first;
        } else {
            final Node temp = this.first;
            this.first = new Node();
            this.first.next = temp;
            temp.prev = this.first;
            this.first.prev = null;
            this.first.value = item;
        }
        this.size++;
    }

    public void addLast(final Item item) {
        validateArgument(item);
        if (isEmpty()) {
            this.last = new Node();
            this.last.value = item;
            this.first = this.last;
        } else {
            final Node temp = this.last;
            this.last = new Node();
            this.last.prev = temp;
            temp.next = this.last;
            this.last.next = null;
            this.last.value = item;
        }
        this.size++;
    }


    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        final Node temp = this.first;
        final Node next = temp.next;
        this.first = next;
        if (next != null) {
            next.prev = null;
        }
        this.size--;
        return temp.value;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        final Node temp = this.last;
        final Node prev = temp.prev;
        if (prev == null) {
            this.last = this.first;
            this.first = null;
        } else {
            this.last = prev;
            prev.next = null;
        }
        this.size--;
        return temp.value;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = Deque.this.first;

        public boolean hasNext() {
            return this.current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            final Item item = this.current.value;
            this.current = this.current.next;
            return item;
        }
    }

    private void validateArgument(final Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null element");
    }

    public static void main(final String... args) {
        final Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.removeLast();
        deque.iterator().forEachRemaining(System.out::println);
    }

}
