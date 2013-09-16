package nc.ui.sc.settle.btnaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.itf.ic.service.IICToSO;
import nc.itf.sc.IOrder;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.sc.pub.BillEdit;
import nc.ui.sc.pub.PublicHelper;
import nc.ui.sc.settle.MaterialledgerHelper;
import nc.ui.sc.settle.SettleUI;
import nc.ui.sc.settle.btnmgr.SettleBtnObject;
import nc.ui.scm.ic.freeitem.FreeItemRefPane;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.mm.pub.FreeItemVO;
import nc.vo.mm.pub.pub1020.DisConditionVO;
import nc.vo.mm.pub.pub1020.DisassembleVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.sc.order.CGDisassembleVO;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.sc.order.OrderItemVO;
import nc.vo.sc.order.OrderReportVO;
import nc.vo.sc.order.OrderVO;
import nc.vo.sc.pub.ScConstants;
import nc.vo.sc.settle.MaterialledgerVO;
import nc.vo.scm.ic.bill.FreeVO;

/**
 * ���Ϻ���ҳ���еġ���������ť������
 * 
 * @author:hanbin
 */
public class VerifyBtnAction implements IBtnAction {
  //���Ϻ���ҳ��
  private SettleUI clientUI;

  private HashMap<String, OrderVO> order_map = new HashMap<String, OrderVO>();
  /**
   *  Created on 2009-8-25 
   * <p>Discription:[����ȡ������ť�ĵ��]</p>
   * @param clientUI
   * @author: ���� hanbin@ufida.com.cn
   * @update:[����YYYY-MM-DD] [����������]
   */
	public Object doButtonAction(SettleUI clientUI) {
		order_map = new HashMap<String, OrderVO>();
		this.clientUI = clientUI;

		// ���ں���
		clientUI.showHintMessage(NCLangRes.getInstance().getStrByID("401205",
				"UPP401205-000071"));

		// �����ǰ���б���ʾ��ת������Ƭ��ʾ
		if (NCLangRes.getInstance().getStrByID("common", "UCH021")
				.equals(clientUI.getBtnManager().getBoReturn().getName())) {
			try {
				// ���"��Ƭ��ʾ"��ť���������
				IBtnAction returnActionObj = ((SettleBtnObject) clientUI
						.getBtnManager().getBoReturn()).getBtnActionObj();
				returnActionObj.doButtonAction(clientUI);
			} catch (Exception ex) {
				clientUI.handleException(ex);
			}
		}

		// ��������
		try {
			int rowcount = clientUI.getBillCardPanel().getRowCount();

			// ������ȡ�����мӹ�Ʒ������Ӧ�Ĳ�����
			DisConditionVO[] conditions = new DisConditionVO[rowcount];
			for (int i = 0; i < rowcount; i++) {
				conditions[i] = BillEdit.getDisConditionVO(ClientEnvironment
						.getInstance().getCorporation().getPk_corp(),
						ClientEnvironment.getInstance().getDate(),
						ClientEnvironment.getInstance().getUser()
								.getPrimaryKey(), clientUI.getBillCardPanel()
								.getHeadItem("cwareid").getValue());

				// �ӹ�Ʒ��������ID
				String cbaseid = (String) clientUI.getBillCardPanel()
						.getBodyValueAt(i, "cbaseid");
				conditions[i].setWlbmid(cbaseid);
				// ����
				conditions[i].setSl(new UFDouble("1"));
				// �����������
				String formula = "pk_measdoc->getColValue(bd_invbasdoc,pk_measdoc,pk_invbasdoc,cbaseid)";
				FormulaParse f = new FormulaParse();
				f.setExpress(formula);
				f.addVariable("cbaseid", cbaseid);
				conditions[i].setJldwid(f.getValue());// ������λID
			}

			List<DisassembleVO[]> bomList = BillEdit.getBomVOBatch(conditions);
			String generalid = clientUI.getBillCardPanel().getHeadItem("cbillid").getValue();
			
			UFDouble[] intotal = new UFDouble[rowcount];
			for(int i=0;i<rowcount;i++) intotal[i]=UFDouble.ZERO_DBL;
			
			HashMap<String,UFDouble> orderitem_map = new HashMap<String, UFDouble>();
			HashMap<String,UFDouble> orderitem_no_map = new HashMap<String, UFDouble>();
			List<String> itemidALL  = new ArrayList<String>();//������¼ί�ⶩ����ID
			for (int i = rowcount - 1; i >= 0; i--) {
				UFDouble nnum = (UFDouble) clientUI.getBillCardPanel().getBodyValueAt(i, "nnum");
				UFDouble nprice = (UFDouble) clientUI.getBillCardPanel().getBodyValueAt(i, "nprocessmny");
				nnum = nnum == null ? UFDouble.ZERO_DBL : nnum;
				nprice = nprice == null ? UFDouble.ZERO_DBL : nprice;
				intotal[i] = nnum.multiply(nprice);
				
				//////////////////////////////
				String corderid = (String) clientUI.getBillCardPanel()
						.getBodyValueAt(i, "corderid");
				String corder_bid = (String) clientUI.getBillCardPanel()
						.getBodyValueAt(i, "corder_bid");
				if (corderid != null) {
					if( order_map.get(corderid) == null){
						IOrder service = NCLocator.getInstance().lookup(
								IOrder.class);
						OrderVO temp = service.findByPrimaryKeyFroOrder(corderid);
						order_map.put(corderid, temp);
					}
					OrderVO srcbillvo = order_map.get(corderid);
					if (srcbillvo != null) {
						OrderItemVO[] itemvos = (OrderItemVO[]) srcbillvo.getChildrenVO();
						UFDouble num = UFDouble.ZERO_DBL;
						//�õ��۸������
						for (int j2 = 0; j2 < itemvos.length; j2++) {
							if(!itemidALL.contains(itemvos[j2].getPrimaryKey())){
								itemidALL.add(itemvos[j2].getPrimaryKey());
							}
							num = itemvos[j2].getNordernum();
							num = num == null ? UFDouble.ZERO_DBL : num;
							UFDouble naccumstorenum = itemvos[j2].getNaccumstorenum();
							naccumstorenum = naccumstorenum == null ? UFDouble.ZERO_DBL : naccumstorenum;
							if(corder_bid.equals(itemvos[j2].getPrimaryKey())){
								orderitem_map.put(itemvos[j2].getPrimaryKey(),num.sub(naccumstorenum).add(nnum).multiply(nprice));//ʹ��ʣ�����
							}else{
								UFDouble order_price = itemvos[j2].getNoriginalcurprice() == null ? UFDouble.ZERO_DBL : itemvos[j2].getNoriginalcurprice();
								if(orderitem_no_map.get(itemvos[j2].getPrimaryKey())==null){
									orderitem_no_map.put(itemvos[j2].getPrimaryKey(), num.sub(naccumstorenum).multiply(order_price));
								}
								
							}
						}
					}
				}
				
			}
			UFDouble ordertotal = UFDouble.ZERO_DBL;
			if(itemidALL.size() > 0){
				for (int i = 0; i < itemidALL.size(); i++) {
					String key = itemidALL.get(i);
					if(orderitem_map.get(key) != null){
						ordertotal = ordertotal.add(orderitem_map.get(key));
					}else{
						ordertotal = ordertotal.add(orderitem_no_map.get(key));
					}
				}
			}
			UFDouble[] xs = new UFDouble[rowcount];
			for(int i=0;i<rowcount;i++) xs[i] = intotal[i].div(ordertotal);
			// ������в���ӹ�Ʒ��Ӧ�Ĳ�����
			for (int i = rowcount - 1; i >= 0; i--) {
				// gc
				String corderid = (String) clientUI.getBillCardPanel()
						.getBodyValueAt(i, "corderid");
				String corder_bid = (String) clientUI.getBillCardPanel()
						.getBodyValueAt(i, "corder_bid");
				if (corderid != null) {
					OrderVO srcbillvo = order_map.get(corderid);

					OrderItemVO[] itemvos = (OrderItemVO[]) srcbillvo
							.getChildrenVO();
					UFDouble num = UFDouble.ZERO_DBL;
					UFDouble money = UFDouble.ZERO_DBL;
					UFDouble totalmoney = UFDouble.ZERO_DBL;
					for (int j2 = 0; j2 < itemvos.length; j2++) {
						UFDouble tempnum = itemvos[j2].getNoriginalsummny();
						tempnum = tempnum == null ? UFDouble.ZERO_DBL : tempnum;
						totalmoney = tempnum.add(totalmoney);
						
						if (corder_bid.equals(itemvos[j2].getPrimaryKey())) {
							num = itemvos[j2].getNordernum();
							num = num == null ? UFDouble.ZERO_DBL : num;
							money = tempnum;
						}
					}

					OrderDdlbVO[] ddlbvos = srcbillvo.getDdlbvos();
					if (ddlbvos != null) {
						HashMap<String, ArrayList<GeneralBillItemVO>> map = null;
						ArrayList<String> list_ids = new ArrayList<String>();
						for (int j = 0; j < ddlbvos.length; j++) {
							list_ids.add(ddlbvos[j].getPrimaryKey());
						}
						map = queryGeneralItemVO(list_ids);
						//gc ��Ҫ���ݲ��ϳ�����ϸ�����ɱ��������
						// ת��ΪBOMVO
						CGDisassembleVO[] disassembleVOs = getDisassembleVOs(list_ids, map, xs[i], corder_bid, ddlbvos,money.div(totalmoney));
						if(disassembleVOs == null || disassembleVOs.length <=0){
							clientUI.showHintMessage("����ʧ��,�ӹ�Ʒ��Ӧԭ�ϲ���");
							return null;
						}
						insertItemVOByVerify(i, disassembleVOs);
					} else {
						clientUI.showHintMessage("����ʧ��,ί�ⶩ�������ϱ���Ϣ");
						return null;
					}

				} else {// ��׼��Ʒ
					this.insertItemVOByVerify(i, bomList.get(i));
				}
			}

			// ���ع�ʽ
			clientUI.getBillCardPanel().getBillModel().execLoadFormula();

			// ����ԭ���ϵ�������
			this.setMaterialFreeItem();

			// �����н�����,���ڳ�Ʒ��
			clientUI.updateInterfaceShow();

			rowcount = clientUI.getBillCardPanel().getRowCount();
			for (int i = 0; i < rowcount; i++) {
				Object value = clientUI.getBillCardPanel().getBodyValueAt(i,
						"nnum");
				clientUI.getBillCardPanel()
						.setBodyValueAt(value, i, "nnumcopy");

				value = clientUI.getBillCardPanel().getBodyValueAt(i,
						"nmatenum");
				clientUI.getBillCardPanel().setBodyValueAt(value, i,
						"nmatenumcopy");
			}
		} catch (Exception e) {
			clientUI.handleException(e);
			clientUI.showHintMessage(NCLangRes.getInstance().getStrByID(
					"401205", "UPP401205-000072")/* @res "����ʧ��" */);
			return null;
		}
		// �����ɹ��󣬸��°�ť��ҳ��״̬
		clientUI.setM_billState(ScConstants.STATE_VERIFY);// ���ں���
		clientUI.getBtnManager().toVfyingStauts();
		clientUI.updateUI();
		clientUI.showHintMessage(NCLangRes.getInstance().getStrByID("401205",
				"UPP401205-000073")/* @res "�����ɹ�" */);
		return null;
	}


