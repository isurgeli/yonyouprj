package nc.itf.sc.order;

import java.sql.SQLException;

import nc.vo.pub.BusinessException;
import nc.vo.sc.order.OrderHeaderVO;
import nc.vo.sc.order.OrderVO;

public interface ISCForCGOrderService {
	public OrderHeaderVO[] queryRefHead(String condition) throws SQLException;
	public OrderVO[] queryBillVO(String condition) throws SQLException, BusinessException;
}
