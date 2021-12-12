package OperationSystem.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.jar.Attributes.Name;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FileProperties extends JFrame implements ActionListener{
	public static FileProperties _instance = null;		
	public Icon icon = null;
	public String path;
	public JTextField NameField, Location;
	public JLabel ImageLabel, SizeLabel, Size, LocationLabel, CreateTimeLabel, CreateTime, ModifyTimeLabel, ModifyTime, AccessTimeLabel, AccessTime,
	TypeLabel, Type, IncludeLabel, Include, FileSysLabel, FileSys, UsedSizeLabel, UsedSize, AvaiSizeLabel, AvaiSize, TotalSizeLabel, TotalSize;	
	public JPanel HeadPanel, MidPanel, BotPanel;
	public JButton EnsureBtn;
	public double Used, Available, Total;
	
	public FileProperties(Icon icon, String name, String size, String url, String CreateTime, String ModifyTime, String AccessTime){//文件右击选择属性时
		this._instance = this;
		this.setTitle("文件属性");
		this.setBounds(500, 500, 350, 630);
		this.getContentPane().setLayout(null);
		Init1();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.path = url;
		this.icon = icon;
		ImageLabel.setIcon(this.icon);
		this.NameField.setText(name);
		NameField.setEditable(false);
		this.Size.setText(size);
		this.Location.setText(url);
		this.CreateTime.setText(CreateTime);
		this.ModifyTime.setText(ModifyTime);
		this.AccessTime.setText(AccessTime);
	}
	
	public FileProperties(Icon icon, String name, String size, long File_Num, long D_Num, String url, String CreateTime){//文件夹右击选择属性时
		this._instance = this;
		this.setTitle("文件夹属性");
		this.setBounds(500, 500, 350, 630);
		this.getContentPane().setLayout(null);
		Init2();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		this.path = url;
		this.icon = icon;
		ImageLabel.setIcon(this.icon);
		this.NameField.setText(name);
		NameField.setEditable(false);
		this.Type.setText("文件夹");
		this.Include.setText(File_Num + "个文件," + D_Num + "个文件夹");
		this.Size.setText(size);
		this.Location.setText(url);
		this.CreateTime.setText(CreateTime);		
	}	
	private void Init1(){//文件属性界面初始化
		HeadPanel = new JPanel();
		HeadPanel.setBounds(10, 10, 330, 100);
		HeadPanel.setLayout(null);
		ImageLabel = new JLabel();
		ImageLabel.setBounds(50, 30, 40, 40);
		NameField = new JTextField();
		NameField.setBounds(80, 40, 200, 25);
		HeadPanel.add(ImageLabel);
		HeadPanel.add(NameField);
		HeadPanel.setBorder(BorderFactory.createTitledBorder("文件名称"));
		this.add(HeadPanel);
		
		//中间的Panel
		MidPanel = new JPanel();
		MidPanel.setBounds(10, 110, 330, 150);
		MidPanel.setLayout(null);
		MidPanel.setBorder(BorderFactory.createTitledBorder("大小及位置"));
		
		SizeLabel = new JLabel();
		SizeLabel.setLocation(10, 30);
		SizeLabel.setSize(50, 30);
		SizeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		SizeLabel.setText("大小:");
		Size = new JLabel();
		Size.setLocation(60, 30);
		Size.setSize(300, 30);
		Size.setFont(new Font("Serif", Font.PLAIN, 18));
		Size.setText("0MB");
		
		LocationLabel = new JLabel("位置：");
		LocationLabel.setBounds(10,80,60,20);
		LocationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		Location = new JTextField();
		Location.setSize(260,20);
		Location.setLocation(60,80);
		Location.setEditable(false);
		Location.setText(" ");
		Location.setFont(new Font("Serif", Font.PLAIN, 16));
		MidPanel.add(SizeLabel);
		MidPanel.add(Size);
		MidPanel.add(LocationLabel);
		MidPanel.add(Location);		
		this.add(MidPanel);
		
		//底部的Panel
		BotPanel = new JPanel();
		BotPanel.setBounds(10, 270, 330, 270);
		BotPanel.setLayout(null);
		BotPanel.setBorder(BorderFactory.createTitledBorder("相关时间"));
		
		CreateTimeLabel = new JLabel("创建时间：");
		CreateTimeLabel.setBounds(10,50,90,20);
		CreateTimeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
	    CreateTime = new JLabel("‎2015‎年‎10‎月‎30‎日,‏‎6:15:27");
	    CreateTime.setBounds(100,50,200,20);
	    CreateTime.setFont(new Font("Serif", Font.PLAIN, 18));
	    
	    ModifyTimeLabel = new JLabel("修改时间：");
	    ModifyTimeLabel.setBounds(10,100,90,20);
	    ModifyTimeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
	    ModifyTime = new JLabel("‎2015‎年‎10‎月‎30‎日,‏‎6:15:27");
	    ModifyTime.setBounds(100,100,200,20);
	    ModifyTime.setFont(new Font("Serif", Font.PLAIN, 18));
	    
	    AccessTimeLabel = new JLabel("访问时间：");
	    AccessTimeLabel.setBounds(10,150,90,20);
	    AccessTimeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
	    AccessTime = new JLabel("‎2015‎年‎10‎月‎30‎日,‏‎6:15:27");
	    AccessTime.setBounds(100,150,200,20);
	    AccessTime.setFont(new Font("Serif", Font.PLAIN, 18));
	    
	    BotPanel.add(CreateTimeLabel);
	    BotPanel.add(CreateTime);
	    BotPanel.add(ModifyTimeLabel);
	    BotPanel.add(ModifyTime);	  
	    BotPanel.add(AccessTimeLabel);
	    BotPanel.add(AccessTime);	  
		this.add(BotPanel);
		
		//确定按钮
		EnsureBtn = new JButton("确定");
		EnsureBtn.setBounds(240, 550, 90, 30);
		EnsureBtn.addActionListener(this);
		this.add(EnsureBtn);
	}
	
	private void Init2(){//文件夹属性界面初始化
		HeadPanel = new JPanel();
		HeadPanel.setBounds(10, 10, 330, 100);
		HeadPanel.setLayout(null);
		ImageLabel = new JLabel();
		ImageLabel.setBounds(50, 30, 40, 40);
		NameField = new JTextField();
		NameField.setBounds(80, 40, 200, 25);
		HeadPanel.add(ImageLabel);
		HeadPanel.add(NameField);
		HeadPanel.setBorder(BorderFactory.createTitledBorder("文件夹名称"));
		this.add(HeadPanel);
		
		//中间的Panel
		MidPanel = new JPanel();
		MidPanel.setBounds(10, 110, 330, 190);
		MidPanel.setLayout(null);
		MidPanel.setBorder(BorderFactory.createTitledBorder("参数"));		
		
		TypeLabel = new JLabel();
		TypeLabel.setLocation(10, 30);
		TypeLabel.setSize(50, 20);
		TypeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		TypeLabel.setText("类型:");
		Type = new JLabel();
		Type.setLocation(60, 30);
		Type.setSize(300, 20);
		Type.setFont(new Font("Serif", Font.PLAIN, 18));
		Type.setText("文件夹");
		
		SizeLabel = new JLabel();
		SizeLabel.setLocation(10, 65);
		SizeLabel.setSize(50, 20);
		SizeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		SizeLabel.setText("大小:");
		Size = new JLabel();
		Size.setLocation(60, 65);
		Size.setSize(300, 20);
		Size.setFont(new Font("Serif", Font.PLAIN, 18));
		Size.setText("0MB");
		
		LocationLabel = new JLabel("位置：");
		LocationLabel.setBounds(10,100,90,20);
		LocationLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		Location = new JTextField();
		Location.setSize(260,20);
		Location.setLocation(60,100);
		Location.setEditable(false);
		Location.setText(" ");
		Location.setFont(new Font("Serif", Font.PLAIN, 16));
		
		IncludeLabel = new JLabel("包含：");
		IncludeLabel.setBounds(10,135,90,20);
		IncludeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		Include = new JLabel();
		Include.setSize(260,20);
		Include.setLocation(60,135);		
		Include.setText("100个文件,100个文件夹");
		Include.setFont(new Font("Serif", Font.PLAIN, 18));
		
		MidPanel.add(TypeLabel);
		MidPanel.add(Type);
		MidPanel.add(SizeLabel);
		MidPanel.add(Size);
		MidPanel.add(LocationLabel);
		MidPanel.add(Location);		
		MidPanel.add(IncludeLabel);
		MidPanel.add(Include);
		this.add(MidPanel);
		
		//底部的Panel
		BotPanel = new JPanel();
		BotPanel.setBounds(10, 330, 330, 100);
		BotPanel.setLayout(null);
		BotPanel.setBorder(BorderFactory.createTitledBorder("相关时间"));
		
		CreateTimeLabel = new JLabel("创建时间：");
		CreateTimeLabel.setBounds(10,30,90,20);
		CreateTimeLabel.setFont(new Font("Serif", Font.PLAIN, 18));
	    CreateTime = new JLabel("‎2015‎年‎10‎月‎30‎日,‏‎6:15:27");
	    CreateTime.setBounds(100,30,200,20);
	    CreateTime.setFont(new Font("Serif", Font.PLAIN, 18));	    	   
	    
	    BotPanel.add(CreateTimeLabel);
	    BotPanel.add(CreateTime);
	   
		this.add(BotPanel);
		
		//确定按钮
		EnsureBtn = new JButton("确定");
		EnsureBtn.setBounds(240, 550, 90, 30);
		EnsureBtn.addActionListener(this);
		this.add(EnsureBtn);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == EnsureBtn){
			dispose();
		}
	}	

}
