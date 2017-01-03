import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;
 // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) 
            copy[i] = queue[i];
       queue = copy;
    }
 // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
 // return the number of items on the queue
    public int size() {
        return size;
    }
 // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (size == queue.length) resize(2 * queue.length);
        queue[size++] = item;
    }
 // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int rand = StdRandom.uniform(size);
        Item item = queue[rand];
        queue[rand] = queue[size-1];
        queue[size-1] = null;
        size--;
        if (size > 0 && size == queue.length/4) resize(queue.length/2);
        return item;
    }
    
 // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int rand = StdRandom.uniform(size);
        return queue[rand];
    }
 // return an independent iterator over items in random order
    public Iterator<Item> iterator()  {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int newSize;
        private Item[] newQueue;
        
        public RandomizedQueueIterator() {
            newSize = size;
            newQueue = (Item[]) new Object[newSize];
            for (int i = 0; i < newSize; i++) 
                newQueue[i] = queue[i];
        }
        
        public boolean hasNext() {
            return newSize > 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int rand = StdRandom.uniform(newSize);
            Item item = newQueue[rand];
            newQueue[rand] = newQueue[newSize-1];
            newQueue[newSize-1] = null;
            newSize--;
            return item;
        }
    }
    // unit testing
    public static void main(String[] args) {
        
    }
}