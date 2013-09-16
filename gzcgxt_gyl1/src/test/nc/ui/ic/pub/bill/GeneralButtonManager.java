package nc.ui.ic.pub.bill;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.pfxx.IPFxxEJBService;
import nc.ui.ic.ic001.BatchCodeDefSetTool;
import nc.ui.ic.pub.QueryInfo;
import nc.ui.ic.pub.RefMsg;
import nc.ui.ic.pub.barcodeoffline.ExcelReadCtrl;
import nc.ui.ic.pub.barcodeparse.BarcodeparseCtrlUI;
import nc.ui.ic.pub.bill.initref.RefFilter;
import nc.ui.ic.pub.device.DevInputCtrl;
import nc.ui.ic.pub.pf.ICSourceRefBaseDlg;
import nc.ui.ic.pub.pf.QryInICBillDlg;
import nc.ui.ic.pub.print.PrintDataInterface;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillActionListener;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.action.BillTableLineAction;
import nc.ui.pub.query.QueryConditionClient;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.scm.file.DocumentManager;
import nc.ui.scm.print.IFreshTsListener;
import nc.ui.scm.pub.CollectSettingDlg;
import nc.ui.scm.pub.bill.ButtonTree;
import nc.ui.scm.pub.sourceref.IBillReferQueryProxy;
import nc.vo.dm.service.delivery.SourceBillDeliveryStatus;
import nc.vo.ic.pub.GenMethod;
import nc.vo.ic.pub.GenParameterVO;
import nc.vo.ic.pub.ICConst;
import nc.vo.ic.pub.ScaleKey;
import nc.vo.ic.pub.SmartVOUtilExt;
import nc.vo.ic.pub.barcodeoffline.ExcelFileVO;
import nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl;
import nc.vo.ic.pub.bc.BarCodeVO;
import nc.vo.ic.pub.bill.BillStatus;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.bill.IItemKey;
import nc.vo.ic.pub.bill.QryInfoConst;
import nc.vo.ic.pub.exp.ICBusinessException;
import nc.vo.ic.pub.exp.MonthNotEqualException;
import nc.vo.ic.pub.exp.RightcheckException;
import nc.vo.ic.pub.lang.ResBase;
import nc.vo.ic.pub.locator.LocatorVO;
import nc.vo.ic.pub.sn.SerialVO;
import nc.vo.pfxx.util.FileUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.constant.ic.BillMode;
import nc.vo.scm.constant.ic.InOutFlag;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.bill.WhVO;
import nc.vo.scm.ic.exp.ATPNotEnoughException;
import nc.vo.scm.print.PrintConst;
import nc.vo.scm.print.ScmPrintlogVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.smart.SmartFieldMeta;
import nc.vo.so.so120.CreditNotEnoughException;
import nc.vo.so.so120.PeriodNotEnoughException;

import org.w3c.dom.Document;

/**
 * 
 * <p>
 * <b>本类主要完成以下功能：</b>
 * 
 * <ul>
 *  <li>维护并保持库存单据ButtonTree对象
 *  <li>按钮状态的维护
 * </ul> 
 *
 * <p>
 * <b>变更历史（可选）：</b>
 * <p>
 * <p>
 * @version 5.3
 * @since 5.3
 * @author duy
 * @time 2007-3-9 下午04:29:04
 */
public class GeneralButtonManager implements IButtonManager,BillActionListener {
	private ButtonTree m_buttonTree;

	private GeneralBillClientUI m_ui;
    


	public GeneralButtonManager(GeneralBillClientUI clientUI)
			throws BusinessException {
		m_ui = clientUI;
		m_buttonTree = new ButtonTree(m_ui.getFunctionNode());
	}
	
	protected GeneralBillClientUI getClientUI() {
		return m_ui;
	}
	
	protected BillCardPanel getBillCardPanel() {
		return getClientUI().getBillCardPanel();
	}
	
	protected BillListPanel getBillListPanel() {
		return getClientUI().getBillListPanel();
	}
	
	protected void handleException(Exception e) {
		getClientUI().handleException(e);
	}
	
	protected void showWarningMessage(String sMsg) {
		getClientUI().showWarningMessage(sMsg);
	}
	
	protected void showHintMessage(String sMsg) {
		getClientUI().showHintMessage(sMsg);
	}
	
	protected void showErrorMessage(String sMsg) {
		getClientUI().showErrorMessage(sMsg);
	}
	
	protected int showYesNoMessage(String sMsg) {
		return getClientUI().showYesNoMessage(sMsg);
	}
	
	protected Environment getEnvironment() {
		return getClientUI().getEnvironment();
	}
	
	protected int getM_iCurPanel() {
		return getClientUI().getM_iCurPanel();
	}
	
	protected void setM_iCurPanel(int iCurPanel) {
		getClientUI().setM_iCurPanel(iCurPanel);
	}
	
	protected int getM_iLastSelListHeadRow() {
		return getClientUI().getM_iLastSelListHeadRow();
	}
	
	protected ArrayList getM_alListData() {
		return getClientUI().getM_alListData();
	}
	
	protected GeneralBillVO getM_voBill() {
		return getClientUI().getM_voBill();
	}
	
	protected void setM_voBill(GeneralBillVO vo) {
		getClientUI().setM_voBill(vo);
	}
	
	protected void setBillVO(GeneralBillVO vo) {
		getClientUI().setBillVO(vo);
	}
	
	protected int getM_iMode() {
		return getClientUI().getM_iMode();
	}
	
	protected void setM_iMode(int iMode) {
		getClientUI().setM_iMode(iMode);
	}
	
	protected int getInOutFlag() {
		return getClientUI().getInOutFlag();
	}
	
	protected String getBillType() {
		return getClientUI().getBillType();
	}

	public ButtonObject getButton(String buttonCode) {
		return m_buttonTree.getButton(buttonCode);
	}

	public ButtonTree getButtonTree() {
		return m_buttonTree;
	}

	public void onButtonClicked(ButtonObject bo) {
		// getBillCardPanel().tableStopCellEditing();
		getBillCardPanel().stopEditing();

		// 清除定位，2003-07-21 ydy
		getClientUI().clearOrientColor();

		showHintMessage(bo.getName());
		// 父菜单是<新增>
		// 没有单据参照的话，新增在单据编辑菜单，否则在业务类型后。
		if (getClientUI().m_bNeedBillRef && getClientUI().isNeedBillRefWithBillType()) {
			if (bo.getParent()== getButtonTree().getButton(
					ICButtonConst.BTN_ADD)) {
				onBizType(bo);
				getBillCardPanel().transferFocusTo(
						nc.ui.pub.bill.BillCardPanel.HEAD);
			} else if (bo.getParent() == getButtonTree().getButton(
					ICButtonConst.BTN_BUSINESS_TYPE)) {
				//SCMEnv.out("刘家清测试跟踪，重新设定的按钮初始化菜单时的按钮: "+getClientUI().getBillTypeCode());
				//SCMEnv.out("刘家清测试跟踪，重新设定的按钮初始化菜单时的按钮: "+bo.getCode() + ":"+bo.getName()+":"+bo.getTag());

				
				getClientUI().onJointAdd(bo);
				getBillCardPanel().transferFocusTo(
						nc.ui.pub.bill.BillCardPanel.HEAD);
			}
		} else if (bo.getName().equals(getButtonTree().getButton(ICButtonConst.BTN_ADD).getCode())
				|| bo == getButtonTree().getButton(
						ICButtonConst.BTN_BILL_ADD_MANUAL)) {
			onAdd();
			getBillCardPanel().transferFocusTo(
					nc.ui.pub.bill.BillCardPanel.HEAD);
		}
		if (bo.getName().equals(getButtonTree().getButton(ICButtonConst.BTN_ADD).getCode())
				|| bo == getButtonTree().getButton(
						ICButtonConst.BTN_BILL_ADD_MANUAL)) {
			onAdd();
			getBillCardPanel().transferFocusTo(
					nc.ui.pub.bill.BillCardPanel.HEAD);
		}
		if (bo == getButtonTree().getButton(ICButtonConst.BTN_LINE_ADD)) {
      onEditAction(BillTableLineAction.ADDLINE);//onAddLine();
		} else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_LINE_DELETE))
      onEditAction(BillTableLineAction.DELLINE);//onDeleteRow();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_LINE_COPY)) {
      onEditAction(BillTableLineAction.COPYLINE);//onCopyLine();
		} else if (bo == getButtonTree()
				.getButton(ICButtonConst.BTN_LINE_PASTE)) {
      onEditAction(BillTableLineAction.PASTELINE);//onPasteLine();
		}  else if (bo == getButtonTree()
				.getButton(ICButtonConst.BTN_LINE_PASTE_TAIL)) {
      onEditAction(BillTableLineAction.PASTELINETOTAIL);//onPasteLineToEnd();
		}else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_LINE_INSERT)) {
      onEditAction(BillTableLineAction.INSERTLINE);//onInsertLine();
		} else if (bo.getCode().equals(getButtonTree().getButton(ICButtonConst.BTN_SAVE).getCode()))
			onSave();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BILL_DELETE))
			onDelete();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_QUERY))
			onQuery(true);
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_BROWSE_REFRESH))
			onQuery(false);
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_QUERY_RELATED))
			onJointCheck();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BILL_EDIT)) {
			onUpdate();
			getBillCardPanel().transferFocusTo(
					nc.ui.pub.bill.BillCardPanel.HEAD);

		} else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_BILL_CANCEL))
			onCancel();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_SWITCH))
			onSwitch();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXECUTE_SIGN_CANCEL))
			onCancelAudit();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_SIGN))
			onAudit();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BILL_COPY))
			onCopy();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_BROWSE_LOCATE))
			onLocate();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_LINE_SPACE))
			onSpaceAssign();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_LINE_SERIAL))
			onSNAssign();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_QUERY_ONHAND))
			onRowQuyQty();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_QUERY_SUITE))
			onSetpart();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_QUERY_PROVIDER_BARCODE))
			onProviderBarcodeQuery();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_ONHAND))
			onOnHandShowHidden();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_PRINT_PRINT))
			onPrint();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_PRINT_PREVIEW))
			onPreview();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_PRINT_SUM))
			onPrintSumRow();

		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_PRINT_SPACE))
			onPrintLocSN(getClientUI().getWholeBill(getM_iLastSelListHeadRow()));
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BROWSE_TOP))
			onFirst();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BROWSE_PREVIOUS))
			onPrevious();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BROWSE_NEXT))
			onNext();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_BROWSE_BOTTOM))
			onLast();
		if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK))
			onCheckData();
		else if (bo == getButtonTree().getButton(ICButtonConst.BTN_IMPORT_BILL))
			onImportData();
		// 导入主次条码
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_BOTH_BARCODE))
			onImptBCExcel(2);
		// 导入主
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_1ST_BARCODE))
			onImptBCExcel(0);
		// 导入次
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_2ND_BARCODE))
			onImptBCExcel(1);
		// 导入来源单据条码
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_SOURCE_BARCODE))
			onImportBarcodeSourceBill();
		// 条码关闭
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE)) {
			onBarcodeOpenClose(0);
		} else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXECUTE_BARCODE_OPEN)) {
			onBarcodeOpenClose(1);
		} else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_LINE_BARCODE)) {
			onBarCodeEdit();
		} else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT))
			onDocument();
		//modified by liuzy 2008-06-26 V3.3的货位选择功能，V5.5重用
		 else if (bo == getButtonTree().getButton(ICButtonConst.BTN_SEL_LOC))
			 onSelLoc();
		// 由应发（收）自动填写实发（收）
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_LINE_AUTO_FILL))
			onFillNum();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO))
			onPickAuto();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_REFER_IN))
			onRefInICBill();
		// 单据导出
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXPORT_TO_DIRECTORY))
			onBillExcel(1);// 导出到指定目录
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXPORT_TO_XML))// 导出XML
			onBillExcel(3);
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_PRINT_DISTINCT)) {
			onMergeShow();
		} else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ADD_NEWROWNO)) {
			onAddNewRowNo();
		} else if (bo == getButtonTree().getButton(
        ICButtonConst.BTN_CARD_EDIT)) {
		  onLineCardEdit();
    }  else if (bo == getButtonTree().getButton(
        ICButtonConst.BTN_BATCH_EDIT)) {
      onLineBatchEdit();
    }  else if (bo == getButtonTree().getButton(
        ICButtonConst.BTN_DMStateQry)) {
      onDMStateQuery();
    } else if (getM_funcExtend() != null) {
			// 支持功能扩展
			if (BillMode.Card == getM_iCurPanel())
				getM_funcExtend().doAction(bo, getClientUI(), getBillCardPanel(),
						getBillListPanel(), nc.ui.scm.extend.IFuncExtend.CARD);
			else if (BillMode.List == getM_iCurPanel())
				getM_funcExtend().doAction(bo, getClientUI(), getBillCardPanel(),
						getBillListPanel(), nc.ui.scm.extend.IFuncExtend.LIST);
		}

		else {
			getClientUI().onExtendBtnsClick(bo);
		}

	}

	protected void onPasteLineToEnd() {
		  // TODO 自动生成方法存根
		  if (getClientUI().m_voaBillItem == null || getClientUI().m_voaBillItem.length <= 0)
		    return;
			
			int iRowCount = getBillCardPanel().getBodyPanel().getTableModel()
			.getRowCount();
			getBillCardPanel().pasteLineToTail();
			// 增加的行数
			int iRowCount1 = getBillCardPanel().getBodyPanel().getTableModel()
					.getRowCount();
			nc.ui.scm.pub.report.BillRowNo.addLineRowNos(getBillCardPanel(),
					getBillType(), IItemKey.CROWNO, iRowCount1 - iRowCount);
			getClientUI().voBillPastTailLine();

			GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
					getBillCardPanel(), getBillType());
		
	}

	public void setButtonStatus(boolean bUpdateButtons) {
		// TODO 自动生成方法存根
		getClientUI().setButtonStatus(bUpdateButtons);
	}

	/**
	 * 创建者：张欣 
	 * 功能：选择了业务类型 
	 * 参数： 
	 * 返回： 
	 * 例外： 
	 * 日期：(2001-5-9 9:23:32) 
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 */
	protected void onBizType(ButtonObject bo) {
		try {
			// 按钮事件处理
      nc.ui.ic.pub.pf.ICSourceRefBaseDlg.childButtonClicked(bo,
					getEnvironment().getCorpID(), getClientUI()
							.getFunctionNode(), getEnvironment()
							.getUserID(), getBillType(),
					getClientUI());

			// 得到用户选择的业务类型的方法. 20021209
			String sBusiTypeID = getClientUI().getSelBusiType();

			if (!nc.ui.ic.pub.pf.ICSourceRefBaseDlg.makeFlag()) {
				// 非自制方式
				if (nc.ui.ic.pub.pf.ICSourceRefBaseDlg.isCloseOK()) {

					long lTime = System.currentTimeMillis();
					nc.vo.pub.AggregatedValueObject[] vos = null;
					try {
						vos = nc.ui.ic.pub.pf.ICSourceRefBaseDlg.getRetsVos();
						
						} catch (Exception e) {
						nc.ui.ic.pub.tools.GenMethod.handleException(null,
								null, e);
						return;
					}
					if (vos == null || vos.length <= 0)
						return;
					SCMEnv.showTime(lTime, "获得参照VO:"/*-=notranslate=-*/);
					// modified by liuzy 2009-8-21 上午10:33:20 修改连接数，处理订单信息改到GeneralBillClientUI.transBillDataDeal
//					GeneralBillUICtl.procOrdEndAfterRefAdd(
//							(GeneralBillItemVO[]) SmartVOUtilExt
//									.getBodyVOs(vos), getBillCardPanel(), getBillType());

					if (vos != null && vos.length > 0)
						// 检查单据是否来源于新的参照界面
						if (!ICConst.IsFromNewRef.equals(vos[0].getParentVO()
								.getAttributeValue(ICConst.IsFromNewRef))) {
							// 按库存默认方式分单
							vos = GenMethod.splitGeneralBillVOs(
									(GeneralBillVO[]) vos,
									getBillType(), getBillListPanel()
											.getHeadBillModel()
											.getFormulaParse());
							// 将外来单据的单位转换为库存默认单位.
							GenMethod.convertICAssistNumAtUI(
									(GeneralBillVO[]) vos, nc.ui.ic.pub.tools.GenMethod.getIntance());
						}
					//added by lirr 2009-02-21 修改原因：非IsFromNewRef的转单需要设置nshouldoutassistnum精度
						else{
								for(GeneralBillVO vo : (GeneralBillVO[])vos){
									GeneralBillItemVO[] item = vo.getItemVOs();

									ScaleKey sk = new ScaleKey();
									sk.setAssistNumKeys(new String[] { "nshouldoutassistnum" });

									GenMethod.setScale(item, sk, getClientUI().m_ScaleValue);
								}
							

						}//end added by lirr

					// v5 lj 支持多张单据参照生成
					if (vos != null && vos.length == 1) {
						getClientUI().setRefBillsFlag(false);
						getClientUI().setBillRefResultVO(sBusiTypeID, vos);
					} else {
						getClientUI().setRefBillsFlag(true);// 是参照生成多张
						getClientUI().setBillRefMultiVOs(sBusiTypeID, (GeneralBillVO[]) vos);
					}
					// end v5

				}
			} else {
				// 自制单据
				getClientUI().setRefBillsFlag(false);
				getClientUI().onAdd(true, null);
				// 重置单据表体行的存货参照过滤和参照的TextField的可编辑与否
				nc.ui.pub.bill.BillItem bi = bi = getBillCardPanel()
						.getBodyItem("cinventorycode");
				RefFilter.filtInv(bi, getEnvironment().getCorpID(), null);
				// set user selected 业务类型 20021209
				if (getBillCardPanel().getHeadItem("cbiztype") != null) {
					getBillCardPanel().setHeadItem("cbiztype", sBusiTypeID);
					((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
							.getHeadItem("cbiztype").getComponent())
							.setPK(sBusiTypeID);
					nc.ui.pub.bill.BillEditEvent event = new nc.ui.pub.bill.BillEditEvent(
							getBillCardPanel().getHeadItem("cbiztype"),
							sBusiTypeID, "cbiztype");
					getClientUI().afterEdit(event);

				}
				// 默认情况下，退库状态不可以用 add by hanwei 2003-10-19
				nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(getClientUI(),
						false);

			}

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showErrorMessage(e.getMessage());
		}
	}

	/**
	 * 创建者：王乃军 功能：新增处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onAdd() {
		// 当前是列表形式时，首先切换到表单形式
		onAdd(true, null);
	}
	
	protected void onAdd(boolean bUpataBotton, GeneralBillVO voBill) {
		getClientUI().onAdd(bUpataBotton, voBill);
	}
	
	protected boolean onSave() {
		return getClientUI().onSave();
	}

	protected void onAddLine() {
		getBillCardPanel().addLine();
		nc.ui.scm.pub.report.BillRowNo.addLineRowNo(getBillCardPanel(),
				getBillType(), IItemKey.CROWNO);
    getClientUI().voBillAddLine(getBillCardPanel().getBillTable().getSelectedRow());
    nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel(),getBillCardPanel().getBillTable().getSelectedRow());
//  二次开发扩展
    try{
      getClientUI().getPluginProxy().onAddLine();
    }catch(Exception e){
      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
      return ;
    }
	}

	/**
	 * 创建者：王乃军 功能：表单形式下删除行处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected void onDeleteRow() {
		nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel(),getBillCardPanel().getBillTable().getSelectedRow());
		
		int[] selrow = getBillCardPanel().getBillTable().getSelectedRows();
		int length = selrow.length;
		SCMEnv.out("count is " + length);
		int iCurLine = 0; // 当前选中行

		// 删除条码数据 add by hanwei
		if (getM_voBill() != null && selrow != null) {
			for (int i = 0; i < selrow.length; i++) {
				if (getM_voBill().getItemBarcodeVO(selrow[i]) != null) {
					getClientUI().getM_utfBarCode().setRemoveBarcode(getM_voBill()
							.getItemBarcodeVO(selrow[i]));
				}
			}
		}

		if (length == 0) { // 没选中一行，返回
			return;
		} else if (length == 1) { // 删除一行
			int allrownums = getBillCardPanel().getRowCount();
			getBillCardPanel().delLine();
			if (selrow[0] + 1 > allrownums) {
				int iRowCount = getBillCardPanel().getRowCount();
				if (iRowCount > 0) {
					getBillCardPanel().getBillTable().setRowSelectionInterval(
							selrow[0], selrow[0]);
					iCurLine = selrow[0];

				}
			}
		} else { // 删除多行
			getBillCardPanel().getBillModel().delLine(selrow);

			int iRowCount = getBillCardPanel().getRowCount();
			if (iRowCount > 0) {
				getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
				iCurLine = 0;
			}
		}
		// 重置序列号是否可用
		getClientUI().setBtnStatusSN(iCurLine, true);
		GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
				getBillCardPanel(), getBillType());
		// 删除条码数据？待加
    
	}

	protected void onCopyLine() {
		getBillCardPanel().copyLine();
		getClientUI().voBillCopyLine();
	}

	/**
	 * 创建者：王乃军 功能：表单/列表形式切换 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * v5: 支持列表下的多张单据参照生成时，修改按钮和双击表头都会在onSwitch中 处理为新增单据
	 * 
	 * 
	 */
	protected void onSwitch() {

		if (BillMode.List == getM_iCurPanel()) {
			// 列表---〉表单切换
			setM_iCurPanel(BillMode.Card);

			// 置排序主键为空
			getClientUI().m_sLastKey = null;

			getButtonTree().getButton(ICButtonConst.BTN_SWITCH).setHint(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000068")/* @res "切换到列表形式" */);
			getClientUI().updateButton(getButtonTree().getButton(ICButtonConst.BTN_SWITCH));

			// 当列表---〉表单切换时，未改选其它单据，则无需重设表单数据，以提高效率。
			if (getClientUI().m_sLastKey != null
					|| getM_iLastSelListHeadRow() >= 0
					// && m_iCurDispBillNum != m_iLastSelListHeadRow
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()
					&& getM_alListData().get(getM_iLastSelListHeadRow()) != null) {

				// zhx new add a varient to store the ini vocher VO
				getClientUI().m_voBillRefInput = (GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone();
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());

				// v5 lj 支持参照生成多单据
				if (getClientUI().m_bRefBills == true) {

					try {
						//修改人：刘家清 修改时间：2008-12-16 下午02:01:36 修改原因：调拨出入库等单据，可以一次会生成多业务类型的单据，所以处理时要考虑把VO上业务类型设置给缓存。
						if (null != getM_voBill() && null != getM_voBill().getBizTypeid())
							getClientUI().setBizTypeRef(getM_voBill().getBizTypeid());
						
						getClientUI().setBillRefResultVO(getClientUI().getBizTypeRef(),
								new GeneralBillVO[] { getM_voBill() });
					} catch (Exception e1) {
						nc.vo.scm.pub.SCMEnv.error(e1);
					}
				}

				else {
					setBillVO(getM_voBill());
				}

				// 当前单据序号
				getClientUI().m_iCurDispBillNum = getM_iLastSelListHeadRow();
				// 当前单据数
				getClientUI().m_iBillQty = getM_alListData().size();

				getClientUI().setCardMode();
			}
		} else {
			// 表单--->列表切换
			setM_iCurPanel(BillMode.List);
			// 当前单据序号
			getClientUI().m_iCurDispBillNum = getM_iLastSelListHeadRow();

			getClientUI().selectListBill(getM_iLastSelListHeadRow());

			getButtonTree().getButton(ICButtonConst.BTN_SWITCH).setHint(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000312")/* @res "切换到表单形式" */);
			getClientUI().updateButton(getButtonTree().getButton(ICButtonConst.BTN_SWITCH));

			// v5 lj
			if (getClientUI().m_bRefBills == true) {
				setM_iMode(BillMode.Browse);// 更改为浏览状态
				// 如果当前列表下的单据为0（保存后删除的），则修改m_bRefBills=false
				if (getM_alListData() == null || getM_alListData().size() == 0) {
					getClientUI().setRefBillsFlag(false);
				}
			}
		}

		getClientUI().showBtnSwitch();

		// 界面显示
		getClientUI().getM_layoutManager().show();

		setButtonStatus(false);
		// 如果来源单据不为空，设置业务类型等不可用
		getClientUI().ctrlSourceBillUI(false);
		getClientUI().ctrlSourceBillButtons(true);
	}

	protected void onPasteLine() {
		  if (getClientUI().m_voaBillItem == null || getClientUI().m_voaBillItem.length <= 0)
		    return;
      
			int iRowCount = getBillCardPanel().getBodyPanel().getTableModel()
					.getRowCount();
			getBillCardPanel().pasteLine();
			int istartrow = getBillCardPanel().getBillTable().getSelectedRow()
					- getClientUI().m_voaBillItem.length;
			for (int i = 0; i < getClientUI().m_voaBillItem.length; i++)
				getBillCardPanel().getBillModel().setBodyRowVO(
						(GeneralBillItemVO) getClientUI().m_voaBillItem[i].clone(),
						istartrow + i);

			// 增加的行数
			iRowCount = getBillCardPanel().getBodyPanel().getTableModel()
					.getRowCount()
					- iRowCount;
			nc.ui.scm.pub.report.BillRowNo.pasteLineRowNo(getBillCardPanel(),
					getBillType(), IItemKey.CROWNO, iRowCount);
			//
			getClientUI().voBillPastLine();
			GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
					getBillCardPanel(), getBillType());
      
//    二次开发扩展
      try{
        getClientUI().getPluginProxy().onPastLine();
      }catch(Exception e){
        nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
        return ;
      }
	}

	protected void onInsertLine() {
    
  		getBillCardPanel().insertLine();
  		nc.ui.scm.pub.report.BillRowNo.insertLineRowNo(getBillCardPanel(),
  				getBillType(), IItemKey.CROWNO);
      getClientUI().voBillAddLine(getBillCardPanel().getBillTable().getSelectedRow());
      nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel(),getBillCardPanel().getBillTable().getSelectedRow());
      
//    二次开发扩展
      try{
        getClientUI().getPluginProxy().onAddLine();
      }catch(Exception e){
        nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
        return ;
      }

	}

	protected void onDelete() {
		getClientUI().m_timer.start("删除开始"/*-=notranslate=-*/);

		nc.vo.ic.pub.bill.Timer t = new nc.vo.ic.pub.bill.Timer();
		t.start();

		int iSelListHeadRowCount = getBillListPanel().getHeadTable()
				.getSelectedRowCount();
		
		if (iSelListHeadRowCount <= 0) {	
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UCH003")/* @res "请选择要处理的数据！" */);
			return;
		}

		switch (nc.ui.pub.beans.MessageDialog.showYesNoDlg(getClientUI(), null,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH002")/*
																				 * @res
																				 * "是否确认要删除？"
																				 */
				, MessageDialog.ID_NO)) {

		case nc.ui.pub.beans.MessageDialog.ID_YES:
			break;
		default:
			return;
		}
		// 在列表下，多张单据删除;在表单形式下,只能每次删除一张单据,因为表单和列表下选中的单据必须
		// 同步，所以可以统一处理。
		ArrayList alvo = getClientUI().getSelectedBills();
		GeneralBillVO voaDeleteBill[] = new GeneralBillVO[alvo.size()];

		// 操作员日志
		nc.vo.sm.log.OperatelogVO log = getClientUI().getNormalOperateLog();

		for (int i = 0; i < alvo.size(); i++) {
			voaDeleteBill[i] = (GeneralBillVO) alvo.get(i);
			// 当前操作员2002-04-10.wnj
			voaDeleteBill[i].getHeaderVO().setCoperatoridnow(
					getEnvironment().getUserID());
			voaDeleteBill[i].getHeaderVO().setAttributeValue("clogdatenow",
					getEnvironment().getLogDate());
			// 帐期、信用信息
			voaDeleteBill[i].m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_DEL;
			voaDeleteBill[i].m_sActionCode = "DELETE";

			voaDeleteBill[i].setOperatelogVO(log);
		}
		String sError = getClientUI().checkBillsCanDeleted(alvo);
		if (sError != null && sError.length() > 0) {
			showWarningMessage(sError);
			return;
		}
    try{
    
  //  二次开发扩展
      getClientUI().getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.DELETE, voaDeleteBill);
      
  		onDeleteKernel(voaDeleteBill);
      
  		if (getClientUI().isM_bOnhandShowHidden()) {
  			getClientUI().m_pnlQueryOnHand.clearCache();
  			getClientUI().m_pnlQueryOnHand.fresh();
  		}
      
//    二次开发扩展
      getClientUI().getPluginProxy().afterAction(nc.vo.scm.plugin.Action.DELETE, voaDeleteBill);
    
    }catch(Exception e){
      nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null, e);
    }

	}

	/**
	 * 创建者：王乃军 功能：删除处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	private void onDeleteKernel(GeneralBillVO[] voaDeleteBill) {
		getClientUI().m_timer.start("删除开始"/*-=notranslate=-*/);

		nc.vo.ic.pub.bill.Timer t = new nc.vo.ic.pub.bill.Timer();
		t.start();

		try {
			int iSelListHeadRowCount = getBillListPanel().getHeadTable()
					.getSelectedRowCount();

			int[] arySelListHeadRows = new int[iSelListHeadRowCount];
			arySelListHeadRows = getBillListPanel().getHeadTable()
					.getSelectedRows();

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH051")/* @res "正在删除" */);

			ArrayList<GeneralBillItemVO[]> bakitemlist = new ArrayList<GeneralBillItemVO[]>(
					voaDeleteBill.length);
			for (int i = 0; i < voaDeleteBill.length; i++) {
				voaDeleteBill[i].setIsCheckCredit(true);
				voaDeleteBill[i].setIsCheckPeriod(true);
				voaDeleteBill[i].setIsCheckAtp(true);
				voaDeleteBill[i].setIsRwtPuUserConfirmFlag(true);
				voaDeleteBill[i].m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_DEL;
				voaDeleteBill[i].m_sActionCode = "DELETE";
				// voaDeleteBill[i].setStatus(nc.vo.pub.VOStatus.DELETED);
				bakitemlist.add(voaDeleteBill[i].getItemVOs());
			}

			while (true) {
				try {
					for (int i = 0; i < voaDeleteBill.length; i++)
						voaDeleteBill[i].setTransNotFullVo();
					nc.ui.pub.pf.PfUtilClient.processBatch("DELETE",
							getBillType(), getClientUI()
									.getEnvironment().getLogDate(),
							voaDeleteBill);
					break;

				} catch (Exception ee1) {

					BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
							.handleException(null, null, ee1);
					if (realbe != null
							&& realbe.getClass() == RightcheckException.class) {
						showErrorMessage(realbe.getMessage()
								+ nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"4008bill", "UPP4008bill-000069")/*
																			 * @res
																			 * ".\n权限校验不通过,保存失败! "
																			 */);
						getClientUI().getAccreditLoginDialog().setCorpID(
								getEnvironment().getCorpID());
						getClientUI().getAccreditLoginDialog().clearPassWord();
						if (getClientUI().getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
							String sUserID = getClientUI().getAccreditLoginDialog()
									.getUserID();
							if (sUserID == null) {
								throw new Exception(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000070")/*
																		 * @res
																		 * "权限校验不通过,保存失败. "
																		 */);
							} else {
								for (int i = 0; i < voaDeleteBill.length; i++)
									// voaDeleteBill[i].setAccreditUserID(sUserID);
									voaDeleteBill[i].setAccreditBarcodeUserID(
											((RightcheckException) realbe)
													.getFunCodeForRightCheck(),
											sUserID);
								continue;
							}
						} else {
							throw new Exception(nc.ui.ml.NCLangRes
									.getInstance().getStrByID("4008bill",
											"UPP4008bill-000070")/*
																	 * @res
																	 * "权限校验不通过,保存失败. "
																	 */);

						}
					} else if (realbe != null
							&& realbe.getClass() == CreditNotEnoughException.class) {
						// 错误信息显示，并询问用户“是否继续？”
						int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n是否继续？*/);
						// 如果用户选择继续
						if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
							for (int i = 0; i < voaDeleteBill.length; i++)
								voaDeleteBill[i].setIsCheckCredit(false);
							continue;
						} else
							return;
					} else if (realbe != null
							&& realbe.getClass() == PeriodNotEnoughException.class) {
						// 错误信息显示，并询问用户“是否继续？”
						int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n是否继续？*/);
						// 如果用户选择继续
						if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
							for (int i = 0; i < voaDeleteBill.length; i++)
								voaDeleteBill[i].setIsCheckPeriod(false);
							continue;
						} else
							return;
					} else if (realbe != null
							&& realbe.getClass() == ATPNotEnoughException.class) {
						ATPNotEnoughException atpe = (ATPNotEnoughException) realbe;
						if (atpe.getHint() == null) {
							showErrorMessage(atpe.getMessage());
							return;
						} else {
							// 错误信息显示，并询问用户“是否继续？”
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{atpe.getMessage()})/*{0} \r\n是否继续？*/);
							// 如果用户选择继续
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								for (int i = 0; i < voaDeleteBill.length; i++)
									voaDeleteBill[i].setIsCheckAtp(false);
								continue;
							} else {
								return;
							}
						}
					} else {
						if (realbe != null)
							throw realbe;
						else
							throw ee1;
					}
				} finally {
					for (int i = 0; i < voaDeleteBill.length; i++)
						voaDeleteBill[i].setChildrenVO(bakitemlist.get(i));
				}
			}

			getClientUI().m_timer.showExecuteTime("平台删除"/*-=notranslate=-*/);
			// showTime(lTime, "删除");

			// 删除后列表界面处理,设置m_iLastSelListHeadRow。
			getClientUI().removeBillsOfList(arySelListHeadRows);
			getClientUI().m_timer.showExecuteTime("removeBillsOfList");
			// 在表单形式下删除，显示与列表相应的单据
			if (BillMode.Card == getM_iCurPanel()
					&& getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()) {
				setBillVO((GeneralBillVO) getM_alListData().get(
						getM_iLastSelListHeadRow()));
				// 执行公式
				// getBillCardPanel().getBillModel().execLoadFormula();
				// getBillCardPanel().updateValue();
			} else if (getM_alListData() == null
					|| getM_alListData().size() == 0) {
				getBillCardPanel().getBillModel().clearBodyData();
				getBillCardPanel().updateValue();
			}
			// 清空当前的货位数据
			getClientUI().setM_alLocatorData(null);
			getClientUI().m_alLocatorDataBackup = null;

			// 清空当前的序列号数据
			getClientUI().setM_alSerialData(null);
			getClientUI().m_alSerialDataBackup = null;
			getClientUI().m_timer.showExecuteTime("Before 重设按钮状态"/*-=notranslate=-*/);
			// 重设按钮状态
			setButtonStatus(false);
			getClientUI().m_timer.showExecuteTime("重设按钮状态"/*-=notranslate=-*/);
			// 根据是否为来源单据控制单据界面
			getClientUI().ctrlSourceBillUI(true);
			getClientUI().m_timer.showExecuteTime("来源单据控制单据界面"/*-=notranslate=-*/);

			// delete the excel barcode file
			OffLineCtrl ctrl = new OffLineCtrl(getClientUI());
			ctrl.deleteExcelFile(voaDeleteBill, getEnvironment().getCorpID());

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"40080604", "UPT4020402001-000006")/* @res "删除成功" */);

		} catch (Exception e) {
			e = nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
			handleException(e);
			showWarningMessage(e.getMessage());
		}
		t.stopAndShow("删除合计"/*-=notranslate=-*/);
	}

	/**
	 * 
	 * 方法功能描述：查询和刷新按钮的方法。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 查询按钮：onQuery(true);
	 * <p>
	 * 刷新按钮：onQuery(false);
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param bQuery
	 *            如果是进行查询，为true；如果是进行刷新，为false
	 *            <p>
	 * @author duy
	 * @time 2007-3-2 下午03:07:52
	 */
	protected void onQuery(boolean bQuery) {
		// 为了兼容以前的版本，加入了成员变量来存储是进行查询还是进行刷新
		getClientUI().m_bQuery = bQuery;

		// 执行查询（刷新）过程，在该方法中进行了查询和刷新的分别处理 
		onQuery();
	}

	protected void onQuery() {
		// DUYONG 增加查询和刷新的代码
		try {
      
      boolean isFrash = !(getClientUI().isBQuery() || !getClientUI().getQryDlgHelp().isBEverQry());
      Object[] ret = getClientUI().getQryDlgHelp().queryData(isFrash);
      if(ret==null || ret[0]==null || !((UFBoolean)ret[0]).booleanValue())
        return;
      ArrayList alListData = (ArrayList)ret[1];
      if(!isFrash){
        //刷新按钮 “曾经查询过”
        setButtonStatus(true);
      }
      int cardOrList = getM_iCurPanel();
      
//			nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
//			timer.start("@@查询开始：");
//			getClientUI().m_sBnoutnumnull = null;
//			QryConditionVO voCond = null;
//
//			// 将原单据类型（卡片/列表）纪录下来，如果在卡片界面执行了刷新，则无须切换到列表界面
//			int cardOrList = getM_iCurPanel();
//			// 如果是[(1)进行查询(2)从来没有进行查询过但是点击了刷新按钮]，则显示查询模板进行查询
//			// 如果曾经查询过，并且点击了刷新按钮，则跳过此段代码
//			if (getClientUI().m_bQuery || !getClientUI().m_bEverQry) {
//				getClientUI().getConditionDlg().showModal();
//				timer.showExecuteTime("@@getConditionDlg().showModal()：");
//
//				if (getClientUI().getConditionDlg().getResult() != nc.ui.pub.beans.UIDialog.ID_OK)
//					// 取消返回
//					return;
//
//				// 获得qrycontionVO的构造
//				voCond = getClientUI().getQryConditionVO();
//
//				// 记录查询条件备用
//				getClientUI().m_voLastQryCond = voCond;
//
//				// 如果是进行查询，则将“曾经查询过”的标识设置成true（这样，才能够进行“刷新”的操作）
//				getClientUI().m_bEverQry = true;
//				// 刷新按钮
//				setButtonStatus(true);
//			} else
//				voCond = getClientUI().m_voLastQryCond;
//			
//			//addied by liuzy 2008-03-31 修正单据日期“从”、“到”字段名
//			
//      /* yb query
//       String str_where = voCond.getQryCond();
//			if(null != str_where && !"".equals(str_where)){
//				if(str_where.indexOf("dbilldate.from") > -1)
//					str_where = str_where.replace("dbilldate.from", "dbilldate");
//				if(str_where.indexOf("dbilldate.end") > -1)
//					str_where = str_where.replace("dbilldate.end", "dbilldate");
//				voCond.setQryCond(str_where);
//       
//			}*/
//
//			// DUYONG 此处应该是刷新按钮功能的开始执行处
//
//			// 如果使用公式则必须传voaCond 到后台. 修改 zhangxing 2003-03-05
//			//nc.vo.pub.query.ConditionVO[] voaCond = getClientUI().getConditionDlg()
//			//		.getConditionVO();
//			
//			//addied by liuzy 2008-03-28 V5.03需求：单据查询增加起止日期
//      /* yb query
//			for (int i = 0; i < voaCond.length; i++) {
//				if (null != voaCond[i] && null != voaCond[i].getFieldCode()
//						&& (voaCond[i].getFieldCode().equals("head.dbilldate.from") || voaCond[i]
//								.getFieldCode().equals("head.dbilldate.end"))) {
//					voaCond[i].setFieldCode("head.dbilldate");
//				}
//			}
//      */
//			
//      /* yb query
//			//voCond.setParam(QryConditionVO.QRY_CONDITIONVO, getClientUI().getAndConditionVO());
//      */
//
//			voCond.setIntParam(0, GeneralBillVO.QRY_HEAD_ONLY_PURE);
//
//			if (getClientUI().m_sBnoutnumnull != null) {
//				// 是否存在实发数量
//				voCond.setParam(33, getClientUI().m_sBnoutnumnull);
//			}
//
//			voCond.setParam(QryConditionVO.QRY_LOGCORPID, getClientUI()
//					.getEnvironment().getCorpID());
//			voCond.setParam(QryConditionVO.QRY_LOGUSERID, getClientUI()
//					.getEnvironment().getUserID());
//
//			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
//					"4008bill", "UPP4008bill-000012")/* @res "正在查询，请稍候..." */);
//			timer.showExecuteTime("Before 查询：：");
//
//			ArrayList alListData = (ArrayList) GeneralBillHelper.queryBills(
//					getBillType(), voCond);
//
//			timer.showExecuteTime("查询时间：");

			getClientUI().setDataOnList(alListData, true);

			// DUYONG 当执行刷新操作，并且当前界面为卡片类型时，不应该切换到列表类型的界面中
			if (!getClientUI().isBQuery()
					&& getM_iCurPanel() != cardOrList) {
				onSwitch();
			}
		} catch (Exception e) {
			handleException(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000014")/* @res "查询出错。" */);
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000015")/* @res "查询出错：" */
					+ e.getMessage());
		}
	}

	/**
	 * 此处插入方法说明。 功能描述:根据表体行的单据ID和单据类型联查上下游单据。 作者：程起伍 输入参数: 返回值: 异常处理:
	 * 日期:(2003-4-22 16:09:14)
	 */
	protected void onJointCheck() {

		if (getM_iLastSelListHeadRow() >= 0
				&& getM_alListData() != null
				&& getM_alListData().size() > getClientUI()
						.getM_iLastSelListHeadRow()
				&& getM_alListData().get(
						getM_iLastSelListHeadRow()) != null) {

			GeneralBillVO voBill = null;
			GeneralBillHeaderVO voHeader = null;
			// 得到单据VO
			voBill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
			// 得到单据表头VO
			voHeader = voBill.getHeaderVO();

			if (voHeader == null) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000098")/* @res "没有要联查的单据！" */);
				return;
			}
			String sBillPK = null;
			String sBillTypeCode = null;
			String sBillCode = null;

			sBillPK = voHeader.getCgeneralhid();
			sBillTypeCode = voHeader.getCbilltypecode();
			sBillCode = voHeader.getVbillcode();
			// 如果sBillPK和sBillTypeCode为空，联查没有意义。
			if (sBillPK == null || sBillTypeCode == null) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000099")/* @res "该行没有对应单据！" */);
				return;
			}
			nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(
					getClientUI(), sBillTypeCode, sBillPK, null, getEnvironment().getUserID(),
					/*getEnvironment().getCorpID()*/sBillCode);
			soureDlg.showModal();
		} else {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000154")/* @res "没有联查的单据！" */);
		}
	}

	/**
	 * 创建者：王乃军 功能：修改处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onUpdate() {

		// v5 lj
		if (getClientUI().m_bRefBills == true) {
			onSwitch();
			return;
		}
		// end v5
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH027")/* @res "正在修改" */);
		// 读序列号及货位--如果需要的话。
		getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);
		GeneralBillVO voMyBill = null;
		// arraylist 有的话用他的,没有话,就是新的.
		if (getM_alListData() != null
				&& getM_alListData().size() > getM_iLastSelListHeadRow())
			voMyBill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
		// 需要用原来的数据的clone，否在在编辑过程中，会同步修改原来的数据。
		if (voMyBill != null) {
			ArrayList alTemp = voMyBill.getLocatorsClone();
			if (alTemp != null) {
				getClientUI().setM_alLocatorData(null);
				getClientUI().setM_alLocatorData(alTemp);
			}
			alTemp = voMyBill.getSNsClone();
			if (alTemp != null) {
				getClientUI().setM_alSerialData(null);
				getClientUI().setM_alSerialData(voMyBill.getSNsClone());
			}
		}

		// 判断单据是否是从U8零售中传入的单据，如果是，则不允许编辑
		java.util.ArrayList<GeneralBillVO> alBills = new java.util.ArrayList<GeneralBillVO>(
				1);
		alBills.add(voMyBill);

		String billCodes = getClientUI().getU8RMBillCodes(alBills);
		if (billCodes != null && billCodes.length() > 0) {
			String message1 = nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008U8RM-000001", null,
					new String[] { billCodes });
			/*
			 * @res "单据号是{0}的单据是由U8零售上传并自动生成的，不能执行该操作！"
			 */
			showWarningMessage("(" + message1 + ")");
			return;
		}

		// 当前是列表形式时，首先切换到表单形式
		if (BillMode.List == getM_iCurPanel())
			onSwitch();

		// 当前正显示单据，则置为可编辑方式。无单据显示时此按钮不可用。
		if (BillMode.Browse == getM_iMode()) {
			getBillCardPanel().updateValue();
			getBillCardPanel().setEnabled(true);

			// v5 lj
			if (getClientUI().m_bRefBills == true)
				setM_iMode(BillMode.New);
			else
				setM_iMode(BillMode.Update);

			setButtonStatus(false);
		}
		// 保存当前的货位数据，以防取消操作。 useless in fact
		getClientUI().m_alLocatorDataBackup = getClientUI().getM_alLocatorData();
		// 保存当前的序列号数据，以防取消操作。 useless in fact
		getClientUI().m_alSerialDataBackup = getClientUI().getM_alSerialData();
		// 如果来源单据不为空，设置业务类型等不可用
		//修改人：刘家清  修改时间：2009-11-5 下午03:37:19 修改原因：修改时可修改仓库，一定要在控制在有来源单据仓库不可修改之后。
		getClientUI().freshWHEditable();

		getClientUI().ctrlSourceBillUI(false);
		// 修改状态时，仓库不可修改。
		//修改人：刘家清 修改时间：2008-7-14 下午03:38:48 修改原因：仓库在单据修改时，普通单可以进行修改。
		/*if (getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE) != null)
			getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE)
					.setEnabled(false);*/
		if (getBillCardPanel().getHeadItem(IItemKey.WASTEWAREHOUSE) != null)
			getBillCardPanel().getHeadItem(IItemKey.WASTEWAREHOUSE).setEnabled(
					false);
		

		// 修改状态时，库存组织不可修改。
		if (getBillCardPanel().getHeadItem(IItemKey.CALBODY) != null)
			getBillCardPanel().getHeadItem(IItemKey.CALBODY).setEnabled(false);
		if (getBillCardPanel().getHeadItem(IItemKey.WASTECALBODY) != null)
			getBillCardPanel().getHeadItem(IItemKey.WASTECALBODY).setEnabled(
					false);
