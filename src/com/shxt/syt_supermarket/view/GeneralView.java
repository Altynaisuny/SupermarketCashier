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
	 * 实收金额
	 */
	private Text text;
	/**
	 * 付款金额
	 */
	private Text text_1;

	/**
	 * 会员卡号
	 */
	private Text text_3;
	/**
	 * 小票
	 */
	Label lblNewLabel_13;
	
	/**
	 * 用户输入的商品序号
	 */
	private Text text_5;
	/**
	 * 当前的钱数
	 */
	Label lblNewLabel_10;
	/**
	 * 当前商品数量
	 */
	Label lblNewLabel_11;
	boolean bool = true;// 这是一个开关
	TableItem tableItem;
	Label lblNewLabel_14;
	String time;
	
	/**
	 * 找零
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
			 * 删除项目 删除以后，还要计算此时的金额，数量 删除某项目要更改的项目：金额，包括单品金额和总金额；数量，也包括单品数量和总数量
			 * 关键是怎么修改 总money减去单品money 数量，总num减去单品num
			 * 然后调用sumMoney方法，计算当前的money和num
			 * 假如某一条数据被完全删除，table中该项怎么办，会不会影响新增项的添加
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				// 获得该项的对象

				int index = table.getSelectionIndex();
				TableItem tableItem = table.getItem(index);
				// 逻辑关系不对
				// 假如该条商品的数量为1，就删除该项目本身
				if (tableItem.getText(3).trim().equals("1")) {
					table.remove(index);

				} else {
					// 假如不为1
					// 此处有错误
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
			 * key事件，当释放键盘时
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
			 * 结算
			 * 打印 warehouse中的商品的数目要变化
			 * 会员的积分要改变，根据积分判断是不是要发放礼品，用户可以选择是否领取，假如领取，就变更VIP表格中的score 关于利润，待议
			 * 判断是否要往money中插入数据 根据时间来判断，系统时间
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				//先判断收银台表中是不是有数据，没有是不允许结算的
				MessageBox bm=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.CLOSE);
				
				if(table.getItemCount()==0){
					bm.setMessage("收银台没有任何交易");
					bm.setText("提示");
					bm.open();
				}else{
					
					MessageBox mmn=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.YES|SWT.NO);
					mmn.setMessage("确定要结账吗");
					mmn.setText("提示");
					int nn=mmn.open();
					if(nn==64){
						// 变更利润表
						
						Date d = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(d);
						DB db = new DB();
						String sql = "insert into money values " + "(" + "'" + date
								+ "'" + "," + "'" + text.getText() + "'" + "," + "'"
								+ String.valueOf(Profit()) + "'" + ")";
						db.update(sql);
						// 变更仓库
						int ItemNum = table.getItemCount();

						for (int i = 0; i < ItemNum; i++) {
							// 表格中的每件商品的数量
							table.getItem(i).getText(3);
							// 数据库中每件商品的数量
							String sql1 = "select *from warehouse where id="
									+ table.getItem(i).getText(0);

							ArrayList<String[]> ar = db.arrQuery(sql1);
							// ar.get(0)[4]**这是该商品的库存量
							int newNum = Integer.parseInt(ar.get(0)[4])
									- Integer.parseInt(table.getItem(i).getText(3));
							String sql2 = "update warehouse set number="
									+ String.valueOf(newNum) + " where id="
									+ table.getItem(i).getText(0);
							db.update(sql2);
						}

						// 变更会员,text_3是会员卡号,test是本次积分
						// 假如有会员就变更会员积分
						// 如果没有会员，就不更改
						if (!text_3.getText().equals("")) {
							String sql3 = "select *from vip where id="
									+ text_3.getText();
							ArrayList<String[]> arrr = db.arrQuery(sql3);
							// 原始积分arrr.get(0)[2]
							// 本次积分Double.parseDouble(text.getText())*100;
							double newScore = Double.parseDouble(arrr.get(0)[2])
									+ Double.parseDouble(text.getText()) * 100;

							String sql4 = "update vip set score="
									+ String.valueOf(newScore) + " where id="
									+ text_3.getText();

							db.update(sql4);
						}
						//清除当前收银台内所有内容
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
			 * key事件
			 * @Override
			 */
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==13){

					bool = true;
					MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), SWT.MIN
							| SWT.MAX | SWT.CLOSE);
					DB db = new DB();
					if (text_5.getText().equals("")) {// 判断是否为空
						mb.setMessage("请输入数据");
						mb.setText("提示");
						mb.open();
					} else {
						String sql = "select * from shopping where id="
								+ text_5.getText();
						ArrayList<String[]> arr = db.arrQuery(sql);

						if (arr.size() == 0) {// 判断在数据库中是否存在该数据
							mb.setMessage("不存在该商品");
							mb.setText("提示");
							mb.open();
						} else {// 如果存在
							int itemNum = table.getItemCount();// 计算table中的行数的多少
							int x = -1;// 重复行的index
							if (itemNum == 0) {
								x = 0;
							}
							for (int i = 0; i < itemNum; i++) {
								// 假如从table中获得的数据与要添加的数据相同
								if (table.getItem(i).getText(0)
										.equals(arr.get(0)[0])) {
									x = i;
									bool = false;
									break;
								}
							}
							if (bool == true) {
								// 如果没有重复的，就新增项目,既然是新增加的项目RUN
								tableItem = new TableItem(table, SWT.NONE);
								tableItem.setText(0, arr.get(0)[0]);// id
								tableItem.setText(1, arr.get(0)[1]);// 品名
								tableItem.setText(2, arr.get(0)[3]);// 单价多少
								tableItem.setText(3, String.valueOf(1));// 1件该商品
								tableItem.setText(4, arr.get(0)[3]);// 多少钱
								
							} else {
								// 如果已经存在该项目
								int m=Integer.parseInt(table.getItem(x).getText(3));
								m+=1;
								// 找到该商品在table中的位置,令该商品的数量增加
								table.getItem(x).setText(3, String.valueOf(m));// 改变数量
								table.getItem(x)
										.setText(
												4,
												String.valueOf(m
														* Double.parseDouble(arr
																.get(0)[3])));// 改变金额
							}

							// 计算总钱数
							// 总钱数应该在循环里吗
							// 计算总件数,应该用一个循环，来计算总体的数量，该一下算法吧
							// 因为要分情况计算钱数总和，所以在决定是否添加新的项目的时候，重新计算数量和钱数

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
			 * 向表格中增加商品 假如重复，变更数目和金额
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				bool = true;
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MIN
						| SWT.MAX | SWT.CLOSE);
				DB db = new DB();
				if (text_5.getText().equals("")) {// 判断是否为空
					mb.setMessage("请输入数据");
					mb.setText("提示");
					mb.open();
				} else {
					String sql = "select * from shopping where id="
							+ text_5.getText();
					ArrayList<String[]> arr = db.arrQuery(sql);

					if (arr.size() == 0) {// 判断在数据库中是否存在该数据
						mb.setMessage("不存在该商品");
						mb.setText("提示");
						mb.open();
					} else {// 如果存在
						int itemNum = table.getItemCount();// 计算table中的行数的多少
						int x = -1;// 重复行的index
						if (itemNum == 0) {
							x = 0;
						}
						for (int i = 0; i < itemNum; i++) {
							// 假如从table中获得的数据与要添加的数据相同
							if (table.getItem(i).getText(0)
									.equals(arr.get(0)[0])) {
								x = i;//这个x就是一个索引，是目标行
								bool = false;
								break;
							}
						}
						if (bool == true) {
							// 如果没有重复的，就新增项目,既然是新增加的项目RUN
							tableItem = new TableItem(table, SWT.NONE);
							tableItem.setText(0, arr.get(0)[0]);// id
							tableItem.setText(1, arr.get(0)[1]);// 品名
							tableItem.setText(2, arr.get(0)[3]);// 单价多少
							tableItem.setText(3, String.valueOf(1));// 1件该商品
							tableItem.setText(4, arr.get(0)[3]);// 多少钱
							
						} else {
							// 如果已经存在该项目
							//这里的n写的有错误，假如增加的是其他的商品，n的值增加的位置不对
							//可以遍历整张表格，找到存在项，获取它的数量值，然后加1
							
							// 找到该商品在table中的位置,令该商品的数量增加
							int m=Integer.parseInt(table.getItem(x).getText(3));
							m+=1;
							table.getItem(x).setText(3, String.valueOf(m));// 改变数量
							table.getItem(x)
									.setText(
											4,
											String.valueOf(m
													* Double.parseDouble(arr
															.get(0)[3])));// 改变金额
						}

						// 计算总钱数
						// 总钱数应该在循环里吗
						// 计算总件数,应该用一个循环，来计算总体的数量，该一下算法吧
						// 因为要分情况计算钱数总和，所以在决定是否添加新的项目的时候，重新计算数量和钱数

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
				.getFont("微软雅黑", 20, SWT.NORMAL));
		lblNewLabel_6.setBounds(23, 10, 91, 44);
		lblNewLabel_6.setText("\u5171\uFF1A\u00A5");

		Label lblNewLabel_9 = new Label(composite, SWT.NONE);
		lblNewLabel_9.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_9.setFont(SWTResourceManager
				.getFont("微软雅黑", 20, SWT.NORMAL));
		lblNewLabel_9.setBounds(23, 73, 127, 44);
		lblNewLabel_9.setText("\u5546\u54C1\u6570\u91CF\uFF1A");

		lblNewLabel_10 = new Label(composite, SWT.NONE);
		lblNewLabel_10.setFont(SWTResourceManager.getFont("微软雅黑", 20,
				SWT.NORMAL));
		lblNewLabel_10.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_10.setBounds(194, 10, 187, 32);
		lblNewLabel_10.setText("0");

		lblNewLabel_11 = new Label(composite, SWT.NONE);
		lblNewLabel_11.setFont(SWTResourceManager.getFont("微软雅黑", 20,
				SWT.NORMAL));
		lblNewLabel_11.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblNewLabel_11.setBounds(194, 73, 187, 32);

		Label lblNewLabel_12 = new Label(container, SWT.NONE);
		lblNewLabel_12.setBounds(452, 23, 103, 17);
		lblNewLabel_12.setText("\u5C0F\u7968\u6D41\u6C34\u53F7");
		/**
		 * 小票的流水号
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
			 * 会员打折 更改实收金额，500分-1000分是9折，500分以下没有折扣，1000分以上是8折
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				double FactReciver;
				DB db = new DB();
				MessageBox mb = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MIN
						| SWT.MAX | SWT.CLOSE);
				// 判断输入的会员卡号是否为空
				if (text_3.getText().trim().equals("")) {
					mb.setMessage("会员卡号不能为空");
					mb.setText("提示");
					mb.open();
				} else {
					// 假如会员卡号不为空
					String sql = "select *from vip where id="
							+ text_3.getText();
					ArrayList<String[]> arr = db.arrQuery(sql);
					// 查询该会员卡号是否存在于数据库中
					if (text_3.getText().trim().equals(arr.get(0)[0])) {
						// 如果存在
						if (Double.parseDouble(arr.get(0)[2].trim()) >= 1000) {
							// FactReciver是会员打折后的实收金额
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
							mt.setMessage("积分不足，无法打折");
							mt.setText("提示");
							mt.open();
							text.setText(lblNewLabel_10.getText());
						}
					} else {
						// 如果不存在
						mb.setMessage("该会员不存在");
						mb.setText("提示");
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
			 * 切换用户
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {

				MessageBox msg = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.OK
						| SWT.CANCEL);
				msg.setText("警告");
				msg.setMessage("确认切换用户吗?");
				int clickNum = msg.open();
				if (clickNum == 32) {
					// 切换用户就是调用 Workbench的重启方法
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
			 * 退出
			 * 
			 * @Override
			 */
			public void widgetSelected(SelectionEvent e) {
				MessageBox msg = new MessageBox(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.OK
						| SWT.CANCEL);
				msg.setText("警告");
				msg.setMessage("确认退出吗?");
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
						// 设置时间变化的时间间隔
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// 获取系统当前时间
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					time = sdf.format(d);
				
					// 获得一个默认的display对象，使用其对象访问前台组件
					Display.getDefault().asyncExec(new Runnable() {
						// 当前时间赋值给lblnewlabel
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
	 * 计算当前table中的num和money
	 */
	public void sumMoney() {
		int num = 0;
		double money = 0;
		int newItemNum = table.getItemCount();
		for (int i = 0; i < newItemNum; i++) {
			// 每次计算完后，把钱数清零，
			money += Double.parseDouble(table.getItem(i).getText(4));

			num += Integer.parseInt(table.getItem(i).getText(3));
		}
		lblNewLabel_11.setText(String.valueOf(num));
		lblNewLabel_10.setText(String.valueOf(money));
		text.setText(lblNewLabel_10.getText());

	}

	/**
	 * @author sun
	 * @return 总利润
	 */
	private double Profit() {
		double sumProfit = 0;
		int newItemNum = table.getItemCount();
		// 总金额在text中
		// 现在要求出总进价
		DB db = new DB();
		double Profit = 0;
		for (int i = 0; i < newItemNum; i++) {

			// 查询商品在仓库中的进价
			String sql = "select *from warehouse where id="
					+ table.getItem(i).getText(0);
			ArrayList<String[]> arr = db.arrQuery(sql);
			// 进价*数量=总成本
			Profit += Double.parseDouble(arr.get(0)[5])
					* Integer.parseInt(table.getItem(i).getText(3));

		}
		// 应收金额-总成本=总利润
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
