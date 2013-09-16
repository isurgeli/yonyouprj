package nc.ui.sc.settle.btnaction;

import nc.ui.ml.NCLangRes;
import nc.ui.sc.settle.SettleUI;
import nc.ui.sc.settle.btnmgr.SettleBtnObject;

/**
 * ���Ϻ���ҳ���еġ���ѯ����ť������
 * 
 * @author:hanbin
 */
public class QueryBtnAction implements IBtnAction {

  /**
   * Created on 2009-10-20 
   * ���ܣ�������ѯ����ť�ĵ��
   * @param clientUI ���Ϻ���ҳ��
   * @author: ���� hanbin@ufida.com.cn
   */
  public Object doButtonAction(SettleUI clientUI) {
    clientUI
        .showHintMessage(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000360"));//��ʼ��ѯ...

    clientUI.getQryCondition().showModal();
    if (!clientUI.getQryCondition().isCloseOK()) {
      clientUI
          .showHintMessage(NCLangRes.getInstance().getStrByID("common", "UCH009")/*@res "��ѯ���"*/);
      return null;
    }

    try {
      //���"ˢ��"��ť���������
      IBtnAction freshBtnActionObj = ((SettleBtnObject) clientUI.getBtnManager().getBoRefresh())
          .getBtnActionObj();
      return freshBtnActionObj.doButtonAction(clientUI);
    }
    catch (Exception ex) {
      clientUI.handleException(ex);
      return null;
    }
  }
}
