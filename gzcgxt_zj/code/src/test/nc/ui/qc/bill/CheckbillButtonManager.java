package nc.ui.qc.bill;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.scm.pub.BusiBillManageTool;
import nc.ui.scm.pub.bill.ButtonTree;
import nc.vo.pub.BusinessException;
import nc.vo.scm.qc.bill.CheckbillHeaderVO;
import nc.vo.scm.qc.bill.CheckbillVO;

/**
 * �ʼ쵥�İ�ť�����������ڹ���ť�ĳ�ʼ����״̬��
 * 
 * @ע�ͣ�hanbin
 * lxt 2013-07 cgxf�����ݳ���ϡ����Ŀ���������ӹ������ʼ������Զ���д��
 * 
 */
public class CheckbillButtonManager {
	CheckBillMaintainUI CheckbillDlg = null;

	String m_sUnitCode = ClientEnvironment.getInstance().getCorporation().pk_corp;

	public ButtonTree m_btnTree = null;// ��ť��ʵ��,since v53

	public ButtonObject m_btnAdd = null; // ����

	public ButtonObject m_btnSave = null; // ����

	public ButtonObject m_btnMaintains = null; // ά��

	public ButtonObject m_btnModify = null; // �޸�

	public ButtonObject m_btnCancel = null; // ȡ��

	public ButtonObject m_btnDelBill = null; // ɾ��

	public ButtonObject m_btnLineOprs = null; // �в���

	public ButtonObject m_btnLineAdd = null; // ����

	public ButtonObject m_btnLineDel = null; // ɾ��

	public ButtonObject m_btnLineIns = null; // ������

	public ButtonObject m_btnLineCpy = null; // ������

	public ButtonObject m_btnLinePst = null; // ճ����

	public ButtonObject m_btnAudit = null; // ���

	public ButtonObject m_btnExecutes = null; // ִ��

	public ButtonObject m_btnSendAudit = null; // ����

	public ButtonObject m_btnUnAudit = null; // ����

	public ButtonObject m_btnQuery = null; // ��ѯ

	public ButtonObject m_btnBrowses = null; // ���

	public ButtonObject m_btnRefresh = null; // ˢ��

	public ButtonObject m_btnLocate = null; // ��λ

	public ButtonObject m_btnFirst = null; // ��ҳ

	public ButtonObject m_btnPrev = null; // ��ҳ

	public ButtonObject m_btnNext = null; // ��ҳ

	public ButtonObject m_btnLast = null; // ĩҳ
	
	public ButtonObject m_btnAddSample = null; //lxt 2013-07 cgxf
	
	public ButtonObject m_btnUpdateSample = null; //lxt 2013-07 cgxf

	public ButtonObject m_btnSelectAll = null; // ȫѡ

	public ButtonObject m_btnSelectNo = null; // ȫ��

	public ButtonObject m_btnSwitch = null; // ��Ƭ��ʾ/�б���ʾ

	public ButtonObject m_btnPrints = null; // ��ӡ����

	public ButtonObject m_btnPrint = null; // ��ӡ

	public ButtonObject m_btnAssQueries = null; // ������ѯ

	public ButtonObject m_btnLinkQuery = null; // ����

	public ButtonObject m_btnWorkFlowQuery = null; // ������״̬

	public ButtonObject m_btnMainMaterialQuery = null; // ������������

	public ButtonObject m_btnAssFuncs = null; // ��������

	public ButtonObject m_btnDocument = null; // �ĵ�����

	public ButtonObject m_btnBack = null; // ����

	public ButtonObject m_btnReturnMMQuery = null;

	public ButtonObject m_btnInfoPreviousMMQuery = null;

	public ButtonObject m_btnInfoNextMMQuery = null;

	public ButtonObject m_btnbatch = null;

	public ButtonObject m_btnRevise = null;

	public ButtonObject[] m_btnListMMQuery = null; // new ButtonObject[] {

	public ButtonObject[] m_btnInfoMMQuery = null; // new ButtonObject[] {

	// ���鵥��Ƭ��ť
	public ButtonObject m_btnCard[] = null;

	public ButtonObject m_btnFlow[] = null;// ��������ť

	// ���鵥�б�ť
	public ButtonObject m_btnList[] = null;

	// ������Ϣ��ť
	public ButtonObject m_btnInfo[] = null;

	// ���쵥�ݰ�ť
	public ButtonObject m_btnPray[] = null;

	// �޶�(�������˹��ܽ�������Ϣ���У�û����ButtonTree����)
	public ButtonObject m_btnReviseInfo = null;

	public CheckbillButtonManager(CheckBillMaintainUI dlg) {
		CheckbillDlg = dlg;
	}

