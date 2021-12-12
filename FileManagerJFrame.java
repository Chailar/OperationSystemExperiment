package OperationSystem;

import java.io.*;
import java.io.ObjectInputFilter.FilterInfo;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;
import javax.management.JMException;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import OperationSystem.utils.*;

public class FileManagerJFrame extends JFrame implements ActionListener {
    private static String path = "E:\\programming\\java\\school\\src\\OperationSystem";
    private JList<String> list;
    private DefaultListModel<String> model;
    private JScrollPane scroll;
    private JPanel panel;
    private JTextField text;
    private JMenuBar bar;
    private JMenu menu;
    private JMenuItem item1, item2, item3, item4, item5;

    public FileManagerJFrame() {
        super("文件管理器");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("地址"), "West");
        panel.add(text = new JTextField(path, 50));
        text.addActionListener(this);
        this.add(panel, "North");
        model = new DefaultListModel<String>();
        list = new JList<String>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new CellRender());
        addElement();
        scroll = new JScrollPane(list);
        this.add(scroll);
        bar = new JMenuBar();
        this.setJMenuBar(bar);
        menu = new JMenu("文件管理");
        bar.add(menu);
        menu.add(item1 = new JMenuItem("新建目录"));
        item1.addActionListener(this);
        menu.add(item2 = new JMenuItem("新建文件"));
        item2.addActionListener(this);
        menu.add(item3 = new JMenuItem("删除"));
        item3.addActionListener(this);
        menu.add(item4 = new JMenuItem("打开"));
        item4.addActionListener(this);
        menu.add(item5 = new JMenuItem("文件属性"));
        item5.addActionListener(this);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FileManagerJFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == text) {
            String str = path;
            String pattern = "^[a-zA-Z]:(\\\\[\\u4E00-\\u9FA5A-Za-z0-9_]+)*(\\\\)?$";
            if (Pattern.matches(pattern, text.getText())) {
                path = text.getText();
                model.removeAllElements();
                addElement();
            } else {
                JOptionPane.showMessageDialog(this, "你输入的地址不符合规范");
                text.setText(str);
            }
        }
        if (e.getSource() == item1) {
            String name = JOptionPane.showInputDialog(this, "目录名");
            if (name != null) {
                String pattern = "^[\\\\u4E00-\\\\u9FA5A-Za-z0-9_]+";
                if (Pattern.matches(pattern, name)) {
                    File f = new File(path, name);
                    if (!f.exists())
                        f.mkdir();
                    else
                        JOptionPane.showMessageDialog(this, "目录名重复");
                } else
                    JOptionPane.showMessageDialog(this, "你输入的目录名不符合规范");
                model.removeAllElements();
                addElement();
            }
        }
        if (e.getSource() == item2) {
            String name = JOptionPane.showInputDialog(this, "文件名");
            if (name != null) {
                String pattern = "^[\\\\u4E00-\\\\u9FA5A-Za-z0-9_]+\\.[a-zA-Z]+";
                if (Pattern.matches(pattern, name)) {
                    File f = new File(path, name);
                    if (!f.exists())
                        try {
                            f.createNewFile();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                            return;
                        }
                    else
                        JOptionPane.showMessageDialog(this, "文件名重复");
                } else
                    JOptionPane.showMessageDialog(this, "你输入的文件名不符合规范");
                model.removeAllElements();
                addElement();
            }
        }
        if (e.getSource() == item3) {
            if (list.getSelectedValue() != null) {
                if (JOptionPane.showConfirmDialog(this, "确认删除?", "确认", JOptionPane.YES_NO_OPTION) == 0) {
                    File f = new File(path, list.getSelectedValue());
                    if (f.isFile())
                        f.delete();
                    else
                        deleteAllFilesOfDir(f);
                    model.removeAllElements();
                    addElement();
                }
            } else
                JOptionPane.showMessageDialog(this, "请选择一项列表");
        }
        if (e.getSource() == item4) {
            if (list.getSelectedValue() != null) {
                if (Pattern.matches("^[\\\\u4E00-\\\\u9FA5A-Za-z0-9_]+\\.[a-zA-Z]+", list.getSelectedValue())) {
                    new Notepad(path + "\\" + list.getSelectedValue()).setVisible(true);
                    // try {
                    //     String command = "cmd /c start " + path + "\\" + list.getSelectedValue();
                    //     Runtime.getRuntime().exec(command);
                    // } catch (IOException exception) {
                    //     exception.printStackTrace();
                    // }
                } else {
                    path = path + "\\" + list.getSelectedValue();
                    text.setText(path);
                    model.removeAllElements();
                    addElement();
                }
            } else
                JOptionPane.showMessageDialog(this, "请选择一项列表");
        }
        if (e.getSource() == item5) {
            if (list.getSelectedValue() != null) {
                File f = new File(path, list.getSelectedValue());
                if (Pattern.matches("^[\\\\u4E00-\\\\u9FA5A-Za-z0-9_]+\\.[a-zA-Z]+", list.getSelectedValue())) {
                    new FileProperties(getIcon(f), f.getName(), Long.toString(f.length()), f.getAbsolutePath(),
                            FileTime.getCreateTime(path + "\\" + list.getSelectedValue()),
                            FileTime.getModifiedTime(path + "\\" + list.getSelectedValue()),
                            FileTime.getLatestAccessTime(path + "\\" + list.getSelectedValue()));
                } else {
                    DirectoryInfo info = new DirectoryInfo();
                    long size = info.getDirSize(f);
                    new FileProperties(getIcon(f), f.getName(), Long.toString(size), info.File_Num, info.Directory_Num,
                            f.getAbsolutePath(), FileTime.getCreateTime(path + "\\" + list.getSelectedValue()));
                }
            } else
                JOptionPane.showMessageDialog(this, "请选择一项列表");
        }
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

    public void addElement() {
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            model.addElement(tempList[i].getName());
        }
    }

    class CellRender extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            setText(value.toString());
            setIcon(FileManagerJFrame.getIcon(new File(path, value.toString())));
            if (isSelected) {
                setForeground(Color.WHITE);
                setBackground(Color.BLUE);         
            } else {
                setForeground(Color.BLACK);
                setBackground(Color.WHITE);
            }
            return this;
        }
    }

    private static Icon getIcon(File f) {
        if (f != null && f.exists()) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            return fsv.getSystemIcon(f);
        }
        return null;
    }

}