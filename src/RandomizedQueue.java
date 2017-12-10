import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue = (Item[]) new Object[1];
    private int size = 0;

    public RandomizedQueue() {

    }


    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(final Item item) {
        validateArgument(item);
        if (this.size == this.queue.length)
            resize(this.queue.length * 2);
        this.queue[this.size++] = item;
    }


    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        final int index = StdRandom.uniform(this.size);
        final Item element = this.queue[index];
        this.queue[index] = this.queue[--this.size];
        this.queue[this.size] = null;
        if (this.size <= this.queue.length / 4)
            resize(this.queue.length / 2);

        return element;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        final int random = StdRandom.uniform(this.size);
        return this.queue[random];
    }

    public Iterator<Item> iterator() {
        return new RandomOrderIterator();
    }

    private class RandomOrderIterator implements Iterator<Item> {
        private final Item[] copiedQueue = RandomizedQueue.this.queue.clone();

        private int index = 0;

        public RandomOrderIterator() {
            StdRandom.shuffle(this.copiedQueue);
        }

        public boolean hasNext() {
            return this.index != RandomizedQueue.this.size - 1;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return this.copiedQueue[this.index++];
        }
    }

    private void validateArgument(final Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null element");
    }

    private void resize(final int newSize) {
        final Item[] newArray = Arrays.copyOfRange(this.queue, 0, newSize);
        this.queue = newArray;
    }


}