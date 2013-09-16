package nc.ui.sc.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.uif.pub.IUifService;
import nc.uif.pub.exception.UifException;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.sc.order.OrderHeaderVO;
import nc.vo.sc.order.OrderItemVO;
import nc.vo.sc.order.OrderVO;

public class OrderModel {
	
	private int currentIndex;
	
	private List<OrderHeaderVO> headerList;
  
  private Map<String, OrderItemVO[]> itemVOs;
  private Map<String, OrderDdlbVO[]> ddlbVOs;
	
	public OrderModel() {
		this.currentIndex = -1; // 表示没有数据
		this.headerList = new ArrayList<OrderHeaderVO>();
    this.itemVOs = new HashMap<String, OrderItemVO[]>();
    this.ddlbVOs = new HashMap<String, OrderDdlbVO[]>();
	}

	public List<OrderHeaderVO> getHeaderList() {
		return headerList;
	}
	
	public OrderHeaderVO[] getHeaderArray() {
		return headerList.toArray( new OrderHeaderVO[ headerList.size() ] );
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public void setCurrentIndex(int newIndex) {
		currentIndex = newIndex;
	}
	
	/**
	 * 当前不是最后一个元素时，此方法才有效。
	 * 当前是最后一个元素时，执行此方法后还是最后一个元素
	 */
	public void next() {
		if ( getCurrentIndex() < (size() - 1) ) {
			currentIndex++;
		}
	}
	
	/**
	 * 当前不是第一个元素时，此方法才有效。
	 * 当前是第一个元素时，执行此方法后还是第一个元素
	 */
	public void previous() {
		if ( getCurrentIndex() > 0 ) {
			currentIndex--;
		}
	}
	
	public void last() {
		if ( size() > 0 ) {
			currentIndex = size() - 1;
		}
		else {
			currentIndex = -1;
		}
	}
	
	public void add( OrderHeaderVO aHeaderVO ) {
		if ( aHeaderVO == null ) {
			return;
		}
		
		headerList.add( aHeaderVO );
    itemVOs.remove(aHeaderVO.getCorderid());
    ddlbVOs.remove(aHeaderVO.getCorderid());

		adjustCurrentIndex();
	}
	
	public void addAll( OrderHeaderVO[] headerVOArray ) {
		if ( (headerVOArray == null) || (headerVOArray.length == 0) ) {
			return;
		}
		
		for ( OrderHeaderVO aHeader : headerVOArray ) {
			if ( aHeader == null ) {
				continue;
			}
			add( aHeader );
		}
		
		adjustCurrentIndex();
	}
	
	public int size() {
		return headerList.size();
	}
	
	public void clear() {
		headerList.clear();
    itemVOs.clear();
    ddlbVOs.clear();
	}
	
	public String getCurrentHeaderId() {
		if ( getCurrentIndex() == -1 ) {
			return null;
		}
		else {
			return ( (OrderHeaderVO)headerList.get( currentIndex ) ).getCorderid();	
		}
	}
	
	public void setHeaderAt( int index, OrderHeaderVO aHeader ) {
		headerList.set( index, aHeader );
    itemVOs.remove(aHeader.getCorderid());
    ddlbVOs.remove(aHeader.getCorderid());
	}
	
	public OrderHeaderVO getHeaderAt( int index ) {
		return headerList.get( index );
	}
  
  public OrderVO getOrderAt(int index) {
    OrderHeaderVO header = getHeaderAt(index);
    if (header == null)
      return null;
    
    OrderVO order = getOrder(header);
    return order;
  }
	
	public OrderHeaderVO getCurrentHeader() {
		if ( getCurrentIndex() == -1 ) {
			return null;
		}
		else {
			return headerList.get( currentIndex );	
		}
	}
  
  public OrderVO getCurrentOrder() {
    OrderHeaderVO header = getCurrentHeader();
    if (header == null)
      return null;
    
    OrderVO order = getOrder(header);
    return order;
  }
  
  private OrderVO getOrder(OrderHeaderVO header) {
    OrderVO order = null;
    if (itemVOs.containsKey(header.getCorderid())) {
      order = new OrderVO();
      order.setParentVO(header);
      order.setChildrenVO(itemVOs.get(header.getCorderid()));
    }
    else {
      try {
        order = OrderHelper.findByPrimaryKey(header.getCorderid());
        if (order != null && order.getChildrenVO() != null)
          itemVOs.put(header.getCorderid(), (OrderItemVO[])order.getChildrenVO());
      }
      catch (Exception ex) {
        //日志异常
        nc.vo.scm.pub.SCMEnv.out(ex);
        nc.ui.pub.beans.MessageDialog.showErrorDlg(null, "错误", ex.getMessage());
      }
    }
    
    //gc
    if (ddlbVOs.containsKey(header.getCorderid())) {
//        order = new OrderVO();
//        order.setParentVO(header);
        order.setDdlbvos(ddlbVOs.get(header.getCorderid()));
      }
      else {
        try {
//          order = OrderHelper.findByPrimaryKey(header.getCorderid());
          if (order != null && order.getChildrenVO() != null)
        	  ddlbVOs.put(header.getCorderid(), (OrderDdlbVO[])order.getDdlbvos());
        }
        catch (Exception ex) {
          //日志异常
          nc.vo.scm.pub.SCMEnv.out(ex);
          nc.ui.pub.beans.MessageDialog.showErrorDlg(null, "错误", ex.getMessage());
        }
      }
    return order;
  }
  
  public OrderVO getOrder(String corderid) {
    OrderHeaderVO header = null;
    for (OrderHeaderVO vo : headerList) {
      if (vo.getCorderid().equals(corderid)) {
        header = vo;
        break;
      }
    }
    if (header == null)
      return null;
    else
      return getOrder(header);
  }
  
  public OrderItemVO[] getOrderItems(String corderid) {
    if (itemVOs.containsKey(corderid)) {
      return itemVOs.get(corderid);
    }
    else {
      try {
        OrderVO order = OrderHelper.findByPrimaryKey(corderid);
        if (order != null && order.getChildrenVO() != null)
          itemVOs.put(corderid, (OrderItemVO[]) order.getChildrenVO());
        return (OrderItemVO[])order.getChildrenVO();
      }
      catch (Exception ex) {
        //日志异常
        nc.vo.scm.pub.SCMEnv.out(ex);
        nc.ui.pub.beans.MessageDialog.showErrorDlg(null, "错误", ex.getMessage());
        return null;
      }
    }
  }
  public OrderDdlbVO[] getOrderDdlbs(String corderid) {
	    if (ddlbVOs.containsKey(corderid)) {
	      return ddlbVOs.get(corderid);
	    }
	    else {
	      try {
//	        OrderVO order = OrderHelper.findByPrimaryKey(corderid);
	    	//gc
	    	  OrderDdlbVO []ddlbs = null;
	            IUifService bo = (IUifService) NCLocator.getInstance().lookup(OrderDdlbVO.class);
	            try {
	            	 ddlbs = (OrderDdlbVO[]) bo.queryByCondition(OrderDdlbVO.class, "corderid='"+corderid+"'");
				} catch (UifException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new SQLException(e.getMessage());
				}
	        if (ddlbs != null && ddlbs.length >= 0)
	        	ddlbVOs.put(corderid, ddlbs);
	        return (OrderDdlbVO[])ddlbs;
	      }
	      catch (Exception ex) {
	        //日志异常
	        nc.vo.scm.pub.SCMEnv.out(ex);
	        nc.ui.pub.beans.MessageDialog.showErrorDlg(null, "错误", ex.getMessage());
	        return null;
	      }
	    }
	  }
	public void deleteCurrentHeader() {
		if ( getCurrentIndex() == -1 ) {
			return;
		}
		
    itemVOs.remove(getCurrentHeader().getCorderid());
    ddlbVOs.remove(getCurrentHeader().getCorderid());
		headerList.remove( currentIndex );
		
		if ( getCurrentIndex() >= size() ) {
			setCurrentIndex( size() - 1 );
		}
	}
	
	private void adjustCurrentIndex() {
		if ( (getCurrentIndex() == -1) && (size() > 0) ) {
			setCurrentIndex( 0 ); 
		}
	}
}
