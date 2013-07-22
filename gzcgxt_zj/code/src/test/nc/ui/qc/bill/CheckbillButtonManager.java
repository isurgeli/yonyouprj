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
 * 质检单的按钮管理器，用于管理按钮的初始化、状态等
 * 
 * @注释：hanbin
 * lxt 2013-07 cgxf：赣州晨光稀土项目，样本增加功能与质检数据自动填写。
 * 
 */
public class CheckbillButtonManager {
	CheckBillMaintainUI CheckbillDlg = null;

	String m_sUnitCode = ClientEnvironment.getInstance().getCorporation().pk_corp;

	public ButtonTree m_btnTree = null;// 按钮树实例,since v53

	public ButtonObject m_btnAdd = null; // 增加

	public ButtonObject m_btnSave = null; // 保存

	public ButtonObject m_btnMaintains = null; // 维护

	public ButtonObject m_btnModify = null; // 修改

	public ButtonObject m_btnCancel = null; // 取消

	public ButtonObject m_btnDelBill = null; // 删除

	public ButtonObject m_btnLineOprs = null; // 行操作

	public ButtonObject m_btnLineAdd = null; // 增行

	public ButtonObject m_btnLineDel = null; // 删行

	public ButtonObject m_btnLineIns = null; // 插入行

	public ButtonObject m_btnLineCpy = null; // 复制行

	public ButtonObject m_btnLinePst = null; // 粘贴行

	public ButtonObject m_btnAudit = null; // 审核

	public ButtonObject m_btnExecutes = null; // 执行

	public ButtonObject m_btnSendAudit = null; // 送审

	public ButtonObject m_btnUnAudit = null; // 弃审

	public ButtonObject m_btnQuery = null; // 查询

	public ButtonObject m_btnBrowses = null; // 浏览

	public ButtonObject m_btnRefresh = null; // 刷新

	public ButtonObject m_btnLocate = null; // 定位

	public ButtonObject m_btnFirst = null; // 首页

	public ButtonObject m_btnPrev = null; // 上页

	public ButtonObject m_btnNext = null; // 下页

	public ButtonObject m_btnLast = null; // 末页
	
	public ButtonObject m_btnAddSample = null; //lxt 2013-07 cgxf
	
	public ButtonObject m_btnUpdateSample = null; //lxt 2013-07 cgxf

	public ButtonObject m_btnSelectAll = null; // 全选

	public ButtonObject m_btnSelectNo = null; // 全消

	public ButtonObject m_btnSwitch = null; // 卡片显示/列表显示

	public ButtonObject m_btnPrints = null; // 打印管理

	public ButtonObject m_btnPrint = null; // 打印

	public ButtonObject m_btnAssQueries = null; // 辅助查询

	public ButtonObject m_btnLinkQuery = null; // 联查

	public ButtonObject m_btnWorkFlowQuery = null; // 审批流状态

	public ButtonObject m_btnMainMaterialQuery = null; // 联查主料质量

	public ButtonObject m_btnAssFuncs = null; // 辅助功能

	public ButtonObject m_btnDocument = null; // 文档管理

	public ButtonObject m_btnBack = null; // 返回

	public ButtonObject m_btnReturnMMQuery = null;

	public ButtonObject m_btnInfoPreviousMMQuery = null;

	public ButtonObject m_btnInfoNextMMQuery = null;

	public ButtonObject m_btnbatch = null;

	public ButtonObject m_btnRevise = null;

	public ButtonObject[] m_btnListMMQuery = null; // new ButtonObject[] {

	public ButtonObject[] m_btnInfoMMQuery = null; // new ButtonObject[] {

	// 检验单卡片按钮
	public ButtonObject m_btnCard[] = null;

	public ButtonObject m_btnFlow[] = null;// 审批流按钮

	// 检验单列表按钮
	public ButtonObject m_btnList[] = null;

	// 检验信息按钮
	public ButtonObject m_btnInfo[] = null;

	// 报检单据按钮
	public ButtonObject m_btnPray[] = null;