//		修改人：刘家清 修改时间：2008-7-14 下午03:38:48 修改原因：单据号在单据修改时，普通单和特殊单都可以进行修改。
		if (!getClientUI().m_bIsEditBillCode && getBillCardPanel().getHeadItem("vbillcode") != null)
			getBillCardPanel().getHeadItem("vbillcode").setEnabled(false);

		// 更新按纽
		getClientUI().updateButtons();

		// 根据表头退库标志，确定退库状态，和单据上退库UI add by hanwei 2003-10-19
		nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(getClientUI());
		// 默认不是导入数据 add by hanwei 2003-10-30
		getClientUI().setIsImportData(false);

		// if current bill is barcode manage, check if flag is "open", then set
		// the file's flag to "close"; when save, first check
		OffLineCtrl ctrl = new OffLineCtrl(getClientUI());
		ctrl.onUpdateBill(voMyBill, getEnvironment().getCorpID());

		getClientUI().setUpdateBillInitData();
	}

	protected void onCancel() {

		switch (nc.ui.pub.beans.MessageDialog.showYesNoDlg(getClientUI(), null,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH067")/*
																				 * @res
																				 * "是否确定要取消？"
																				 */
				, MessageDialog.ID_NO)) {

		case nc.ui.pub.beans.MessageDialog.ID_YES:
			// songhy, 2009-11-16，start，解决保存时，拔掉网线导致数据重复的问题
			getClientUI().clearOID();
			// songhy, 2009-11-16，end.
			break;
		default:
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH045")/* @res "正在取消" */);
		if (-1 < getM_iLastSelListHeadRow() && null != getM_alListData()
				.get(getM_iLastSelListHeadRow()))
			setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
					.get(getM_iLastSelListHeadRow())).clone());
		
		nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel());
		
		GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);

		// v5 lj 支持参照生成多张单据
		if (getM_iCurPanel() == BillMode.List) {
			if (getClientUI().m_bRefBills == true) {
				setM_iMode(BillMode.Browse);
				getClientUI().setRefBillsFlag(false);
        getClientUI().getBillCardPanel().setEnabled(false);
				getClientUI().setDataOnList(getClientUI().getM_alRefBillsBak(), false);
				getClientUI().setRefBtnStatus();
				return;
			}
		}
		if (getM_iCurPanel() == BillMode.Card) {
			if (getClientUI().m_bRefBills == true) {
				onSwitch();
				return;
			}
		}
		getClientUI().getEditCtrl().resetCardEditFlag(getBillCardPanel());
		GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);
		// end
		
		getClientUI().getM_utfBarCode().setText(null);

		if (getM_iMode() != BillMode.Browse) {

			// when edit, cancel should re-write the excel'file status
			if (getM_iMode() == BillMode.Update) {
				OffLineCtrl ctrl = new OffLineCtrl(getClientUI());
				ctrl.cancelEdit(getM_voBill());
			}
			if (getM_voBill() != null)
				getClientUI().getM_utfBarCode().setRemoveBarcode(getM_voBill().getItemVOs());
			getBillCardPanel().setEnabled(false);
			setM_iMode(BillMode.Browse);
			getBillCardPanel().resumeValue();
			// 恢复货位数据
			getClientUI().setM_alLocatorData(getClientUI().m_alLocatorDataBackup);
			// 清备份数据
			getClientUI().m_alLocatorDataBackup = null;

			// 恢复序列号数据
			getClientUI().setM_alSerialData(getClientUI().m_alSerialDataBackup);
			// 清序列号备份数据
			getClientUI().m_alSerialDataBackup = null;

			// 恢复billvo
			if (getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()
					&& getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());
				setBillVO(getM_voBill());
				// resumeValue恢复错误，刷新表尾显示
				getClientUI().setTailValue(0);
			} else {
				// 否则情况表单界面！
				GeneralBillVO voNullBill = new GeneralBillVO();
				voNullBill.setParentVO(new GeneralBillHeaderVO());
				voNullBill
						.setChildrenVO(new GeneralBillItemVO[] { new GeneralBillItemVO() });
				getBillCardPanel().setBillValueVO(voNullBill);
				getBillCardPanel().getBillModel().clearBodyData();
			}
			if (getM_voBill() != null)
				getClientUI().getM_utfBarCode().setAddBarcodes(getM_voBill().getItemVOs());
			setButtonStatus(false);
			// 根据是否为来源单据控制单据界面
			getClientUI().ctrlSourceBillUI(true);

		}
		// 修改人：刘家清 修改日期：2007-8-13下午04:51:06
		// 修改原因：解决如下问题,先做退货的入库单，录入条码后，数量为负的；然后再做正的时候，录入条码，增加的数量也是为负的。
		getClientUI().m_bFixBarcodeNegative = false;// 条码数量为正数
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH008")/*
							 * @res "取消成功"
							 */);
	}

	/**
	 * 创建者：王乃军 功能：取消记账 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onCancelAudit() {
		try {
			if (getClientUI().m_bRefBills) {
				showHintMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000003")/*新增状态,不能取消签字*/);
				return;
			}
			switch (nc.ui.pub.beans.MessageDialog.showYesNoDlg(getClientUI(), null,
					ResBase.getIsCancleSign()/* @res "是否确定要取消签字？" */
					, MessageDialog.ID_NO)) {

			case nc.ui.pub.beans.MessageDialog.ID_YES:
				break;
			default:
				return;
			}
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000006")/*
														 * @res "正在取消签字，请稍候..."
														 */);

			if (BillMode.List == getM_iCurPanel()
					&& getBillListPanel().getHeadTable().getSelectedRowCount() > 1) {
				getClientUI().onBatchAction(ICConst.CANCELSIGN);
				return;
			}
			// 设置m_voBill,以读取控制数据。
			if (getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()
					&& getM_alListData().get(getM_iLastSelListHeadRow()) != null)
				// 这里不能clone(),修改m_voBill同时修改list.
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());
			if (getM_voBill() != null) {
				// 检查单据是否符合取消签字条件，如果不符合，给出提示
				String message = getClientUI().checkBillForCancelAudit(getM_voBill());
				if (message != null && message.length() > 0) {
					showWarningMessage(message);
					return;
				}

				// 设置条码状态未没有被修改
        GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
				// 支持平台，clone一个，以便于以后的处理，同时防止修改了m_voBill
				GeneralBillVO voAudit = (GeneralBillVO) getM_voBill().clone();
				voAudit = getClientUI().getCancelAuditVO(voAudit);
				long lTime = System.currentTimeMillis();
				// 返回值
				ArrayList alRet = null;
				// GeneralBillVO voAuditnew = (GeneralBillVO) voAudit.clone();
				GeneralBillItemVO[] itemvos = voAudit.getItemVOs();
//		      二次开发扩展
		        getClientUI().getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.UNAUDIT, new GeneralBillVO[]{voAudit});
				while (true) {
					try {

						// add by liuzy 2007-11-02 10:16 压缩数据
						// ObjectUtils.objectReference(voAudit);
						// voAudit = (GeneralBillVO) voAudit.clone();
						voAudit.setTransNotFullVo();
						// 执行取消签字
						alRet = (ArrayList) nc.ui.pub.pf.PfUtilClient
								.processAction("CANCELSIGN", getBillType(),
										getEnvironment().getLogDate(), voAudit);
						break;

					} catch (Exception ee1) {

						BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
								.handleException(null, null, ee1);
						if (realbe != null
								&& realbe.getClass() == CreditNotEnoughException.class) {
							// 错误信息显示，并询问用户“是否继续？”
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n是否继续？*/);
							// 如果用户选择继续
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setIsCheckCredit(false);
								continue;
							} else
								return;
						} else if (realbe != null
								&& realbe.getClass() == PeriodNotEnoughException.class) {
							// 错误信息显示，并询问用户“是否继续？”
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n是否继续？*/);
							// 如果用户选择继续
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setIsCheckPeriod(false);
								continue;
							} else
								return;
						} else if (realbe != null
								&& realbe.getClass() == ATPNotEnoughException.class) {
							ATPNotEnoughException atpe = (ATPNotEnoughException) realbe;
							if (atpe.getHint() == null) {
								showErrorMessage(atpe.getMessage());
								return;
							} else {
								// 错误信息显示，并询问用户“是否继续？”
								int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{atpe.getMessage()})/*{0} \r\n是否继续？*/);
								// 如果用户选择继续
								if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
									voAudit.setIsCheckAtp(false);
									continue;
								} else {
									return;
								}
							}
						} else {
							if (realbe != null)
								throw realbe;
							else
								throw ee1;
						}
					} finally {
						voAudit.setChildrenVO(itemvos);
					}
				}

				getClientUI().showTime(lTime, "取消签字"/*-=notranslate=-*/);
				// 返回处理
				String sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000309")/* @res "取消签字出错！" */;

				// ---- old ------如果是保存即签字需要刷新界面,当然，数量此时是必输项，在保存时检查了。
				// ---- old ------只要能保存，说明数量已经正确录入了。
				// -- new ---因为走平台，保存完整动作执行完后，还要重读是否签字了。
				String sBillStatus = GeneralBillUICtl
						.updateDataAfterCacelAudit(getClientUI(), voAudit);
				getClientUI().showTime(lTime, "freshStatusTs");
				if (sBillStatus != null && sBillStatus.equals(BillStatus.FREE)) {
					// int curmode=getM_iCurPanel();
					getClientUI().freshAfterCancelSignedOK();
					// if(curmode==BillMode.Card)
					// onSwitch();
					sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"4008bill", "UPP4008bill-000310")/*
																 * @res
																 * "取消签字成功！"
																 */;
				}

				// 取消签字后，要根据有否来源单据对菜单删除按钮控制
				getClientUI().ctrlSourceBillButtons(true);
