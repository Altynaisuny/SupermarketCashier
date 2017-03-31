package com.shxt.syt_supermarket.editor;

/**
 * �����ѯ
 */
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.shxt.syt_supermarket.tools.DB;

public class ProfitSearch extends EditorPart implements IEditorInput {
	/**
	 * ��ȷ��ѯ��ʱ���
	 */
	DateTime dateTime;
	/**
	 * ���������ѯ�Ľ��
	 */
	Label lblNewLabel;
	/**
	 * ���������ѯ�Ľ��
	 */
	Label lblNewLabel_1;
	/**
	 * ĳ�������ѯ�Ľ��
	 */
	Label lblNewLabel_2;
	public static final String ID = "com.shxt.syt_supermarket.editor.ProfitSearch"; //$NON-NLS-1$

	public ProfitSearch() {
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
		composite.setBounds(92, 65, 403, 277);

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/*
			 * ���������ѯ ��õ�ǰʱ�䣬Ȼ���ѯmoney����dialog��ʾ���
			 * 
			 * @Override(non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// ��ǰϵͳʱ��
				String time = sdf.format(d);
				String sql = "select *from money where date=" + "'" + time
						+ "'";
				DB db = new DB();
				ArrayList<String[]> arr = db.arrQuery(sql);
				// ��ѯ�Ľ����ֹһ��,��ÿһ�ε���ӣ�������ս��
				double OneDayProfit = 0;
				for (int i = 0; i < arr.size(); i++) {
					OneDayProfit += Double.parseDouble(arr.get(i)[2]);
				}
				lblNewLabel.setText(String.valueOf(OneDayProfit));
			}
		});
		btnNewButton.setBounds(44, 37, 80, 27);
		btnNewButton.setText("\u5F53\u5929\u5229\u6DA6\u67E5\u8BE2");

		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * ���������ѯ
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// ���Ȼ�ȡ��ǰ��ʱ��
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

				String time = sdf.format(d);
				// �����ݿ��в�ѯ����������
				String sql = "select *from money where date like " + "'" + time
						+ "%" + "'";
				DB db = new DB();
				ArrayList<String[]> arr = db.arrQuery(sql);
				// ����ÿһ�β�ѯ�Ľ��
				double OneMonthProfit = 0;
				for (int i = 0; i < arr.size(); i++) {
					OneMonthProfit += Double.parseDouble(arr.get(i)[2]);
				}
				lblNewLabel_1.setText(String.valueOf(OneMonthProfit));
			}
		});
		btnNewButton_1.setBounds(44, 94, 80, 27);
		btnNewButton_1.setText("\u5F53\u6708\u5229\u6DA6\u67E5\u8BE2");

		dateTime = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		dateTime.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		dateTime.setBounds(36, 163, 88, 24);

		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * ��ȷ��ѯ
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
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
					mb.setMessage("��ѡ����������");
					mb.setText("��ʾ");
					mb.open();
				} else {
					double ThisDayProfit = 0;
					for (int i = 0; i < arr.size(); i++) {
						ThisDayProfit += Double.parseDouble(arr.get(i)[2]);
					}
					lblNewLabel_2.setText(String.valueOf(ThisDayProfit));
				}

			}
		});
		button.setBounds(139, 163, 36, 27);
		button.setText("\u67E5\u8BE2");

		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel.setBounds(246, 37, 61, 17);

		lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_1.setBounds(246, 94, 61, 17);

		lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_2.setBounds(246, 170, 61, 17);

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
		return "getName";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "gettooltip";
	}
}
