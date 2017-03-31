package com.shxt.syt_supermarket.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.shxt.syt_supermarket.dialog.GoodsWarehousingDialog;
import com.shxt.syt_supermarket.dialog.VipInputDialog;
import com.shxt.syt_supermarket.editor.EntrySearch;
import com.shxt.syt_supermarket.editor.HelloEditor;
import com.shxt.syt_supermarket.editor.InfoEditor;
import com.shxt.syt_supermarket.editor.ProfitSearch;
import com.shxt.syt_supermarket.editor.PurchaseSearch;
import com.shxt.syt_supermarket.editor.UpDownEditor;
import com.shxt.syt_supermarket.editor.UserManagement;
import com.shxt.syt_supermarket.editor.VipEditor;
import org.eclipse.wb.swt.SWTResourceManager;

public class ManagerView extends ViewPart {

	public static final String ID = "com.shxt.syt_supermarket.view.ManagerView"; //$NON-NLS-1$

	public ManagerView() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new HelloEditor(), HelloEditor.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			ExpandBar expandBar = new ExpandBar(container, SWT.NONE);
			expandBar.setToolTipText("");
			{
				ExpandItem expandItem = new ExpandItem(expandBar, SWT.NONE);
				expandItem.setExpanded(true);
				expandItem.setText("\u5546\u54C1\u7BA1\u7406");
				{
					Composite composite = new Composite(expandBar, SWT.NONE);
					composite.setBackground(SWTResourceManager
							.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
					expandItem.setControl(composite);
					{
						Button btnNewButton = new Button(composite, SWT.NONE);
						btnNewButton.setBounds(0, 0, 142, 37);
						btnNewButton
								.addSelectionListener(new SelectionAdapter() {
									/**
									 * 商品上下架
									 * 
									 * @Override
									 */
									public void widgetSelected(SelectionEvent e) {
										try {

											PlatformUI
													.getWorkbench()
													.getActiveWorkbenchWindow()
													.getActivePage()
													.openEditor(
															new UpDownEditor(),
															UpDownEditor.ID);

										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								});
						btnNewButton.setText("\u5546\u54C1\u4E0A\u4E0B\u67B6");
					}

					Button button = new Button(composite, SWT.NONE);
					button.setBounds(0, 43, 142, 37);
					button.addSelectionListener(new SelectionAdapter() {
						/**
						 * 商品入库
						 * 
						 * @Override
						 */
						public void widgetSelected(SelectionEvent e) {
							GoodsWarehousingDialog gid = new GoodsWarehousingDialog(
									PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getShell(), SWT.MIN | SWT.MAX
											| SWT.CLOSE);
							gid.open();
						}
					});
					button.setText("\u5546\u54C1\u5165\u5E93");
					{
						Button btnNewButton_1 = new Button(composite, SWT.NONE);
						btnNewButton_1.setBounds(0, 83, 142, 37);
						btnNewButton_1
								.addSelectionListener(new SelectionAdapter() {
									/**
									 * 商品信息管理
									 * 关联的editor和dialog分别是infoeditor和infoalterdialog
									 * 
									 * @Override
									 */
									public void widgetSelected(SelectionEvent e) {
										// 打开Infoeditor
										try {
											PlatformUI
													.getWorkbench()
													.getActiveWorkbenchWindow()
													.getActivePage()
													.openEditor(
															new InfoEditor(),
															InfoEditor.ID);

										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

									}
								});
						btnNewButton_1
								.setText("\u5546\u54C1\u4FE1\u606F\u7BA1\u7406");
					}
				}
				expandItem.setHeight(120);
			}
			ExpandItem expandItem_1 = new ExpandItem(expandBar, SWT.NONE);
			expandItem_1.setExpanded(true);
			expandItem_1.setText("\u4F1A\u5458\u7BA1\u7406");
			{
				Composite composite = new Composite(expandBar, SWT.NONE);
				composite.setBackground(SWTResourceManager
						.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
				expandItem_1.setControl(composite);
				{
					Button btnNewButton_2 = new Button(composite, SWT.NONE);
					btnNewButton_2.setBounds(0, 0, 141, 37);
					btnNewButton_2.addSelectionListener(new SelectionAdapter() {
						/**
						 * 会员申请
						 * 
						 * @Override
						 */
						public void widgetSelected(SelectionEvent e) {
							// 打开dialog
							VipInputDialog ve = new VipInputDialog(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), SWT.NONE);
							ve.open();
						}
					});
					btnNewButton_2.setText("\u4F1A\u5458\u7533\u8BF7");
				}
				{
					Button button = new Button(composite, SWT.NONE);
					button.setBounds(0, 43, 141, 37);
					button.addSelectionListener(new SelectionAdapter() {
						/**
						 * 会员管理
						 * 
						 * @Override
						 */
						public void widgetSelected(SelectionEvent e) {
							try {

								PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.openEditor(new VipEditor(),
												VipEditor.ID);

							} catch (Exception e2) {
								// TODO: handle exception
								e2.printStackTrace();
							}

						}
					});
					button.setText("\u4F1A\u5458\u7BA1\u7406");
				}
			}
			expandItem_1.setHeight(80);
			{
				ExpandItem expandItem = new ExpandItem(expandBar, SWT.NONE);
				expandItem.setExpanded(true);
				expandItem.setText("\u8D22\u52A1\u7EDF\u8BA1");
				{
					Composite composite = new Composite(expandBar, SWT.NONE);
					composite.setBackground(SWTResourceManager
							.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
					expandItem.setControl(composite);

					Button btnNewButton_3 = new Button(composite, SWT.NONE);
					btnNewButton_3.setBounds(0, 0, 140, 37);
					btnNewButton_3.addSelectionListener(new SelectionAdapter() {
						/**
						 * 利润查询
						 * 
						 * @Override
						 */
						public void widgetSelected(SelectionEvent e) {

							try {
								PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.openEditor(new ProfitSearch(),
												ProfitSearch.ID);
							} catch (PartInitException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
					btnNewButton_3.setText("\u5229\u6DA6\u67E5\u8BE2");

					Button button = new Button(composite, SWT.NONE);
					button.setBounds(0, 43, 140, 37);
					button.addSelectionListener(new SelectionAdapter() {
						/**
						 * 入账查询
						 * 
						 * @Override
						 */
						public void widgetSelected(SelectionEvent e) {
							try {
								PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.openEditor(new EntrySearch(),
												EntrySearch.ID);
							} catch (PartInitException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					button.setText("\u5165\u8D26\u67E5\u8BE2");

					Button button_1 = new Button(composite, SWT.NONE);
					button_1.setBounds(0, 83, 140, 37);
					button_1.addSelectionListener(new SelectionAdapter() {
						/**
						 * 进货明细
						 * 
						 * @Override
						 */
						public void widgetSelected(SelectionEvent e) {
							try {
								PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.openEditor(new PurchaseSearch(),
												PurchaseSearch.ID);
							} catch (PartInitException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
					button_1.setText("\u8FDB\u8D27\u660E\u7EC6");
				}
				expandItem.setHeight(120);
			}
			{
				{
					Composite composite = new Composite(expandBar, SWT.NONE);
					{
						Button button = new Button(composite, SWT.NONE);
						button.setBounds(20, 10, 80, 27);
						button.setText("\u5229\u6DA6\u7EDF\u8BA1");
					}
				}
			}
			{
				ExpandItem expandItem = new ExpandItem(expandBar, SWT.NONE);
				expandItem.setExpanded(true);
				expandItem.setText("\u4F7F\u7528\u7BA1\u7406");

				Composite composite = new Composite(expandBar, SWT.NONE);
				composite.setBackground(SWTResourceManager
						.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
				expandItem.setControl(composite);
				expandItem.setHeight(120);

				Button btnNewButton_4 = new Button(composite, SWT.NONE);
				btnNewButton_4.addSelectionListener(new SelectionAdapter() {
					/*
					 * 退出登陆
					 * 
					 * @Override(non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.swt.events.SelectionAdapter#widgetSelected
					 * (org.eclipse.swt.events.SelectionEvent)
					 */
					public void widgetSelected(SelectionEvent e) {

						MessageBox msg = new MessageBox(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), SWT.OK | SWT.CANCEL);
						msg.setText("警告");
						msg.setMessage("确认退出吗?");
						int clickNum = msg.open();
						if (clickNum == 32) {
							System.exit(0);

						}

					}
				});
				btnNewButton_4.setBounds(0, 0, 142, 35);
				btnNewButton_4.setText("\u9000\u51FA");

				Button btnNewButton_5 = new Button(composite, SWT.NONE);
				btnNewButton_5.addSelectionListener(new SelectionAdapter() {
					/**
					 * 切换用户
					 * 
					 * @Override
					 */
					public void widgetSelected(SelectionEvent e) {

						MessageBox msg = new MessageBox(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), SWT.OK | SWT.CANCEL);
						msg.setText("警告");
						msg.setMessage("确认切换用户吗?");
						int clickNum = msg.open();
						if (clickNum == 32) {
							// 切换用户就是调用 Workbench的重启方法
							PlatformUI.getWorkbench().restart();
						}

					}
				});
				btnNewButton_5.setBounds(0, 45, 142, 35);
				btnNewButton_5.setText("\u5207\u6362\u7528\u6237");

				Button button = new Button(composite, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					/**
					 * 用户管理
					 * 
					 * @Override
					 */
					public void widgetSelected(SelectionEvent e) {
						try {
							PlatformUI
									.getWorkbench()
									.getActiveWorkbenchWindow()
									.getActivePage()
									.openEditor(new UserManagement(),
											UserManagement.ID);
						} catch (PartInitException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
				button.setBounds(0, 86, 142, 34);
				button.setText("\u7528\u6237\u7BA1\u7406");
			}
		}

		createActions();
		initializeToolBar();
		initializeMenu();
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
