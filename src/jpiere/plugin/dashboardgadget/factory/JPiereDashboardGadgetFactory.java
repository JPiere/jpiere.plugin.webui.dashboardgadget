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

import org.adempiere.webui.factory.IDashboardGadgetFactory;
import org.zkoss.zk.ui.Component;

/**
 *  JPiere Plugins(JPPS) Dashboard Gadget Form Factory
 *
 *  @author Hideaki Hagiwara（萩原 秀明:h.hagiwara@oss-erp.co.jp）
 *
 */
public class JPiereDashboardGadgetFactory implements IDashboardGadgetFactory {
	
	@Override
	public Component getGadget(String uri, Component parent) {

		if (uri != null && uri.startsWith("JP_InfoGadgetCategory_ID="))
		{
			String JP_InfoGadgetCategory_ID = uri.substring("JP_InfoGadgetCategory_ID=".length());
		   return new JPiereCreateInfoGadget(new Integer(JP_InfoGadgetCategory_ID).intValue());
		}
		return null;
	}

}