	/**
	 * gc
	 * @param csourcebillbids
	 * @param map
	 * @param xs
	 * @param corder_bid
	 * @param ddlbvos
	 * @return
	 * @throws BusinessException
	 */
private CGDisassembleVO[] getDisassembleVOs(ArrayList<String> csourcebillbids,HashMap<String, 
		  ArrayList<GeneralBillItemVO>> map,UFDouble xs, String corder_bid,
		OrderDdlbVO[] ddlbvos,UFDouble bl) throws BusinessException {
	UFDouble[] hxnum = new UFDouble[csourcebillbids.size()];
	for (int j = 0; j < csourcebillbids.size(); j++) {
		// ///////////���Ѻ���//////////////
		UFDouble totalnum = ddlbvos[j].getNaccumwritenum()== null ?  UFDouble.ZERO_DBL:ddlbvos[j].getNaccumwritenum();
//		try {
//			MaterialledgerVO paramvo = new MaterialledgerVO();
//			paramvo.setPk_corp(ClientEnvironment
//					.getInstance().getCorporation()
//					.getPrimaryKey());
//			paramvo.setCorder_bid(corder_bid);
//			paramvo.setCmaterialmangid(ddlbvos[j]
//					.getCmangid());
//			Vector all_mater = MaterialledgerHelper
//					.queryByVOs(
//							new MaterialledgerVO[] { paramvo },
//							new Boolean[] { true });
//			if (all_mater != null) {
//				MaterialledgerVO[] materivos = (MaterialledgerVO[]) all_mater
//						.get(0);
//
//				for (int ii = 0; ii < materivos.length; ii++) {
//					MaterialledgerVO materialledgerVO = materivos[ii];
//					UFDouble update = materialledgerVO
//							.getNnum();
//					update = update == null ? UFDouble.ZERO_DBL
//							: update;
//					totalnum = totalnum.add(update);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// ////////////////////////////////
		
		hxnum[j] = ddlbvos[j].getNnum().sub(totalnum).multiply(xs);//.multiply(bl);
				
	}
	
	CGDisassembleVO[] returnvalue = getDisassembleVOsFromIC(csourcebillbids, map, hxnum, ddlbvos);
	return returnvalue;
}

