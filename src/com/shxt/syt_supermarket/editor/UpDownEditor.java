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

import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class UpDownEditor extends EditorPart implements IEditorInput {

	public static final String ID = "com.shxt.syt_supermarket.editor.MyEditor"; //$NON-NLS-1$
	private Table table;
	Label lblNewLabel;
	String sql = "select *from warehouse ";
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
	public int pageCount = 6;

	public UpDownEditor() {
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
		table.setBounds(22, 20, 555, 296);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(71);
		tblclmnNewColumn.setText("\u5546\u54C1\u7F16\u53F7");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(73);
		tblclmnNewColumn_1.setText("\u5546\u54C1\u540D\u79F0");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(77);
		tblclmnNewColumn_2.setText("\u4EA7\u5730");

		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(54);
		tblclmnPrice.setText("\u5355\u4EF7");

		TableColumn tblclmnNumber = new TableColumn(table, SWT.NONE);
		tblclmnNumber.setWidth(82);
		tblclmnNumber.setText("\u5F53\u524D\u5E93\u5B58");

		TableColumn tblclmnOpeningPrice = new TableColumn(table, SWT.NONE);
		tblclmnOpeningPrice.setWidth(93);
		tblclmnOpeningPrice.setText("\u8FDB\u4EF7");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u662F\u5426\u4E0A\u67B6");

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(256, 353, 17, 17);
		lblNewLabel.setText("1");

		putTable();// ****

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setBounds(242, 353, 61, 17);
		lblNewLabel_1.setText("          /");

		maxPage = getMaxPage();// ****
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setBounds(302, 353, 29, 17);
		lblNewLabel_2.setText(String.valueOf(maxPage));

		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * 上一页 如果是首页，应该有提示，并且无法再进行操作
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
		button.setBounds(142, 348, 80, 27);
		button.setText("\u4E0A\u4E00\u9875");

		Button button_1 = new Button(container, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 下一页 如果是末页，提示是最后一页，无法操作
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
		button_1.setBounds(337, 348, 80, 27);
		button_1.setText("\u4E0B\u4E00\u9875");

		Label lblt = new Label(container, SWT.NONE);
		lblt.setBounds(74, 426, 161, 17);
		lblt.setText("**\u6CE8\u610F\uFF0C\u8BE5\u8868\u4E2DT\u8868\u793A\u5DF2\u7ECF\u4E0A\u67B6");

		Label lblNewLabel_3 = new Label(container, SWT.NONE);
		lblNewLabel_3.setBounds(251, 426, 80, 17);
		lblNewLabel_3.setText("F\u8868\u793A\u672A\u4E0A\u67B6");

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 上架
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// 读取仓库中的数据，插入到收银台中,如果shopping中有该条数据，就不在添加，
				//这有一个bug，假如选择的是空，怎么办
				int index = table.getSelectionIndex();
				if(index==-1){
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setText("提示");
					mb.setMessage("此处没有记录哦");
					mb.open();
				}else{
					TableItem ti = table.getItem(index);

					DB db = new DB();
					ArrayList<String[]> arr = db
							.arrQuery("select *from shopping where id=" + "'"
									+ ti.getText(0) + "'");
					if (arr.size() == 1) {
						MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell(), SWT.NONE);
						mb.setText("提示");
						mb.setMessage("该商品已经上架，无法再次添加");
						mb.open();
					} else {
						String newsql = "insert into shopping values" + "(" + "'"
								+ ti.getText(0) + "'" + "," + "'" + ti.getText(1) + "'"
								+ "," + "'" + ti.getText(2) + "'" + "," + "'"
								+ ti.getText(3) + "'" + ")";
						int n = db.update(newsql);
						if (n != 0) {
							MessageBox mb = new MessageBox(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), SWT.NONE);
							mb.setText("提示");
							mb.setMessage("上架成功");
							mb.open();
						} else {
							MessageBox mb = new MessageBox(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), SWT.NONE);
							mb.setText("提示");
							mb.setMessage("上架失败");
							mb.open();
						}
					}
					// 应该再刷新一次
					putTable();
				}
			}
				
		});
		btnNewButton.setBounds(466, 353, 80, 27);
		btnNewButton.setText("\u4E0A\u67B6");

		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 下架
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				int index = table.getSelectionIndex();
				if(index==-1){

					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.NONE);
					mb.setText("提示");
					mb.setMessage("此处没有记录哦");
					mb.open();
				
				}else{
					TableItem ti = table.getItem(index);
					String sql = "delete from shopping where id=" + "'"
							+ ti.getText(0) + "'";// 拼凑成sql语句
					// 符合条件的结果有几个
					DB db = new DB();
					ArrayList<String[]> arr = db
							.arrQuery("select *from shopping where id=" + "'"
									+ ti.getText(0) + "'");
					// 假如有一条

					if (arr.size() == 1) {
						int n = db.update(sql);
						if (n != 0) {
							MessageBox mb = new MessageBox(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), SWT.NONE);
							mb.setText("提示");
							mb.setMessage("成功");
							mb.open();
						} else {// shopping中没有此项
							MessageBox mb = new MessageBox(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), SWT.NONE);
							mb.setText("提示");
							mb.setMessage("失败");
							mb.open();
						}
					} else {
						MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell(), SWT.NONE);
						mb.setText("提示");
						mb.setMessage("该商品已经下架了,无法再次下架");
						mb.open();
					}
					putTable();

				}
				
			}
		});
		btnNewButton_1.setBounds(466, 402, 80, 27);
		btnNewButton_1.setText("\u4E0B\u67B6");
	}

	/**
	 * 查看商品是否已经上架，就是在shopping中检查，是不是有这一条数据
	 * 
	 */
	public char checkStatus(String id) {
		DB db = new DB();
		String sql = "select *from shopping where id =" + id;
		ArrayList<String[]> stt = db.arrQuery(sql);
		if (stt.size() == 1) {
			return 'T';
		} else {
			return 'F';
		}
	}

	/**
	 * @author sun 显示当前页的内容,显示当前页的页码
	 */
	public void putTable() {
		table.removeAll();
		DB db = new DB();
		ArrayList<String[]> arr = db.pageQuery(pageCount, pageNum, sql);
		for (int i = 0; i < arr.size(); i++) {
			TableItem tableItem = new TableItem(table, SWT.NONE);

			tableItem.setText(0, arr.get(i)[0]);
			tableItem.setText(1, arr.get(i)[1]);
			tableItem.setText(2, arr.get(i)[2]);
			tableItem.setText(3, arr.get(i)[3]);
			tableItem.setText(4, arr.get(i)[4]);
			tableItem.setText(5, arr.get(i)[5]);
			// 第七条要额外判断
			tableItem.setText(6, String.valueOf(checkStatus(arr.get(i)[0])));
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
		this.setSite(site);
		this.setInput(input);
		// Initialize the editor part
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
		return "gettools";
	}
}