//		      二次开发扩展
		        getClientUI().getPluginProxy().afterAction(nc.vo.scm.plugin.Action.UNAUDIT, new GeneralBillVO[]{voAudit});
		        
				showHintMessage(sMsg);
			}
		} catch (Exception e) {
			e = nc.vo.ic.pub.GenMethod
			.getRealBusiException(e);
			handleException(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000007")/* @res "取消签字出错。" */);
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000008")/* @res "取消签字出错:" */
					+ e.getMessage());
		}

	}

	/**
	 * 创建者：王乃军 功能：签字 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
	 * 
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 */
	protected void onAudit() {

		getClientUI().m_timer.start("签字开始："/*-=notranslate=-*/);
		try {
			if (getClientUI().m_bRefBills) {
				showHintMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000004")/*新增状态,不能签字*/);
				return;
			}

			if (BillMode.List == getM_iCurPanel()
					&& getBillListPanel().getHeadTable().getSelectedRowCount() > 1) {
				getClientUI().onBatchAction(ICConst.SIGN);
				return;
			}

			if (getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()
					&& getM_alListData().get(getM_iLastSelListHeadRow()) != null)
				// 这里不能clone(),修改m_voBill同时修改list.
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());

			if (getM_voBill() != null) {
				
				/*
				 * UFTime ufdPre1 = new UFTime(System.currentTimeMillis());//
				 * 系统当前时间 UFDateTime ufdPre = new UFDateTime(m_sLogDate + " " +
				 * ufdPre1.toString());
				 */

				// 设置条码状态未没有被修改
        GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
				// 支持平台，clone一个，以便于以后的处理，同时防止修改了m_voBill
				GeneralBillVO voAudit = (GeneralBillVO) getM_voBill().clone();
				voAudit = getClientUI().getAuditVO(voAudit, null);

				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000003")/*
															 * @res
															 * "正在签字，请稍候..."
															 */);
				getClientUI().m_timer.showExecuteTime("before 平台签字："/*-=notranslate=-*/);
//      二次开发扩展
        getClientUI().getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.AUDIT, new GeneralBillVO[]{voAudit});

				while (true) {
					try {

						// 审核的核心方法
						getClientUI().onAuditKernel(voAudit);
						break;

					} 
					catch (RightcheckException e) {
						showErrorMessage(e.getMessage()
								+ nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"4008bill", "UPP4008bill-000069")/*
																			 * @res
																			 * ".\n权限校验不通过,保存失败! "
																			 */);
						getClientUI().getAccreditLoginDialog().setCorpID(
								getEnvironment().getCorpID());
						getClientUI().getAccreditLoginDialog().clearPassWord();
						if (getClientUI().getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
							String sUserID = getClientUI().getAccreditLoginDialog()
									.getUserID();
							if (sUserID == null) {
								throw new Exception(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000070")/*
																		 * @res
																		 * "权限校验不通过,保存失败. "
																		 */);
							} else {
								// voAudit.setAccreditUserID(sUserID);
								voAudit.setAccreditBarcodeUserID(e
										.getFunCodeForRightCheck(), sUserID);
								continue;
							}
						} else {
							throw new Exception(nc.ui.ml.NCLangRes
									.getInstance().getStrByID("4008bill",
											"UPP4008bill-000070")/*
																	 * @res
																	 * "权限校验不通过,保存失败. "
																	 */);

						}
					}
					catch (Exception ee1) {

						BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
								.handleException(null, null, ee1);
						if (realbe != null
								&& realbe.getClass() == CreditNotEnoughException.class) {
							// 错误信息显示，并询问用户“是否继续？”
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n是否继续？*/);
							// 如果用户选择继续
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setIsCheckCredit(false);
								continue;
							} else
								return;
						} else if (realbe != null
								&& realbe.getClass() == PeriodNotEnoughException.class) {
							// 错误信息显示，并询问用户“是否继续？”
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n是否继续？*/);
							// 如果用户选择继续
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setIsCheckPeriod(false);
								continue;
							} else
								return;
						} else if (realbe != null
								&& realbe.getClass() == ATPNotEnoughException.class) {
							ATPNotEnoughException atpe = (ATPNotEnoughException) realbe;
							if (atpe.getHint() == null) {
								showErrorMessage(atpe.getMessage());
								return;
							} else {
								// 错误信息显示，并询问用户“是否继续？”
								int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{atpe.getMessage()})/*{0} \r\n是否继续？*/);
								// 如果用户选择继续
								if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
									voAudit.setIsCheckAtp(false);
									continue;
								} else {
									return;
								}
							}
						} else if (realbe != null
								&& realbe.getClass() == nc.vo.ic.pub.exp.OtherOut4MException.class) {
							// 错误信息显示，并询问用户“是否继续？”
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000005", null, new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi","UPP4008busi-000401"),realbe.getMessage()})/*{0}{1}，是否继续？*/);
							// 如果用户选择继续
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setCheckOutFor4M(false);
								continue;
							} else {
								return;
							}
						} else if (realbe != null
								&& realbe.getClass() == nc.vo.ic.ic009.PackCheckBusException.class) {
							handleException(realbe);
							showHintMessage(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000105")/*
																	 * @res
																	 * "保存出错。"
																	 */);
							String se = realbe.getMessage();
							if (se != null) {
								int index = se.indexOf("$$ZZZ$$");
								if (index >= 0)
									se = se.substring(index + 7);
							}
							// packCheckBusDialog = null;
							getClientUI().getpackCheckBusDialog().setText(se);

							getClientUI().getpackCheckBusDialog().showModal();
							return;
						} else if (realbe != null
								&& realbe.getClass() == MonthNotEqualException.class) {
							int iflag = getClientUI().showYesNoCancelMessage(
									realbe.getMessage());
							if (iflag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.getHeaderVO().setAttributeValue(
										"isBusiAndbilldateMonthEqual",
										UFBoolean.FALSE);
							}
						} else {
							if (realbe != null)
								throw realbe;
							else
								throw ee1;
						}
					}
				}

				// showTime(lTime, "签字");
				getClientUI().m_timer.showExecuteTime("平台签字时间："/*-=notranslate=-*/);
				// 返回处理
				String sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000307")/* @res "签字成功！" */;
				// ---- old ------如果是保存即签字需要刷新界面,当然，数量此时是必输项，在保存时检查了。
				// ---- old ------只要能保存，说明数量已经正确录入了。
				// -- new ---因为走平台，保存完整动作执行完后，还要重读是否签字了。
				String sBillStatus = getClientUI().afterAuditFrushData(voAudit,false); 
         // 用新的方法替代,一次后台交互

				getClientUI().m_timer.showExecuteTime("freshStatusTs");
				if (sBillStatus != null && !sBillStatus.equals(BillStatus.FREE)
						&& !sBillStatus.equals(BillStatus.DELETED)) {
					SCMEnv.out("**** signed ***");
				} else {
					sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"4008bill", "UPP4008bill-000005")/* @res "签字出错！" */;
					sMsg += nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"4008bill", "UPP4008bill-000552")/*
																 * @res
																 * "当前单据状态不正确"
																 */;
				}
        
//      二次开发扩展
        getClientUI().getPluginProxy().afterAction(nc.vo.scm.plugin.Action.AUDIT, new GeneralBillVO[]{voAudit});

				showHintMessage(sMsg);
			}
			nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getClientUI().getBillCardPanel());
			
		} catch (Exception be) {
			// ###################################################
			// 签字异常，后台日志 add by hanwei 2004-6-8
			nc.ui.ic.pub.bill.GeneralBillUICtl.insertOperatelogVO(getM_voBill()
					.getHeaderVO(), nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40080402", "UPT40080402-000013")/*
																	 * @res "签字"
																	 */, nc.vo.scm.funcs.Businesslog.MSGERROR + "" + be.getMessage());
			be = nc.vo.ic.pub.GenMethod
			.getRealBusiException(be);
			handleException(be);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000004")/* @res "签字出错。" */
					+ be.getMessage());			
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000005")/* @res "签字出错：" */
					+ be.getMessage());
			
			if (be instanceof ICBusinessException){
				ICBusinessException ee = (ICBusinessException) be;
				// 更改颜色
				nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getClientUI().getBillCardPanel(),ee);
			}
		}

	}
  

	/**
	 * 创建者：王乃军 功能：复制当前单据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onCopy() {
		try {
			// 当前是列表形式时，首先切换到表单形式
			if (BillMode.List == getM_iCurPanel()) {
				onSwitch();

			}

			if (getM_iLastSelListHeadRow() < 0
					|| getM_alListData() == null
					|| getM_iLastSelListHeadRow() >= getM_alListData().size()
					|| getM_alListData().get(getM_iLastSelListHeadRow()) == null) {
				SCMEnv.out("sn error,or list null");
				return;
			}
			// 判断单据是否是从U8零售中传入的单据，如果是，则不允许复制
			java.util.ArrayList<GeneralBillVO> alBills = new java.util.ArrayList<GeneralBillVO>(
					1);
			alBills.add((GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow()));
			String billCodes = getClientUI().getU8RMBillCodes(alBills);
			if (billCodes != null && billCodes.length() > 0) {
				String message1 = nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008U8RM-000001", null,
						new String[] { billCodes });
				/*
				 * @res "单据号是{0}的单据是由U8零售上传并自动生成的，不能执行该操作！"
				 */
				showWarningMessage(message1);
				return;
			}
			// added by lirr 2009-10-27下午02:28:24 查询出来直接复制的话没有单据的货位信息
			getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);
			// 显示复制的单据内容
			setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow())).clone());
			if (getM_voBill() == null)
				return;

			GeneralBillUICtl.processBillVOFor(getM_voBill(),
					GeneralBillUICtl.Action.COPY, getClientUI().m_bIsInitBill);

			// 新增
			getBillCardPanel().addNew();
			int iRowCount = getM_voBill().getItemCount();

			getClientUI().setBillVO(getM_voBill(),true,false);
			// 重置其它数据

			// 设置当前的条码框的条码 韩卫 2004-04-05
			if (getClientUI().getM_utfBarCode() != null)
				getClientUI().getM_utfBarCode().setCurBillItem(null);

			nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();

			bmTemp.setNeedCalculate(false);
			for (int row = 0; row < iRowCount; row++) {
				// 设置行状态为新增
				if (bmTemp != null) {
					bmTemp.setRowState(row, nc.ui.pub.bill.BillModel.ADD);
					bmTemp.setValueAt(null, row, IItemKey.NAME_BODYID);
				}

			}
			bmTemp.setNeedCalculate(true);

			// 设置新增单据的初始数据，如日期，制单人等。
			getClientUI().setNewBillInitData();
			getBillCardPanel().setEnabled(true);
			setM_iMode(BillMode.New);
			// 设置单据号是否可编辑
			if (getBillCardPanel().getHeadItem("vbillcode") != null)
				getBillCardPanel().getHeadItem("vbillcode").setEnabled(
						getClientUI().m_bIsEditBillCode);

			setButtonStatus(true);

		} catch (Exception e) {
			handleException(e);

		}
	}

	/**
	 * 创建者：王乃军 功能：定位单据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void onLocate() {
		if (getM_alListData() == null || getM_alListData().size() < 1)
			return;

		nc.ui.scm.pub.report.OrientDialog dlgOrient = null;
		if (getM_iCurPanel() == BillMode.Card) {
			dlgOrient = new nc.ui.scm.pub.report.OrientDialog(getClientUI(),
					getBillCardPanel().getBillModel(), getBillCardPanel()
							.getBodyItems(), getBillCardPanel().getBillTable());
			dlgOrient.showModal();
			if (dlgOrient.getResult() == nc.ui.pub.beans.UIDialog.ID_OK) {
				getClientUI().m_isLocated = true;
			}
		} else {
			dlgOrient = new nc.ui.scm.pub.report.OrientDialog(getClientUI(),
					getBillListPanel().getHeadBillModel(), getBillListPanel()
							.getBillListData().getHeadItems(),
					getBillListPanel().getHeadTable());

			dlgOrient.showModal();
			if (dlgOrient.getResult() == nc.ui.pub.beans.UIDialog.ID_OK) {
				getClientUI().m_isLocated = true;
			}
		}
	}

	/**
	 * 创建者：王乃军 功能：货位分配 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onSpaceAssign() {
		// 浏览方式下先查询货位/序列号，当然qryLocSN对每张单据只查一次库。
		// m_dlgSpaceAllocation = null;

		// if (BillMode.Browse == m_iMode)
		getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);

		// 这里是一次分配，实际上可以支持选行分配的。
		// 在先选择完序列号后剩余的需货位分配的行就要选行处理。
		// 处理方法：保存数据对应的行号，返回数据后set到相应的位置即可。

		InvVO[] voInv = null;
		// 仓库
		WhVO voWh = null;

		// 表单形式下
		if (BillMode.Card == getM_iCurPanel()) {
			// 滤去空行
			if (BillMode.Update == getM_iMode() || BillMode.New == getM_iMode())
				getClientUI().filterNullLine();
			if (getM_voBill() != null) {
				// 属性VO
				GeneralBillItemVO voItemPty[] = getM_voBill().getItemVOs();
				// ID
				GeneralBillItemVO voItemID[] = getClientUI().getBodyVOs();
				voInv = new InvVO[getM_voBill().getItemCount()];
				// 合并属性、ID
				for (int row = 0; row < getM_voBill().getItemCount(); row++) {
					voItemPty[row].setIDItems(voItemID[row]);
					// 带属性的InvVO
					voInv[row] = voItemPty[row].getInv();
				}
				voWh = getM_voBill().getWh();
			}
		} else
		// 列表形式下察看，先读数据，所以一定要放在setInitVOs之前
		if (getM_iLastSelListHeadRow() >= 0
				&& getM_iLastSelListHeadRow() < getM_alListData().size()) {
			// 置入各行的货位分配等辅表数据
			GeneralBillVO bvo = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
			if (bvo != null) {
				int iCount = bvo.getItemCount();
				voInv = new InvVO[iCount];
				for (int line = 0; line < iCount; line++)
					voInv[line] = bvo.getItemInv(line);
				// warehouse
				voWh = bvo.getWh();
			}

		}
		if (BillMode.Browse != getM_iMode())
			// 置入各行的货位分配等辅表数据
			// resetBodyAssistData(m_iLastSelListHeadRow);
			// else
			// 出库的单据行时请先执行“序列号”，在序列号界面上汇总货位。
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000022")/*
														 * @res
														 * "出库的单据行时请先执行“序列号”，在序列号界面上汇总货位。"
														 */);
