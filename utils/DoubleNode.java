package OperationSystem.utils;
//《数据结构与算法（Java版）（第5版）》，作者：叶核亚，2019年7月22日
//§2.3.3  循环双链表
//1. 双链表结点

public class DoubleNode<T>                       //双链表结点类，T指定结点的元素类型
{
    public T data;                               //数据域，存储数据元素
    public DoubleNode<T> prev, next;             //地址域，prev指向前驱结点，next指向后继结点

    //构造结点，data指定元素，prev指向前驱结点，next指向后继结点
    public DoubleNode(T data, DoubleNode<T> prev, DoubleNode<T> next)
    {
        this.data = data;                        //T对象引用赋值
        this.prev = prev;                        //DoubleNode<T>对象引用赋值
        this.next = next;
    }
    public DoubleNode()
    {
        this(null, null, null);
    }
    public String toString()                     //返回结点数据域的描述字符串
    {
        return this.data.toString();
    }    
}
/*  //以下第5版没写
    public DoubleNode(T data)
    {
        this(data, null, null);
    }
    //教材没有写，没有直接调用。算法同单链表结点
    public boolean equals(Object obj)            //比较两个结点值是否相等，覆盖Object类的equals(obj)方法
    {
        return obj==this || 
               obj instanceof DoubleNode1<?> && this.data.equals(((DoubleNode1<T>)obj).data);
    }    
 */
//@author：Yeheya，2014-10-9，2014年6月23日，2019年7月22日