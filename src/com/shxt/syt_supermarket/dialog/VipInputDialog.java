package com.shxt.syt_supermarket.dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;

import com.shxt.syt_supermarket.core.Activator;
import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class VipInputDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_1;
	private Button btnNewButton_1;
	String resource;
	private Text text;
	private Text text_2;
	private Text text_3;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public VipInputDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX);
		shell.setSize(531, 448);
		shell.setText("\u4F1A\u5458\u7533\u8BF7");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(28, 58, 61, 17);
		lblNewLabel_1.setText("\u59D3\u540D");

		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(148, 55, 73, 23);

		btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 确定修改
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				if (text.getText().trim().equals("")
						|| text_1.getText().trim().equals("")
						|| text_2.getText().trim().equals("")
						|| text_3.getText().trim().equals("")) {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.MIN
							| SWT.MAX | SWT.CLOSE);
					mb.setMessage("数据不完整，请再次输入");
					mb.setText("提示");
					mb.open();
				} else {
					DB db = new DB();
					String sql = "insert into vip (name,score,qq,phone,address)values ("
							+ "'"
							+ text_1.getText()
							+ "'"
							+ " ,"
							+"'"
							+ "0"
							+"'"
							+ ","
							+"'"
							+text.getText()
							+ "'"
							+","
							+"'"
							+ text_2.getText()
							+ "'"
							+ ","
							+ "'"
							+ text_3.getText() + "'" + ")";

					int num = db.update(sql);
					if (num == 1) {
						MessageBox mb = new MessageBox(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), SWT.MIN | SWT.MAX | SWT.CLOSE);
						mb.setMessage("增加成功");
						mb.setText("提示");
						mb.open();
						shell.close();

					} else {
						MessageBox mb = new MessageBox(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), SWT.MIN | SWT.MAX | SWT.CLOSE);
						mb.setMessage("增加失败");
						mb.setText("提示");
						mb.open();
					}
				}

			}
		});
		btnNewButton_1.setBounds(114, 326, 80, 27);
		btnNewButton_1.setText("\u63D0\u4EA4");

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(28, 113, 61, 17);
		lblNewLabel.setText("QQ");

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(28, 172, 61, 17);
		lblNewLabel_2.setText("\u7535\u8BDD");

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(28, 235, 61, 17);
		lblNewLabel_3.setText("\u5730\u5740");

		text = new Text(shell, SWT.BORDER);
		
		text.setBounds(148, 113, 73, 23);

		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(148, 172, 73, 23);

		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(148, 235, 73, 23);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 取消
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton.setBounds(295, 326, 80, 27);
		btnNewButton.setText("\u53D6\u6D88");

	}
	
}