// added by lirr 2009-11-19下午04:23:34 增加参数getClientUI().isCheckAssetInv()
		UFBoolean isCapitalStor = voWh.getIsCapitalStor();
		String cbilltypecode = getClientUI().getBillTypeCode();
		boolean isCheckAssetInv = isCapitalStor.booleanValue()
        && (ScmConst.m_purchaseIn.equals(cbilltypecode) || ScmConst.m_allocationIn
            .equals(cbilltypecode)) ? true : getClientUI().isCheckAssetInv();
		getClientUI().getSpaceAllocationDlg().setData(getInOutFlag(), voWh, voInv,
				getM_iMode(), getClientUI().getM_alLocatorData(), getClientUI().getM_alSerialData(),isCheckAssetInv);
		int result = getClientUI().getSpaceAllocationDlg().showModal();

		if (nc.ui.pub.beans.UIDialog.ID_OK == result
				&& (BillMode.New == getM_iMode() || BillMode.Update == getM_iMode())) {
			ArrayList alRes = getClientUI().getSpaceAllocationDlg().getDataSpaceVO();
			// 保存
			// 分配的序列号,如果返回结果的某个element是null,表示是出库的存货，不通过货位分配界面分配序列号。所以不能置到序列号数据。
			ArrayList alSnRes = null;

			for (int row = 0; row < getM_voBill().getItemCount(); row++) {
				if (getM_voBill().getItemVOs()[row].getInOutFlag() == InOutFlag.IN) {
					alSnRes = getClientUI().getSpaceAllocationDlg().getDataSNVO(row);
					if (alSnRes != null && alSnRes.size() > 0) {
						SerialVO[] voaSN = new SerialVO[alSnRes.size()];
						// toArray ERR! ????
						for (int hh = 0; hh < alSnRes.size(); hh++)
							voaSN[hh] = (SerialVO) alSnRes.get(hh);

						getClientUI().getM_alSerialData().set(row, voaSN);
						SCMEnv.out(row + " sn is set!");
					}
				}
			}
			// 设置货位
			if (alRes != null && alRes.size() > 0) {
				// 如果需要，初始化之
				if (getClientUI().getM_alLocatorData() == null)
					getClientUI().initM_alLocatorData(alRes.size());
				for (int i = 0; i < alRes.size(); i++) {
					LocatorVO voLoc[] = (LocatorVO[]) alRes.get(i);
					if (i < voInv.length && voInv[i].getIsSerialMgt() != null
							&& voInv[i].getIsSerialMgt().intValue() == 1
							&& voInv[i].getInOutFlag() != InOutFlag.IN)
						continue;
					// 只设置有数据的，因为序列号也会修改货位数据
					if (voLoc != null && voLoc.length > 0) {
						if (getClientUI().getM_alLocatorData().size() < i + 1)
							getClientUI().getM_alLocatorData().add(i, voLoc);
						getClientUI().getM_alLocatorData().set(i, voLoc);
					} else {
						getClientUI().getM_alLocatorData().set(i, null);
					}
					getClientUI().setBodySpace(i);

					SCMEnv.out(i + " space is set!");
					// }
				}

			}
		}

	}

	/**
	 * 创建者：王乃军 功能：序列号分配 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onSNAssign() {
		nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
		timer.start(NCLangRes.getInstance().getStrByID("bill", "bill-000006")/*@@序列号分配开始：*/);
		// 浏览方式下先查询货位/序列号，当然qryLocSN对每张单据只查一次库。
		// if (BillMode.Browse == m_iMode) {
		getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);
		// timer.showExecuteTime("@@浏览方式下查询货位/序列号查询时间：");
		// }
		// 当前选中的行
		int iCurSelBodyLine = -1;
		if (BillMode.Card == getM_iCurPanel()) {
			iCurSelBodyLine = getBillCardPanel().getBillTable()
					.getSelectedRow();
			if (iCurSelBodyLine < 0) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000016")/*
															 * @res
															 * "请选中要进行序列号分配的行。"
															 */);
				return;
			}
		} else {
			iCurSelBodyLine = getBillListPanel().getBodyTable()
					.getSelectedRow();
			if (iCurSelBodyLine < 0) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000017")/*
															 * @res
															 * "请选中要查看序列号的行。"
															 */);
				return;
			}
		}
		InvVO voInv = null;
		// 仓库
		WhVO voWh = null;
		// 单据VO,表单下是m_voBill，列表下读自m_alListData.
		GeneralBillVO voBill = null;

		// 浏览状态下察看，先读数据，所以一定要放在setInitVOs之前

		// 读表体vo,区分列表下还是表单下
		// 表单形式下
		if (BillMode.Card == getM_iCurPanel()) {
			if (getM_voBill() == null || getM_voBill().getItemCount() <= 0) {
				SCMEnv.out("bill null E.");
				return;
			}
			voBill = getM_voBill();
			// 数据VO
			GeneralBillItemVO voItem = getClientUI().getBodyVO(iCurSelBodyLine);
			// 属性VO
			GeneralBillItemVO voItemPty = voBill.getItemVOs()[iCurSelBodyLine];
			// 合并
			if (voItemPty != null)
				voItemPty.setIDItems(voItem);
			// 完整的存货行数据
			if (voItem != null)
				voInv = voItemPty.getInv();

			if (voBill != null)
				voWh = voBill.getWh();
			
			// 如果是非浏览模式，并且仓库是货位管理，则提醒入库时应走货位。
			if (getM_iMode() != BillMode.Browse) {
				if (voWh != null
						&& voWh.getIsLocatorMgt() != null
						&& voWh.getIsLocatorMgt().intValue() == 1
						&& voItem.getInOutFlag() == InOutFlag.IN
						&& getM_voBill().getItemValue(iCurSelBodyLine,
								"cspaceid") == null) {
					nc.ui.pub.beans.MessageDialog.showHintDlg(getClientUI(),
							nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"4008bill", "UPPSCMCommon-000270")/*
																		 * @res
																		 * "提示"
																		 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"4008bill", "UPP4008bill-000018")/*
																		 * @res
																		 * "入库时序列号分配请先执行“货位分配”，在货位界面上执行“序列号”。！"
																		 */);
					return;

				}
			}

		} else
		// 列表形式下察看，先读数据，所以一定要放在setInitVOs之前
		if (getM_iLastSelListHeadRow() >= 0
				&& getM_iLastSelListHeadRow() < getM_alListData().size()) {
			// 置入各行的货位分配等辅表数据
			voBill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
			if (voBill == null) {
				SCMEnv.out("bill null E.");
				return;
			}
			voInv = voBill.getItemInv(iCurSelBodyLine);
			// warehouse
			voWh = voBill.getWh();
			// 公司PK
			voWh.setPk_corp(getEnvironment().getCorpID());
		}
		
		
		if ("40080822".equals(getClientUI().getFunctionNode())){
			if (null != voBill.getItemValue(iCurSelBodyLine, "cwarehouseid") && ! voBill.getItemValue(iCurSelBodyLine, "cwarehouseid").toString().equals(voWh.getCwarehouseid()) ){
				int iQryMode = QryInfoConst.WH;
				// 参数
				Object oParam = voBill.getItemValue(iCurSelBodyLine, "cwarehouseid").toString();
				Object oRet;
				try {
					oRet = GeneralBillHelper.queryInfo(
							new Integer(iQryMode), oParam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null, e);
					return;
				}
				if (null != oRet){
					voWh = (WhVO) oRet;
					voWh.setPk_corp(getEnvironment().getCorpID());
				}
			}
		}

		if (voInv != null && voBill != null) {
			// 设置上当前单据的PK.
			voInv.setCgeneralhid(voBill.getHeaderVO().getPrimaryKey());
			String csrctype = voBill.getItemVOs()[iCurSelBodyLine]
					.getCsourcetype();
			if ("A3".equals(csrctype))
				voInv.setCfreezeid(voBill.getItemVOs()[iCurSelBodyLine]
						.getCsourcebillbid());
			else if (csrctype != null
					&& (csrctype.startsWith("5") || csrctype.startsWith("30")))
				voInv.setCfreezeid(voBill.getItemVOs()[iCurSelBodyLine]
						.getCfirstbillbid());
			// 对应的入库单表体行，可能为null，
			// 当出库跟踪入库不能为null
			String sCorrespondBodyItemPK = voInv.getCcorrespondbid();
			// 出库是否跟踪入库
			// zhx
			if (getClientUI().getIsInvTrackedBill(voInv)
					&& voInv.getInOutFlag() == InOutFlag.OUT
					&& (sCorrespondBodyItemPK == null || sCorrespondBodyItemPK
							.trim().length() == 0)) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000019")/*
															 * @res
															 * "请先指定对应入库单，然后再试。"
															 */);
				return;
			}

			// 非浏览模式下数量不能为零
			if (BillMode.Card == getM_iCurPanel()
					&& (BillMode.Update == getM_iMode() || BillMode.New == getM_iMode())) {
				Object oQty = voInv.getAttributeValue(getEnvironment()
						.getNumItemKey());
				if (oQty == null || oQty.toString().trim().length() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000020")/*
																			 * @res
																			 * "请先输入数量。"
																			 */);
					return;
				}
				// 数量必须不能小数 by hanwei 2003-07-20
				if (nc.vo.ic.pub.check.VOCheck.checkIsDecimal(oQty.toString())) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000021")/*
																			 * @res
																			 * "序列号管理存货不允许输入小数。"
																			 */);
					return;
				}

			}

			// if (BillMode.Browse == m_iMode)
			// //置入各行的货位分配等辅表数据
			// resetBodyAssistData(m_iLastSelListHeadRow);
			// ydy2002-12-19
			LocatorVO aLoc = null;
			LocatorVO[] voLocs = null;
			if (getClientUI().getM_alLocatorData() != null && getClientUI().getM_alLocatorData().size() > 0)
				voLocs = (LocatorVO[]) getClientUI().getM_alLocatorData().get(iCurSelBodyLine);

			if (voLocs == null || voLocs.length != 1) {
				// showHintMessage("货位数据有误");
				aLoc = new LocatorVO();

			} else
				aLoc = voLocs[0];
			// end ydy
			// -浏览；非货位仓库的序列号入库；出库序列号
			SerialVO[] serials = null;
			if (getClientUI().getM_alSerialData() != null && getClientUI().getM_alSerialData().size() > 0)
				serials = (SerialVO[]) getClientUI().getM_alSerialData().get(iCurSelBodyLine);
			else {
				serials = new SerialVO[] {};
				getClientUI().getM_alSerialData().add(iCurSelBodyLine, serials);
			}
			getClientUI().getSerialAllocationDlg().setDataVO(getInOutFlag(), voWh, aLoc,
					voInv, getM_iMode(), serials, voLocs);

			// 当前的序列号们

			int result = getClientUI().getSerialAllocationDlg().showModal();
			if (nc.ui.pub.beans.UIDialog.ID_OK == result
					&& (BillMode.New == getM_iMode() || BillMode.Update == getM_iMode())) {
				SerialVO voaSN[] = getClientUI().getSerialAllocationDlg().getDataSNVO();
				// 保存分配结果
				getClientUI().getM_alSerialData().set(iCurSelBodyLine, voaSN);
				// if (voaSN != null)
				// for (int i = 0; i < voaSN.length; i++)
				// SCMEnv.out("ret sn[" + i + "] is" +
				// voaSN[i].getVserialcode());
				// 如果是出库的话，还可能有货位分配数据
				ArrayList alRes = getClientUI().getSerialAllocationDlg().getDataSpaceVO();
				if (alRes != null && alRes.size() > 0) {
					SCMEnv.out("space is ready!");
					LocatorVO voaLoc[] = new LocatorVO[alRes.size()];

					UFDouble dTempGrossNum = null;
					UFDouble dTempNum = null;
					UFDouble dTemp = new UFDouble(0.0);
					Object oTempValue = getBillCardPanel().getBodyValueAt(
							iCurSelBodyLine,
							getEnvironment().getGrossNumItemKey());
					if (oTempValue != null
							&& oTempValue.toString().trim().length() > 0)
						dTempGrossNum = new UFDouble(oTempValue.toString());
					oTempValue = getBillCardPanel().getBodyValueAt(
							iCurSelBodyLine, getEnvironment().getNumItemKey());
					if (oTempValue != null
							&& oTempValue.toString().trim().length() > 0)
						dTempNum = new UFDouble(oTempValue.toString());

					for (int i = 0; i < alRes.size(); i++) {
						voaLoc[i] = (LocatorVO) alRes.get(i);
						// 由于序列号参照上面没有毛重,此处特意重置一下
						if (dTempGrossNum != null && dTempNum != null) {
							UFDouble dGrossNum = null;
							UFDouble dNum = null;
							if (voaLoc[i].getNinspacenum() != null)
								dNum = voaLoc[i].getNinspacenum();
							else
								dNum = voaLoc[i].getNoutspacenum();

							if (dNum != null) {
								dGrossNum = dNum.div(dTempNum).multiply(
										dTempGrossNum);
								if (i == alRes.size() - 1) {
									dGrossNum = dTempGrossNum.sub(dTemp);
								}
								voaLoc[i].setNoutgrossnum(dGrossNum);
								dTemp = dTemp.add(dGrossNum);
							}
						}
					}

					// 保存到对应的行
					getClientUI().getM_alLocatorData().set(iCurSelBodyLine, voaLoc);
				}
				
				getClientUI().setBodySpace(iCurSelBodyLine);

			}
		} else {
			SCMEnv.out("----> no bill ERR.");
		}
	}

	/**
	 * 存量查询 修改 创建日期：(2001-4-18 19:45:39)
	 */
	protected void onRowQuyQty() { // finished
		nc.vo.ic.pub.bill.GeneralBillVO nowVObill = null;
		int rownow = -1;
		if (getM_iCurPanel() == BillMode.Card) {
			rownow = getBillCardPanel().getBillTable().getSelectedRow();
			nowVObill = getM_voBill();
		} else {
			rownow = getBillListPanel().getBodyTable().getSelectedRow();
			nowVObill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
		}

		String WhID = "";
		String InvID = "";

		if ((nowVObill != null) && (rownow >= 0)) {

			WhID = (String) nowVObill.getHeaderValue(IItemKey.WAREHOUSE);
			InvID = (String) nowVObill.getItemValue(rownow, "cinventoryid");
		}

		getClientUI().getIohdDlg().setParam(WhID, InvID);

		getClientUI().getIohdDlg().onQuery();
		getClientUI().getIohdDlg().showModal();
	}

	/**
	 * 判断选择行是否成套件 创建日期：(2004-3-12 21:14:17)
	 */
	protected void onSetpart() {
		int rownow = -1;
		String sInvID = null;
		if (getM_iCurPanel() == BillMode.Card) {
			rownow = getBillCardPanel().getBillTable().getSelectedRow();
			if ((getM_voBill() != null) && (rownow >= 0)) {
				sInvID = (String) getM_voBill().getItemValue(rownow,
						"cinventoryid");
			}
		}
		else if (getM_iCurPanel() == BillMode.List) {
			rownow = getBillListPanel().getBodyTable().getSelectedRow();
			sInvID = (String) ((GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow())).getItemInv(rownow)
					.getAttributeValue("cinventoryid");
		}

		if (rownow < 0) {
			MessageDialog.showErrorDlg(getClientUI(), null, nc.ui.ml.NCLangRes
					.getInstance().getStrByID("common", "UCH004")/*
																	 * @res
																	 * "请选择要处理的数据行！"
																	 */);
			return;
		}

		if (getEnvironment().getCorpID() == null) {
			MessageDialog.showErrorDlg(getClientUI(), null, nc.ui.ml.NCLangRes
					.getInstance().getStrByID("4008bill", "UPP4008bill-000109")/*
																				 * @res
																				 * "当前登陆公司ID为空！"
																				 */);
			return;
		}
		if (sInvID == null || sInvID.trim().length() == 0) {
			MessageDialog.showErrorDlg(getClientUI(), null, nc.ui.ml.NCLangRes
					.getInstance().getStrByID("4008bill", "UPP4008bill-000110")/*
																				 * @res
																				 * "选中行没有存货编码！"
																				 */);
			return;
		}

		getClientUI().getSetpartDlg().setParam(getEnvironment().getCorpID(), sInvID);
		getClientUI().getSetpartDlg().showSetpartDlg();

	}

	/**
	 * 创建人：刘家清 创建日期：2007-7-13下午02:50:27
	 * 创建原因：库存入库业务中的采购入库单、产成品入库单、委托加工入库单、其它入库单、调拨入库单、借入单、来料加工入库单中的辅助查询下增加“供应商条码查询”按钮。
	 * 
	 */
	protected void onProviderBarcodeQuery() {
		RefMsg msg = new RefMsg(getClientUI());
		msg.setIBillOperate(nc.ui.ic.ic009.Const.PBQFromIC);
		nc.ui.ic.ic009.ClientUI.openNodeAsDlg(getClientUI(), msg);
	}

	/**
	 * 李俊 显示或者隐藏现存量面板
	 * 
	 */
	protected void onOnHandShowHidden() {
		if (getM_iCurPanel() == BillMode.List) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000065")/* @res "请切换到卡片界面！" */);
			return;
		}

		getClientUI().setM_bOnhandShowHidden(!getClientUI().isM_bOnhandShowHidden());

		if (getClientUI().isM_bOnhandShowHidden()) {
			getClientUI().m_sMultiMode = MultiCardMode.CARD_TAB;
		} else
			getClientUI().m_sMultiMode = MultiCardMode.CARD_PURE;

		getClientUI().getM_layoutManager().show();
	}

	/**
	 * 创建者：仲瑞庆 功能：打印 参数： 返回： 例外： 日期：(2001-5-10 下午 4:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 修改说明：增加打印次数控制 修改者：邵兵 2005-01-12
	 */
	protected void onPrint() {
		try {
			// 调出打印窗口
			// 依当前是列表还是表单而定打印内容
			if (getM_iMode() == BillMode.Browse
					&& getM_iCurPanel() == BillMode.Card) { // 浏览

				/* 增加打印次数控制后的打印方案 */
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000047")/*
															 * @res
															 * "正在打印，请稍候..."
															 */);
				// 准备数据：获得要打印的vo.
				GeneralBillVO voBill = null;

				if (getM_iLastSelListHeadRow() != -1
						&& null != getM_alListData()
						&& getM_alListData().size() != 0) {
					if (getClientUI().m_sLastKey != null && getM_voBill() != null)
						voBill = getM_voBill();
					else
						voBill = (GeneralBillVO) getM_alListData().get(
								getM_iLastSelListHeadRow());
					if (getBillCardPanel().getHeadItem("vcustname") != null)
						voBill.setHeaderValue("vcustname", getBillCardPanel()
								.getHeadItem("vcustname").getValue());
				}

				if (voBill == null) {
					voBill = new GeneralBillVO();
				}
				if (voBill.getParentVO() == null) {
					voBill.setParentVO(new GeneralBillHeaderVO());
				}
				if ((voBill.getChildrenVO() == null)
						|| (voBill.getChildrenVO().length == 0)
						|| (voBill.getChildrenVO()[0] == null)) {
					GeneralBillItemVO[] ivo = new GeneralBillItemVO[1];
					ivo[0] = new GeneralBillItemVO();
					voBill.setChildrenVO(ivo);
				}
				// reset PrintEntry
				getClientUI().m_print = null;

				if (getClientUI().getPrintEntry().selectTemplate() < 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000048")/*
																			 * @res
																			 * "请先定义打印模板。"
																			 */);
					return;
				}

				// 卡片打印
				printOnCard(voBill, false);

			} else if (getM_iCurPanel() == BillMode.List) { // 列表
				/* 增加打印次数控制前的打印方案 */
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "请先查询或录入单据。"
																			 */);
					return;
				}

				// 得到要打印的列表vo,ArrayList.
				ArrayList alBill = getClientUI().getSelectedBills();
				// 置小数精度
				getClientUI().setScaleOfListData(alBill);
				if (alBill == null || alBill.size() <= 0
						|| alBill.get(0) == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "请选择要处理的数据！"
															 */);
					return;
				}

				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000047")/*
															 * @res
															 * "正在打印，请稍候..."
															 */);
				// 修改人：刘家清 修改日期：2007-10-29上午09:43:07 修改原因：解决批量打印时，有些单据货位编码打印不出来。
				if (null != getBillCardPanel().getBodyItem("vspacename")
						&& getBillCardPanel().getBodyItem("vspacename")
								.isShow()) {
					GeneralBillVO vospBill = null;
					for (int i = 0; i < alBill.size(); i++) {
						vospBill = (GeneralBillVO) alBill.get(i);
						if (null != vospBill.getItemVOs()
								&& 0 < vospBill.getItemVOs().length
								&& null != vospBill.getItemVOs()[0]
								&& (null == vospBill.getItemVOs()[0]
										.getCspaceid() || (null != vospBill
										.getItemVOs()[0].getCspaceid() && ""
										.equals(vospBill.getItemVOs()[0]
												.getCspaceid().toString()))))
							getClientUI().appendLocator(vospBill);
					}
				}

				// 列表下打印
				printOnList(alBill);

				// showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				// "SCMCOMMON", "UPPSCMCommon-000133")/* @res "就绪" */);

			} else
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000052")/*
															 * @res
															 * "请注意：您只能在浏览状态下打印"
															 */);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000061")/* @res "打印出错" */
					+ e.getMessage());
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：打印预览 参数： 返回： 例外： 日期：(2001-5-10 下午 4:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 修改说明：增加打印次数控制 修改者：邵兵 2004-01-12
	 */
	protected void onPreview() {

		try {
			// 调出打印窗口
			// 依当前是列表还是表单而定打印内容
			if (getM_iMode() == BillMode.Browse
					&& getM_iCurPanel() == BillMode.Card) { // 浏览

				/* 增加打印次数控制后的打印方案 */
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000047")/*
															 * @res
															 * "正在打印，请稍候..."
															 */);
				// 准备数据：获得要打印的vo.
				GeneralBillVO voBill = null;

				if (getM_iLastSelListHeadRow() != -1
						&& null != getM_alListData()
						&& getM_alListData().size() != 0) {
					if (getClientUI().m_sLastKey != null && getM_voBill() != null)
						voBill = getM_voBill();
					else
						voBill = (GeneralBillVO) getM_alListData().get(
								getM_iLastSelListHeadRow());
					if (getBillCardPanel().getHeadItem("vcustname") != null)
						voBill.setHeaderValue("vcustname", getBillCardPanel()
								.getHeadItem("vcustname").getValueObject());
				}

				if (voBill == null) {
					voBill = new GeneralBillVO();
				}
				if (voBill.getParentVO() == null) {
					voBill.setParentVO(new GeneralBillHeaderVO());
				}
				if ((voBill.getChildrenVO() == null)
						|| (voBill.getChildrenVO().length == 0)
						|| (voBill.getChildrenVO()[0] == null)) {
					GeneralBillItemVO[] ivo = new GeneralBillItemVO[1];
					ivo[0] = new GeneralBillItemVO();
					voBill.setChildrenVO(ivo);
				}

				// reset PrintEntry
				getClientUI().m_print = null;

				if (getClientUI().getPrintEntry().selectTemplate() < 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000048")/*
																			 * @res
																			 * "请先定义打印模板。"
																			 */);
					return;
				}

				// 卡片下预览
				printOnCard(voBill, true);

			} else if (getM_iCurPanel() == BillMode.List) { // 列表

				/* 增加打印次数控制后的打印方案 */
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "请先查询或录入单据。"
																			 */);
					return;
				}
				// 需要的化，查询缺少的单据数据
				// queryLeftItem(m_alListData);

				ArrayList alBill = getClientUI().getSelectedBills();
				if (alBill == null || alBill.size() <= 0
						|| alBill.get(0) == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "请选择要处理的数据！"
															 */);
					return;
				}
				// 置小数精度
				getClientUI().setScaleOfListData(alBill);

				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000100")/*
															 * @res
															 * "正在生成第一张单据的打印预览数据，请稍候..."
															 */);

				GeneralBillVO voBill = (GeneralBillVO) alBill.get(0);
				// 单据主表
				GeneralBillHeaderVO headerVO = voBill.getHeaderVO();
				String sBillID = headerVO.getPrimaryKey();

				// 构造PringLogClient以及设置PrintInfo
				ScmPrintlogVO voSpl = new ScmPrintlogVO();
				voSpl = new ScmPrintlogVO();
				voSpl.setCbillid(sBillID); // 单据主表的ID
				voSpl.setVbillcode(headerVO.getVbillcode());// 传入单据号，用于显示。
				voSpl.setCbilltypecode(headerVO.getCbilltypecode());
				voSpl.setCoperatorid(headerVO.getCoperatorid());
				voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));// 固定
				voSpl.setPk_corp(getEnvironment().getCorpID());
				voSpl.setTs(headerVO.getTs());// 单据主表的TS

				SCMEnv.out("ts=========tata" + voSpl.getTs());
				nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();

				nc.ui.pub.print.PrintEntry pe = getClientUI().getPrintEntryNew();

				if (pe.selectTemplate() < 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000048")/*
																			 * @res
																			 * "请先定义打印模板。"
																			 */);
					return;
				}

				plc.setPrintEntry(pe);
				// 设置单据信息
				plc.setPrintInfo(voSpl);
				// 设置ts和printcount刷新监听.
				plc.addFreshTsListener(new FreshTsListener());
				// 设置打印监听
				pe.setPrintListener(plc);
				
				GeneralBillVO[] destvoBills =  (GeneralBillVO[])getClientUI().getPluginProxy().beforePrint(new GeneralBillVO[]{(GeneralBillVO)voBill.clone()});
				if (null != destvoBills &&  0 < destvoBills.length && destvoBills[0] instanceof GeneralBillVO)
					getClientUI().getDataSource().setVO(destvoBills[0]);
				else
				// 打印预览
					getClientUI().getDataSource().setVO(voBill);
				pe.setDataSource(getClientUI().getDataSource());
				pe.preview();

			} else
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000101")/*
															 * @res
															 * "请注意：您只能在浏览状态下执行打印预览。"
															 */);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000061")/* @res "打印出错" */
					+ e.getMessage());
		}
	}
	

	/**
	 * 
	 * 增加一个内部类. 继承IFreshTsListener. 实现打印后对ts及printcount的更新.
	 * 
	 * @author 邵兵 创建时间: 2004-12-23
	 * 
	 */
	private class FreshTsListener implements IFreshTsListener {

		/*
		 * （非 Javadoc）
		 * 
		 * @see nc.ui.scm.print.IFreshTsListener#freshTs(java.lang.String,
		 *      java.lang.String)
		 */
		public void freshTs(String sBillID, String sTS, Integer iPrintCount) {
			// fresh local TS with voPr.getNewTs();

			SCMEnv.out("new Ts = " + sTS);
			SCMEnv.out("new iPrintCount = " + iPrintCount);

			if (getM_alListData() == null || getM_alListData().size() == 0)
				return;

			// 判断打印的vo是否仍在缓存中．
			// 在打印预览状态打印时，缓存vo可能会有改变，故需要判断．
			int index = 0;
			GeneralBillVO voBill = null;
			GeneralBillHeaderVO headerVO = null;
			for (; index < getM_alListData().size(); index++) {
				voBill = (GeneralBillVO) getM_alListData().get(index);
				headerVO = voBill.getHeaderVO();

				// 在sBillID传入时，已经判断sBillID不为null.
				if (sBillID.equals(headerVO.getPrimaryKey()))
					break;
			}

			if (index == getM_alListData().size()) // 不在缓存vo中，无需进行更新．
				return;

			// 在缓存vo中
			headerVO.setAttributeValue("ts", sTS);
			headerVO.setAttributeValue("iprintcount", iPrintCount);

			if (getM_iCurPanel() == BillMode.Card) { // Card
				if (index == getClientUI().m_iCurDispBillNum) { // 如果为当前card显示vo.
					getBillCardPanel().setHeadItem("ts", sTS);
					getBillCardPanel().setTailItem("iprintcount", iPrintCount);
				}
			} else { // List
				int iPrintColumn = getBillListPanel().getHeadBillModel()
						.getBodyColByKey("ts");
				getBillListPanel().getHeadBillModel().setValueAt(sTS, index,
						iPrintColumn);
				iPrintColumn = getBillListPanel().getHeadBillModel()
						.getBodyColByKey("iprintcount");
				getBillListPanel().getHeadBillModel().setValueAt(iPrintCount,
						index, iPrintColumn);
			}
		}
	}


	/**
	 * 汇总行打印，通过汇总条件对话框获得汇总条件，汇总行VO，打印预览
	 */
	protected void onPrintSumRow() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi",
				"UPP4008busi-000248")/* @res "正在打印，请稍候..." */);
		SCMEnv.out("打印批次汇总开始!\n"/*-=notranslate=-*/);
		try {
			// 调出打印窗口
			// 依当前是列表还是表单而定打印内容
			if (getM_iMode() == BillMode.Browse
					&& getM_iCurPanel() == BillMode.Card) { // 浏览
				SCMEnv.out(NCLangRes.getInstance().getStrByID("bill", "bill-000007")/*打印批次汇总开始!表单打印!\n*/);
				// 准备数据
				GeneralBillVO vo = null;

				if (getM_iLastSelListHeadRow() != -1
						&& null != getM_alListData()
						&& getM_alListData().size() != 0) {
					vo = (GeneralBillVO) getM_alListData().get(
							getM_iLastSelListHeadRow());
					if (getBillCardPanel().getHeadItem("vcustname") != null)
						vo.setHeaderValue("vcustname", getBillCardPanel()
								.getHeadItem("vcustname").getValueObject());
				}

				if (null == vo) {
					vo = new GeneralBillVO();
				}
				if (null == vo.getParentVO()) {
					vo.setParentVO(new GeneralBillHeaderVO());
				}
				if ((null == vo.getChildrenVO())
						|| (vo.getChildrenVO().length == 0)
						|| (vo.getChildrenVO()[0] == null)) {
					GeneralBillItemVO[] ivo = new GeneralBillItemVO[1];
					ivo[0] = new GeneralBillItemVO();
					vo.setChildrenVO(ivo);
				}

				if (getClientUI().getPrintEntry().selectTemplate() < 0)
					return;
				GeneralBillVO gvobak = (GeneralBillVO) vo.clone();

				// 得到汇总条件,如果不选择汇总辅助计量单位，则不用汇总负数量,默认选择汇总辅数量
				MergeRowDialog dlgMerge = new MergeRowDialog(getClientUI());
				if (dlgMerge.showModal() == UIDialog.ID_CANCEL)
					return;
				ArrayList alGroupBy = dlgMerge.getGroupingAttr();
				if (alGroupBy == null || alGroupBy.size() <= 0
						|| alGroupBy.size() != 6)
					return;

				// 得到分组字段
				String[] Fields = new String[] { "cinventoryid", "vbatchcode",
						"castunitid", "vfree0", "cspaceid" };
				ArrayList alChooseGroup = new ArrayList();
				for (int i = 0; i < alGroupBy.size() - 1; i++) {
					if (((Boolean) alGroupBy.get(i)).booleanValue() == true)
						alChooseGroup.add(Fields[i]);
				}
				String[] saGroupField = null;
				if (alChooseGroup.size() > 0)
					saGroupField = new String[alChooseGroup.size()];
				alChooseGroup.toArray(saGroupField);

				nc.vo.scm.merge.DefaultVOMerger dvomerger = new nc.vo.scm.merge.DefaultVOMerger();
				dvomerger.setGroupingAttr(saGroupField);

				// 得到Summing字段
				String[] saSummingField = null;
				if (getInOutFlag() == InOutFlag.IN) {
					if (((Boolean) alGroupBy.get(2)).booleanValue() == true)
						saSummingField = new String[] { "nshouldinnum",
								"nneedinassistnum", "ninnum", "ninassistnum",
								"nmny" };
					else
						saSummingField = new String[] { "nshouldinnum",
								"ninnum", "nmny" };
				} else if (getInOutFlag() == InOutFlag.OUT) {
					if (((Boolean) alGroupBy.get(2)).booleanValue() == true)
						saSummingField = new String[] { "nshouldoutnum",
								"nshouldoutassistnum", "noutnum",
								"noutassistnum", "nmny" };
					else
						saSummingField = new String[] { "nshouldoutnum",
								"noutnum", "nmny" };
				}
				dvomerger.setSummingAttr(saSummingField);

				nc.vo.ic.pub.bill.GeneralBillItemVO[] itemvosnew = (nc.vo.ic.pub.bill.GeneralBillItemVO[]) dvomerger
						.mergeByGroup(gvobak.getItemVOs());

				if (itemvosnew != null) {
					UFDouble udNum = null;
					UFDouble udMny = null;
					for (int k = 0; k < itemvosnew.length; k++) {
						udNum = itemvosnew[k].getNoutnum();
						udMny = itemvosnew[k].getNmny();
						if (udNum != null && udMny != null) {
							itemvosnew[k].setNprice(udMny.div(udNum));
						}
					}

				}

				gvobak.setChildrenVO(itemvosnew);

				// 汇总预览
				printOnCard(gvobak, true);

			} else if (getM_iCurPanel() == BillMode.List) {
				// 列表

				SCMEnv.out("列表打印开始!\n"/*-=notranslate=-*/);
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					return;
				}
				if (getClientUI().getPrintEntry().selectTemplate() < 0)
					return;
				ArrayList alBill = getClientUI().getSelectedBills();
				// 置小数精度
				getClientUI().setScaleOfListData(alBill);
				SCMEnv.out("列表打印:得到选中的单据并设置数量精度!\n"/*-=notranslate=-*/);
				if (alBill == null)
					return;
				nc.vo.scm.merge.DefaultVOMerger dvomerger = null;
				for (int i = 0; i < alBill.size(); i++) {
					SCMEnv.out("列表打印:开始合并表体行!\n"/*-=notranslate=-*/);
					GeneralBillVO gvobak = (GeneralBillVO) alBill.get(i);
					// /
					dvomerger = new nc.vo.scm.merge.DefaultVOMerger();
					dvomerger.setGroupingAttr(new String[] { "cinventoryid",
							"castunitid" });
					dvomerger.setSummingAttr(new String[] { "nshouldoutnum",
							"nshouldoutassistnum", "noutnum", "noutassistnum",
							"nmny" });
					nc.vo.ic.pub.bill.GeneralBillItemVO[] itemvosnew = (nc.vo.ic.pub.bill.GeneralBillItemVO[]) dvomerger
							.mergeByGroup(gvobak.getItemVOs());
					SCMEnv.out("列表打印:得到合并后的表体行!\n"/*-=notranslate=-*/);
					if (itemvosnew != null) {
						UFDouble udNum = null;
						UFDouble udMny = null;
						for (int k = 0; k < itemvosnew.length; k++) {
							udNum = itemvosnew[k].getNoutnum();
							udMny = itemvosnew[k].getNmny();
							if (udNum != null && udMny != null) {
								itemvosnew[k].setNprice(udMny.div(udNum));
							}

						}
						gvobak.setChildrenVO(itemvosnew);
						alBill.set(i, gvobak);
					}

				}
				//
				SCMEnv.out("列表打印:得到合并后的单据!\n"/*-=notranslate=-*/);

				// 列表下打印，而非预览。
				printOnList(alBill);

			} else
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008busi", "UPP4008busi-000249")/* @res "只能在浏览状态下打印" */);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008busi", "UPPSCMCommon-000061")/* @res "打印出错" */
					+ e.getMessage());
		}
	}

	/*
	 * 列表下打印 @author 邵兵 on Jun 15, 2005
	 */
	private void printOnList(ArrayList alBill) throws InterruptedException {
		nc.ui.pub.print.PrintEntry pe = getClientUI().getPrintEntryNew();

		if (pe.selectTemplate() < 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000048")/*
														 * @res "请先定义打印模板。"
														 */);
			return;
		}

		pe.beginBatchPrint();

		PrintDataInterface ds = null;
		GeneralBillVO voBill = null;
		// 单据主表
		GeneralBillHeaderVO headerVO = null;
		// nc.vo.scm.print.PrintResultVO printResultVO = null;

		nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();
		plc.setBatchPrint(true);// 设置是批打
		// 设置打印监听
		pe.setPrintListener(plc);
		plc.setPrintEntry(pe);
		plc.addFreshTsListener(new FreshTsListener());

		// 打印操作
		for (int i = 0; i < alBill.size(); i++) {
			voBill = (GeneralBillVO) alBill.get(i);
			headerVO = voBill.getHeaderVO();
			
			// 修改原因：在单据是新增状态时，不要去刷新批次信息，因为特殊单可能会在新增保存之前刷新单据界面
			if (nc.vo.pub.VOStatus.NEW != voBill.getStatus())
				BatchCodeDefSetTool.execFormulaForBatchCode(voBill.getItemVOs());

			ScmPrintlogVO voSpl = new ScmPrintlogVO();
			voSpl = new ScmPrintlogVO();
			voSpl.setCbillid(headerVO.getPrimaryKey()); // 单据主表的ID
			voSpl.setVbillcode(headerVO.getVbillcode());// 传入单据号，用于显示。
			voSpl.setCbilltypecode(headerVO.getCbilltypecode());
			voSpl.setCoperatorid(headerVO.getCoperatorid());
			voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));// 固定
			voSpl.setPk_corp(getEnvironment().getCorpID());
			voSpl.setTs(headerVO.getTs());// 单据主表的TS

			SCMEnv.out("ts=========tata" + voSpl.getTs());
			// 设置单据信息
			plc.setPrintInfo(voSpl);

			if (plc.check()) {
				// 检查通过才执行打印，有错误的话自动插入打印日志，这里不用处理。
				ds = getClientUI().getDataSourceNew();
				GeneralBillVO[] destvoBills = (GeneralBillVO[]) getClientUI().getPluginProxy().beforePrint(new GeneralBillVO[]{(GeneralBillVO)voBill.clone()});
				if (null != destvoBills &&  0 < destvoBills.length && destvoBills[0] instanceof GeneralBillVO)
					ds.setVO(destvoBills[0]);
				else
					ds.setVO(voBill);
				pe.setDataSource(ds);
			}

		}
		pe.endBatchPrint();

		//MessageDialog.showHintDlg(getClientUI(), null, plc.getPrintResultMsg(false));
	}

	/*
	 * 卡片打印/预览
	 * 
	 * @param isPreview boolean true-打印预览；false-直接打印 * @author 邵兵 on Jun 15,
	 * 2005
	 */
	protected void printOnCard(GeneralBillVO voBill, boolean isPreview) {

		// 单据主表
		GeneralBillHeaderVO headerVO = voBill.getHeaderVO();
		String sBillID = headerVO.getPrimaryKey();

		// 构造PrintLogClient及设置PrintInfo.
		ScmPrintlogVO voSpl = new ScmPrintlogVO();
		voSpl.setCbillid(sBillID); // 单据主表的ID
		voSpl.setVbillcode(headerVO.getVbillcode());// 传入单据号，用于显示。
		voSpl.setCbilltypecode(headerVO.getCbilltypecode());
		voSpl.setCoperatorid(headerVO.getCoperatorid());
		voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));// 固定
		voSpl.setPk_corp(getEnvironment().getCorpID());
		voSpl.setTs(headerVO.getTs());// 单据主表的TS

		SCMEnv.out("ts=========tata" + voSpl.getTs());
		nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();
		// 设置单据信息
		plc.setPrintInfo(voSpl);
		// 设置TS刷新监听.
		plc.addFreshTsListener(new FreshTsListener());
		// 设置打印监听
		getClientUI().getPrintEntry().setPrintListener(plc);

		plc.setPrintEntry(getClientUI().getPrintEntry());// 用于单打时
		// 设置单据信息
		plc.setPrintInfo(voSpl);
		
		GeneralBillVO[] destvoBills =  (GeneralBillVO[])getClientUI().getPluginProxy().beforePrint(new GeneralBillVO[]{(GeneralBillVO)voBill.clone()});
		if (null != destvoBills &&  0 < destvoBills.length && destvoBills[0] instanceof GeneralBillVO)
			getClientUI().getDataSource().setVO(destvoBills[0]);
		else
		// 向打印置入数据源，进行打印
			getClientUI().getDataSource().setVO(voBill);
		getClientUI().getPrintEntry().setDataSource(getClientUI().getDataSource());

		// 打印提示信息
		String sPrintMsg = null;
		// 执行打印
		if (isPreview) {
			getClientUI().getPrintEntry().preview();
			sPrintMsg = plc.getPrintResultMsg(true);
		} else {
			getClientUI().getPrintEntry().print();
			sPrintMsg = plc.getPrintResultMsg(false);
		}
	}

	/**
	 * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-10-23 9:07:19) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void onPrintLocSN(GeneralBillVO voBill) {
		// 打印数据来自m_voBill
		if (voBill == null || voBill.getParentVO() == null
				|| voBill.getChildrenVO() == null) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000053")/* @res "没有数据！" */);
			return;
		}
		// 准备打印标题
		String title = nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000319")/* @res " 货位序列号分配明细表" */;
		GeneralBillHeaderVO voHead = (GeneralBillHeaderVO) voBill.getParentVO();
		// 准备表头字串
		StringBuffer headstr = new StringBuffer(nc.ui.ml.NCLangRes
				.getInstance().getStrByID("4008bill", "UPP4008bill-000320")/*
																			 * @res
																			 * "单据号："
																			 */);
		if (voHead.getVbillcode() != null)
			headstr.append(voHead.getVbillcode());
		else
			headstr.append("      ");

		headstr.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000321")/* @res "仓库：" */);
		if (voHead.getCwarehousename() != null)
			headstr.append(voHead.getCwarehousename());
		else
			headstr.append("       ");
		headstr.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000322")/* @res "日期：" */);
		if (voHead.getDbilldate() != null)
			headstr.append(voHead.getDbilldate().toString());
		else
			headstr.append("       ");

		// 准备表体列名数组
		String[][] colname = new String[1][12];
		int[] colwidth = new int[12];
		int[] alignflag = new int[12];
		String[] names = new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0001480")/* @res "存货编码" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0001453")/* @res "存货名称" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0000745")/* @res "单位" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003327")/* @res "自由项" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000182")/* @res "批次" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003971")/* @res "辅数量" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003938")/* @res "辅单位" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0002161")/* @res "换算率" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0002282")/* @res "数量" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003830")/* @res "货位" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0001819") /* @res "序列号" */,
				// modified by liuzy 2007-12-27 增加毛重主数量
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000475") /* @res "毛重主数量" */
		};
		for (int i = 0; i < 12; i++) {
			colname[0][i] = names[i];
			colwidth[i] = 60;
			// String
			alignflag[i] = 0;
			// decimal
			if (i == 5 || i == 8 || i == 7)
				alignflag[i] = 2;

		}

		// 准备表体数据数组

		Vector v = new Vector(); // 缓存要打印的数据
		Object[] data = null;
		// 设置数据精度by ZSS
		ArrayList alVO = new ArrayList();
		alVO.add(voBill);
		getClientUI().setScaleOfListData(alVO);
		voBill = (GeneralBillVO) alVO.get(0);
		//
		GeneralBillItemVO[] voItems = (GeneralBillItemVO[]) voBill
				.getChildrenVO();
		for (int i = 0; i < voItems.length; i++) {
			// 如果表体行的货位不为空。取得货位分配数组，每增加一个货位即增加一行，数量取自货位VO，序列号与货位pk相关

			if (voItems[i].getLocator() != null) {
				LocatorVO[] locs = voItems[i].getLocator();

				ScaleKey sk = new ScaleKey();
				sk.setNumKeys(new String[] { "ninspacenum", "noutspacenum" });
				sk.setAssistNumKeys(new String[] { "ninspaceassistnum",
						"noutspaceassistnum" });

				GenMethod.setScale(locs, sk, getClientUI().m_ScaleValue);

				for (int j = 0; j < locs.length; j++) {
					data = new Object[12];
					// 存货编码
					data[0] = voItems[i].getCinventorycode();
					data[1] = voItems[i].getInvname();
					data[2] = voItems[i].getMeasdocname();
					data[3] = voItems[i].getVfree0();
					data[4] = voItems[i].getVbatchcode();
					data[6] = voItems[i].getCastunitname();
					data[7] = voItems[i].getHsl();
					// 数量ninspacenum or noutspacenum
					if (locs[j].getNinspacenum() != null) {
						data[8] = locs[j].getNinspacenum();
						data[5] = locs[j].getNinspaceassistnum();
					} else {
						data[8] = locs[j].getNoutspacenum();
						data[5] = locs[j].getNoutspaceassistnum();
					}

					// 货位csname
					data[9] = locs[j].getVspacename();
					// 如果序列号不为空
					data[10] = getClientUI().getSNString(voItems[i].getSerial(), locs[j]
							.getCspaceid());
					// modified by liuzy 2007-12-27 增加毛重主数量
					data[11] = voItems[i].getAttributeValue(getEnvironment()
							.getGrossNumItemKey());
					// modify by liuzy 2007-11-21 货位打印问题
					v.add(data);
				}
			}
			// 如果货位为空，
			else {
				data = new Object[12];
				// 存货编码
				data[0] = voItems[i].getCinventorycode();
				data[1] = voItems[i].getInvname();
				data[2] = voItems[i].getMeasdocname();
				data[3] = voItems[i].getVfree0();
				data[4] = voItems[i].getVbatchcode();
				data[6] = voItems[i].getCastunitname();
				data[7] = voItems[i].getHsl();
				// 数量取自表体行
				if (voItems[i].getNinnum() != null) {
					data[8] = voItems[i].getNinnum();
					data[5] = voItems[i].getNinassistnum();
				} else {
					data[8] = voItems[i].getNoutnum();
					data[5] = voItems[i].getNoutassistnum();
				}

				// 如果序列号不为空,序列号信息与表体行相关
				data[10] = getClientUI().getSNString(voItems[i].getSerial(), null);
				// modified by liuzy 2007-12-27 增加毛重主数量
				data[11] = voItems[i].getAttributeValue(getEnvironment()
						.getGrossNumItemKey());
				v.add(data);
			}

		}
		if (v.size() > 0) {
			// 表体数据（去掉隐藏列）
			Object[][] data1 = new Object[v.size()][12];

			for (int i = 0; i < v.size(); i++) {

				data1[i] = (Object[]) v.get(i);

			}

			java.awt.Font font = new java.awt.Font("dialog",
					java.awt.Font.BOLD, 18);
			java.awt.Font font1 = new java.awt.Font("dialog",
					java.awt.Font.PLAIN, 12);
			String topstr = nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000323")/* @res "公司：" */
					+ nc.ui.pub.ClientEnvironment.getInstance()
							.getCorporation().getUnitname() + headstr;

			String botstr = nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000325")/* @res "制表人：" */
					+ nc.ui.pub.ClientEnvironment.getInstance().getUser()
							.getUserName()
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000324")/* @res " 制表日期：" */
					+ nc.ui.pub.ClientEnvironment.getInstance().getDate();
			//
			//nc.ui.pub.print.PrintDirectEntry print = new nc.ui.pub.print.PrintDirectEntry();
			nc.ui.pub.print.PrintDirectEntry print = new nc.ui.pub.print.PrintDirectEntry(getClientUI());

			print.setTitle(title);
			// 标题 可选
			print.setTitleFont(font);
			// 标题字体 可选
			print.setContentFont(font1);
			// 内容字体（表头、表格、表尾） 可选
			print.setTopStr(topstr);
			// 表头信息 可选
			// 页号
			print.setBottomStr(botstr);
			print.setPageNumDisp(true);
			print.setPageNumFont(font1);
			// 左右0 1 2
			print.setPageNumAlign(2);
			// 上下0 1 2
			print.setPageNumPos(2);
			print.setPageNumTotalDisp(true);
			// 固定表头
			print.setFixedRows(1);
			// 表尾信息 可选
			print.setColNames(colname);
			// 表格列名（二维数组形式）
			print.setData(data1);
			// 表格数据
			print.setColWidth(colwidth);
			// 表格列宽 可选
			print.setAlignFlag(alignflag);
			// 表格每列的对齐方式（0-左, 1-中, 2-右）可选

			print.preview();
			// 预览

		}
		// 提交打印
	}

	/**
	 * 此处插入方法说明。 功能描述:在卡片模式下转向第一张。 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27
	 * 14:47:24)
	 */
	protected void onFirst() {
		if (getM_alListData() != null && getM_alListData().size() > 0) {
			int iAll = getM_alListData().size();
			getClientUI().scrollBill(0);
			getClientUI().m_pageBtn.first(iAll);
			getClientUI().updateButtons();
		}
	}

	/**
	 * 此处插入方法说明。 功能描述:在卡片模式下指向前一张 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27
	 * 14:48:02)
	 */
	protected void onPrevious() {
		if (getM_alListData() != null && getM_alListData().size() > 0) {
			int iAll = getM_alListData().size();
			int iCur = getM_iLastSelListHeadRow() - 1;
			getClientUI().scrollBill(iCur);
			getClientUI().m_pageBtn.previous(iAll, iCur);
			getClientUI().updateButtons();
		}
	}

	/**
	 * 此处插入方法说明。 功能描述:在卡片模式下指向下一张 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27
	 * 14:48:31)
	 */
	protected void onNext() {
		if (getM_alListData() != null && getM_alListData().size() > 0) {
			int iAll = getM_alListData().size();
			int iCur = getM_iLastSelListHeadRow() + 1;
			getClientUI().scrollBill(iCur);
			getClientUI().m_pageBtn.next(iAll, iCur);
			getClientUI().updateButtons();
		}
	}

	/**
	 * 此处插入方法说明。 功能描述:在卡片模式下指向最后一张 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27
	 * 14:48:54)
	 */
	protected void onLast() {
		if (getM_alListData() != null && getM_alListData().size() > 0) {
			int iAll = getM_alListData().size();
			int iCur = iAll - 1;
			getClientUI().scrollBill(iCur);
			getClientUI().m_pageBtn.last(iAll);
			getClientUI().updateButtons();
		}
	}

	/**
	 * 
	 * 功能： 核对数据事件响应 参数： 返回： 例外： 日期：(2002-04-18 10:43:46) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void onCheckData() {
		try {
			getClientUI().getDevInputCtrl().setBillVO(getM_voBill());
			java.util.ArrayList alResult = getClientUI().getDevInputCtrl().onOpenFile(
					getClientUI().getDevInputCtrl().ACT_CHECK_ITEM);

			alResult.get(1);
		} catch (Exception e) {
			String sErrorMsg = null;
			sErrorMsg = e.getMessage();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000044")/* @res "错误提示：" */
					+ sErrorMsg);
		}
	}

	/**
	 * 
	 * 功能： 导入数据事件响应 参数： 返回： 例外： 日期：(2002-04-18 10:43:46) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void onImportData() {
		try {
			// 过滤空行
			getClientUI().filterNullLine();
			// 处理行号
			int iRowCount = getBillCardPanel().getRowCount();
			if (iRowCount > 0 && getM_voBill().getChildrenVO() != null) {
				if (getBillCardPanel().getBodyItem(IItemKey.CROWNO) != null)
					for (int i = 0; i < iRowCount; i++) {
						getM_voBill().setItemValue(
								i,
								IItemKey.CROWNO,
								getBillCardPanel().getBodyValueAt(i,
										IItemKey.CROWNO));
					}
			}

			nc.ui.ic.pub.device.DevInputCtrl devInputCtrl = getClientUI().getDevInputCtrl();
			GeneralBillVO vonew = (GeneralBillVO) (getBillCardPanel()
					.getBillValueVO(GeneralBillVO.class.getName(),
							GeneralBillHeaderVO.class.getName(),
							GeneralBillItemVO.class.getName()));
			devInputCtrl.setBillVOUI(vonew);

			devInputCtrl.setBillVO(getM_voBill());
			devInputCtrl.setWarehouseidFieldName("cwarehouseid");
			devInputCtrl.setWarehouseNameFieldName("cwarehousename");

			java.util.ArrayList alResult = devInputCtrl
					.onOpenFile(DevInputCtrl.ACT_ADD_ITEM);
			if (alResult == null || alResult.size() == 0) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000046")/* @res "没有导入成功！" */);
				return;
			}

			String sAppendType = (String) alResult.get(0);

			nc.vo.pub.CircularlyAccessibleValueObject[] voaDi = (nc.vo.pub.CircularlyAccessibleValueObject[]) alResult
					.get(1);

			int iAppendType = Integer.parseInt(sAppendType);
			getClientUI().setIsImportData(false);
			if (iAppendType == DevInputCtrl.ACT_ADD_ITEM) {
				if (voaDi != null && voaDi.length > 0) {
					getClientUI().setIsImportData(true);
					// 用于是否在保存时校验导入数据的正确性
					String sWarehouseid = null;
					nc.ui.pub.beans.UIRefPane refpane = (UIRefPane) getBillCardPanel()
							.getHeadItem("cwarehouseid").getComponent();

					if (refpane != null)
						sWarehouseid = refpane.getRefPK();
					// 同步vo.
					getClientUI().synVO(voaDi, sWarehouseid, getClientUI().getM_alLocatorData());
					// 必须将货位信息同步到m_alLocatorData,否则单据保存时，getBillVOs方法会清掉m_voBill的货位信息
					GeneralBillItemVO[] voaItemBill = (GeneralBillItemVO[]) getM_voBill()
							.getChildrenVO();
					int len = voaItemBill.length;
					for (int i = 0; i < len; i++) {
						LocatorVO[] voLoc = voaItemBill[i].getLocator();
						getClientUI().getM_alLocatorData().add(i, voLoc);
					}

				} else
					getClientUI().setIsImportData(false);
			} else {
				// 不用校验存货信息，不更新这些信息，只更新数量
				getClientUI().setIsImportData(false);
			}

		} catch (Exception e) {
			String sErrorMsg = null;
			sErrorMsg = e.getMessage();
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000044")/* @res "错误提示：" */
					+ sErrorMsg);
			getClientUI().setIsImportData(false);
		}
	}

	/**
	 * 用以导入Excel主或次条码文件(整合了所有菜单） 作者:李俊 iMenu =0 ,1，2 分别对应导入主、次条码和主次条码
	 * 创建日期：(2004-4-21 20:41:28)
	 */
	protected void onImptBCExcel(int iMenu) {
		// 是否覆盖旧的存货的条码
		boolean bCover = false;
		// 当前选中行
		int rownow;
		// 导入条码文件于此VO数组中
		BarCodeVO[] voaImport = null;
		// 导入条码数量
		int sizeVOImp = 0;
		// 新旧条码的信息
		ArrayList alOldVO = new ArrayList();
		ArrayList alNewVO = new ArrayList();
		// 整合返回的BarcodeVO数组
		BarCodeVO[] bcVOTotal = null;

		rownow = checkSelectionRow();
		if (rownow == -1) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH004")/* @res "请选择要处理的数据行！" */);
			return;
		}
		// 得到旧的存货条码信息
		GeneralBillItemVO billItemvo = (GeneralBillItemVO) getM_voBill()
				.getItemVOs()[rownow];
		alOldVO = BarcodeparseCtrlUI.getVOInfoOld(billItemvo);
		// 导入文件至VO
		try {
			nc.ui.scm.pub.excel.ExcelBarcodeDialog m_eDlg = new nc.ui.scm.pub.excel.ExcelBarcodeDialog(
					getClientUI());
			m_eDlg.setVOName("nc.vo.ic.pub.bc.BarCodeVO");
			m_eDlg.setCHandENnames(BarcodeparseCtrl.getVOStringType(iMenu));
			m_eDlg.showModal();
			bCover = m_eDlg.getRadioSelect();
			voaImport = null;
			if (m_eDlg.isExportOK()) {
				voaImport = (BarCodeVO[]) m_eDlg.getExportVO();
				if (voaImport == null || voaImport.length == 0
						|| voaImport[0] == null) {
					MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
							.getInstance().getStrByID("SCMCOMMON",
									"UPPSCMCommon-000059")/*
															 * @res "错误"
															 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000088")/*
													 * @res "Excel文件条码为空！"
													 */);
					return;
				}
			} else
				return;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000089")/* @res "打开Excel条码文件出错！" */
					+ e.getMessage());
		}
		/** 对条码长度进行条码规则校验 */
		String sMsg = BarcodeparseCtrlUI.verifyLenInfo(getEnvironment()
				.getCorpID(), voaImport);
		if (sMsg != null) {
			showErrorMessage(sMsg);
			return;
		}

		// 条码规则判断,只对第一个条码校验
		if (voaImport != null && voaImport.length != 0 && voaImport[0] != null) {
			alNewVO = BarcodeparseCtrlUI.getVOInfoNew(voaImport[0],
					getEnvironment().getCorpID());
			if (alNewVO == null) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000059")/*
														 * @res "错误"
														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
						"UPP4008bill-000090")/*
												 * @res "导入条码不符合条码规则！"
												 */);
				return;
			}
			if (iMenu == 0 && alNewVO.get(1) != BarcodeparseCtrl.MAINBARCODE) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000059")/* @res "错误" */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPP4008bill-000327")/* @res "导入条码不是主条码！" */);
				return;
			}
			if (iMenu == 1 && alNewVO.get(1) != BarcodeparseCtrl.SUBBARCODE) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000059")/* @res "错误" */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPP4008bill-000328")/* @res "导入条码不是次条码！" */);
				return;
			}
			if (iMenu == 2 && alNewVO.get(1) != BarcodeparseCtrl.BOTHCODEHAVE) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000059")/* @res "错误" */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPP4008bill-000329")/* @res "导入条码不是主次条码！" */);
				return;
			}
		}
		// 置入数量和单件属性
		if (voaImport != null)
			sizeVOImp = voaImport.length;
		for (int i = 0; i < sizeVOImp; i++) {
			voaImport[i].setNnumber(new UFDouble("1.00"));
			voaImport[i].setPk_corp(getEnvironment().getCorpID());
			if (voaImport[i].getVbarcode() != null)
				voaImport[i].setBsingletype((UFBoolean) alNewVO.get(0));
			else
				voaImport[i].setBsingletypesub((UFBoolean) alNewVO.get(0));

		}

		// 整合VO[]
		BarCodeVO[] voaOld = billItemvo.getBarCodeVOs();
		BarCodeVO[] voaOldCopy = null;
		if (voaOld != null) {
			voaOldCopy = new BarCodeVO[voaOld.length];
			for (int i = 0; i < voaOld.length; i++) {
				voaOldCopy[i] = (nc.vo.ic.pub.bc.BarCodeVO) voaOld[i].clone();
			}
		}
		// 修改人：刘家清 修改日期：2007-12-13下午03:23:00 修改原因：
		nc.vo.ic.pub.GenMethod.fillBarCodeVOsByBarcode(voaImport);
		bcVOTotal = BarcodeparseCtrlUI.barCodeAddWrapper(iMenu, bCover,
				alOldVO, voaOld, voaImport);
		// *浏览状状态和签字状态下，条码数量应小于实际数量
		if ((getM_voBill().getHeaderVO().getPrimaryKey() != null)
				&& (getM_iMode() == BillMode.Browse)) {
			UFDouble num = new UFDouble(0);
			if ((null != voaImport[0].getBasstaddflag() && voaImport[0].getBasstaddflag().booleanValue()) 
					|| (null != voaImport[0].getBasstaddflagsub() && voaImport[0].getBasstaddflagsub().booleanValue())){
				num = (UFDouble) (billItemvo.getAttributeValue(getEnvironment().getAssistNumItemKey()));
				
			}else{
				num = (UFDouble) (billItemvo.getAttributeValue(getEnvironment().getNumItemKey()));

			}

			double allNum = 0.00;
			int len = bcVOTotal.length;
			for (int i = 0; i < len; i++) {
				if (bcVOTotal[i].getStatus() == nc.vo.pub.VOStatus.DELETED)
					continue;
				allNum += bcVOTotal[i].getNnumber().doubleValue();
			}
			if (allNum > Math.abs(num.doubleValue())) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000059")/*
														 * @res "错误"
														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
						"UPP4008bill-000091")/*
												 * @res "条码数量不应大于实际收发数量！"
												 */);
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000092")/*
															 * @res
															 * "条码数量不应大于实际收发数量"
															 */);
				getM_voBill().getItemVOs()[rownow].setBarCodeVOs(voaOldCopy);
				return;
			}
		}else{
			//修改人：刘家清 修改时间：2008-12-27 上午11:38:36 修改原因：编辑状态下应该增加数量。
			double allNum = 0.00;
			int len = bcVOTotal.length;
			for (int i = 0; i < len; i++) {
				if (bcVOTotal[i].getStatus() == nc.vo.pub.VOStatus.DELETED)
					continue;
				allNum += bcVOTotal[i].getNnumber().doubleValue();
			}
			if ((null != voaImport[0].getBasstaddflag() && voaImport[0].getBasstaddflag().booleanValue()) 
					|| (null != voaImport[0].getBasstaddflagsub() && voaImport[0].getBasstaddflagsub().booleanValue())){
				//if (null == getM_voBill().getItemVOs()[rownow].getAttributeValue(getEnvironment().getAssistNumItemKey()))
					getM_voBill().getItemVOs()[rownow].setAttributeValue(getEnvironment().getAssistNumItemKey(),new UFDouble(allNum));
/*				else
					getM_voBill().getItemVOs()[rownow].setAttributeValue(getEnvironment().getAssistNumItemKey(),(((UFDouble) getM_voBill().getItemVOs()[rownow].getAttributeValue(getEnvironment().getAssistNumItemKey())).add(new UFDouble(allNum))));
*/				getBillCardPanel().setBodyValueAt(new UFDouble(allNum), rownow, getEnvironment().getAssistNumItemKey());
				BillEditEvent ev = new BillEditEvent(
						getBillCardPanel().getBodyItem(
								getEnvironment().getAssistNumItemKey()), null,
								new UFDouble(allNum), getEnvironment().getAssistNumItemKey(),
						rownow, BillItem.BODY);
				getClientUI().afterEdit(ev);
			}else{
				//if (null == getM_voBill().getItemVOs()[rownow].getAttributeValue(getEnvironment().getNumItemKey()))
					getM_voBill().getItemVOs()[rownow].setAttributeValue(getEnvironment().getNumItemKey(),new UFDouble(allNum));
/*				else
					getM_voBill().getItemVOs()[rownow].setAttributeValue(getEnvironment().getNumItemKey(),(((UFDouble) getM_voBill().getItemVOs()[rownow].getAttributeValue(getEnvironment().getNumItemKey())).add(new UFDouble(allNum))));
			*/	getBillCardPanel().setBodyValueAt(new UFDouble(allNum), rownow, getEnvironment().getNumItemKey());
				BillEditEvent ev = new BillEditEvent(
						getBillCardPanel().getBodyItem(
								getEnvironment().getNumItemKey()), null,
								new UFDouble(allNum), getEnvironment().getNumItemKey(),
						rownow, BillItem.BODY);
				getClientUI().afterEdit(ev);
			}
			
			
		}
		// 5.将VO放在存货界面VO上
		getM_voBill().getItemVOs()[rownow].setBarCodeVOs(bcVOTotal);
		if (bcVOTotal == null || bcVOTotal.equals(voaOld)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000093")/* @res "没有导入成功，请重新导入!" */);
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000094")/* @res "已将条码导入到单据,请保存!" */);
		// 6
		try {
			if ((getM_voBill().getHeaderVO().getPrimaryKey() != null)
					&& (getM_iMode() == BillMode.Browse)) {
				if (getClientUI().onImportSignedBillBarcode(getM_voBill(), true) == 1)
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000095")/*
																			 * @res
																			 * "导入并保存条码成功(请注意库存单据是否允许保存条码）!"
																			 */);
				else {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000096")/*
																			 * @res "
																			 * 没有保存成功！"
																			 */);
					getM_voBill().getItemVOs()[rownow]
							.setBarCodeVOs(voaOldCopy);
				}
			}
		} catch (Exception e) {
			if (e instanceof nc.vo.ic.ic009.PackCheckBusException) {

				handleException(e);
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000105")/* @res "保存出错。" */);
				String se = e.getMessage();
				if (se != null) {
					int index = se.indexOf("$$ZZZ$$");
					if (index >= 0)
						se = se.substring(index + 7);
				}
				// packCheckBusDialog = null;
				getClientUI().getpackCheckBusDialog().setText(se);

				getClientUI().getpackCheckBusDialog().showModal();

			} else {
				nc.vo.scm.pub.SCMEnv.error(e);
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000097")/* @res "导入失败：" */
						+ e.getMessage());
			}
			getM_voBill().getItemVOs()[rownow].setBarCodeVOs(voaOldCopy);
		}
	}

	/**
	 * 作者：李俊 判断选中行(导入Excel条码）是否有数据，是否选中。 创建日期：(2004-5-4 13:12:20)
	 */
	private int checkSelectionRow() {
		if (getM_iCurPanel() == BillMode.Card) {
			if (getM_iLastSelListHeadRow() != -1 && null != getM_alListData()
					&& getM_alListData().size() != 0) {
				if (getM_voBill() == null)
					setM_voBill((GeneralBillVO) getM_alListData().get(
							getM_iLastSelListHeadRow()));
			}
		} else if (getM_iCurPanel() == BillMode.List) { // 列表
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000065")/* @res "请切换到卡片界面导入数据!" */);
			return -1;
		}
		int rownow = getBillCardPanel().getBillTable().getSelectedRow();
		if (rownow < 0) {
			return -1;
		}
		String invID = (String) getBillCardPanel().getBodyValueAt(rownow,
				"cinventoryid");
		if (invID == null || invID.trim().equals("")) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000066")/* @res "没有选中存货!" */);
			return -1;
		}
		if (getM_voBill() == null || getM_voBill().getChildrenVO() == null
				|| getM_voBill().getChildrenVO().length < rownow) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH003")/* @res "请选择要处理的数据！" */);
			return -1;
		}
		return rownow;
	}

	/**
	 * 此处插入方法说明。 导入来源单据：只在新增加、浏览、已经审核情况下可以直接保存导入 创建日期：(2004-4-20 11:36:04)
	 */
	protected void onImportBarcodeSourceBill() {
		try {

			if (getM_iMode() != BillMode.Browse) {
				// 编辑情况下：
				// 滤去表单形式下的空行
				getClientUI().filterNullLine();
				if (getBillCardPanel().getRowCount() <= 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000072")/*
																			 * @res
																			 * "请输入表体行!"
																			 */);
					return;
				}
				// 新增情况是以行号为索引的，必须校验
				if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
						getBillCardPanel(), IItemKey.CROWNO)) {
					return;
				}
			}

			// 执行后台的导入操作，需要判断是在列表下或卡片下
			ArrayList alBill = new ArrayList();
			if (getM_iCurPanel() == BillMode.Card) { // 浏览
				GeneralBillVO vo = null;
				if (getM_iMode() == BillMode.Browse) {
					if (getM_iLastSelListHeadRow() != -1
							&& null != getM_alListData()
							&& getM_alListData().size() != 0) {
						vo = (GeneralBillVO) getM_alListData().get(
								getM_iLastSelListHeadRow());
					} else {
						showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000077")/*
																				 * @res
																				 * "卡片下没有单据数据不可以导入！"
																				 */);
						return;
					}
				} else {
					vo = getM_voBill();
				}
				alBill.add(vo);
			} else if (getM_iCurPanel() == BillMode.List) { // 列表
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000078")/* @res "列表下不可以导入！" */);
				return;
			}
			String sHID = null;
			String sBillTypecode = null;
			if (alBill != null && alBill.size() > 0) {
				GeneralBillVO billVO = null;
				StringBuffer sbErr = new StringBuffer("");
				ArrayList alSourceHID = new ArrayList();
				for (int n = 0; n < alBill.size(); n++) {
					// 校验数据
					billVO = (GeneralBillVO) alBill.get(n);

					if (billVO == null)
						continue;

					nc.vo.ic.pub.bill.GeneralBillHeaderVO headvo = billVO
							.getHeaderVO();
					GeneralBillItemVO[] billItemVOs = (GeneralBillItemVO[]) billVO
							.getChildrenVO();
					if (billVO == null || billVO.getHeaderVO() == null
							|| billVO.getChildrenVO() == null
							|| billVO.getChildrenVO().length == 0) {
						if (getM_iCurPanel() == BillMode.Card)
							sbErr.append(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000325")/*
																	 * @res
																	 * "当前单据表体没有数据不可以导入！"
																	 */);
						continue;
					}

					// 准备数据
					sHID = headvo.getCgeneralhid();
					
					sBillTypecode = headvo.getCbilltypecode();
					if (sBillTypecode != null
							&& (sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_otherIn)
									|| sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_otherOut)
									|| sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_allocationOut)
									|| sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_allocationIn) || sBillTypecode
									.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_saleOut))) { // 符合规则：其他出入库单，调拨出入库单，销售出库可以导入单据条码
						// 符合规则：其他出入库单，调拨出入库单，销售出库可以导入单据条码
					} else {
						sbErr.append(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000326")/*
																				 * @res
																				 * "当前单据表体，非销售出库、其他出入库单或调拨出入库单，不可以导入来源单据条码"
																				 */);
						showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000079")/*
																				 * @res
																				 * "没有来源单据，不可导入！"
																				 */);
						return;
					}

					java.util.ArrayList alBIDCrowno = new java.util.ArrayList();
					java.util.ArrayList alBIDSourceid = new java.util.ArrayList();
					java.util.Hashtable htbSourceBIDRepeat = new java.util.Hashtable();
					UFBoolean ufbHasHBID = new UFBoolean(true);
					if (sHID == null) {
						// 没有表体ID，直接新增情况，设置false
						ufbHasHBID = new UFBoolean(false);
					}
					String sSourceHID = null;
					// String sFirstHID = null;
					String sCourceTypecode = null; // 来源单据号
					boolean bTranBill = false; // 是否转库

					String sCsourcebillbid = null;
					java.util.ArrayList alRepeatRow = new java.util.ArrayList(); // 重复的
					for (int i = 0; i < billItemVOs.length; i++) {
						// if (i == 0) {
						sCourceTypecode = billItemVOs[i].getCsourcetype();
						if (sCourceTypecode == null
								|| sCourceTypecode.trim().length() == 0)
							continue;

						// 转库单用来源单据号
						if (sCourceTypecode.startsWith("4")) {
							sSourceHID = billItemVOs[i].getCsourcebillhid();
							bTranBill = true;
						} else // 调拨单据用源头单据好
						{
							sSourceHID = billItemVOs[i].getCfirstbillhid();
							bTranBill = false;
						}
						// }
						if (!alSourceHID.contains(sSourceHID))
							alSourceHID.add(sSourceHID);

						if (billItemVOs[i].getCsourcebillbid() != null) {
							if (bTranBill) {
								sCsourcebillbid = billItemVOs[i]
										.getCsourcebillbid();
							} else {
								sCsourcebillbid = billItemVOs[i]
										.getCfirstbillbid();
							}
							alBIDSourceid.add(sCsourcebillbid);
							// 校验重复的来源单据ID
							if (sCsourcebillbid != null) {
								if (htbSourceBIDRepeat
										.containsKey(sCsourcebillbid)) {
									alRepeatRow.add(billItemVOs[i].getCrowno());
								} else {
									htbSourceBIDRepeat.put(sCsourcebillbid,
											sCsourcebillbid);
								}
							}

							if (ufbHasHBID.booleanValue()) {
								alBIDCrowno
										.add(billItemVOs[i].getCgeneralbid());
							} else
								alBIDCrowno.add(billItemVOs[i].getCrowno());

						}
					}

					if (alBIDSourceid.size() == 0) {
						showHintMessage(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000079")/*
																				 * @res
																				 * "没有来源单据，不可导入！"
																				 */);
						return;
					}
					// 不允许有重复的来源单据行：
					if (alRepeatRow != null && alRepeatRow.size() > 0) {
						StringBuffer sbError = new StringBuffer();
						String sRowno = null;
						sbError.append(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000080")/*
																				 * @res
																				 * "存在下列重复的来源单据行，不能导入来源单据，请合并：\n"
																				 */);
						for (int i = 0; i < alRepeatRow.size(); i++) {
							sRowno = (String) alRepeatRow.get(i);
							if (i > 0)
								sbError.append(nc.ui.ml.NCLangRes.getInstance()
										.getStrByID("SCMCOMMON",
												"UPPSCMCommon-000000")/*
																		 * @res
																		 * "、"
																		 */);
							sbError.append(sRowno);
						}
						showErrorMessage(sbError.toString());
						return;
					}

					java.util.ArrayList alCon = new ArrayList();
					alCon.add(sHID);
					// 把行号或BID作为表体ID传到数据中
					alCon.add(alBIDCrowno);
					alCon.add(alBIDSourceid);
					alCon.add(getEnvironment().getCorpID());
					alCon.add(sBillTypecode);
					alCon.add(ufbHasHBID);
					// alCon.add(sSourceHID);
					alCon.add(alSourceHID);
					alCon.add(sCourceTypecode);

					// 导入条码
					try {
						java.util.ArrayList alResult = nc.ui.ic.pub.bc.BarCodeImportBO_Client
								.importSourceBarcode(alCon);
                         //采用新方法实现来源条码的覆盖当前条码 陈倪娜 2009-11-20
						java.util.ArrayList alresult = nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
								.setImportBarcode(billVO, alResult,true);

						if (alresult.size() > 0) {
							StringBuffer sbMsg = new StringBuffer(
									nc.ui.ml.NCLangRes.getInstance()
											.getStrByID("4008bill",
													"UPP4008bill-000081")/*
																			 * @res
																			 * "单据导入条码结果：\n"
																			 */);
							for (int i = 0; i < alresult.size(); i++) {
								sbMsg.append((String) alresult.get(i) + "\n");
							}
							showWarningMessage(sbMsg.toString());
						} else {
							showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000082")/*
																	 * @res
																	 * "单据行没有条码导入！"
																	 */);
							return;
						}
						// 如果当前在浏览情况下，需要直接保存到后台
						// false:前台不作校验，后台校验
						if (getM_iMode() == BillMode.Browse) {
							getClientUI().onImportSignedBillBarcode(billVO, false);
							showHintMessage(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000083")/*
																	 * @res
																	 * "导入来源单据完成！条码已经保存完毕。"
																	 */);
						} else {
							showHintMessage(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000084")/*
																	 * @res
																	 * "导入来源单据条码到当前单据界面上，请点按纽“保存”，保存单据。"
																	 */);
						}

					} catch (Exception e) {
						String[] args = new String[1];
						args[0] = headvo.getVbillcode().toString();
						String message = nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000342",
										null, args);
						/* @res "单据号为{0}的单据,不能导入来源单据的条码出现异常:" */
						sbErr.append(message);
					}
				}
				String sErrMsg = sbErr.toString();
				if (sErrMsg != null && sErrMsg.trim().length() > 0) {
					showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000044")/*
																			 * @res
																			 * "错误提示："
																			 */
							+ sErrMsg);
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000085")/*
																			 * @res
																			 * "导入来源单据失败！"
																			 */);

				}

			} else {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000086")/*
															 * @res
															 * "错误提示：没有选择的单据"
															 */);
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000085")/* @res "导入来源单据失败！" */);
			} // 把导入结果放到前台
		} catch (Exception e) {
			String sErrorMsg = e.getMessage();
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000044")/* @res "错误提示：" */
					+ sErrorMsg);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000085")/* @res "导入来源单据失败！" */);
		}
	}

	/**
	 * 李俊 功能：执行条码关闭 参数： 返回： 例外： 日期：(2004-10-18 10:37:47) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param iChoose
	 *            int
	 * 
	 */
	protected void onBarcodeOpenClose(int iChoose) {

		int[] iaSel = null;
		// get the selection of card or list
		if (BillMode.List == getM_iCurPanel()) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000125")/* @res "请在卡片模式下关闭打开条码" */);
			return;
		} else {
			iaSel = getBillCardPanel().getBillTable().getSelectedRows();
		}
		if (getM_iMode() == BillMode.New) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000126")/* @res "新增状态不能关闭打开条码！" */);
		}
		BarcodeCloseCtrl ctrl = new BarcodeCloseCtrl(getClientUI());

		UFBoolean ufBTrue = new UFBoolean(true);
		UFBoolean ufBFalse = new UFBoolean(false);
		UFBoolean ufBChoose = null;

		ArrayList alParam = null;
		if (iChoose == 0) {
			ufBChoose = ufBTrue;
			alParam = ctrl.openCloseBC(getM_voBill(), iaSel, ufBTrue);
		} else if (iChoose == 1) {
			ufBChoose = ufBFalse;
			alParam = ctrl.openCloseBC(getM_voBill(), iaSel, ufBFalse);
		}
		if (alParam == null)
			return;

		// fresh the voItems to UI

		GeneralBillItemVO[] voaItemBill = (GeneralBillItemVO[]) getM_voBill()
				.getChildrenVO();
		int sizeBill = voaItemBill.length;
		if (sizeBill == 0)
			return;

		ArrayList alBids = (ArrayList) alParam.get(1);
		ArrayList alTss = (ArrayList) alParam.get(2);
		// add to hash
		String sBIdsTemp = null;
		java.util.Hashtable htBids = new java.util.Hashtable();
		for (int i = 0; i < alBids.size(); i++) {
			sBIdsTemp = (String) alBids.get(i);
			if (sBIdsTemp == null)
				continue;
			htBids.put(sBIdsTemp, alTss.get(i));
		}

		// fresh m_voBill's ts
		// GeneralBillItemVO voItemTemp = null;
		String sBodyPKTemp = null;
		for (int m = 0; m < sizeBill; m++) {
			sBodyPKTemp = voaItemBill[m].getCgeneralbid();
			if (htBids.containsKey(sBodyPKTemp)) {
				voaItemBill[m].setTs((String) htBids.get(sBodyPKTemp));
				voaItemBill[m].setBarcodeClose(ufBChoose);
			}
		}
		// fresh the billModel's ts
		nc.ui.pub.bill.BillModel bm = getBillCardPanel().getBillModel();
		int iCount = bm.getRowCount();
		String strValue = null;
		for (int j = 0; j < iCount; j++) {

			strValue = (String) bm.getValueAt(j, "cgeneralbid");
			if (strValue == null)
				continue;
			if (htBids.containsKey(strValue)) {
				bm.setValueAt(htBids.get(strValue), j, "ts");
				bm.setValueAt(ufBChoose, j, "bbarcodeclose");
			}
		}
		// 修改人：刘家清 修改日期：2007-12-20上午10:37:57 修改原因：updateValue()后，会修改行状态。
		// getBillCardPanel().updateValue();
		// 修改人：刘家清 修改日期：2007-12-17上午09:12:25 修改原因：修改完后，恢复行的选择。
		for (int isel : iaSel) {
			if (isel > -1
					&& isel < getBillCardPanel().getBillTable().getRowCount())
				getBillCardPanel().getBillTable().setRowSelectionInterval(isel,
						isel);
		}

		// 修改人：刘家清 修改日期：2007-8-21上午10:23:19 修改原因：条码关闭之后，刷新的TS，所以要去刷新列表数据
		// updateBillToList(getM_voBill());
		// 修改人：刘家清 修改日期：2007-10-24下午01:59:14
		// 修改原因：用表单形式下的单据刷新列表数据，只更新bbarcodeclose和ts。
		getClientUI().updateBillToListByItemKeys(getM_voBill(), null, new String[] {
				nc.ui.ic.pub.bill.ItemKeyS.bbarcodeclose,
				nc.ui.ic.pub.bill.ItemKeyS.ts });

	}

	/**
	 * 条码编辑按钮点击事件相应方法 创建日期：(2003-09-28 9:51:50)
	 */
	protected void onBarCodeEdit() {
		// 判断是否能够进行条码编辑
		GeneralBillVO voBill = null;

		int iCurFixLine = 0;
		// 是否可以编辑
		boolean bDirectSave = false;
		if (getM_iMode() == BillMode.Browse || !getClientUI().m_bAddBarCodeField) {
			bDirectSave = true;
		} else {
			bDirectSave = false;
		}

		if (BillMode.List == getM_iCurPanel()) {
			bDirectSave = false;
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000071")/* @res "请在卡片模式下编辑条码" */);
			return;
		} else {
			voBill = getM_voBill();
			iCurFixLine = getBillCardPanel().getBillTable().getSelectedRow();
		}

		// 滤去表单形式下的空行
		getClientUI().filterNullLine();
		if (getBillCardPanel().getRowCount() <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000072")/* @res "请输入表体行!" */);
			getBillCardPanel().addLine();
			nc.ui.scm.pub.report.BillRowNo.addLineRowNo(getBillCardPanel(),
					getBillType(), IItemKey.CROWNO);
			return;
		}
		// 检查行号的合法性; 该方法应放在过滤空行的后面。
		// 需要按行号确定唯一行
		if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
				getBillCardPanel(), IItemKey.CROWNO)) {
			return;
		}
		boolean bEditable = true;

		getClientUI().m_dlgBarCodeEdit = getClientUI().getBarCodeDlg(bEditable, bDirectSave);

		if (voBill != null && iCurFixLine >= 0
				&& iCurFixLine < voBill.getItemCount()) {

			GeneralBillItemVO itemvo = voBill.getItemVOs()[iCurFixLine];
			// 是条码管理的存货
			if (itemvo.getBarcodeManagerflag().booleanValue()) {
				// 得到表头的单据号, 表体行号, 存货名称,存货编码
				// ArrayList altemp = new ArrayList();

				// 新增情况下，行号没有在m_voBill中存在,这里设置行号
				getClientUI().getGenBillUICtl().setBillCrowNo(voBill, getBillCardPanel());

				// 获得条码管理的Items

				ArrayList alReuslt = getClientUI().getBarcodeCtrl().getCurBarcodeItems(
						voBill, iCurFixLine);
				if (alReuslt == null || alReuslt.size() < 2) {
					return;
				}
				GeneralBillItemVO[] itemBarcodeVos = (GeneralBillItemVO[]) alReuslt
						.get(0);
				int iFixLine = ((Integer) alReuslt.get(1)).intValue();
				GeneralBillHeaderVO headervo = voBill.getHeaderVO();
				getClientUI().m_dlgBarCodeEdit.setHeaderItemvo(headervo);

				if (itemBarcodeVos[iFixLine].getBarCodeVOs() != null
						&& itemBarcodeVos[iFixLine].getBarCodeVOs().length > 0
						&& ((itemBarcodeVos[iFixLine].getBarCodeVOs()[itemBarcodeVos[iFixLine]
								.getBarCodeVOs().length - 1]
								.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
								&& itemBarcodeVos[iFixLine].getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 1]
										.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && itemBarcodeVos[iFixLine]
								.getBarCodeVOs()[itemBarcodeVos[iFixLine]
								.getBarCodeVOs().length - 1].getBasstaddflag()
								.booleanValue())
								|| (itemBarcodeVos[iFixLine].getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 1]
										.getAttributeValue(BarcodeparseCtrl.VBARCODE) == null
										&& itemBarcodeVos[iFixLine]
												.getBarCodeVOs()[itemBarcodeVos[iFixLine]
												.getBarCodeVOs().length - 1]
												.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && itemBarcodeVos[iFixLine]
										.getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 1]
										.getBasstaddflagsub().booleanValue())
								|| (itemBarcodeVos[iFixLine].getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 1]
										.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
										&& itemBarcodeVos[iFixLine]
												.getBarCodeVOs()[itemBarcodeVos[iFixLine]
												.getBarCodeVOs().length - 1]
												.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && itemBarcodeVos[iFixLine]
										.getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 1]
										.getBasstaddflagsub().booleanValue()) || (itemBarcodeVos[iFixLine]
								.getBarCodeVOs().length > 1
								&& itemBarcodeVos[iFixLine].getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 2]
										.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
								&& itemBarcodeVos[iFixLine].getBarCodeVOs()[itemBarcodeVos[iFixLine]
										.getBarCodeVOs().length - 2]
										.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && itemBarcodeVos[iFixLine]
								.getBarCodeVOs()[itemBarcodeVos[iFixLine]
								.getBarCodeVOs().length - 2]
								.getBasstaddflagsub().booleanValue()))
						&& itemBarcodeVos[iFixLine].getCastunitname() != null) {

					getClientUI().m_dlgBarCodeEdit.m_sNumItemKey = getEnvironment()
							.getAssistNumItemKey();
					getClientUI().m_dlgBarCodeEdit.m_sShouldNumItemKey = getEnvironment()
							.getShouldAssistNumItemKey();

				} else {
					getClientUI().m_dlgBarCodeEdit.m_sNumItemKey = getEnvironment()
							.getNumItemKey();
					getClientUI().m_dlgBarCodeEdit.m_sShouldNumItemKey = getEnvironment()
							.getShouldNumItemKey();
				}
				getClientUI().m_dlgBarCodeEdit.m_iBarcodeUIColorRow = getClientUI().m_iBarcodeUIColorRow;
				getClientUI().m_dlgBarCodeEdit.setColor(getClientUI().m_sColorRow);
				getClientUI().m_dlgBarCodeEdit.setScale(getClientUI().m_ScaleValue.getScaleValueArray());

				// 将条码是否保存参数设置到条码编辑界面，便于在编辑界面保存条码前控制
				// m_dlgBarCodeEdit.setSaveBarCode(m_bBarcodeSave);
				getClientUI().m_dlgBarCodeEdit.setSaveBadBarCode(getClientUI().m_bBadBarcodeSave);
				// 修改人:刘家清 修改日期:2007-04-10
				// 修改原因:逻辑不对,对于一个单据,是否保存条码是用bSaveBarcodeFinal
				try {
					if (voBill.bSaveBarcodeFinal())
						getClientUI().m_dlgBarCodeEdit.setSaveBadBarCode(true);
				} catch (BusinessException e) {
					nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null, e);

				}
				// 如果条码关闭，设置条码编辑框不能编辑
				boolean bbarcodeclose = itemvo.getBarcodeClose().booleanValue();
				getClientUI().m_dlgBarCodeEdit.setUIEditableBarcodeClose(!bbarcodeclose);
				getClientUI().m_dlgBarCodeEdit.setUIEditable(getM_iMode());
				// 设置到Items中
				getClientUI().m_dlgBarCodeEdit.setCurBarcodeItems(itemBarcodeVos, iFixLine);

				if (getClientUI().m_dlgBarCodeEdit.showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
					getBillCardPanel().getBillModel().setNeedCalculate(false);
					// 设置条码框数据
					getClientUI().getM_utfBarCode().setCurBillItem(itemBarcodeVos);
					// 还有要设置条码框的删除数据？m_utfBarCode.setRemoveBarcode(m_dlgBarCodeEdit.getBarCodeDelofAllVOs());

					// 目的设置m_billvo的条码数据，修改卡片界面状态

					getClientUI().setBarCodeOnUI(voBill, itemBarcodeVos);

					Hashtable htbItemBarcodeVos = getClientUI().getBarcodeCtrl()
							.getHtbItemBarcodeVos(itemBarcodeVos);

					if (htbItemBarcodeVos != null
							&& htbItemBarcodeVos.size() > 0) {

						GeneralBillItemVO billItemTemp = null;
						if (htbItemBarcodeVos != null) {
							GeneralBillItemVO[] billItemsAll = (GeneralBillItemVO[]) voBill
									.getChildrenVO();
							String sRowNo = null;
							for (int i = 0; i < billItemsAll.length; i++) {
								sRowNo = billItemsAll[i].getCrowno();
								if (sRowNo != null
										&& htbItemBarcodeVos
												.containsKey(sRowNo)) {
									billItemTemp = (GeneralBillItemVO) htbItemBarcodeVos
											.get(sRowNo);

									onBarCodeEditUpdateBill(i, billItemTemp);

								}

							}

							if (!getClientUI().m_dlgBarCodeEdit.m_bModifyBillUINum) { // 修改卡片界面状态
								showHintMessage(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000073")/*
																		 * @res
																		 * "条码编辑框的参数设置是不修改界面数量，单据界面实际数量不被修改！"
																		 */);
							}

							if (!getClientUI().getBarcodeCtrl().isModifyBillUINum()) {
								showHintMessage(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000074")/*
																		 * @res
																		 * "当前单据界面不允许修改通过条码数量修改实际数量，单据界面实际数量不被修改！"
																		 */);

							}

							if (getClientUI().m_dlgBarCodeEdit.m_bModifyBillUINum
									&& getClientUI().getBarcodeCtrl().isModifyBillUINum()) { // 修改卡片界面状态
								showHintMessage(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000075")/*
																		 * @res
																		 * "条码编辑框的参数设置是修改界面数量并且界面规则允许修改实际数量，单据界面实际数量已经被修改！"
																		 */);
							}

						}

					}
					// dw
					getClientUI().getEditCtrl().resetSpace(iCurFixLine);

					getBillCardPanel().getBillModel().setNeedCalculate(true);
					getBillCardPanel().getBillModel().reCalcurateAll();

				}

			} else {// 修改人:刘家清 修改日期:2007-04-05 修改原因:不是存货管理的物品
				nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null,
						new BusinessException(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000002")/*
																				 * @res
																				 * "下列存货非主条码管理或次条码管理，请修改存货管理档案的属性："
																				 */
								+ itemvo.getCinventorycode()));
			}

		} else {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000356")/* @res "请选择表体行！" */);
		}
	}

	/**
	 * ?user> 功能： 参数： 返回： 例外： 日期：(2004-5-24 15:54:15) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param iCurFixLine
	 *            int
	 * @param billItemvo
	 *            nc.vo.ic.pub.bill.GeneralBillItemVO
	 */
	private void onBarCodeEditUpdateBill(int iCurFixLine,
			GeneralBillItemVO billItemvo) {

		boolean bNegative = false; // 是否负数
		// 修改实发数量和应发数量
		UFDouble ufdNum = null;
		// 根据"是否按辅单位增加数量"属性，如果是则辅数量自动加一；否则主数量自动加一。
		String m_sMyItemKey = null;
		String m_sMyShouldItemKey = null;
		if (billItemvo.getBarCodeVOs() != null
				&& billItemvo.getBarCodeVOs().length > 0
				&& ((billItemvo.getBarCodeVOs().length == 1
						&& billItemvo.getBarCodeVOs()[billItemvo
								.getBarCodeVOs().length - 1]
								.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
						&& billItemvo.getBarCodeVOs()[billItemvo
								.getBarCodeVOs().length - 1]
								.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && billItemvo
						.getBarCodeVOs()[billItemvo.getBarCodeVOs().length - 1]
						.getBasstaddflag().booleanValue())
						|| (billItemvo.getBarCodeVOs().length > 1
								&& billItemvo.getBarCodeVOs()[billItemvo
										.getBarCodeVOs().length - 2]
										.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
								&& billItemvo.getBarCodeVOs()[billItemvo
										.getBarCodeVOs().length - 2]
										.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && billItemvo
								.getBarCodeVOs()[billItemvo.getBarCodeVOs().length - 2]
								.getBasstaddflag().booleanValue())
						|| (billItemvo.getBarCodeVOs()[billItemvo
								.getBarCodeVOs().length - 1]
								.getAttributeValue(BarcodeparseCtrl.VBARCODE) == null
								&& billItemvo.getBarCodeVOs()[billItemvo
										.getBarCodeVOs().length - 1]
										.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && billItemvo
								.getBarCodeVOs()[billItemvo.getBarCodeVOs().length - 1]
								.getBasstaddflagsub().booleanValue())
						|| (billItemvo.getBarCodeVOs()[billItemvo
								.getBarCodeVOs().length - 1]
								.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
								&& billItemvo.getBarCodeVOs()[billItemvo
										.getBarCodeVOs().length - 1]
										.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && billItemvo
								.getBarCodeVOs()[billItemvo.getBarCodeVOs().length - 1]
								.getBasstaddflagsub().booleanValue()) || (billItemvo
						.getBarCodeVOs().length > 1
						&& billItemvo.getBarCodeVOs()[billItemvo
								.getBarCodeVOs().length - 2]
								.getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
						&& billItemvo.getBarCodeVOs()[billItemvo
								.getBarCodeVOs().length - 2]
								.getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && billItemvo
						.getBarCodeVOs()[billItemvo.getBarCodeVOs().length - 2]
						.getBasstaddflagsub().booleanValue()))
				&& billItemvo.getCastunitname() != null) {

			m_sMyItemKey = getEnvironment().getAssistNumItemKey();
			m_sMyShouldItemKey = getEnvironment().getShouldAssistNumItemKey();

		} else {
			m_sMyItemKey = getEnvironment().getNumItemKey();
			m_sMyShouldItemKey = getEnvironment().getShouldNumItemKey();
		}
		Object oTemp = getBillCardPanel().getBodyValueAt(iCurFixLine,
				m_sMyItemKey);
		if (oTemp == null) {
			ufdNum = GeneralBillClientUI.UFDZERO;
		} else {
			ufdNum = (UFDouble) oTemp;
		}

		UFDouble ufdShouldNum = null;
		Object oShouldTemp = getBillCardPanel().getBodyValueAt(iCurFixLine,
				m_sMyShouldItemKey);
		if (oShouldTemp == null) {
			ufdShouldNum = GeneralBillClientUI.UFDZERO;
		} else {
			ufdShouldNum = (UFDouble) oShouldTemp;
		}
		if (ufdNum.doubleValue() < 0 || ufdShouldNum.doubleValue() < 0) {
			bNegative = true;
		}

		// 删除的数据
		// UFDouble ufdZero = UFDZERO;
		UFDouble ufdNumDlg = GeneralBillClientUI.UFDZERO;
		nc.vo.ic.pub.bc.BarCodeVO[] barcodevosAll = billItemvo.getBarCodeVOs();

		if (barcodevosAll != null) {
			for (int n = 0; n < barcodevosAll.length; n++) {
				if (barcodevosAll[n] != null
						&& barcodevosAll[n].getStatus() != nc.vo.pub.VOStatus.DELETED) {
					ufdNumDlg = ufdNumDlg.add(barcodevosAll[n].getNnumber());
				}
			}
		}
		// 大于应发数量，而又控制不能超应发数量：盘点调整
		if (ufdNumDlg.doubleValue() > ufdShouldNum.doubleValue()
				&& !getClientUI().getBarcodeCtrl().isOverShouldNum()) {
			ufdNumDlg = ufdShouldNum.abs();
		}

		// 转换为负数
		if (getClientUI().m_bFixBarcodeNegative || bNegative)
			ufdNumDlg = ufdNumDlg.multiply(GeneralBillClientUI.UFDNEGATIVE);

		if (ufdNumDlg == null)
			ufdNumDlg = GeneralBillClientUI.UFDZERO;

		// 设置条码数量字段
		try {
			getBillCardPanel().setBodyValueAt(ufdNumDlg.abs(), iCurFixLine,
					IItemKey.NBARCODENUM);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
		}
		// 是否修改单据数量要根据下面的情况判断
		if (getClientUI().m_dlgBarCodeEdit.m_bModifyBillUINum
				&& getClientUI().getBarcodeCtrl().isModifyBillUINum()
				&& getM_iMode() != BillMode.Browse) {
			// 修改卡片界面状态

			getBillCardPanel().setBodyValueAt(ufdNumDlg, iCurFixLine,
					m_sMyItemKey);

			nc.ui.pub.bill.BillEditEvent event1 = new nc.ui.pub.bill.BillEditEvent(
					getBillCardPanel().getBodyItem(m_sMyItemKey), ufdNumDlg,
					m_sMyItemKey, iCurFixLine);
			getClientUI().afterEdit(event1);
			// 执行模版公式
			getClientUI().getGenBillUICtl().execEditFormula(getBillCardPanel(), iCurFixLine,
					m_sMyItemKey);
			// 触发单据行状态为修改
			if (getBillCardPanel().getBodyValueAt(iCurFixLine,
					IItemKey.NAME_BODYID) != null)
				getBillCardPanel().getBillModel().setRowState(iCurFixLine,
						BillMode.Update);

		}
	}

	/**
	 * 篭n创建日期：(2003-3-6 15:13:32) 作者：余大英 修改日期： 修改人： 修改原因： 算法说明：
	 */
	protected void onDocument() {
//    try{
    
//      nc.ui.ic.pub.tools.GenMethod.callICEJBService("nc.bs.ic.util.BtnReg", 
//          "getBillBtnRegSql", 
//          new Class[]{
//            String.class,String.class,String.class,String.class,String.class
//          }, 
//          new Object[]{
//            "行操作","批改","SCMCOMMONIC55YB003","Alt","R"
//          }
//      );
//      
//      nc.ui.ic.pub.tools.GenMethod.callICEJBService("nc.bs.ic.util.BtnReg", 
//          "getSpecBillBtnRegSql", 
//          new Class[]{
//            String.class,String.class,String.class,String.class,String.class
//          }, 
//          new Object[]{
//            "行操作","批改","SCMCOMMONIC55YB003","Alt","R"
//          }
//      );
//      
//    }catch(Exception e){
//      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
//    }
    
		ArrayList alBill = getClientUI().getSelectedBills();
		if (alBill == null || alBill.size() == 0) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000076")/* @res "请先选择单据！" */);
			return;
		}
		String[] spk = new String[alBill.size()];
		String[] scode = new String[alBill.size()];
		GeneralBillVO vo = null;
		GeneralBillHeaderVO voHead = null;
		for (int i = 0; i < alBill.size(); i++) {
			vo = (GeneralBillVO) alBill.get(i);
			if (vo != null) {
				voHead = (GeneralBillHeaderVO) vo.getParentVO();
				if (voHead != null) {
					spk[i] = voHead.getCgeneralhid();
					scode[i] = voHead.getVbillcode();
				}

			}
		}
		DocumentManager.showDM(getClientUI(), vo.getCbilltypecode() ,spk/*, scode*/);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-8-25 13:53:58) 由应发（收）自动填写实发（收）
	 */
	protected void onFillNum() {

		// 数量
		if (getBillCardPanel().getBodyItem(
				getEnvironment().getShouldNumItemKey()) != null
				&& getBillCardPanel().getBodyItem(
						getEnvironment().getNumItemKey()) != null)
			GeneralBillUICtl.fillValue(getBillCardPanel(), getClientUI(),
					getEnvironment().getShouldNumItemKey(), getEnvironment()
							.getNumItemKey(),getEnvironment().getAssistNumItemKey());

		if (getM_iMode() == BillMode.New) {
			GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
					getBillCardPanel(), getBillType());
		}

		return;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-8-25 13:53:58) 出库单自动拣货, 是否拆行
	 */
	protected void onPickAuto() {

		if (getM_voBill() == null)
			return;

		if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
				getBillCardPanel(), IItemKey.CROWNO)) {
			return;
		}
		// zhy2005-05-17设置单据类型 解决自动捡货空指针
		getM_voBill().setHeaderValue("cbilltypecode", getBillType());

		GeneralBillVO voOutBill = (GeneralBillVO) getM_voBill().clone();
		// 借出转销售的不允许捡货
		String sBillType = voOutBill.getHeaderVO().getCbiztypeid();
		try {
			String sReturn = (new QueryInfo()).queryBusiTypeVerify(sBillType);
			if (sReturn != null && sReturn.equals("C")) {
				nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(),
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000270")/*
														 * @res "提示"
														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"4008bill", "UPP4008bill-000496")/*
																	 * @res
																	 * "借出转销售不能自动捡货！"
																	 */);
				return;
			}

		} catch (Exception e) {

			nc.vo.scm.pub.SCMEnv.error(e);
      return;
		}

		ArrayList alrow = new ArrayList();

		// ArrayList alNew = new ArrayList();

		GeneralBillItemVO[] voItems = voOutBill.getItemVOs();
		for (int i = 0; i < voItems.length; i++) {
			// zhy如果是辅计量管理的存货,没有输入辅单位,则不能做自动拣货
			InvVO invvo = voItems[i].getInv();
			Integer IsAstUOMmgt = invvo.getIsAstUOMmgt();
			if (IsAstUOMmgt != null
					&& IsAstUOMmgt.intValue() == 1
					&& (voItems[i].getCastunitid() == null || voItems[i]
							.getCastunitid().trim().length() == 0)) {
				nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(),
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000270")/*
																	 * @res "提示"
																	 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"4008bill", "UPP4008bill-000511")/*
																	 * @res
																	 * "表体行辅计量管理存货应输入辅单位，不能做自动拣货！"
																	 */);
				return;
			}

			if (voItems[i].getNshouldoutnum() == null
					|| voItems[i].getNshouldoutnum().doubleValue() < 0) {
				nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(),
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000270")/*
																	 * @res "提示"
																	 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"4008bill", "UPP4008bill-000117")/*
																	 * @res
																	 * "表体行应发数量小于零，不能做自动拣货！"
																	 */);
				return;
			}
			// 过滤空行,纪录原始行号
			String crowno = null;
			if (voItems[i].getCinventoryid() != null) {
				if (voItems[i].getCrowno() == null)
					crowno = (String) getBillCardPanel().getBodyValueAt(i,
							"crowno");
				else
					crowno = voItems[i].getCrowno();
				voItems[i].setVbodynote2(crowno);
				alrow.add(voItems[i]);
			}

		}
		if (alrow.size() > 0) {
			voItems = new GeneralBillItemVO[alrow.size()];
			alrow.toArray(voItems);
			voOutBill.setChildrenVO(voItems);

		}
		Boolean isSort = getBillCardPanel().getBillTable().isSortEnabled();
    try{
      if (isSort)
      getClientUI().setSortEnable(false);


  		java.util.Hashtable ht = new java.util.Hashtable();
  		// Vector v = new Vector();
  		GeneralBillVO voNew = null;
  		voOutBill.getHeaderVO().setCoperatoridnow(getEnvironment().getUserID());
  		voOutBill.getHeaderVO().setClogdatenow(getEnvironment().getLogDate());
  		boolean isSN = false;
  		boolean isLocator = false;
  		try {
  			voNew = nc.ui.ic.ic2a1.PickBillHelper.pickAuto(voOutBill,
  					new nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
  			if (voNew != null && voNew.getItemVOs() != null) {
  
  				getClientUI().calcSpace(voNew);
  
  				voItems = voNew.getItemVOs();
  				// 执行公式
  				getBillCardPanel().getBillModel().execFormulasWithVO(voItems,
  						getClientUI().getBodyFormula());
  
  				// 纪录货未行
  
  				boolean isVendor = false;
  				// 货未档案ID
  				String[] aryItemField11 = new String[] {
  						"vspacename->getColValue(bd_cargdoc,csname,pk_cargdoc,cspaceid)",
  						"vspacecode->getColValue(bd_cargdoc,cscode,pk_cargdoc,cspaceid)",
  						"crsvbaseid5->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,cvendorid)",
  						"vvendorname->getColValue(bd_cubasdoc,custname,pk_cubasdoc,crsvbaseid5)" };
  
  				for (int i = 0; i < voItems.length; i++) {
  					if (voItems[i].getCvendorid() != null)
  						isVendor = true;
  					if (voItems[i].getLocator() != null
  							&& voItems[i].getLocator().length > 0) {
  
  						isLocator = true;
  
  					}
  					if (voItems[i].getSerial() != null
  							&& voItems[i].getSerial().length > 0) {
  
  						isSN = true;
  					}
  				}
  
  				// 有货位数据
  				if (isLocator || isVendor) {
  					nc.vo.pub.SuperVOUtil.execFormulaWithVOs(voItems,
  							aryItemField11, null);
  
  				}
  
  			}
  		} catch (Exception e) {
  
  			nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
  					.getInstance().getStrByID("SCMCOMMON",
  							"UPPSCMCommon-000059")/* @res "错误" */,
  					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
  							"UPP4008bill-000118")/* @res "自动拣货失败：" */
  							+ e.getMessage());
  			return;
  		}
  
  		if (voItems != null && voItems.length > 0) {
  			String key = null;
  			UFDouble dkey = new UFDouble(0);
  			int iCurRow = 0;
  			int icount = 0;// 计数器
  
  			GeneralBillItemVO voRow = null;
  
  			getBillCardPanel().getBillModel().setNeedCalculate(false);
  
  			int irows = getBillCardPanel().getRowCount();
  			UFDouble dLastRowNo = new UFDouble(0);
  			String sLastRowNo = null;
  			if (irows > 0) {
  				sLastRowNo = (String) getBillCardPanel().getBodyValueAt(
  						irows - 1, "crowno");// 最后一行的行号
  				dLastRowNo = new UFDouble(sLastRowNo);
  				// 现将光标置于第一行
  				//getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
  			}
  
  			//BatchCodeDefSetTool.execFormulaForBatchCode(voItems);
  
  			for (int i = 0; i < voItems.length; i++) {
  				key = voItems[i].getVbodynote2();
  				dkey = new UFDouble(key);
  				voItems[i].procLocNumDigit(getClientUI().m_ScaleValue.getNumScale(),
  						getClientUI().m_ScaleValue.getAssistNumScale());
  				if (!ht.containsKey(key)) {// 如果是原行
  					ht.put(key, key);
  					voRow = voItems[i];
  					getBillCardPanel().setBodyValueAt(key, i, "crowno");
  					voRow.setCgeneralhid(voOutBill.getItemVOs()[icount]
  							.getCgeneralhid());
  					voRow.setCgeneralbid(voOutBill.getItemVOs()[icount]
  							.getCgeneralbid());
  					voRow.setCrowno(voRow.getVbodynote2());
  					voRow.setVbodynote2(null);
  					getBillCardPanel().getBillModel().setBodyRowVO(voRow, i);
  					// 执行辅数量的编辑公式。1201
  					getClientUI().execEditFomulas(i, getEnvironment().getNumItemKey());
  					if (getBillCardPanel().getBillModel().getRowState(i) != nc.ui.pub.bill.BillModel.ADD
  							&& getBillCardPanel().getBillModel().getRowState(i) != nc.ui.pub.bill.BillModel.MODIFICATION)
  						getBillCardPanel().getBillModel().setRowState(i,
  								nc.ui.pub.bill.BillModel.MODIFICATION);
  
  					// 光标置于下一行（需要考虑临界状态）
  
  					// if(dkey.compareTo(dLastRowNo)<0)
  					// getBillCardPanel().getBillTable().setRowSelectionInterval(i+1,
  					// i+1);
  					// else{
  					// //在后面增行
  					// }
  					// iCurRow=i+1;
  					icount++;
  				} else {// 如果是拆分的行，则插入行，然后将vo置入
  					voRow = voItems[i];
  					voRow.setCgeneralbid(null);
  					voRow.setCgeneralhid(null);
  					voRow.setVbodynote2(null);
  					voRow.setNshouldoutnum(null);
  					voRow.setNshouldoutassistnum(null);
//  				deleted by lirr 2009-06-02 修改原因：NCdp200867926 存货多行时部分序列号没有选中
  					//voRow.setSerial(null);
  					voRow.setAttributeValue("cparentid", null);
  					voRow.setBarCodeVOs(null);
  					voRow.setAttributeValue(IItemKey.NBARCODENUM, new UFDouble(
  							0.0));
  					voRow.setBarcodeClose(new nc.vo.pub.lang.UFBoolean('N'));
  					voRow.setAttributeValue(IItemKey.NKDNUM, null);
  
  					// if(key.compareTo(sLastRowNo)<0){
  					if (dkey.compareTo(dLastRowNo) < 0) {
  						// 插行
  						iCurRow = i;
  						getBillCardPanel().getBillTable()
  								.setRowSelectionInterval(iCurRow, iCurRow);
  						getBillCardPanel().insertLine();
  						nc.ui.scm.pub.report.BillRowNo.insertLineRowNos(
  								getBillCardPanel(), getBillType(),
  								IItemKey.CROWNO,iCurRow+1,1);
  						voRow.setCrowno((String) getBillCardPanel()
  								.getBodyValueAt(iCurRow, "crowno"));
  						getBillCardPanel().getBillModel().setBodyRowVO(voRow,
  								iCurRow);
  						getClientUI().execEditFomulas(iCurRow, getEnvironment()
  								.getNumItemKey());
  					} else {
  						// 增行
  						getBillCardPanel().addLine();
  						nc.ui.scm.pub.report.BillRowNo.addLineRowNo(
  								getBillCardPanel(), getBillType(),
  								IItemKey.CROWNO);
  						voRow.setCrowno((String) getBillCardPanel()
  								.getBodyValueAt(i, "crowno"));
  						getBillCardPanel().getBillModel()
  								.setBodyRowVO(voRow, i);
  						getClientUI().execEditFomulas(i, getEnvironment().getNumItemKey());
  					}
  
  				}
  
  				// zhy拣货后,从界面取一遍hsl
  				GeneralBillUICtl.synUi2Vo(getBillCardPanel(), voNew,
  						new String[] { "hsl" }, i);
  			}
  
  			getBillCardPanel().getBillModel().setNeedCalculate(true);
  			getBillCardPanel().getBillModel().reCalcurateAll();
  			// 修改人：刘家清 修改日期：2007-11-20下午03:43:09 修改原因：复制授权用户ID
  			voNew
  					.setAccreditBarcodeUserID(
  							GenParameterVO.FUNBARCODE4EFROM4YCHECK,
  							voOutBill
  									.getAccreditBarcodeUserID(GenParameterVO.FUNBARCODE4EFROM4YCHECK));
  			voNew
  					.setAccreditBarcodeUserID(
  							GenParameterVO.FUNBARCODEONHANDCHECK,
  							voOutBill
  									.getAccreditBarcodeUserID(GenParameterVO.FUNBARCODEONHANDCHECK));
  			voNew
  					.setAccreditBarcodeUserID(
  							GenParameterVO.FUNBARCODEREPEATCHECK,
  							voOutBill
  									.getAccreditBarcodeUserID(GenParameterVO.FUNBARCODEREPEATCHECK));
  			voNew
  					.setAccreditBarcodeUserID(
  							GenParameterVO.FUNFORCESAVEBARCODE,
  							voOutBill
  									.getAccreditBarcodeUserID(GenParameterVO.FUNFORCESAVEBARCODE));
  			voNew
  					.setAccreditBarcodeUserID(
  							GenParameterVO.FUNFORCESAVELENDBARCODE,
  							voOutBill
  									.getAccreditBarcodeUserID(GenParameterVO.FUNFORCESAVELENDBARCODE));
  			voNew
  					.setAccreditBarcodeUserID(
  							GenParameterVO.FUNFORCESAVESALERETUNBARCODE,
  							voOutBill
  									.getAccreditBarcodeUserID(GenParameterVO.FUNFORCESAVESALERETUNBARCODE));
  			setM_voBill((GeneralBillVO) voNew.clone());
  			
//  			同步行定位器
			getClientUI().synUIVORowPos();
  			
  			// 初始化序列号数据
  			if (isSN)
  				getClientUI().setM_alSerialData(voNew.getSNs());
  			// 有货位数据
  			if (isLocator)
  
  				getClientUI().setM_alLocatorData(voNew.getLocators());
  
  		}
  
  		if (getM_iMode() == BillMode.New) {
  			GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
  					getBillCardPanel(), getBillType());
  		}