  /**
   * gc
   * ���Ϻ���ʱ������в���ӹ�Ʒ��Ӧ�Ĳ����� 
   * @param rowIndex �ӹ�Ʒ���к�
   * @param disassembleVOs �������ԭ��������
   * @throws Exception 
   * �������ڣ�(2001-7-24 13:19:07)
   */
  private int insertItemVOByVerify(int rowIndex, CGDisassembleVO[] disassembleVOs) throws Exception {
    if (disassembleVOs == null || disassembleVOs.length == 0)
      return 0;

    int insertMateNum = disassembleVOs.length;

    //����ԭ���ϻ���ID����ò��Ϲ���ID
    String mateBaseIDArray[] = new String[insertMateNum];
    for (int i = 0; i < insertMateNum; i++)
      mateBaseIDArray[i] = disassembleVOs[i].getWlbmid();
    //���Ϲ���ID
    String mateMangIDArray[] = PublicHelper.getProcessMangID(disassembleVOs[0].getPk_corp(),
        mateBaseIDArray);

    if (mateMangIDArray == null || mateMangIDArray.length != mateBaseIDArray.length)
      return 0;

    for (int i = 0; i < insertMateNum; i++) {
      clientUI.getBillCardPanel().getBillModel().insertRow(rowIndex + i + 1);

      //�ӹ�Ʒ����
      Object procSLobj = clientUI.getBillCardPanel().getBodyValueAt(rowIndex, "nnum");
      UFDouble processSL = new UFDouble(procSLobj == null ? "0" : procSLobj.toString());

      //���ϵ��
      UFDouble shxs = disassembleVOs[i].getShxs();
      //��׼����
      UFDouble mateSL = disassembleVOs[i].getSl();
      //��λ�ӹ�Ʒ�������� = ��׼���� + ��׼���� * ���ϵ��
//      UFDouble trueSL = mateSL.add(shxs.multiply(mateSL));//gc

      //��ӱ�ʶ������ = ԭ����
      clientUI.getBillCardPanel().setBodyValueAt("", (rowIndex + i + 1), "bismaterial");

      //ԭ���ϻ���id
      String cmatebaseid = mateBaseIDArray[i];
      clientUI.getBillCardPanel().setBodyValueAt(cmatebaseid, (rowIndex + i + 1), "cbaseid");

      //ԭ���Ϲ���id
      String cmatemangid = mateMangIDArray[i];
      clientUI.getBillCardPanel().setBodyValueAt(cmatemangid, (rowIndex + i + 1), "cmangid");

      //���/��������=��Ʒ������*��λ�ӹ�Ʒ��������
      clientUI.getBillCardPanel().setBodyValueAt(mateSL/*(processSL.multiply(trueSL))gc*/, (rowIndex + i + 1),
          "nnum");

      //��λ�ӹ�Ʒ�������������ֶμ����Ŀ���ʱʹ��
      clientUI.getBillCardPanel().setBodyValueAt(mateSL.div(processSL)/*trueSLgc*/, (rowIndex + i + 1), "nmatenum");

      //����ʱ���������е�cbill_bid���ó����Ӧ��Ʒ�еĳ�Ʒ�е�cbill_bid��
      //����:����ʱ���ݳ�Ʒ�е�cbill_bidѭ����������У���cbill_bidֵΨһ����˵����"δ����"����ⵥ�У����ܱ���
      clientUI.getBillCardPanel().setBodyValueAt(
          clientUI.getBillCardPanel().getBodyValueAt(rowIndex, "cbill_bid"), (rowIndex + i + 1),
          "cbill_bid");
      //���ϵ��ۡ����Ͻ��
      if(disassembleVOs[i].getNprice() != null){
    	  clientUI.getBillCardPanel().setBodyValueAt(new UFDouble(disassembleVOs[i].getNprice()), (rowIndex + i + 1), "nprice");
    	  clientUI.getBillCardPanel().setBodyValueAt(new UFDouble(disassembleVOs[i].getNprice()).multiply(mateSL), (rowIndex + i + 1), "nprocessmny");
      }
      clientUI.getBillCardPanel().setBodyValueAt(disassembleVOs[i].getVbatch(),(rowIndex + i + 1), "vbatch");
      
      //gc
//      getBodyBillModel().setValueAt(freeVO.getVfree0(), i, "vfree0");
      //gc-end
      String free="";
      if(disassembleVOs[i].getInvVO() !=null
    		  && disassembleVOs[i].getInvVO().getFreeItemVO() != null){
    	  FreeVO freevo = disassembleVOs[i].getInvVO().getFreeItemVO();
    	  free = freevo.getVfree1();
    	  for (int j = 2; j < 6; j++) {
			if(freevo.getAttributeValue("vfree"+j) != null){
				free +="@@@"+freevo.getAttributeValue("vfree"+j);
			}else{
				free +="@@@";
			}
		}
    	  
    	  clientUI.getBillCardPanel().setBodyValueAt(free, (rowIndex + i + 1), "vfreeall");
      }
//      clientUI.getBillCardPanel().setBodyValueAt(disassembleVOs[i].getInvVO(), (rowIndex + i + 1), "invvo");
    }

    return insertMateNum;
  }
  
