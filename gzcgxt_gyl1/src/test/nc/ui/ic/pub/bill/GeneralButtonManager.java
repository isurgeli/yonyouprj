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
 * <b>������Ҫ������¹��ܣ�</b>
 * 
 * <ul>
 *  <li>ά�������ֿ�浥��ButtonTree����
 *  <li>��ť״̬��ά��
 * </ul> 
 *
 * <p>
 * <b>�����ʷ����ѡ����</b>
 * <p>
 * <p>
 * @version 5.3
 * @since 5.3
 * @author duy
 * @time 2007-3-9 ����04:29:04
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

		// �����λ��2003-07-21 ydy
		getClientUI().clearOrientColor();

		showHintMessage(bo.getName());
		// ���˵���<����>
		// û�е��ݲ��յĻ��������ڵ��ݱ༭�˵���������ҵ�����ͺ�
		if (getClientUI().m_bNeedBillRef && getClientUI().isNeedBillRefWithBillType()) {
			if (bo.getParent()== getButtonTree().getButton(
					ICButtonConst.BTN_ADD)) {
				onBizType(bo);
				getBillCardPanel().transferFocusTo(
						nc.ui.pub.bill.BillCardPanel.HEAD);
			} else if (bo.getParent() == getButtonTree().getButton(
					ICButtonConst.BTN_BUSINESS_TYPE)) {
				//SCMEnv.out("��������Ը��٣������趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getClientUI().getBillTypeCode());
				//SCMEnv.out("��������Ը��٣������趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+bo.getCode() + ":"+bo.getName()+":"+bo.getTag());

				
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
		// ������������
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_BOTH_BARCODE))
			onImptBCExcel(2);
		// ������
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_1ST_BARCODE))
			onImptBCExcel(0);
		// �����
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_2ND_BARCODE))
			onImptBCExcel(1);
		// ������Դ��������
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_IMPORT_SOURCE_BARCODE))
			onImportBarcodeSourceBill();
		// ����ر�
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
		//modified by liuzy 2008-06-26 V3.3�Ļ�λѡ���ܣ�V5.5����
		 else if (bo == getButtonTree().getButton(ICButtonConst.BTN_SEL_LOC))
			 onSelLoc();
		// ��Ӧ�����գ��Զ���дʵ�����գ�
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_LINE_AUTO_FILL))
			onFillNum();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO))
			onPickAuto();
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_ASSIST_FUNC_REFER_IN))
			onRefInICBill();
		// ���ݵ���
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXPORT_TO_DIRECTORY))
			onBillExcel(1);// ������ָ��Ŀ¼
		else if (bo == getButtonTree().getButton(
				ICButtonConst.BTN_EXPORT_TO_XML))// ����XML
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
			// ֧�ֹ�����չ
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
		  // TODO �Զ����ɷ������
		  if (getClientUI().m_voaBillItem == null || getClientUI().m_voaBillItem.length <= 0)
		    return;
			
			int iRowCount = getBillCardPanel().getBodyPanel().getTableModel()
			.getRowCount();
			getBillCardPanel().pasteLineToTail();
			// ���ӵ�����
			int iRowCount1 = getBillCardPanel().getBodyPanel().getTableModel()
					.getRowCount();
			nc.ui.scm.pub.report.BillRowNo.addLineRowNos(getBillCardPanel(),
					getBillType(), IItemKey.CROWNO, iRowCount1 - iRowCount);
			getClientUI().voBillPastTailLine();

			GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
					getBillCardPanel(), getBillType());
		
	}

	public void setButtonStatus(boolean bUpdateButtons) {
		// TODO �Զ����ɷ������
		getClientUI().setButtonStatus(bUpdateButtons);
	}

	/**
	 * �����ߣ����� 
	 * ���ܣ�ѡ����ҵ������ 
	 * ������ 
	 * ���أ� 
	 * ���⣺ 
	 * ���ڣ�(2001-5-9 9:23:32) 
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 */
	protected void onBizType(ButtonObject bo) {
		try {
			// ��ť�¼�����
      nc.ui.ic.pub.pf.ICSourceRefBaseDlg.childButtonClicked(bo,
					getEnvironment().getCorpID(), getClientUI()
							.getFunctionNode(), getEnvironment()
							.getUserID(), getBillType(),
					getClientUI());

			// �õ��û�ѡ���ҵ�����͵ķ���. 20021209
			String sBusiTypeID = getClientUI().getSelBusiType();

			if (!nc.ui.ic.pub.pf.ICSourceRefBaseDlg.makeFlag()) {
				// �����Ʒ�ʽ
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
					SCMEnv.showTime(lTime, "��ò���VO:"/*-=notranslate=-*/);
					// modified by liuzy 2009-8-21 ����10:33:20 �޸�����������������Ϣ�ĵ�GeneralBillClientUI.transBillDataDeal
//					GeneralBillUICtl.procOrdEndAfterRefAdd(
//							(GeneralBillItemVO[]) SmartVOUtilExt
//									.getBodyVOs(vos), getBillCardPanel(), getBillType());

					if (vos != null && vos.length > 0)
						// ��鵥���Ƿ���Դ���µĲ��ս���
						if (!ICConst.IsFromNewRef.equals(vos[0].getParentVO()
								.getAttributeValue(ICConst.IsFromNewRef))) {
							// �����Ĭ�Ϸ�ʽ�ֵ�
							vos = GenMethod.splitGeneralBillVOs(
									(GeneralBillVO[]) vos,
									getBillType(), getBillListPanel()
											.getHeadBillModel()
											.getFormulaParse());
							// ���������ݵĵ�λת��Ϊ���Ĭ�ϵ�λ.
							GenMethod.convertICAssistNumAtUI(
									(GeneralBillVO[]) vos, nc.ui.ic.pub.tools.GenMethod.getIntance());
						}
					//added by lirr 2009-02-21 �޸�ԭ�򣺷�IsFromNewRef��ת����Ҫ����nshouldoutassistnum����
						else{
								for(GeneralBillVO vo : (GeneralBillVO[])vos){
									GeneralBillItemVO[] item = vo.getItemVOs();

									ScaleKey sk = new ScaleKey();
									sk.setAssistNumKeys(new String[] { "nshouldoutassistnum" });

									GenMethod.setScale(item, sk, getClientUI().m_ScaleValue);
								}
							

						}//end added by lirr

					// v5 lj ֧�ֶ��ŵ��ݲ�������
					if (vos != null && vos.length == 1) {
						getClientUI().setRefBillsFlag(false);
						getClientUI().setBillRefResultVO(sBusiTypeID, vos);
					} else {
						getClientUI().setRefBillsFlag(true);// �ǲ������ɶ���
						getClientUI().setBillRefMultiVOs(sBusiTypeID, (GeneralBillVO[]) vos);
					}
					// end v5

				}
			} else {
				// ���Ƶ���
				getClientUI().setRefBillsFlag(false);
				getClientUI().onAdd(true, null);
				// ���õ��ݱ����еĴ�����չ��˺Ͳ��յ�TextField�Ŀɱ༭���
				nc.ui.pub.bill.BillItem bi = bi = getBillCardPanel()
						.getBodyItem("cinventorycode");
				RefFilter.filtInv(bi, getEnvironment().getCorpID(), null);
				// set user selected ҵ������ 20021209
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
				// Ĭ������£��˿�״̬�������� add by hanwei 2003-10-19
				nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(getClientUI(),
						false);

			}

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showErrorMessage(e.getMessage());
		}
	}

	/**
	 * �����ߣ����˾� ���ܣ��������� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onAdd() {
		// ��ǰ���б���ʽʱ�������л�������ʽ
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
//  ���ο�����չ
    try{
      getClientUI().getPluginProxy().onAddLine();
    }catch(Exception e){
      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
      return ;
    }
	}

	/**
	 * �����ߣ����˾� ���ܣ�����ʽ��ɾ���д��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	protected void onDeleteRow() {
		nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel(),getBillCardPanel().getBillTable().getSelectedRow());
		
		int[] selrow = getBillCardPanel().getBillTable().getSelectedRows();
		int length = selrow.length;
		SCMEnv.out("count is " + length);
		int iCurLine = 0; // ��ǰѡ����

		// ɾ���������� add by hanwei
		if (getM_voBill() != null && selrow != null) {
			for (int i = 0; i < selrow.length; i++) {
				if (getM_voBill().getItemBarcodeVO(selrow[i]) != null) {
					getClientUI().getM_utfBarCode().setRemoveBarcode(getM_voBill()
							.getItemBarcodeVO(selrow[i]));
				}
			}
		}

		if (length == 0) { // ûѡ��һ�У�����
			return;
		} else if (length == 1) { // ɾ��һ��
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
		} else { // ɾ������
			getBillCardPanel().getBillModel().delLine(selrow);

			int iRowCount = getBillCardPanel().getRowCount();
			if (iRowCount > 0) {
				getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
				iCurLine = 0;
			}
		}
		// �������к��Ƿ����
		getClientUI().setBtnStatusSN(iCurLine, true);
		GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
				getBillCardPanel(), getBillType());
		// ɾ���������ݣ�����
    
	}

	protected void onCopyLine() {
		getBillCardPanel().copyLine();
		getClientUI().voBillCopyLine();
	}

	/**
	 * �����ߣ����˾� ���ܣ���/�б���ʽ�л� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * v5: ֧���б��µĶ��ŵ��ݲ�������ʱ���޸İ�ť��˫����ͷ������onSwitch�� ����Ϊ��������
	 * 
	 * 
	 */
	protected void onSwitch() {

		if (BillMode.List == getM_iCurPanel()) {
			// �б�---�����л�
			setM_iCurPanel(BillMode.Card);

			// ����������Ϊ��
			getClientUI().m_sLastKey = null;

			getButtonTree().getButton(ICButtonConst.BTN_SWITCH).setHint(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000068")/* @res "�л����б���ʽ" */);
			getClientUI().updateButton(getButtonTree().getButton(ICButtonConst.BTN_SWITCH));

			// ���б�---�����л�ʱ��δ��ѡ�������ݣ���������������ݣ������Ч�ʡ�
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

				// v5 lj ֧�ֲ������ɶ൥��
				if (getClientUI().m_bRefBills == true) {

					try {
						//�޸��ˣ������� �޸�ʱ�䣺2008-12-16 ����02:01:36 �޸�ԭ�򣺵��������ȵ��ݣ�����һ�λ����ɶ�ҵ�����͵ĵ��ݣ����Դ���ʱҪ���ǰ�VO��ҵ���������ø����档
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

				// ��ǰ�������
				getClientUI().m_iCurDispBillNum = getM_iLastSelListHeadRow();
				// ��ǰ������
				getClientUI().m_iBillQty = getM_alListData().size();

				getClientUI().setCardMode();
			}
		} else {
			// ��--->�б��л�
			setM_iCurPanel(BillMode.List);
			// ��ǰ�������
			getClientUI().m_iCurDispBillNum = getM_iLastSelListHeadRow();

			getClientUI().selectListBill(getM_iLastSelListHeadRow());

			getButtonTree().getButton(ICButtonConst.BTN_SWITCH).setHint(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000312")/* @res "�л�������ʽ" */);
			getClientUI().updateButton(getButtonTree().getButton(ICButtonConst.BTN_SWITCH));

			// v5 lj
			if (getClientUI().m_bRefBills == true) {
				setM_iMode(BillMode.Browse);// ����Ϊ���״̬
				// �����ǰ�б��µĵ���Ϊ0�������ɾ���ģ������޸�m_bRefBills=false
				if (getM_alListData() == null || getM_alListData().size() == 0) {
					getClientUI().setRefBillsFlag(false);
				}
			}
		}

		getClientUI().showBtnSwitch();

		// ������ʾ
		getClientUI().getM_layoutManager().show();

		setButtonStatus(false);
		// �����Դ���ݲ�Ϊ�գ�����ҵ�����͵Ȳ�����
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

			// ���ӵ�����
			iRowCount = getBillCardPanel().getBodyPanel().getTableModel()
					.getRowCount()
					- iRowCount;
			nc.ui.scm.pub.report.BillRowNo.pasteLineRowNo(getBillCardPanel(),
					getBillType(), IItemKey.CROWNO, iRowCount);
			//
			getClientUI().voBillPastLine();
			GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
					getBillCardPanel(), getBillType());
      
//    ���ο�����չ
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
      
//    ���ο�����չ
      try{
        getClientUI().getPluginProxy().onAddLine();
      }catch(Exception e){
        nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
        return ;
      }

	}

	protected void onDelete() {
		getClientUI().m_timer.start("ɾ����ʼ"/*-=notranslate=-*/);

		nc.vo.ic.pub.bill.Timer t = new nc.vo.ic.pub.bill.Timer();
		t.start();

		int iSelListHeadRowCount = getBillListPanel().getHeadTable()
				.getSelectedRowCount();
		
		if (iSelListHeadRowCount <= 0) {	
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UCH003")/* @res "��ѡ��Ҫ��������ݣ�" */);
			return;
		}

		switch (nc.ui.pub.beans.MessageDialog.showYesNoDlg(getClientUI(), null,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH002")/*
																				 * @res
																				 * "�Ƿ�ȷ��Ҫɾ����"
																				 */
				, MessageDialog.ID_NO)) {

		case nc.ui.pub.beans.MessageDialog.ID_YES:
			break;
		default:
			return;
		}
		// ���б��£����ŵ���ɾ��;�ڱ���ʽ��,ֻ��ÿ��ɾ��һ�ŵ���,��Ϊ�����б���ѡ�еĵ��ݱ���
		// ͬ�������Կ���ͳһ����
		ArrayList alvo = getClientUI().getSelectedBills();
		GeneralBillVO voaDeleteBill[] = new GeneralBillVO[alvo.size()];

		// ����Ա��־
		nc.vo.sm.log.OperatelogVO log = getClientUI().getNormalOperateLog();

		for (int i = 0; i < alvo.size(); i++) {
			voaDeleteBill[i] = (GeneralBillVO) alvo.get(i);
			// ��ǰ����Ա2002-04-10.wnj
			voaDeleteBill[i].getHeaderVO().setCoperatoridnow(
					getEnvironment().getUserID());
			voaDeleteBill[i].getHeaderVO().setAttributeValue("clogdatenow",
					getEnvironment().getLogDate());
			// ���ڡ�������Ϣ
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
    
  //  ���ο�����չ
      getClientUI().getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.DELETE, voaDeleteBill);
      
  		onDeleteKernel(voaDeleteBill);
      
  		if (getClientUI().isM_bOnhandShowHidden()) {
  			getClientUI().m_pnlQueryOnHand.clearCache();
  			getClientUI().m_pnlQueryOnHand.fresh();
  		}
      
//    ���ο�����չ
      getClientUI().getPluginProxy().afterAction(nc.vo.scm.plugin.Action.DELETE, voaDeleteBill);
    
    }catch(Exception e){
      nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null, e);
    }

	}

	/**
	 * �����ߣ����˾� ���ܣ�ɾ������ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * 
	 * 
	 */
	private void onDeleteKernel(GeneralBillVO[] voaDeleteBill) {
		getClientUI().m_timer.start("ɾ����ʼ"/*-=notranslate=-*/);

		nc.vo.ic.pub.bill.Timer t = new nc.vo.ic.pub.bill.Timer();
		t.start();

		try {
			int iSelListHeadRowCount = getBillListPanel().getHeadTable()
					.getSelectedRowCount();

			int[] arySelListHeadRows = new int[iSelListHeadRowCount];
			arySelListHeadRows = getBillListPanel().getHeadTable()
					.getSelectedRows();

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH051")/* @res "����ɾ��" */);

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
																			 * ".\nȨ��У�鲻ͨ��,����ʧ��! "
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
																		 * "Ȩ��У�鲻ͨ��,����ʧ��. "
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
																	 * "Ȩ��У�鲻ͨ��,����ʧ��. "
																	 */);

						}
					} else if (realbe != null
							&& realbe.getClass() == CreditNotEnoughException.class) {
						// ������Ϣ��ʾ����ѯ���û����Ƿ��������
						int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n�Ƿ������*/);
						// ����û�ѡ�����
						if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
							for (int i = 0; i < voaDeleteBill.length; i++)
								voaDeleteBill[i].setIsCheckCredit(false);
							continue;
						} else
							return;
					} else if (realbe != null
							&& realbe.getClass() == PeriodNotEnoughException.class) {
						// ������Ϣ��ʾ����ѯ���û����Ƿ��������
						int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n�Ƿ������*/);
						// ����û�ѡ�����
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
							// ������Ϣ��ʾ����ѯ���û����Ƿ��������
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{atpe.getMessage()})/*{0} \r\n�Ƿ������*/);
							// ����û�ѡ�����
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

			getClientUI().m_timer.showExecuteTime("ƽ̨ɾ��"/*-=notranslate=-*/);
			// showTime(lTime, "ɾ��");

			// ɾ�����б���洦��,����m_iLastSelListHeadRow��
			getClientUI().removeBillsOfList(arySelListHeadRows);
			getClientUI().m_timer.showExecuteTime("removeBillsOfList");
			// �ڱ���ʽ��ɾ������ʾ���б���Ӧ�ĵ���
			if (BillMode.Card == getM_iCurPanel()
					&& getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()) {
				setBillVO((GeneralBillVO) getM_alListData().get(
						getM_iLastSelListHeadRow()));
				// ִ�й�ʽ
				// getBillCardPanel().getBillModel().execLoadFormula();
				// getBillCardPanel().updateValue();
			} else if (getM_alListData() == null
					|| getM_alListData().size() == 0) {
				getBillCardPanel().getBillModel().clearBodyData();
				getBillCardPanel().updateValue();
			}
			// ��յ�ǰ�Ļ�λ����
			getClientUI().setM_alLocatorData(null);
			getClientUI().m_alLocatorDataBackup = null;

			// ��յ�ǰ�����к�����
			getClientUI().setM_alSerialData(null);
			getClientUI().m_alSerialDataBackup = null;
			getClientUI().m_timer.showExecuteTime("Before ���谴ť״̬"/*-=notranslate=-*/);
			// ���谴ť״̬
			setButtonStatus(false);
			getClientUI().m_timer.showExecuteTime("���谴ť״̬"/*-=notranslate=-*/);
			// �����Ƿ�Ϊ��Դ���ݿ��Ƶ��ݽ���
			getClientUI().ctrlSourceBillUI(true);
			getClientUI().m_timer.showExecuteTime("��Դ���ݿ��Ƶ��ݽ���"/*-=notranslate=-*/);

			// delete the excel barcode file
			OffLineCtrl ctrl = new OffLineCtrl(getClientUI());
			ctrl.deleteExcelFile(voaDeleteBill, getEnvironment().getCorpID());

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"40080604", "UPT4020402001-000006")/* @res "ɾ���ɹ�" */);

		} catch (Exception e) {
			e = nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
			handleException(e);
			showWarningMessage(e.getMessage());
		}
		t.stopAndShow("ɾ���ϼ�"/*-=notranslate=-*/);
	}

	/**
	 * 
	 * ����������������ѯ��ˢ�°�ť�ķ�����
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ��ѯ��ť��onQuery(true);
	 * <p>
	 * ˢ�°�ť��onQuery(false);
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @param bQuery
	 *            ����ǽ��в�ѯ��Ϊtrue������ǽ���ˢ�£�Ϊfalse
	 *            <p>
	 * @author duy
	 * @time 2007-3-2 ����03:07:52
	 */
	protected void onQuery(boolean bQuery) {
		// Ϊ�˼�����ǰ�İ汾�������˳�Ա�������洢�ǽ��в�ѯ���ǽ���ˢ��
		getClientUI().m_bQuery = bQuery;

		// ִ�в�ѯ��ˢ�£����̣��ڸ÷����н����˲�ѯ��ˢ�µķֱ��� 
		onQuery();
	}

	protected void onQuery() {
		// DUYONG ���Ӳ�ѯ��ˢ�µĴ���
		try {
      
      boolean isFrash = !(getClientUI().isBQuery() || !getClientUI().getQryDlgHelp().isBEverQry());
      Object[] ret = getClientUI().getQryDlgHelp().queryData(isFrash);
      if(ret==null || ret[0]==null || !((UFBoolean)ret[0]).booleanValue())
        return;
      ArrayList alListData = (ArrayList)ret[1];
      if(!isFrash){
        //ˢ�°�ť ��������ѯ����
        setButtonStatus(true);
      }
      int cardOrList = getM_iCurPanel();
      
//			nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
//			timer.start("@@��ѯ��ʼ��");
//			getClientUI().m_sBnoutnumnull = null;
//			QryConditionVO voCond = null;
//
//			// ��ԭ�������ͣ���Ƭ/�б���¼����������ڿ�Ƭ����ִ����ˢ�£��������л����б����
//			int cardOrList = getM_iCurPanel();
//			// �����[(1)���в�ѯ(2)����û�н��в�ѯ�����ǵ����ˢ�°�ť]������ʾ��ѯģ����в�ѯ
//			// ���������ѯ�������ҵ����ˢ�°�ť���������˶δ���
//			if (getClientUI().m_bQuery || !getClientUI().m_bEverQry) {
//				getClientUI().getConditionDlg().showModal();
//				timer.showExecuteTime("@@getConditionDlg().showModal()��");
//
//				if (getClientUI().getConditionDlg().getResult() != nc.ui.pub.beans.UIDialog.ID_OK)
//					// ȡ������
//					return;
//
//				// ���qrycontionVO�Ĺ���
//				voCond = getClientUI().getQryConditionVO();
//
//				// ��¼��ѯ��������
//				getClientUI().m_voLastQryCond = voCond;
//
//				// ����ǽ��в�ѯ���򽫡�������ѯ�����ı�ʶ���ó�true�����������ܹ����С�ˢ�¡��Ĳ�����
//				getClientUI().m_bEverQry = true;
//				// ˢ�°�ť
//				setButtonStatus(true);
//			} else
//				voCond = getClientUI().m_voLastQryCond;
//			
//			//addied by liuzy 2008-03-31 �����������ڡ��ӡ����������ֶ���
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
//			// DUYONG �˴�Ӧ����ˢ�°�ť���ܵĿ�ʼִ�д�
//
//			// ���ʹ�ù�ʽ����봫voaCond ����̨. �޸� zhangxing 2003-03-05
//			//nc.vo.pub.query.ConditionVO[] voaCond = getClientUI().getConditionDlg()
//			//		.getConditionVO();
//			
//			//addied by liuzy 2008-03-28 V5.03���󣺵��ݲ�ѯ������ֹ����
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
//				// �Ƿ����ʵ������
//				voCond.setParam(33, getClientUI().m_sBnoutnumnull);
//			}
//
//			voCond.setParam(QryConditionVO.QRY_LOGCORPID, getClientUI()
//					.getEnvironment().getCorpID());
//			voCond.setParam(QryConditionVO.QRY_LOGUSERID, getClientUI()
//					.getEnvironment().getUserID());
//
//			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
//					"4008bill", "UPP4008bill-000012")/* @res "���ڲ�ѯ�����Ժ�..." */);
//			timer.showExecuteTime("Before ��ѯ����");
//
//			ArrayList alListData = (ArrayList) GeneralBillHelper.queryBills(
//					getBillType(), voCond);
//
//			timer.showExecuteTime("��ѯʱ�䣺");

			getClientUI().setDataOnList(alListData, true);

			// DUYONG ��ִ��ˢ�²��������ҵ�ǰ����Ϊ��Ƭ����ʱ����Ӧ���л����б����͵Ľ�����
			if (!getClientUI().isBQuery()
					&& getM_iCurPanel() != cardOrList) {
				onSwitch();
			}
		} catch (Exception e) {
			handleException(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000014")/* @res "��ѯ����" */);
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000015")/* @res "��ѯ����" */
					+ e.getMessage());
		}
	}

	/**
	 * �˴����뷽��˵���� ��������:���ݱ����еĵ���ID�͵����������������ε��ݡ� ���ߣ������� �������: ����ֵ: �쳣����:
	 * ����:(2003-4-22 16:09:14)
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
			// �õ�����VO
			voBill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
			// �õ����ݱ�ͷVO
			voHeader = voBill.getHeaderVO();

			if (voHeader == null) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000098")/* @res "û��Ҫ����ĵ��ݣ�" */);
				return;
			}
			String sBillPK = null;
			String sBillTypeCode = null;
			String sBillCode = null;

			sBillPK = voHeader.getCgeneralhid();
			sBillTypeCode = voHeader.getCbilltypecode();
			sBillCode = voHeader.getVbillcode();
			// ���sBillPK��sBillTypeCodeΪ�գ�����û�����塣
			if (sBillPK == null || sBillTypeCode == null) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000099")/* @res "����û�ж�Ӧ���ݣ�" */);
				return;
			}
			nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(
					getClientUI(), sBillTypeCode, sBillPK, null, getEnvironment().getUserID(),
					/*getEnvironment().getCorpID()*/sBillCode);
			soureDlg.showModal();
		} else {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000154")/* @res "û������ĵ��ݣ�" */);
		}
	}

	/**
	 * �����ߣ����˾� ���ܣ��޸Ĵ��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
				"UCH027")/* @res "�����޸�" */);
		// �����кż���λ--�����Ҫ�Ļ���
		getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);
		GeneralBillVO voMyBill = null;
		// arraylist �еĻ�������,û�л�,�����µ�.
		if (getM_alListData() != null
				&& getM_alListData().size() > getM_iLastSelListHeadRow())
			voMyBill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
		// ��Ҫ��ԭ�������ݵ�clone�������ڱ༭�����У���ͬ���޸�ԭ�������ݡ�
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

		// �жϵ����Ƿ��Ǵ�U8�����д���ĵ��ݣ�����ǣ�������༭
		java.util.ArrayList<GeneralBillVO> alBills = new java.util.ArrayList<GeneralBillVO>(
				1);
		alBills.add(voMyBill);

		String billCodes = getClientUI().getU8RMBillCodes(alBills);
		if (billCodes != null && billCodes.length() > 0) {
			String message1 = nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008U8RM-000001", null,
					new String[] { billCodes });
			/*
			 * @res "���ݺ���{0}�ĵ�������U8�����ϴ����Զ����ɵģ�����ִ�иò�����"
			 */
			showWarningMessage("(" + message1 + ")");
			return;
		}

		// ��ǰ���б���ʽʱ�������л�������ʽ
		if (BillMode.List == getM_iCurPanel())
			onSwitch();

		// ��ǰ����ʾ���ݣ�����Ϊ�ɱ༭��ʽ���޵�����ʾʱ�˰�ť�����á�
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
		// ���浱ǰ�Ļ�λ���ݣ��Է�ȡ�������� useless in fact
		getClientUI().m_alLocatorDataBackup = getClientUI().getM_alLocatorData();
		// ���浱ǰ�����к����ݣ��Է�ȡ�������� useless in fact
		getClientUI().m_alSerialDataBackup = getClientUI().getM_alSerialData();
		// �����Դ���ݲ�Ϊ�գ�����ҵ�����͵Ȳ�����
		//�޸��ˣ�������  �޸�ʱ�䣺2009-11-5 ����03:37:19 �޸�ԭ���޸�ʱ���޸Ĳֿ⣬һ��Ҫ�ڿ���������Դ���ݲֿⲻ���޸�֮��
		getClientUI().freshWHEditable();

		getClientUI().ctrlSourceBillUI(false);
		// �޸�״̬ʱ���ֿⲻ���޸ġ�
		//�޸��ˣ������� �޸�ʱ�䣺2008-7-14 ����03:38:48 �޸�ԭ�򣺲ֿ��ڵ����޸�ʱ����ͨ�����Խ����޸ġ�
		/*if (getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE) != null)
			getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE)
					.setEnabled(false);*/
		if (getBillCardPanel().getHeadItem(IItemKey.WASTEWAREHOUSE) != null)
			getBillCardPanel().getHeadItem(IItemKey.WASTEWAREHOUSE).setEnabled(
					false);
		

		// �޸�״̬ʱ�������֯�����޸ġ�
		if (getBillCardPanel().getHeadItem(IItemKey.CALBODY) != null)
			getBillCardPanel().getHeadItem(IItemKey.CALBODY).setEnabled(false);
		if (getBillCardPanel().getHeadItem(IItemKey.WASTECALBODY) != null)
			getBillCardPanel().getHeadItem(IItemKey.WASTECALBODY).setEnabled(
					false);
