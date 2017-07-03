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
package jpiere.plugin.dashboardgadget.factory;


import jpiere.plugin.dashboardgadget.form.JPiereCreateInfoGadget;
import jpiere.plugin.dashboardgadget.form.JPiereCreatePivotWindowGadget;

import org.adempiere.webui.factory.IDashboardGadgetFactory;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.zkoss.zk.ui.Component;

/**
 *  JPiere Plugins(JPPS) Dashboard Gadget Form Factory
 *
 *  @author Hideaki Hagiwara（h.hagiwara@oss-erp.co.jp）
 *
 */
public class JPiereDashboardGadgetFactory implements IDashboardGadgetFactory {

	@Override
	public Component getGadget(String uri, Component parent) {

		if (uri != null && uri.startsWith("JP_InfoGadgetCategory_ID="))
		{
			String JP_InfoGadgetCategory_ID = uri.substring("JP_InfoGadgetCategory_ID=".length());
		   return new JPiereCreateInfoGadget(new Integer(JP_InfoGadgetCategory_ID).intValue());
		
		}else if (uri != null && uri.startsWith("JP_PivotWindow")){//JPIERE-0359
			
			MTable pivotWidnowTable = MTable.get(Env.getCtx(), "JP_PivotWindow");
			if(pivotWidnowTable == null)
				return null;
			
			return new JPiereCreatePivotWindowGadget();
		}
		
		return null;
	}

}
