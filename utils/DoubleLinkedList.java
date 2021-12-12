package OperationSystem.utils;

public class DoubleLinkedList<T> {
    public DoubleNode<Element<T>> head, out;
    public int maxLength;
    public int outNumber;

    public DoubleLinkedList(int length) {
        this.head = new DoubleNode<Element<T>>();
        this.head.prev = this.head;
        this.head.next = this.head;
        this.out = this.head;
        this.maxLength = length;
        this.outNumber = 0;
    }

    public int size() {
        int number = 0;
        for (DoubleNode<Element<T>> p = this.head.next; p != this.head; p = p.next)
            number++;
        return number;
    }

    public DoubleNode<Element<T>> searchIt(T value) {
        for (DoubleNode<Element<T>> p = this.head.next; p != this.head; p = p.next)
            if (p.data.equals(new Element<T>(value)))
                return p;
        this.outNumber++;
        return null;
    }

    public boolean add(T value) {
        if (value == null)
            return false;
        DoubleNode<Element<T>> find = searchIt(value);
        if (find != null) {
            find.data.isVisit = true;
        } else {
            if (this.size() >= this.maxLength) {
                findOutPages();
                DoubleNode<Element<T>> p = new DoubleNode<Element<T>>(new Element<T>(value), this.out.prev,
                        this.out.next);
                this.out.prev.next = p;
                this.out.next.prev = p;
                this.out=this.out.next;
            } else {
                DoubleNode<Element<T>> p = new DoubleNode<Element<T>>(new Element<T>(value), this.head.prev, this.head);
                this.head.prev.next = p;
                this.head.prev = p;
            }
        }
        return true;
    }

    public void findOutPages() {
        while (true) {
            if (this.out == this.head)
                this.out = this.out.next;
            if (this.out.data.isVisit) {
                this.out.data.isVisit = false;
                this.out = this.out.next;
            } else
                break;
        }
        System.out.println("淘汰" + this.out.data.data.toString() + "页");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("(");
        for (DoubleNode<Element<T>> p = this.head.next; p != this.head; p = p.next)
            stringBuffer.append(p.data + ",");
        stringBuffer.setCharAt(stringBuffer.length() - 1, ')');
        return new String(stringBuffer);
    }

    class Element<T> {
        public T data;
        public boolean isVisit;

        public Element(T data) {
            this.data = data;
            isVisit = true;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || obj instanceof DoubleLinkedList.Element && this.data.equals(((Element<T>) obj).data);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}