//		�޸��ˣ������� �޸�ʱ�䣺2008-7-14 ����03:38:48 �޸�ԭ�򣺵��ݺ��ڵ����޸�ʱ����ͨ�������ⵥ�����Խ����޸ġ�
		if (!getClientUI().m_bIsEditBillCode && getBillCardPanel().getHeadItem("vbillcode") != null)
			getBillCardPanel().getHeadItem("vbillcode").setEnabled(false);

		// ���°�Ŧ
		getClientUI().updateButtons();

		// ���ݱ�ͷ�˿��־��ȷ���˿�״̬���͵������˿�UI add by hanwei 2003-10-19
		nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(getClientUI());
		// Ĭ�ϲ��ǵ������� add by hanwei 2003-10-30
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
																				 * "�Ƿ�ȷ��Ҫȡ����"
																				 */
				, MessageDialog.ID_NO)) {

		case nc.ui.pub.beans.MessageDialog.ID_YES:
			// songhy, 2009-11-16��start���������ʱ���ε����ߵ��������ظ�������
			getClientUI().clearOID();
			// songhy, 2009-11-16��end.
			break;
		default:
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH045")/* @res "����ȡ��" */);
		if (-1 < getM_iLastSelListHeadRow() && null != getM_alListData()
				.get(getM_iLastSelListHeadRow()))
			setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
					.get(getM_iLastSelListHeadRow())).clone());
		
		nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel());
		
		GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);

		// v5 lj ֧�ֲ������ɶ��ŵ���
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
			// �ָ���λ����
			getClientUI().setM_alLocatorData(getClientUI().m_alLocatorDataBackup);
			// �屸������
			getClientUI().m_alLocatorDataBackup = null;

			// �ָ����к�����
			getClientUI().setM_alSerialData(getClientUI().m_alSerialDataBackup);
			// �����кű�������
			getClientUI().m_alSerialDataBackup = null;

			// �ָ�billvo
			if (getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()
					&& getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());
				setBillVO(getM_voBill());
				// resumeValue�ָ�����ˢ�±�β��ʾ
				getClientUI().setTailValue(0);
			} else {
				// ������������棡
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
			// �����Ƿ�Ϊ��Դ���ݿ��Ƶ��ݽ���
			getClientUI().ctrlSourceBillUI(true);

		}
		// �޸��ˣ������� �޸����ڣ�2007-8-13����04:51:06
		// �޸�ԭ�򣺽����������,�����˻�����ⵥ��¼�����������Ϊ���ģ�Ȼ����������ʱ��¼�����룬���ӵ�����Ҳ��Ϊ���ġ�
		getClientUI().m_bFixBarcodeNegative = false;// ��������Ϊ����
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH008")/*
							 * @res "ȡ���ɹ�"
							 */);
	}

	/**
	 * �����ߣ����˾� ���ܣ�ȡ������ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onCancelAudit() {
		try {
			if (getClientUI().m_bRefBills) {
				showHintMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000003")/*����״̬,����ȡ��ǩ��*/);
				return;
			}
			switch (nc.ui.pub.beans.MessageDialog.showYesNoDlg(getClientUI(), null,
					ResBase.getIsCancleSign()/* @res "�Ƿ�ȷ��Ҫȡ��ǩ�֣�" */
					, MessageDialog.ID_NO)) {

			case nc.ui.pub.beans.MessageDialog.ID_YES:
				break;
			default:
				return;
			}
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000006")/*
														 * @res "����ȡ��ǩ�֣����Ժ�..."
														 */);

			if (BillMode.List == getM_iCurPanel()
					&& getBillListPanel().getHeadTable().getSelectedRowCount() > 1) {
				getClientUI().onBatchAction(ICConst.CANCELSIGN);
				return;
			}
			// ����m_voBill,�Զ�ȡ�������ݡ�
			if (getM_iLastSelListHeadRow() >= 0
					&& getM_alListData() != null
					&& getM_alListData().size() > getM_iLastSelListHeadRow()
					&& getM_alListData().get(getM_iLastSelListHeadRow()) != null)
				// ���ﲻ��clone(),�޸�m_voBillͬʱ�޸�list.
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());
			if (getM_voBill() != null) {
				// ��鵥���Ƿ����ȡ��ǩ����������������ϣ�������ʾ
				String message = getClientUI().checkBillForCancelAudit(getM_voBill());
				if (message != null && message.length() > 0) {
					showWarningMessage(message);
					return;
				}

				// ��������״̬δû�б��޸�
        GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
				// ֧��ƽ̨��cloneһ�����Ա����Ժ�Ĵ���ͬʱ��ֹ�޸���m_voBill
				GeneralBillVO voAudit = (GeneralBillVO) getM_voBill().clone();
				voAudit = getClientUI().getCancelAuditVO(voAudit);
				long lTime = System.currentTimeMillis();
				// ����ֵ
				ArrayList alRet = null;
				// GeneralBillVO voAuditnew = (GeneralBillVO) voAudit.clone();
				GeneralBillItemVO[] itemvos = voAudit.getItemVOs();
//		      ���ο�����չ
		        getClientUI().getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.UNAUDIT, new GeneralBillVO[]{voAudit});
				while (true) {
					try {

						// add by liuzy 2007-11-02 10:16 ѹ������
						// ObjectUtils.objectReference(voAudit);
						// voAudit = (GeneralBillVO) voAudit.clone();
						voAudit.setTransNotFullVo();
						// ִ��ȡ��ǩ��
						alRet = (ArrayList) nc.ui.pub.pf.PfUtilClient
								.processAction("CANCELSIGN", getBillType(),
										getEnvironment().getLogDate(), voAudit);
						break;

					} catch (Exception ee1) {

						BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
								.handleException(null, null, ee1);
						if (realbe != null
								&& realbe.getClass() == CreditNotEnoughException.class) {
							// ������Ϣ��ʾ����ѯ���û����Ƿ��������
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n�Ƿ������*/);
							// ����û�ѡ�����
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setIsCheckCredit(false);
								continue;
							} else
								return;
						} else if (realbe != null
								&& realbe.getClass() == PeriodNotEnoughException.class) {
							// ������Ϣ��ʾ����ѯ���û����Ƿ��������
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n�Ƿ������*/);
							// ����û�ѡ�����
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
								// ������Ϣ��ʾ����ѯ���û����Ƿ��������
								int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{atpe.getMessage()})/*{0} \r\n�Ƿ������*/);
								// ����û�ѡ�����
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

				getClientUI().showTime(lTime, "ȡ��ǩ��"/*-=notranslate=-*/);
				// ���ش���
				String sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000309")/* @res "ȡ��ǩ�ֳ���" */;

				// ---- old ------����Ǳ��漴ǩ����Ҫˢ�½���,��Ȼ��������ʱ�Ǳ�����ڱ���ʱ����ˡ�
				// ---- old ------ֻҪ�ܱ��棬˵�������Ѿ���ȷ¼���ˡ�
				// -- new ---��Ϊ��ƽ̨��������������ִ����󣬻�Ҫ�ض��Ƿ�ǩ���ˡ�
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
																 * "ȡ��ǩ�ֳɹ���"
																 */;
				}

				// ȡ��ǩ�ֺ�Ҫ�����з���Դ���ݶԲ˵�ɾ����ť����
				getClientUI().ctrlSourceBillButtons(true);
