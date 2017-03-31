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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import com.shxt.syt_supermarket.dialog.InfoAlterDialog;
import com.shxt.syt_supermarket.dialog.NewGoodsDialog;
import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class InfoEditor extends EditorPart implements IEditorInput {

	public static final String ID = "com.shxt.syt_supermarket.editor.InfoEditor"; //$NON-NLS-1$
	private static final Color COLOR_RED = null;
	private Table table;
	Label lblNewLabel;
	String sql = "select *from warehouse ";
	/**
	 * ���ҳ
	 */
	public int maxPage;
	/**
	 * ��ǰҳ
	 */
	public int pageNum = 1;
	/**
	 * ÿҳ��ʾ����Ŀ����
	 */
	public int pageCount = 7;

	public InfoEditor() {
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
		table.setBounds(31, 30, 540, 321);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(74);
		tblclmnNewColumn.setText("��Ʒ���");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("��Ʒ����");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(87);
		tblclmnNewColumn_2.setText("����");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(78);
		tblclmnNewColumn_3.setText("����");

		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(93);
		tblclmnNewColumn_4.setText("�����");

		TableColumn tblclmnOp = new TableColumn(table, SWT.NONE);
		tblclmnOp.setWidth(100);
		tblclmnOp.setText("��Ʒ����");

		TableItem tableItem = new TableItem(table, SWT.NONE);

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * �޸�
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// �޸�
				InfoAlterDialog iad = new InfoAlterDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						SWT.MIN | SWT.MAX | SWT.CLOSE);
				int index = table.getSelectionIndex();
				if(index==-1){
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.MIN
							| SWT.MAX|SWT.CLOSE);
					mb.setMessage("����û������Ŷ");
					mb.setText("��ʾ");
				}else{
					TableItem item = table.getItem(index);
					String[] arr = { item.getText(0), item.getText(1),
							item.getText(2), item.getText(3), item.getText(4),
							item.getText(5) };
					iad.open(arr);
					putTable();
				}
				
			}
		});
		mntmNewItem_1.setText("\u66F4\u6539");

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			/**
			 * ɾ��
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				DB db = new DB();
				int index = table.getSelectionIndex();
				if(index==-1){
					
					MessageBox mt = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.MIN
							| SWT.MAX|SWT.CLOSE);
					mt.setMessage("����û������Ŷ");
					mt.setText("��ʾ");
					mt.open();
				}else{
					TableItem item = table.getItem(index);
					String newsql = item.getText(0);
					newsql = "delete  from warehouse where id=" +"'"+ newsql+"'";
					int n = db.update(newsql);
					MessageBox mb=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
					if (n == 1) {
						mb.setText("��ʾ");
						mb.setMessage("ɾ���ɹ�");
						mb.open();
					}else{
						mb.setText("��ʾ");
						mb.setMessage("ɾ��ʧ��");
						mb.open();
					}
					// Ȼ�����¼��㵱ǰҳ��ҳ������
					maxPage = getMaxPage();
					if (pageNum > maxPage) {
						// ������ҳ��
						lblNewLabel.setText(String.valueOf(maxPage));
					}
					putTable();// �����ǰҳ�͵�ǰ��ҳ��
				}
			}
		});
		mntmNewItem.setText("\u5220\u9664");

		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(192, 410, 27, 17);
		lblNewLabel.setText("1");

		putTable();

		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setBounds(210, 410, 39, 17);
		lblNewLabel_2.setText("      /");

		maxPage = getMaxPage();

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setBounds(271, 410, 27, 17);
		lblNewLabel_1.setText(String.valueOf(maxPage));

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * ��һҳ
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
					mb.setText("��ʾ");
					mb.setMessage("�ף��Ѿ��ǵ�һҳ��Ŷ");
					mb.open();
				}
			}
		});
		btnNewButton.setBounds(78, 405, 80, 27);
		btnNewButton.setText("\u4E0A\u4E00\u9875");

		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * ��һҳ
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
					mb.setText("��ʾ");
					mb.setMessage("�ף��Ѿ������һҳ��Ŷ");
					mb.open();
				}
			}
		});
		button.setBounds(310, 400, 80, 27);
		button.setText("\u4E0B\u4E00\u9875");

		Button button_1 = new Button(container, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * ������Ʒ
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				NewGoodsDialog ngd = new NewGoodsDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						SWT.MIN | SWT.MAX | SWT.CLOSE);
				ngd.open();

			}
		});
		button_1.setBounds(407, 390, 80, 57);
		button_1.setText("\u65B0\u589E\u5546\u54C1");

	}

	/**
	 * @author sun ��ʾ��ǰҳ������,��ʾ��ǰҳ��ҳ��
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
		//������ǰ��ʾ�ı�����ݣ�����ֿ����������50��������������ɫ���
		TableItem tableItem;
		for (int i = 0; i < table.getItemCount(); i++) {
			 tableItem = table.getItem(i);
			if(Integer.parseInt(tableItem.getText(4))<50){	
				tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
			}
		}
	}
	/**
	 * @author sun ����ж���ҳ
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
		return "gettooltip";
	}
}
