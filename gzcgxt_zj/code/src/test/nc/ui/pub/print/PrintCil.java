package nc.ui.pub.print;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.cil.ICilService;

/**
 * <b>   </b>
 *
 * <p>  
 *
 * </p>
 *
 * Create at 2008-8-13 обнГ01:16:34
 * 
 * @author bq 
 * @since V5.5
 */

public class PrintCil {

	public PrintCil() {
		super();
	}

	public static boolean isTraining() {
		return false;
//		return ((ICilService)NCLocator.getInstance().lookup(ICilService.class.getName())).isNCDEMO();
	}
	
}
