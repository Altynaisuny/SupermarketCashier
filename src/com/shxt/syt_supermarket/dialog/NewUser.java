package com.shxt.syt_supermarket.dialog;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;

import com.shxt.syt_supermarket.tools.DB;

public class NewUser extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	Spinner spinner;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public NewUser(Shell parent, int style) {
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
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(41, 36, 61, 17);
		lblNewLabel.setText("\u7F16\u53F7");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(41, 87, 61, 17);
		lblNewLabel_1.setText("\u59D3\u540D");

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(41, 131, 61, 17);
		lblNewLabel_2.setText("\u6743\u9650");

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(41, 182, 61, 17);
		lblNewLabel_3.setText("\u5BC6\u7801");
		// 权限
		spinner = new Spinner(shell, SWT.BORDER);
		spinner.setMaximum(2);
		spinner.setMinimum(1);
		spinner.setBounds(163, 125, 47, 23);
		// 编号
		text = new Text(shell, SWT.BORDER);
		text.setBounds(163, 36, 73, 23);
		// 姓名
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(163, 81, 73, 23);
		// 密码
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(163, 182, 73, 23);

		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * 确定
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				String sql = "insert into user values (" + "'" + text.getText()
						+ "'" + "," + "'" + text_1.getText() + "'" + "," + "'"
						+ spinner.getSelection() + "'" + "," + "'"
						+ text_2.getText() + "'" + ")";
				DB db = new DB();
				int num = db.update(sql);
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MIN
						| SWT.MAX | SWT.CLOSE);
				if (num == 1) {
					mb.setMessage("增加成功");
					mb.setText("提示");
					mb.open();
				} else {
					mb.setMessage("增加失败");
					mb.setText("提示");
					mb.open();
				}
				shell.close();

			}
		});
		button.setBounds(309, 77, 80, 27);
		button.setText("\u786E\u5B9A\u4E0A\u4F20");

	}
}
