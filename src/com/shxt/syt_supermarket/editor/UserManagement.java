package com.shxt.syt_supermarket.editor;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableItem;

import com.shxt.syt_supermarket.dialog.NewUser;
import com.shxt.syt_supermarket.dialog.UpdateUserDialog;
import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UserManagement extends EditorPart implements IEditorInput {

	public static final String ID = "com.shxt.syt_supermarket.editor.UserManagement"; //$NON-NLS-1$
	private Table table;
	TableItem tableItem;
	int pageNum = 1;
	int maxPage;
	Label lblNewLabel;
	String sql = "select *from user";
	int pageCount = 5;

	public UserManagement() {
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setBounds(41, 77, 406, 229);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u7F16\u53F7");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("\u59D3\u540D");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("\u6743\u9650");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("\u5BC6\u7801");

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 修改
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				UpdateUserDialog ud = new UpdateUserDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						SWT.MIN | SWT.MAX | SWT.CLOSE);
				// 将数据传输到dialog中去，需要传输什么信息
				// dialog无法从数据库中接受数据吗
				int index = table.getSelectionIndex();
				if(index==-1){
					MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
					mb.setMessage("这里没有数据哦");
					mb.setText("提示");
					mb.open();
				}else{
					TableItem item = table.getItem(index);
					String[] arr = { item.getText(0), item.getText(1),
							item.getText(2), item.getText(3) };
					ud.open(arr);
					putTable();
				}
				
			}
		});
		btnNewButton.setBounds(480, 139, 80, 27);
		btnNewButton.setText("\u4FEE\u6539");

		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 新增用户
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				NewUser un = new NewUser(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MIN
						| SWT.CLOSE);
				un.open();
				putTable();
			}
		});
		btnNewButton_1.setBounds(480, 247, 80, 27);
		btnNewButton_1.setText("\u65B0\u589E");

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(162, 354, 61, 17);
		lblNewLabel.setText("1");

		putTable();

		Label label = new Label(container, SWT.NONE);
		label.setBounds(249, 354, 21, 17);
		label.setText("/");

		maxPage = getMaxPage();

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setBounds(298, 354, 61, 17);
		lblNewLabel_1.setText(String.valueOf(maxPage));

		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * 上一页
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				if (pageNum > 1) {
					pageNum--;
					putTable();
				} else {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setText("提示");
					mb.setMessage("亲，已经是第一页了哦");
					mb.open();
				}

			}
		});
		button.setBounds(58, 349, 80, 27);
		button.setText("\u4E0A\u4E00\u9875");

		Button btnNewButton_2 = new Button(container, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			/**
			 * 下一页
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				if (pageNum < maxPage) {
					pageNum++;
					putTable();
				} else {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setText("提示");
					mb.setMessage("亲，已经是最后一页了哦");
					mb.open();
				}

			}
		});
		btnNewButton_2.setBounds(404, 349, 80, 27);
		btnNewButton_2.setText("\u4E0B\u4E00\u9875");

		Button button_1 = new Button(container, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 删除
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();
				if(index==-1){
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setText("提示");
					mb.setMessage("这里没有数据哦");
					mb.open();
				}else{
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setText("提示");
					mb.setMessage("要删除该项吗");
					int n = mb.open();
			
					if (n == 32) {
						tableItem = table.getItem(index);
						String sql = "delete from user where id=" + "'"
								+ tableItem.getText(0) + "'";
						DB db = new DB();
						int m = db.update(sql);
						if (m == 1) {
							mb.setText("提示");
							mb.setMessage("删除成功");
							mb.open();
						} else {
							mb.setText("提示");
							mb.setMessage("删除失败");
							mb.open();
						}
					}
					putTable();
				}
				
			}
		});
		button_1.setBounds(480, 196, 80, 27);
		button_1.setText("\u5220\u9664");

		Label label_1 = new Label(container, SWT.NONE);
		label_1.setBounds(90, 434, 194, 17);
		label_1.setText("**\u6743\u96501\u4E3A\u7BA1\u7406\u5458\uFF0C\u6743\u96502\u4E3A\u6536\u94F6\u5458");

	}

	/**
	 * 显示当前页的内容
	 */
	public void putTable() {
		table.removeAll();
		DB db = new DB();
		ArrayList<String[]> arr = db.pageQuery(pageCount, pageNum, sql);
		for (int i = 0; i < arr.size(); i++) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			String[] temp = arr.get(i);
			tableItem.setText(temp);
		}
		lblNewLabel.setText(String.valueOf(pageNum));
	}

	/**
	 * 
	 * 
	 */
	public int getMaxPage() {

		DB db = new DB();
		ArrayList<String[]> arr = db.arrQuery(sql);
		int num = arr.size();
		if (num % pageCount == 0) {
			return num / pageCount;
		} else {
			return num / pageCount + 1;

		}

	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// Initialize the editor part
		this.setSite(site);
		this.setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "getname";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "gettooltiptext";
	}
}
