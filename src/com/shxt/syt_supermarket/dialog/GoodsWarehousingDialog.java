package com.shxt.syt_supermarket.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class GoodsWarehousingDialog extends Dialog {

	protected Object result;
	/**
	 * 商品名称
	 */
	Label lblNewLabel_3;
	/**
	 * 地址
	 */
	Label lblNewLabel_7;
	/**
	 * 价格
	 */
	Label lblNewLabel_9;
	/**
	 * 当前库存量
	 */
	Label lblNewLabel_10;
	protected Shell shell;
	/**
	 * 本次入商品id
	 */
	private Text text;
	/**
	 * 本次入库商品数量
	 */
	private Text text_4;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public GoodsWarehousingDialog(Shell parent, int style) {
		super(parent, style);
		setText("商品入库");
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
		shell.setSize(444, 449);
		shell.setText(getText());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(34, 36, 61, 17);
		lblNewLabel.setText("\u5546\u54C1\u7F16\u53F7");

		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setBounds(34, 255, 61, 17);
		lblNewLabel_4.setText("\u6B64\u6B21\u8FDB\u8D27\u91CF");

		text = new Text(shell, SWT.BORDER);
		text.addKeyListener(new KeyAdapter() {
			/**
			 * 释放键盘，读出该编号的商品信息
			 * @Override
			 */
			public void keyReleased(KeyEvent e) {
				DB db = new DB();
				if(!text.getText().equals("")){
					String sql = "select *from warehouse where id="
							+ text.getText();
					ArrayList<String[]> arr = db.arrQuery(sql);
					if(arr.size()==1){
						// 商品名称
						lblNewLabel_3.setText(arr.get(0)[1]);
						// 地址
						lblNewLabel_7.setText(arr.get(0)[2]);
						// 价格
						lblNewLabel_9.setText(arr.get(0)[3]);
						// 当前库存量
						lblNewLabel_10.setText(arr.get(0)[4]);
					}else{
						lblNewLabel_3.setText("");
						lblNewLabel_7.setText("");
						lblNewLabel_9.setText("");
						lblNewLabel_10.setText("");
						text.setText("");
						MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
						mb.setMessage("并没有该商品");
						mb.setText("提示");
						mb.open();
					}
				}
				
			}
		});
		text.setBounds(156, 36, 95, 23);

		text_4 = new Text(shell, SWT.BORDER);
		text_4.setBounds(156, 252, 95, 23);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 入库
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// 首先将数据写入warehouse中
				DB db = new DB();
				ArrayList<String[]> arr = db
						.arrQuery("select *from warehouse where id="
								+ text.getText());
				int result = Integer.parseInt(text_4.getText())
						+ Integer.parseInt(arr.get(0)[4]);
				String sql = "update warehouse set number=" + result
						+ " where id=" + text.getText();

				int num = db.update(sql);// 变更warehouse

				// 为了记录下每一次的进货记录，要将每一条数据插入到purchase中，该数据包含的字段有，date，id,name,number，进货不用计算，只显示进货量
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String time = sdf.format(d);
				String newsql = "insert into purchase values " + "(" + "'"
						+ time + "'" + "," + "'" + text.getText().trim() + "'"
						+ "," + "'" + arr.get(0)[1] + "'" + "," + "'"
						+ text_4.getText() + "'" + ")";// 检验，
				// 有错,同一天插入的确实有错，因为数据不是唯一的，时间，id，都有可能一样，假如一样，就合并相同商品的，
				// 判断，时间，id，用date和id作为主键，用秒，秒数一定是唯一的,接下来的就是判断了

				int m = db.update(newsql);// 变更purchase

				if (num == 1 && m == 1) {
					// 成功后再载入数据库
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setMessage("入库成功");
					mb.setText("提示");
					mb.open();
					shell.close();
				} else {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setMessage("入库失败");
					mb.setText("提示");
					mb.open();
					shell.close();
				}

			}
		});
		btnNewButton.setBounds(66, 303, 80, 27);
		btnNewButton.setText("\u5165\u5E93");

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
		btnNewButton_1.setBounds(294, 303, 80, 27);
		btnNewButton_1.setText("\u53D6\u6D88");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(115, 361, 192, 17);
		lblNewLabel_1
				.setText("**\u9002\u5408\u4ED3\u5E93\u5DF2\u6709\u7684\u5546\u54C1");

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(34, 85, 61, 17);
		lblNewLabel_2.setText("\u5546\u54C1\u540D\u79F0");

		lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(156, 85, 250, 17);

		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setBounds(34, 127, 61, 17);
		lblNewLabel_5.setText("\u5730\u5740");

		Label label = new Label(shell, SWT.NONE);
		label.setBounds(34, 168, 61, 17);
		label.setText("\u4EF7\u683C");

		lblNewLabel_7 = new Label(shell, SWT.NONE);
		lblNewLabel_7.setBounds(156, 127, 137, 17);

		Label lblNewLabel_8 = new Label(shell, SWT.NONE);
		lblNewLabel_8.setBounds(34, 216, 61, 17);
		lblNewLabel_8.setText("\u5F53\u524D\u5E93\u5B58\u91CF");

		lblNewLabel_9 = new Label(shell, SWT.NONE);
		lblNewLabel_9.setBounds(156, 168, 61, 17);

		lblNewLabel_10 = new Label(shell, SWT.NONE);
		lblNewLabel_10.setBounds(156, 216, 61, 17);

	}
}