//		      ���ο�����չ
		        getClientUI().getPluginProxy().afterAction(nc.vo.scm.plugin.Action.UNAUDIT, new GeneralBillVO[]{voAudit});
		        
				showHintMessage(sMsg);
			}
		} catch (Exception e) {
			e = nc.vo.ic.pub.GenMethod
			.getRealBusiException(e);
			handleException(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000007")/* @res "ȡ��ǩ�ֳ���" */);
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000008")/* @res "ȡ��ǩ�ֳ���:" */
					+ e.getMessage());
		}

	}

	/**
	 * �����ߣ����˾� ���ܣ�ǩ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
	 * 
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 */
	protected void onAudit() {

		getClientUI().m_timer.start("ǩ�ֿ�ʼ��"/*-=notranslate=-*/);
		try {
			if (getClientUI().m_bRefBills) {
				showHintMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000004")/*����״̬,����ǩ��*/);
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
				// ���ﲻ��clone(),�޸�m_voBillͬʱ�޸�list.
				setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData()
						.get(getM_iLastSelListHeadRow())).clone());

			if (getM_voBill() != null) {
				
				/*
				 * UFTime ufdPre1 = new UFTime(System.currentTimeMillis());//
				 * ϵͳ��ǰʱ�� UFDateTime ufdPre = new UFDateTime(m_sLogDate + " " +
				 * ufdPre1.toString());
				 */

				// ��������״̬δû�б��޸�
        GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
				// ֧��ƽ̨��cloneһ�����Ա����Ժ�Ĵ���ͬʱ��ֹ�޸���m_voBill
				GeneralBillVO voAudit = (GeneralBillVO) getM_voBill().clone();
				voAudit = getClientUI().getAuditVO(voAudit, null);

				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000003")/*
															 * @res
															 * "����ǩ�֣����Ժ�..."
															 */);
				getClientUI().m_timer.showExecuteTime("before ƽ̨ǩ�֣�"/*-=notranslate=-*/);
//      ���ο�����չ
        getClientUI().getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.AUDIT, new GeneralBillVO[]{voAudit});

				while (true) {
					try {

						// ��˵ĺ��ķ���
						getClientUI().onAuditKernel(voAudit);
						break;

					} 
					catch (RightcheckException e) {
						showErrorMessage(e.getMessage()
								+ nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"4008bill", "UPP4008bill-000069")/*
																			 * @res
																			 * ".\nȨ��У�鲻ͨ��,����ʧ��! "
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
																		 * "Ȩ��У�鲻ͨ��,����ʧ��. "
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
																	 * "Ȩ��У�鲻ͨ��,����ʧ��. "
																	 */);

						}
					}
					catch (Exception ee1) {

						BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
								.handleException(null, null, ee1);
						if (realbe != null
								&& realbe.getClass() == CreditNotEnoughException.class) {
							// ������Ϣ��ʾ����ѯ���û����Ƿ��������
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n�Ƿ������*/);
							// ����û�ѡ�����
							if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
								voAudit.setIsCheckCredit(false);
								continue;
							} else
								return;
						} else if (realbe != null
								&& realbe.getClass() == PeriodNotEnoughException.class) {
							// ������Ϣ��ʾ����ѯ���û����Ƿ��������
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{realbe.getMessage()})/*{0} \r\n�Ƿ������*/);
							// ����û�ѡ�����
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
								// ������Ϣ��ʾ����ѯ���û����Ƿ��������
								int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000002", null, new String[]{atpe.getMessage()})/*{0} \r\n�Ƿ������*/);
								// ����û�ѡ�����
								if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
									voAudit.setIsCheckAtp(false);
									continue;
								} else {
									return;
								}
							}
						} else if (realbe != null
								&& realbe.getClass() == nc.vo.ic.pub.exp.OtherOut4MException.class) {
							// ������Ϣ��ʾ����ѯ���û����Ƿ��������
							int iFlag = showYesNoMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000005", null, new String[]{nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi","UPP4008busi-000401"),realbe.getMessage()})/*{0}{1}���Ƿ������*/);
							// ����û�ѡ�����
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
																	 * "�������"
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

				// showTime(lTime, "ǩ��");
				getClientUI().m_timer.showExecuteTime("ƽ̨ǩ��ʱ�䣺"/*-=notranslate=-*/);
				// ���ش���
				String sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000307")/* @res "ǩ�ֳɹ���" */;
				// ---- old ------����Ǳ��漴ǩ����Ҫˢ�½���,��Ȼ��������ʱ�Ǳ�����ڱ���ʱ����ˡ�
				// ---- old ------ֻҪ�ܱ��棬˵�������Ѿ���ȷ¼���ˡ�
				// -- new ---��Ϊ��ƽ̨��������������ִ����󣬻�Ҫ�ض��Ƿ�ǩ���ˡ�
				String sBillStatus = getClientUI().afterAuditFrushData(voAudit,false); 
         // ���µķ������,һ�κ�̨����

				getClientUI().m_timer.showExecuteTime("freshStatusTs");
				if (sBillStatus != null && !sBillStatus.equals(BillStatus.FREE)
						&& !sBillStatus.equals(BillStatus.DELETED)) {
					SCMEnv.out("**** signed ***");
				} else {
					sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"4008bill", "UPP4008bill-000005")/* @res "ǩ�ֳ���" */;
					sMsg += nc.ui.ml.NCLangRes.getInstance().getStrByID(
							"4008bill", "UPP4008bill-000552")/*
																 * @res
																 * "��ǰ����״̬����ȷ"
																 */;
				}
        
//      ���ο�����չ
        getClientUI().getPluginProxy().afterAction(nc.vo.scm.plugin.Action.AUDIT, new GeneralBillVO[]{voAudit});

				showHintMessage(sMsg);
			}
			nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getClientUI().getBillCardPanel());
			
		} catch (Exception be) {
			// ###################################################
			// ǩ���쳣����̨��־ add by hanwei 2004-6-8
			nc.ui.ic.pub.bill.GeneralBillUICtl.insertOperatelogVO(getM_voBill()
					.getHeaderVO(), nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40080402", "UPT40080402-000013")/*
																	 * @res "ǩ��"
																	 */, nc.vo.scm.funcs.Businesslog.MSGERROR + "" + be.getMessage());
			be = nc.vo.ic.pub.GenMethod
			.getRealBusiException(be);
			handleException(be);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000004")/* @res "ǩ�ֳ���" */
					+ be.getMessage());			
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000005")/* @res "ǩ�ֳ���" */
					+ be.getMessage());
			
			if (be instanceof ICBusinessException){
				ICBusinessException ee = (ICBusinessException) be;
				// ������ɫ
				nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getClientUI().getBillCardPanel(),ee);
			}
		}

	}
  

	/**
	 * �����ߣ����˾� ���ܣ����Ƶ�ǰ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onCopy() {
		try {
			// ��ǰ���б���ʽʱ�������л�������ʽ
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
			// �жϵ����Ƿ��Ǵ�U8�����д���ĵ��ݣ�����ǣ���������
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
				 * @res "���ݺ���{0}�ĵ�������U8�����ϴ����Զ����ɵģ�����ִ�иò�����"
				 */
				showWarningMessage(message1);
				return;
			}
			// added by lirr 2009-10-27����02:28:24 ��ѯ����ֱ�Ӹ��ƵĻ�û�е��ݵĻ�λ��Ϣ
			getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);
			// ��ʾ���Ƶĵ�������
			setM_voBill((GeneralBillVO) ((GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow())).clone());
			if (getM_voBill() == null)
				return;

			GeneralBillUICtl.processBillVOFor(getM_voBill(),
					GeneralBillUICtl.Action.COPY, getClientUI().m_bIsInitBill);

			// ����
			getBillCardPanel().addNew();
			int iRowCount = getM_voBill().getItemCount();

			getClientUI().setBillVO(getM_voBill(),true,false);
			// ������������

			// ���õ�ǰ������������ ���� 2004-04-05
			if (getClientUI().getM_utfBarCode() != null)
				getClientUI().getM_utfBarCode().setCurBillItem(null);

			nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();

			bmTemp.setNeedCalculate(false);
			for (int row = 0; row < iRowCount; row++) {
				// ������״̬Ϊ����
				if (bmTemp != null) {
					bmTemp.setRowState(row, nc.ui.pub.bill.BillModel.ADD);
					bmTemp.setValueAt(null, row, IItemKey.NAME_BODYID);
				}

			}
			bmTemp.setNeedCalculate(true);

			// �����������ݵĳ�ʼ���ݣ������ڣ��Ƶ��˵ȡ�
			getClientUI().setNewBillInitData();
			getBillCardPanel().setEnabled(true);
			setM_iMode(BillMode.New);
			// ���õ��ݺ��Ƿ�ɱ༭
			if (getBillCardPanel().getHeadItem("vbillcode") != null)
				getBillCardPanel().getHeadItem("vbillcode").setEnabled(
						getClientUI().m_bIsEditBillCode);

			setButtonStatus(true);

		} catch (Exception e) {
			handleException(e);

		}
	}

	/**
	 * �����ߣ����˾� ���ܣ���λ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ����˾� ���ܣ���λ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onSpaceAssign() {
		// �����ʽ���Ȳ�ѯ��λ/���кţ���ȻqryLocSN��ÿ�ŵ���ֻ��һ�ο⡣
		// m_dlgSpaceAllocation = null;

		// if (BillMode.Browse == m_iMode)
		getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);

		// ������һ�η��䣬ʵ���Ͽ���֧��ѡ�з���ġ�
		// ����ѡ�������кź�ʣ������λ������о�Ҫѡ�д���
		// ���������������ݶ�Ӧ���кţ��������ݺ�set����Ӧ��λ�ü��ɡ�

		InvVO[] voInv = null;
		// �ֿ�
		WhVO voWh = null;

		// ����ʽ��
		if (BillMode.Card == getM_iCurPanel()) {
			// ��ȥ����
			if (BillMode.Update == getM_iMode() || BillMode.New == getM_iMode())
				getClientUI().filterNullLine();
			if (getM_voBill() != null) {
				// ����VO
				GeneralBillItemVO voItemPty[] = getM_voBill().getItemVOs();
				// ID
				GeneralBillItemVO voItemID[] = getClientUI().getBodyVOs();
				voInv = new InvVO[getM_voBill().getItemCount()];
				// �ϲ����ԡ�ID
				for (int row = 0; row < getM_voBill().getItemCount(); row++) {
					voItemPty[row].setIDItems(voItemID[row]);
					// �����Ե�InvVO
					voInv[row] = voItemPty[row].getInv();
				}
				voWh = getM_voBill().getWh();
			}
		} else
		// �б���ʽ�²쿴���ȶ����ݣ�����һ��Ҫ����setInitVOs֮ǰ
		if (getM_iLastSelListHeadRow() >= 0
				&& getM_iLastSelListHeadRow() < getM_alListData().size()) {
			// ������еĻ�λ����ȸ�������
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
			// ������еĻ�λ����ȸ�������
			// resetBodyAssistData(m_iLastSelListHeadRow);
			// else
			// ����ĵ�����ʱ����ִ�С����кš��������кŽ����ϻ��ܻ�λ��
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000022")/*
														 * @res
														 * "����ĵ�����ʱ����ִ�С����кš��������кŽ����ϻ��ܻ�λ��"
														 */);
