import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemQueue = null;
    private int N = 0;

    public RandomizedQueue() {
        itemQueue = (Item[]) new Object[2];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (N == itemQueue.length)
            resize(2 * itemQueue.length);
        itemQueue[N++] = item;

    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int chooseItem = StdRandom.uniform(N);
        swap(chooseItem, N - 1);
        Item item = itemQueue[N - 1];
        itemQueue[N - 1] = null;
        N--;
        if (N > 0 && N == itemQueue.length / 4)
            resize(itemQueue.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int chooseItem = StdRandom.uniform(N);
        return itemQueue[chooseItem];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) {
            temp[i] = itemQueue[i];
        }
        itemQueue = temp;
    }

    private void swap(int i, int j) {
        Item item = itemQueue[i];
        itemQueue[i] = itemQueue[j];
        itemQueue[j] = item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] iterOrder;
        private int now = 0;

        public RandomizedQueueIterator() {
            iterOrder = new int[N];
            for (int i = 0; i < N; i++) {
                iterOrder[i] = i;
            }
            StdRandom.shuffle(iterOrder);
        }

        @Override
        public boolean hasNext() {
            return now < N;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = itemQueue[iterOrder[now]];
            now++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("=")) {
                StdOut.println("iterator");
                for (String string : q) {
                    StdOut.println(string);
                }
                StdOut.println();
            } else if (item.equals("-")) {
                StdOut.println("dequeue output:");
                if (!q.isEmpty())
                    StdOut.println(q.dequeue());
            } else {
                q.enqueue(item);
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}