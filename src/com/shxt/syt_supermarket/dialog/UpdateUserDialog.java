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

import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.PlatformUI;

/**
 * 修改选中项user的信息
 * 
 * @author sun
 * 
 */
public class UpdateUserDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_3;
	Spinner spinner;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public UpdateUserDialog(Shell parent, int style) {
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
		shell.setSize(450, 300);
		shell.setText(getText());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(36, 29, 61, 17);
		lblNewLabel.setText("\u7F16\u53F7");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setText("\u59D3\u540D");
		lblNewLabel_1.setBounds(36, 83, 61, 17);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(36, 138, 61, 17);
		lblNewLabel_2.setText("\u6743\u9650");

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(36, 191, 61, 17);
		lblNewLabel_3.setText("\u5BC6\u7801");

		text = new Text(shell, SWT.BORDER);
		text.setEnabled(false);
		text.setBounds(166, 29, 73, 23);
		text.setText(arr[0]);

		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(166, 83, 73, 23);
		text_1.setText(arr[1]);

		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(166, 191, 73, 23);
		text_3.setText(arr[3]);
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 确定修改
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				DB db = new DB();
				String sql = "update user set name=" + "'" + text_1.getText()
						+ "'" + "," + "power=" + spinner.getText() + ","
						+ " password=" + "'" + text_3.getText() + "'"
						+ "where id=" + text.getText();

				int n = db.update(sql);
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.NONE);

				if (n == 1) {
					mb.setMessage("修改陈功");
					mb.setText("提示");
					mb.open();
					shell.close();
				} else {
					mb.setMessage("修改失败");
					mb.setText("提示");
					mb.open();
					shell.close();
				}
			}
		});
		btnNewButton.setBounds(318, 83, 80, 27);
		btnNewButton.setText("\u786E\u5B9A");

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 撤销
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton_1.setBounds(318, 158, 80, 27);
		btnNewButton_1.setText("\u64A4\u9500");

		spinner = new Spinner(shell, SWT.BORDER);
		spinner.setMaximum(2);
		spinner.setMinimum(1);
		spinner.setBounds(166, 138, 47, 23);
		spinner.equals(arr[2]);

	}
}
