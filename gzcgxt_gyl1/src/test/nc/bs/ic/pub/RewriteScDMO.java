package nc.bs.ic.pub;

import java.util.ArrayList;
import nc.bs.dao.BaseDAO;
import nc.bs.pub.SystemException;
import nc.vo.ic.pub.GenMethod;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.constant.ic.InOutFlag;

public class RewriteScDMO {
	nc.vo.ic.pub.bill.Timer m_timer = new nc.vo.ic.pub.bill.Timer();

	public void reWriteCorNum(GeneralBillVO newVO, GeneralBillVO oldVO)
			throws nc.vo.pub.BusinessException {

		m_timer.start();
		// GeneralBillVO newVO=(GeneralBillVO)newVO1;
		// GeneralBillVO oldVO=(GeneralBillVO)oldVO1;
		String[] sNumFields = new String[] { "ninnum", "ninassistnum",
				"ningrossnum" };
		GeneralBillVO vot = null;
		GeneralBillVO votNew = null;
		GeneralBillVO votOld = null;
		boolean isQtyFilled = false;
		if (newVO != null) {
			vot = (GeneralBillVO) newVO;
		} else {
			vot = (GeneralBillVO) oldVO;
		}
		if (newVO == null && oldVO == null)

			return;

		if (vot.getBillInOutFlag() == InOutFlag.OUT) {
			sNumFields[0] = "noutnum";
			sNumFields[1] = "noutassistnum";
			sNumFields[2] = "noutgrossnum";
		}

		try {

			GeneralBillItemVO[] voItems = getCombinedItems(newVO, oldVO,
					new String[] { "csourcebillbid" }, sNumFields);

			m_timer.stopAndShow("����VO"/* -=notranslate=- */);
			// ��ǰ���ݵõ��ݺ�
			String billcode = null;

			if (voItems == null || voItems.length < 1) {
				nc.vo.scm.pub.SCMEnv.out("����д�ۼƳ�������"/* -=notranslate=- */);
				return;
			}

			// nc.bs.ic.pub.bill.GeneralBillDMO dmo=new
			// nc.bs.ic.pub.bill.GeneralBillDMO();
			String[] ids = new String[voItems.length];
			UFDouble[] nnums = new UFDouble[voItems.length];
			UFDouble[] nastnums = new UFDouble[voItems.length];
			UFDouble[] ngrsnums = new UFDouble[voItems.length];

			UFDouble neg = new UFDouble(-1);
			for (int i = 0; i < voItems.length; i++) {
				ids[i] = voItems[i].getCsourcebillbid();
				nnums[i] = (UFDouble) voItems[i]
						.getAttributeValue(sNumFields[0]);
				nastnums[i] = (UFDouble) voItems[i]
						.getAttributeValue(sNumFields[1]);
				ngrsnums[i] = (UFDouble) voItems[i]
						.getAttributeValue(sNumFields[2]);
				if (nnums[i] == null)
					nnums[i] = GenMethod.ZERO;
				if (nastnums[i] == null)
					nastnums[i] = GenMethod.ZERO;
				if (ngrsnums[i] == null)
					ngrsnums[i] = GenMethod.ZERO;

				if (vot.getBillInOutFlag() == InOutFlag.IN) {
					if (nnums[i] != null)
						nnums[i] = nnums[i].multiply(neg);
					if (nastnums[i] != null)
						nastnums[i] = nastnums[i].multiply(neg);
					if (ngrsnums[i] != null)
						ngrsnums[i] = ngrsnums[i].multiply(neg);

				}
			}
			// ��д
			updateCorNumBatch(ids, nnums, nastnums, ngrsnums);

			m_timer.stopAndShow("��д�ۼƳ�������"/* -=notranslate=- */);

		} catch (Exception e) {
			// throw new BusinessException(e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	public void reWriteCorNumForDelete(GeneralBillVO newVO, GeneralBillVO oldVO)
			throws nc.vo.pub.BusinessException {

		m_timer.start();
		// GeneralBillVO newVO=(GeneralBillVO)newVO1;
		// GeneralBillVO oldVO=(GeneralBillVO)oldVO1;
		String[] sNumFields = new String[] { "ninnum", "ninassistnum",
				"ningrossnum" };
		GeneralBillVO vot = null;
		GeneralBillVO votNew = null;
		GeneralBillVO votOld = null;
		boolean isQtyFilled = false;
		if (newVO != null) {
			vot = (GeneralBillVO) newVO;
		} else {
			vot = (GeneralBillVO) oldVO;
		}
		if (newVO == null && oldVO == null)

			return;

		if (vot.getBillInOutFlag() == InOutFlag.OUT) {
			sNumFields[0] = "noutnum";
			sNumFields[1] = "noutassistnum";
			sNumFields[2] = "noutgrossnum";
		}

		try {

			GeneralBillItemVO[] voItems = getCombinedItemsForDelete(newVO, oldVO,
					new String[] { "csourcebillbid" }, sNumFields);
//
//			m_timer.stopAndShow("����VO"/* -=notranslate=- */);
//			// ��ǰ���ݵõ��ݺ�
//			String billcode = null;
//
//			if (voItems == null || voItems.length < 1) {
//				nc.vo.scm.pub.SCMEnv.out("����д�ۼƳ�������"/* -=notranslate=- */);
//				return;
//			}
//
//			// nc.bs.ic.pub.bill.GeneralBillDMO dmo=new
//			// nc.bs.ic.pub.bill.GeneralBillDMO();
//			String[] ids = new String[voItems.length];
//			UFDouble[] nnums = new UFDouble[voItems.length];
//			UFDouble[] nastnums = new UFDouble[voItems.length];
//			UFDouble[] ngrsnums = new UFDouble[voItems.length];
//
//			UFDouble neg = new UFDouble(-1);
//			for (int i = 0; i < voItems.length; i++) {
//				ids[i] = voItems[i].getCsourcebillbid();
//				nnums[i] = (UFDouble) voItems[i]
//						.getAttributeValue(sNumFields[0]);
//				nastnums[i] = (UFDouble) voItems[i]
//						.getAttributeValue(sNumFields[1]);
//				ngrsnums[i] = (UFDouble) voItems[i]
//						.getAttributeValue(sNumFields[2]);
//				if (nnums[i] == null)
//					nnums[i] = GenMethod.ZERO;
//				if (nastnums[i] == null)
//					nastnums[i] = GenMethod.ZERO;
//				if (ngrsnums[i] == null)
//					ngrsnums[i] = GenMethod.ZERO;
//
//				if (vot.getBillInOutFlag() == InOutFlag.IN) {
//					if (nnums[i] != null)
//						nnums[i] = nnums[i].multiply(neg);
//					if (nastnums[i] != null)
//						nastnums[i] = nastnums[i].multiply(neg);
//					if (ngrsnums[i] != null)
//						ngrsnums[i] = ngrsnums[i].multiply(neg);
//
//				}
//			}
//			// ��д
//			updateCorNumBatch(ids, nnums, nastnums, ngrsnums);
//
//			m_timer.stopAndShow("��д�ۼƳ�������"/* -=notranslate=- */);

		} catch (Exception e) {
			// throw new BusinessException(e.getMessage());
			throw new BusinessException(e);
		}
	}

	/**
	 * �����ߣ����˾� ���ܣ��޸��ۼƳ������� ���� : String[] sBillItemPK, ����PK UFDouble[] dNum ����
	 * ���أ� ���⣺ ���ڣ�(2001-6-12 20:38:02) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.vo.ic.pub.bill.GeneralBillVO[]
	 * @param voQryCond
	 *            nc.vo.ic.pub.bill.QryConditionVO
	 */
	public void updateCorNumBatch(String[] sBillItemPKs, UFDouble[] dNums,
			UFDouble[] dAstNums, UFDouble[] dGrsNums)
			throws java.sql.SQLException, BusinessException, SystemException {
		if (sBillItemPKs == null || sBillItemPKs.length == 0 || dNums == null
				|| dNums.length == 0)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("4008bill", "UPP4008bill-000490")/*
																				 * @
																				 * res
																				 * "���������������"
																				 */);

		BaseDAO dao = new BaseDAO();
		for (int i = 0; i < dNums.length; i++) {
			String sql = "UPDATE sc_order_ddlb SET  naccumsendnum = COALESCE(naccumsendnum,0.0)+("
					+ dNums[i]
					+ ") WHERE corder_lb_id = '"
					+ sBillItemPKs[i]
					+ "'";// , ncorrespondastnum =
							// COALESCE(ncorrespondastnum,0.0)+(?),ncorrespondgrsnum
							// = COALESCE(ncorrespondgrsnum,0.0)+(?)
			dao.executeUpdate(sql);
		}
	}

	/**
	 * 
	 * ���ܣ���õ��ݵ��޸������ϲ����ݣ�Ȼ�󷵻�items�� ������ ���أ� ���⣺ ���ڣ�(2002-04-30 14:16:35)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private GeneralBillItemVO[] getCombinedItems(GeneralBillVO newVO,
			GeneralBillVO oldVO) {

		GeneralBillItemVO[] voItems = null; // waiting rewriting items.

		if (newVO != null)
			newVO.setBisIUK_SourceBodyidOnly(true);
		if (oldVO != null)
			oldVO.setBisIUK_SourceBodyidOnly(true);
		GeneralBillVO voBill = GeneralBillVO.combine(newVO, oldVO);
		if (voBill != null)
			voItems = voBill.getItemVOs();
		return voItems;
	}

	/**
	 * 
	 * ���ܣ���õ��ݵ��޸������ϲ����ݣ�Ȼ�󷵻�items�� ������ ���أ� ���⣺ ���ڣ�(2002-04-30 14:16:35)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private GeneralBillItemVO[] getCombinedItems(GeneralBillVO newVO,
			GeneralBillVO oldVO, String[] sGroupFields, String[] sNumFields)
			throws Exception {

		m_timer.start();
		GeneralBillItemVO[] voItems = null;
		ArrayList alvo = new ArrayList();
		if (newVO != null) {
			voItems = newVO.getItemVOs();
			for (int i = 0; i < voItems.length; i++) {
				if (voItems[i].getStatus() == nc.vo.pub.VOStatus.DELETED){
					continue;
				}

				for (int j = 0; j < sGroupFields.length; j++) {
					if (voItems[i].getAttributeValue(sGroupFields[j]) != null) {
						alvo.add(voItems[i]);
						break;
					}
				}
			}
		}

		if (oldVO != null) {// &&oldVO.isQtyFilled()){
			voItems = oldVO.getItemVOs();
			for (int i = 0; i < voItems.length; i++) {
				GeneralBillItemVO voItem = new GeneralBillItemVO();// (GeneralBillItemVO)voItems[i].clone();
				UFDouble neg = new UFDouble(-1.0);
				boolean isHasvalue = false;
				for (int j = 0; j < sGroupFields.length; j++) {
					if (voItems[i].getAttributeValue(sGroupFields[j]) != null) {
						voItem.setAttributeValue(sGroupFields[j],
								voItems[i].getAttributeValue(sGroupFields[j]));
						isHasvalue = true;
					}

				}
				if (isHasvalue) {
					alvo.add(voItem);

					for (int j = 0; j < sNumFields.length; j++) {
						if (voItems[i].getAttributeValue(sNumFields[j]) != null)
							voItem.setAttributeValue(sNumFields[j],
									((UFDouble) voItems[i]
											.getAttributeValue(sNumFields[j]))
											.multiply(neg));
						else
							voItem.setAttributeValue(sNumFields[j],
									GenMethod.ZERO);

					}

				}
			}

		}
		m_timer.stopAndShow("getCombinedItems");
		GeneralBillItemVO[] voRes = null;

		if (alvo.size() > 0) {

			GeneralBillItemVO[] voItemstmp = new GeneralBillItemVO[alvo.size()];
			alvo.toArray(voItemstmp);
			m_timer.stopAndShow("getCombinedItems");
			nc.vo.scm.merge.DefaultVOMerger m = new nc.vo.scm.merge.DefaultVOMerger();
			m.setMergeAttrs(sGroupFields, sNumFields, null, null, null);
			voRes = (GeneralBillItemVO[]) m.mergeByGroupOnly(voItemstmp);

			m_timer.stopAndShow("getCombinedItems_merge");

		}
		return voRes;
	}
	
	/**
	 * 
	 * ���ܣ���õ��ݵ��޸������ϲ����ݣ�Ȼ�󷵻�items�� ������ ���أ� ���⣺ ���ڣ�(2002-04-30 14:16:35)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private GeneralBillItemVO[] getCombinedItemsForDelete(GeneralBillVO newVO,
			GeneralBillVO oldVO, String[] sGroupFields, String[] sNumFields)
			throws Exception {

		m_timer.start();
		GeneralBillItemVO[] voItems = null;
		ArrayList alvo = new ArrayList();
		if (newVO != null) {
			voItems = newVO.getItemVOs();
			for (int i = 0; i < voItems.length; i++) {
				if (voItems[i].getStatus() == nc.vo.pub.VOStatus.DELETED){
					// ��д
					UFDouble num = voItems[i].getNoutnum() == null ? UFDouble.ZERO_DBL : voItems[i].getNoutnum().multiply(-1);
					updateCorNumBatch(new String[]{voItems[i].getCsourcebillbid()}, new UFDouble[]{num}, null, null);
					continue;
				}

//				for (int j = 0; j < sGroupFields.length; j++) {
//					if (voItems[i].getAttributeValue(sGroupFields[j]) != null) {
//						alvo.add(voItems[i]);
//						break;
//					}
//				}
			}
		}

//		if (oldVO != null) {// &&oldVO.isQtyFilled()){
//			voItems = oldVO.getItemVOs();
//			for (int i = 0; i < voItems.length; i++) {
//				GeneralBillItemVO voItem = new GeneralBillItemVO();// (GeneralBillItemVO)voItems[i].clone();
//				UFDouble neg = new UFDouble(-1.0);
//				boolean isHasvalue = false;
//				for (int j = 0; j < sGroupFields.length; j++) {
//					if (voItems[i].getAttributeValue(sGroupFields[j]) != null) {
//						voItem.setAttributeValue(sGroupFields[j],
//								voItems[i].getAttributeValue(sGroupFields[j]));
//						isHasvalue = true;
//					}
//
//				}
//				if (isHasvalue) {
//					alvo.add(voItem);
//
//					for (int j = 0; j < sNumFields.length; j++) {
//						if (voItems[i].getAttributeValue(sNumFields[j]) != null)
//							voItem.setAttributeValue(sNumFields[j],
//									((UFDouble) voItems[i]
//											.getAttributeValue(sNumFields[j]))
//											.multiply(neg));
//						else
//							voItem.setAttributeValue(sNumFields[j],
//									GenMethod.ZERO);
//
//					}
//
//				}
//			}
//
//		}
//		m_timer.stopAndShow("getCombinedItems");
//		GeneralBillItemVO[] voRes = null;
//
//		if (alvo.size() > 0) {
//
//			GeneralBillItemVO[] voItemstmp = new GeneralBillItemVO[alvo.size()];
//			alvo.toArray(voItemstmp);
//			m_timer.stopAndShow("getCombinedItems");
//			nc.vo.scm.merge.DefaultVOMerger m = new nc.vo.scm.merge.DefaultVOMerger();
//			m.setMergeAttrs(sGroupFields, sNumFields, null, null, null);
//			voRes = (GeneralBillItemVO[]) m.mergeByGroupOnly(voItemstmp);
//
//			m_timer.stopAndShow("getCombinedItems_merge");
//
//		}
		return null;//voRes;
	}
}
