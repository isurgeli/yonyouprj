package nc.ui.sc.settle.btnaction;

import nc.ui.ml.NCLangRes;
import nc.ui.sc.settle.SettleUI;
import nc.ui.sc.settle.btnmgr.SettleBtnObject;

/**
 * 材料核销页面中的“查询”按钮处理类
 * 
 * @author:hanbin
 */
public class QueryBtnAction implements IBtnAction {

  /**
   * Created on 2009-10-20 
   * 功能：处理“查询”按钮的点击
   * @param clientUI 材料核销页面
   * @author: 韩彬 hanbin@ufida.com.cn
   */
  public Object doButtonAction(SettleUI clientUI) {
    clientUI
        .showHintMessage(NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000360"));//开始查询...

    clientUI.getQryCondition().showModal();
    if (!clientUI.getQryCondition().isCloseOK()) {
      clientUI
          .showHintMessage(NCLangRes.getInstance().getStrByID("common", "UCH009")/*@res "查询完成"*/);
      return null;
    }

    try {
      //获得"刷新"按钮处理类对象
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