	// 修订(特例，此功能仅检验信息处有，没有走ButtonTree功能)
	public ButtonObject m_btnReviseInfo = null;

	public CheckbillButtonManager(CheckBillMaintainUI dlg) {
		CheckbillDlg = dlg;
	}

	/**
   * 初始化按钮(ButtonTree) <Strong>说明：</Strong>
   * <p>
   * 1、卡片、列表按钮相同
   * <p>
   * 2、报检点单据联查显示界面部分按钮单独处理
   * <p>
   * 3、检验信息界面按钮部分按钮单独处理
   * <p>
   * 4、联查制造主料质量按钮部分按钮单独处理
   * <p>
   * 5、支持产业链扩展按钮处理
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午03:21:43
   */
	public void initButtons() {
		// 创建按钮实例
		m_btnAdd = getBtnTree().getButton(IButtonConstCheck.BTN_ADD); // 增加
		m_btnSave = getBtnTree().getButton(IButtonConstCheck.BTN_SAVE); // 保存
		m_btnMaintains = getBtnTree().getButton(IButtonConstCheck.BTN_BILL); // 维护
		m_btnModify = getBtnTree().getButton(IButtonConstCheck.BTN_BILL_EDIT); // 修改
		m_btnCancel = getBtnTree().getButton(IButtonConstCheck.BTN_BILL_CANCEL); // 取消
		m_btnDelBill = getBtnTree().getButton(IButtonConstCheck.BTN_BILL_DELETE); // 删除
		m_btnLineOprs = getBtnTree().getButton(IButtonConstCheck.BTN_LINE); // 行操作
		m_btnLineAdd = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_ADD); // 增行
		m_btnLineDel = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_DELETE); // 删行
		m_btnLineIns = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_INSERT); // 插入行
		m_btnLineCpy = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_COPY); // 复制行
		m_btnLinePst = getBtnTree().getButton(IButtonConstCheck.BTN_LINE_PASTE); // 粘贴行
		m_btnAudit = getBtnTree().getButton(IButtonConstCheck.BTN_AUDIT); // 审核
		m_btnExecutes = getBtnTree().getButton(IButtonConstCheck.BTN_EXECUTE); // 执行
		m_btnSendAudit = getBtnTree().getButton(IButtonConstCheck.BTN_EXECUTE_AUDIT); // 送审
		m_btnbatch = getBtnTree().getButton(IButtonConstCheck.BTN_BATCH_OPERATE);// 批处理
		m_btnUnAudit = getBtnTree().getButton(IButtonConstCheck.BTN_EXECUTE_AUDIT_CANCEL); // 弃审
		m_btnQuery = getBtnTree().getButton(IButtonConstCheck.BTN_QUERY); // 查询
		m_btnBrowses = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE); // 浏览
		m_btnRefresh = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_REFRESH); // 刷新
		m_btnLocate = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_LOCATE); // 定位
		m_btnFirst = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_TOP); // 首页
		m_btnPrev = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_PREVIOUS); // 上页
		m_btnNext = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_NEXT); // 下页
		m_btnLast = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_BOTTOM); // 末页
		m_btnSelectAll = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_SELECT_ALL); // 全选
		m_btnSelectNo = getBtnTree().getButton(IButtonConstCheck.BTN_BROWSE_SELECT_NONE); // 全消
		m_btnSwitch = getBtnTree().getButton(IButtonConstCheck.BTN_SWITCH); // 卡片显示/列表显示
		m_btnPrints = getBtnTree().getButton(IButtonConstCheck.BTN_PRINT); // 打印管理
		m_btnPrint = getBtnTree().getButton(IButtonConstCheck.BTN_PRINT_PRINT); // 打印
		m_btnAssQueries = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_QUERY); // 辅助查询
		m_btnLinkQuery = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_QUERY_RELATED); // 联查
		m_btnWorkFlowQuery = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_QUERY_WORKFLOW); // 审批流状态
		m_btnMainMaterialQuery = getBtnTree().getButton(IButtonConstCheck.BTN_MASS_QUERY); // 联查主料质量
		m_btnAssFuncs = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_FUNC); // 辅助功能
		m_btnDocument = getBtnTree().getButton(IButtonConstCheck.BTN_ASSIST_FUNC_DOCUMENT); // 文档管理
		m_btnBack = getBtnTree().getButton(IButtonConstCheck.BTN_BACK); // 返回
		m_btnRevise = getBtnTree().getButton(IButtonConstCheck.BTN_REVISE); // 修订
		m_btnRevise.setVisible(false);
		//lxt 2013-07 gzcg
		getBtnTree().getButton("增加样本").setVisible(false);
		getBtnTree().getButton("检测数据").setVisible(false);
		//lxt 2013-07 gzcg
		// 卡片、列表按钮组实例化(一致)
		m_btnCard = getBtnTree().getButtonArray();
		m_btnList = m_btnCard;
		// 如果当前报检点不是完工报告，则删除按钮“联查主料质量”
		if (!CheckbillDlg.isFromMM()) {
			m_btnAssFuncs.removeChildButton(m_btnMainMaterialQuery);
		}

		// 信息界面按钮组实例化{增加/修改/删除/增行/删行/保存/取消/首页/上页/下页/末页}

		// 单据维护
		ButtonObject btnlMaintainsInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101",
				"UPTC0020101-000029")/* @res "单据维护" */, nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101",
				"UPTC0020101-000029")/* @res "单据维护" */, 2, "维护"/*-=notranslate=-*/); /*-=notranslate=-*/
		btnlMaintainsInfo.addChildButton(m_btnModify);
		btnlMaintainsInfo.addChildButton(m_btnCancel);
		btnlMaintainsInfo.addChildButton(m_btnDelBill);
		// 修订(未走ButtonTree)
		m_btnReviseInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000290")/*
                                                                                                                       * @res
                                                                                                                       * "修订"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000290")/*
                                                                                         * @res
                                                                                         * "修订"
                                                                                         */, 0, "修订"/*-=notranslate=-*/); /*-=notranslate=-*/
		btnlMaintainsInfo.addChildButton(m_btnReviseInfo);
		// lxt 2013-07 gzcg
		m_btnAddSample = new ButtonObject("增加样本", "增加样本", 2, "增加样本");
		m_btnUpdateSample = new ButtonObject("检测数据", "检测数据", 2, "检测数据");
		btnlMaintainsInfo.addChildButton(m_btnAddSample);
		btnlMaintainsInfo.addChildButton(m_btnUpdateSample);
		// lxt 2013-07 gzcg
		
		// 行操作
		ButtonObject btnLineOpersInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UC001-0000011")/*
                         * @res "行操作"
                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000011")/*
                                                                                 * @res
                                                                                 * "行操作"
                                                                                 */, 2, "行操作"/*-=notranslate=-*/);
		btnLineOpersInfo.addChildButton(m_btnLineAdd);
		btnLineOpersInfo.addChildButton(m_btnLineDel);
		// 浏览
		ButtonObject btnBrowsesInfo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UC001-0000021")/*
                         * @res "浏览"
                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000021")/*
                                                                                 * @res
                                                                                 * "浏览"
                                                                                 */, 2, "浏览"/*-=notranslate=-*/);
		btnBrowsesInfo.addChildButton(m_btnFirst);
		btnBrowsesInfo.addChildButton(m_btnPrev);
		btnBrowsesInfo.addChildButton(m_btnNext);
		btnBrowsesInfo.addChildButton(m_btnLast);
		
		m_btnInfo = new ButtonObject[] { m_btnAdd, m_btnSave, btnlMaintainsInfo, btnLineOpersInfo, btnBrowsesInfo,
				m_btnPrint };
		// 联查报检点界面按钮组实例化{返回/上页/下页}
		m_btnPray = new ButtonObject[] { m_btnBack, m_btnPrev, m_btnNext };
		// 联查主料质量界面按钮组
		m_btnReturnMMQuery = m_btnBack;// 返回
		m_btnInfoPreviousMMQuery = m_btnPrev;// 上页
		m_btnInfoNextMMQuery = m_btnNext;// 下页
		m_btnListMMQuery = new ButtonObject[] { m_btnReturnMMQuery };
		m_btnInfoMMQuery = new ButtonObject[] { m_btnReturnMMQuery, m_btnInfoPreviousMMQuery, m_btnInfoNextMMQuery };

		// 支持扩展按钮
		addExtendBtns();
	}

	public ButtonTree getBtnTree() {
		if (m_btnTree == null) {
			try {
				m_btnTree = new ButtonTree("C0020101");
			} catch (BusinessException be) {
				MessageDialog.showErrorDlg(CheckbillDlg, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000000")/*错误*/, be.getMessage());
				return null;
			}
		}
		return m_btnTree;
	}

	/**
   * 增加扩展按钮到卡片界面。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午04:07:39
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
			MessageDialog.showErrorDlg(CheckbillDlg, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000000")/*错误*/, be.getMessage());
			return;
		}
	}

	/** 为二次开发提供接口 ********************************** */

	/**
   * 创建日期： 2005-9-20 功能描述： 获取扩展按钮数组（只提供卡片界面按钮）
   */
	public ButtonObject[] getExtendBtns() {
		return null;
	}

	/**
   * 界面按钮状态-检验单卡片浏览(含没数据的处理)。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午04:44:25
   */
	public void setButtonsStateCard() {
		// 增加
		m_btnAdd.setEnabled(CheckbillDlg.isFromQC());
		// 保存
		m_btnSave.setEnabled(false);
		// 维护
		m_btnMaintains.setEnabled(true);
		// 当前单据数目
		int iBillCnt = CheckbillDlg.m_checkBillVOs == null ? 0 : CheckbillDlg.m_checkBillVOs.length;
		// 修改
		if (iBillCnt == 0) {
			m_btnModify.setEnabled(false);
		} else {
			m_btnModify.setEnabled(CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanModify());
		}
		// 取消
		m_btnCancel.setEnabled(false);
		// 删除
		m_btnDelBill.setEnabled(CheckbillDlg.m_checkBillVOs == null ? false
				: CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanDelete());
		// 行操作
		m_btnLineOprs.setEnabled(false);
		// 增行
		m_btnLineAdd.setEnabled(false);
		// 删行
		m_btnLineDel.setEnabled(false);
		// 插入行
		m_btnLineIns.setEnabled(false);
		// 复制行
		m_btnLineCpy.setEnabled(false);
		// 粘贴行
		m_btnLinePst.setEnabled(false);
		// 审核
		if (iBillCnt == 0) {
			m_btnAudit.setEnabled(false);
		} else {
			m_btnAudit.setEnabled(CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanAudit());
		}
		// 执行
		m_btnExecutes.setEnabled(true);
		// 送审
		if (iBillCnt == 0) {
			m_btnSendAudit.setEnabled(false);
		} else {
			String curUser = ClientEnvironment.getInstance().getUser().getPrimaryKey();
			CheckbillHeaderVO hvo = (CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO();
			// 当前登录用户 == 单据制单人
			if (curUser != null && curUser.equals(hvo.getCprayerid())) {
				int iBillStatus = ((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO())
						.getIbillstatus();
				if (iBillStatus == 0)
					m_btnSendAudit.setEnabled(true);// 自由态时，一直可以送审
				else {
					boolean bSend = BusiBillManageTool.isNeedSendToAudit(CheckbillDlg.strBilltemplet, m_sUnitCode, null, hvo
							.getCcheckbillid(), curUser);
					m_btnSendAudit.setEnabled(bSend);// 检查审批流，是否可以送审
				}
			} else {
				// 如果当前登录用户 != 单据制单人，肯定不允许送审
//				m_btnSendAudit.setEnabled(false);
				//当前登录用户!= 单据制单人&&当前登录用户存在于审批流中 则可以送审 -- by 刘江波  问题来源： 金圣化工ERP 问题id：NCdp203568758
				boolean bSend = BusiBillManageTool.isNeedSendToAudit(CheckbillDlg.strBilltemplet, m_sUnitCode, null, hvo
						.getCcheckbillid(), curUser);
				m_btnSendAudit.setEnabled(bSend);
			}
		}
		// 弃审
		if (iBillCnt == 0) {
			m_btnUnAudit.setEnabled(false);
		} else {
			CheckbillVO vo = CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord];
			// 正在审批中（审批人==当前用户时可以进行弃审操作）
			if (vo.getHeadVo().getIbillstatus().intValue() == 2) {
				String curUser = ClientEnvironment.getInstance().getUser().getPrimaryKey();
				String auditpsn = vo.getHeadVo().getCauditpsn();
				m_btnUnAudit.setEnabled(StringUtils.equals(curUser, auditpsn));
			} else {
				m_btnUnAudit.setEnabled(vo.isCanUnAudit());
			}
		}
		// 查询
		m_btnQuery.setEnabled(true);
		// 浏览
		m_btnBrowses.setEnabled(true);
		// 刷新
		m_btnRefresh.setEnabled(CheckbillDlg.m_checkBillVOs == null ? false : true);
		// 定位
		m_btnLocate.setEnabled(iBillCnt > 0);
		// 首页、上页、下页、末页
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
		// 全选
		m_btnSelectAll.setEnabled(false);
		// 全消
		m_btnSelectNo.setEnabled(false);
		// 卡片显示/列表显示
		m_btnSwitch.setEnabled(true);
		m_btnSwitch.setName(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000464")/*
                                                                                               * @res
                                                                                               * "列表显示"
                                                                                               */);
		// 打印管理
		m_btnPrints.setEnabled(true);
		// 打印
		m_btnPrint.setEnabled(iBillCnt > 0);
		// 辅助查询
		m_btnAssQueries.setEnabled(true);
		// 联查
		m_btnLinkQuery.setEnabled((!CheckbillDlg.isFromQC() && !CheckbillDlg.isFromIC()) && iBillCnt > 0);
		// 审批流状态
		m_btnWorkFlowQuery.setEnabled(true);
		// 联查主料质量
		if (CheckbillDlg.isFromMM()) {
			m_btnMainMaterialQuery.setEnabled(iBillCnt > 0);
		} else {
			m_btnMainMaterialQuery.setEnabled(false);
		}
		// 辅助功能
		m_btnAssFuncs.setEnabled(true);
		// 文档管理
		m_btnDocument.setEnabled(iBillCnt > 0);

		m_btnBack.setEnabled(false);

		m_btnbatch.setEnabled(false);
		// 更新按钮状态
		updateButtonsAll();
	}

	/**
   * 界面按钮状态-检验单卡片修改。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午04:44:25
   */
	public void setButtonsStateCardModify() {
		// 当前单据数目
		int iBillCnt = CheckbillDlg.m_checkBillVOs == null ? 0 : CheckbillDlg.m_checkBillVOs.length;
		if (!CheckbillDlg.m_bBillEdit) {
			m_btnDelBill.setEnabled(((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord]
					.getParentVO()).getIbillstatus() == 3 ? false : true);
			m_btnModify
					.setEnabled(((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO())
							.getIbillstatus() == 3 ? false : true);
			// 增加
			m_btnAdd.setEnabled(CheckbillDlg.isFromQC());
			// 行操作
			m_btnLineOprs.setEnabled(!CheckbillDlg.isFromIC());
			// 首页、上页、下页、末页
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
			// 更新按钮状态
			updateButtonsAll();
			return;
		}
		// 增加
		m_btnAdd.setEnabled(false);
		// 保存
		m_btnSave.setEnabled(true);
		// 维护
		m_btnMaintains.setEnabled(true);
		// 修改
		m_btnModify.setEnabled(false);
		// 取消
		m_btnCancel.setEnabled(true);
		// 删除
		m_btnDelBill.setEnabled(false);
		// 行操作
		m_btnLineOprs.setEnabled(!CheckbillDlg.isFromIC());
		if (CheckbillDlg.isFromQC()) {
			// 手工报检可以增行
			m_btnLineAdd.setEnabled(true);
		} else {
			// 其他报检不能增行
			m_btnLineAdd.setEnabled(false);
			CheckbillDlg.getBillCardPanel().getAddLineMenuItem().setEnabled(false);
		}
		// 插入行
		if (CheckbillDlg.isFromQC()) {
			// 手工报检可以插行
			m_btnLineIns.setEnabled(true);
		} else {
			// 其他报检不能插行
			m_btnLineIns.setEnabled(false);
			CheckbillDlg.getBillCardPanel().getInsertLineMenuItem().setEnabled(false);
			UIMenuItem[] items = CheckbillDlg.getBillCardPanel().getBodyMenuItems();
			Vector vecData = new Vector();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText().equals(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000001")/*增行*/) || items[i].getText().equals(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000002")/*插入行*/)) {
					continue;
				}
				vecData.add(items[i]);
			}
			UIMenuItem[] bodyItems = new UIMenuItem[vecData.size()];
			vecData.copyInto(bodyItems);
			CheckbillDlg.getBillCardPanel().setBodyMenu(bodyItems);
		}
		m_btnLineDel.setEnabled(true);
		// 复制行
		m_btnLineCpy.setEnabled(true);
		// 粘贴行
		m_btnLinePst.setEnabled(true);
		// 审核
		m_btnAudit.setEnabled(false);
		// 执行
		m_btnExecutes.setEnabled(false);
		// 送审
		boolean bSend = false;
		if (!CheckbillDlg.m_bBillAdd) {
			bSend = BusiBillManageTool
					.isNeedSendToAudit(CheckbillDlg.strBilltemplet, m_sUnitCode, null,
							((CheckbillHeaderVO) CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].getParentVO())
									.getCcheckbillid(), ClientEnvironment.getInstance().getUser().getPrimaryKey());
		}
		m_btnSendAudit.setEnabled(bSend);
		// 弃审
		m_btnUnAudit.setEnabled(false);
		// 查询
		m_btnQuery.setEnabled(false);
		// 浏览
		m_btnBrowses.setEnabled(false);
		// 刷新
		m_btnRefresh.setEnabled(false);
		// 定位
		m_btnLocate.setEnabled(false);

		// 首页、上页、下页、末页
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
		// 全选
		this.m_btnSelectAll.setEnabled(false);
		// 全消
		this.m_btnSelectNo.setEnabled(false);
		// 卡片显示/列表显示
		this.m_btnSwitch.setEnabled(false);
		this.m_btnSwitch.setName(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000464")/*
                                                                                                     * @res
                                                                                                     * "列表显示"
                                                                                                     */);
		// 打印管理
		this.m_btnPrints.setEnabled(false);
		// 打印
		this.m_btnPrint.setEnabled(false);
		// 辅助查询
		this.m_btnAssQueries.setEnabled(false);
		// 联查
		this.m_btnLinkQuery.setEnabled(false);
		// 审批流状态
		this.m_btnWorkFlowQuery.setEnabled(false);
		// 联查主料质量
		this.m_btnMainMaterialQuery.setEnabled(false);
		// 辅助功能
		this.m_btnAssFuncs.setEnabled(false);
		// 文档管理
		this.m_btnDocument.setEnabled(false);

		this.m_btnbatch.setEnabled(true);

		// 更新按钮状态
		updateButtonsAll();
	}

	/**
   * 界面按钮状态-检验单列表浏览(含没数据的处理)。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午04:44:25
   */
	public void setButtonsStateList() {
		// 增加
		this.m_btnAdd.setEnabled(false);
		// 保存
		this.m_btnSave.setEnabled(false);
		// 维护
		this.m_btnMaintains.setEnabled(true);
		// 当前单据数目
		int iBillCnt = CheckbillDlg.m_checkBillVOs == null ? 0 : CheckbillDlg.m_checkBillVOs.length;
		// 选中的行数
		int iSelCnt = CheckbillDlg.getBillListPanel().getHeadTable().getSelectedRowCount();
		// 选中的所有行
		int[] iSelRows = CheckbillDlg.getBillListPanel().getHeadTable().getSelectedRows();
		// 修改
		if (iBillCnt == 0 || iSelCnt > 1) {
			this.m_btnModify.setEnabled(false);
		} else {
			if (CheckbillDlg.m_nBillRecord == -1) {
				CheckbillDlg.m_nBillRecord = 0;
			}
			this.m_btnModify.setEnabled(iSelRows.length > 0 && CheckbillDlg.m_checkBillVOs[iSelRows[0]].isCanModify());
		}
		// 取消
		this.m_btnCancel.setEnabled(false);
		// 删除
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
		// 行操作
		this.m_btnLineOprs.setEnabled(false);
		// 增行
		this.m_btnLineAdd.setEnabled(false);
		// 删行
		this.m_btnLineDel.setEnabled(false);
		// 插入行
		this.m_btnLineIns.setEnabled(false);
		// 复制行
		this.m_btnLineCpy.setEnabled(false);
		// 粘贴行
		this.m_btnLinePst.setEnabled(false);
		// 审核
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
		// 执行
		this.m_btnExecutes.setEnabled(true);
		// 送审(列表未支持)
		this.m_btnSendAudit.setEnabled(false);
		// 弃审
		if (iBillCnt == 0 || iSelCnt == 0) {
			this.m_btnUnAudit.setEnabled(false);
		} else if (iSelCnt == 1) {
			CheckbillVO vo = CheckbillDlg.m_checkBillVOs[iSelRows[0]];
			// 正在审批中（审批人==当前用户时可以进行弃审操作）
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
		// 查询
		this.m_btnQuery.setEnabled(true);
		// 浏览
		this.m_btnBrowses.setEnabled(true);
		// 刷新
		this.m_btnRefresh.setEnabled(CheckbillDlg.m_bQueried);
		// 定位(列表定位走模板控件，不支持此按钮功能)
		this.m_btnLocate.setEnabled(false);
		// 首页、上页、下页、末页
		this.m_btnFirst.setEnabled(false);
		this.m_btnPrev.setEnabled(false);
		this.m_btnNext.setEnabled(false);
		this.m_btnLast.setEnabled(false);
		// 全选
		if (iBillCnt == 0 || iBillCnt == iSelCnt) {
			this.m_btnSelectAll.setEnabled(false);
		} else {
			this.m_btnSelectAll.setEnabled(true);
		}
		// 全消
		if (iBillCnt > 0 && iSelCnt != 0) {
			this.m_btnSelectNo.setEnabled(true);
		} else {
			this.m_btnSelectNo.setEnabled(false);
		}
		// 卡片显示/列表显示
		this.m_btnSwitch.setEnabled(iSelCnt == 1 || iBillCnt == 0);
		//
		this.m_btnSwitch.setName(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000463")/*
                                                                                                     * @res
                                                                                                     * "卡片显示"
                                                                                                     */);
		// 打印管理
		this.m_btnPrints.setEnabled(true);
		// 打印
		this.m_btnPrint.setEnabled(iSelCnt != 0);
		// 辅助查询
		this.m_btnAssQueries.setEnabled(true);
		// 联查
		this.m_btnLinkQuery.setEnabled((!CheckbillDlg.isFromQC() && !CheckbillDlg.isFromIC()) && iSelCnt == 1);
		// 审批流状态
		this.m_btnWorkFlowQuery.setEnabled(iSelCnt == 1);
		// 联查主料质量
		if (CheckbillDlg.isFromMM()) {
			this.m_btnMainMaterialQuery.setEnabled(iSelCnt == 1);
		}
		// 辅助功能
		this.m_btnAssFuncs.setEnabled(true);
		// 文档管理
		this.m_btnDocument.setEnabled(iSelCnt > 0);

		// 更新按钮状态
		updateButtonsAll();
	}

	/**
   * 界面按钮状态-检验信息卡片浏览(含没数据的处理)。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午04:44:25
   */
	public void setButtonsStateCardInfo() {
		// 增加
		this.m_btnAdd.setEnabled(false);
		// 当前单据数目
		int iInfoCnt = 0;
		if (CheckbillDlg.m_grandVOs != null) {
			iInfoCnt = CheckbillDlg.m_grandVOs == null ? 0 : CheckbillDlg.m_grandVOs.length;
		}
		// 维护
		this.m_btnMaintains.setEnabled(true);
		// 行操作
		this.m_btnLineOprs.setEnabled(true);
		// 浏览
		this.m_btnBrowses.setEnabled(true);
		// 保存
		this.m_btnSave.setEnabled(false);
		// 取消
		this.m_btnCancel.setEnabled(false);
		
		this.m_btnUpdateSample.setEnabled(false); //lxt 2013-07 gzcg
		// 增行
		this.m_btnLineAdd.setEnabled(false);
		// 修订
		this.m_btnReviseInfo.setEnabled(false);
		// 删行
		this.m_btnLineDel.setEnabled(false);
		// 浏览状态下，审批的单据
		if (CheckbillDlg.m_checkBillVOs != null && CheckbillDlg.m_nBillRecord != -1
				&& !(CheckbillDlg.m_bBillAdd || CheckbillDlg.m_bBillEdit)) {
			if (CheckbillDlg.m_checkBillVOs[CheckbillDlg.m_nBillRecord].isCanUnAudit()) {
				// 修订
				this.m_btnReviseInfo.setEnabled(true);// 设置修订功能
			}
		}
		// 没有信息数据
		if (iInfoCnt == 0) {
			// 增加
			this.m_btnAdd.setEnabled(true);
			// 修改
			this.m_btnModify.setEnabled(false);
			// 删除
			this.m_btnDelBill.setEnabled(false);
			// 修订
			this.m_btnReviseInfo.setEnabled(false);
			updateButton(m_btnReviseInfo);
			// 首页
			this.m_btnFirst.setEnabled(false);
			// 上页
			this.m_btnPrev.setEnabled(false);
			// 下页
			this.m_btnNext.setEnabled(false);
			// 末页
			this.m_btnLast.setEnabled(false);
			// 更新按钮状态
			updateButtonsAll();
			return;
		}

		// 修改
		this.m_btnModify.setEnabled(true);
		// 删除
		this.m_btnDelBill.setEnabled(true);
		
		this.m_btnAddSample.setEnabled(true); // lxt 2013-07 gzcg
		// // 修订
		// this.m_btnReviseInfo
		// .setEnabled(false);
		updateButton(this.m_btnReviseInfo);
		// 首页、上页、下页、末页
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
		// 更新按钮状态
		updateButtonsAll();
		updateButton(m_btnAddSample);
		updateButton(m_btnUpdateSample);
	}

	/**
   * 界面按钮状态-检验信息卡片修改。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午04:44:25
   */
	public void setButtonsStateCardInfoModify() {
		// 增加
		this.m_btnAdd.setEnabled(false);
		// 维护
		this.m_btnMaintains.setEnabled(true);
		// 行操作
		this.m_btnLineOprs.setEnabled(true);
		// 浏览
		this.m_btnBrowses.setEnabled(true);
		// 保存
		this.m_btnSave.setEnabled(true);
		// 取消
		this.m_btnCancel.setEnabled(true);
		
		this.m_btnUpdateSample.setEnabled(true); //lxt 2013-07 gzcg
		// 增行
		this.m_btnLineAdd.setEnabled(true);
		// 删行
		this.m_btnLineDel.setEnabled(true);

		// 修改
		this.m_btnModify.setEnabled(false);
		// 删除
		this.m_btnDelBill.setEnabled(false);
		
		this.m_btnAddSample.setEnabled(false); // lxt 2013-07 gzcg
		// 修订
		this.m_btnReviseInfo.setEnabled(false);
		updateButton(this.m_btnReviseInfo);

		this.m_btnFirst.setEnabled(false);
		this.m_btnPrev.setEnabled(false);
		this.m_btnNext.setEnabled(false);
		this.m_btnLast.setEnabled(false);

		// 更新按钮状态
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
   * 按钮逻辑-查询主料质量。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-26 下午03:51:26
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
