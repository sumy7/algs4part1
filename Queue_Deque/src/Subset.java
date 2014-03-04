public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            que.enqueue(item);
        }
        int N = Integer.parseInt(args[0]);
        for (int i = 0; i < N; i++) {
            StdOut.println(que.dequeue());
        }
    }
}