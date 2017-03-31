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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

import com.shxt.syt_supermarket.dialog.GiftDialog;
import com.shxt.syt_supermarket.dialog.UpdateVipDialog;
import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class VipEditor extends EditorPart implements IEditorInput {

	public static final String ID = "com.shxt.syt_supermarket.editor.VipEditor"; //$NON-NLS-1$
	private Table table;
	TableItem tableItem;
	/**
	 * 最大页
	 */
	public int maxPage;
	/**
	 * 当前页
	 */
	public int pageNum = 1;
	/**
	 * 每页显示的项目个数
	 */
	public int pageCount = 3;
	Label lblNewLabel;
	Label lblNewLabel_1;
	String sql = "select *from vip";

	public VipEditor() {
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
		table.setBounds(80, 59, 407, 232);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u4F1A\u5458\u7F16\u53F7");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("\u540D\u79F0");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setText("\u79EF\u5206");
		tblclmnNewColumn_2.setWidth(100);

		tableItem = new TableItem(table, SWT.NONE);

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			/**
			 * 删除
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.YES
						| SWT.NO);
				mb.setMessage("是否要删除数据");
				mb.setText("警告");
				int num = mb.open();
				if (num == 64) {
					int index = table.getSelectionIndex();
					TableItem item = table.getItem(index);
					String newsql = "delete *from vip where id="
							+ item.getText(0);
					DB db = new DB();
					int u = db.update(newsql);
					if (u != 0) {
						mb.setText("提示");
						mb.setMessage("删除成功");
						mb.open();
						maxPage = getMaxPage();
						if (pageNum > maxPage) {
							lblNewLabel_1.setText(String.valueOf(maxPage));
						}
						putTable();
					} else {
						mb.setText("提示");
						mb.setMessage("删除失败");
						mb.open();
					}
				}
			}
		});
		menuItem.setText("\u5220\u9664");

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			/**
			 * 修改
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				UpdateVipDialog ud = new UpdateVipDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						SWT.MAX | SWT.MIN | SWT.CLOSE);
				int index = table.getSelectionIndex();
				TableItem item = table.getItem(index);
				String[] arr = { item.getText(0), item.getText(1),
						item.getText(2) };
				ud.open(arr);
				putTable();
			}
		});
		mntmNewItem.setText("\u4FEE\u6539");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u7B49\u7EA7");

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(252, 326, 20, 17);
		lblNewLabel.setText("1");

		putTable();

		maxPage = getMaxPage();

		lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setBounds(303, 326, 20, 17);
		lblNewLabel_1.setText(String.valueOf(maxPage));

		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setBounds(240, 326, 49, 17);
		lblNewLabel_2.setText("         /");

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
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
		btnNewButton.setBounds(133, 321, 80, 27);
		btnNewButton.setText("\u4E0A\u4E00\u9875");

		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
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
		btnNewButton_1.setBounds(357, 321, 80, 27);
		btnNewButton_1.setText("\u4E0B\u4E00\u9875");
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * 赠品管理
			 * 根据积分来判断应该兑换什么礼品，选择礼品种类，
			 * 根据选项来，用一个dialog来实现
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				
				GiftDialog gd=new GiftDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
				int index=table.getSelectionIndex();
				if(index==-1){
					MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
					mb.setMessage("这里没有数据哦");
					mb.setText("提示");
					mb.open();
				}else{
					TableItem item = table.getItem(index);
					//获取选中项的id
					String id=item.getText(0);
					gd.open(id);
				}
				putTable();
			}
		});
		button.setBounds(357, 386, 80, 27);
		button.setText("\u8D60\u54C1\u5151\u6362");

	}

	/**
	 * @author sun 显示当前页的内容,显示当前页的页码
	 */
	public void putTable() {
		table.removeAll();
		DB db = new DB();
		ArrayList<String[]> arr = db.pageQuery(pageCount, pageNum, sql);
		for (int i = 0; i < arr.size(); i++) {
			tableItem = new TableItem(table, SWT.NONE);

			tableItem.setText(0, arr.get(i)[0]);
			tableItem.setText(1, arr.get(i)[1]);
			tableItem.setText(2, arr.get(i)[2]);
			if (Double.parseDouble(arr.get(i)[2]) >= 1000) {
				tableItem.setText(3, "金牌会员");
			} else if (Double.parseDouble(arr.get(i)[2]) < 1000
					&& Double.parseDouble(arr.get(i)[2]) >= 500) {
				tableItem.setText(3, "银牌会员");
			} else {
				tableItem.setText(3, "铜牌会员");

			}

		}

		lblNewLabel.setText(String.valueOf(pageNum));
	}

	/**
	 * @author sun 最大有多少页
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
		return "getnam";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "gettool";
	}
}
