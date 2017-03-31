package com.shxt.syt_supermarket.dialog;

/**
 * 修改商品信息
 */
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

public class InfoAlterDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public InfoAlterDialog(Shell parent, int style) {
		super(parent, style);
		setText("信息更改");
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
		shell.setSize(450, 408);
		shell.setText(getText());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(34, 10, 61, 17);
		lblNewLabel.setText("id");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(34, 49, 61, 17);
		lblNewLabel_1.setText("name");

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setText("address");
		lblNewLabel_2.setBounds(34, 98, 61, 17);

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(34, 145, 61, 17);
		lblNewLabel_3.setText("price");

		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setBounds(34, 198, 61, 17);
		lblNewLabel_4.setText("number");

		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setBounds(34, 245, 88, 17);
		lblNewLabel_5.setText("opening price");

		text = new Text(shell, SWT.BORDER);
		text.setEnabled(false);
		text.setBounds(144, 4, 73, 23);
		text.setText(arr[0]);// dialog接受来自editor的数据

		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(144, 49, 73, 23);
		text_1.setText(arr[1]);

		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(144, 98, 73, 23);
		text_2.setText(arr[2]);

		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(144, 145, 73, 23);
		text_3.setText(arr[3]);

		text_4 = new Text(shell, SWT.BORDER);
		text_4.setBounds(144, 198, 73, 23);
		text_4.setText(arr[4]);

		text_5 = new Text(shell, SWT.BORDER);
		text_5.setBounds(144, 245, 73, 23);
		text_5.setText(arr[5]);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 确定修改
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				DB db = new DB();
				// ***SQL语句报错
				String newsql = "update warehouse set name=" + "'"
						+ text_1.getText() + "'" + "," + "address=" + "'"
						+ text_2.getText() + "'" + "," + "price=" + "'"
						+ text_3.getText() + "'" + "," + "number=" + "'"
						+ text_4.getText() + "'" + "," + "openingprice=" + "'"
						+ text_5.getText() + "'" + "where id=" + text.getText();

				int num = db.update(newsql);

				if (num >= 1) {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setMessage("修改成功");
					mb.setText("提示");
					mb.open();
					shell.close();
				} else {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setMessage("修改失败");
					mb.setText("提示");
					mb.open();
					shell.close();
				}
				// 怎么刷新此时的表格内容
			}
		});
		btnNewButton.setBounds(287, 88, 80, 27);
		btnNewButton.setText("\u786E\u5B9A\u4FEE\u6539");

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 取消
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton_1.setBounds(287, 177, 80, 27);
		btnNewButton_1.setText("\u53D6\u6D88");

	}
}
