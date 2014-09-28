import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by allenc289 on 9/27/14.
 */
public class DequeTests {
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
    public void testAddFirstNullItem() {
        new Deque<Item>().addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddLastNullItem() {
        new Deque<Item>().addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstFromEmptyDeque() {
        new Deque<Item>().removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastFromEmptyDeque() {
        new Deque<Item>().removeLast();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveFromIterator() {
        new Deque<Item>().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextFromEmptyIterator() {
        new Deque<Item>().iterator().next();
    }

    @Test
    public void addFirstOne() {
        Deque<Item> deque = new Deque();
        deque.addFirst(new Item(2));

        assert (!deque.isEmpty());

        Iterator<Item> iterator = deque.iterator();
        assert (iterator.hasNext());
        assert (iterator.next().getIndex() == 2);
        assert (!iterator.hasNext());
    }

    @Test
    public void addFirstMultiple() {
        Deque<Item> deque = new Deque();
        for (int i = 49; i >= 0; i--) {
            deque.addFirst(new Item(i));
        }
        assertEquals(50, deque.size());

        Iterator<Item> iterator = deque.iterator();
        for (int i = 0; i < 50; i++) {
            assertTrue("Iteration: " + i, iterator.hasNext());
            assertEquals(i, iterator.next().getIndex());
        }
        assert (!iterator.hasNext());
    }

    @Test
    public void addLastOne() {
        Deque<Item> deque = new Deque();
        deque.addLast(new Item(2));

        assert (!deque.isEmpty());

        Iterator<Item> iterator = deque.iterator();
        assert (iterator.hasNext());
        assertEquals(2, iterator.next().getIndex());
        assert (!iterator.hasNext());
    }

    @Test
    public void addLastMultiple() {
        Deque<Item> deque = new Deque();
        for (int i = 0; i < 50; i++) {
            deque.addLast(new Item(i));
        }
        assertEquals(50, deque.size());

        Iterator<Item> iterator = deque.iterator();
        for (int i = 0; i < 50; i++) {
            assertTrue("Iteration: " + i, iterator.hasNext());
            assertEquals(i, iterator.next().getIndex());
        }

        assert (!iterator.hasNext());
    }

    @Test
    public void removeFirstOne() {
        Deque<Item> deque = new Deque();
        deque.addFirst(new Item(2));

        assert (!deque.isEmpty());
        Item item = deque.removeFirst();
        assertNotEquals(null, item);
        assertEquals(2, item.getIndex());
    }

    @Test
    public void removeFirstMultiple() {
        Deque<Item> deque = new Deque();
        for (int i = 0; i < 50; i++) {
            deque.addLast(new Item(i));
        }
        assertEquals(50, deque.size());

        for (int i = 0; i < 50; i++) {
            Item item = deque.removeFirst();
            assertNotEquals(null, item);
            assertEquals(i, item.getIndex());
        }

        assert (deque.isEmpty());
    }

    @Test
    public void removeLastOne() {
        Deque<Item> deque = new Deque();
        deque.addFirst(new Item(2));

        assert (!deque.isEmpty());
        Item item = deque.removeLast();
        assertNotEquals(null, item);
        assertEquals(2, item.getIndex());
    }

    @Test
    public void removeLastMultiple() {
        Deque<Item> deque = new Deque();
        for (int i = 0; i < 50; i++) {
            deque.addFirst(new Item(i));
        }
        assertEquals(50, deque.size());

        for (int i = 0; i < 50; i++) {
            Item item = deque.removeLast();
            assertNotEquals(null, item);
            assertEquals(i, item.getIndex());
        }

        assert (deque.isEmpty());
    }

    @Test
    public void addRemoveAdd() {
        Deque<Item> deque = new Deque();
        for (int i = 0; i < 10; i++) {
            deque.addFirst(new Item(i));
        }
        assertEquals(10, deque.size());

        for (int i = 0; i < 10; i++) {
            Item item = deque.removeLast();
            assertNotEquals(null, item);
            assertEquals(i, item.getIndex());
        }

        assert (deque.isEmpty());

        deque.addFirst(new Item(2));
        assert (!deque.isEmpty());

        Iterator<Item> iterator = deque.iterator();
        assert (iterator.hasNext());
        assert (iterator.next().getIndex() == 2);
        assert (!iterator.hasNext());
    }

    @Test
    public void randomTest() {
        //   500 random calls (p1 = 0.4, p2 = 0.4, p3 = 0.1, p4 = 0.1)
        StringBuilder sb = new StringBuilder();
        Deque<Item> deque = new Deque();
        for (int i = 0; i < 500; i++) {
            int random = StdRandom.uniform(10);

            if (random <= 3) deque.addFirst(new Item(i));
            else if (random <= 7) deque.addLast(new Item(i));
            else if (random <= 8) deque.removeFirst();
            else deque.removeLast();

            if (random <= 3) sb.append("addFirst" + "\n");
            else if (random <= 7) sb.append("addLast" + "\n");
            else if (random <= 8) sb.append("removeFirst" + "\n");
            else sb.append("removeLast" + "\n");

            if (deque.size() > 0) assertTrue(deque.size() + "\n" + sb.toString(), !deque.isEmpty());
        }
    }
}
