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
	 * ��Ʒ����
	 */
	Label lblNewLabel_3;
	/**
	 * ��ַ
	 */
	Label lblNewLabel_7;
	/**
	 * �۸�
	 */
	Label lblNewLabel_9;
	/**
	 * ��ǰ�����
	 */
	Label lblNewLabel_10;
	protected Shell shell;
	/**
	 * ��������Ʒid
	 */
	private Text text;
	/**
	 * ���������Ʒ����
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
		setText("��Ʒ���");
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
			 * �ͷż��̣������ñ�ŵ���Ʒ��Ϣ
			 * @Override
			 */
			public void keyReleased(KeyEvent e) {
				DB db = new DB();
				if(!text.getText().equals("")){
					String sql = "select *from warehouse where id="
							+ text.getText();
					ArrayList<String[]> arr = db.arrQuery(sql);
					if(arr.size()==1){
						// ��Ʒ����
						lblNewLabel_3.setText(arr.get(0)[1]);
						// ��ַ
						lblNewLabel_7.setText(arr.get(0)[2]);
						// �۸�
						lblNewLabel_9.setText(arr.get(0)[3]);
						// ��ǰ�����
						lblNewLabel_10.setText(arr.get(0)[4]);
					}else{
						lblNewLabel_3.setText("");
						lblNewLabel_7.setText("");
						lblNewLabel_9.setText("");
						lblNewLabel_10.setText("");
						text.setText("");
						MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
						mb.setMessage("��û�и���Ʒ");
						mb.setText("��ʾ");
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
			 * ���
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// ���Ƚ�����д��warehouse��
				DB db = new DB();
				ArrayList<String[]> arr = db
						.arrQuery("select *from warehouse where id="
								+ text.getText());
				int result = Integer.parseInt(text_4.getText())
						+ Integer.parseInt(arr.get(0)[4]);
				String sql = "update warehouse set number=" + result
						+ " where id=" + text.getText();

				int num = db.update(sql);// ���warehouse

				// Ϊ�˼�¼��ÿһ�εĽ�����¼��Ҫ��ÿһ�����ݲ��뵽purchase�У������ݰ������ֶ��У�date��id,name,number���������ü��㣬ֻ��ʾ������
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String time = sdf.format(d);
				String newsql = "insert into purchase values " + "(" + "'"
						+ time + "'" + "," + "'" + text.getText().trim() + "'"
						+ "," + "'" + arr.get(0)[1] + "'" + "," + "'"
						+ text_4.getText() + "'" + ")";// ���飬
				// �д�,ͬһ������ȷʵ�д���Ϊ���ݲ���Ψһ�ģ�ʱ�䣬id�����п���һ��������һ�����ͺϲ���ͬ��Ʒ�ģ�
				// �жϣ�ʱ�䣬id����date��id��Ϊ���������룬����һ����Ψһ��,�������ľ����ж���

				int m = db.update(newsql);// ���purchase

				if (num == 1 && m == 1) {
					// �ɹ������������ݿ�
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setMessage("���ɹ�");
					mb.setText("��ʾ");
					mb.open();
					shell.close();
				} else {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setMessage("���ʧ��");
					mb.setText("��ʾ");
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
			 * ȡ��
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
