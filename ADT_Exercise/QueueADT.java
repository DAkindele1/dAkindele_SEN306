public interface QueueADT<T> {
    void enqueue(int element);
    int dequeue();
    boolean isEmpty();
    int size();
}