// added by lirr 2009-11-19����04:23:34 ���Ӳ���getClientUI().isCheckAssetInv()
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
			// ����
			// ��������к�,������ؽ����ĳ��element��null,��ʾ�ǳ���Ĵ������ͨ����λ�������������кš����Բ����õ����к����ݡ�
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
			// ���û�λ
			if (alRes != null && alRes.size() > 0) {
				// �����Ҫ����ʼ��֮
				if (getClientUI().getM_alLocatorData() == null)
					getClientUI().initM_alLocatorData(alRes.size());
				for (int i = 0; i < alRes.size(); i++) {
					LocatorVO voLoc[] = (LocatorVO[]) alRes.get(i);
					if (i < voInv.length && voInv[i].getIsSerialMgt() != null
							&& voInv[i].getIsSerialMgt().intValue() == 1
							&& voInv[i].getInOutFlag() != InOutFlag.IN)
						continue;
					// ֻ���������ݵģ���Ϊ���к�Ҳ���޸Ļ�λ����
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
	 * �����ߣ����˾� ���ܣ����кŷ��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 
	 * 
	 * 
	 */
	protected void onSNAssign() {
		nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
		timer.start(NCLangRes.getInstance().getStrByID("bill", "bill-000006")/*@@���кŷ��俪ʼ��*/);
		// �����ʽ���Ȳ�ѯ��λ/���кţ���ȻqryLocSN��ÿ�ŵ���ֻ��һ�ο⡣
		// if (BillMode.Browse == m_iMode) {
		getClientUI().qryLocSN(getM_iLastSelListHeadRow(), QryInfoConst.LOC_SN);
		// timer.showExecuteTime("@@�����ʽ�²�ѯ��λ/���кŲ�ѯʱ�䣺");
		// }
		// ��ǰѡ�е���
		int iCurSelBodyLine = -1;
		if (BillMode.Card == getM_iCurPanel()) {
			iCurSelBodyLine = getBillCardPanel().getBillTable()
					.getSelectedRow();
			if (iCurSelBodyLine < 0) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000016")/*
															 * @res
															 * "��ѡ��Ҫ�������кŷ�����С�"
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
															 * "��ѡ��Ҫ�鿴���кŵ��С�"
															 */);
				return;
			}
		}
		InvVO voInv = null;
		// �ֿ�
		WhVO voWh = null;
		// ����VO,������m_voBill���б��¶���m_alListData.
		GeneralBillVO voBill = null;

		// ���״̬�²쿴���ȶ����ݣ�����һ��Ҫ����setInitVOs֮ǰ

		// ������vo,�����б��»��Ǳ���
		// ����ʽ��
		if (BillMode.Card == getM_iCurPanel()) {
			if (getM_voBill() == null || getM_voBill().getItemCount() <= 0) {
				SCMEnv.out("bill null E.");
				return;
			}
			voBill = getM_voBill();
			// ����VO
			GeneralBillItemVO voItem = getClientUI().getBodyVO(iCurSelBodyLine);
			// ����VO
			GeneralBillItemVO voItemPty = voBill.getItemVOs()[iCurSelBodyLine];
			// �ϲ�
			if (voItemPty != null)
				voItemPty.setIDItems(voItem);
			// �����Ĵ��������
			if (voItem != null)
				voInv = voItemPty.getInv();

			if (voBill != null)
				voWh = voBill.getWh();
			
			// ����Ƿ����ģʽ�����Ҳֿ��ǻ�λ�������������ʱӦ�߻�λ��
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
																		 * "��ʾ"
																		 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"4008bill", "UPP4008bill-000018")/*
																		 * @res
																		 * "���ʱ���кŷ�������ִ�С���λ���䡱���ڻ�λ������ִ�С����кš�����"
																		 */);
					return;

				}
			}

		} else
		// �б���ʽ�²쿴���ȶ����ݣ�����һ��Ҫ����setInitVOs֮ǰ
		if (getM_iLastSelListHeadRow() >= 0
				&& getM_iLastSelListHeadRow() < getM_alListData().size()) {
			// ������еĻ�λ����ȸ�������
			voBill = (GeneralBillVO) getM_alListData().get(
					getM_iLastSelListHeadRow());
			if (voBill == null) {
				SCMEnv.out("bill null E.");
				return;
			}
			voInv = voBill.getItemInv(iCurSelBodyLine);
			// warehouse
			voWh = voBill.getWh();
			// ��˾PK
			voWh.setPk_corp(getEnvironment().getCorpID());
		}
		
		
		if ("40080822".equals(getClientUI().getFunctionNode())){
			if (null != voBill.getItemValue(iCurSelBodyLine, "cwarehouseid") && ! voBill.getItemValue(iCurSelBodyLine, "cwarehouseid").toString().equals(voWh.getCwarehouseid()) ){
				int iQryMode = QryInfoConst.WH;
				// ����
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
			// �����ϵ�ǰ���ݵ�PK.
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
			// ��Ӧ����ⵥ�����У�����Ϊnull��
			// �����������ⲻ��Ϊnull
			String sCorrespondBodyItemPK = voInv.getCcorrespondbid();
			// �����Ƿ�������
			// zhx
			if (getClientUI().getIsInvTrackedBill(voInv)
					&& voInv.getInOutFlag() == InOutFlag.OUT
					&& (sCorrespondBodyItemPK == null || sCorrespondBodyItemPK
							.trim().length() == 0)) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000019")/*
															 * @res
															 * "����ָ����Ӧ��ⵥ��Ȼ�����ԡ�"
															 */);
				return;
			}

			// �����ģʽ����������Ϊ��
			if (BillMode.Card == getM_iCurPanel()
					&& (BillMode.Update == getM_iMode() || BillMode.New == getM_iMode())) {
				Object oQty = voInv.getAttributeValue(getEnvironment()
						.getNumItemKey());
				if (oQty == null || oQty.toString().trim().length() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000020")/*
																			 * @res
																			 * "��������������"
																			 */);
					return;
				}
				// �������벻��С�� by hanwei 2003-07-20
				if (nc.vo.ic.pub.check.VOCheck.checkIsDecimal(oQty.toString())) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000021")/*
																			 * @res
																			 * "���кŹ���������������С����"
																			 */);
					return;
				}

			}

			// if (BillMode.Browse == m_iMode)
			// //������еĻ�λ����ȸ�������
			// resetBodyAssistData(m_iLastSelListHeadRow);
			// ydy2002-12-19
			LocatorVO aLoc = null;
			LocatorVO[] voLocs = null;
			if (getClientUI().getM_alLocatorData() != null && getClientUI().getM_alLocatorData().size() > 0)
				voLocs = (LocatorVO[]) getClientUI().getM_alLocatorData().get(iCurSelBodyLine);

			if (voLocs == null || voLocs.length != 1) {
				// showHintMessage("��λ��������");
				aLoc = new LocatorVO();

			} else
				aLoc = voLocs[0];
			// end ydy
			// -������ǻ�λ�ֿ�����к���⣻�������к�
			SerialVO[] serials = null;
			if (getClientUI().getM_alSerialData() != null && getClientUI().getM_alSerialData().size() > 0)
				serials = (SerialVO[]) getClientUI().getM_alSerialData().get(iCurSelBodyLine);
			else {
				serials = new SerialVO[] {};
				getClientUI().getM_alSerialData().add(iCurSelBodyLine, serials);
			}
			getClientUI().getSerialAllocationDlg().setDataVO(getInOutFlag(), voWh, aLoc,
					voInv, getM_iMode(), serials, voLocs);

			// ��ǰ�����к���

			int result = getClientUI().getSerialAllocationDlg().showModal();
			if (nc.ui.pub.beans.UIDialog.ID_OK == result
					&& (BillMode.New == getM_iMode() || BillMode.Update == getM_iMode())) {
				SerialVO voaSN[] = getClientUI().getSerialAllocationDlg().getDataSNVO();
				// ���������
				getClientUI().getM_alSerialData().set(iCurSelBodyLine, voaSN);
				// if (voaSN != null)
				// for (int i = 0; i < voaSN.length; i++)
				// SCMEnv.out("ret sn[" + i + "] is" +
				// voaSN[i].getVserialcode());
				// ����ǳ���Ļ����������л�λ��������
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
						// �������кŲ�������û��ë��,�˴���������һ��
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

					// ���浽��Ӧ����
					getClientUI().getM_alLocatorData().set(iCurSelBodyLine, voaLoc);
				}
				
				getClientUI().setBodySpace(iCurSelBodyLine);

			}
		} else {
			SCMEnv.out("----> no bill ERR.");
		}
	}

	/**
	 * ������ѯ �޸� �������ڣ�(2001-4-18 19:45:39)
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
	 * �ж�ѡ�����Ƿ���׼� �������ڣ�(2004-3-12 21:14:17)
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
																	 * "��ѡ��Ҫ����������У�"
																	 */);
			return;
		}

		if (getEnvironment().getCorpID() == null) {
			MessageDialog.showErrorDlg(getClientUI(), null, nc.ui.ml.NCLangRes
					.getInstance().getStrByID("4008bill", "UPP4008bill-000109")/*
																				 * @res
																				 * "��ǰ��½��˾IDΪ�գ�"
																				 */);
			return;
		}
		if (sInvID == null || sInvID.trim().length() == 0) {
			MessageDialog.showErrorDlg(getClientUI(), null, nc.ui.ml.NCLangRes
					.getInstance().getStrByID("4008bill", "UPP4008bill-000110")/*
																				 * @res
																				 * "ѡ����û�д�����룡"
																				 */);
			return;
		}

		getClientUI().getSetpartDlg().setParam(getEnvironment().getCorpID(), sInvID);
		getClientUI().getSetpartDlg().showSetpartDlg();

	}

	/**
	 * �����ˣ������� �������ڣ�2007-7-13����02:50:27
	 * ����ԭ�򣺿�����ҵ���еĲɹ���ⵥ������Ʒ��ⵥ��ί�мӹ���ⵥ��������ⵥ��������ⵥ�����뵥�����ϼӹ���ⵥ�еĸ�����ѯ�����ӡ���Ӧ�������ѯ����ť��
	 * 
	 */
	protected void onProviderBarcodeQuery() {
		RefMsg msg = new RefMsg(getClientUI());
		msg.setIBillOperate(nc.ui.ic.ic009.Const.PBQFromIC);
		nc.ui.ic.ic009.ClientUI.openNodeAsDlg(getClientUI(), msg);
	}

	/**
	 * � ��ʾ���������ִ������
	 * 
	 */
	protected void onOnHandShowHidden() {
		if (getM_iCurPanel() == BillMode.List) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000065")/* @res "���л�����Ƭ���棡" */);
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
	 * �����ߣ������� ���ܣ���ӡ ������ ���أ� ���⣺ ���ڣ�(2001-5-10 ���� 4:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * �޸�˵�������Ӵ�ӡ�������� �޸��ߣ��۱� 2005-01-12
	 */
	protected void onPrint() {
		try {
			// ������ӡ����
			// ����ǰ���б��Ǳ�������ӡ����
			if (getM_iMode() == BillMode.Browse
					&& getM_iCurPanel() == BillMode.Card) { // ���

				/* ���Ӵ�ӡ�������ƺ�Ĵ�ӡ���� */
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000047")/*
															 * @res
															 * "���ڴ�ӡ�����Ժ�..."
															 */);
				// ׼�����ݣ����Ҫ��ӡ��vo.
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
																			 * "���ȶ����ӡģ�塣"
																			 */);
					return;
				}

				// ��Ƭ��ӡ
				printOnCard(voBill, false);

			} else if (getM_iCurPanel() == BillMode.List) { // �б�
				/* ���Ӵ�ӡ��������ǰ�Ĵ�ӡ���� */
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "���Ȳ�ѯ��¼�뵥�ݡ�"
																			 */);
					return;
				}

				// �õ�Ҫ��ӡ���б�vo,ArrayList.
				ArrayList alBill = getClientUI().getSelectedBills();
				// ��С������
				getClientUI().setScaleOfListData(alBill);
				if (alBill == null || alBill.size() <= 0
						|| alBill.get(0) == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "��ѡ��Ҫ��������ݣ�"
															 */);
					return;
				}

				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000047")/*
															 * @res
															 * "���ڴ�ӡ�����Ժ�..."
															 */);
				// �޸��ˣ������� �޸����ڣ�2007-10-29����09:43:07 �޸�ԭ�򣺽��������ӡʱ����Щ���ݻ�λ�����ӡ��������
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

				// �б��´�ӡ
				printOnList(alBill);

				// showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				// "SCMCOMMON", "UPPSCMCommon-000133")/* @res "����" */);

			} else
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000052")/*
															 * @res
															 * "��ע�⣺��ֻ�������״̬�´�ӡ"
															 */);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000061")/* @res "��ӡ����" */
					+ e.getMessage());
		}
	}

	/**
	 * �����ߣ������� ���ܣ���ӡԤ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-10 ���� 4:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * �޸�˵�������Ӵ�ӡ�������� �޸��ߣ��۱� 2004-01-12
	 */
	protected void onPreview() {

		try {
			// ������ӡ����
			// ����ǰ���б��Ǳ�������ӡ����
			if (getM_iMode() == BillMode.Browse
					&& getM_iCurPanel() == BillMode.Card) { // ���

				/* ���Ӵ�ӡ�������ƺ�Ĵ�ӡ���� */
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000047")/*
															 * @res
															 * "���ڴ�ӡ�����Ժ�..."
															 */);
				// ׼�����ݣ����Ҫ��ӡ��vo.
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
																			 * "���ȶ����ӡģ�塣"
																			 */);
					return;
				}

				// ��Ƭ��Ԥ��
				printOnCard(voBill, true);

			} else if (getM_iCurPanel() == BillMode.List) { // �б�

				/* ���Ӵ�ӡ�������ƺ�Ĵ�ӡ���� */
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "���Ȳ�ѯ��¼�뵥�ݡ�"
																			 */);
					return;
				}
				// ��Ҫ�Ļ�����ѯȱ�ٵĵ�������
				// queryLeftItem(m_alListData);

				ArrayList alBill = getClientUI().getSelectedBills();
				if (alBill == null || alBill.size() <= 0
						|| alBill.get(0) == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "��ѡ��Ҫ��������ݣ�"
															 */);
					return;
				}
				// ��С������
				getClientUI().setScaleOfListData(alBill);

				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000100")/*
															 * @res
															 * "�������ɵ�һ�ŵ��ݵĴ�ӡԤ�����ݣ����Ժ�..."
															 */);

				GeneralBillVO voBill = (GeneralBillVO) alBill.get(0);
				// ��������
				GeneralBillHeaderVO headerVO = voBill.getHeaderVO();
				String sBillID = headerVO.getPrimaryKey();

				// ����PringLogClient�Լ�����PrintInfo
				ScmPrintlogVO voSpl = new ScmPrintlogVO();
				voSpl = new ScmPrintlogVO();
				voSpl.setCbillid(sBillID); // ���������ID
				voSpl.setVbillcode(headerVO.getVbillcode());// ���뵥�ݺţ�������ʾ��
				voSpl.setCbilltypecode(headerVO.getCbilltypecode());
				voSpl.setCoperatorid(headerVO.getCoperatorid());
				voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));// �̶�
				voSpl.setPk_corp(getEnvironment().getCorpID());
				voSpl.setTs(headerVO.getTs());// ���������TS

				SCMEnv.out("ts=========tata" + voSpl.getTs());
				nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();

				nc.ui.pub.print.PrintEntry pe = getClientUI().getPrintEntryNew();

				if (pe.selectTemplate() < 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000048")/*
																			 * @res
																			 * "���ȶ����ӡģ�塣"
																			 */);
					return;
				}

				plc.setPrintEntry(pe);
				// ���õ�����Ϣ
				plc.setPrintInfo(voSpl);
				// ����ts��printcountˢ�¼���.
				plc.addFreshTsListener(new FreshTsListener());
				// ���ô�ӡ����
				pe.setPrintListener(plc);
				
				GeneralBillVO[] destvoBills =  (GeneralBillVO[])getClientUI().getPluginProxy().beforePrint(new GeneralBillVO[]{(GeneralBillVO)voBill.clone()});
				if (null != destvoBills &&  0 < destvoBills.length && destvoBills[0] instanceof GeneralBillVO)
					getClientUI().getDataSource().setVO(destvoBills[0]);
				else
				// ��ӡԤ��
					getClientUI().getDataSource().setVO(voBill);
				pe.setDataSource(getClientUI().getDataSource());
				pe.preview();

			} else
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000101")/*
															 * @res
															 * "��ע�⣺��ֻ�������״̬��ִ�д�ӡԤ����"
															 */);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000061")/* @res "��ӡ����" */
					+ e.getMessage());
		}
	}
	

	/**
	 * 
	 * ����һ���ڲ���. �̳�IFreshTsListener. ʵ�ִ�ӡ���ts��printcount�ĸ���.
	 * 
	 * @author �۱� ����ʱ��: 2004-12-23
	 * 
	 */
	private class FreshTsListener implements IFreshTsListener {

		/*
		 * ���� Javadoc��
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

			// �жϴ�ӡ��vo�Ƿ����ڻ����У�
			// �ڴ�ӡԤ��״̬��ӡʱ������vo���ܻ��иı䣬����Ҫ�жϣ�
			int index = 0;
			GeneralBillVO voBill = null;
			GeneralBillHeaderVO headerVO = null;
			for (; index < getM_alListData().size(); index++) {
				voBill = (GeneralBillVO) getM_alListData().get(index);
				headerVO = voBill.getHeaderVO();

				// ��sBillID����ʱ���Ѿ��ж�sBillID��Ϊnull.
				if (sBillID.equals(headerVO.getPrimaryKey()))
					break;
			}

			if (index == getM_alListData().size()) // ���ڻ���vo�У�������и��£�
				return;

			// �ڻ���vo��
			headerVO.setAttributeValue("ts", sTS);
			headerVO.setAttributeValue("iprintcount", iPrintCount);

			if (getM_iCurPanel() == BillMode.Card) { // Card
				if (index == getClientUI().m_iCurDispBillNum) { // ���Ϊ��ǰcard��ʾvo.
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
	 * �����д�ӡ��ͨ�����������Ի����û���������������VO����ӡԤ��
	 */
	protected void onPrintSumRow() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi",
				"UPP4008busi-000248")/* @res "���ڴ�ӡ�����Ժ�..." */);
		SCMEnv.out("��ӡ���λ��ܿ�ʼ!\n"/*-=notranslate=-*/);
		try {
			// ������ӡ����
			// ����ǰ���б��Ǳ�������ӡ����
			if (getM_iMode() == BillMode.Browse
					&& getM_iCurPanel() == BillMode.Card) { // ���
				SCMEnv.out(NCLangRes.getInstance().getStrByID("bill", "bill-000007")/*��ӡ���λ��ܿ�ʼ!����ӡ!\n*/);
				// ׼������
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

				// �õ���������,�����ѡ����ܸ���������λ�����û��ܸ�����,Ĭ��ѡ����ܸ�����
				MergeRowDialog dlgMerge = new MergeRowDialog(getClientUI());
				if (dlgMerge.showModal() == UIDialog.ID_CANCEL)
					return;
				ArrayList alGroupBy = dlgMerge.getGroupingAttr();
				if (alGroupBy == null || alGroupBy.size() <= 0
						|| alGroupBy.size() != 6)
					return;

				// �õ������ֶ�
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

				// �õ�Summing�ֶ�
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

				// ����Ԥ��
				printOnCard(gvobak, true);

			} else if (getM_iCurPanel() == BillMode.List) {
				// �б�

				SCMEnv.out("�б��ӡ��ʼ!\n"/*-=notranslate=-*/);
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					return;
				}
				if (getClientUI().getPrintEntry().selectTemplate() < 0)
					return;
				ArrayList alBill = getClientUI().getSelectedBills();
				// ��С������
				getClientUI().setScaleOfListData(alBill);
				SCMEnv.out("�б��ӡ:�õ�ѡ�еĵ��ݲ�������������!\n"/*-=notranslate=-*/);
				if (alBill == null)
					return;
				nc.vo.scm.merge.DefaultVOMerger dvomerger = null;
				for (int i = 0; i < alBill.size(); i++) {
					SCMEnv.out("�б��ӡ:��ʼ�ϲ�������!\n"/*-=notranslate=-*/);
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
					SCMEnv.out("�б��ӡ:�õ��ϲ���ı�����!\n"/*-=notranslate=-*/);
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
				SCMEnv.out("�б��ӡ:�õ��ϲ���ĵ���!\n"/*-=notranslate=-*/);

				// �б��´�ӡ������Ԥ����
				printOnList(alBill);

			} else
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008busi", "UPP4008busi-000249")/* @res "ֻ�������״̬�´�ӡ" */);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008busi", "UPPSCMCommon-000061")/* @res "��ӡ����" */
					+ e.getMessage());
		}
	}

	/*
	 * �б��´�ӡ @author �۱� on Jun 15, 2005
	 */
	private void printOnList(ArrayList alBill) throws InterruptedException {
		nc.ui.pub.print.PrintEntry pe = getClientUI().getPrintEntryNew();

		if (pe.selectTemplate() < 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000048")/*
														 * @res "���ȶ����ӡģ�塣"
														 */);
			return;
		}

		pe.beginBatchPrint();

		PrintDataInterface ds = null;
		GeneralBillVO voBill = null;
		// ��������
		GeneralBillHeaderVO headerVO = null;
		// nc.vo.scm.print.PrintResultVO printResultVO = null;

		nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();
		plc.setBatchPrint(true);// ����������
		// ���ô�ӡ����
		pe.setPrintListener(plc);
		plc.setPrintEntry(pe);
		plc.addFreshTsListener(new FreshTsListener());

		// ��ӡ����
		for (int i = 0; i < alBill.size(); i++) {
			voBill = (GeneralBillVO) alBill.get(i);
			headerVO = voBill.getHeaderVO();
			
			// �޸�ԭ���ڵ���������״̬ʱ����Ҫȥˢ��������Ϣ����Ϊ���ⵥ���ܻ�����������֮ǰˢ�µ��ݽ���
			if (nc.vo.pub.VOStatus.NEW != voBill.getStatus())
				BatchCodeDefSetTool.execFormulaForBatchCode(voBill.getItemVOs());

			ScmPrintlogVO voSpl = new ScmPrintlogVO();
			voSpl = new ScmPrintlogVO();
			voSpl.setCbillid(headerVO.getPrimaryKey()); // ���������ID
			voSpl.setVbillcode(headerVO.getVbillcode());// ���뵥�ݺţ�������ʾ��
			voSpl.setCbilltypecode(headerVO.getCbilltypecode());
			voSpl.setCoperatorid(headerVO.getCoperatorid());
			voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));// �̶�
			voSpl.setPk_corp(getEnvironment().getCorpID());
			voSpl.setTs(headerVO.getTs());// ���������TS

			SCMEnv.out("ts=========tata" + voSpl.getTs());
			// ���õ�����Ϣ
			plc.setPrintInfo(voSpl);

			if (plc.check()) {
				// ���ͨ����ִ�д�ӡ���д���Ļ��Զ������ӡ��־�����ﲻ�ô���
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
	 * ��Ƭ��ӡ/Ԥ��
	 * 
	 * @param isPreview boolean true-��ӡԤ����false-ֱ�Ӵ�ӡ * @author �۱� on Jun 15,
	 * 2005
	 */
	protected void printOnCard(GeneralBillVO voBill, boolean isPreview) {

		// ��������
		GeneralBillHeaderVO headerVO = voBill.getHeaderVO();
		String sBillID = headerVO.getPrimaryKey();

		// ����PrintLogClient������PrintInfo.
		ScmPrintlogVO voSpl = new ScmPrintlogVO();
		voSpl.setCbillid(sBillID); // ���������ID
		voSpl.setVbillcode(headerVO.getVbillcode());// ���뵥�ݺţ�������ʾ��
		voSpl.setCbilltypecode(headerVO.getCbilltypecode());
		voSpl.setCoperatorid(headerVO.getCoperatorid());
		voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));// �̶�
		voSpl.setPk_corp(getEnvironment().getCorpID());
		voSpl.setTs(headerVO.getTs());// ���������TS

		SCMEnv.out("ts=========tata" + voSpl.getTs());
		nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();
		// ���õ�����Ϣ
		plc.setPrintInfo(voSpl);
		// ����TSˢ�¼���.
		plc.addFreshTsListener(new FreshTsListener());
		// ���ô�ӡ����
		getClientUI().getPrintEntry().setPrintListener(plc);

		plc.setPrintEntry(getClientUI().getPrintEntry());// ���ڵ���ʱ
		// ���õ�����Ϣ
		plc.setPrintInfo(voSpl);
		
		GeneralBillVO[] destvoBills =  (GeneralBillVO[])getClientUI().getPluginProxy().beforePrint(new GeneralBillVO[]{(GeneralBillVO)voBill.clone()});
		if (null != destvoBills &&  0 < destvoBills.length && destvoBills[0] instanceof GeneralBillVO)
			getClientUI().getDataSource().setVO(destvoBills[0]);
		else
		// ���ӡ��������Դ�����д�ӡ
			getClientUI().getDataSource().setVO(voBill);
		getClientUI().getPrintEntry().setDataSource(getClientUI().getDataSource());

		// ��ӡ��ʾ��Ϣ
		String sPrintMsg = null;
		// ִ�д�ӡ
		if (isPreview) {
			getClientUI().getPrintEntry().preview();
			sPrintMsg = plc.getPrintResultMsg(true);
		} else {
			getClientUI().getPrintEntry().print();
			sPrintMsg = plc.getPrintResultMsg(false);
		}
	}

	/**
	 * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-10-23 9:07:19) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	protected void onPrintLocSN(GeneralBillVO voBill) {
		// ��ӡ��������m_voBill
		if (voBill == null || voBill.getParentVO() == null
				|| voBill.getChildrenVO() == null) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000053")/* @res "û�����ݣ�" */);
			return;
		}
		// ׼����ӡ����
		String title = nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000319")/* @res " ��λ���кŷ�����ϸ��" */;
		GeneralBillHeaderVO voHead = (GeneralBillHeaderVO) voBill.getParentVO();
		// ׼����ͷ�ִ�
		StringBuffer headstr = new StringBuffer(nc.ui.ml.NCLangRes
				.getInstance().getStrByID("4008bill", "UPP4008bill-000320")/*
																			 * @res
																			 * "���ݺţ�"
																			 */);
		if (voHead.getVbillcode() != null)
			headstr.append(voHead.getVbillcode());
		else
			headstr.append("      ");

		headstr.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000321")/* @res "�ֿ⣺" */);
		if (voHead.getCwarehousename() != null)
			headstr.append(voHead.getCwarehousename());
		else
			headstr.append("       ");
		headstr.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000322")/* @res "���ڣ�" */);
		if (voHead.getDbilldate() != null)
			headstr.append(voHead.getDbilldate().toString());
		else
			headstr.append("       ");

		// ׼��������������
		String[][] colname = new String[1][12];
		int[] colwidth = new int[12];
		int[] alignflag = new int[12];
		String[] names = new String[] {
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0001480")/* @res "�������" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0001453")/* @res "�������" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0000745")/* @res "��λ" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003327")/* @res "������" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000182")/* @res "����" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003971")/* @res "������" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003938")/* @res "����λ" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0002161")/* @res "������" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0002282")/* @res "����" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0003830")/* @res "��λ" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC000-0001819") /* @res "���к�" */,
				// modified by liuzy 2007-12-27 ����ë��������
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000475") /* @res "ë��������" */
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

		// ׼��������������

		Vector v = new Vector(); // ����Ҫ��ӡ������
		Object[] data = null;
		// �������ݾ���by ZSS
		ArrayList alVO = new ArrayList();
		alVO.add(voBill);
		getClientUI().setScaleOfListData(alVO);
		voBill = (GeneralBillVO) alVO.get(0);
		//
		GeneralBillItemVO[] voItems = (GeneralBillItemVO[]) voBill
				.getChildrenVO();
		for (int i = 0; i < voItems.length; i++) {
			// ��������еĻ�λ��Ϊ�ա�ȡ�û�λ�������飬ÿ����һ����λ������һ�У�����ȡ�Ի�λVO�����к����λpk���

			if (voItems[i].getLocator() != null) {
				LocatorVO[] locs = voItems[i].getLocator();

				ScaleKey sk = new ScaleKey();
				sk.setNumKeys(new String[] { "ninspacenum", "noutspacenum" });
				sk.setAssistNumKeys(new String[] { "ninspaceassistnum",
						"noutspaceassistnum" });

				GenMethod.setScale(locs, sk, getClientUI().m_ScaleValue);

				for (int j = 0; j < locs.length; j++) {
					data = new Object[12];
					// �������
					data[0] = voItems[i].getCinventorycode();
					data[1] = voItems[i].getInvname();
					data[2] = voItems[i].getMeasdocname();
					data[3] = voItems[i].getVfree0();
					data[4] = voItems[i].getVbatchcode();
					data[6] = voItems[i].getCastunitname();
					data[7] = voItems[i].getHsl();
					// ����ninspacenum or noutspacenum
					if (locs[j].getNinspacenum() != null) {
						data[8] = locs[j].getNinspacenum();
						data[5] = locs[j].getNinspaceassistnum();
					} else {
						data[8] = locs[j].getNoutspacenum();
						data[5] = locs[j].getNoutspaceassistnum();
					}

					// ��λcsname
					data[9] = locs[j].getVspacename();
					// ������кŲ�Ϊ��
					data[10] = getClientUI().getSNString(voItems[i].getSerial(), locs[j]
							.getCspaceid());
					// modified by liuzy 2007-12-27 ����ë��������
					data[11] = voItems[i].getAttributeValue(getEnvironment()
							.getGrossNumItemKey());
					// modify by liuzy 2007-11-21 ��λ��ӡ����
					v.add(data);
				}
			}
			// �����λΪ�գ�
			else {
				data = new Object[12];
				// �������
				data[0] = voItems[i].getCinventorycode();
				data[1] = voItems[i].getInvname();
				data[2] = voItems[i].getMeasdocname();
				data[3] = voItems[i].getVfree0();
				data[4] = voItems[i].getVbatchcode();
				data[6] = voItems[i].getCastunitname();
				data[7] = voItems[i].getHsl();
				// ����ȡ�Ա�����
				if (voItems[i].getNinnum() != null) {
					data[8] = voItems[i].getNinnum();
					data[5] = voItems[i].getNinassistnum();
				} else {
					data[8] = voItems[i].getNoutnum();
					data[5] = voItems[i].getNoutassistnum();
				}

				// ������кŲ�Ϊ��,���к���Ϣ����������
				data[10] = getClientUI().getSNString(voItems[i].getSerial(), null);
				// modified by liuzy 2007-12-27 ����ë��������
				data[11] = voItems[i].getAttributeValue(getEnvironment()
						.getGrossNumItemKey());
				v.add(data);
			}

		}
		if (v.size() > 0) {
			// �������ݣ�ȥ�������У�
			Object[][] data1 = new Object[v.size()][12];

			for (int i = 0; i < v.size(); i++) {

				data1[i] = (Object[]) v.get(i);

			}

			java.awt.Font font = new java.awt.Font("dialog",
					java.awt.Font.BOLD, 18);
			java.awt.Font font1 = new java.awt.Font("dialog",
					java.awt.Font.PLAIN, 12);
			String topstr = nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000323")/* @res "��˾��" */
					+ nc.ui.pub.ClientEnvironment.getInstance()
							.getCorporation().getUnitname() + headstr;

			String botstr = nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000325")/* @res "�Ʊ��ˣ�" */
					+ nc.ui.pub.ClientEnvironment.getInstance().getUser()
							.getUserName()
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000324")/* @res " �Ʊ����ڣ�" */
					+ nc.ui.pub.ClientEnvironment.getInstance().getDate();
			//
			//nc.ui.pub.print.PrintDirectEntry print = new nc.ui.pub.print.PrintDirectEntry();
			nc.ui.pub.print.PrintDirectEntry print = new nc.ui.pub.print.PrintDirectEntry(getClientUI());

			print.setTitle(title);
			// ���� ��ѡ
			print.setTitleFont(font);
			// �������� ��ѡ
			print.setContentFont(font1);
			// �������壨��ͷ����񡢱�β�� ��ѡ
			print.setTopStr(topstr);
			// ��ͷ��Ϣ ��ѡ
			// ҳ��
			print.setBottomStr(botstr);
			print.setPageNumDisp(true);
			print.setPageNumFont(font1);
			// ����0 1 2
			print.setPageNumAlign(2);
			// ����0 1 2
			print.setPageNumPos(2);
			print.setPageNumTotalDisp(true);
			// �̶���ͷ
			print.setFixedRows(1);
			// ��β��Ϣ ��ѡ
			print.setColNames(colname);
			// �����������ά������ʽ��
			print.setData(data1);
			// �������
			print.setColWidth(colwidth);
			// ����п� ��ѡ
			print.setAlignFlag(alignflag);
			// ���ÿ�еĶ��뷽ʽ��0-��, 1-��, 2-�ң���ѡ

			print.preview();
			// Ԥ��

		}
		// �ύ��ӡ
	}

	/**
	 * �˴����뷽��˵���� ��������:�ڿ�Ƭģʽ��ת���һ�š� ���ߣ������� �������: ����ֵ: �쳣����: ����:(2003-5-27
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
	 * �˴����뷽��˵���� ��������:�ڿ�Ƭģʽ��ָ��ǰһ�� ���ߣ������� �������: ����ֵ: �쳣����: ����:(2003-5-27
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
	 * �˴����뷽��˵���� ��������:�ڿ�Ƭģʽ��ָ����һ�� ���ߣ������� �������: ����ֵ: �쳣����: ����:(2003-5-27
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
	 * �˴����뷽��˵���� ��������:�ڿ�Ƭģʽ��ָ�����һ�� ���ߣ������� �������: ����ֵ: �쳣����: ����:(2003-5-27
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
	 * ���ܣ� �˶������¼���Ӧ ������ ���أ� ���⣺ ���ڣ�(2002-04-18 10:43:46) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
					"4008bill", "UPP4008bill-000044")/* @res "������ʾ��" */
					+ sErrorMsg);
		}
	}

	/**
	 * 
	 * ���ܣ� ���������¼���Ӧ ������ ���أ� ���⣺ ���ڣ�(2002-04-18 10:43:46) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	protected void onImportData() {
		try {
			// ���˿���
			getClientUI().filterNullLine();
			// �����к�
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
						"4008bill", "UPP4008bill-000046")/* @res "û�е���ɹ���" */);
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
					// �����Ƿ��ڱ���ʱУ�鵼�����ݵ���ȷ��
					String sWarehouseid = null;
					nc.ui.pub.beans.UIRefPane refpane = (UIRefPane) getBillCardPanel()
							.getHeadItem("cwarehouseid").getComponent();

					if (refpane != null)
						sWarehouseid = refpane.getRefPK();
					// ͬ��vo.
					getClientUI().synVO(voaDi, sWarehouseid, getClientUI().getM_alLocatorData());
					// ���뽫��λ��Ϣͬ����m_alLocatorData,���򵥾ݱ���ʱ��getBillVOs���������m_voBill�Ļ�λ��Ϣ
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
				// ����У������Ϣ����������Щ��Ϣ��ֻ��������
				getClientUI().setIsImportData(false);
			}

		} catch (Exception e) {
			String sErrorMsg = null;
			sErrorMsg = e.getMessage();
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000044")/* @res "������ʾ��" */
					+ sErrorMsg);
			getClientUI().setIsImportData(false);
		}
	}

	/**
	 * ���Ե���Excel����������ļ�(���������в˵��� ����:� iMenu =0 ,1��2 �ֱ��Ӧ�����������������������
	 * �������ڣ�(2004-4-21 20:41:28)
	 */
	protected void onImptBCExcel(int iMenu) {
		// �Ƿ񸲸ǾɵĴ��������
		boolean bCover = false;
		// ��ǰѡ����
		int rownow;
		// ���������ļ��ڴ�VO������
		BarCodeVO[] voaImport = null;
		// ������������
		int sizeVOImp = 0;
		// �¾��������Ϣ
		ArrayList alOldVO = new ArrayList();
		ArrayList alNewVO = new ArrayList();
		// ���Ϸ��ص�BarcodeVO����
		BarCodeVO[] bcVOTotal = null;

		rownow = checkSelectionRow();
		if (rownow == -1) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH004")/* @res "��ѡ��Ҫ����������У�" */);
			return;
		}
		// �õ��ɵĴ��������Ϣ
		GeneralBillItemVO billItemvo = (GeneralBillItemVO) getM_voBill()
				.getItemVOs()[rownow];
		alOldVO = BarcodeparseCtrlUI.getVOInfoOld(billItemvo);
		// �����ļ���VO
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
															 * @res "����"
															 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000088")/*
													 * @res "Excel�ļ�����Ϊ�գ�"
													 */);
					return;
				}
			} else
				return;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000089")/* @res "��Excel�����ļ�����" */
					+ e.getMessage());
		}
		/** �����볤�Ƚ����������У�� */
		String sMsg = BarcodeparseCtrlUI.verifyLenInfo(getEnvironment()
				.getCorpID(), voaImport);
		if (sMsg != null) {
			showErrorMessage(sMsg);
			return;
		}

		// ��������ж�,ֻ�Ե�һ������У��
		if (voaImport != null && voaImport.length != 0 && voaImport[0] != null) {
			alNewVO = BarcodeparseCtrlUI.getVOInfoNew(voaImport[0],
					getEnvironment().getCorpID());
			if (alNewVO == null) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000059")/*
														 * @res "����"
														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
						"UPP4008bill-000090")/*
												 * @res "�������벻�����������"
												 */);
				return;
			}
			if (iMenu == 0 && alNewVO.get(1) != BarcodeparseCtrl.MAINBARCODE) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000059")/* @res "����" */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPP4008bill-000327")/* @res "�������벻�������룡" */);
				return;
			}
			if (iMenu == 1 && alNewVO.get(1) != BarcodeparseCtrl.SUBBARCODE) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000059")/* @res "����" */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPP4008bill-000328")/* @res "�������벻�Ǵ����룡" */);
				return;
			}
			if (iMenu == 2 && alNewVO.get(1) != BarcodeparseCtrl.BOTHCODEHAVE) {
				MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
						.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000059")/* @res "����" */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPP4008bill-000329")/* @res "�������벻���������룡" */);
				return;
			}
		}
		// ���������͵�������
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

		// ����VO[]
		BarCodeVO[] voaOld = billItemvo.getBarCodeVOs();
		BarCodeVO[] voaOldCopy = null;
		if (voaOld != null) {
			voaOldCopy = new BarCodeVO[voaOld.length];
			for (int i = 0; i < voaOld.length; i++) {
				voaOldCopy[i] = (nc.vo.ic.pub.bc.BarCodeVO) voaOld[i].clone();
			}
		}
		// �޸��ˣ������� �޸����ڣ�2007-12-13����03:23:00 �޸�ԭ��
		nc.vo.ic.pub.GenMethod.fillBarCodeVOsByBarcode(voaImport);
		bcVOTotal = BarcodeparseCtrlUI.barCodeAddWrapper(iMenu, bCover,
				alOldVO, voaOld, voaImport);
		// *���״״̬��ǩ��״̬�£���������ӦС��ʵ������
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
														 * @res "����"
														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
						"UPP4008bill-000091")/*
												 * @res "����������Ӧ����ʵ���շ�������"
												 */);
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000092")/*
															 * @res
															 * "����������Ӧ����ʵ���շ�����"
															 */);
				getM_voBill().getItemVOs()[rownow].setBarCodeVOs(voaOldCopy);
				return;
			}
		}else{
			//�޸��ˣ������� �޸�ʱ�䣺2008-12-27 ����11:38:36 �޸�ԭ�򣺱༭״̬��Ӧ������������
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
		// 5.��VO���ڴ������VO��
		getM_voBill().getItemVOs()[rownow].setBarCodeVOs(bcVOTotal);
		if (bcVOTotal == null || bcVOTotal.equals(voaOld)) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000093")/* @res "û�е���ɹ��������µ���!" */);
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
				"UPP4008bill-000094")/* @res "�ѽ����뵼�뵽����,�뱣��!" */);
		// 6
		try {
			if ((getM_voBill().getHeaderVO().getPrimaryKey() != null)
					&& (getM_iMode() == BillMode.Browse)) {
				if (getClientUI().onImportSignedBillBarcode(getM_voBill(), true) == 1)
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000095")/*
																			 * @res
																			 * "���벢��������ɹ�(��ע���浥���Ƿ����������룩!"
																			 */);
				else {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000096")/*
																			 * @res "
																			 * û�б���ɹ���"
																			 */);
					getM_voBill().getItemVOs()[rownow]
							.setBarCodeVOs(voaOldCopy);
				}
			}
		} catch (Exception e) {
			if (e instanceof nc.vo.ic.ic009.PackCheckBusException) {

				handleException(e);
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000105")/* @res "�������" */);
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
						"4008bill", "UPP4008bill-000097")/* @res "����ʧ�ܣ�" */
						+ e.getMessage());
			}
			getM_voBill().getItemVOs()[rownow].setBarCodeVOs(voaOldCopy);
		}
	}

	/**
	 * ���ߣ�� �ж�ѡ����(����Excel���룩�Ƿ������ݣ��Ƿ�ѡ�С� �������ڣ�(2004-5-4 13:12:20)
	 */
	private int checkSelectionRow() {
		if (getM_iCurPanel() == BillMode.Card) {
			if (getM_iLastSelListHeadRow() != -1 && null != getM_alListData()
					&& getM_alListData().size() != 0) {
				if (getM_voBill() == null)
					setM_voBill((GeneralBillVO) getM_alListData().get(
							getM_iLastSelListHeadRow()));
			}
		} else if (getM_iCurPanel() == BillMode.List) { // �б�
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000065")/* @res "���л�����Ƭ���浼������!" */);
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
					"4008bill", "UPP4008bill-000066")/* @res "û��ѡ�д��!" */);
			return -1;
		}
		if (getM_voBill() == null || getM_voBill().getChildrenVO() == null
				|| getM_voBill().getChildrenVO().length < rownow) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH003")/* @res "��ѡ��Ҫ��������ݣ�" */);
			return -1;
		}
		return rownow;
	}

	/**
	 * �˴����뷽��˵���� ������Դ���ݣ�ֻ�������ӡ�������Ѿ��������¿���ֱ�ӱ��浼�� �������ڣ�(2004-4-20 11:36:04)
	 */
	protected void onImportBarcodeSourceBill() {
		try {

			if (getM_iMode() != BillMode.Browse) {
				// �༭����£�
				// ��ȥ����ʽ�µĿ���
				getClientUI().filterNullLine();
				if (getBillCardPanel().getRowCount() <= 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000072")/*
																			 * @res
																			 * "�����������!"
																			 */);
					return;
				}
				// ������������к�Ϊ�����ģ�����У��
				if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
						getBillCardPanel(), IItemKey.CROWNO)) {
					return;
				}
			}

			// ִ�к�̨�ĵ����������Ҫ�ж������б��»�Ƭ��
			ArrayList alBill = new ArrayList();
			if (getM_iCurPanel() == BillMode.Card) { // ���
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
																				 * "��Ƭ��û�е������ݲ����Ե��룡"
																				 */);
						return;
					}
				} else {
					vo = getM_voBill();
				}
				alBill.add(vo);
			} else if (getM_iCurPanel() == BillMode.List) { // �б�
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000078")/* @res "�б��²����Ե��룡" */);
				return;
			}
			String sHID = null;
			String sBillTypecode = null;
			if (alBill != null && alBill.size() > 0) {
				GeneralBillVO billVO = null;
				StringBuffer sbErr = new StringBuffer("");
				ArrayList alSourceHID = new ArrayList();
				for (int n = 0; n < alBill.size(); n++) {
					// У������
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
																	 * "��ǰ���ݱ���û�����ݲ����Ե��룡"
																	 */);
						continue;
					}

					// ׼������
					sHID = headvo.getCgeneralhid();
					
					sBillTypecode = headvo.getCbilltypecode();
					if (sBillTypecode != null
							&& (sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_otherIn)
									|| sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_otherOut)
									|| sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_allocationOut)
									|| sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_allocationIn) || sBillTypecode
									.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_saleOut))) { // ���Ϲ�����������ⵥ����������ⵥ�����۳�����Ե��뵥������
						// ���Ϲ�����������ⵥ����������ⵥ�����۳�����Ե��뵥������
					} else {
						sbErr.append(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000326")/*
																				 * @res
																				 * "��ǰ���ݱ��壬�����۳��⡢��������ⵥ���������ⵥ�������Ե�����Դ��������"
																				 */);
						showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000079")/*
																				 * @res
																				 * "û����Դ���ݣ����ɵ��룡"
																				 */);
						return;
					}

					java.util.ArrayList alBIDCrowno = new java.util.ArrayList();
					java.util.ArrayList alBIDSourceid = new java.util.ArrayList();
					java.util.Hashtable htbSourceBIDRepeat = new java.util.Hashtable();
					UFBoolean ufbHasHBID = new UFBoolean(true);
					if (sHID == null) {
						// û�б���ID��ֱ���������������false
						ufbHasHBID = new UFBoolean(false);
					}
					String sSourceHID = null;
					// String sFirstHID = null;
					String sCourceTypecode = null; // ��Դ���ݺ�
					boolean bTranBill = false; // �Ƿ�ת��

					String sCsourcebillbid = null;
					java.util.ArrayList alRepeatRow = new java.util.ArrayList(); // �ظ���
					for (int i = 0; i < billItemVOs.length; i++) {
						// if (i == 0) {
						sCourceTypecode = billItemVOs[i].getCsourcetype();
						if (sCourceTypecode == null
								|| sCourceTypecode.trim().length() == 0)
							continue;

						// ת�ⵥ����Դ���ݺ�
						if (sCourceTypecode.startsWith("4")) {
							sSourceHID = billItemVOs[i].getCsourcebillhid();
							bTranBill = true;
						} else // ����������Դͷ���ݺ�
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
							// У���ظ�����Դ����ID
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
																				 * "û����Դ���ݣ����ɵ��룡"
																				 */);
						return;
					}
					// ���������ظ�����Դ�����У�
					if (alRepeatRow != null && alRepeatRow.size() > 0) {
						StringBuffer sbError = new StringBuffer();
						String sRowno = null;
						sbError.append(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000080")/*
																				 * @res
																				 * "���������ظ�����Դ�����У����ܵ�����Դ���ݣ���ϲ���\n"
																				 */);
						for (int i = 0; i < alRepeatRow.size(); i++) {
							sRowno = (String) alRepeatRow.get(i);
							if (i > 0)
								sbError.append(nc.ui.ml.NCLangRes.getInstance()
										.getStrByID("SCMCOMMON",
												"UPPSCMCommon-000000")/*
																		 * @res
																		 * "��"
																		 */);
							sbError.append(sRowno);
						}
						showErrorMessage(sbError.toString());
						return;
					}

					java.util.ArrayList alCon = new ArrayList();
					alCon.add(sHID);
					// ���кŻ�BID��Ϊ����ID����������
					alCon.add(alBIDCrowno);
					alCon.add(alBIDSourceid);
					alCon.add(getEnvironment().getCorpID());
					alCon.add(sBillTypecode);
					alCon.add(ufbHasHBID);
					// alCon.add(sSourceHID);
					alCon.add(alSourceHID);
					alCon.add(sCourceTypecode);

					// ��������
					try {
						java.util.ArrayList alResult = nc.ui.ic.pub.bc.BarCodeImportBO_Client
								.importSourceBarcode(alCon);
                         //�����·���ʵ����Դ����ĸ��ǵ�ǰ���� ������ 2009-11-20
						java.util.ArrayList alresult = nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
								.setImportBarcode(billVO, alResult,true);

						if (alresult.size() > 0) {
							StringBuffer sbMsg = new StringBuffer(
									nc.ui.ml.NCLangRes.getInstance()
											.getStrByID("4008bill",
													"UPP4008bill-000081")/*
																			 * @res
																			 * "���ݵ�����������\n"
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
																	 * "������û�����뵼�룡"
																	 */);
							return;
						}
						// �����ǰ���������£���Ҫֱ�ӱ��浽��̨
						// false:ǰ̨����У�飬��̨У��
						if (getM_iMode() == BillMode.Browse) {
							getClientUI().onImportSignedBillBarcode(billVO, false);
							showHintMessage(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000083")/*
																	 * @res
																	 * "������Դ������ɣ������Ѿ�������ϡ�"
																	 */);
						} else {
							showHintMessage(nc.ui.ml.NCLangRes.getInstance()
									.getStrByID("4008bill",
											"UPP4008bill-000084")/*
																	 * @res
																	 * "������Դ�������뵽��ǰ���ݽ����ϣ���㰴Ŧ�����桱�����浥�ݡ�"
																	 */);
						}

					} catch (Exception e) {
						String[] args = new String[1];
						args[0] = headvo.getVbillcode().toString();
						String message = nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000342",
										null, args);
						/* @res "���ݺ�Ϊ{0}�ĵ���,���ܵ�����Դ���ݵ���������쳣:" */
						sbErr.append(message);
					}
				}
				String sErrMsg = sbErr.toString();
				if (sErrMsg != null && sErrMsg.trim().length() > 0) {
					showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000044")/*
																			 * @res
																			 * "������ʾ��"
																			 */
							+ sErrMsg);
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000085")/*
																			 * @res
																			 * "������Դ����ʧ�ܣ�"
																			 */);

				}

			} else {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000086")/*
															 * @res
															 * "������ʾ��û��ѡ��ĵ���"
															 */);
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000085")/* @res "������Դ����ʧ�ܣ�" */);
			} // �ѵ������ŵ�ǰ̨
		} catch (Exception e) {
			String sErrorMsg = e.getMessage();
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000044")/* @res "������ʾ��" */
					+ sErrorMsg);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000085")/* @res "������Դ����ʧ�ܣ�" */);
		}
	}

	/**
	 * � ���ܣ�ִ������ر� ������ ���أ� ���⣺ ���ڣ�(2004-10-18 10:37:47) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
					"4008bill", "UPP4008bill-000125")/* @res "���ڿ�Ƭģʽ�¹رմ�����" */);
			return;
		} else {
			iaSel = getBillCardPanel().getBillTable().getSelectedRows();
		}
		if (getM_iMode() == BillMode.New) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000126")/* @res "����״̬���ܹرմ����룡" */);
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
		// �޸��ˣ������� �޸����ڣ�2007-12-20����10:37:57 �޸�ԭ��updateValue()�󣬻��޸���״̬��
		// getBillCardPanel().updateValue();
		// �޸��ˣ������� �޸����ڣ�2007-12-17����09:12:25 �޸�ԭ���޸���󣬻ָ��е�ѡ��
		for (int isel : iaSel) {
			if (isel > -1
					&& isel < getBillCardPanel().getBillTable().getRowCount())
				getBillCardPanel().getBillTable().setRowSelectionInterval(isel,
						isel);
		}

		// �޸��ˣ������� �޸����ڣ�2007-8-21����10:23:19 �޸�ԭ������ر�֮��ˢ�µ�TS������Ҫȥˢ���б�����
		// updateBillToList(getM_voBill());
		// �޸��ˣ������� �޸����ڣ�2007-10-24����01:59:14
		// �޸�ԭ���ñ���ʽ�µĵ���ˢ���б����ݣ�ֻ����bbarcodeclose��ts��
		getClientUI().updateBillToListByItemKeys(getM_voBill(), null, new String[] {
				nc.ui.ic.pub.bill.ItemKeyS.bbarcodeclose,
				nc.ui.ic.pub.bill.ItemKeyS.ts });

	}

	/**
	 * ����༭��ť����¼���Ӧ���� �������ڣ�(2003-09-28 9:51:50)
	 */
	protected void onBarCodeEdit() {
		// �ж��Ƿ��ܹ���������༭
		GeneralBillVO voBill = null;

		int iCurFixLine = 0;
		// �Ƿ���Ա༭
		boolean bDirectSave = false;
		if (getM_iMode() == BillMode.Browse || !getClientUI().m_bAddBarCodeField) {
			bDirectSave = true;
		} else {
			bDirectSave = false;
		}

		if (BillMode.List == getM_iCurPanel()) {
			bDirectSave = false;
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000071")/* @res "���ڿ�Ƭģʽ�±༭����" */);
			return;
		} else {
			voBill = getM_voBill();
			iCurFixLine = getBillCardPanel().getBillTable().getSelectedRow();
		}

		// ��ȥ����ʽ�µĿ���
		getClientUI().filterNullLine();
		if (getBillCardPanel().getRowCount() <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000072")/* @res "�����������!" */);
			getBillCardPanel().addLine();
			nc.ui.scm.pub.report.BillRowNo.addLineRowNo(getBillCardPanel(),
					getBillType(), IItemKey.CROWNO);
			return;
		}
		// ����кŵĺϷ���; �÷���Ӧ���ڹ��˿��еĺ��档
		// ��Ҫ���к�ȷ��Ψһ��
		if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
				getBillCardPanel(), IItemKey.CROWNO)) {
			return;
		}
		boolean bEditable = true;

		getClientUI().m_dlgBarCodeEdit = getClientUI().getBarCodeDlg(bEditable, bDirectSave);

		if (voBill != null && iCurFixLine >= 0
				&& iCurFixLine < voBill.getItemCount()) {

			GeneralBillItemVO itemvo = voBill.getItemVOs()[iCurFixLine];
			// ���������Ĵ��
			if (itemvo.getBarcodeManagerflag().booleanValue()) {
				// �õ���ͷ�ĵ��ݺ�, �����к�, �������,�������
				// ArrayList altemp = new ArrayList();

				// ��������£��к�û����m_voBill�д���,���������к�
				getClientUI().getGenBillUICtl().setBillCrowNo(voBill, getBillCardPanel());

				// �����������Items

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

				// �������Ƿ񱣴�������õ�����༭���棬�����ڱ༭���汣������ǰ����
				// m_dlgBarCodeEdit.setSaveBarCode(m_bBarcodeSave);
				getClientUI().m_dlgBarCodeEdit.setSaveBadBarCode(getClientUI().m_bBadBarcodeSave);
				// �޸���:������ �޸�����:2007-04-10
				// �޸�ԭ��:�߼�����,����һ������,�Ƿ񱣴���������bSaveBarcodeFinal
				try {
					if (voBill.bSaveBarcodeFinal())
						getClientUI().m_dlgBarCodeEdit.setSaveBadBarCode(true);
				} catch (BusinessException e) {
					nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null, e);

				}
				// �������رգ���������༭���ܱ༭
				boolean bbarcodeclose = itemvo.getBarcodeClose().booleanValue();
				getClientUI().m_dlgBarCodeEdit.setUIEditableBarcodeClose(!bbarcodeclose);
				getClientUI().m_dlgBarCodeEdit.setUIEditable(getM_iMode());
				// ���õ�Items��
				getClientUI().m_dlgBarCodeEdit.setCurBarcodeItems(itemBarcodeVos, iFixLine);

				if (getClientUI().m_dlgBarCodeEdit.showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
					getBillCardPanel().getBillModel().setNeedCalculate(false);
					// �������������
					getClientUI().getM_utfBarCode().setCurBillItem(itemBarcodeVos);
					// ����Ҫ����������ɾ�����ݣ�m_utfBarCode.setRemoveBarcode(m_dlgBarCodeEdit.getBarCodeDelofAllVOs());

					// Ŀ������m_billvo���������ݣ��޸Ŀ�Ƭ����״̬

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

							if (!getClientUI().m_dlgBarCodeEdit.m_bModifyBillUINum) { // �޸Ŀ�Ƭ����״̬
								showHintMessage(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000073")/*
																		 * @res
																		 * "����༭��Ĳ��������ǲ��޸Ľ������������ݽ���ʵ�����������޸ģ�"
																		 */);
							}

							if (!getClientUI().getBarcodeCtrl().isModifyBillUINum()) {
								showHintMessage(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000074")/*
																		 * @res
																		 * "��ǰ���ݽ��治�����޸�ͨ�����������޸�ʵ�����������ݽ���ʵ�����������޸ģ�"
																		 */);

							}

							if (getClientUI().m_dlgBarCodeEdit.m_bModifyBillUINum
									&& getClientUI().getBarcodeCtrl().isModifyBillUINum()) { // �޸Ŀ�Ƭ����״̬
								showHintMessage(nc.ui.ml.NCLangRes
										.getInstance().getStrByID("4008bill",
												"UPP4008bill-000075")/*
																		 * @res
																		 * "����༭��Ĳ����������޸Ľ����������ҽ�����������޸�ʵ�����������ݽ���ʵ�������Ѿ����޸ģ�"
																		 */);
							}

						}

					}
					// dw
					getClientUI().getEditCtrl().resetSpace(iCurFixLine);

					getBillCardPanel().getBillModel().setNeedCalculate(true);
					getBillCardPanel().getBillModel().reCalcurateAll();

				}

			} else {// �޸���:������ �޸�����:2007-04-05 �޸�ԭ��:���Ǵ���������Ʒ
				nc.ui.ic.pub.tools.GenMethod.handleException(getClientUI(), null,
						new BusinessException(nc.ui.ml.NCLangRes.getInstance()
								.getStrByID("4008bill", "UPP4008bill-000002")/*
																				 * @res
																				 * "���д�������������������������޸Ĵ�������������ԣ�"
																				 */
								+ itemvo.getCinventorycode()));
			}

		} else {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000356")/* @res "��ѡ������У�" */);
		}
	}

	/**
	 * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-5-24 15:54:15) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param iCurFixLine
	 *            int
	 * @param billItemvo
	 *            nc.vo.ic.pub.bill.GeneralBillItemVO
	 */
	private void onBarCodeEditUpdateBill(int iCurFixLine,
			GeneralBillItemVO billItemvo) {

		boolean bNegative = false; // �Ƿ���
		// �޸�ʵ��������Ӧ������
		UFDouble ufdNum = null;
		// ����"�Ƿ񰴸���λ��������"���ԣ�������������Զ���һ�������������Զ���һ��
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

		// ɾ��������
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
		// ����Ӧ�����������ֿ��Ʋ��ܳ�Ӧ���������̵����
		if (ufdNumDlg.doubleValue() > ufdShouldNum.doubleValue()
				&& !getClientUI().getBarcodeCtrl().isOverShouldNum()) {
			ufdNumDlg = ufdShouldNum.abs();
		}

		// ת��Ϊ����
		if (getClientUI().m_bFixBarcodeNegative || bNegative)
			ufdNumDlg = ufdNumDlg.multiply(GeneralBillClientUI.UFDNEGATIVE);

		if (ufdNumDlg == null)
			ufdNumDlg = GeneralBillClientUI.UFDZERO;

		// �������������ֶ�
		try {
			getBillCardPanel().setBodyValueAt(ufdNumDlg.abs(), iCurFixLine,
					IItemKey.NBARCODENUM);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
		}
		// �Ƿ��޸ĵ�������Ҫ�������������ж�
		if (getClientUI().m_dlgBarCodeEdit.m_bModifyBillUINum
				&& getClientUI().getBarcodeCtrl().isModifyBillUINum()
				&& getM_iMode() != BillMode.Browse) {
			// �޸Ŀ�Ƭ����״̬

			getBillCardPanel().setBodyValueAt(ufdNumDlg, iCurFixLine,
					m_sMyItemKey);

			nc.ui.pub.bill.BillEditEvent event1 = new nc.ui.pub.bill.BillEditEvent(
					getBillCardPanel().getBodyItem(m_sMyItemKey), ufdNumDlg,
					m_sMyItemKey, iCurFixLine);
			getClientUI().afterEdit(event1);
			// ִ��ģ�湫ʽ
			getClientUI().getGenBillUICtl().execEditFormula(getBillCardPanel(), iCurFixLine,
					m_sMyItemKey);
			// ����������״̬Ϊ�޸�
			if (getBillCardPanel().getBodyValueAt(iCurFixLine,
					IItemKey.NAME_BODYID) != null)
				getBillCardPanel().getBillModel().setRowState(iCurFixLine,
						BillMode.Update);

		}
	}

	/**
	 * �\n�������ڣ�(2003-3-6 15:13:32) ���ߣ����Ӣ �޸����ڣ� �޸��ˣ� �޸�ԭ�� �㷨˵����
	 */
	protected void onDocument() {
//    try{
    
//      nc.ui.ic.pub.tools.GenMethod.callICEJBService("nc.bs.ic.util.BtnReg", 
//          "getBillBtnRegSql", 
//          new Class[]{
//            String.class,String.class,String.class,String.class,String.class
//          }, 
//          new Object[]{
//            "�в���","����","SCMCOMMONIC55YB003","Alt","R"
//          }
//      );
//      
//      nc.ui.ic.pub.tools.GenMethod.callICEJBService("nc.bs.ic.util.BtnReg", 
//          "getSpecBillBtnRegSql", 
//          new Class[]{
//            String.class,String.class,String.class,String.class,String.class
//          }, 
//          new Object[]{
//            "�в���","����","SCMCOMMONIC55YB003","Alt","R"
//          }
//      );
//      
//    }catch(Exception e){
//      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
//    }
    
		ArrayList alBill = getClientUI().getSelectedBills();
		if (alBill == null || alBill.size() == 0) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000076")/* @res "����ѡ�񵥾ݣ�" */);
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
	 * �˴����뷽��˵���� �������ڣ�(2003-8-25 13:53:58) ��Ӧ�����գ��Զ���дʵ�����գ�
	 */
	protected void onFillNum() {

		// ����
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
	 * �˴����뷽��˵���� �������ڣ�(2003-8-25 13:53:58) ���ⵥ�Զ����, �Ƿ����
	 */
	protected void onPickAuto() {

		if (getM_voBill() == null)
			return;

		if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
				getBillCardPanel(), IItemKey.CROWNO)) {
			return;
		}
		// zhy2005-05-17���õ������� ����Զ������ָ��
		getM_voBill().setHeaderValue("cbilltypecode", getBillType());

		GeneralBillVO voOutBill = (GeneralBillVO) getM_voBill().clone();
		// ���ת���۵Ĳ�������
		String sBillType = voOutBill.getHeaderVO().getCbiztypeid();
		try {
			String sReturn = (new QueryInfo()).queryBusiTypeVerify(sBillType);
			if (sReturn != null && sReturn.equals("C")) {
				nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(),
						nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
								"UPPSCMCommon-000270")/*
														 * @res "��ʾ"
														 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"4008bill", "UPP4008bill-000496")/*
																	 * @res
																	 * "���ת���۲����Զ������"
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
			// zhy����Ǹ���������Ĵ��,û�����븨��λ,�������Զ����
			InvVO invvo = voItems[i].getInv();
			Integer IsAstUOMmgt = invvo.getIsAstUOMmgt();
			if (IsAstUOMmgt != null
					&& IsAstUOMmgt.intValue() == 1
					&& (voItems[i].getCastunitid() == null || voItems[i]
							.getCastunitid().trim().length() == 0)) {
				nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(),
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000270")/*
																	 * @res "��ʾ"
																	 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"4008bill", "UPP4008bill-000511")/*
																	 * @res
																	 * "�����и�����������Ӧ���븨��λ���������Զ������"
																	 */);
				return;
			}

			if (voItems[i].getNshouldoutnum() == null
					|| voItems[i].getNshouldoutnum().doubleValue() < 0) {
				nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(),
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000270")/*
																	 * @res "��ʾ"
																	 */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"4008bill", "UPP4008bill-000117")/*
																	 * @res
																	 * "������Ӧ������С���㣬�������Զ������"
																	 */);
				return;
			}
			// ���˿���,��¼ԭʼ�к�
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
  				// ִ�й�ʽ
  				getBillCardPanel().getBillModel().execFormulasWithVO(voItems,
  						getClientUI().getBodyFormula());
  
  				// ��¼��δ��
  
  				boolean isVendor = false;
  				// ��δ����ID
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
  
  				// �л�λ����
  				if (isLocator || isVendor) {
  					nc.vo.pub.SuperVOUtil.execFormulaWithVOs(voItems,
  							aryItemField11, null);
  
  				}
  
  			}
  		} catch (Exception e) {
  
  			nc.ui.pub.beans.MessageDialog.showErrorDlg(getClientUI(), nc.ui.ml.NCLangRes
  					.getInstance().getStrByID("SCMCOMMON",
  							"UPPSCMCommon-000059")/* @res "����" */,
  					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
  							"UPP4008bill-000118")/* @res "�Զ����ʧ�ܣ�" */
  							+ e.getMessage());
  			return;
  		}
  
  		if (voItems != null && voItems.length > 0) {
  			String key = null;
  			UFDouble dkey = new UFDouble(0);
  			int iCurRow = 0;
  			int icount = 0;// ������
  
  			GeneralBillItemVO voRow = null;
  
  			getBillCardPanel().getBillModel().setNeedCalculate(false);
  
  			int irows = getBillCardPanel().getRowCount();
  			UFDouble dLastRowNo = new UFDouble(0);
  			String sLastRowNo = null;
  			if (irows > 0) {
  				sLastRowNo = (String) getBillCardPanel().getBodyValueAt(
  						irows - 1, "crowno");// ���һ�е��к�
  				dLastRowNo = new UFDouble(sLastRowNo);
  				// �ֽ�������ڵ�һ��
  				//getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
  			}
  
  			//BatchCodeDefSetTool.execFormulaForBatchCode(voItems);
  
  			for (int i = 0; i < voItems.length; i++) {
  				key = voItems[i].getVbodynote2();
  				dkey = new UFDouble(key);
  				voItems[i].procLocNumDigit(getClientUI().m_ScaleValue.getNumScale(),
  						getClientUI().m_ScaleValue.getAssistNumScale());
  				if (!ht.containsKey(key)) {// �����ԭ��
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
  					// ִ�и������ı༭��ʽ��1201
  					getClientUI().execEditFomulas(i, getEnvironment().getNumItemKey());
  					if (getBillCardPanel().getBillModel().getRowState(i) != nc.ui.pub.bill.BillModel.ADD
  							&& getBillCardPanel().getBillModel().getRowState(i) != nc.ui.pub.bill.BillModel.MODIFICATION)
  						getBillCardPanel().getBillModel().setRowState(i,
  								nc.ui.pub.bill.BillModel.MODIFICATION);
  
  					// ���������һ�У���Ҫ�����ٽ�״̬��
  
  					// if(dkey.compareTo(dLastRowNo)<0)
  					// getBillCardPanel().getBillTable().setRowSelectionInterval(i+1,
  					// i+1);
  					// else{
  					// //�ں�������
  					// }
  					// iCurRow=i+1;
  					icount++;
  				} else {// ����ǲ�ֵ��У�������У�Ȼ��vo����
  					voRow = voItems[i];
  					voRow.setCgeneralbid(null);
  					voRow.setCgeneralhid(null);
  					voRow.setVbodynote2(null);
  					voRow.setNshouldoutnum(null);
  					voRow.setNshouldoutassistnum(null);
//  				deleted by lirr 2009-06-02 �޸�ԭ��NCdp200867926 �������ʱ�������к�û��ѡ��
  					//voRow.setSerial(null);
  					voRow.setAttributeValue("cparentid", null);
  					voRow.setBarCodeVOs(null);
  					voRow.setAttributeValue(IItemKey.NBARCODENUM, new UFDouble(
  							0.0));
  					voRow.setBarcodeClose(new nc.vo.pub.lang.UFBoolean('N'));
  					voRow.setAttributeValue(IItemKey.NKDNUM, null);
  
  					// if(key.compareTo(sLastRowNo)<0){
  					if (dkey.compareTo(dLastRowNo) < 0) {
  						// ����
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
  						// ����
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
  
  				// zhy�����,�ӽ���ȡһ��hsl
  				GeneralBillUICtl.synUi2Vo(getBillCardPanel(), voNew,
  						new String[] { "hsl" }, i);
  			}
  
  			getBillCardPanel().getBillModel().setNeedCalculate(true);
  			getBillCardPanel().getBillModel().reCalcurateAll();
  			// �޸��ˣ������� �޸����ڣ�2007-11-20����03:43:09 �޸�ԭ�򣺸�����Ȩ�û�ID
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
  			
//  			ͬ���ж�λ��
			getClientUI().synUIVORowPos();
  			
  			// ��ʼ�����к�����
  			if (isSN)
  				getClientUI().setM_alSerialData(voNew.getSNs());
  			// �л�λ����
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

           //���ӵ��ݲ�����ʽҳǩ 2009-09-29 ������
			nc.ui.ic.pub.pf.ICBillQuery dlgQry = new nc.ui.ic.pub.pf.ICBillQuery(
					getClientUI());
			
			dlgQry.setTempletID(getEnvironment().getCorpID(), "40080608",
					getEnvironment().getUserID(), null, "40089908");

			dlgQry.initData(getEnvironment().getCorpID(), getEnvironment().getUserID(),
					"40089908", null, "4I", nc.vo.ic.pub.BillTypeConst.m_otherIn, null);

			dlgQry.setBillRefModeSelPanel(true);
	
			if (dlgQry.showModal() == nc.ui.pub.beans.MessageDialog.ID_OK) {
				

				// ��Ҫע��
				nc.vo.pub.query.ConditionVO[] voCons = dlgQry.getConditionVO();

				// ��ȡ��ѯ����
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
					   * ʹ�õ����²�ѯģ��
					   * 
					   */
					  public  boolean isNewQryDlg() {
						  return false;
					  }
					  
					  /**
					   * �����²�ѯģ��
					   * 
					   */
					  public QueryConditionDLG createNewQryDlg(){
						  return null;
					  }
					  
					  /**
					   * �����ɲ�ѯģ��
					   * 
					   */
					  public QueryConditionClient createOldQryDlg() {
						  return this.dlg;
					  }
					  
					  
					  /**
					   * ��ʾ������˫����� 
					   */
					  public boolean isShowDoubleTableRef(){
						  return this.dlg.isShowDoubleTableRef();
					  }
					  
					  /**
					   * ʹ���û���ѡ�ڴ�����ѯ�Ի���ʱ���ò�ѯ�Ի�����ʾģʽ�����ӱ��ǵ���
					   * ��Ҫ����ʵ�֣�������Ҫ���öԻ������ʾģʽѡ��
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
					// ��ȡ��ѡVO
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

					// ���ƽ���
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
	 * ���ݵ���Excel �������ڣ�(2003-09-28 9:51:50)
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

		if (iFlag == 1 || iFlag == 3/* ����Ϊxml */) {
			// ���ļ�
			if (getClientUI().getChooser().showSaveDialog(getClientUI()) == javax.swing.JFileChooser.CANCEL_OPTION)
				return;
			sFilePathDir = getClientUI().getChooser().getSelectedFile().toString();
		}
		if (sFilePathDir == null) {
			showHintMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000008")/*�������ļ�������!*/);
			return;
		}

		try {
			boolean isHaveBC = false;
			
			// ����ǰ���б��Ǳ�������������
			if (getM_iCurPanel() == BillMode.Card) { // ���
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000119")/*
															 * @res
															 * "���ڵ��������Ժ�..."
															 */);

				// ׼������
				voBill = getM_voBill();
				if (voBill == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "���Ȳ�ѯ��¼�뵥�ݡ�"
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
															 * @res "��ѡ��Ҫ��������ݣ�"
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
																			 * "�������"
																			 */);
					return;
				}

				// �õ����ݺţ���˾
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
				// ��Ҫ���������Ϣһ�𵼳�
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
							// ���ݺ���Ϣ
							vo.setAttributeValue("billcode", sBillCode); // ���ݺ�
							// ��������Ϣ
							vo.setAttributeValue("rowno", tvos[i].getCrowno()); // �к�
							vo.setAttributeValue("inventorycode", tvos[i]
									.getCinventorycode()); // �������
							vo.setAttributeValue("inventoryname", tvos[i]
									.getInvname()); // �������
							vo.setAttributeValue("billtypecode", sBillTypeCode);
							vo.setAttributeValue("billtypename", sBillTypeName);

							UFDouble dshouldin = null; // Ӧ��
							UFDouble dshouldout = null; // Ӧ��
							UFDouble dShouldnum = null;

							UFDouble din = null; // ʵ��
							UFDouble dout = null; // ʵ��
							UFDouble dnum = null; // Ҫ����������

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
										.toString()); // ���������ʵ��������
							if (dnum != null)
								vo.setAttributeValue("nnum", dnum.toString()); // ���������ʵ��������
							vo.setAttributeValue("free", tvos[i].getVfree0()); // ������Ŀ
							vo.setAttributeValue("batchcode", tvos[i]
									.getVbatchcode()); // ���κ�
							vo.setAttributeValue("cgeneralbid", tvos[i]
									.getCgeneralbid()); // ����pk
							vo.setAttributeValue("cgeneralhid", tvos[i]
									.getCgeneralhid()); // ��ͷpk
							// ������Ϣ
							vo.setBarcode(bvos[j].getVbarcode()); // ������
							vo.setBarcodesub(bvos[j].getVbarcodesub()); // ������
							vo.setPackcode(bvos[j].getVpackcode()); // ������

							v.add(vo);

						}
					} else {

						vo = new ExcelFileVO();
						// ���ݺ���Ϣ
						vo.setAttributeValue("billcode", sBillCode); // ���ݺ�
						// ��������Ϣ
						vo.setAttributeValue("rowno", tvos[i].getCrowno()); // �к�
						vo.setAttributeValue("inventorycode", tvos[i]
								.getCinventorycode()); // �������
						vo.setAttributeValue("inventoryname", tvos[i]
								.getInvname()); // �������
						vo.setAttributeValue("billtypecode", sBillTypeCode);
						vo.setAttributeValue("billtypename", sBillTypeName);

						UFDouble dshouldin = null; // Ӧ��
						UFDouble dshouldout = null; // Ӧ��
						UFDouble dShouldnum = null;

						UFDouble din = null; // ʵ��
						UFDouble dout = null; // ʵ��
						UFDouble dnum = null; // Ҫ����������

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
									.toString()); // ���������ʵ��������
						if (dnum != null)
							vo.setAttributeValue("nnum", dnum.toString()); // ���������ʵ��������
						vo.setAttributeValue("free", tvos[i].getVfree0()); // ������Ŀ
						vo.setAttributeValue("batchcode", tvos[i]
								.getVbatchcode()); // ���κ�
						vo.setAttributeValue("cgeneralbid", tvos[i]
								.getCgeneralbid()); // ����pk
						vo.setAttributeValue("cgeneralhid", tvos[i]
								.getCgeneralhid()); // ��ͷpk
						v.add(vo);
					}
					 
					
					// �Ѿ��õ�vo��Ȼ�󵼳�vo
					vos = new ExcelFileVO[v.size()];
					v.copyInto(vos);
					// ���õ����ӿ�
					// �ļ����ƹ���Icbill+��˾PK+���ݺ�+".xls"
					// sFilePath = sFilePathDir;
					ExcelReadCtrl erc = new ExcelReadCtrl();
					erc.setVOToExcel(vos, sFilePathDir);
					// д״̬
					ExcelReadCtrl erc1 = new ExcelReadCtrl(sFilePathDir, true);
					// д״̬
					erc1
							.setExcelFileFlag(nc.ui.ic.pub.barcodeoffline.IExcelFileFlag.F_NEW);
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000120")/*
																			 * @res
																			 * "�������"
																			 */);
				}
			} else if (getM_iCurPanel() == BillMode.List) { // �б�
				if (null == getM_alListData() || getM_alListData().size() == 0) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("4008bill", "UPP4008bill-000049")/*
																			 * @res
																			 * "���Ȳ�ѯ��¼�뵥�ݡ�"
																			 */);
					return;
				}

				ArrayList alBill = getClientUI().getSelectedBills();

				if (alBill == null || alBill.size() <= 0
						|| alBill.get(0) == null) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("common", "UCH003")/*
															 * @res "��ѡ��Ҫ��������ݣ�"
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
																			 * "�������"
																			 */);
					return;
				}

				for (int k = 0; k < alBill.size(); k++) {
					voBill = (GeneralBillVO) alBill.get(k);
					// �õ����ݺţ���˾
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

					// ����vo
					tvos = (GeneralBillItemVO[]) voBill.getChildrenVO();
					// ��Ҫ���������Ϣһ�𵼳�
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
								// ���ݺ���Ϣ
								vo.setAttributeValue("billcode", sBillCode); // ���ݺ�
								// ���ݺ���Ϣ
								vo.setAttributeValue("billcode", sBillCode); // ���ݺ�
								// ��������Ϣ
								vo.setAttributeValue("rowno", tvos[i]
										.getCrowno()); // �к�
								vo.setAttributeValue("inventorycode", tvos[i]
										.getCinventorycode()); // �������
								vo.setAttributeValue("inventoryname", tvos[i]
										.getInvname()); // �������
								vo.setAttributeValue("billtypecode",
										sBillTypeCode);
								vo.setAttributeValue("billtypename",
										sBillTypeName);

								UFDouble dshouldin = null; // Ӧ��
								UFDouble dshouldout = null; // Ӧ��
								UFDouble dShouldnum = null;

								UFDouble din = null; // ʵ��
								UFDouble dout = null; // ʵ��
								UFDouble dnum = null; // Ҫ����������

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
											dShouldnum.toString()); // ���������ʵ��������
								if (dnum != null)
									vo.setAttributeValue("nnum", dnum
											.toString()); // ���������ʵ��������
								vo.setAttributeValue("free", tvos[i]
										.getVfree0()); // ������Ŀ
								vo.setAttributeValue("batchcode", tvos[i]
										.getVbatchcode()); // ���κ�
								vo.setAttributeValue("cgeneralbid", tvos[i]
										.getCgeneralbid()); // ����pk
								vo.setAttributeValue("cgeneralhid", tvos[i]
										.getCgeneralhid()); // ��ͷpk
								// ��ͷpk //������Ϣ
								vo.setBarcode(bvos[j].getVbarcode()); // ������
								vo.setBarcodesub(bvos[j].getVbarcodesub()); // ������
								vo.setPackcode(bvos[j].getVpackcode()); // ������

								v.add(vo);
							}
						} else {
							vo = new ExcelFileVO();
							vo = new ExcelFileVO();
							// ���ݺ���Ϣ
							vo.setAttributeValue("billcode", sBillCode); // ���ݺ�
							// ��������Ϣ
							vo.setAttributeValue("rowno", tvos[i].getCrowno()); // �к�
							vo.setAttributeValue("inventorycode", tvos[i]
									.getCinventorycode()); // �������
							vo.setAttributeValue("inventoryname", tvos[i]
									.getInvname()); // �������
							vo.setAttributeValue("billtypecode", sBillTypeCode);
							vo.setAttributeValue("billtypename", sBillTypeName);

							UFDouble dshouldin = null; // Ӧ��
							UFDouble dshouldout = null; // Ӧ��
							UFDouble dShouldnum = null;

							UFDouble din = null; // ʵ��
							UFDouble dout = null; // ʵ��
							UFDouble dnum = null; // Ҫ����������

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
										.toString()); // ���������ʵ��������
							if (dnum != null)
								vo.setAttributeValue("nnum", dnum.toString()); // ���������ʵ��������
							vo.setAttributeValue("free", tvos[i].getVfree0()); // ������Ŀ
							vo.setAttributeValue("batchcode", tvos[i]
									.getVbatchcode()); // ���κ�
							vo.setAttributeValue("cgeneralbid", tvos[i]
									.getCgeneralbid()); // ����pk
							vo.setAttributeValue("cgeneralhid", tvos[i]
									.getCgeneralhid()); // ��ͷpk
							v.add(vo);
						}
					}
					// ÿ�ŵ��ݽ�����һ���ļ�
					// �Ѿ��õ�vo��Ȼ�󵼳�vo
					vos = new ExcelFileVO[v.size()];
					v.copyInto(vos);
					// ���õ����ӿ�
					// �ļ����ƹ���Icbill+��˾PK+���ݺ�+".xls"
					// sFilePath = sFilePathDir + "\\" + "Icbill" + sBillCode
					// + ".xls";
					ExcelReadCtrl erc = new ExcelReadCtrl();
					erc.setVOToExcel(vos, sFilePathDir);
					// д״̬
					ExcelReadCtrl erc1 = new ExcelReadCtrl(sFilePathDir, true);
					erc1
							.setExcelFileFlag(nc.ui.ic.pub.barcodeoffline.IExcelFileFlag.F_NEW);
				}
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000120")/* @res "�������" */);
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000120")/* @res "�������" */);
			}
			
			if (!isHaveBC)
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008bill", "UPP4008bill-000569")/* @res "��ѡ��������ĵ���" */);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000121")/* @res "��������" */);
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000121")/* @res "��������" */
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000330")/* @res "��" */
					+ e.getMessage()
					+ ","
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
							"UPP4008bill-000331")/* @res "�ļ�·��" */
					+ ":" + sFilePathDir);
		}
	}

	/**
	 * ���ݵ���XML �������ڣ�(2003-09-28 9:51:50)
	 */
	private void onExportXML(GeneralBillVO[] billvos, String filename) {
		if (billvos == null || billvos.length <= 0 || filename == null)
			return;
		try {
			MessageDialog
					.showInputDlg(getClientUI(), NCLangRes.getInstance().getStrByID("bill", "bill-000009")/*�������ⲿϵͳ����*/, NCLangRes.getInstance().getStrByID("bill", "bill-000010")/*�������ⲿϵͳ����:*/, "20", 5);

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
//				.getBtnMergeShowName()/* @res "�ϲ���ʾ" */);
    
    CollectSettingDlg dlg = new CollectSettingDlg(getClientUI(), getBillType(),
        getClientUI().getFunctionNode(),getClientUI().getEnvironment().getCorpID(),
        getClientUI().getEnvironment().getUserID(),
        GeneralBillVO.class.getName(),
        GeneralBillHeaderVO.class.getName(),
        GeneralBillItemVO.class.getName());
    
    dlg.setBilltype(getClientUI().getBillTypeCode());
    dlg.setNodecode(getClientUI().getFunctionNode());


		dlg.initData(getBillCardPanel(), new String[] { "cinventorycode",
				"invname", "invspec", "invtype" }, // �̶�������
				null,// ȱʡ������
				new String[] { "nshouldinnum", "nneedinassistnum",
						"nshouldoutnum", "nshouldoutassistnum", "ninnum",
						"ninassistnum", "noutnum", "noutassistnum",
						"ningrossnum", "noutgrossnum", "nmny", "nplannedmny",
						"ntarenum" },// �����
				null,// ��ƽ����
				null,// ���Ȩƽ����
				null// ������
				);
		//dlg.show();
		dlg.showModal();
	}

	/**
	 * �����ˣ������� �������ڣ�2007-12-26����09:27:52 ����ԭ���Զ����ý��������е������е��к�
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
		// TODO �Զ����ɷ������

		UIMenuItem item = (UIMenuItem) e.getSource();
		// ����
		if (item == getBillCardPanel().getCopyLineMenuItem()) {

			onCopyLine();
		} else // ճ��
		if (item == getBillCardPanel().getPasteLineMenuItem()) {

			onPasteLine();
		}
		// ճ������βʱ,�����к�
		else if (item == getBillCardPanel().getPasteLineToTailMenuItem()) {
			int iRowCount = getBillCardPanel().getBodyPanel()
					.getTableModel().getRowCount();
			getBillCardPanel().pasteLineToTail();
			// ���ӵ�����
			int iRowCount1 = getBillCardPanel().getBodyPanel()
					.getTableModel().getRowCount();
			nc.ui.scm.pub.report.BillRowNo.addLineRowNos(
					getBillCardPanel(), getBillType(), IItemKey.CROWNO,
					iRowCount1 - iRowCount);
			getClientUI().voBillPastTailLine();

		} else // ����
		if (item == getBillCardPanel().getAddLineMenuItem()) {

			onAddLine();

		} else // ɾ��
		if (item == getBillCardPanel().getDelLineMenuItem())
			onDeleteRow();
		else // ������
		if (item == getBillCardPanel().getInsertLineMenuItem()) {
			onInsertLine();
		}

	}
	
	/**
	 * �����ߣ����˾� ���ܣ���λָ��
	 * 
	 * ������ ���أ� ���⣺ ���ڣ�(2003-7-2 19:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * modified by liuzy 2008-06-26 3.0���ܣ�5.5����
	 * 
	 * 
	 */
	protected void onSelLoc() {
		// warehouse id
		String sNewWhID = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
				.getHeadItem(IItemKey.WAREHOUSE).getComponent()).getRefPK();
		if (sNewWhID == null || sNewWhID.trim().length() == 0) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008bill", "UPP4008bill-000107")/* @res "����ѡ��ֿ�" */);
		} else {
			getClientUI().getLocSelDlg().setWhID(sNewWhID);
			if (getClientUI().getLocSelDlg().showModal() == LocSelDlg.ID_OK) {

				String cspaceid = getClientUI().getLocSelDlg().getLocID();
				String csname = getClientUI().getLocSelDlg().getLocName();
				// �����ֵ�ݴ�
				Object oTempValue = null;
				// ����model
				nc.ui.pub.bill.BillModel bmBill = getBillCardPanel()
						.getBillModel();
				// ����кţ�Ч�ʸ�һЩ��
				int iInvCol = bmBill.getBodyColByKey(IItemKey.INVID);

				// �����д����
				if (bmBill != null && iInvCol >= 0
						&& iInvCol < bmBill.getColumnCount()) {
					// ����
					int iRowCount = getBillCardPanel().getRowCount();
					// �Ӻ���ǰɾ
					for (int line = 0; line < iRowCount; line++) {
						// �������
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
//          "4008other", "UPP4008other-000515")/* @res "û��ѡ�еı����С�" */);
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
          "scmcommon", "UPPSCMCommon-000167")/* @res "û��ѡ�񵥾�" */);
      return;
    }
    
    Class dlgclass = null;
    Object dlg=null;
    try{
      dlgclass = Class.forName("nc.ui.dm.service.delivery.SourceBillDeliveryStatusUI");
      dlg = dlgclass.getConstructor(new Class[]{ToftPanel.class}).newInstance(new Object[]{getClientUI()});
    }catch(Throwable e){
      showErrorMessage(NCLangRes.getInstance().getStrByID("bill", "bill-000011")/*����û�а�װ*/);
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
   * ���ݱ���˵���������.
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
