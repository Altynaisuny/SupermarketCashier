package com.shxt.syt_supermarket.dialog;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;

import com.shxt.syt_supermarket.tools.DB;

public class GiftDialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GiftDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(String id) {
		createContents(id);
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
	private void createContents(final String id) {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText("\u8D60\u54C1\u5151\u6362");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * �һ���Ʒ1
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.NO);
				mb.setMessage("ȷ��Ҫ�һ���");
				mb.setText("��ʾ");
				int num=mb.open();
				if(num==64){
					//�Ȳ�ѯvip�����ж��ٷ֣��������һ�
					DB db=new DB();
					String sql="select *from vip where id="+id;
					ArrayList<String[]> arr = db.arrQuery(sql);
					if(Double.parseDouble(arr.get(0)[2])<1000){
						//��ʾ�޷��һ�
						mb.setMessage("���ֲ���");
						mb.setText("��ʾ");
						mb.open();
					}else{
						//���ֹ������Ͷһ���Ʒ�������ٻ�����
						double score=Double.parseDouble(arr.get(0)[2]);//��ǰ�Ļ���
						score=score-1000;//���ֶһ���Ʒ���۳�����
						String newsql="update vip set score="+String.valueOf(score)+" where id="+id;
						int n=db.update(newsql);
						MessageBox mt=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);

						if(n!=0){
							mt.setMessage("�һ��ɹ�");
							mt.setText("��ʾ");
							mt.open();
						}else{
							mt.setMessage("�һ�ʧ��");
							mt.setText("��ʾ");
							mt.open();
						}
						shell.close();
					}
				}else{
					shell.close();
				}
			}
		});
		btnNewButton.setBounds(72, 45, 80, 27);
		btnNewButton.setText("\u5151\u6362\u793C\u54C11");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * �һ���Ʒ2
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.NO);
				mb.setMessage("ȷ��Ҫ�һ���");
				mb.setText("��ʾ");
				int num=mb.open();
				if(num==64){
					//�Ȳ�ѯvip�����ж��ٷ֣��������һ�
					DB db=new DB();
					String sql="select *from vip where id="+id;
					ArrayList<String[]> arr = db.arrQuery(sql);
					if(Double.parseDouble(arr.get(0)[2])<500){
						//��ʾ�޷��һ�
						mb.setMessage("���ֲ���");
						mb.setText("��ʾ");
						mb.open();
					}else{
						//���ֹ������Ͷһ���Ʒ�������ٻ�����
						double score=Double.parseDouble(arr.get(0)[2]);//��ǰ�Ļ���
						score=score-500;//���ֶһ���Ʒ���۳�����
						String newsql="update vip set score="+String.valueOf(score)+" where id="+id;
						int n=db.update(newsql);
						MessageBox mt=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);

						if(n!=0){
							mt.setMessage("�һ��ɹ�");
							mt.setText("��ʾ");
							mt.open();
						}else{
							mt.setMessage("�һ�ʧ��");
							mt.setText("��ʾ");
							mt.open();
						}
						shell.close();
					}
				}else{
					shell.close();
				}
			
			}
		});
		btnNewButton_1.setBounds(252, 45, 80, 27);
		btnNewButton_1.setText("\u5151\u6362\u793C\u54C12");
		
		Button btnNewButton_4 = new Button(shell, SWT.NONE);
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			/**
			 * ����
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton_4.setBounds(159, 179, 80, 27);
		btnNewButton_4.setText("\u64A4\u9500");

	}

}