  /**
   * 
   * ���Ϻ���ʱ������в���ӹ�Ʒ��Ӧ�Ĳ����� 
   * @param rowIndex �ӹ�Ʒ���к�
   * @param disassembleVOs �������ԭ��������
   * @throws Exception 
   * �������ڣ�(2001-7-24 13:19:07)
   */
  private int insertItemVOByVerify(int rowIndex, DisassembleVO[] disassembleVOs) throws Exception {
    if (disassembleVOs == null || disassembleVOs.length == 0)
      return 0;

    int insertMateNum = disassembleVOs.length;

    //����ԭ���ϻ���ID����ò��Ϲ���ID
    String mateBaseIDArray[] = new String[insertMateNum];
    for (int i = 0; i < insertMateNum; i++)
      mateBaseIDArray[i] = disassembleVOs[i].getWlbmid();
    //���Ϲ���ID
    String mateMangIDArray[] = PublicHelper.getProcessMangID(disassembleVOs[0].getPk_corp(),
        mateBaseIDArray);

    if (mateMangIDArray == null || mateMangIDArray.length != mateBaseIDArray.length)
      return 0;

    for (int i = 0; i < insertMateNum; i++) {
      clientUI.getBillCardPanel().getBillModel().insertRow(rowIndex + i + 1);

      //�ӹ�Ʒ����
      Object procSLobj = clientUI.getBillCardPanel().getBodyValueAt(rowIndex, "nnum");
      UFDouble processSL = new UFDouble(procSLobj == null ? "0" : procSLobj.toString());

      //���ϵ��
      UFDouble shxs = disassembleVOs[i].getShxs();
      //��׼����
      UFDouble mateSL = disassembleVOs[i].getSl();
      //��λ�ӹ�Ʒ�������� = ��׼���� + ��׼���� * ���ϵ��
//      UFDouble trueSL = mateSL.add(shxs.multiply(mateSL));//gc

      //��ӱ�ʶ������ = ԭ����
      clientUI.getBillCardPanel().setBodyValueAt("", (rowIndex + i + 1), "bismaterial");

      //ԭ���ϻ���id
      String cmatebaseid = mateBaseIDArray[i];
      clientUI.getBillCardPanel().setBodyValueAt(cmatebaseid, (rowIndex + i + 1), "cbaseid");

      //ԭ���Ϲ���id
      String cmatemangid = mateMangIDArray[i];
      clientUI.getBillCardPanel().setBodyValueAt(cmatemangid, (rowIndex + i + 1), "cmangid");

      //���/��������=��Ʒ������*��λ�ӹ�Ʒ��������
      clientUI.getBillCardPanel().setBodyValueAt(mateSL/*(processSL.multiply(trueSL))gc*/, (rowIndex + i + 1),
          "nnum");

      //��λ�ӹ�Ʒ�������������ֶμ����Ŀ���ʱʹ��
      clientUI.getBillCardPanel().setBodyValueAt(mateSL.div(processSL)/*trueSLgc*/, (rowIndex + i + 1), "nmatenum");

      //����ʱ���������е�cbill_bid���ó����Ӧ��Ʒ�еĳ�Ʒ�е�cbill_bid��
      //����:����ʱ���ݳ�Ʒ�е�cbill_bidѭ����������У���cbill_bidֵΨһ����˵����"δ����"����ⵥ�У����ܱ���
      clientUI.getBillCardPanel().setBodyValueAt(
          clientUI.getBillCardPanel().getBodyValueAt(rowIndex, "cbill_bid"), (rowIndex + i + 1),
          "cbill_bid");
      //���ϵ��ۡ����Ͻ��
      clientUI.getBillCardPanel().setBodyValueAt(null, (rowIndex + i + 1), "nprice");
      clientUI.getBillCardPanel().setBodyValueAt(null, (rowIndex + i + 1), "nmny");
    }

    return insertMateNum;
  }

