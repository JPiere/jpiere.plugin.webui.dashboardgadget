/******************************************************************************
 * Product: JPiere                                                            *
 * Copyright (C) Hideaki Hagiwara (h.hagiwara@oss-erp.co.jp)                  *
 *                                                                            *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY.                          *
 * See the GNU General Public License for more details.                       *
 *                                                                            *
 * JPiere is maintained by OSS ERP Solutions Co., Ltd.                        *
 * (http://www.oss-erp.co.jp)                                                 *
 *****************************************************************************/
package jpiere.plugin.dashboardgadget.form;


import java.util.HashMap;
import java.util.List;

import org.adempiere.base.Service;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.dashboard.DashboardPanel;
import org.adempiere.webui.factory.IFormFactory;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MForm;
import org.compiere.model.MRole;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Box;
import org.zkoss.zul.Html;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;


/**
 *  JPIERE-0359
 *  JPiere Plugins(JPPS) Dashboard Gadget Create Pivot Window Gadget
 *
 *  @author Hideaki Hagiwara（h.hagiwara@oss-erp.co.jp）
 *
 */
public class JPiereCreatePivotWindowGadget extends DashboardPanel  implements EventListener<Event> {


	public JPiereCreatePivotWindowGadget()
	{
		super();
		setSclass("views-box");
		this.appendChild(createPivotWindowGadgetPanel());
	}

	HashMap<Integer, Integer> pv_form_map = new HashMap<Integer, Integer>();
	boolean hasPivotWindow = false;
			
	private Box createPivotWindowGadgetPanel()
	{
		//Check Plugin of Pivot Window exist
		List<IFormFactory> factories = Service.locator().list(IFormFactory.class).getServices();
		if (factories != null) 
		{
			for(IFormFactory factory : factories) 
			{
				if(factory.toString().equals("PivotWindow"))
				{
					hasPivotWindow = true;
					break;
				}
			}
		}
		
		if(!hasPivotWindow)
		{
			Vlayout div = new Vlayout();
			this.appendChild(div);
			div.setStyle("font-size: 12px;line-height: 18px;border: 1px solid #dddddd; padding: 2px; margin: 2px");
			div.appendChild(new Html(Msg.getMsg(Env.getCtx(), "JP_PivotWindow_JPiereSupporter")));//Pivot Window use library of ZK Pivottable that is Commercial License.
			div.appendChild(new Html(Msg.getMsg(Env.getCtx(), "JP_SupporterURL")));
			div.appendChild(new Html(Msg.getMsg(Env.getCtx(), "JP_PivotWindow_Demo")));//You can try Pivot Window at JPiere Demo site.
			div.appendChild(new Html(Msg.getMsg(Env.getCtx(), "JP_DemoSiteURL")));//<p>JPiere Demo Site: <a href="http://jpiere.net/webui/" target="_blank">http://jpiere.net/webui/</a></p>
		}
		
		MRole role = MRole.getDefault();
		
		List<MForm> formList = new Query(Env.getCtx(), "AD_Form", "Classname like 'JP_PivotWindow_ID=%'", null)
				.setOnlyActiveRecords(true)
				.list();
		
		Boolean isOK = new Boolean(false);
		for(MForm form : formList)
		{
			isOK = role.getFormAccess(form.getAD_Form_ID());
			if(isOK != null && isOK.booleanValue())
				pv_form_map.put(Integer.valueOf(form.getClassname().substring("JP_PivotWindow_ID=".length())), form.getAD_Form_ID() );
		}
		
		Vbox vbox = new Vbox();
		if(pv_form_map.size() > 0)
		{
		
			List<PO> pivotWindowList = new Query(Env.getCtx(), "JP_PivotWindow", "IsValid='Y' AND IsShowInDashboard='Y'", null)
			.setOnlyActiveRecords(true)
			.setOrderBy("SeqNo")
			.list();
			
			PO[] pivots = pivotWindowList.toArray(new PO[pivotWindowList.size()]);
			int JP_PivotWindow_ID = 0;
			
			for (int i = 0; i < pivots.length; i++)
			{
				PO pivot = pivots[i];
				JP_PivotWindow_ID = pivot.get_ValueAsInt("JP_PivotWindow_ID");
				if(JP_PivotWindow_ID > 0 && pv_form_map.get(JP_PivotWindow_ID) != null && role.getFormAccess(pv_form_map.get(JP_PivotWindow_ID)))
				{
					ToolBarButton btn = new ToolBarButton(pivot.get_ValueAsString("Name"));
					btn.setSclass("link");
					btn.setLabel(pivot.get_Translation("Name"));
					btn.setImage(ThemeManager.getThemeResource("images/" + (Util.isEmpty(pivot.get_ValueAsString("ImageURL")) ? "Info16.png" : pivot.get_Value("ImageURL"))));
					btn.addEventListener(Events.ON_CLICK, this);
					vbox.appendChild(btn);
				}
			}
		
		}

		return vbox;
	
	}
	

	@Override
	public void onEvent(Event event) throws Exception 
	{
		Component comp = event.getTarget();
		String eventName = event.getName();
		if(eventName.equals(Events.ON_CLICK))
		{
			if(comp instanceof ToolBarButton)
			{				
				if(hasPivotWindow)
				{
				
					ToolBarButton btn = (ToolBarButton) comp;
					String actionCommand = btn.getName();
	
					int JP_PivotWindow_ID = new Query(Env.getCtx(), "JP_PivotWindow", "Name = ?", null)
					.setParameters(actionCommand)
					.setOnlyActiveRecords(true)
					.firstIdOnly();
	
					if (JP_PivotWindow_ID<=0)
						return;
					
					SessionManager.getAppDesktop().openForm(pv_form_map.get(JP_PivotWindow_ID));
					
				}else{
					
					FDialog.info(0, this
							, Msg.getMsg(Env.getCtx(), "JP_PivotWindow_JPiereSupporter")//Pivot Window use library of ZK Pivottable that is Commercial License.
							, Msg.getMsg(Env.getCtx(), "JP_PivotWindow_Demo")			//You can try Pivot Window at JPiere Demo site.
							, Msg.getElement(Env.getCtx(), "JP_PivotWindow_ID") );
				}
			}

		}
	}


}
