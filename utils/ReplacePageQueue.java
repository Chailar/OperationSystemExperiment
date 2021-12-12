package OperationSystem.utils;

import java.beans.BeanInfo;

public class ReplacePageQueue<T> implements Queue<T> {
    public Object elements[];
    public int front, rear;
    public int outNumber;
    public int[] pages;

    public ReplacePageQueue(int length) {
        this.front = this.rear = 0;
        this.elements = new Object[length + 1];
        this.outNumber = 0;
    }

    public boolean isEmpty() {
        return this.front == this.rear;
    }

    public boolean isFull() {
        return this.front == (this.rear + 1) % this.elements.length;
    }

    public boolean haveIt(T x) {
        for (int i = this.front; i != this.rear; i = (i + 1) % this.elements.length) {
            if (this.elements[i] == x)
                return true;
        }
        outNumber++;
        return false;
    }

    @Override
    public boolean add(T x) {
        if (x == null)
            return false;
        if (!this.haveIt(x)) {
            if (this.isFull()) {
                T out = this.poll();
                System.out.println("淘汰" + out.toString() + "页");
            }
            this.elements[this.rear] = x;
            this.rear = (this.rear + 1) % this.elements.length;
        }
        return true;
    }

    @Override
    public T peek() {
        return this.isEmpty() ? null : (T) this.elements[this.front];
    }

    @Override
    public T poll() {
        if (this.isEmpty())
            return null;
        T temp = (T) this.elements[this.front];
        this.front = (this.front + 1) % this.elements.length;
        return temp;
    }

    @Override
    public String toString() {
        StringBuffer strbuf = new StringBuffer("(");
        for (int i = this.front; i != this.rear; i = (i + 1) % this.elements.length)
            strbuf.append(this.elements[i].toString() + ",");
        if (this.isEmpty())
            strbuf.append(')');
        else
            strbuf.setCharAt(strbuf.length() - 1, ')');
        return new String(strbuf);
    }

    public void remove(T value) {
        if (this.isEmpty())
            return;
        int order = -1;
        for (int i = this.front; i != this.rear; i = (i + 1) % this.elements.length)
            if (this.elements[i] == value)
                order = i;
        for (int i = order; i != this.rear; i = (i + 1) % this.elements.length) {
            this.elements[i] = this.elements[(i + 1) % this.elements.length];
        }
        this.rear = (this.rear + this.elements.length - 1) % this.elements.length;
        System.out.println("淘汰" + value.toString() + "页");
    }

    public void removeOPTIndex(int[] pages, int begin) {
        int[] next = new int[this.elements.length];
        for (int i = this.front; i != this.rear; i = (i + 1) % this.elements.length) {
            for (int j = begin; j < pages.length; j++)
                if (pages[j] == (int) this.elements[i]) {
                    next[i] = j - begin;
                    break;
                }
        }
        for (int i = this.front; i != this.rear; i = (i + 1) % this.elements.length)
            if (next[i] == 0)
                next[i] = Integer.MAX_VALUE;
        int MaxSize = this.front;
        for (int i = (this.front + 1) % this.elements.length; i != this.rear; i = (i + 1)
                % this.elements.length) {
            if (next[i] > next[MaxSize])
                MaxSize = i;
        }
        int a = 1;
        this.remove((T) this.elements[MaxSize]);
    }

    public boolean addOPT(T x, int[] pages, int begin) {
        if (x == null)
            return false;
        if (!this.haveIt(x)) {
            if (isFull())
                this.removeOPTIndex(pages, begin);
                this.elements[this.rear] = x;
                this.rear = (this.rear + 1) % this.elements.length;
        }
        return true;
    }
}