  /**
   * ����ԭ���ϵ�������
   * �������ڣ�(2001-7-24 13:19:07)
   */
  private void setMaterialFreeItem() {
    java.util.ArrayList list = new java.util.ArrayList();
    int num = clientUI.getBillCardPanel().getRowCount();
    for (int i = 0; i < num; i++) {
      Object value = clientUI.getBillCardPanel().getBodyValueAt(i, "bismaterial");
      if (value == null || value.toString().trim().equals("")) {
        Object pk_invmangdoc = clientUI.getBillCardPanel().getBodyValueAt(i, "cmangid");
        if (pk_invmangdoc == null || pk_invmangdoc.toString().trim().length() == 0)
          continue;
        list.add(pk_invmangdoc);
      }
    }
    if (list.size() == 0)
      return;

    // ���λ��������
    java.util.ArrayList retList = new java.util.ArrayList();
    try {
      retList = nc.ui.sc.adjust.AdjustbillHelper.queryFreeVOByInvIDs(list);
    }
    catch (Exception e) {
      clientUI.handleException(e);
      return;
    }
    if (retList == null || retList.size() == 0)
      return;

    // ����������
    int j = 0;
    for (int i = 0; i < num; i++) {
      Object value = clientUI.getBillCardPanel().getBodyValueAt(i, "bismaterial");
      if (value == null || value.toString().trim().equals("")) {

        Object pk_invbasedoc = clientUI.getBillCardPanel().getBodyValueAt(i, "cbaseid");
        Object pk_invmangdoc = clientUI.getBillCardPanel().getBodyValueAt(i, "cmangid");
        Object cinventorycode = clientUI.getBillCardPanel().getBodyValueAt(i, "cinventorycode");
        Object cinventoryname = clientUI.getBillCardPanel().getBodyValueAt(i, "cinventoryname");
        Object invspec = clientUI.getBillCardPanel().getBodyValueAt(i, "invspec");
        Object invtype = clientUI.getBillCardPanel().getBodyValueAt(i, "invtype");
        if (pk_invmangdoc == null)
          continue;

        nc.vo.scm.ic.bill.FreeVO freeVO = null;
        nc.vo.scm.ic.bill.InvVO invVO = null;

        freeVO = (nc.vo.scm.ic.bill.FreeVO) retList.get(j);
        j++;

        invVO = new nc.vo.scm.ic.bill.InvVO();
        invVO.setFreeItemVO(freeVO);
        invVO.setCinvmanid(pk_invmangdoc.toString());
        invVO.setCinventoryid(pk_invbasedoc.toString());
        invVO.setIsFreeItemMgt(new Integer(1));
        invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
        invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
        invVO.setInvspec(invspec == null ? "" : invspec.toString());
        invVO.setInvtype(invtype == null ? "" : invtype.toString());

        FreeItemRefPane freeRef = (FreeItemRefPane) clientUI.getBillCardPanel().getBodyItem(
            "vfree0").getComponent();
        freeRef.setFreeItemParam(invVO);
        //gc
        String vfreeall = (String) clientUI.getBillCardPanel().getBodyValueAt( i, "vfreeall");
        if(vfreeall != null){
        	String []frees = vfreeall.split("@@@");
        	for (int k = 0; k <frees.length && k < 6; k++) {
				String string = frees[k];
				if(invVO.getFreeItemVO() !=null){
					invVO.setAttributeValue("vfree"+(k+1), frees[k]);
					clientUI.getBillCardPanel().setBodyValueAt(frees[k], i, "vfree"+(k+1));
				}
			}
        }
        if(invVO.getFreeItemVO() !=null){
        	clientUI.getBillCardPanel().setBodyValueAt(invVO.getFreeItemVO().getVfree0(), i, "vfree0");
        }
        //gc
        clientUI.getBillCardPanel().setBodyValueAt(invVO, i, "invvo");
      }
    }
  }
  private void comp(OrderVO billvo){
	 	  //�õ�ϵ��
	  OrderItemVO[] items = (OrderItemVO[]) billvo.getChildrenVO();
	  OrderDdlbVO[] ddlbs = billvo.getDdlbvos();
	  if(items==null ||items.length <=0
			  || ddlbs == null || ddlbs.length <=0){
		  return ;
	  }
	  for (int i = 0; i < ddlbs.length; i++) {
		OrderDdlbVO orderDdlbVO = ddlbs[i];
		
	}
  }
 /**
  * gc
  * @param csourcebillbids
  * @param totalnum
  * @throws BusinessException
  */
  private CGDisassembleVO[] getDisassembleVOsFromIC(ArrayList<String> csourcebillbids,HashMap<String, 
		  ArrayList<GeneralBillItemVO>> map,UFDouble[] totalnum,OrderDdlbVO[] ddlbvos ) throws BusinessException{
	  if(map == null){
		//��
		  return null;
	  }
	  List<CGDisassembleVO> list_return = new ArrayList<CGDisassembleVO>();
	  for (int i = 0; i < totalnum.length; i++) {
		UFDouble num = totalnum[i];
		String srcid = csourcebillbids.get(i);
		ArrayList<GeneralBillItemVO> list = map.get(srcid); 
		if(list !=null && list.size() > 0){
			GeneralBillItemVO[] itemvos = list.toArray(new GeneralBillItemVO[list.size()]);
			//��̯
			for (int j = 0; num.doubleValue() > 0 && j < itemvos.length; j++) {
				UFDouble outnum = itemvos[j].getNoutnum() == null ? UFDouble.ZERO_DBL : itemvos[j].getNoutnum();
				if(outnum.doubleValue() >= num.doubleValue()){
					//���ñ�������
					CGDisassembleVO tempvo = newDisassembleVOs(num, itemvos[j],ddlbvos[i]);
					list_return.add(tempvo);
					num = UFDouble.ZERO_DBL;
					break;
				}else{
					//���ñ�������
					CGDisassembleVO tempvo = newDisassembleVOs(outnum, itemvos[j],ddlbvos[i]);
					list_return.add(tempvo);
					num = num.sub(outnum);
				}
			}
			if(num.doubleValue() >0 ){
				//��
				return null;
			}
		}else{
			//��
			return null;
		}
	}
	  return list_return.toArray(new CGDisassembleVO[0]);
  }

