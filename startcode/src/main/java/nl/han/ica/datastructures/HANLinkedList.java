package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    private final Node<T> head = new Node<>();
    private int size;

    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if(head.next != null){
            newNode.next = head.next;
        }
        head.next = newNode;
        size++;
    }

    @Override
    public void clear() {
        head.next = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        Node<T> currentNode = head;

        for(int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> afterInsertedNode = currentNode.next;
        Node<T> insertedNode = new Node<>(value);
        currentNode.next = insertedNode;
        insertedNode.next = afterInsertedNode;
        size++;
    }

    @Override
    public void delete(int pos) {
        Node<T> currentNode = head;

        for(int i = 0; i< pos; i++){
            currentNode = currentNode.next;
        }
        currentNode.next = currentNode.next.next;
        size--;
    }

    @Override
    public T get(int pos) {
        Node<T> currentNode = head;

        for(int i = 0; i< pos; i++){
            currentNode = currentNode.next;
        }

        return currentNode.next.value;
    }

    @Override
    public void removeFirst() {
        head.next = head.next.next;
        size--;
    }

    @Override
    public T getFirst() {
        return head.next.value;
    }

    @Override
    public int getSize() {
        return size;
    }
}
