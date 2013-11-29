package nc.ui.ztwzj.sapitf.voucher;

import javax.swing.SwingUtilities;

import nc.ui.pub.beans.MessageDialog;
import nc.ui.pubapp.uif2app.model.IRefreshable;
import nc.ui.pubapp.uif2app.query2.model.IModelDataManager;
import nc.ui.pubapp.uif2app.query2.model.IModelDataProcessor;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.pubapp.uif2app.query2.model.ModelDataRefresher;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.ui.uif2.model.AbstractUIAppModel;
import nc.ui.uif2.model.ModelDataDescriptor;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.res.Variable;

public class ModelDataManager implements IModelDataManager, IRefreshable {
	private AbstractUIAppModel model;

	private IModelDataProcessor modelDataProcessor;

	private IQueryService qryService;

	private IQueryScheme queryScheme;

	public AbstractUIAppModel getModel() {
		return this.model;
	}

	public IModelDataProcessor getModelDataProcessor() {
		return this.modelDataProcessor;
	}

	public IQueryService getQryService() {
		return this.qryService;
	}

	public IQueryScheme getQueryScheme() {
		return this.queryScheme;
	}

	@Override
	public void initModel() {
		this.getModel().initModel(null);
	}

	@Override
	public void initModelByQueryScheme(IQueryScheme qryScheme) {
		this.queryScheme = qryScheme;
		try {
			Object[] objs = this.qryService.queryByQueryScheme(qryScheme);
			if (objs != null && objs.length == Variable.getMaxQueryCount()) {
				// String hint =
				// "��ѯ���̫��ֻ������ " + Variable.getMaxQueryCount() +
				// " �����ݣ�����С��Χ�ٴβ�ѯ";

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String hint = nc.vo.ml.NCLangRes4VoTransl
								.getNCLangRes()
								.getStrByID(
										"pubapp_0",
										"0pubapp-0268",
										null,
										new String[] { ""
												+ Variable.getMaxQueryCount() })/*
																				 * @
																				 * res
																				 * "��ѯ���̫��ֻ������{0}�����ݣ�����С��Χ�ٴβ�ѯ"
																				 */;
						MessageDialog.showHintDlg(getModel().getContext()
								.getEntranceUI(), null, hint);
					}
				});
			}

			if (this.getModelDataProcessor() != null) {
				objs = this.getModelDataProcessor().processModelData(qryScheme,
						objs);
			}
			String schemeName = qryScheme.getName();
			if (!StringUtil.isEmptyWithTrim(schemeName)) {
				ModelDataDescriptor modelDataDescriptor = new ModelDataDescriptor(
						schemeName/*
								 * + "(" + (objs == null ? 0 : objs.length) +
								 * ")"
								 */);
				this.getModel().initModel(objs, modelDataDescriptor);
			} else {
				ModelDataDescriptor modelDataDescriptor = new ModelDataDescriptor(
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"pubapp_0", "0pubapp-0144")/* @res "��ѯ���" */);
				this.getModel().initModel(objs, modelDataDescriptor);
			}

		} catch (BusinessException be) {
			throw new BusinessRuntimeException(be.getMessage()); 
		}catch (Exception e) {
			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("pubapp_0", "0pubapp-0007")/*
																		 * @res
																		 * "��ѯ���ݷ����쳣"
																		 */, e);
		}
	}

	@Override
	public void refresh() {
		if (this.queryScheme != null) {
			this.initModelByQueryScheme(this.queryScheme);
		} else {
			try {
				new ModelDataRefresher(this.model).refreshData();
			} catch (Exception e) {
				ExceptionUtils.wrappException(e);
			}
		}
	}

	@Override
	public boolean refreshable() {
		return this.queryScheme != null;
	}

	public void setModel(AbstractUIAppModel model) {
		this.model = model;
	}

	public void setModelDataProcessor(IModelDataProcessor modelDataProcessor) {
		this.modelDataProcessor = modelDataProcessor;
	}

	public void setService(IQueryService service) {
		this.qryService = service;
	}

	protected void setQueryScheme(IQueryScheme queryScheme) {
		this.queryScheme = queryScheme;
	}

}