  	/**
  	 * gc
  	 * @param num
  	 * @param ddlbvo
  	 * @return
  	 */
	private CGDisassembleVO newDisassembleVOs(UFDouble num,GeneralBillItemVO itemvo,OrderDdlbVO ddlbvo) {
		CGDisassembleVO disassemblevo = new CGDisassembleVO();
		disassemblevo.setSl(new UFDouble(num.doubleValue()));// ������������
		disassemblevo.setPk_corp(ClientEnvironment.getInstance()
				.getCorporation().getPk_corp());
		disassemblevo.setWlbmid(ddlbvo.getCbaseid());
		UFDouble wastrate = ddlbvo.getNwastrate() == null ? UFDouble.ZERO_DBL
				: ddlbvo.getNwastrate();
		disassemblevo.setShxs(UFDouble.ZERO_DBL);
		disassemblevo.setNprice(itemvo.getNprice());
		disassemblevo.setInvVO(itemvo.getInv());
		disassemblevo.setVbatch(itemvo.getVbatchcode());
		
		
		return disassemblevo;

	}
/**
 * gc
 * @param csourcebillbids
 * @return
 * @throws BusinessException
 */
private HashMap<String, ArrayList<GeneralBillItemVO>> queryGeneralItemVO(
		ArrayList<String> csourcebillbids) throws BusinessException {
	IICToSO service = NCLocator.getInstance().lookup(IICToSO.class);
	  HashMap<String, ArrayList<GeneralBillItemVO>> map = service.getGeneralBillItemVOsByCSBids(csourcebillbids);
	return map;
}
}
