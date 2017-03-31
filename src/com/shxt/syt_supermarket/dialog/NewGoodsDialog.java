package com.shxt.syt_supermarket.dialog;

/**
 * 新增商品
 */
import java.util.ArrayList;

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

public class NewGoodsDialog extends Dialog {

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
	public NewGoodsDialog(Shell parent, int style) {
		super(parent, style);
		setText("增加");
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
		shell.setSize(553, 461);
		shell.setText(getText());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(53, 65, 61, 17);
		lblNewLabel.setText("\u5546\u54C1\u7F16\u53F7");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(53, 115, 61, 17);
		lblNewLabel_1.setText("\u5546\u54C1\u540D\u79F0");

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(53, 171, 61, 17);
		lblNewLabel_2.setText("\u4EA7\u5730");

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(53, 229, 61, 17);
		lblNewLabel_3.setText("\u4EF7\u683C");

		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setBounds(53, 285, 61, 17);
		lblNewLabel_4.setText("\u8FDB\u8D27\u91CF");

		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setBounds(53, 342, 61, 17);
		lblNewLabel_5.setText("\u5355\u54C1\u8FDB\u4EF7");

		text = new Text(shell, SWT.BORDER);
		text.setBounds(205, 65, 73, 23);

		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(205, 115, 73, 23);

		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(205, 171, 73, 23);

		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(205, 229, 73, 23);

		text_4 = new Text(shell, SWT.BORDER);
		text_4.setBounds(205, 285, 73, 23);

		text_5 = new Text(shell, SWT.BORDER);
		text_5.setBounds(205, 342, 73, 23);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 增加 怎样保持插入数据的id是不同的，如果是相同的应该怎么修改(待解决)
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow()
						.getShell(), SWT.MIN | SWT.MAX | SWT.CLOSE);

				

				if (text.getText().equals("")//只要数据不全，就显示数据不完整，重新输入
						
						|| text_1.getText().trim().equals("")
						|| text_2.getText().trim().equals("")
						|| text_3.getText().trim().equals("")
						|| text_4.getText().trim().equals("")
						|| text_5.getText().trim().equals("")) {
					

					mb.setMessage("数据不完整，请再次输入");
					mb.setText("提示");
					mb.open();
				} else {//信息完整，下面开始判断是不是已经存在这个ID，假如存在无法无法再次插入，并提示

					DB db = new DB();
					String judgesql="select *from warehouse where id="+text.getText();
					ArrayList<String[]> ass = db.arrQuery(judgesql);
					if(ass.size()!=0){
						//编号重复了，不允许
						mb.setMessage("编号违法，请重新输入");
						mb.setText("提示");
						mb.open();
					}else{
						String sql = "insert into warehouse values (" + "'"
								+ text.getText().trim() + "'" + " ," + "'"
								+ text_1.getText().trim() + "'" + " ," + "'"
								+ text_2.getText().trim() + "'" + " ," + "'"
								+ text_3.getText().trim() + "'" + " ," + "'"
								+ text_4.getText().trim() + "'" + " ," + "'"
								+ text_5.getText().trim() + "'" + ")";
						int num = db.update(sql);
						if (num == 1) {
							
							mb.setMessage("增加成功");
							mb.setText("提示");
							mb.open();
							shell.close();

						} else {
						
							mb.setMessage("增加失败");
							mb.setText("提示");
							mb.open();
						}
					}
					
					
				}

			}
		});
		btnNewButton.setBounds(368, 201, 80, 101);
		btnNewButton.setText("\u589E\u52A0");

	}
}
