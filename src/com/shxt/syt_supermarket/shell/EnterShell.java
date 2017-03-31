package com.shxt.syt_supermarket.shell;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.shxt.syt_supermarket.core.Application;
import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class EnterShell extends Shell {
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			EnterShell shell = new EnterShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public EnterShell(Display display) {
		super(display, SWT.RESIZE | SWT.TITLE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		text = new Text(this, SWT.BORDER);
		text.setBounds(150, 51, 73, 23);
		text.getText();

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel.setBounds(25, 51, 61, 17);
		lblNewLabel.setText("\u8D26\u53F7");

		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel_1.setBounds(25, 138, 61, 17);
		lblNewLabel_1.setText("\u5BC6\u7801");

		text_1 = new Text(this, SWT.BORDER | SWT.PASSWORD);
		text_1.addKeyListener(new KeyAdapter() {
			/**
			 * 回车键入时间
			 * @Override
			 */
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==13){

					String name = text.getText();
					String passWord = text_1.getText();
					String sql = "SELECT *FROM USER WHERE name=" + "'" + name + "'"
							+ " AND  PASSWORD=" + "'" + passWord + "'";

					if (name == null || passWord == null) {
						MessageBox mb = new MessageBox(EnterShell.this, SWT.CLOSE);
						mb.setText("提示");
						mb.setMessage("请正确输入");
						mb.open();
					} else {
						DB db = new DB();
						ArrayList<String[]> arr = db.arrQuery(sql);
						if (arr.size() == 1) {
							if (Integer.parseInt(arr.get(0)[2]) == 1) {
								Application.power = 1;
							} else if (Integer.parseInt(arr.get(0)[2]) == 2) {
								Application.power = 2;
							}
							// 输入的账号在数据库中存在，关闭shell
							EnterShell.this.close();
						} else {
							MessageBox mb_1 = new MessageBox(EnterShell.this,
									SWT.CLOSE);
							mb_1.setText("提示");
							mb_1.setMessage("用户名或者密码输入错误");
							mb_1.open();
						}
					}

				
				}
			}
		});
		text_1.setBounds(150, 138, 73, 23);
		text_1.getText();

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 判断并进入view
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				String name = text.getText();
				String passWord = text_1.getText();
				String sql = "SELECT *FROM USER WHERE name=" + "'" + name + "'"
						+ " AND  PASSWORD=" + "'" + passWord + "'";

				if (name == null || passWord == null) {
					MessageBox mb = new MessageBox(EnterShell.this, SWT.CLOSE);
					mb.setText("提示");
					mb.setMessage("请正确输入");
					mb.open();
				} else {
					DB db = new DB();
					ArrayList<String[]> arr = db.arrQuery(sql);
					if (arr.size() == 1) {
						if (Integer.parseInt(arr.get(0)[2]) == 1) {
							Application.power = 1;
						} else if (Integer.parseInt(arr.get(0)[2]) == 2) {
							Application.power = 2;
						}
						// 输入的账号在数据库中存在，关闭shell
						EnterShell.this.close();
					} else {
						MessageBox mb_1 = new MessageBox(EnterShell.this,
								SWT.CLOSE);
						mb_1.setText("提示");
						mb_1.setMessage("用户名或者密码输入错误");
						mb_1.open();
					}
				}

			}
		});
		btnNewButton.setBounds(64, 196, 80, 27);
		btnNewButton.setText("\u786E\u5B9A");

		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 退出登录界面
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				EnterShell.this.close();
			}
		});
		btnNewButton_1.setBounds(251, 196, 80, 27);
		btnNewButton_1.setText("\u9000\u51FA");

		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel_2.setBounds(130, 10, 232, 17);
		lblNewLabel_2
				.setText("\u6B22\u8FCE\u6765\u5230\u8D85\u5E02\u7BA1\u7406\u7CFB\u7EDF\uFF01");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
