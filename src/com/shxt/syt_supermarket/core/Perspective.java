package com.shxt.syt_supermarket.core;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.shxt.syt_supermarket.view.GeneralView;
import com.shxt.syt_supermarket.view.ManagerView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {

		if(Application.power==1){
			layout.addView(ManagerView.ID, layout.LEFT, 0.2f, layout.getEditorArea());
			layout.setEditorAreaVisible(true);
			layout.getViewLayout(ManagerView.ID).setCloseable(false);
			layout.getViewLayout(ManagerView.ID).setMoveable(false);

		}else if(Application.power==2){
			layout.addView(GeneralView.ID, layout.LEFT, 0.3f, layout.getEditorArea());
			layout.addStandaloneView(GeneralView.ID, false,IPageLayout.LEFT, 1.0f, layout.getEditorArea());
			layout.setEditorAreaVisible(false);
			layout.getViewLayout(GeneralView.ID).setCloseable(false);
			layout.getViewLayout(GeneralView.ID).setMoveable(false);
		}	
	}
}