	/**
   * ��ʼ����ť(ButtonTree) <Strong>˵����</Strong>
   * <p>
   * 1����Ƭ���б�ť��ͬ
   * <p>
   * 2������㵥��������ʾ���沿�ְ�ť��������
   * <p>
   * 3��������Ϣ���水ť���ְ�ť��������
   * <p>
   * 4��������������������ť���ְ�ť��������
   * <p>
   * 5��֧�ֲ�ҵ����չ��ť����
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����03:21:43
   */
	public void initButtons() {
		// ������ťʵ��
		m_btnAdd = getBtnTree().getButton(IButtonConstCheck.BTN_ADD); // ����
		m_btnSave = getBtnTree().getButton(IButtonConstCheck.BTN_SAVE); // ����
		m_btnMaintains = getBtnTree().getButton(IButtonConstCheck.BTN_BILL); // ά��
		m_btnModify = getBtnTree().getButton(IButtonConstCheck.BTN_BILL_EDIT); // �޸�
		m_btnCancel = getBtnTree().getButton(IButtonConstCheck.BTN_BILL_CANCEL); // ȡ��
		m_btnDelBill = getBtnTree().getButton(IButtonConstCheck.BTN_BILL_DELETE); // ɾ��
		m_btnLineOprs = getBtnTree().getButton(IButtonConstCheck.BTN_LINE); // �в���
		m_btnLineAdd = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_ADD); // ����
		m_btnLineDel = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_DELETE); // ɾ��
		m_btnLineIns = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_INSERT); // ������
		m_btnLineCpy = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_COPY); // ������
		m_btnLinePst = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_PASTE); // ճ����
		m_btnAudit = getBtnTree().getButton(IButtonConstCheck.BTN_AUDIT); // ���
		m_btnExecutes = getBtnTree().getButton(IButtonConstCheck.BTN_EXECUTE); // ִ��
		m_btnSendAudit = getBtnTree().getButton(IButtonConstCheck.BTN_EXECUTE_AUDIT); // ����
		m_btnbatch = getBtnTree().getButton(IButtonConstCheck.BTN_BATCH_OPERATE);// ������
		m_btnUnAudit = getBtnTree().getButton(IButtonConstCheck.BTN_EXECUTE_AUDIT_CANCEL); // ����
		m_btnQuery = getBtnTree().getButton(IButtonConstCheck.BTN_QUERY); // ��ѯ
		m_btnBrowses = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE); // ���
		m_btnRefresh = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_REFRESH); // ˢ��
		m_btnLocate = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_LOCATE); // ��λ
		m_btnFirst = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_TOP); // ��ҳ
		m_btnPrev = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_PREVIOUS); // ��ҳ
		m_btnNext = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_NEXT); // ��ҳ
		m_btnLast = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_BOTTOM); // ĩҳ
		m_btnSelectAll = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_SELECT_ALL); // ȫѡ
		m_btnSelectNo = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_SELECT_NONE); // ȫ��
		m_btnSwitch = getBtnTree().getButton(IButtonConstCheck.BTN_SWITCH); // ��Ƭ��ʾ/�б���ʾ
		m_btnPrints = getBtnTree().getButton(IButtonConstCheck.BTN_PRINT); // ��ӡ����
		m_btnPrint = getBtnTree().getButton(IButtonConstCheck.BTN_PRINT_PRINT); // ��ӡ
		m_btnAssQueries = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_QUERY); // ������ѯ
		m_btnLinkQuery = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_QUERY_RELATED); // ����
		m_btnWorkFlowQuery = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_QUERY_WORKFLOW); // ������״̬
		m_btnMainMaterialQuery = getBtnTree().getButton(IButtonConstCheck.BTN_MASS_QUERY); // ������������
		m_btnAssFuncs = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_FUNC); // ��������
		m_btnDocument = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_FUNC_DOCUMENT); // �ĵ�����
		m_btnBack = getBtnTree().getButton(IButtonConstCheck.BTN_BACK); // ����
		m_btnRevise = getBtnTree().getButton(IButtonConstCheck.BTN_REVISE); // �޶�
		m_btnRevise.setVisible(false);
		//lxt 2013-07 gzcg
		getBtnTree().getButton("��������").setVisible(false);
		getBtnTree().getButton("�������").setVisible(false);
		//lxt 2013-07 gzcg
		// ��Ƭ���б�ť��ʵ����(һ��)
		m_btnCard = getBtnTree().getButtonArray();
		m_btnList = m_btnCard;
		// �����ǰ����㲻���깤���棬��ɾ����ť����������������
		if (!CheckbillDlg.isFromMM()) {
			m_btnAssFuncs.removeChildButton(m_btnMainMaterialQuery);
		}

		// ��Ϣ���水ť��ʵ����{����/�޸�/ɾ��/����/ɾ��/����/ȡ��/��ҳ/��ҳ/��ҳ/ĩҳ}

		// ����ά��
		ButtonObject btnlMaintainsInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101",
				"UPTC0020101-000029")/* @res "����ά��" */, nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101",
				"UPTC0020101-000029")/* @res "����ά��" */, 2, "ά��"/*-=notranslate=-*/); /*-=notranslate=-*/
		btnlMaintainsInfo.addChildButton(m_btnModify);
		btnlMaintainsInfo.addChildButton(m_btnCancel);
		btnlMaintainsInfo.addChildButton(m_btnDelBill);
		// �޶�(δ��ButtonTree)
		m_btnReviseInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000290")/*
                                                                                                                       * @res
                                                                                                                       * "�޶�"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000290")/*
                                                                                         * @res
                                                                                         * "�޶�"
                                                                                         */, 0, "�޶�"/*-=notranslate=-*/); /*-=notranslate=-*/
		btnlMaintainsInfo.addChildButton(m_btnReviseInfo);
		// lxt 2013-07 gzcg
		m_btnAddSample = new ButtonObject("��������", "��������", 2, "��������");
		m_btnUpdateSample = new ButtonObject("�������", "�������", 2, "�������");
		btnlMaintainsInfo.addChildButton(m_btnAddSample);
		btnlMaintainsInfo.addChildButton(m_btnUpdateSample);
		// lxt 2013-07 gzcg
		
		// �в���
		ButtonObject btnLineOpersInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UC001-0000011")/*
                         * @res "�в���"
                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000011")/*
                                                                                 * @res
                                                                                 * "�в���"
                                                                                 */, 2, "�в���"/*-=notranslate=-*/);
		btnLineOpersInfo.addChildButton(m_btnLineAdd);
		btnLineOpersInfo.addChildButton(m_btnLineDel);
		// ���
		ButtonObject btnBrowsesInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UC001-0000021")/*
                         * @res "���"
                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000021")/*
                                                                                 * @res
                                                                                 * "���"
                                                                                 */, 2, "���"/*-=notranslate=-*/);
		btnBrowsesInfo.addChildButton(m_btnFirst);
		btnBrowsesInfo.addChildButton(m_btnPrev);
		btnBrowsesInfo.addChildButton(m_btnNext);
		btnBrowsesInfo.addChildButton(m_btnLast);
		
		m_btnInfo = new ButtonObject[] { m_btnAdd, m_btnSave, btnlMaintainsInfo, btnLineOpersInfo, btnBrowsesInfo,
				m_btnPrint };
		// ���鱨�����水ť��ʵ����{����/��ҳ/��ҳ}
		m_btnPray = new ButtonObject[] { m_btnBack, m_btnPrev, m_btnNext };
		// ���������������水ť��
		m_btnReturnMMQuery = m_btnBack;// ����
		m_btnInfoPreviousMMQuery = m_btnPrev;// ��ҳ
		m_btnInfoNextMMQuery = m_btnNext;// ��ҳ
		m_btnListMMQuery = new ButtonObject[] { m_btnReturnMMQuery };
		m_btnInfoMMQuery = new ButtonObject[] { m_btnReturnMMQuery, m_btnInfoPreviousMMQuery, m_btnInfoNextMMQuery };

		// ֧����չ��ť
		addExtendBtns();
	}

	public ButtonTree getBtnTree() {
		if (m_btnTree == null) {
			try {
				m_btnTree = new ButtonTree("C0020101");
			} catch (BusinessException be) {
				MessageDialog.showErrorDlg(CheckbillDlg, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000000")/*����*/, be.getMessage());
				return null;
			}
		}
		return m_btnTree;
	}

	/**
   * ������չ��ť����Ƭ���档
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����04:07:39
   */
	private void addExtendBtns() {
		ButtonObject[] btnsExtend = getExtendBtns();
		if (btnsExtend == null || btnsExtend.length <= 0) {
			return;
		}
		ButtonObject boExtTop = getBtnTree().getExtTopButton();
		getBtnTree().addMenu(boExtTop);
		int iLen = btnsExtend.length;
		try {
			for (int j = 0; j < iLen; j++) {
				getBtnTree().addChildMenu(boExtTop, btnsExtend[j]);
			}
		} catch (BusinessException be) {
			MessageDialog.showErrorDlg(CheckbillDlg, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000000")/*����*/, be.getMessage());
			return;
		}
	}

	/** Ϊ���ο����ṩ�ӿ� ********************************** */

	/**
   * �������ڣ� 2005-9-20 ���������� ��ȡ��չ��ť���飨ֻ�ṩ��Ƭ���水ť��
   */
	public ButtonObject[] getExtendBtns() {
		return null;
	}

	/**
   * ���水ť״̬-���鵥��Ƭ���(��û���ݵĴ���)��
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����04:44:25
   */
	public void setButtonsStateCard() {
		// ����
		m_btnAdd.setEnabled(CheckbillDlg.isFromQC());
		// ����
		m_btnSave.setEnabled(false);
		// ά��
		m_btnMaintains.setEnabled(true);
		// ��ǰ������Ŀ
		int iBillCnt = CheckbillDlg.m_checkBillVOs == null ? 0 : CheckbillDlg.m_checkBillVOs.length;
		// �޸�
		if (iBillCnt == 0) {
			m_btnModify.setEnabled(false);
		} else {
			m_btnModify.setEnabled(CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanModify());
		}
		// ȡ��
		m_btnCancel.setEnabled(false);
		// ɾ��
		m_btnDelBill.setEnabled(CheckbillDlg.m_checkBillVOs == null ? false
				: CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanDelete());
		// �в���
		m_btnLineOprs.setEnabled(false);
		// ����
		m_btnLineAdd.setEnabled(false);
		// ɾ��
		m_btnLineDel.setEnabled(false);
		// ������
		m_btnLineIns.setEnabled(false);
		// ������
		m_btnLineCpy.setEnabled(false);
		// ճ����
		m_btnLinePst.setEnabled(false);
		// ���
		if (iBillCnt == 0) {
			m_btnAudit.setEnabled(false);
		} else {
			m_btnAudit.setEnabled(CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanAudit());
		}
		// ִ��
		m_btnExecutes.setEnabled(true);
		// ����
		if (iBillCnt == 0) {
			m_btnSendAudit.setEnabled(false);
		} else {
			String curUser = ClientEnvironment.getInstance().getUser().getPrimaryKey();
			CheckbillHeaderVO hvo = (CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO();
			// ��ǰ��¼�û� == �����Ƶ���
			if (curUser != null && curUser.equals(hvo.getCprayerid())) {
				int iBillStatus = ((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO())
						.getIbillstatus();
				if (iBillStatus == 0)
					m_btnSendAudit.setEnabled(true);// ����̬ʱ��һֱ��������
				else {
					boolean bSend = BusiBillManageTool.isNeedSendToAudit(CheckbillDlg.strBilltemplet, m_sUnitCode, null, hvo
							.getCcheckbillid(), curUser);
					m_btnSendAudit.setEnabled(bSend);// ������������Ƿ��������
				}
			} else {
				// �����ǰ��¼�û� != �����Ƶ��ˣ��϶�����������
//				m_btnSendAudit.setEnabled(false);
				//��ǰ��¼�û�!= �����Ƶ���&&��ǰ��¼�û��������������� ��������� -- by ������  ������Դ�� ��ʥ����ERP ����id��NCdp203568758
				boolean bSend = BusiBillManageTool.isNeedSendToAudit(CheckbillDlg.strBilltemplet, m_sUnitCode, null, hvo
						.getCcheckbillid(), curUser);
				m_btnSendAudit.setEnabled(bSend);
			}
		}
		// ����
		if (iBillCnt == 0) {
			m_btnUnAudit.setEnabled(false);
		} else {
			CheckbillVO vo = CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord];
			// ���������У�������==��ǰ�û�ʱ���Խ������������
			if (vo.getHeadVo().getIbillstatus().intValue() == 2) {
				String curUser = ClientEnvironment.getInstance().getUser().getPrimaryKey();
				String auditpsn = vo.getHeadVo().getCauditpsn();
				m_btnUnAudit.setEnabled(StringUtils.equals(curUser, auditpsn));
			} else {
				m_btnUnAudit.setEnabled(vo.isCanUnAudit());
			}
		}
		// ��ѯ
		m_btnQuery.setEnabled(true);
		// ���
		m_btnBrowses.setEnabled(true);
		// ˢ��
		m_btnRefresh.setEnabled(CheckbillDlg.m_checkBillVOs == null ? false : true);
		// ��λ
		m_btnLocate.setEnabled(iBillCnt > 0);
		// ��ҳ����ҳ����ҳ��ĩҳ
		if (iBillCnt == 0 || iBillCnt == 1) {
			m_btnFirst.setEnabled(false);
			m_btnPrev.setEnabled(false);
			m_btnNext.setEnabled(false);
			m_btnLast.setEnabled(false);
		} else if (CheckbillDlg.m_nBillRecord == iBillCnt - 1) {
			m_btnFirst.setEnabled(true);
			m_btnPrev.setEnabled(true);
			m_btnNext.setEnabled(false);
			m_btnLast.setEnabled(false);
		} else if (CheckbillDlg.m_nBillRecord == 0) {
			m_btnFirst.setEnabled(false);
			m_btnPrev.setEnabled(false);
			m_btnNext.setEnabled(true);
			m_btnLast.setEnabled(true);
		} else {
			m_btnFirst.setEnabled(true);
			m_btnPrev.setEnabled(true);
			m_btnNext.setEnabled(true);
			m_btnLast.setEnabled(true);
		}
		// ȫѡ
		m_btnSelectAll.setEnabled(false);
		// ȫ��
		m_btnSelectNo.setEnabled(false);
		// ��Ƭ��ʾ/�б���ʾ
		m_btnSwitch.setEnabled(true);
		m_btnSwitch.setName(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000464")/*
                                                                                               * @res
                                                                                               * "�б���ʾ"
                                                                                               */);
		// ��ӡ����
		m_btnPrints.setEnabled(true);
		// ��ӡ
		m_btnPrint.setEnabled(iBillCnt > 0);
		// ������ѯ
		m_btnAssQueries.setEnabled(true);
		// ����
		m_btnLinkQuery.setEnabled((!CheckbillDlg.isFromQC() && !CheckbillDlg.isFromIC()) && iBillCnt > 0);
		// ������״̬
		m_btnWorkFlowQuery.setEnabled(true);
		// ������������
		if (CheckbillDlg.isFromMM()) {
			m_btnMainMaterialQuery.setEnabled(iBillCnt > 0);
		} else {
			m_btnMainMaterialQuery.setEnabled(false);
		}
		// ��������
		m_btnAssFuncs.setEnabled(true);
		// �ĵ�����
		m_btnDocument.setEnabled(iBillCnt > 0);

		m_btnBack.setEnabled(false);

		m_btnbatch.setEnabled(false);
		// ���°�ť״̬
		updateButtonsAll();
	}

	/**
   * ���水ť״̬-���鵥��Ƭ�޸ġ�
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����04:44:25
   */
	public void setButtonsStateCardModify() {
		// ��ǰ������Ŀ
		int iBillCnt = CheckbillDlg.m_checkBillVOs == null ? 0 : CheckbillDlg.m_checkBillVOs.length;
		if (!CheckbillDlg.m_bBillEdit) {
			m_btnDelBill.setEnabled(((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord]
					.getParentVO()).getIbillstatus() == 3 ? false : true);
			m_btnModify
					.setEnabled(((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO())
							.getIbillstatus() == 3 ? false : true);
			// ����
			m_btnAdd.setEnabled(CheckbillDlg.isFromQC());
			// �в���
			m_btnLineOprs.setEnabled(!CheckbillDlg.isFromIC());
			// ��ҳ����ҳ����ҳ��ĩҳ
			if (iBillCnt == 0 || iBillCnt == 1) {
				m_btnFirst.setEnabled(false);
				m_btnPrev.setEnabled(false);
				m_btnNext.setEnabled(false);
				m_btnLast.setEnabled(false);
			} else if (CheckbillDlg.m_nBillRecord == iBillCnt - 1) {
				m_btnFirst.setEnabled(true);
				m_btnPrev.setEnabled(true);
				m_btnNext.setEnabled(false);
				m_btnLast.setEnabled(false);
			} else if (CheckbillDlg.m_nBillRecord == 0) {
				m_btnFirst.setEnabled(false);
				m_btnPrev.setEnabled(false);
				m_btnNext.setEnabled(true);
				m_btnLast.setEnabled(true);
			} else {
				m_btnFirst.setEnabled(true);
				m_btnPrev.setEnabled(true);
				m_btnNext.setEnabled(true);
				m_btnLast.setEnabled(true);
			}
			// ���°�ť״̬
			updateButtonsAll();
			return;
		}
		// ����
		m_btnAdd.setEnabled(false);
		// ����
		m_btnSave.setEnabled(true);
		// ά��
		m_btnMaintains.setEnabled(true);
		// �޸�
		m_btnModify.setEnabled(false);
		// ȡ��
		m_btnCancel.setEnabled(true);
		// ɾ��
		m_btnDelBill.setEnabled(false);
		// �в���
		m_btnLineOprs.setEnabled(!CheckbillDlg.isFromIC());
		if (CheckbillDlg.isFromQC()) {
			// �ֹ������������
			m_btnLineAdd.setEnabled(true);
		} else {
			// �������첻������
			m_btnLineAdd.setEnabled(false);
			CheckbillDlg.getBillCardPanel().getAddLineMenuItem().setEnabled(false);
		}
		// ������
		if (CheckbillDlg.isFromQC()) {
			// �ֹ�������Բ���
			m_btnLineIns.setEnabled(true);
		} else {
			// �������첻�ܲ���
			m_btnLineIns.setEnabled(false);
			CheckbillDlg.getBillCardPanel().getInsertLineMenuItem().setEnabled(false);
			UIMenuItem[] items = CheckbillDlg.getBillCardPanel().getBodyMenuItems();
			Vector vecData = new Vector();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText().equals(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000001")/*����*/) || items[i].getText().equals(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000002")/*������*/)) {
					continue;
				}
				vecData.add(items[i]);
			}
			UIMenuItem[] bodyItems = new UIMenuItem[vecData.size()];
			vecData.copyInto(bodyItems);
			CheckbillDlg.getBillCardPanel().setBodyMenu(bodyItems);
		}
		m_btnLineDel.setEnabled(true);
		// ������
		m_btnLineCpy.setEnabled(true);
		// ճ����
		m_btnLinePst.setEnabled(true);
		// ���
		m_btnAudit.setEnabled(false);
		// ִ��
		m_btnExecutes.setEnabled(false);
		// ����
		boolean bSend = false;
		if (!CheckbillDlg.m_bBillAdd) {
			bSend = BusiBillManageTool
					.isNeedSendToAudit(CheckbillDlg.strBilltemplet, m_sUnitCode, null,
							((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO())
									.getCcheckbillid(), ClientEnvironment.getInstance().getUser().getPrimaryKey());
		}
		m_btnSendAudit.setEnabled(bSend);
		// ����
		m_btnUnAudit.setEnabled(false);
		// ��ѯ
		m_btnQuery.setEnabled(false);
		// ���
		m_btnBrowses.setEnabled(false);
		// ˢ��
		m_btnRefresh.setEnabled(false);
		// ��λ
		m_btnLocate.setEnabled(false);

		// ��ҳ����ҳ����ҳ��ĩҳ
		if (iBillCnt == 0 || iBillCnt == 1) {
			this.m_btnFirst.setEnabled(false);
			this.m_btnPrev.setEnabled(false);
			this.m_btnNext.setEnabled(false);
			this.m_btnLast.setEnabled(false);
		} else if (CheckbillDlg.m_nBillRecord == iBillCnt - 1) {
			this.m_btnFirst.setEnabled(true);
			this.m_btnPrev.setEnabled(true);
			this.m_btnNext.setEnabled(false);
			this.m_btnLast.setEnabled(false);
		} else if (CheckbillDlg.m_nBillRecord == 0) {
			this.m_btnFirst.setEnabled(false);
			this.m_btnPrev.setEnabled(false);
			this.m_btnNext.setEnabled(true);
			this.m_btnLast.setEnabled(true);
		} else {
			this.m_btnFirst.setEnabled(true);
			this.m_btnPrev.setEnabled(true);
			this.m_btnNext.setEnabled(true);
			this.m_btnLast.setEnabled(true);
		}
		// ȫѡ
		this.m_btnSelectAll.setEnabled(false);
		// ȫ��
		this.m_btnSelectNo.setEnabled(false);
		// ��Ƭ��ʾ/�б���ʾ
		this.m_btnSwitch.setEnabled(false);
		this.m_btnSwitch.setName(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000464")/*
                                                                                                     * @res
                                                                                                     * "�б���ʾ"
                                                                                                     */);
		// ��ӡ����
		this.m_btnPrints.setEnabled(false);
		// ��ӡ
		this.m_btnPrint.setEnabled(false);
		// ������ѯ
		this.m_btnAssQueries.setEnabled(false);
		// ����
		this.m_btnLinkQuery.setEnabled(false);
		// ������״̬
		this.m_btnWorkFlowQuery.setEnabled(false);
		// ������������
		this.m_btnMainMaterialQuery.setEnabled(false);
		// ��������
		this.m_btnAssFuncs.setEnabled(false);
		// �ĵ�����
		this.m_btnDocument.setEnabled(false);

		this.m_btnbatch.setEnabled(true);

		// ���°�ť״̬
		updateButtonsAll();
	}

	/**
   * ���水ť״̬-���鵥�б����(��û���ݵĴ���)��
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����04:44:25
   */
	public void setButtonsStateList() {
		// ����
		this.m_btnAdd.setEnabled(false);
		// ����
		this.m_btnSave.setEnabled(false);
		// ά��
		this.m_btnMaintains.setEnabled(true);
		// ��ǰ������Ŀ
		int iBillCnt = CheckbillDlg.m_checkBillVOs == null ? 0 : CheckbillDlg.m_checkBillVOs.length;
		// ѡ�е�����
		int iSelCnt = CheckbillDlg.getBillListPanel().getHeadTable().getSelectedRowCount();
		// ѡ�е�������
		int[] iSelRows = CheckbillDlg.getBillListPanel().getHeadTable().getSelectedRows();
		// �޸�
		if (iBillCnt == 0 || iSelCnt > 1) {
			this.m_btnModify.setEnabled(false);
		} else {
			if (CheckbillDlg.m_nBillRecord == -1) {
				CheckbillDlg.m_nBillRecord = 0;
			}
			this.m_btnModify.setEnabled(iSelRows.length > 0 && CheckbillDlg.m_checkBillVOs[iSelRows[0]].isCanModify());
		}
		// ȡ��
		this.m_btnCancel.setEnabled(false);
		// ɾ��
		if (iBillCnt == 0 || iSelCnt == 0) {
			this.m_btnDelBill.setEnabled(false);
		} else if (iSelCnt == 1) {
			this.m_btnDelBill.setEnabled(m_btnModify.isEnabled());
		} else if (iSelCnt > 1) {
			boolean bSel = true;
			for (int i = 0; i < iSelCnt; i++) {
				bSel = CheckbillDlg.m_checkBillVOs[iSelRows[i]].isCanModify();
				if (!bSel)
					break;
			}
			this.m_btnDelBill.setEnabled(bSel);
		} else {
			this.m_btnDelBill.setEnabled(true);
		}
		// �в���
		this.m_btnLineOprs.setEnabled(false);
		// ����
		this.m_btnLineAdd.setEnabled(false);
		// ɾ��
		this.m_btnLineDel.setEnabled(false);
		// ������
		this.m_btnLineIns.setEnabled(false);
		// ������
		this.m_btnLineCpy.setEnabled(false);
		// ճ����
		this.m_btnLinePst.setEnabled(false);
		// ���
		if (iBillCnt == 0 || iSelCnt == 0) {
			this.m_btnAudit.setEnabled(false);
		} else if (iSelCnt == 1) {
			this.m_btnAudit.setEnabled(CheckbillDlg.m_checkBillVOs[iSelRows[0]].isCanAudit());
		} else if (iSelCnt > 1) {
			boolean bSel = true;
			for (int i = 0; i < iSelCnt; i++) {
				bSel = CheckbillDlg.m_checkBillVOs[iSelRows[i]].isCanAudit();
				if (!bSel)
					break;
			}
			this.m_btnAudit.setEnabled(bSel);
		} else {
			this.m_btnAudit.setEnabled(true);
		}
		// ִ��
		this.m_btnExecutes.setEnabled(true);
		// ����(�б�δ֧��)
		this.m_btnSendAudit.setEnabled(false);
		// ����
		if (iBillCnt == 0 || iSelCnt == 0) {
			this.m_btnUnAudit.setEnabled(false);
		} else if (iSelCnt == 1) {
			CheckbillVO vo = CheckbillDlg.m_checkBillVOs[iSelRows[0]];
			// ���������У�������==��ǰ�û�ʱ���Խ������������
			if (vo.getHeadVo().getIbillstatus().intValue() == 2) {
				String curUser = ClientEnvironment.getInstance().getUser().getPrimaryKey();
				String auditpsn = vo.getHeadVo().getCauditpsn();
				m_btnUnAudit.setEnabled(StringUtils.equals(curUser, auditpsn));
			} else {
				m_btnUnAudit.setEnabled(vo.isCanUnAudit());
			}
		} else if (iSelCnt > 1) {
			boolean bSel = true;
			int[] iSel = CheckbillDlg.getBillListPanel().getHeadTable().getSelectedRows();
			for (int i = 0; i < iSelCnt; i++) {
				bSel = CheckbillDlg.m_checkBillVOs[iSel[i]].isCanUnAudit();
				if (!bSel)
					break;
			}
			this.m_btnUnAudit.setEnabled(bSel);
		} else {
			this.m_btnUnAudit.setEnabled(true);
		}
		// ��ѯ
		this.m_btnQuery.setEnabled(true);
		// ���
		this.m_btnBrowses.setEnabled(true);
		// ˢ��
		this.m_btnRefresh.setEnabled(CheckbillDlg.m_bQueried);
		// ��λ(�б�λ��ģ��ؼ�����֧�ִ˰�ť����)
		this.m_btnLocate.setEnabled(false);
		// ��ҳ����ҳ����ҳ��ĩҳ
		this.m_btnFirst.setEnabled(false);
		this.m_btnPrev.setEnabled(false);
		this.m_btnNext.setEnabled(false);
		this.m_btnLast.setEnabled(false);
		// ȫѡ
		if (iBillCnt == 0 || iBillCnt == iSelCnt) {
			this.m_btnSelectAll.setEnabled(false);
		} else {
			this.m_btnSelectAll.setEnabled(true);
		}
		// ȫ��
		if (iBillCnt > 0 && iSelCnt != 0) {
			this.m_btnSelectNo.setEnabled(true);
		} else {
			this.m_btnSelectNo.setEnabled(false);
		}
		// ��Ƭ��ʾ/�б���ʾ
		this.m_btnSwitch.setEnabled(iSelCnt == 1 || iBillCnt == 0);
		//
		this.m_btnSwitch.setName(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000463")/*
                                                                                                     * @res
                                                                                                     * "��Ƭ��ʾ"
                                                                                                     */);
		// ��ӡ����
		this.m_btnPrints.setEnabled(true);
		// ��ӡ
		this.m_btnPrint.setEnabled(iSelCnt != 0);
		// ������ѯ
		this.m_btnAssQueries.setEnabled(true);
		// ����
		this.m_btnLinkQuery.setEnabled((!CheckbillDlg.isFromQC() && !CheckbillDlg.isFromIC()) && iSelCnt == 1);
		// ������״̬
		this.m_btnWorkFlowQuery.setEnabled(iSelCnt == 1);
		// ������������
		if (CheckbillDlg.isFromMM()) {
			this.m_btnMainMaterialQuery.setEnabled(iSelCnt == 1);
		}
		// ��������
		this.m_btnAssFuncs.setEnabled(true);
		// �ĵ�����
		this.m_btnDocument.setEnabled(iSelCnt > 0);

		// ���°�ť״̬
		updateButtonsAll();
	}

	/**
   * ���水ť״̬-������Ϣ��Ƭ���(��û���ݵĴ���)��
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����04:44:25
   */
	public void setButtonsStateCardInfo() {
		// ����
		this.m_btnAdd.setEnabled(false);
		// ��ǰ������Ŀ
		int iInfoCnt = 0;
		if (CheckbillDlg.m_grandVOs != null) {
			iInfoCnt = CheckbillDlg.m_grandVOs == null ? 0 : CheckbillDlg.m_grandVOs.length;
		}
		// ά��
		this.m_btnMaintains.setEnabled(true);
		// �в���
		this.m_btnLineOprs.setEnabled(true);
		// ���
		this.m_btnBrowses.setEnabled(true);
		// ����
		this.m_btnSave.setEnabled(false);
		// ȡ��
		this.m_btnCancel.setEnabled(false);
		
		this.m_btnUpdateSample.setEnabled(false); //lxt 2013-07 gzcg
		// ����
		this.m_btnLineAdd.setEnabled(false);
		// �޶�
		this.m_btnReviseInfo.setEnabled(false);
		// ɾ��
		this.m_btnLineDel.setEnabled(false);
		// ���״̬�£������ĵ���
		if (CheckbillDlg.m_checkBillVOs != null && CheckbillDlg.m_nBillRecord != -1
				&& !(CheckbillDlg.m_bBillAdd || CheckbillDlg.m_bBillEdit)) {
			if (CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanUnAudit()) {
				// �޶�
				this.m_btnReviseInfo.setEnabled(true);// �����޶�����
			}
		}
		// û����Ϣ����
		if (iInfoCnt == 0) {
			// ����
			this.m_btnAdd.setEnabled(true);
			// �޸�
			this.m_btnModify.setEnabled(false);
			// ɾ��
			this.m_btnDelBill.setEnabled(false);
			// �޶�
			this.m_btnReviseInfo.setEnabled(false);
			updateButton(m_btnReviseInfo);
			// ��ҳ
			this.m_btnFirst.setEnabled(false);
			// ��ҳ
			this.m_btnPrev.setEnabled(false);
			// ��ҳ
			this.m_btnNext.setEnabled(false);
			// ĩҳ
			this.m_btnLast.setEnabled(false);
			// ���°�ť״̬
			updateButtonsAll();
			return;
		}

		// �޸�
		this.m_btnModify.setEnabled(true);
		// ɾ��
		this.m_btnDelBill.setEnabled(true);
		
		this.m_btnAddSample.setEnabled(true); // lxt 2013-07 gzcg
		// // �޶�
		// this.m_btnReviseInfo
		// .setEnabled(false);
		updateButton(this.m_btnReviseInfo);
		// ��ҳ����ҳ����ҳ��ĩҳ
		if (iInfoCnt == 0 || iInfoCnt == 1) {
			this.m_btnFirst.setEnabled(false);
			this.m_btnPrev.setEnabled(false);
			this.m_btnNext.setEnabled(false);
			this.m_btnLast.setEnabled(false);
		} else if (CheckbillDlg.m_nInfoRecord == iInfoCnt - 1) {
			this.m_btnFirst.setEnabled(true);
			this.m_btnPrev.setEnabled(true);
			this.m_btnNext.setEnabled(false);
			this.m_btnLast.setEnabled(false);
		} else if (CheckbillDlg.m_nInfoRecord == 0) {
			this.m_btnFirst.setEnabled(false);
			this.m_btnPrev.setEnabled(false);
			this.m_btnNext.setEnabled(true);
			this.m_btnLast.setEnabled(true);
		} else {
			this.m_btnFirst.setEnabled(true);
			this.m_btnPrev.setEnabled(true);
			this.m_btnNext.setEnabled(true);
			this.m_btnLast.setEnabled(true);
		}
		// ���°�ť״̬
		updateButtonsAll();
		updateButton(m_btnAddSample);
		updateButton(m_btnUpdateSample);
	}

	/**
   * ���水ť״̬-������Ϣ��Ƭ�޸ġ�
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 ����04:44:25
   */
	public void setButtonsStateCardInfoModify() {
		// ����
		this.m_btnAdd.setEnabled(false);
		// ά��
		this.m_btnMaintains.setEnabled(true);
		// �в���
		this.m_btnLineOprs.setEnabled(true);
		// ���
		this.m_btnBrowses.setEnabled(true);
		// ����
		this.m_btnSave.setEnabled(true);
		// ȡ��
		this.m_btnCancel.setEnabled(true);
		
		this.m_btnUpdateSample.setEnabled(true); //lxt 2013-07 gzcg
		// ����
		this.m_btnLineAdd.setEnabled(true);
		// ɾ��
		this.m_btnLineDel.setEnabled(true);

		// �޸�
		this.m_btnModify.setEnabled(false);
		// ɾ��
		this.m_btnDelBill.setEnabled(false);
		
		this.m_btnAddSample.setEnabled(false); // lxt 2013-07 gzcg
		// �޶�
		this.m_btnReviseInfo.setEnabled(false);
		updateButton(this.m_btnReviseInfo);

		this.m_btnFirst.setEnabled(false);
		this.m_btnPrev.setEnabled(false);
		this.m_btnNext.setEnabled(false);
		this.m_btnLast.setEnabled(false);

		// ���°�ť״̬
		updateButtonsAll();
		updateButton(m_btnAddSample);
		updateButton(m_btnUpdateSample);
	}

	public void setButtonsStateCardWorkFlow() {
		this.m_btnAudit.setEnabled(true);
		this.m_btnUnAudit.setEnabled(true);
		this.m_btnAssQueries.setEnabled(true);
		this.m_btnAssFuncs.setEnabled(true);
		this.m_btnDocument.setEnabled(true);
		this.m_btnWorkFlowQuery.setEnabled(true);
		//
		updateButtonsAll();
	}

	/**
   * ��ť�߼�-��ѯ����������
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-26 ����03:51:26
   */
	public void setButtonsStateListMMQuery() {
		this.m_btnBack.setEnabled(true);
		this.m_btnPrev.setEnabled(true);
		this.m_btnNext.setEnabled(true);
		updateButtonsAll();
	}

	private void updateButtonsAll() {
		CheckbillDlg.updateButtonsAll();
	}

	private void updateButton(ButtonObject bo) {
		CheckbillDlg.updateButton(bo);
	}
}
