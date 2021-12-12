package OperationSystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class MoveArmSchedul {
    public static int[] rangeNumber(int accessNumber, int maxCylinderNumber) {
        int[] cylinders = new int[accessNumber];
        for (int i = 0; i < accessNumber; i++)
            cylinders[i] = (int) (Math.random() * maxCylinderNumber);
        return cylinders;
    }

    public static void printArray(int[] pages) {
        for (int i = 0; i < pages.length; i++)
            System.out.print(pages[i] + " ");
        System.out.println();
    }

    public static void FIFO(int lastCylinder, int currentCylinder, int[] cylinders) {
        int[] accessSequence = new int[cylinders.length];
        for (int i = 0; i < cylinders.length; i++)
            accessSequence[i] = cylinders[i];
        String str = "先来先服务访问顺序:(" + currentCylinder + ") ";
        for (int i = 0; i < accessSequence.length; i++)
            str += accessSequence[i] + " ";
        System.out.println(str);
        str = "先来先服务移动距离 ";
        int distanceSum = Math.abs(currentCylinder - accessSequence[0]);
        str += distanceSum + "+";
        for (int i = 1; i < accessSequence.length; i++) {
            int distance = Math.abs(accessSequence[i] - accessSequence[i - 1]);
            distanceSum += distance;
            if (i != accessSequence.length - 1) {
                str += distance + "+";
            } else {
                str += distance + "=" + distanceSum;
            }
        }
        System.out.println(str);
    }

    public static void SSTF(int lastCylinder, int currentCylinder, int[] cylinders) {
        int[] visit = new int[cylinders.length];
        for (int i = 0; i < visit.length; i++)
            visit[i] = 0;
        int[] accessSequence = new int[cylinders.length + 1];
        accessSequence[0] = currentCylinder;
        for (int i = 1; i < accessSequence.length; i++) {
            int index = -1;
            for (int j = 0; j < cylinders.length; j++) {
                if (visit[j] == 1)
                    continue;
                if (index == -1) {
                    index = j;
                    continue;
                }
                if (Math.abs(cylinders[index] - accessSequence[i - 1]) > Math.abs(cylinders[j] - accessSequence[i - 1]))
                    index = j;
            }
            accessSequence[i] = cylinders[index];
            visit[index] = 1;
        }
        String str = "最短寻路优先访问顺序:(" + currentCylinder + ") ";
        for (int i = 1; i < accessSequence.length; i++)
            str += accessSequence[i] + " ";
        System.out.println(str);
        str = "最短寻路优先移动距离 ";
        int distanceSum = Math.abs(currentCylinder - accessSequence[1]);
        str += distanceSum + "+";
        for (int i = 2; i < accessSequence.length; i++) {
            int distance = Math.abs(accessSequence[i] - accessSequence[i - 1]);
            distanceSum += distance;
            if (i != accessSequence.length - 1) {
                str += distance + "+";
            } else {
                str += distance + "=" + distanceSum;
            }
        }
        System.out.println(str);
    }

    public static void ED(int lastCylinder, int currentCylinder, int[] cylinders) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> accessSequenceList = new ArrayList<>();
        for (int i = 0; i < cylinders.length; i++)
            list.add(cylinders[i]);
        list.sort(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            };
        });
        boolean positiveDirection = (currentCylinder > lastCylinder);
        int index = -1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i) - currentCylinder > 0) {
                index = i;
                break;
            }
        if (index == -1)
            index = list.size();
        if (positiveDirection) {
            for (int i = index; i < list.size(); i++)
                accessSequenceList.add(list.get(i));
            if (index != 0)
                for (int i = index - 1; i >= 0; i--)
                    accessSequenceList.add(list.get(i));
        } else {
            if (index != 0)
                for (int i = index - 1; i >= 0; i--)
                    accessSequenceList.add(list.get(i));
            for (int i = index; i < list.size(); i++)
                accessSequenceList.add(list.get(i));
        }
        int[] accessSequence = accessSequenceList.stream().mapToInt(Integer::valueOf).toArray();
        String str = "电梯调度访问顺序:(" + currentCylinder + ") ";
        for (int i = 0; i < accessSequence.length; i++)
            str += accessSequence[i] + " ";
        System.out.println(str);
        str = "电梯调度移动距离 ";
        int distanceSum = Math.abs(currentCylinder - accessSequence[0]);
        str += distanceSum + "+";
        for (int i = 1; i < accessSequence.length; i++) {
            int distance = Math.abs(accessSequence[i] - accessSequence[i - 1]);
            distanceSum += distance;
            if (i != accessSequence.length - 1) {
                str += distance + "+";
            } else {
                str += distance + "=" + distanceSum;
            }
        }
        System.out.println(str);
    }

    public static void SCAN(int lastCylinder, int currentCylinder, int[] cylinders) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> accessSequenceList = new ArrayList<>();
        for (int i = 0; i < cylinders.length; i++)
            list.add(cylinders[i]);
        list.sort(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            };
        });
        int index = -1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i) - currentCylinder > 0) {
                index = i;
                break;
            }
        if (index == -1)
            index = list.size();
        for (int i = index; i < list.size(); i++)
            accessSequenceList.add(list.get(i));
        for (int i = 0; i < index; i++)
            accessSequenceList.add(list.get(i));
        int[] accessSequence = accessSequenceList.stream().mapToInt(Integer::valueOf).toArray();
        String str = "单向扫描访问顺序:(" + currentCylinder + ") ";
        for (int i = 0; i < accessSequence.length; i++)
            str += accessSequence[i] + " ";
        System.out.println(str);
        str = "单向扫描移动距离 ";
        int distanceSum = Math.abs(currentCylinder - accessSequence[0]);
        str += distanceSum + "+";
        for (int i = 1; i < accessSequence.length; i++) {
            int distance = -1;
            if (accessSequence[i] - accessSequence[i - 1] < 0) {
                str += 1 + "+";
                distanceSum += 1;
                distance = accessSequence[i];
            } else
                distance = Math.abs(accessSequence[i] - accessSequence[i - 1]);
            distanceSum += distance;
            if (i != accessSequence.length - 1) {
                str += distance + "+";
            } else {
                str += distance + "=" + distanceSum;
            }
        }
        System.out.println(str);
    }

    public static void main(String[] args) {
        int[] cylinders = { 86, 147, 91, 177, 94, 150, 102, 175, 130 };
        FIFO(143, 125, cylinders);
        SSTF(143, 125, cylinders);
        ED(143,125,cylinders);
        SCAN(143, 125, cylinders);
    }
}