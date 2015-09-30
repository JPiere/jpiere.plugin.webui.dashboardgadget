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

import java.sql.ResultSet;

import jpiere.plugin.dashboardgadget.model.MInfoGadget;
import jpiere.plugin.dashboardgadget.model.MInfoGadgetCategory;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;


/**
 *  JPiere Plugins(JPPS) Dashboard Gadget Model Factory
 *
 *  @author Hideaki Hagiwara（萩原 秀明:h.hagiwara@oss-erp.co.jp）
 *
 */
public class JPiereDashboardGadgetModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if(tableName.equals(MInfoGadget.Table_Name)){
			return MInfoGadget.class;
		}else if(tableName.equals(MInfoGadgetCategory.Table_Name)){
			return MInfoGadgetCategory.class;
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if(tableName.equals(MInfoGadget.Table_Name)){
			return  new MInfoGadget(Env.getCtx(), Record_ID, trxName);
		}else if(tableName.equals(MInfoGadgetCategory.Table_Name)){
			return new MInfoGadgetCategory(Env.getCtx(), Record_ID, trxName);
		}

		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if(tableName.equals(MInfoGadget.Table_Name)){
			return  new MInfoGadget(Env.getCtx(), rs, trxName);
		}else if(tableName.equals(MInfoGadgetCategory.Table_Name)){
			return new MInfoGadgetCategory(Env.getCtx(), rs, trxName);
		}
		return null;
	}

}
