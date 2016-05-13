package problems;

/**
 * Created by allenc289 on 4/5/16.
 */
/*
 * public interface Iterator<T> {
 *     // Returns true, if the iteration has more elements.
 *     boolean hasNext();
 *
 *     // Returns the next element if it exists, throws NoSuchElementException (a RuntimeException) if not.
 *     T next();
 *
 *     // Removes from the underlying collection the last element returned by this iterator (optional operation).
 *     void remove();
 * }
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * An iterator that returns a subset of the elements returned by the underlying iterator.
 * For example:
 *   Iterator<Long> primes = new FilteringIterator(positiveNumberIterator, primeTester);
 *   while (true)
 *      System.out.println(primes.next());
 * or
 *   Iterator<Sku>  outOfStockIterator = new FilteringIterator(inventoryIterator, outOfStockTester);
 *   while (outOfStockIterator.hasNext()) {
 *      outOfStockIterator.next().orderMore();
 *   }
 */
public class FilteringIterator<T> implements Iterator<T> {
    // p.test(T) -> true if the element satisfies the predicate, false otherwise
    private final Predicate<T> p;
    private final Iterator<T> source;

    /**
     * @param source   the iterator to wrap
     * @param filter   elements must match this filter to be returned
     */
    public FilteringIterator(Iterator<T> source, Predicate<T> p) {
        this.p = p;
        this.source = source;
    }

    private T elementThatSatisfiesPredicate;

    @Override
    public boolean hasNext() {
        if (elementThatSatisfiesPredicate != null) {
            return true;
        }

        while (source.hasNext()) {
            elementThatSatisfiesPredicate = source.next();
            if (p.test(elementThatSatisfiesPredicate)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public T next() {
        // TODO
        if (elementThatSatisfiesPredicate != null) {
            T savedElementThatSatisfiesPredicate = elementThatSatisfiesPredicate;
            elementThatSatisfiesPredicate = null;
            return savedElementThatSatisfiesPredicate;
        }


        while (source.hasNext()) {
            T nextCandidate = source.next();
            if (p.test(nextCandidate)) {
                return nextCandidate;
            }
        }

        throw new NoSuchElementException();
    }

    public void main(String[] args) {
        
    }
}
