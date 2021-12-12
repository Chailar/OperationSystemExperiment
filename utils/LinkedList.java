package OperationSystem.utils;

public class LinkedList<T> {
    public Node<T> head;
    public int maxLength;
    public int outNumber;

    public LinkedList(int number) {
        this.head = new Node<T>();
        this.maxLength = number;
        this.outNumber=0;
    }

    public int size() {
        int number = 0;
        for (Node<T> p = this.head.next; p != null; p = p.next)
            number++;
        return number;
    }

    public Node<T> searchFont(T key) {
        for (Node<T> p = this.head; p.next != null; p = p.next) {
            if (key.equals(p.next.data))
                return p;
        }
        outNumber++;
        return null;
    }

    public void add(T value) {
        Node<T> p = this.searchFont(value);
        if (p != null) {
            p.next = p.next.next;
        }
        if (this.size() >= this.maxLength) {
            System.out.println("淘汰" + this.head.next.data + "页");
            this.head.next = this.head.next.next;
        }
        Node<T> p2 = this.head;
        while (true) {
            if (p2.next == null)
                break;
            p2 = p2.next;
        }
        p2.next = new Node<T>(value, null);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer("(");
        for(Node<T> p=this.head.next;p!=null;p=p.next)
        stringBuffer.append(p.data+",");
        stringBuffer.setCharAt(stringBuffer.length()-1, ')');
        return new String(stringBuffer);
    }
}