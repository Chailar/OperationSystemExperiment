package OperationSystem;

import java.text.DecimalFormat;
import OperationSystem.utils.*;

public class PageReplace {
    private static int number = 3;

    public static int[] rangeNumber(int pageNumber, int maxPageNumber) {
        int[] pages = new int[pageNumber];
        for (int i = 0; i < pageNumber; i++)
            pages[i] = (int) (Math.random() * maxPageNumber);
        return pages;
    }

    public static void printArray(int[] pages) {
        for (int i = 0; i < pages.length; i++)
            System.out.print(pages[i] + " ");
        System.out.println();
    }

    public static void OPT(int[] pages) {
        System.out.println("最佳置换算法(OPT)");
        ReplacePageQueue<Integer> queue = new ReplacePageQueue<Integer>(number);
        for (int m = 0; m < pages.length; m++) {
            queue.addOPT(pages[m], pages, m);
            System.out.println(queue.toString());
        }
        DecimalFormat dFormat = new DecimalFormat("00.00");
        System.out
                .println("缺页" + queue.outNumber + "次,缺页中断率为"
                        + dFormat.format((float) queue.outNumber / pages.length * 100) + "%");
    }

    public static void FIFO(int[] pages) {
        System.out.println("先进先出置换算法(FIFO)");
        ReplacePageQueue<Integer> queue = new ReplacePageQueue<Integer>(number);
        for (int i = 0; i < pages.length; i++) {
            queue.add(pages[i]);
            System.out.println(queue.toString());
        }
        DecimalFormat dFormat = new DecimalFormat("00.00");
        System.out
                .println("缺页" + queue.outNumber + "次,缺页中断率为"
                        + dFormat.format((float) queue.outNumber / pages.length * 100) + "%");
    }

    public static void LRU(int[] pages) {
        System.out.println("最久未被使用置换算法(LRU)");
        LinkedList<Integer> list = new LinkedList<>(number);
        for (int i = 0; i < pages.length; i++) {
            list.add(pages[i]);
            System.out.println(list.toString());
        }
        DecimalFormat dFormat = new DecimalFormat("00.00");
        System.out
                .println("缺页" + list.outNumber + "次,缺页中断率为"
                        + dFormat.format((float) list.outNumber / pages.length * 100) + "%");
    }

    public static void Clock(int[] pages) {
        System.out.println("时钟置换算法(Clock)");
        DoubleLinkedList<Integer> list=new DoubleLinkedList<>(number);
        for (int i = 0; i < pages.length; i++) {
            list.add(pages[i]);
            System.out.println(list.toString());
        }
        DecimalFormat dFormat = new DecimalFormat("00.00");
        System.out
                .println("缺页" + list.outNumber + "次,缺页中断率为"
                        + dFormat.format((float) list.outNumber / pages.length * 100) + "%");
    }

    public static void main(String[] args) {
        // int[] pages = rangeNumber(19, 8);
        int[] pages = { 2,3,2,1,5,2,4,5,3,2,5,2 };
        printArray(pages);
        // FIFO(pages);
        // OPT(pages);
        // LRU(pages);
        Clock(pages);
    }
}
