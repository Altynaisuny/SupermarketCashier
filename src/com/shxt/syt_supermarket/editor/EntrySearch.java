package com.shxt.syt_supermarket.editor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.shxt.syt_supermarket.tools.DB;

public class EntrySearch extends EditorPart implements IEditorInput {

	public static final String ID = "com.shxt.syt_supermarket.editor.EntrySearch"; //$NON-NLS-1$

	/**
	 * 当天入账
	 */
	Label lblNewLabel;
	/**
	 * 当月入账
	 */
	Label lblNewLabel_1;
	/**
	 * 指定日入账
	 */
	Label lblNewLabel_2;
	/**
	 * 指定月入账
	 */
	Label lblNewLabel_3;
	/**
	 * 指定日
	 */
	DateTime dateTime;

	public EntrySearch() {
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		Composite composite = new Composite(container, SWT.NONE);
		composite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		composite.setBounds(75, 115, 449, 298);

		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * 当天入账
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// 当前系统时间
				String time = sdf.format(d);
				String sql = "select *from money where date=" + "'" + time
						+ "'";
				DB db = new DB();
				ArrayList<String[]> arr = db.arrQuery(sql);
				// 查询的结果不止一个,把每一次的相加，输出最终结果
				double OneDayEntry = 0;
				for (int i = 0; i < arr.size(); i++) {
					OneDayEntry += Double.parseDouble(arr.get(i)[1]);
				}
				lblNewLabel.setText(String.valueOf(OneDayEntry));
			}
		});
		button.setBounds(44, 51, 80, 27);
		button.setText("\u5F53\u5929\u5165\u8D26");

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * 当月入账
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

				String time = sdf.format(d);
				// 在数据库中查询符合条件的
				String sql = "select *from money where date like " + "'" + time
						+ "%" + "'";
				DB db = new DB();
				ArrayList<String[]> arr = db.arrQuery(sql);
				// 保留每一次查询的结果
				double OneMonthEntry = 0;
				for (int i = 0; i < arr.size(); i++) {
					OneMonthEntry += Double.parseDouble(arr.get(i)[1]);
				}
				lblNewLabel_1.setText(String.valueOf(OneMonthEntry));
			}
		});
		button_1.setBounds(44, 108, 80, 27);
		button_1.setText("\u5F53\u6708\u5165\u5E10");

		dateTime = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		dateTime.setBounds(44, 178, 88, 24);

		Button button_2 = new Button(composite, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			/**
			 * 查询指定日
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// 因为单个月份是一个数字，而数据库中的数字是两位，所以，要加一个0，
				int month = dateTime.getMonth() + 1;
				String sql;
				if (month <= 9) {
					String newMonth = "0" + String.valueOf(month);
					sql = "select *from money where date=" + "'"
							+ dateTime.getYear() + "-" + newMonth + "-"
							+ dateTime.getDay() + "'";

				} else {
					sql = "select *from money where date=" + "'"
							+ dateTime.getYear() + "-" + String.valueOf(month)
							+ "-" + dateTime.getDay() + "'";
				}

				DB db = new DB();
				ArrayList<String[]> arr = db.arrQuery(sql);
				if (arr.size() == 0) {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.OK
							| SWT.CANCEL);
					mb.setMessage("所选日期无数据");
					mb.setText("提示");
					mb.open();
				} else {
					double ThisDayEntry = 0;
					for (int i = 0; i < arr.size(); i++) {
						ThisDayEntry += Double.parseDouble(arr.get(i)[1]);
					}
					lblNewLabel_2.setText(String.valueOf(ThisDayEntry));
				}
			}
		});
		button_2.setBounds(149, 175, 96, 27);
		button_2.setText("\u67E5\u8BE2\u6307\u5B9A\u65E5\u5165\u8D26");

		DateTime dateTime_1 = new DateTime(composite, SWT.BORDER
				| SWT.DROP_DOWN);
		dateTime_1.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		dateTime_1.setBounds(44, 228, 88, 24);

		Button button_3 = new Button(composite, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			/**
			 * 查询指定月
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				int month = dateTime.getMonth() + 1;
				String sql;
				if (month <= 9) {
					String newMonth = "0" + String.valueOf(month);
					sql = "select *from money where date like " + "'"
							+ dateTime.getYear() + "-" + newMonth + "%" + "'";

				} else {
					sql = "select *from money where date like " + "'"
							+ dateTime.getYear() + "-" + String.valueOf(month)
							+ "%" + "'";
				}
				DB db = new DB();
				ArrayList<String[]> arr = db.arrQuery(sql);
				if (arr.size() == 0) {
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.OK
							| SWT.CANCEL);
					mb.setMessage("所选日期无数据");
					mb.setText("提示");
					mb.open();
				} else {
					double ThisMonthEntry = 0;
					for (int i = 0; i < arr.size(); i++) {
						ThisMonthEntry += Double.parseDouble(arr.get(i)[1]);
					}
					lblNewLabel_3.setText(String.valueOf(ThisMonthEntry));
				}
			}
		});
		button_3.setBounds(149, 228, 96, 27);
		button_3.setText("\u67E5\u8BE2\u6307\u5B9A\u6708\u5165\u8D26");

		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel.setBounds(296, 51, 61, 17);

		lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_1.setBounds(296, 118, 61, 17);

		lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_2.setBounds(296, 178, 61, 17);

		lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_3.setBounds(296, 228, 61, 17);

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
		return "gettoolTip";
	}

}
