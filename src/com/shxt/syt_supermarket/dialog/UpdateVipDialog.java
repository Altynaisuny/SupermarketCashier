package com.shxt.syt_supermarket.dialog;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;

import com.shxt.syt_supermarket.tools.DB;

public class UpdateVipDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public UpdateVipDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open(String[] arr) {
		createContents(arr);
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
	private void createContents(String[] arr) {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(534, 425);
		shell.setText(getText());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(48, 32, 61, 17);
		lblNewLabel.setText("id");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(48, 90, 61, 17);
		lblNewLabel_1.setText("name");

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(48, 154, 61, 17);
		lblNewLabel_2.setText("score");

		text = new Text(shell, SWT.BORDER);
		text.setEnabled(false);
		text.setBounds(180, 32, 73, 23);
		text.setText(arr[0]);

		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(180, 84, 73, 23);
		text.setText(arr[1]);

		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(180, 148, 73, 23);
		text.setText(arr[2]);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 确认修改
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				String sql = "update vip set name=" + "'" + text_1.getText()
						+ "'" + "score=" + "'" + text_2.getText() + "'"
						+ "where id=" + text.getText();
				DB db = new DB();
				int num = db.update(sql);
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.YES
						| SWT.NO);
				if (text_1 != null && text_2 != null) {
					if (num == 1) {
						mb.setMessage("修改成功");
						mb.setText("提示");
						mb.open();
					} else {
						mb.setMessage("修改失败");
						mb.setText("提示");
						mb.open();
					}
				} else {
					mb.setMessage("格式错误，请重新输入");
					mb.setText("提示");
					mb.open();
				}

			}
		});
		btnNewButton.setBounds(58, 283, 80, 27);
		btnNewButton.setText("\u786E\u8BA4\u4FEE\u6539");

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 返回
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton_1.setBounds(315, 283, 80, 27);
		btnNewButton_1.setText("\u8FD4\u56DE");

		Label lblNewLabel_3 = new Label(shell, SWT.BORDER);
		lblNewLabel_3.setBounds(310, 32, 152, 161);

	}

}
