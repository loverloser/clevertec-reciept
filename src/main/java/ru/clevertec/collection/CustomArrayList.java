package ru.clevertec.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This List implementation contains all methods from list.
 * CustomArrayList let us interact with object what have general type.
 * You can manipulate date in List with follows methods: add, remove, set, contains and other.
 @author Tsimafei Labanovich
 */

public class CustomArrayList<T> implements List<T> {

    private final int initialCapacity = 10;
    private T[] elementData;
    private int size;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        this.elementData = (T[]) new Object[initialCapacity];
    }

    @SuppressWarnings("unchecked")
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(initialCapacity + " is illegal capacity.");
        } else if (initialCapacity == 0) {
            this.elementData = (T[]) new Object[]{};
        } else {
            this.elementData = (T[]) new Object[this.initialCapacity];
        }
    }


    /**
     * Returns the number of elements in this list.  If this list contains
     * more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        try{
            readLock.lock();
            return size;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        try {
            readLock.lock();
            return size == 0;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        try {
            readLock.lock();
            return indexOf(o) >= 0;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<T> {
        private int cursor;

        Iter() {
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            try {
                readLock.lock();
                return cursor != size;
            }finally {
                readLock.unlock();
            }
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public T next() {
            try {
                writeLock.lock();
                if (hasNext()) {
                    return elementData[cursor++];
                }
                throw new NoSuchElementException();
            }finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     *
     * @return an array containing all of the elements in this list in proper
     * sequence
     * @see Arrays#asList(Object[])
     */
    @Override
    public Object[] toArray() {
        try {
            readLock.lock();
            return Arrays.copyOf(elementData, size);
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * @param t element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified element is null and this
     *                                       list does not permit null elements
     * @throws IllegalArgumentException      if some property of this element
     *                                       prevents it from being added to this list
     */
    @Override
    public boolean add(T t) {
        try{
            writeLock.lock();
            if (elementData.length == size) {
                ensureCapacity();
            }
            elementData[size++] = t;
            return true;
        }finally {
            writeLock.unlock();
        }

    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     * @throws NullPointerException          if the specified element is null and this
     *                                       list does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean remove(Object o) {
        try{
            writeLock.lock();
            if (o == null) {
                for (int i = 0; i < size; i++) {
                    if (elementData[i] == null) {
                        removeAndShift(i);
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (elementData[i].equals(o)) {
                        removeAndShift(i);
                        return true;
                    }
                }
            }
            return false;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return <tt>true</tt> if this list contains all of the elements of the
     * specified collection
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this list does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        try {
            readLock.lock();
            for (Object o : c) {
                for (T elementDatum : elementData) {
                    if (!elementDatum.equals(o)) {
                        return false;
                    }
                }
            }
            return true;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this list does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     * @see #add(Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends T> c) {
        try {
            writeLock.lock();
            T[] objects = (T[]) c.toArray();
            ensureCapacity(c.size());
            System.arraycopy(objects, 0, elementData, objects.length, objects.length);
            return true;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this list
     */
    @Override
    public void clear() {
        try {
            writeLock.lock();
            Arrays.fill(elementData, null);
            size = 0;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public T get(int index) {
        try {
            readLock.lock();
            checkCorrectIndexForAddInTheEnd(index);
            return elementData[index];
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>set</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified element is null and
     *                                       this list does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public T set(int index, T element) {
        try {
            writeLock.lock();
            checkCorrectIndexForAddInTheEnd(index);
            T oldValue = elementData[index];
            elementData[index] = element;
            return oldValue;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element right.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified element is null and
     *                                       this list does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    @Override
    public void add(int index, T element) {
        checkIndexForAddInAnyPosition(index);
        try {
            writeLock.lock();
            if (index >= 0 && index < size - 1) {
                ensureCapacity();
                System.arraycopy(elementData, index, elementData, index + 1, size - index);
                elementData[index] = element;
                size++;
            }
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public T remove(int index) {
        checkCorrectIndexForAddInTheEnd(index);
        try {
            writeLock.lock();
            T oldVal = elementData[index];
            removeAndShift(index);

            return oldVal;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        try {
            readLock.lock();
            if (o == null) {
                for (int i = 0; i < size; i++) {
                    if (elementData[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (o.equals(elementData[i])) {
                        return i;
                    }
                }
            }
            return -1;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        try {
            readLock.lock();
            if (o == null) {
                for (int i = size - 1; i > -1; i--) {
                    if (elementData[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = size - 1; i > -1; i--) {
                    if (elementData[i].equals(o)) {
                        return i;
                    }
                }
            }
            return -1;
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            str.append(elementData[i]);
            str.append(", ");
        }
        return "{" + str + "}";
    }

    //Ensure capacity of this list.
    private void ensureCapacity() {
        int newLength = (int) (size * 1.75);
        elementData = Arrays.copyOf(elementData, newLength);
    }

    //Ensure capacity of this list with new length.
    private void ensureCapacity(int newLength) {
        elementData = Arrays.copyOf(elementData, newLength + size);
        size = elementData.length;
    }

    private void checkCorrectIndexForAddInTheEnd(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(index + " illegal index");
    }

    private void checkIndexForAddInAnyPosition(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(index + " is illegal index");
    }

    // Remove element and decrement size of list.
    private void removeAndShift(int index) {
        int numDel = size - index - 1;
        if (numDel > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numDel);
        }
        elementData[--size] = null;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }
}
