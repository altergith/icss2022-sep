package nl.han.ica.datastructures;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HANLinkedListTest {
    public HANLinkedList<Integer> sut = new HANLinkedList<>();

    @BeforeEach
    public void setUp() {
        sut = new HANLinkedList<>();
    }


    @Test
    public void linkedListReturnsCorrectSizeAfterInserts(){
        //arrange
        sut.insert(0, 1);
        sut.insert(1, 2);
        sut.insert(2, 3);

        int correctArrayLength = 3;
        //assert
        Assertions.assertEquals(3,sut.getSize());
    }

    @Test
    public void linkedListReturnsCorrectNodesAfterInserts(){

        //arrange/act
        sut.addFirst(5);
        sut.insert(1,2);
        sut.insert(2,3);

        //assert
        assertEquals(5,sut.get(0));
        assertEquals(2,sut.get(1));
        assertEquals(3,sut.get(2));
    }

    @Test
    public void linkedListMovesNodeOnSameIndex(){
        //arrange
        Integer original= 4;
        Integer newInteger= 6;

        int correctArrayLength = 2;
        //assert
        //act
            sut.insert(0, original);
            sut.insert(0, newInteger);

        assertEquals(newInteger,sut.get(0));
        assertEquals(correctArrayLength,sut.getSize());

    }
    @Test
    public void testDelete() {
        sut.addFirst(2);
        sut.addFirst(3);
        sut.addFirst(1);

        sut.delete(1);

        assertEquals(2, sut.getSize());

        assertEquals(1, sut.get(0));
        assertEquals(2, sut.get(1));
    }
    @Test
    public void testRemoveFirst() {


        sut.addFirst(1);
        sut.addFirst(2);

        sut.removeFirst(); // Remove the first element

        assertEquals(1, sut.getSize());
        assertEquals(1, sut.getFirst());
    }
}
