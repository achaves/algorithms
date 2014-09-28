import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by allenc289 on 9/27/14.
 */
public class Deque<Item> implements Iterable<Item> {
    // LinkedList node
    private class Node<Item> {
        private Item item;
        private Node nextNode;
        private Node priorNode;

        public Node(Item item, Node next, Node prior) {
            this.item = item;
            this.nextNode = next;
            this.priorNode = prior;
        }

        public Item getItem() {
            return item;
        }

        public Node getNext() {
            return nextNode;
        }

        public void setNext(Node next) {
            this.nextNode = next;
        }

        public Node getPrior() {
            return priorNode;
        }

        public void setPrior(Node prior) {
            this.priorNode = prior;
        }

        public void cleanReferences() {
            item = null;
            nextNode = null;
            priorNode = null;
        }
    }

    // LinkedList
    private Node firstNode;
    private Node lastNode;
    private int numberOfNodes;

    // construct an empty deque
    public Deque() {
        assert check();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return firstNode == null;
    }

    // return the number of items on the deque
    public int size() {
        return numberOfNodes;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();

        numberOfNodes++;
        Node nodeToAdd = new Node(item, firstNode, prior(firstNode));
        if (firstNode != null) {
            firstNode.setPrior(nodeToAdd);
        }
        if (numberOfNodes == 1) {
            lastNode = nodeToAdd;
        }
        firstNode = nodeToAdd;

        assert check();
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();

        numberOfNodes++;
        Node nodeToAdd = new Node(item, null, lastNode);

        if (lastNode != null) {
            lastNode.setNext(nodeToAdd);
        }
        if (numberOfNodes == 1) {
            firstNode = nodeToAdd;
        }
        lastNode = nodeToAdd;
        assert check();
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        numberOfNodes--;
        Node nodeToRemove = firstNode;

        firstNode = nodeToRemove.getNext();
        if (firstNode != null) {
            firstNode.setPrior(null);
        }
        if (numberOfNodes <= 1) {
            lastNode = firstNode;
        }

        Item item = (Item) nodeToRemove.getItem();
        nodeToRemove.cleanReferences();

        assert check();
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        numberOfNodes--;
        Node nodeToRemove = lastNode;

        lastNode = nodeToRemove.getPrior();
        if (lastNode != null) {
            lastNode.setNext(null);
        }
        if (numberOfNodes <= 1) {
            firstNode = lastNode;
        }

        Item item = (Item) nodeToRemove.getItem();
        nodeToRemove.cleanReferences();

        assert check();
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(this.firstNode);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Deque.Node currentNode;

        public DequeIterator(Node firstNode) {
            this.currentNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (currentNode == null) throw new NoSuchElementException();

            Item item = (Item) currentNode.getItem();
            currentNode = currentNode.getNext();
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node prior(Node node) {
        if (node != null)
            return node.getPrior();
        else
            return null;
    }

    // check internal invariants
    private boolean check() {
        if (numberOfNodes == 0) {
            if (firstNode != null) return false;
        }
        else if (numberOfNodes == 1) {
            if (firstNode == null)      return false;
            if (firstNode.getNext() != null) return false;
        }
        else {
            if (firstNode.getNext() == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = firstNode; x != null; x = x.getNext()) {
            numberOfNodes++;
        }
        if (numberOfNodes != this.numberOfNodes) return false;

        return true;
    }
}
