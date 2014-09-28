import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by allenc289 on 9/27/14.
 */
public class RandomizedQueueTests {
    private class Item {
        public Item(int index) {
            this.index = index;
        }

        private int index = 0;

        public int getIndex() {
            return index;
        }
    }

    @Test(expected = NullPointerException.class)
    public void testenqueueNullItem() {
        new RandomizedQueue<Item>().enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueFromEmptyDeque() {
        new RandomizedQueue<Item>().dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void testSampleFromEmptyDeque() {
        new RandomizedQueue<Item>().sample();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveFromIterator() {
        new RandomizedQueue<Item>().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextFromEmptyIterator() {
        new RandomizedQueue<Item>().iterator().next();
    }

    @Test
    public void enqueueOne() {
        RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<Item>();
        randomizedQueue.enqueue(new Item(2));

        assert (!randomizedQueue.isEmpty());

        Item item = randomizedQueue.sample();
        assert (item.getIndex() == 2);
    }


    @Test
    public void enqueueMultiple() {
        RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<Item>();
        for (int i = 0; i < 50; i++) {
            randomizedQueue.enqueue(new Item(i));
        }
        assertEquals(50, randomizedQueue.size());

        Iterator<Item> iterator = randomizedQueue.iterator();
        for (int i = 0; i < 50; i++) {
            assertTrue("Iteration: " + i, iterator.hasNext());

            Item item = iterator.next();
            assertTrue(Integer.toString(item.getIndex()), item.getIndex() >= 0);
        }
        assert (!iterator.hasNext());
    }

    @Test
    public void dequeueOne() {
        RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<Item>();
        randomizedQueue.enqueue(new Item(2));

        assert (!randomizedQueue.isEmpty());

        Item item = randomizedQueue.dequeue();
        assert (item.getIndex() == 2);

        assert (randomizedQueue.isEmpty());
    }

    @Test
    public void dequeueMultiple() {
        RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<Item>();
        for (int i = 0; i < 50; i++) {
            randomizedQueue.enqueue(new Item(i));
        }
        assertEquals(50, randomizedQueue.size());

        for (int i = 0; i < 50; i++) {
            Item item = randomizedQueue.dequeue();
            assertTrue(Integer.toString(item.getIndex()), item.getIndex() >= 0);
        }

        assertEquals(0, randomizedQueue.size());
        assert(randomizedQueue.isEmpty());
    }

    @Test
    public void multipleIndependentIterators() {
        RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<Item>();
        for (int i = 0; i < 50; i++) {
            randomizedQueue.enqueue(new Item(i));
        }
        assertEquals(50, randomizedQueue.size());

        boolean differentIteration = false;
        Iterator<Item> iterator1 = randomizedQueue.iterator();
        Iterator<Item> iterator2 = randomizedQueue.iterator();

        for (int i = 0; i < 50; i++) {
            assertTrue("Iteration: " + i, iterator1.hasNext());
            assertTrue("Iteration: " + i, iterator2.hasNext());

            Item item1 = iterator1.next();
            assertTrue(Integer.toString(item1.getIndex()), item1.getIndex() >= 0);

            Item item2 = iterator2.next();
            assertTrue(Integer.toString(item2.getIndex()), item2.getIndex() >= 0);

            if (item1.getIndex() != item2.getIndex()) differentIteration = true;
        }
        assert (!iterator1.hasNext());
        assert (!iterator2.hasNext());
        assertTrue(differentIteration);
    }
}
