package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T>{
    private final HANLinkedList<T> stack = new HANLinkedList<T>();

    @Override
    public void push(T value) {
        stack.insert(0, value);
    }

    @Override
    public T pop() {
        if (stack.getSize() != 0) {
            T lastValueInList = stack.getFirst();
            stack.delete(0);
            return lastValueInList;
        }
        return null;
    }

    @Override
    public T peek() {
        return  stack.getFirst();
    }
}
