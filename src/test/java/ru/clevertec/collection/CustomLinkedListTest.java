package ru.clevertec.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomLinkedListTest {
    private CustomLinkedList<String> list;
    private CustomLinkedList<String> emptyList;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<>();
        emptyList = new CustomLinkedList<>();
        list.add("line1");
        list.add("line2");
        list.add(null);
        list.add("line3");
        list.add("line4");
        list.add("line5");
        list.add(null);
    }

    @Test
    void size() {
        assertEquals(7, list.size());
    }

    @Test
    void ifListEmptyReturnTrue() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void ifContainsElementsReturnTrue() {
        assertAll(
                () -> assertTrue(list.contains("line1")),
                () -> assertTrue(list.contains("line5")),
                () -> assertTrue(list.contains(null))
        );
    }

    @Test
    void testIterator() {
        int count = 0;
        for (String list : list) {
            count++;
        }

        assertEquals(count, list.size());
    }

    @Test
    void tryConvertListToArray() {
        Object[] objects = list.toArray();
        assertEquals(list.size(), objects.length);
    }

    @Test
    void ifElementAddReturnTrue() {
        String line = "added line";
        list.add(line);
        assertTrue(list.contains(line));

    }

    @Test
    void ifElementRemoveReturnTrue() {
        int sizeBefore = list.size();
        list.add("added line");
        assertEquals(sizeBefore + 1, list.size());
    }

    @Test
    void testClearList() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void ifElementsGetReturnTrue() {
        assertAll(
                () -> assertEquals("line1", list.get(0)),
                () -> assertEquals("line2", list.get(1)),
                () -> assertNull(list.get(list.size() - 1))
        );
    }

    @Test
    void testSetElementByIndex() {
        String lineForAdd = "add line";
        list.set(0, lineForAdd);
        assertEquals(lineForAdd, list.get(0));
    }

    @Test
    void testAddElementByIndex() {
        String line = "line for add";
        int sizeBeforeAdd = list.size();
        list.add(0, line);
        assertAll(
                () -> assertEquals(line, list.get(0)),
                () -> assertEquals(sizeBeforeAdd + 1, list.size())
        );
    }

    @Test
    void testRemoveByIndex() {
        int sizeBefore = list.size();
        list.remove(0);
        assertEquals(sizeBefore - 1, list.size());
    }

    @Test
    void returnIndexOfElement() {
        assertEquals(2, list.indexOf(null));
    }

    @Test
    void returnLastIndexOfNull() {
        assertEquals(list.size() - 1, list.lastIndexOf(null));
    }

    @Test
    void throwIfListIteratorFromIndexCalled() {
        assertThrows(UnsupportedOperationException.class, () -> list.listIterator(0));
    }

    @Test
    void throwIfSubListCalled() {
        assertThrows(UnsupportedOperationException.class, () -> list.subList(0, 1));
    }

    @Test
    void throwIfAddAllFromIndexCalled() {
        assertThrows(UnsupportedOperationException.class, () -> list.addAll(0, emptyList));
    }

    @Test
    void throwIfRemoveAllCalled() {
        assertThrows(UnsupportedOperationException.class, () -> list.removeAll(emptyList));
    }

    @Test
    void throwIfRetainAllCalled() {
        assertThrows(UnsupportedOperationException.class, () -> list.retainAll(emptyList));
    }

    @Test
    void throwIfToArrayWithParamCalled() {
        assertThrows(UnsupportedOperationException.class, () -> list.toArray(new Object[0]));
    }
}