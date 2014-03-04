import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node<Item> first = null;
    private Node<Item> last = null;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node<Item> newfirst = new Node<Item>();
        newfirst.item = item;
        newfirst.next = first;
        newfirst.prev = null;
        if (isEmpty()) {
            last = newfirst;
        } else {
            first.prev = newfirst;
        }
        first = newfirst;
        N++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        q.addFirst("A");
        StdOut.println(q.removeFirst());
        StdOut.println(q.isEmpty());
        q.addFirst("A");
        q.addFirst("B");
        StdOut.println(q.removeLast());
        StdOut.println(q.removeLast());
        StdOut.println(q.isEmpty());
        q.addFirst("B");
        q.addLast("A");
        q.addFirst("C");
        for (String string : q) {
            StdOut.print(string + " ");
        }
        StdOut.println();
        StdOut.println(q.removeLast());
        StdOut.println(q.removeLast());
        StdOut.println(q.isEmpty());
    }
}