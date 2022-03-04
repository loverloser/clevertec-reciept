package ru.clevertec.collection;

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

public class CustomLinkedList<E> implements List<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public CustomLinkedList() {
    }

    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
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
        try {
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
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        try {
            readLock.lock();
            return indexOf(o) != -1;
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
    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {

        private int cursor = 0;

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
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            try {
                writeLock.lock();
                if (hasNext()) {
                    return get(cursor++);
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
     */
    @Override
    public Object[] toArray() {
        try {
            readLock.lock();
            Object[] arr = new Object[size];
            int counter = 0;
            for (Node<E> x = first; x.next != null; x = x.next) {
                arr[counter++] = x.element;
            }
            return arr;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws NullPointerException     if the specified element is null and this
     *                                  list does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this list
     */
    @Override
    public boolean add(E e) {
        try {
            writeLock.lock();
            Node<E> l = last;
            Node<E> newNode = new Node<>(e, l, null);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
            size++;

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
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean remove(Object o) {
        try {
            writeLock.lock();
            if (o == null) {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (x.element == null) {
                        innerRemove(x);
                        return true;
                    }
                }
            } else {
                for (Node<E> x = first; x != null; x = x.next) {
                    if (o.equals(x.element)) {
                        innerRemove(x);
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
     * Removes all the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this list
     */
    @Override
    public void clear() {
        try {
            writeLock.lock();
            for (Node<E> x = first; x != null; ) {
                Node<E> e = x.next;
                x.prev = null;
                x.element = null;
                x.next = null;
                x = e;
            }
            first = last = null;
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
    public E get(int index) {
        try {
            readLock.lock();
            checkIndex(index);
            return findNode(index).element;
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
     * @throws NullPointerException      if the specified element is null and
     *                                   this list does not permit null elements
     * @throws IllegalArgumentException  if some property of the specified
     *                                   element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public E set(int index, E element) {
        try {
            writeLock.lock();
            checkIndex(index);
            Node<E> node = findNode(index);
            E result = node.element;
            node.element = element;
            return result;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this list
     * @throws NullPointerException          if the specified element is null and
     *                                       this list does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    @Override
    public void add(int index, E element) {
        try {
            writeLock.lock();
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }

            if (index == size) {
                add(element);
            } else {
                Node<E> node = findNode(index);
                Node<E> tempPrev = node.prev;
                Node<E> newNode = new Node<>(element, tempPrev, node);
                node.prev = newNode;
                if (tempPrev == null)
                    first = newNode;
                else
                    tempPrev.next = newNode;
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
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public E remove(int index) {
        try {
            writeLock.lock();
            checkIndex(index);
            Node<E> node = findNode(index);
            return innerRemove(node);
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
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        try {
            readLock.lock();
            int index = 0;
            if (o == null) {
                for (Node<E> i = first; i != null; i = i.next) {
                    if (i.element == null) {
                        return index;
                    }
                    index++;
                }
            } else {
                for (Node<E> i = first; i != null; i = i.next) {
                    if (o.equals(i.element)) {
                        return index;
                    }
                    index++;
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
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        try {
            readLock.lock();
            int index = size;
            if (o == null) {
                for (Node<E> i = last; i != null; i = i.prev) {
                    index--;
                    if (i.element == null) {
                        return index;
                    }
                }
            } else {
                for (Node<E> i = last; i != null; i = i.prev) {
                    index--;
                    if (i.element.equals(o)) {
                        return index;
                    }
                }
            }
            return -1;
        }finally {
            readLock.unlock();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

    }

    private Node<E> findNode(int index) {
        Node<E> x;
        if (index < size / 2) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; index < i; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private E innerRemove(Node<E> node) {
        E element = node.element;
        Node<E> next = node.next;
        Node<E> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.element = null;
        size--;
        return element;
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
