package OperationSystem.utils;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class Notepad extends JFrame implements ActionListener {

	// 窗体和输入区域
	JPanel pl = new JPanel();
	JTextArea myarea = new JTextArea();
	String path="";
	String textContent = "";// 编辑框中的内容

	UndoManager undoManager = new UndoManager();// 撤销管理器

	public Notepad(String path) {
		initComponment();// 面板初始化
		this.path=path;
		try {
			FileReader file_reader = new FileReader(path);// 此处必须要捕获异常
			BufferedReader br = new BufferedReader(file_reader);
			String temp = "";
			while (br.ready())// 判断缓冲区是否为空，非空时返回true
			{
				int c = br.read();
				temp = temp + (char) c;
			}
			myarea.setText(temp);
			br.close();
			file_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textContent = myarea.getText();
		setTitle("记事本-" + path);
	}

	private void initComponment() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// 菜单栏
		JMenuBar mb = new JMenuBar();

		// 弹出菜单
		final JPopupMenu myPopMenu = new JPopupMenu();
		JMenuItem copy_pop = new JMenuItem("复制");
		JMenuItem cut_pop = new JMenuItem("剪切");
		JMenuItem paste_pop = new JMenuItem("粘贴");
		JMenuItem delete_pop = new JMenuItem("删除");
		JMenuItem exit_pop = new JMenuItem("清空");

		myPopMenu.add(cut_pop);
		myPopMenu.add(copy_pop);
		myPopMenu.add(delete_pop);
		myPopMenu.add(paste_pop);
		myPopMenu.add(exit_pop);

		// 绑定监听器
		cut_pop.addActionListener(this);
		copy_pop.addActionListener(this);
		delete_pop.addActionListener(this);
		paste_pop.addActionListener(this);
		exit_pop.addActionListener(this);

		// 菜单
		JMenu file = new JMenu("文件");
		JMenu edit = new JMenu("编辑");

		// 子菜单
		JMenuItem save = new JMenuItem("保存");
		JMenuItem save_as = new JMenuItem("另存为");
		JMenuItem exit = new JMenuItem("退出");

		JMenuItem copy = new JMenuItem("复制");
		JMenuItem cut = new JMenuItem("剪切");
		JMenuItem paste = new JMenuItem("粘贴");
		JMenuItem delete = new JMenuItem("删除");
		JMenuItem search = new JMenuItem("查找和替换");

		// 绑定监听事件

		save.addActionListener(this);
		save_as.addActionListener(this);
		exit.addActionListener(this);

		copy.addActionListener(this);
		cut.addActionListener(this);
		paste.addActionListener(this);
		delete.addActionListener(this);
		search.addActionListener(this);

		// 将菜单和相应的子菜单添加到菜单栏
		mb.add(file);
		mb.add(edit);

		file.add(save);
		file.add(save_as);
		file.add(exit);

		edit.add(copy);
		edit.add(cut);
		edit.add(paste);
		edit.add(delete);
		edit.add(search);

		// 给文本区域添加滚动条
		myarea.add(myPopMenu);
		JScrollPane scrollpane = new JScrollPane(myarea);
		add(scrollpane);
		// 主窗口
		setTitle("记事本");
		setSize(600, 400);
		setLocation(400, 300);
		// 添加菜单栏
		setJMenuBar(mb);

		// 窗口监听
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				if (!myarea.getText().equals(textContent)) {
					int result = JOptionPane.showConfirmDialog(null, "文件内容已改变，确认保存退出吗？", "警告",
							JOptionPane.YES_NO_OPTION);
					switch (result) {
						case JOptionPane.NO_OPTION:
							System.exit(0);
							break;
						case JOptionPane.YES_OPTION:
							save();
							System.exit(0);
							break;
						default:
							break;
					}
				} else {
					System.exit(0);
				}
			}
		});

		// 键盘监听
		myarea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {

				// ctrl+f实现查找功能
				if ((ke.getKeyCode() == KeyEvent.VK_F)
						&& (ke.isControlDown())) {
					// 查找对话框
					JDialog search = new JDialog(Notepad.this, "查找和替换");
					search.setSize(200, 100);
					search.setLocation(450, 350);
					JLabel label_1 = new JLabel("查找的内容");
					JLabel label_2 = new JLabel("替换的内容");
					final JTextField textField_1 = new JTextField(5);
					final JTextField textField_2 = new JTextField(5);
					JButton buttonFind = new JButton("查找");
					JButton buttonChange = new JButton("替换");
					JPanel panel = new JPanel(new GridLayout(2, 3));
					panel.add(label_1);
					panel.add(textField_1);
					panel.add(buttonFind);
					panel.add(label_2);
					panel.add(textField_2);
					panel.add(buttonChange);
					search.add(panel);
					search.setVisible(true);

					// 为查找下一个 按钮绑定监听事件
					buttonFind.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String findText = textField_1.getText();// 查找的字符串

							String textArea = myarea.getText();// 当前文本框的内容
							start = textArea.indexOf(findText, end);
							end = start + findText.length();
							if (start == -1)// 没有找到
							{
								JOptionPane.showMessageDialog(null, "“" + findText + "”" + "已经查找完毕", "记事本",
										JOptionPane.WARNING_MESSAGE);
								myarea.select(start, end);
							} else {
								myarea.select(start, end);
							}

						}
					});
					// 为替换按钮绑定监听时间
					buttonChange.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String changeText = textField_2.getText();// 替换的字符串
							myarea.select(start, end);
							myarea.replaceSelection(changeText);
							myarea.select(start, end);
						}
					});

				}
				// esc退出
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (!myarea.getText().equals(textContent)) {
						int result = JOptionPane.showConfirmDialog(null, "文件内容已改变，确认保存退出吗？", "警告", 1);
						switch (result) {
							case JOptionPane.NO_OPTION:
								System.exit(0);
								break;
							case JOptionPane.YES_OPTION:
								save();
								System.exit(0);
								break;
							case JOptionPane.CANCEL_OPTION:
								break;
							default:
								break;
						}
					}
				}

			}
		});

		// 鼠标监听
		myarea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int mods = e.getModifiers();
				// 鼠标右键
				if ((mods & InputEvent.BUTTON3_MASK) != 0) {
					// 弹出菜单
					myPopMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});

	}

	// 相关变量
	int start = 0;// 查找开始位置
	int end = 0;// 查找结束位置

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			if (e.getActionCommand() == "保存") {
				save();
			} else if (e.getActionCommand() == "另存为") {
				otherSave();
			} else if (e.getActionCommand() == "退出") {
				if (!myarea.getText().equals(textContent)) {
					int result = JOptionPane.showConfirmDialog(null, "文件内容已改变，确认保存退出吗？", "警告", 1);
					switch (result) {
						case JOptionPane.NO_OPTION:
							System.exit(0);
							break;
						case JOptionPane.YES_OPTION:
							save();
							System.exit(0);
							break;
						case JOptionPane.CANCEL_OPTION:
							break;
						default:
							break;
					}
				} else {
					System.exit(0);

				}
			} else if (e.getActionCommand() == "查找和替换") {
				// 查找对话框
				JDialog search = new JDialog(this, "查找和替换");
				search.setSize(200, 100);
				search.setLocation(450, 350);
				JLabel label_1 = new JLabel("查找的内容");
				JLabel label_2 = new JLabel("替换的内容");
				final JTextField textField_1 = new JTextField(5);
				final JTextField textField_2 = new JTextField(5);
				JButton buttonFind = new JButton("查找");
				JButton buttonChange = new JButton("替换");
				JPanel panel = new JPanel(new GridLayout(2, 3));
				panel.add(label_1);
				panel.add(textField_1);
				panel.add(buttonFind);
				panel.add(label_2);
				panel.add(textField_2);
				panel.add(buttonChange);
				search.add(panel);
				search.setVisible(true);

				// 为查找下一个 按钮绑定监听事件
				buttonFind.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String findText = textField_1.getText();// 查找的字符串

						String textArea = myarea.getText();// 当前文本框的内容
						start = textArea.indexOf(findText, end);
						end = start + findText.length();
						// 没有找到
						if (start == -1) {
							JOptionPane.showMessageDialog(null, "“" + findText + "”" + "已经查找完毕", "记事本",
									JOptionPane.WARNING_MESSAGE);
							myarea.select(start, end);
						} else {
							myarea.select(start, end);
						}

					}
				});
				// 为替换按钮绑定监听事件
				buttonChange.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String changeText = textField_2.getText();// 替换的字符串
						myarea.select(start, end);
						myarea.replaceSelection(changeText);
						myarea.select(start, end);
					}
				});

			} else if (e.getActionCommand() == "复制") {
				copy();
			} else if (e.getActionCommand() == "粘贴") {
				paste();
			} else if (e.getActionCommand() == "剪切") {
				cut();
			} else if (e.getActionCommand() == "删除") {
				delete();
			} else if (e.getActionCommand() == "清空") {

				int result = JOptionPane.showConfirmDialog(null, "确认清空所有文字吗？", "警告", 1);
				if (result == JOptionPane.OK_OPTION) {
					// myarea.replaceRange(null,0,textContent.length());
					myarea.setText(null);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// 保存
	private void save() {
			try {
				File file = new File(path);
				FileWriter file_writer = new FileWriter(file);
				// 将文件输出流包装进缓冲区
				BufferedWriter bw = new BufferedWriter(file_writer);
				PrintWriter pw = new PrintWriter(bw);

				pw.print(myarea.getText());
				textContent = myarea.getText();
				pw.close();
				bw.close();
				file_writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	// 另存为
	private void otherSave() {
		FileDialog fileDialog = new FileDialog(this, "另存为", FileDialog.SAVE);
		fileDialog.setFile("*.txt");
		fileDialog.setVisible(true);
		if (fileDialog.getFile() != null) {
			// 写入文件
			FileWriter fw;
			try {
				fw = new FileWriter(fileDialog.getDirectory() + fileDialog.getFile());
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				pw.print(myarea.getText());
				textContent = myarea.getText();
				pw.close();
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 剪切
	private void cut() {
		copy();
		delete();
	}

	// 复制
	private void copy() {
		if (myarea.getSelectedText() == null) {
			JOptionPane.showMessageDialog(null, "你没有选中任何文字！", "记事本", JOptionPane.WARNING_MESSAGE);
		}
		Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(myarea.getSelectedText());
		clipBoard.setContents(stringSelection, null);
	}

	// 粘贴
	private void paste() throws UnsupportedFlavorException, IOException {
		String content_copy = "";
		// 构造系统剪切板
		Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();

		// 获取剪切板内容
		Transferable content = clipBoard.getContents(null);

		if (content != null) {
			// 检查是否是文本类型
			if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				content_copy = (String) content.getTransferData(DataFlavor.stringFlavor);

				// 判断文本框中有无文字选中
				if (myarea.getSelectedText() != null) {
					myarea.replaceSelection(content_copy);
				} else {
					myarea.insert(content_copy, myarea.getSelectionStart());
				}
			}
		}
	}

	// 删除
	private void delete() {

		if (myarea.getSelectedText() == null) {
			JOptionPane.showMessageDialog(null, "你没有选中任何文字！", "记事本", JOptionPane.WARNING_MESSAGE);
		}
		myarea.replaceSelection("");
	}
	public static void main(String[] args) {
		new Notepad("E:\\programming\\java\\school\\src\\OperationSystem\\utils\\Notepad.java").setVisible(true);;
	}
}