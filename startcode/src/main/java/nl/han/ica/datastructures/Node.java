package nl.han.ica.datastructures;

public class Node<T> {
    Node<T> next;
    T value;

    public Node() {
    }
    public Node(T value){
        this.value = value;
    }


    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getValue() {
        return value;
    }
}
