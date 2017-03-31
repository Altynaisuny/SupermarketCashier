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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableColumn;

import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.widgets.TableItem;

public class PurchaseSearch extends EditorPart implements IEditorInput {

	public static final String ID = "com.shxt.syt_supermarket.editor.PurchaseSearch"; //$NON-NLS-1$
	MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getShell(), SWT.MIN | SWT.MAX
			| SWT.CLOSE);

	private Table table;
	/**
	 * 用户输入的时间
	 */
	DateTime dateTime;
	/**
	 * 用户输入的精确日期
	 */
	TableItem tableItem;
	/**
	 * 当前页
	 */
	Label lblNewLabel;
	/**
	 * 总页数
	 */
	Label label_1;

	/**
	 * 当前页数
	 */
	int pageNum = 1;
	/**
	 * 每一页显示的项目个数
	 */
	int pageCount = 10;
	/**
	 * 最大页数
	 */
	int maxPage;

	public PurchaseSearch() {
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		dateTime = new DateTime(container, SWT.BORDER | SWT.DROP_DOWN);
		dateTime.setBounds(77, 27, 88, 24);

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setBounds(77, 79, 397, 279);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(72);
		tableColumn.setText("\u5546\u54C1\u7F16\u53F7");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(102);
		tableColumn_3.setText("\u5546\u54C1\u540D\u79F0");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(77);
		tableColumn_1.setText("\u8FDB\u8D27\u91CF");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(142);
		tableColumn_2.setText("\u8FDB\u8D27\u65F6\u95F4");

		tableItem = new TableItem(table, SWT.NONE);

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 查询
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				putTable();
			}
		});
		btnNewButton.setBounds(204, 27, 42, 27);
		btnNewButton.setText("\u67E5\u8BE2");

		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/*
			 * 上一页
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				if (pageNum > 1) {

					pageNum--;
					putTable();
					lblNewLabel.setText(String.valueOf(pageNum));
				} else {
					mb.setMessage("亲，已经是第一页了哦");
					mb.setText("提示");
					mb.open();
				}
			}
		});
		btnNewButton_1.setBounds(118, 389, 80, 27);
		btnNewButton_1.setText("\u4E0A\u4E00\u9875");

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
					lblNewLabel.setText(String.valueOf(pageNum));
				} else {

					mb.setMessage("亲，已经是最后一页了哦");
					mb.setText("提示");
					mb.open();
				}
			}
		});
		btnNewButton_2.setBounds(346, 389, 80, 27);
		btnNewButton_2.setText("\u4E0B\u4E00\u9875");

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel.setBounds(216, 394, 30, 17);
		lblNewLabel.setText("1");

		Label label = new Label(container, SWT.NONE);
		label.setBounds(252, 394, 25, 17);
		label.setText("   /");

		countMaxPage();
		label_1 = new Label(container, SWT.NONE);
		label_1.setBounds(295, 394, 30, 17);
		label_1.setText(String.valueOf(maxPage));

	}

	/**
	 * 分页显示用户指定日期的内容
	 */
	public void putTable() {
		table.removeAll();// 消除当前所有的显示信息
		int month = dateTime.getMonth() + 1;
		String sql;
		if (month <= 9) {
			String newMonth = "0" + String.valueOf(month);
			sql = "select *from purchase where date like " + "'"
					+ dateTime.getYear() + "-" + newMonth + "-"
					+ dateTime.getDay() + "%'";

		} else {
			sql = "select *from purchase where date like " + "'"
					+ dateTime.getYear() + "-" + String.valueOf(month) + "-"
					+ dateTime.getDay() + "%'";
		}
		// 从purchase中查询当天的销售记录
		// 用模糊查询显示同一天的
		DB db = new DB();
		ArrayList<String[]> arr = db.pageQuery(pageCount, pageNum, sql);

		for (int i = 0; i < arr.size(); i++) {
			tableItem = new TableItem(table, SWT.NONE);// 新建一行
			// 编号，名称，进货量，进货时间
			table.getItem(i).setText(0, arr.get(i)[1]);
			table.getItem(i).setText(1, arr.get(i)[2]);
			table.getItem(i).setText(2, arr.get(i)[3]);
			table.getItem(i).setText(3, arr.get(i)[0]);
		}

	}

	/**
	 * 计算最大页
	 */
	public void countMaxPage() {
		int month = dateTime.getMonth() + 1;
		String sql;
		if (month <= 9) {
			String newMonth = "0" + String.valueOf(month);
			sql = "select *from purchase where date like " + "'"
					+ dateTime.getYear() + "-" + newMonth + "-"
					+ dateTime.getDay() + "%'";

		} else {
			sql = "select *from purchase where date like " + "'"
					+ dateTime.getYear() + "-" + String.valueOf(month) + "-"
					+ dateTime.getDay() + "%'";
		}
		// 从purchase中查询当天的销售记录
		// 用模糊查询显示同一天的
		DB db = new DB();
		ArrayList<String[]> arr = db.arrQuery(sql);
		int num = arr.size();
		if (num % pageCount != 0) {
			maxPage = num / pageCount + 1;
		} else if (num <= pageCount) {
			maxPage = 1;
		} else {
			maxPage = num;
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
		return "getToolTipText";
	}
}
