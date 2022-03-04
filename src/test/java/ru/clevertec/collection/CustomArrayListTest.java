package ru.clevertec.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomArrayListTest {
    private CustomArrayList<String> listOfStrings;
    private CustomArrayList<String> emptyList;

    @BeforeEach
    void setUp() {
        emptyList = new CustomArrayList<>();
        listOfStrings = new CustomArrayList<>();
        listOfStrings.add("line1");
        listOfStrings.add("line2");
        listOfStrings.add("line3");
        listOfStrings.add("line4");
        listOfStrings.add("line5");
        listOfStrings.add("line6");
        listOfStrings.add("line7");
        listOfStrings.add(null);
        listOfStrings.add("line9");
        listOfStrings.add("line10");
        listOfStrings.add("line11");
    }

    @Test
    void ifAddElementSizeIncrease() {
        emptyList.add("420");
        assertEquals(1, emptyList.size());
    }

    @Test
    void ifListEmptyReturnTrue() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void ifListContainsElementReturnTrue() {
        assertTrue(listOfStrings.contains("line4"));
    }

    @Test
    void testIterator() {
        int count = 0;
        for (String list : listOfStrings) {
            count++;
        }

        assertEquals(count, listOfStrings.size());
    }

    @Test
    void checkListSizeIfCallToArray() {
        assertEquals(new Object[]{}.length, emptyList.toArray().length);
    }

    @Test
    void tryRemoveByIndexAndGet() {
        String value = "value";
        emptyList.add(value);
        assertEquals(value, emptyList.remove(0));
    }

    @Test
    void ifListAddAllSizeIncrease() {
        int sizeBefore = listOfStrings.size();
        emptyList.add("Val");
        emptyList.add("Val2");
        listOfStrings.addAll(emptyList);
        int sizeAfter = listOfStrings.size();
        assertEquals(sizeBefore + 2, sizeAfter);
    }

    @Test
    void tryGetElementsIfIndexesIncorrectThrowIOB() {
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class, () -> listOfStrings.add(14432, "v1")),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> listOfStrings.add(-123, "v2")),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> listOfStrings.add(11, "v3"))
        );
    }

    @Test
    void ifIndexesCorrectReturnTrue() {
        String val1 = "val1";
        String val2 = "val2";
        String val3 = "val3";
        listOfStrings.add(0, val1);
        listOfStrings.add(1, val2);
        listOfStrings.add(2, val3);
        assertAll(
                () -> assertEquals(val1, listOfStrings.get(0)),
                () -> assertEquals(val2, listOfStrings.get(1)),
                () -> assertEquals(val3, listOfStrings.get(2))
        );
    }

    @Test
    void setElementByIndex() {
        String element = "element for set";
        listOfStrings.set(0, element);
        assertEquals(element, listOfStrings.get(0));
    }

    @Test
    void testRemove() {
        int sizeBefore = listOfStrings.size();
        listOfStrings.remove(null);
        assertEquals(sizeBefore - 1, listOfStrings.size());
    }

    @Test
    void ifElementNotExistsReturnMinusOne() {
        assertEquals(-1, listOfStrings.indexOf("non-existent val"));
    }

    @Test
    void lastIndexOfNull() {
        assertEquals(7, listOfStrings.lastIndexOf(null));
    }

    @Test
    void throwIfListIteratorCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.listIterator());
    }

    @Test
    void throwIfListIteratorFromIndexCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.listIterator(0));
    }

    @Test
    void throwIfSubListCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.subList(0, 1));
    }

    @Test
    void throwIfAddAllFromIndexCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.addAll(0, emptyList));
    }

    @Test
    void throwIfRemoveAllCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.removeAll(emptyList));
    }

    @Test
    void throwIfRetainAllCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.retainAll(emptyList));
    }

    @Test
    void throwIfToArrayWithParamCalled() {
        assertThrows(UnsupportedOperationException.class, () -> listOfStrings.toArray(new Object[0]));
    }
}