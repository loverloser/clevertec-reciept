package ru.clevertec.caches.impl;

import ru.clevertec.caches.Cacheable;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<T>  implements Cacheable<T> {
    private final int capacity;
    private int size;
    private final Map<Long, Node> hashmap;
    private final DoublyLinkedList internalQueue;

    public LRUCache(final int capacity) {
        this.capacity = capacity;
        this.hashmap = new HashMap<>();
        this.internalQueue = new DoublyLinkedList();
        this.size = 0;
    }

    @Override
    public T get(final Long key) {
        Node node = hashmap.get(key);
        if(node == null) {
            return null;
        }
        internalQueue.modeNodeToFront(node);
        return hashmap.get(key).value;
    }

    @Override
    public void put(final Long key, final T value) {
        Node currentNode = hashmap.get(key);
        if(currentNode != null) {
            currentNode.value = value;
            internalQueue.modeNodeToFront(currentNode);
            return;
        }

        if(size == capacity) {
            Long rearNodeKey = internalQueue.getRearKey();
            internalQueue.removeNodeFromRear();
            hashmap.remove(rearNodeKey);
            size--;
        }

        Node node = new Node(key, value);
        internalQueue.addNodeToFront(node);
        hashmap.put(key, node);
        size++;
    }

    @Override
    public Long remove(Long key) {
        return hashmap.remove(key) == null ? null : key;
    }

    private class Node {
        Long key;
        T value;
        Node next, prev;
        public Node(final Long key, final T value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    private class DoublyLinkedList {
        private Node front, rear;
        public DoublyLinkedList() {
            front = rear = null;
        }

        private void addNodeToFront(final Node node) {
            if(rear == null) {
                front = rear = node;
                return;
            }
            node.next = front;
            front.prev = node;
            front = node;
        }

        public void modeNodeToFront(final Node node) {
            if(front == node) {
                return;
            }

            if(node == rear) {
                rear = rear.prev;
                rear.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            node.prev = null;
            node.next = front;
            front.prev = node;
            front = node;
        }

        private void removeNodeFromRear() {
            if(rear == null) {
                return;
            }

            if(front == rear) {
                front = rear = null;
            } else {
                rear = rear.prev;
                rear.next = null;
            }
        }

        private Long getRearKey() {
            return rear.key;
        }
    }

}
