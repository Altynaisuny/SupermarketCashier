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
			 * 兑换礼品1
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.NO);
				mb.setMessage("确认要兑换吗");
				mb.setText("提示");
				int num=mb.open();
				if(num==64){
					//先查询vip，看有多少分，够不够兑换
					DB db=new DB();
					String sql="select *from vip where id="+id;
					ArrayList<String[]> arr = db.arrQuery(sql);
					if(Double.parseDouble(arr.get(0)[2])<1000){
						//提示无法兑换
						mb.setMessage("积分不够");
						mb.setText("提示");
						mb.open();
					}else{
						//积分够数，就兑换礼品，并减少积分数
						double score=Double.parseDouble(arr.get(0)[2]);//当前的积分
						score=score-1000;//积分兑换礼品，扣除积分
						String newsql="update vip set score="+String.valueOf(score)+" where id="+id;
						int n=db.update(newsql);
						MessageBox mt=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);

						if(n!=0){
							mt.setMessage("兑换成功");
							mt.setText("提示");
							mt.open();
						}else{
							mt.setMessage("兑换失败");
							mt.setText("提示");
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
			 * 兑换礼品2
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.NO);
				mb.setMessage("确认要兑换吗");
				mb.setText("提示");
				int num=mb.open();
				if(num==64){
					//先查询vip，看有多少分，够不够兑换
					DB db=new DB();
					String sql="select *from vip where id="+id;
					ArrayList<String[]> arr = db.arrQuery(sql);
					if(Double.parseDouble(arr.get(0)[2])<500){
						//提示无法兑换
						mb.setMessage("积分不够");
						mb.setText("提示");
						mb.open();
					}else{
						//积分够数，就兑换礼品，并减少积分数
						double score=Double.parseDouble(arr.get(0)[2]);//当前的积分
						score=score-500;//积分兑换礼品，扣除积分
						String newsql="update vip set score="+String.valueOf(score)+" where id="+id;
						int n=db.update(newsql);
						MessageBox mt=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);

						if(n!=0){
							mt.setMessage("兑换成功");
							mt.setText("提示");
							mt.open();
						}else{
							mt.setMessage("兑换失败");
							mt.setText("提示");
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
			 * 撤销
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
