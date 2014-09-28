import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by allenc289 on 9/27/14.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queueItems;         // array of queueItems (to allow for randomized access)
    private int queueSize;             // number of elements on queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        queueItems = (Item[]) new Object[2];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return queueSize == 0;
    }

    // return the number of queueItems on the queue
    public int size() {
        return queueSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        if (queueSize == queueItems.length) resize(2 * queueItems.length);    // double size of array if necessary
        queueItems[queueSize++] = item;
    }

    // delete and return queueItems random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        // Pick a random position and delete the item
        // After deleting, move last item into position picked so the array resizing can happen
        // That swap is necessary and harmless (as the queue is randomized anyway)
        int randomPosition = StdRandom.uniform(queueSize);
        Item item = queueItems[randomPosition];
        queueItems[randomPosition] = queueItems[queueSize -1];         // move last item to position just deleted
        queueItems[queueSize - 1] = null;                              // to avoid loitering
        queueSize--;

        // shrink size of array if necessary
        if (queueSize > 0 && queueSize == queueItems.length / 4) resize(queueItems.length / 2);

        return item;
    }

    // return (but do not delete) queueItems random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomPosition = StdRandom.uniform(queueSize);
        return queueItems[randomPosition];
    }

    // return an independent iterator over queueItems in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(this.queueItems, size());
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private final Item[] randomizedItems;
        private int currentPosition;

        public RandomizedQueueIterator(final Item[] queueItems, final int queueSize) {
            this.randomizedItems = queueItems.clone();
            if (queueSize > 0) StdRandom.shuffle(randomizedItems, 0, queueSize - 1);
            currentPosition = queueSize - 1;
        }

        @Override
        public boolean hasNext() {
            return currentPosition >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return randomizedItems[currentPosition--];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= queueSize;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < queueSize; i++) {
            temp[i] = queueItems[i];
        }
        queueItems = temp;
    }
}