/*  		int selrow = getBillCardPanel().getBillTable().getSelectedRow();
  		if (selrow >= 0) {
  			nc.ui.pub.bill.BillEditEvent event = new nc.ui.pub.bill.BillEditEvent(
  					getBillCardPanel().getBillTable(), selrow, selrow);
  			getClientUI().bodyRowChange(event);
  		}*/
  
  		return;
    }finally{
    	if(isSort)
      getClientUI().setSortEnable(true);
    }

	}

	protected void onRefInICBill() {
		try { 

           //增加单据参照形式页签 2009-09-29 陈倪娜
			nc.ui.ic.pub.pf.ICBillQuery dlgQry = new nc.ui.ic.pub.pf.ICBillQuery(
					getClientUI());
			
			dlgQry.setTempletID(getEnvironment().getCorpID(), "40080608",
					getEnvironment().getUserID(), null, "40089908");

			dlgQry.initData(getEnvironment().getCorpID(), getEnvironment().getUserID(),
					"40089908", null, "4I", nc.vo.ic.pub.BillTypeConst.m_otherIn, null);

			dlgQry.setBillRefModeSelPanel(true);
	
			if (dlgQry.showModal() == nc.ui.pub.beans.MessageDialog.ID_OK) {
				

				// 需要注册
				nc.vo.pub.query.ConditionVO[] voCons = dlgQry.getConditionVO();

				// 获取查询条件
				StringBuffer sWhere = new StringBuffer(" 1=1 ");
				if (voCons != null && voCons.length > 0 && voCons[0] != null) {
					sWhere.append(" and " + dlgQry.getWhereSQL(voCons));
				}
				sWhere.append(" ");
				
				nc.ui.ic.pub.pf.ICSourceRefBaseDlg dlgBill = new ICSourceRefBaseDlg(
					      IItemKey.CGENERALHID, getEnvironment().getCorpID(), 
					      getEnvironment().getUserID(), "40080608",
					      sWhere.toString(), nc.vo.ic.pub.BillTypeConst.m_otherIn, null,
					      null, "4I", getClientUI());
				class IBillReferQueryProxyExt extends IBillReferQueryProxy{
					
					  public IBillReferQueryProxyExt(nc.ui.ic.pub.pf.ICBillQuery adlg){
						  this.dlg = adlg;
					  }
					  
					  nc.ui.ic.pub.pf.ICBillQuery dlg;
					  /**
					   * 使用的是新查询模版
					   * 
					   */
					  public  boolean isNewQryDlg() {
						  return false;
					  }
					  
					  /**
					   * 创建新查询模版
					   * 
					   */
					  public QueryConditionDLG createNewQryDlg(){
						  return null;
					  }
					  
					  /**
					   * 创建旧查询模版
					   * 
					   */
					  public QueryConditionClient createOldQryDlg() {
						  return this.dlg;
					  }
					  
					  
					  /**
					   * 显示单表还是双表参照 
					   */
					  public boolean isShowDoubleTableRef(){
						  return this.dlg.isShowDoubleTableRef();
					  }
					  
					  /**
					   * 使用用户的选在创建查询对话框时设置查询对话框显示模式（主子表还是单表）
					   * 需要子类实现，子类需要设置对话框的显示模式选择
					   */
					  public void setUserRefShowMode(boolean isShowDoubleTableRef) {
						  this.dlg.setBillRefShowMode(isShowDoubleTableRef);
					  }
				};
				dlgBill.setQueyDlg(new IBillReferQueryProxyExt(dlgQry));
				dlgBill.loadHeadData();
				dlgBill.addBillUI();
				

				nc.vo.scm.pub.ctrl.GenMsgCtrl.printHint("will load qrybilldlg");
				if (dlgBill.showModal() == nc.ui.pub.beans.MessageDialog.ID_OK) {
					nc.vo.scm.pub.ctrl.GenMsgCtrl
							.printHint("qrybilldlg closeok");
					// 获取所选VO
					nc.vo.pub.AggregatedValueObject[] vos = dlgBill.getRetVos();
					nc.vo.scm.pub.ctrl.GenMsgCtrl
							.printHint("qrybilldlg getRetVos");

					if (vos == null) {
						nc.vo.scm.pub.ctrl.GenMsgCtrl
								.printHint("qrybilldlg getRetVos null");
						return;
					}

					// //
					nc.vo.scm.pub.ctrl.GenMsgCtrl
							.printHint("qrybilldlg getRetVos is not null");
/*					nc.vo.pub.AggregatedValueObject[] voRetvos = (nc.vo.pub.AggregatedValueObject[]) nc.ui.pub.change.PfChangeBO_Client
							.pfChangeBillToBillArray(vos, nc.vo.ic.pub.BillTypeConst.m_otherIn, "4I");*/
					nc.vo.pub.AggregatedValueObject[] voRetvos =vos;
					nc.vo.scm.pub.ctrl.GenMsgCtrl
							.printHint("qrybilldlg getRetVos pfChangeBillToBillArray ok");

					// 控制界面
					getClientUI().setBillRefResultVO(null, voRetvos);
					if (getM_voBill().getItemVOs().length > 0
							&& getM_voBill().getItemVOs()[0] != null
							&& getM_voBill().getItemVOs()[0].getNoutnum() != null) {
						getClientUI().setM_alSerialData(getM_voBill().getSNs());
						getClientUI().setM_alLocatorData(getM_voBill().getLocators());
					}

					nc.vo.scm.pub.ctrl.GenMsgCtrl
							.printHint("qrybilldlg getRetVos pfChangeBillToBillArray ok setBillRefResultVO ok");

				}
			}

		} catch (Exception e) {

			showErrorMessage(e.getMessage());
		}

	}


	/**
	 * 单据导出Excel 创建日期：(2003-09-28 9:51:50)
	 */
	protected void onBillExcel(int iFlag) {

		ExcelFileVO[] vos = null;
		GeneralBillItemVO[] tvos = null;
		GeneralBillVO voBill = null;
		String sBillCode = null;
		// String sPKCorp = null;
		// String sFilePath = null;
		String sFilePathDir = null;
		String sBillTypeCode = null;
		String sBillTypeName = null;

		if (iFlag == 1 || iFlag == 3/* 导出为xml */) {
			// 打开文件
			if (getClientUI().getChooser().showSaveDialog(getClientUI()) == javax.swing.JFileChooser.CANCEL_OPTION)
				return;
			sFilePathDir = getClientUI().getChooser().getSelectedFile().toString();
		}
		if (sFilePathDir == null) {
			showHintMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000008")/*请输入文件名保存!*/);
			return;
		}

		try {
			boolean isHaveBC = false;
			
			// 依当前是列表还是表单而定导出内容
			if (getM_iCurPanel() == BillMode.Card) { // 浏览
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000119")/*
															 * @res
															 * "正在导出，请稍候..."
															 */);

				// 准备数据
				voBill = getM_voBill();
				if (voBill == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "请先查询或录入单据。"
																			 */);
					return;
				}
				if (voBill.getParentVO() == null) {
					voBill.setParentVO(new GeneralBillHeaderVO());
				}
				if ((voBill.getChildrenVO() == null)
						|| (voBill.getChildrenVO().length == 0)
						|| (voBill.getChildrenVO()[0] == null)) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "请选择要处理的数据！"
															 */);
					return;
				}

				if (iFlag == 3) {

					sBillCode = voBill.getHeaderVO().getVbillcode();
					// sFilePath = sFilePathDir + "\\" + "Icbill" + sBillCode
					// + ".xml";
					onExportXML(new GeneralBillVO[] { voBill }, sFilePathDir);
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000120")/*
																			 * @res
																			 * "导出完成"
																			 */);
					return;
				}

				// 得到单据号，公司
				sBillCode = voBill.getParentVO().getAttributeValue("vbillcode") == null ? ""
						: voBill.getParentVO().getAttributeValue("vbillcode")
								.toString();
				// sPKCorp = voBill.getParentVO().getAttributeValue("pk_corp")
				// == null ? ""
				// : voBill.getParentVO().getAttributeValue("pk_corp")
				// .toString();
				sBillTypeCode = voBill.getBillTypeCode();
				sBillTypeName = GeneralBillVO.getBillTypeName(sBillTypeCode);

				tvos = (GeneralBillItemVO[]) voBill.getChildrenVO();
				// 需要将条码的信息一起导出
				Vector v = new Vector();
				ExcelFileVO vo = null;

				BarCodeVO[] bvos = null;
				
				//
				for (int i = 0; i < tvos.length; i++) {
					bvos = tvos[i].getBarCodeVOs();
					
					if (null == bvos || 0 == bvos.length)
						continue;
					
					isHaveBC = true;

					// if (tvos[i].getBarcodeManagerflag().booleanValue() ==
					// false)
					// continue;
					//
					// if (tvos[i].getBarcodeClose() == null
					// || tvos[i].getBarcodeClose().booleanValue() == true)
					// continue;

					if (bvos != null && bvos.length > 0) {
						for (int j = 0; j < bvos.length; j++) {
							vo = new ExcelFileVO();
							// 单据号信息
							vo.setAttributeValue("billcode", sBillCode); // 单据号
							// 单据行信息
							vo.setAttributeValue("rowno", tvos[i].getCrowno()); // 行号
							vo.setAttributeValue("inventorycode", tvos[i]
									.getCinventorycode()); // 存货编码
							vo.setAttributeValue("inventoryname", tvos[i]
									.getInvname()); // 存货名称
							vo.setAttributeValue("billtypecode", sBillTypeCode);
							vo.setAttributeValue("billtypename", sBillTypeName);

							UFDouble dshouldin = null; // 应收
							UFDouble dshouldout = null; // 应发
							UFDouble dShouldnum = null;

							UFDouble din = null; // 实收
							UFDouble dout = null; // 实发
							UFDouble dnum = null; // 要导出的数量

							dshouldin = tvos[i].getNshouldinnum();
							dshouldout = tvos[i].getNshouldoutnum();
							if (dshouldin == null && dshouldout == null) {
								dShouldnum = null;
							} else {
								dShouldnum = dshouldin == null ? dshouldout
										: dshouldin;
							}

							din = tvos[i].getNinnum();
							dout = tvos[i].getNoutnum();
							if (din == null && dout == null) {
								dnum = null;
							} else {
								dnum = din == null ? dout : din;
							}
							if (dShouldnum != null)
								vo.setAttributeValue("shouldnum", dShouldnum
										.toString()); // 存货数量（实收数量）
							if (dnum != null)
								vo.setAttributeValue("nnum", dnum.toString()); // 存货数量（实收数量）
							vo.setAttributeValue("free", tvos[i].getVfree0()); // 自由项目
							vo.setAttributeValue("batchcode", tvos[i]
									.getVbatchcode()); // 批次号
							vo.setAttributeValue("cgeneralbid", tvos[i]
									.getCgeneralbid()); // 表体pk
							vo.setAttributeValue("cgeneralhid", tvos[i]
									.getCgeneralhid()); // 表头pk
							// 条码信息
							vo.setBarcode(bvos[j].getVbarcode()); // 主条码
							vo.setBarcodesub(bvos[j].getVbarcodesub()); // 次条码
							vo.setPackcode(bvos[j].getVpackcode()); // 箱条码

							v.add(vo);

						}
					} else {

						vo = new ExcelFileVO();
						// 单据号信息
						vo.setAttributeValue("billcode", sBillCode); // 单据号
						// 单据行信息
						vo.setAttributeValue("rowno", tvos[i].getCrowno()); // 行号
						vo.setAttributeValue("inventorycode", tvos[i]
								.getCinventorycode()); // 存货编码
						vo.setAttributeValue("inventoryname", tvos[i]
								.getInvname()); // 存货名称
						vo.setAttributeValue("billtypecode", sBillTypeCode);
						vo.setAttributeValue("billtypename", sBillTypeName);

						UFDouble dshouldin = null; // 应收
						UFDouble dshouldout = null; // 应发
						UFDouble dShouldnum = null;

						UFDouble din = null; // 实收
						UFDouble dout = null; // 实发
						UFDouble dnum = null; // 要导出的数量

						dshouldin = tvos[i].getNshouldinnum();
						dshouldout = tvos[i].getNshouldoutnum();
						if (dshouldin == null && dshouldout == null) {
							dShouldnum = null;
						} else {
							dShouldnum = dshouldin == null ? dshouldout
									: dshouldin;
						}

						din = tvos[i].getNinnum();
						dout = tvos[i].getNoutnum();
						if (din == null && dout == null) {
							dnum = null;
						} else {
							dnum = din == null ? dout : din;
						}
						if (dShouldnum != null)
							vo.setAttributeValue("shouldnum", dShouldnum
									.toString()); // 存货数量（实收数量）
						if (dnum != null)
							vo.setAttributeValue("nnum", dnum.toString()); // 存货数量（实收数量）
						vo.setAttributeValue("free", tvos[i].getVfree0()); // 自由项目
						vo.setAttributeValue("batchcode", tvos[i]
								.getVbatchcode()); // 批次号
						vo.setAttributeValue("cgeneralbid", tvos[i]
								.getCgeneralbid()); // 表体pk
						vo.setAttributeValue("cgeneralhid", tvos[i]
								.getCgeneralhid()); // 表头pk
						v.add(vo);
					}
					 
					
					// 已经得到vo，然后导出vo
					vos = new ExcelFileVO[v.size()];
					v.copyInto(vos);
					// 调用导出接口
					// 文件名称规则：Icbill+公司PK+单据号+".xls"
					// sFilePath = sFilePathDir;
					ExcelReadCtrl erc = new ExcelReadCtrl();
					erc.setVOToExcel(vos, sFilePathDir);
					// 写状态
					ExcelReadCtrl erc1 = new ExcelReadCtrl(sFilePathDir, true);
					// 写状态
					erc1
							.setExcelFileFlag(nc.ui.ic.pub.barcodeoffline.IExcelFileFlag.F_NEW);
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000120")/*
																			 * @res
																			 * "导出完成"
																			 */);
				}
			} else if (getM_iCurPanel() == BillMode.List) { // 列表
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "请先查询或录入单据。"
																			 */);
					return;
				}

				ArrayList alBill = getClientUI().getSelectedBills();

				if (alBill == null || alBill.size() <= 0
						|| alBill.get(0) == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "请选择要处理的数据！"
															 */);
					return;
				}

				if (iFlag == 3) {
					sBillCode = ((GeneralBillVO) alBill.get(0)).getHeaderVO()
							.getVbillcode();
					onExportXML((GeneralBillVO[]) alBill
							.toArray(new GeneralBillVO[alBill.size()]),
							sFilePathDir);
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000120")/*
																			 * @res
																			 * "导出完成"
																			 */);
					return;
				}

				for (int k = 0; k < alBill.size(); k++) {
					voBill = (GeneralBillVO) alBill.get(k);
					// 得到单据号，公司
					sBillCode = voBill.getParentVO().getAttributeValue(
							"vbillcode") == null ? "" : voBill.getParentVO()
							.getAttributeValue("vbillcode").toString();
					// sPKCorp =
					// voBill.getParentVO().getAttributeValue("pk_corp") == null
					// ? ""
					// : voBill.getParentVO().getAttributeValue("pk_corp")
					// .toString();

					sBillTypeCode = voBill.getBillTypeCode();
					sBillTypeName = GeneralBillVO
							.getBillTypeName(sBillTypeCode);

					// 表体vo
					tvos = (GeneralBillItemVO[]) voBill.getChildrenVO();
					// 需要将条码的信息一起导出
					Vector v = new Vector();
					ExcelFileVO vo = null;

					BarCodeVO[] bvos = null;

					for (int i = 0; i < tvos.length; i++) {

						// if (tvos[i].getBarcodeManagerflag().booleanValue() ==
						// false)
						// continue;
						// if (tvos[i].getBarcodeClose().booleanValue() == true)
						// continue;

						bvos = tvos[i].getBarCodeVOs();
						
						if (null == bvos || 0 == bvos.length)
							continue;
						
						isHaveBC = true;
						
						if (bvos != null && bvos.length > 0) {
							for (int j = 0; j < bvos.length; j++) {
								vo = new ExcelFileVO();
								vo = new ExcelFileVO();
								// 单据号信息
								vo.setAttributeValue("billcode", sBillCode); // 单据号
								// 单据号信息
								vo.setAttributeValue("billcode", sBillCode); // 单据号
								// 单据行信息
								vo.setAttributeValue("rowno", tvos[i]
										.getCrowno()); // 行号
								vo.setAttributeValue("inventorycode", tvos[i]
										.getCinventorycode()); // 存货编码
								vo.setAttributeValue("inventoryname", tvos[i]
										.getInvname()); // 存货名称
								vo.setAttributeValue("billtypecode",
										sBillTypeCode);
								vo.setAttributeValue("billtypename",
										sBillTypeName);

								UFDouble dshouldin = null; // 应收
								UFDouble dshouldout = null; // 应发
								UFDouble dShouldnum = null;

								UFDouble din = null; // 实收
								UFDouble dout = null; // 实发
								UFDouble dnum = null; // 要导出的数量

								dshouldin = tvos[i].getNshouldinnum();
								dshouldout = tvos[i].getNshouldoutnum();
								if (dshouldin == null && dshouldout == null) {
									dShouldnum = null;
								} else {
									dShouldnum = dshouldin == null ? dshouldout
											: dshouldin;
								}

								din = tvos[i].getNinnum();
								dout = tvos[i].getNoutnum();
								if (din == null && dout == null) {
									dnum = null;
								} else {
									dnum = din == null ? dout : din;
								}
								if (dShouldnum != null)
									vo.setAttributeValue("shouldnum",
											dShouldnum.toString()); // 存货数量（实收数量）
								if (dnum != null)
									vo.setAttributeValue("nnum", dnum
											.toString()); // 存货数量（实收数量）
								vo.setAttributeValue("free", tvos[i]
										.getVfree0()); // 自由项目
								vo.setAttributeValue("batchcode", tvos[i]
										.getVbatchcode()); // 批次号
								vo.setAttributeValue("cgeneralbid", tvos[i]
										.getCgeneralbid()); // 表体pk
								vo.setAttributeValue("cgeneralhid", tvos[i]
										.getCgeneralhid()); // 表头pk
								// 表头pk //条码信息
								vo.setBarcode(bvos[j].getVbarcode()); // 主条码
								vo.setBarcodesub(bvos[j].getVbarcodesub()); // 次条码
								vo.setPackcode(bvos[j].getVpackcode()); // 箱条码

								v.add(vo);
							}
						} else {
							vo = new ExcelFileVO();
							vo = new ExcelFileVO();
							// 单据号信息
							vo.setAttributeValue("billcode", sBillCode); // 单据号
							// 单据行信息
							vo.setAttributeValue("rowno", tvos[i].getCrowno()); // 行号
							vo.setAttributeValue("inventorycode", tvos[i]
									.getCinventorycode()); // 存货编码
							vo.setAttributeValue("inventoryname", tvos[i]
									.getInvname()); // 存货名称
							vo.setAttributeValue("billtypecode", sBillTypeCode);
							vo.setAttributeValue("billtypename", sBillTypeName);

							UFDouble dshouldin = null; // 应收
							UFDouble dshouldout = null; // 应发
							UFDouble dShouldnum = null;

							UFDouble din = null; // 实收
							UFDouble dout = null; // 实发
							UFDouble dnum = null; // 要导出的数量

							dshouldin = tvos[i].getNshouldinnum();
							dshouldout = tvos[i].getNshouldoutnum();
							if (dshouldin == null && dshouldout == null) {
								dShouldnum = null;
							} else {
								dShouldnum = dshouldin == null ? dshouldout
										: dshouldin;
							}

							din = tvos[i].getNinnum();
							dout = tvos[i].getNoutnum();
							if (din == null && dout == null) {
								dnum = null;
							} else {
								dnum = din == null ? dout : din;
							}
							if (dShouldnum != null)
								vo.setAttributeValue("shouldnum", dShouldnum
										.toString()); // 存货数量（实收数量）
							if (dnum != null)
								vo.setAttributeValue("nnum", dnum.toString()); // 存货数量（实收数量）
							vo.setAttributeValue("free", tvos[i].getVfree0()); // 自由项目
							vo.setAttributeValue("batchcode", tvos[i]
									.getVbatchcode()); // 批次号
							vo.setAttributeValue("cgeneralbid", tvos[i]
									.getCgeneralbid()); // 表体pk
							vo.setAttributeValue("cgeneralhid", tvos[i]
									.getCgeneralhid()); // 表头pk
							v.add(vo);
						}
					}
					// 每张单据将生成一个文件
					// 已经得到vo，然后导出vo
					vos = new ExcelFileVO[v.size()];
					v.copyInto(vos);
					// 调用导出接口
					// 文件名称规则：Icbill+公司PK+单据号+".xls"
					// sFilePath = sFilePathDir + "\\" + "Icbill" + sBillCode
					// + ".xls";
					ExcelReadCtrl erc = new ExcelReadCtrl();
					erc.setVOToExcel(vos, sFilePathDir);
					// 写状态
					ExcelReadCtrl erc1 = new ExcelReadCtrl(sFilePathDir, true);
					erc1
							.setExcelFileFlag(nc.ui.ic.pub.barcodeoffline.IExcelFileFlag.F_NEW);
				}
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000120")/* @res "导出完成" */);
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000120")/* @res "导出完成" */);
			}
			
			if (!isHaveBC)
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000569")/* @res "请选择有条码的单据" */);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000121")/* @res "导出出错" */);
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000121")/* @res "导出出错" */
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000330")/* @res "：" */
					+ e.getMessage()
					+ ","
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000331")/* @res "文件路径" */
					+ ":" + sFilePathDir);
		}
	}

	/**
	 * 单据导出XML 创建日期：(2003-09-28 9:51:50)
	 */
	private void onExportXML(GeneralBillVO[] billvos, String filename) {
		if (billvos == null || billvos.length <= 0 || filename == null)
			return;
		try {
			MessageDialog
					.showInputDlg(getClientUI(), NCLangRes.getInstance().getStrByID("bill", "bill-000009")/*请输入外部系统编码*/, NCLangRes.getInstance().getStrByID("bill", "bill-000010")/*请输入外部系统编码:*/, "20", 5);

			IPFxxEJBService export = (IPFxxEJBService) NCLocator.getInstance()
					.lookup(IPFxxEJBService.class.getName());
			Document outdocs = export.exportBills(billvos,
					nc.ui.pub.ClientEnvironment.getInstance().getAccount().getAccountCode(),
					nc.ui.pub.ClientEnvironment.getInstance().getCorporation().getPrimaryKey(),
					"IC", "20");

			FileUtils.writeDocToXMLFile(outdocs, filename);
		} catch (Exception e) {
			showErrorMessage(e.getMessage());
		}
	}

	protected void onMergeShow() {
		if (getM_iCurPanel() == BillMode.Card) {
			if (getM_voBill() == null)
				return;
		} else {
			if (getM_alListData() == null || getM_alListData().size() == 0)
				return;
		}

//		CollectSettingDlg dlg = new CollectSettingDlg(getClientUI(), ResBase
//				.getBtnMergeShowName()/* @res "合并显示" */);
    
    CollectSettingDlg dlg = new CollectSettingDlg(getClientUI(), getBillType(),
        getClientUI().getFunctionNode(),getClientUI().getEnvironment().getCorpID(),
        getClientUI().getEnvironment().getUserID(),
        GeneralBillVO.class.getName(),
        GeneralBillHeaderVO.class.getName(),
        GeneralBillItemVO.class.getName());
    
    dlg.setBilltype(getClientUI().getBillTypeCode());
    dlg.setNodecode(getClientUI().getFunctionNode());


		dlg.initData(getBillCardPanel(), new String[] { "cinventorycode",
				"invname", "invspec", "invtype" }, // 固定分组列
				null,// 缺省分组列
				new String[] { "nshouldinnum", "nneedinassistnum",
						"nshouldoutnum", "nshouldoutassistnum", "ninnum",
						"ninassistnum", "noutnum", "noutassistnum",
						"ningrossnum", "noutgrossnum", "nmny", "nplannedmny",
						"ntarenum" },// 求和列
				null,// 求平均列
				null,// 求加权平均列
				null// 数量列
				);
		//dlg.show();
		dlg.showModal();
	}

	/**
	 * 创建人：刘家清 创建日期：2007-12-26上午09:27:52 创建原因：自动设置界面上已有的所有行的行号
	 * 
	 */
	protected void onAddNewRowNo() {
		nc.ui.scm.pub.report.BillRowNo.addNewRowNo(getBillCardPanel(),
				getBillType(), IItemKey.CROWNO);

		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			if (getBillCardPanel().getBillModel().getRowState(i) == BillModel.NORMAL){
				getBillCardPanel().getBillModel().setRowState(i,
						BillModel.MODIFICATION);
        getM_voBill().setItemValue(i, "crowno", getBillCardPanel().getBodyValueAt(i, "crowno"));
      }
		}
	}
	
	public nc.ui.scm.extend.IFuncExtend getM_funcExtend() {
		return getClientUI().getM_funcExtend();
	}
	
	
	public void onMenuItemClick(ActionEvent e) {
		// TODO 自动生成方法存根

		UIMenuItem item = (UIMenuItem) e.getSource();
		// 复制
		if (item == getBillCardPanel().getCopyLineMenuItem()) {

			onCopyLine();
		} else // 粘贴
		if (item == getBillCardPanel().getPasteLineMenuItem()) {

			onPasteLine();
		}
		// 粘贴到表尾时,设置行号
		else if (item == getBillCardPanel().getPasteLineToTailMenuItem()) {
			int iRowCount = getBillCardPanel().getBodyPanel()
					.getTableModel().getRowCount();
			getBillCardPanel().pasteLineToTail();
			// 增加的行数
			int iRowCount1 = getBillCardPanel().getBodyPanel()
					.getTableModel().getRowCount();
			nc.ui.scm.pub.report.BillRowNo.addLineRowNos(
					getBillCardPanel(), getBillType(), IItemKey.CROWNO,
					iRowCount1 - iRowCount);
			getClientUI().voBillPastTailLine();

		} else // 增行
		if (item == getBillCardPanel().getAddLineMenuItem()) {

			onAddLine();

		} else // 删行
		if (item == getBillCardPanel().getDelLineMenuItem())
			onDeleteRow();
		else // 插入行
		if (item == getBillCardPanel().getInsertLineMenuItem()) {
			onInsertLine();
		}

	}
	
	/**
	 * 创建者：王乃军 功能：货位指定
	 * 
	 * 参数： 返回： 例外： 日期：(2003-7-2 19:23:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * modified by liuzy 2008-06-26 3.0功能，5.5重用
	 * 
	 * 
	 */
	protected void onSelLoc() {
		// warehouse id
		String sNewWhID = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
				.getHeadItem(IItemKey.WAREHOUSE).getComponent()).getRefPK();
		if (sNewWhID == null || sNewWhID.trim().length() == 0) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000107")/* @res "请先选择仓库" */);
		} else {
			getClientUI().getLocSelDlg().setWhID(sNewWhID);
			if (getClientUI().getLocSelDlg().showModal() == LocSelDlg.ID_OK) {

				String cspaceid = getClientUI().getLocSelDlg().getLocID();
				String csname = getClientUI().getLocSelDlg().getLocName();
				// 存货列值暂存
				Object oTempValue = null;
				// 表体model
				nc.ui.pub.bill.BillModel bmBill = getBillCardPanel()
						.getBillModel();
				// 存货列号，效率高一些。
				int iInvCol = bmBill.getBodyColByKey(IItemKey.INVID);

				// 必须有存货列
				if (bmBill != null && iInvCol >= 0
						&& iInvCol < bmBill.getColumnCount()) {
					// 行数
					int iRowCount = getBillCardPanel().getRowCount();
					// 从后向前删
					for (int line = 0; line < iRowCount; line++) {
						// 存货填了
						oTempValue = bmBill.getValueAt(line, IItemKey.INVID);
						if (oTempValue != null
								&& oTempValue.toString().trim().length() > 0)
							getClientUI().getEditCtrl().setRowSpaceData(line,
									cspaceid, csname);
					}
				}

			}
		}

	}
  
  
  protected void onLineBatchEdit() {
    
//    if (getBillCardPanel().getBillTable().getSelectedRow() < 0) {
//      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
//          "4008other", "UPP4008other-000515")/* @res "没有选中的表体行。" */);
//      return;
//    }
//    Boolean isSort = getBillCardPanel().getBillTable().isSortEnabled();
//    try{
//      if (isSort)
//        getClientUI().setSortEnable(false);
//      BatchEditDlg dlg = new BatchEditDlg(getBillCardPanel(),
//          new String[]{"scrq","dvalidate",IItemKey.NOUTASTNUM,IItemKey.NOUTNUM,
//                        IItemKey.NINASTNUM,IItemKey.NINNUM,  
//                        IItemKey.NPRICE,IItemKey.NMNY,IItemKey.DBIZDATE,
//                        "vspacename","vvendorname","cprojectname","cprojectphasename"});
//      dlg.showModal();
//    }finally{
//      if(isSort)
//        getClientUI().setSortEnable(true);
//    }
  }
  
  protected void onLineCardEdit() {
      //getBillCardPanel().getBillData().setEnabled(false);
      //getBillCardPanel().getBillModel().setEnabled(true);
      getClientUI().setLineCardEdit(true);
      boolean ise = getBillCardPanel().getBillModel().getRowEditState();
      try{
        boolean isenableaddrow = false;
        if(getButton(ICButtonConst.BTN_LINE_ADD)!=null && getButton(ICButtonConst.BTN_LINE_ADD).isEnabled())
          isenableaddrow = true;
        getBillCardPanel().getBillModel().setRowEditState(!isenableaddrow);
        String[] keys = new String[]{"castunitname"};
        BillItem item = null;
        for(String key : keys){
          item = getBillCardPanel().getBodyItem(key);
          if(item!=null && item.getComponent() instanceof UIRefPane)
            ((UIRefPane)item.getComponent()).setReturnCode(false);
        }
        getBillCardPanel().addActionListener(this);
        getBillCardPanel().startRowCardEdit();
      }finally{
        getBillCardPanel().getBillModel().setRowEditState(ise);
        getClientUI().setLineCardEdit(false);
        getBillCardPanel().addActionListener(this);
      }

  }
  
  protected void onDMStateQuery() {
    GeneralBillVO vo = getClientUI().getCurVO();
    if (vo == null) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "scmcommon", "UPPSCMCommon-000167")/* @res "没有选择单据" */);
      return;
    }
    
    Class dlgclass = null;
    Object dlg=null;
    try{
      dlgclass = Class.forName("nc.ui.dm.service.delivery.SourceBillDeliveryStatusUI");
      dlg = dlgclass.getConstructor(new Class[]{ToftPanel.class}).newInstance(new Object[]{getClientUI()});
    }catch(Throwable e){
      showErrorMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000011")/*运输没有安装*/);
      return;
    }
    
    
    HashMap hsdata = nc.ui.ic.pub.tools.GenMethod.getValues("ic_general_bb3", 
        new String[]{IItemKey.ntotaltrannum,IItemKey.btranendflag}, 
        new int[]{SmartFieldMeta.JAVATYPE_UFDOUBLE,SmartFieldMeta.JAVATYPE_UFBOOLEAN}, 
        IItemKey.CGENERALBID,
        SmartVOUtilExt.getVOsValues(vo.getChildrenVO(), IItemKey.CGENERALBID), null);
    
    SmartVOUtilExt.execFormulas(new String[]{
        "pk_measdoc0->getColValue(bd_invbasdoc,pk_measdoc,pk_invbasdoc, cinvbasid)",
        "invname->getColValue(bd_invbasdoc,invname,pk_invbasdoc, cinvbasid)"}, 
        vo.getItemVOs(), getBillCardPanel().getFormulaParse());
    
    UFDouble zero = new UFDouble(0.0);
    SourceBillDeliveryStatus[] qrydatas = new SourceBillDeliveryStatus[vo.getItemCount()];
    for(int i=0;i<qrydatas.length;i++){
    
      qrydatas[i] = new SourceBillDeliveryStatus();
      qrydatas[i].setCbillid(vo.getHeaderVO().getCgeneralhid());
      qrydatas[i].setCbilltypecode(vo.getHeaderVO().getCbilltypecode());
      qrydatas[i].setPk_corp(vo.getHeaderVO().getPk_corp());
      
      qrydatas[i].setCbill_bid((String)vo.getItemValue(i, IItemKey.CGENERALBID));
      qrydatas[i].setCinvbasid((String)vo.getItemValue(i, IItemKey.INVBASID));
      qrydatas[i].setCrowno((String)vo.getItemValue(i, IItemKey.CROWNO));
      qrydatas[i].setCunitid((String)vo.getItemValue(i, "pk_measdoc0"));
      qrydatas[i].setCunitname((String)vo.getItemValue(i, "measdocname"));
      qrydatas[i].setNnumber((UFDouble)vo.getItemValue(i, "noutnum"));
      qrydatas[i].setCinvname((String)vo.getItemValue(i, "invname"));
      
      if(hsdata!=null && hsdata.size()>0){
          Object[] row = (Object[])hsdata.get(qrydatas[i].getCbill_bid());
          if(row!=null && row.length>0){
            qrydatas[i].setNsendnum((UFDouble)row[0]);
            qrydatas[i].setBsendendflag((UFBoolean)row[1]);
          }
      }
      qrydatas[i].setNcansendnum(
          SmartVOUtilExt.sub(qrydatas[i].getNnumber(), qrydatas[i].getNsendnum()==null?zero:qrydatas[i].getNsendnum())
      );
    }
    
   
    try{
      
      nc.ui.ic.pub.tools.GenMethod.callMethod(dlg, "show", 
          new Class[]{qrydatas.getClass()}, new Object[]{qrydatas});
//      nc.ui.dm.service.delivery.SourceBillDeliveryStatusUI dlg = new  
//        nc.ui.dm.service.delivery.SourceBillDeliveryStatusUI(getClientUI());
//      dlg.show(qrydatas);
    }catch(Throwable e){
      nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null, e);
    }
    
  }
  
  /**
   * 
   * 单据表体菜单动作监听.
   * @param e ufbill.BillEditEvent
   */
  public boolean onEditAction(int action){
    
    boolean isSort = getBillCardPanel().getBillTable().isSortEnabled();
    BillActionListener  bal = getBillCardPanel().getBodyPanel().getBillActionListener(); 
    try {
      if(bal!=null)
        getBillCardPanel().getBodyPanel().removeBillActionListener();
      if (isSort)
        getClientUI().setSortEnable(false);
      getBillCardPanel().getBillModel().setNeedCalculate(false);
    
      switch(action){
        case BillTableLineAction.ADDLINE:
          onAddLine();
          break;
        case BillTableLineAction.INSERTLINE:
          onInsertLine();
          break;  
        case BillTableLineAction.DELLINE:
          onDeleteRow();
          break;
        case BillTableLineAction.COPYLINE:
          onCopyLine();
          break;
        case BillTableLineAction.PASTELINE:
          onPasteLine();
          break;
        case BillTableLineAction.PASTELINETOTAIL:
          onPasteLineToEnd();
          break;
        case BillTableLineAction.EDITLINE:
          onLineCardEdit();
          break;  
        default:
          return true;
      }
    
    } finally {
      if(bal!=null)
        getBillCardPanel().getBodyPanel().addBillActionListener(bal);
      if (isSort)
        getClientUI().setSortEnable(true);
      getBillCardPanel().getBillModel().setNeedCalculate(true);
    }
    
    return false;
  }


}
