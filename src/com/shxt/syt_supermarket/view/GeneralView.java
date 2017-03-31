package com.shxt.syt_supermarket.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.shxt.syt_supermarket.tools.DB;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class GeneralView extends ViewPart {

	public static final String ID = "com.shxt.syt_supermarket.view.GeneralView"; //$NON-NLS-1$
	private Table table;
	/**
	 * ʵ�ս��
	 */
	private Text text;
	/**
	 * ������
	 */
	private Text text_1;

	/**
	 * ��Ա����
	 */
	private Text text_3;
	/**
	 * СƱ
	 */
	Label lblNewLabel_13;
	
	/**
	 * �û��������Ʒ���
	 */
	private Text text_5;
	/**
	 * ��ǰ��Ǯ��
	 */
	Label lblNewLabel_10;
	/**
	 * ��ǰ��Ʒ����
	 */
	Label lblNewLabel_11;
	boolean bool = true;// ����һ������
	TableItem tableItem;
	Label lblNewLabel_14;
	String time;
	
	/**
	 * ����
	 */
	private Text text_2;
	String indexTime;
	public GeneralView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 52, 704, 232);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(106);
		tblclmnNewColumn.setText("\u6761\u7801");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(127);
		tblclmnNewColumn_1.setText("\u54C1\u540D");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(121);
		tblclmnNewColumn_2.setText("\u5355\u4EF7");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(159);
		tblclmnNewColumn_3.setText("\u6570\u91CF");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(184);
		tableColumn.setText("\u91D1\u989D");

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * ɾ����Ŀ ɾ���Ժ󣬻�Ҫ�����ʱ�Ľ����� ɾ��ĳ��ĿҪ���ĵ���Ŀ����������Ʒ�����ܽ�������Ҳ������Ʒ������������
			 * �ؼ�����ô�޸� ��money��ȥ��Ʒmoney ��������num��ȥ��Ʒnum
			 * Ȼ�����sumMoney���������㵱ǰ��money��num
			 * ����ĳһ�����ݱ���ȫɾ����table�и�����ô�죬�᲻��Ӱ������������
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// ��ø���Ķ���

				int index = table.getSelectionIndex();
				TableItem tableItem = table.getItem(index);
				// �߼���ϵ����
				// ���������Ʒ������Ϊ1����ɾ������Ŀ����
				if (tableItem.getText(3).trim().equals("1")) {
					table.remove(index);

				} else {
					// ���粻Ϊ1
					// �˴��д���
					String new_number = String.valueOf(Integer
							.parseInt(tableItem.getText(3)) - 1);
					String new_price = String.valueOf(Double
							.parseDouble(tableItem.getText(4))
							- Double.parseDouble(tableItem.getText(2)));
					tableItem.setText(3, new_number);
					tableItem.setText(4, new_price);
				}
				sumMoney();
			}
		});
		menuItem_1.setText("\u5220\u9664");

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(499, 319, 13, 17);
		lblNewLabel.setText("\u00A5");

		text = new Text(container, SWT.BORDER);
		text.setEnabled(false);
		text.setBounds(526, 316, 73, 23);

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setText("\u00A5");
		lblNewLabel_1.setBounds(499, 391, 21, 17);

		text_1 = new Text(container, SWT.BORDER);
		text_1.addKeyListener(new KeyAdapter() {
			/**
			 * key�¼������ͷż���ʱ
			 * 
			 * @Override
			 */
			public void keyReleased(KeyEvent e) {
				DecimalFormat df=new DecimalFormat(".00");
				Double result=Double.parseDouble(text_1.getText())-Double.parseDouble(text.getText());
				String truePrice=df.format(result);
				if(Double.parseDouble(truePrice)<1){
					truePrice="0"+truePrice;
				}
				text_2.setText(String.valueOf(truePrice));
			}
		});
		text_1.setBounds(526, 388, 73, 23);

		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setBounds(499, 458, 21, 17);
		lblNewLabel_2.setText("\u00A5");

		Label lblNewLabel_3 = new Label(container, SWT.NONE);
		lblNewLabel_3.setBounds(526, 293, 61, 17);
		lblNewLabel_3.setText("\u5B9E\u6536\u91D1\u989D");

		Label lblNewLabel_4 = new Label(container, SWT.NONE);
		lblNewLabel_4.setText("\u4ED8\u6B3E\u91D1\u989D");
		lblNewLabel_4.setBounds(526, 364, 61, 17);

		Label lblNewLabel_5 = new Label(container, SWT.NONE);
		lblNewLabel_5.setBounds(526, 432, 61, 17);
		lblNewLabel_5.setText("\u627E\u96F6");

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton
				.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * ����
			 * ��ӡ warehouse�е���Ʒ����ĿҪ�仯
			 * ��Ա�Ļ���Ҫ�ı䣬���ݻ����ж��ǲ���Ҫ������Ʒ���û�����ѡ���Ƿ���ȡ��������ȡ���ͱ��VIP����е�score �������󣬴���
			 * �ж��Ƿ�Ҫ��money�в������� ����ʱ�����жϣ�ϵͳʱ��
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				//���ж�����̨�����ǲ��������ݣ�û���ǲ���������
				MessageBox bm=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.CLOSE);
				
				if(table.getItemCount()==0){
					bm.setMessage("����̨û���κν���");
					bm.setText("��ʾ");
					bm.open();
				}else{
					
					MessageBox mmn=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.NO);
					mmn.setMessage("ȷ��Ҫ������");
					mmn.setText("��ʾ");
					int nn=mmn.open();
					if(nn==64){
						// ��������
						
						Date d = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(d);
						DB db = new DB();
						String sql = "insert into money values " + "(" + "'" + date
								+ "'" + "," + "'" + text.getText() + "'" + "," + "'"
								+ String.valueOf(Profit()) + "'" + ")";
						db.update(sql);
						// ����ֿ�
						int ItemNum = table.getItemCount();

						for (int i = 0; i < ItemNum; i++) {
							// ����е�ÿ����Ʒ������
							table.getItem(i).getText(3);
							// ���ݿ���ÿ����Ʒ������
							String sql1 = "select *from warehouse where id="
									+ table.getItem(i).getText(0);

							ArrayList<String[]> ar = db.arrQuery(sql1);
							// ar.get(0)[4]**���Ǹ���Ʒ�Ŀ����
							int newNum = Integer.parseInt(ar.get(0)[4])
									- Integer.parseInt(table.getItem(i).getText(3));
							String sql2 = "update warehouse set number="
									+ String.valueOf(newNum) + " where id="
									+ table.getItem(i).getText(0);
							db.update(sql2);
						}

						// �����Ա,text_3�ǻ�Ա����,test�Ǳ��λ���
						// �����л�Ա�ͱ����Ա����
						// ���û�л�Ա���Ͳ�����
						if (!text_3.getText().equals("")) {
							String sql3 = "select *from vip where id="
									+ text_3.getText();
							ArrayList<String[]> arrr = db.arrQuery(sql3);
							// ԭʼ����arrr.get(0)[2]
							// ���λ���Double.parseDouble(text.getText())*100;
							double newScore = Double.parseDouble(arrr.get(0)[2])
									+ Double.parseDouble(text.getText()) * 100;

							String sql4 = "update vip set score="
									+ String.valueOf(newScore) + " where id="
									+ text_3.getText();

							db.update(sql4);
						}
						//�����ǰ����̨����������
						table.removeAll();
						sumMoney();
						text_3.setText("");
						Date ds=new Date();
						SimpleDateFormat dsf = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						indexTime = dsf.format(d);
						lblNewLabel_13.setText(indexTime);
						text_1.setText("");
						text_5.setText("");
						
						
					}
				}
				
			
				
			}
		});
		btnNewButton.setBounds(649, 319, 54, 159);
		btnNewButton.setText("\u7ED3\r\n\u7B97\r\n\u6253\r\n\u5370");

		Label lblNewLabel_7 = new Label(container, SWT.NONE);
		lblNewLabel_7.setBounds(162, 496, 36, 17);
		lblNewLabel_7.setText("\u4F1A\u5458\u5361");

		text_3 = new Text(container, SWT.BORDER);
		text_3.setBounds(235, 493, 73, 17);

		Label label = new Label(container, SWT.NONE);
		label.setBounds(56, 23, 61, 17);
		label.setText("\u5546\u54C1\u6761\u7801");

		text_5 = new Text(container, SWT.BORDER);
		text_5.addKeyListener(new KeyAdapter() {
			/**
			 * key�¼�
			 * @Override
			 */
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==13){

					bool = true;
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.MIN
							| SWT.MAX | SWT.CLOSE);
					DB db = new DB();
					if (text_5.getText().equals("")) {// �ж��Ƿ�Ϊ��
						mb.setMessage("����������");
						mb.setText("��ʾ");
						mb.open();
					} else {
						String sql = "select * from shopping where id="
								+ text_5.getText();
						ArrayList<String[]> arr = db.arrQuery(sql);

						if (arr.size() == 0) {// �ж������ݿ����Ƿ���ڸ�����
							mb.setMessage("�����ڸ���Ʒ");
							mb.setText("��ʾ");
							mb.open();
						} else {// �������
							int itemNum = table.getItemCount();// ����table�е������Ķ���
							int x = -1;// �ظ��е�index
							if (itemNum == 0) {
								x = 0;
							}
							for (int i = 0; i < itemNum; i++) {
								// �����table�л�õ�������Ҫ��ӵ�������ͬ
								if (table.getItem(i).getText(0)
										.equals(arr.get(0)[0])) {
									x = i;
									bool = false;
									break;
								}
							}
							if (bool == true) {
								// ���û���ظ��ģ���������Ŀ,��Ȼ�������ӵ���ĿRUN
								tableItem = new TableItem(table, SWT.NONE);
								tableItem.setText(0, arr.get(0)[0]);// id
								tableItem.setText(1, arr.get(0)[1]);// Ʒ��
								tableItem.setText(2, arr.get(0)[3]);// ���۶���
								tableItem.setText(3, String.valueOf(1));// 1������Ʒ
								tableItem.setText(4, arr.get(0)[3]);// ����Ǯ
								
							} else {
								// ����Ѿ����ڸ���Ŀ
								int m=Integer.parseInt(table.getItem(x).getText(3));
								m+=1;
								// �ҵ�����Ʒ��table�е�λ��,�����Ʒ����������
								table.getItem(x).setText(3, String.valueOf(m));// �ı�����
								table.getItem(x)
										.setText(
												4,
												String.valueOf(m
														* Double.parseDouble(arr
																.get(0)[3])));// �ı���
							}

							// ������Ǯ��
							// ��Ǯ��Ӧ����ѭ������
							// �����ܼ���,Ӧ����һ��ѭ�����������������������һ���㷨��
							// ��ΪҪ���������Ǯ���ܺͣ������ھ����Ƿ�����µ���Ŀ��ʱ�����¼���������Ǯ��

							sumMoney();
						}
					}

				
				}
			}
		});
		text_5.setBounds(134, 20, 174, 23);

		Button btnNewButton_1 = new Button(container, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			/**
			 * ������������Ʒ �����ظ��������Ŀ�ͽ��
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				bool = true;
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MIN
						| SWT.MAX | SWT.CLOSE);
				DB db = new DB();
				if (text_5.getText().equals("")) {// �ж��Ƿ�Ϊ��
					mb.setMessage("����������");
					mb.setText("��ʾ");
					mb.open();
				} else {
					String sql = "select * from shopping where id="
							+ text_5.getText();
					ArrayList<String[]> arr = db.arrQuery(sql);

					if (arr.size() == 0) {// �ж������ݿ����Ƿ���ڸ�����
						mb.setMessage("�����ڸ���Ʒ");
						mb.setText("��ʾ");
						mb.open();
					} else {// �������
						int itemNum = table.getItemCount();// ����table�е������Ķ���
						int x = -1;// �ظ��е�index
						if (itemNum == 0) {
							x = 0;
						}
						for (int i = 0; i < itemNum; i++) {
							// �����table�л�õ�������Ҫ��ӵ�������ͬ
							if (table.getItem(i).getText(0)
									.equals(arr.get(0)[0])) {
								x = i;//���x����һ����������Ŀ����
								bool = false;
								break;
							}
						}
						if (bool == true) {
							// ���û���ظ��ģ���������Ŀ,��Ȼ�������ӵ���ĿRUN
							tableItem = new TableItem(table, SWT.NONE);
							tableItem.setText(0, arr.get(0)[0]);// id
							tableItem.setText(1, arr.get(0)[1]);// Ʒ��
							tableItem.setText(2, arr.get(0)[3]);// ���۶���
							tableItem.setText(3, String.valueOf(1));// 1������Ʒ
							tableItem.setText(4, arr.get(0)[3]);// ����Ǯ
							
						} else {
							// ����Ѿ����ڸ���Ŀ
							//�����nд���д��󣬼������ӵ�����������Ʒ��n��ֵ���ӵ�λ�ò���
							//���Ա������ű���ҵ��������ȡ��������ֵ��Ȼ���1
							
							// �ҵ�����Ʒ��table�е�λ��,�����Ʒ����������
							int m=Integer.parseInt(table.getItem(x).getText(3));
							m+=1;
							table.getItem(x).setText(3, String.valueOf(m));// �ı�����
							table.getItem(x)
									.setText(
											4,
											String.valueOf(m
													* Double.parseDouble(arr
															.get(0)[3])));// �ı���
						}

						// ������Ǯ��
						// ��Ǯ��Ӧ����ѭ������
						// �����ܼ���,Ӧ����һ��ѭ�����������������������һ���㷨��
						// ��ΪҪ���������Ǯ���ܺͣ������ھ����Ƿ�����µ���Ŀ��ʱ�����¼���������Ǯ��

						sumMoney();
					}
				}

			}
		});
		btnNewButton_1.setBounds(356, 19, 41, 27);
		btnNewButton_1.setText("\u589E\u52A0");

		Composite composite = new Composite(container, SWT.NONE);
		composite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		composite.setBounds(10, 300, 449, 159);

		Label lblNewLabel_6 = new Label(composite, SWT.NONE);
		lblNewLabel_6.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_6.setFont(SWTResourceManager
				.getFont("΢���ź�", 20, SWT.NORMAL));
		lblNewLabel_6.setBounds(23, 10, 91, 44);
		lblNewLabel_6.setText("\u5171\uFF1A\u00A5");

		Label lblNewLabel_9 = new Label(composite, SWT.NONE);
		lblNewLabel_9.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_9.setFont(SWTResourceManager
				.getFont("΢���ź�", 20, SWT.NORMAL));
		lblNewLabel_9.setBounds(23, 73, 127, 44);
		lblNewLabel_9.setText("\u5546\u54C1\u6570\u91CF\uFF1A");

		lblNewLabel_10 = new Label(composite, SWT.NONE);
		lblNewLabel_10.setFont(SWTResourceManager.getFont("΢���ź�", 20,
				SWT.NORMAL));
		lblNewLabel_10.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_10.setBounds(194, 10, 187, 32);
		lblNewLabel_10.setText("0");

		lblNewLabel_11 = new Label(composite, SWT.NONE);
		lblNewLabel_11.setFont(SWTResourceManager.getFont("΢���ź�", 20,
				SWT.NORMAL));
		lblNewLabel_11.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_11.setBounds(194, 73, 187, 32);

		Label lblNewLabel_12 = new Label(container, SWT.NONE);
		lblNewLabel_12.setBounds(452, 23, 103, 17);
		lblNewLabel_12.setText("\u5C0F\u7968\u6D41\u6C34\u53F7");
		/**
		 * СƱ����ˮ��
		 */
		lblNewLabel_13 = new Label(container, SWT.NONE);
		lblNewLabel_13.setBounds(561, 23, 142, 17);
		Date d=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		indexTime = sdf.format(d);
		lblNewLabel_13.setText(indexTime);
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			/**
			 * ��Ա���� ����ʵ�ս�500��-1000����9�ۣ�500������û���ۿۣ�1000��������8��
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				double FactReciver;
				DB db = new DB();
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MIN
						| SWT.MAX | SWT.CLOSE);
				// �ж�����Ļ�Ա�����Ƿ�Ϊ��
				if (text_3.getText().trim().equals("")) {
					mb.setMessage("��Ա���Ų���Ϊ��");
					mb.setText("��ʾ");
					mb.open();
				} else {
					// �����Ա���Ų�Ϊ��
					String sql = "select *from vip where id="
							+ text_3.getText();
					ArrayList<String[]> arr = db.arrQuery(sql);
					// ��ѯ�û�Ա�����Ƿ���������ݿ���
					if (text_3.getText().trim().equals(arr.get(0)[0])) {
						// �������
						if (Double.parseDouble(arr.get(0)[2].trim()) >= 1000) {
							// FactReciver�ǻ�Ա���ۺ��ʵ�ս��
							FactReciver = Double.parseDouble(lblNewLabel_10
									.getText()) * 0.8;
							DecimalFormat df=new DecimalFormat(".00");
							String truePrice=df.format(FactReciver);
							text.setText(String.valueOf(truePrice));
						} else if (Double.parseDouble(arr.get(0)[2].trim()) >= 500
								&& Double.parseDouble(arr.get(0)[2]) < 1000) {
							FactReciver = Double.parseDouble(lblNewLabel_10
									.getText()) * 0.9;
							DecimalFormat df=new DecimalFormat(".00");
							String truePrice=df.format(FactReciver);
							text.setText(String.valueOf(truePrice));
						} else {
							MessageBox mt=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.MIN|SWT.MAX|SWT.CLOSE);
							mt.setMessage("���ֲ��㣬�޷�����");
							mt.setText("��ʾ");
							mt.open();
							text.setText(lblNewLabel_10.getText());
						}
					} else {
						// ���������
						mb.setMessage("�û�Ա������");
						mb.setText("��ʾ");
						mb.open();
					}
				}
			}
		});
		button.setBounds(356, 483, 80, 27);
		button.setText("\u6253\u6298");

		Label label_1 = new Label(container, SWT.NONE);
		label_1.setBounds(459, 512, 73, 17);
		label_1.setText("\u5F53\u524D\u7CFB\u7EDF\u65F6\u95F4");

		lblNewLabel_14 = new Label(container, SWT.NONE);
		lblNewLabel_14.setBounds(582, 512, 121, 17);

		Button btnNewButton_2 = new Button(container, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			/**
			 * �л��û�
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				MessageBox msg = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.OK
						| SWT.CANCEL);
				msg.setText("����");
				msg.setMessage("ȷ���л��û���?");
				int clickNum = msg.open();
				if (clickNum == 32) {
					// �л��û����ǵ��� Workbench����������
					PlatformUI.getWorkbench().restart();
				}

			}
		});
		btnNewButton_2.setBounds(10, 486, 80, 27);
		btnNewButton_2.setText("\u5207\u6362\u7528\u6237");

		text_2 = new Text(container, SWT.BORDER);
		text_2.setEnabled(false);
		text_2.setBounds(526, 458, 73, 23);

		Button btnNewButton_3 = new Button(container, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			/**
			 * �˳�
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				MessageBox msg = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.OK
						| SWT.CANCEL);
				msg.setText("����");
				msg.setMessage("ȷ���˳���?");
				int clickNum = msg.open();
				if (clickNum == 32) {
					System.exit(0);

				}
			}
		});
		btnNewButton_3.setBounds(10, 519, 80, 27);
		btnNewButton_3.setText("\u9000\u51FA");
		Thread h = new Thread() {

			

			public void run() {

				while (true) {
					try {
						// ����ʱ��仯��ʱ����
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// ��ȡϵͳ��ǰʱ��
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					time = sdf.format(d);
				
					// ���һ��Ĭ�ϵ�display����ʹ����������ǰ̨���
					Display.getDefault().asyncExec(new Runnable() {
						// ��ǰʱ�丳ֵ��lblnewlabel
						@Override
						public void run() {
							lblNewLabel_14.setText(time);
						}
					});

				}

			}

		};
		h.start();

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * ���㵱ǰtable�е�num��money
	 */
	public void sumMoney() {
		int num = 0;
		double money = 0;
		int newItemNum = table.getItemCount();
		for (int i = 0; i < newItemNum; i++) {
			// ÿ�μ�����󣬰�Ǯ�����㣬
			money += Double.parseDouble(table.getItem(i).getText(4));

			num += Integer.parseInt(table.getItem(i).getText(3));
		}
		lblNewLabel_11.setText(String.valueOf(num));
		lblNewLabel_10.setText(String.valueOf(money));
		text.setText(lblNewLabel_10.getText());

	}

	/**
	 * @author sun
	 * @return ������
	 */
	private double Profit() {
		double sumProfit = 0;
		int newItemNum = table.getItemCount();
		// �ܽ����text��
		// ����Ҫ����ܽ���
		DB db = new DB();
		double Profit = 0;
		for (int i = 0; i < newItemNum; i++) {

			// ��ѯ��Ʒ�ڲֿ��еĽ���
			String sql = "select *from warehouse where id="
					+ table.getItem(i).getText(0);
			ArrayList<String[]> arr = db.arrQuery(sql);
			// ����*����=�ܳɱ�
			Profit += Double.parseDouble(arr.get(0)[5])
					* Integer.parseInt(table.getItem(i).getText(3));

		}
		// Ӧ�ս��-�ܳɱ�=������
		sumProfit = Double.parseDouble(text.getText()) - Profit;
		return sumProfit;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
