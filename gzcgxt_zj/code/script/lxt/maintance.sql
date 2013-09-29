--建立表
drop table GZCG_BD_SAMPLEDOC; 
create table GZCG_BD_SAMPLEDOC
(
  burgent      CHAR(1),
  ddate        CHAR(10),
  dr           NUMBER(10) default 0,
  pk_corp      CHAR(4),
  pk_invmandoc CHAR(20),
  pk_sample    CHAR(20) not null,
  pk_user      CHAR(20),
  ts           CHAR(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  vdef1        VARCHAR2(50),
  vdef10       VARCHAR2(50),
  vdef2        VARCHAR2(50),
  vdef3        VARCHAR2(50),
  vdef4        VARCHAR2(50),
  vdef5        VARCHAR2(50),
  vdef6        VARCHAR2(50),
  vdef7        VARCHAR2(50),
  vdef8        VARCHAR2(50),
  vdef9        VARCHAR2(50),
  vmemo        VARCHAR2(200),
  vorderbill   VARCHAR2(50),
  vqctype      VARCHAR2(50),
  vsampleno    VARCHAR2(50),
  vstockbatch  VARCHAR2(50),
  vsamplenofar VARCHAR2(50)
)
;
alter table GZCG_BD_SAMPLEDOC
  add constraint PK_GZCG_BD_SAMPLEDOC primary key (PK_SAMPLE);

drop table GZZG_TMP_INVTYPE;
create table GZZG_TMP_INVTYPE
(
  invcode   VARCHAR2(100),
  checktype VARCHAR2(100),
  pk_corp   VARCHAR2(100)
)
;

--建立质检单基本数据视图
drop view qc_checkbill_b2_v;
create view qc_checkbill_b2_v as (select * from qc_checkbill_b2 where nvl(qc_checkbill_b2.dr,0)=0 and qc_checkbill_b2.cresult is not null);

drop view ic_general_b_v;
create view ic_general_b_v as (select * from ic_general_b where nvl(ic_general_b.dr,0)=0);

drop view ic_general_h_v;
create view ic_general_h_v as (select * from ic_general_h where nvl(ic_general_h.dr,0)=0);



drop view gzcg_qcrp_checkbill_v;
create view gzcg_qcrp_checkbill_v as (
select distinct qc_checkbill.pk_corp,
                qc_checkbill.vcheckbillcode,
                qc_checkbill.cchecktypeid,
                qc_checkbill.ccheckbillid,
                qc_checkbill.cvendormangid,
                qc_checkbill_b1.cmangid,
                bd_invbasdoc.pk_invcl,
                qc_checkbill.nchecknum,
                qc_checkbill_b1.bqualified,
                count(distinct qc_checkbill_b2_v.vsamplecode) over(partition by qc_checkbill.ccheckbillid) nsamplecount,
                qc_checkbill_b1.cdefectprocessid,
                qc_checkbill.creporterid,
                qc_checkbill.dpraydate,
                FIRST_VALUE(ic_general_h_v.daccountdate) over(partition by qc_checkbill.ccheckbillid order by ic_general_h_v.daccountdate) daccountdate, 
                qc_checkbill.dreportdate,
                FIRST_VALUE(ic_general_b_v.vbatchcode) over(partition by qc_checkbill.ccheckbillid order by ic_general_b_v.vbatchcode) vbatchcode,
                po_order.vordercode,
				qc_checkbill_b1.csourcebilltypecode
  from qc_checkbill,
       qc_checkbill_b1,
       qc_checkbill_b2_v,
       po_arriveorder_b,
       po_order,
       ic_general_b_v,
       ic_general_h_v,
       bd_invmandoc,
       bd_invbasdoc
 where qc_checkbill.ccheckbillid = qc_checkbill_b1.ccheckbillid
   and qc_checkbill.ccheckbillid = qc_checkbill_b2_v.ccheckbillid(+)
   and qc_checkbill_b1.cmangid = bd_invmandoc.pk_invmandoc
   and bd_invmandoc.pk_invbasdoc = bd_invbasdoc.pk_invbasdoc
   and qc_checkbill_b1.csourcebillrowid = ic_general_b_v.csourcebillbid(+)
   and ic_general_b_v.cgeneralhid = ic_general_h_v.cgeneralhid(+)
   and qc_checkbill_b1.csourcebillrowid = po_arriveorder_b.carriveorder_bid
   and po_arriveorder_b.corderid = po_order.corderid
   and qc_checkbill_b1.csourcebilltypecode = '23'
   and qc_checkbill_b1.norder = 0
   and nvl(qc_checkbill.dr, 0) = 0
   and nvl(qc_checkbill_b1.dr, 0) = 0
);

drop view gzcg_qcrp_checkbill_v2;
create view gzcg_qcrp_checkbill_v2 as (
select distinct qc_checkbill.pk_corp,
                qc_checkbill.vcheckbillcode,
                qc_checkbill.cchecktypeid,
                qc_checkbill.ccheckbillid,
                qc_checkbill.cvendormangid,
                qc_checkbill_b1.cmangid,
                bd_invbasdoc.pk_invcl,
                qc_checkbill.nchecknum,
                qc_checkbill_b1.bqualified,
                count(distinct qc_checkbill_b2_v.vsamplecode) over(partition by qc_checkbill.ccheckbillid) nsamplecount,
                qc_checkbill_b1.cdefectprocessid,
                qc_checkbill.creporterid,
                qc_checkbill.dpraydate,
                FIRST_VALUE(ic_general_h_v.daccountdate) over(partition by qc_checkbill.ccheckbillid order by ic_general_h_v.daccountdate) daccountdate, 
                qc_checkbill.dreportdate,
                ic_general_b_v.vbatchcode,
                '' vordercode,
                qc_checkbill_b1.csourcebilltypecode
  from qc_checkbill,
       qc_checkbill_b1,
       qc_checkbill_b2_v,
       ic_general_b_v,
       ic_general_h_v,
       bd_invmandoc,
       bd_invbasdoc
 where qc_checkbill.ccheckbillid = qc_checkbill_b1.ccheckbillid
   and qc_checkbill.ccheckbillid = qc_checkbill_b2_v.ccheckbillid(+)
   and qc_checkbill_b1.cmangid = bd_invmandoc.pk_invmandoc
   and bd_invmandoc.pk_invbasdoc = bd_invbasdoc.pk_invbasdoc
   and rtrim(qc_checkbill_b1.csourcebillrowid) = ic_general_b_v.VBATCHCODE(+)
   and qc_checkbill_b1.csourcebillid = ic_general_b_v.CINVENTORYID(+)
   and ic_general_b_v.cgeneralhid = ic_general_h_v.cgeneralhid(+)
   and nvl(ic_general_b_v.NOUTNUM,0) = 0
   and qc_checkbill_b1.csourcebilltypecode = '4Z'
   and qc_checkbill_b1.norder = 0
   and nvl(qc_checkbill.dr, 0) = 0
   and nvl(qc_checkbill_b1.dr, 0) = 0
);

drop view gzcg_qcrp_checkbill_v3;
create view gzcg_qcrp_checkbill_v3 as (
select distinct gzcg_bd_sampledoc.pk_corp,
                '-' vcheckbillcode,
                '-' cchecktypeid,
                '-' ccheckbillid,
                '-' cvendormangid,
                gzcg_bd_sampledoc.vdef1 cmangid,
                '-' pk_invcl,
                1 nchecknum,
                'Y' bqualified,
                1 nsamplecount,
                '-' cdefectprocessid,
                '-' creporterid,
                gzcg_bd_sampledoc.ddate dpraydate,
                gzcg_bd_sampledoc.ddate daccountdate, 
                gzcg_bd_sampledoc.ddate dreportdate,
                '-' vbatchcode,
                '-' vordercode,
                '-' csourcebilltypecode
  from gzcg_bd_sampledoc
 where gzcg_bd_sampledoc.vqctype = 'S'
   and nvl(gzcg_bd_sampledoc.dr, 0) = 0
);

--删除模板
delete from sm_funcregister where (sm_funcregister.fun_code >= 'C0020410' and sm_funcregister.fun_code < 'C0020439') or (sm_funcregister.fun_code >= 'C0010115' and sm_funcregister.fun_code < 'C0010120');
delete from sm_butnregister where sm_butnregister.fun_code in ('C002010190', 'C002010191');

delete from pub_billtemplet_t where pub_billtemplet_t.pk_billtemplet in (select pub_billtemplet.pk_billtemplet from pub_billtemplet where pub_billtemplet.pk_billtypecode in ('C0010115'));
delete from pub_billtemplet_b where pub_billtemplet_b.pk_billtemplet in (select pub_billtemplet.pk_billtemplet from pub_billtemplet where pub_billtemplet.pk_billtypecode in ('C0010115'));
delete from pub_billtemplet where pub_billtemplet.pk_billtypecode in ('C0010115');

delete from pub_billtemplet_t where pub_billtemplet_t.pk_billtemplet in (select pub_billtemplet.pk_billtemplet from pub_billtemplet where pub_billtemplet.pk_billtypecode in ('C0020410','C0020420','C0020430'));
delete from pub_billtemplet_b where pub_billtemplet_b.pk_billtemplet in (select pub_billtemplet.pk_billtemplet from pub_billtemplet where pub_billtemplet.pk_billtypecode in ('C0020410','C0020420','C0020430'));
delete from pub_billtemplet where pub_billtemplet.pk_billtypecode in ('C0020410','C0020420','C0020430');

delete from pub_report_model where pub_report_model.pk_templet in (select pub_report_templet.pk_templet from pub_report_templet where pub_report_templet.node_code in ('C002041000','C00201010100','C002042000','C002043000'));
delete from pub_report_templet where pub_report_templet.node_code in ('C002041000','C00201010100','C002042000','C002043000');

delete from pub_query_condition where pub_query_condition.pk_templet in (select pub_query_templet.id from pub_query_templet where pub_query_templet.node_code in ('C0020410','C0020420','C0020430'));
delete from pub_query_templet where pub_query_templet.node_code in ('C0020410','C0020420','C0020430');

delete from pub_query_condition where pub_query_condition.pk_templet in (select pub_query_templet.id from pub_query_templet where pub_query_templet.node_code in ('C0010115','C0010116','C0010117'));
delete from pub_query_templet where pub_query_templet.node_code in ('C0010115','C0010116','C0010117');

delete from pub_print_dataitem where vnodecode='C0020430';
delete from pub_print_cell where pub_print_cell.ctemplateid in (select pub_print_template.ctemplateid from pub_print_template where pub_print_template.vnodecode='C0020430');
delete from pub_print_template where vnodecode='C0020430';

delete from pub_systemplate where funnode in ('C0010115', 'C0010116','C0010117', 'C0020410','C0020420','C0020430');

--功能节点注册
insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NS0N', 'nc.ui.gzcg.report.CustomerAnalysisUI', 'C0020410', null, null, 0, 'C0020410', null, 4, '供应商质量分析', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6L', 'nc.ui.gzcg.report.MaterialAnalysisUI', 'C0020411', null, null, 0, 'C0020411', null, 4, '主原料质量分析', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6M', 'nc.ui.gzcg.report.AssistMaterialAnalysisUI', 'C0020412', null, null, 0, 'C0020412', null, 4, '辅料质量分析', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6N', 'nc.ui.gzcg.report.SemiProductAnalysisUI', 'C0020413', null, null, 0, 'C0020413', null, 4, '半产品质量分析', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6O', 'nc.ui.gzcg.report.ProductAnalysisUI', 'C0020414', null, null, 0, 'C0020414', null, 4, '产品质量分析', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYC', 'nc.ui.gzcg.report.MaterialStatisticsUI', 'C0020420', null, null, 0, 'C0020420', null, 4, '主原料检验数据', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYD', 'nc.ui.gzcg.report.AssistMaterialStatisticsUI', 'C0020421', null, null, 0, 'C0020421', null, 4, '辅料检验数据', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYE', 'nc.ui.gzcg.report.SemiProductStatisticsUI', 'C0020422', null, null, 0, 'C0020422', null, 4, '半产品检验数据', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYF', 'nc.ui.gzcg.report.ProductStatisticsUI', 'C0020423', null, null, 0, 'C0020423', null, 4, '产品检验数据', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001O1BC', 'nc.ui.gzcg.mixturetool.MixtureToolUI', 'C0020430', null, null, 0, 'C0020430', null, 4, '混料计算工具', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NIR0', 'nc.ui.gzcg.bd.MaterialSampleUI', 'C0010115', null, null, 0, 'C0010115', null, 4, '原辅料取样', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0010100000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NOX8', 'nc.ui.gzcg.bd.SemiProductSampleUI', 'C0010116', null, null, 0, 'C0010116', null, 4, '中控取样', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0010100000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NOX9', 'nc.ui.gzcg.bd.ProductSampleUI', 'C0010117', null, null, 0, 'C0010117', null, 4, '产品取样', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0010100000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

--按键注册
insert into sm_butnregister (CFUNID, CLASS_NAME, DR, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, ORGTYPECODE, PARENT_ID, RESID, TS)
values ('0001ZF1000000001O919', '增加样本', 0, 0, 'C002010190', null, 5, '增加样本', 2, '1', 'C0020101000000000000', null, '2013-07-22 14:13:27');

insert into sm_butnregister (CFUNID, CLASS_NAME, DR, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, ORGTYPECODE, PARENT_ID, RESID, TS)
values ('0001ZF1000000001O91A', '检测数据', 0, 0, 'C002010191', null, 5, '检测数据', 2, '1', 'C0020101000000000000', null, '2013-07-22 14:13:48');

--样品档案单据模板
insert into pub_billtemplet (BILL_TEMPLETCAPTION, BILL_TEMPLETNAME, DR, MODEL_TYPE, NODECODE, OPTIONS, PK_BILLTEMPLET, PK_BILLTYPECODE, PK_CORP, RESID, SHAREFLAG, TS, VALIDATEFORMULA, METADATACLASS)
values ('样品档案', 'SYSTEM', 0, null, null, null, '0001ZF1000000001NL2C', 'C0010115', '@@@@', null, 'N', '2013-09-08 09:49:53', null, 'sample_bill.sampledoc');

insert into pub_billtemplet_t (BASETAB, DR, MIXINDEX, PK_BILLTEMPLET, PK_BILLTEMPLET_T, POS, POSITION, RESID, TABCODE, TABINDEX, TABNAME, TS, METADATACLASS, METADATAPATH, VDEF1, VDEF2, VDEF3)
values (null, 0, null, '0001ZF1000000001NL2C', '0001AB1000000001NI0N', 1, 1, null, 'sampledoc', 0, '样品档案', '2013-09-08 09:49:53', 'sample_bill.sampledoc', null, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 5, '存货编码', null, 0, 1, null, -1, 'pk_invmandoc', 20, 'vinvmancode', 0, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '1003ZF1000000001SUVM', '@@@@', 1, '存货档案', null, null, 'N', 1, 4, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, '主检验批次', null, 0, 1, null, -1, null, -1, 'vsamplenofar', 0, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001OCUA', '@@@@', 1, null, null, null, 'N', 1, 8, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vsamplenofar', 'sample_bill.sampledoc.vsamplenofar', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef10', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2Q', null, 1, null, null, null, 'N', 0, 24, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef10', 'sample_bill.sampledoc.vdef10', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'ddate', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2R', null, 1, null, null, null, 'N', 0, 13, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'ddate', 'sample_bill.sampledoc.ddate', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef9', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2S', null, 1, null, null, null, 'N', 0, 23, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef9', 'sample_bill.sampledoc.vdef9', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'pk_corp', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2T', null, 1, null, null, null, 'N', 0, 2, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_corp', 'sample_bill.sampledoc.pk_corp', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef8', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2U', null, 1, null, null, null, 'N', 0, 22, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef8', 'sample_bill.sampledoc.vdef8', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vstockbatch', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2V', null, 1, null, null, null, 'N', 0, 11, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vstockbatch', 'sample_bill.sampledoc.vstockbatch', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vorderbill', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2X', null, 1, null, null, null, 'N', 0, 10, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vorderbill', 'sample_bill.sampledoc.vorderbill', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef4', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2Y', null, 1, null, null, null, 'N', 0, 18, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef4', 'sample_bill.sampledoc.vdef4', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'burgent', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2Z', null, 1, null, null, null, 'N', 1, 12, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'burgent', 'sample_bill.sampledoc.burgent', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, 'pk_invbas', null, 0, 0, null, -1, null, 20, 'pk_invbas', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL31', null, 1, null, null, null, 'N', 0, 26, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '存货名称不用', null, 0, 0, null, -1, null, 20, 'vinvmanname', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2W', null, 1, null, null, null, 'N', 0, 9, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, '存货PK', null, 0, 0, 'pk_invbas->getColValue(bd_invmandoc, pk_invbasdoc, pk_invmandoc, pk_invmandoc);vdef1->getColValue(bd_invbasdoc, invname, pk_invbasdoc, pk_invbas);', -1, null, -1, 'pk_invmandoc', null, 'N', 1, 'N', 'pk_invbas->getColValue(bd_invmandoc, pk_invbasdoc, pk_invmandoc, pk_invmandoc);vinvmancode->getColValue(bd_invbasdoc, invcode, pk_invbasdoc, pk_invbas);', 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2G', null, 1, null, null, null, 'N', 0, 3, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_invmandoc', 'sample_bill.sampledoc.pk_invmandoc', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vqctype', null, 'N', 1, 'N', null, 0, 'N', 1, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2D', null, 1, null, null, null, 'N', 0, 6, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vqctype', 'sample_bill.sampledoc.vqctype', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef7', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2E', null, 1, null, null, null, 'N', 0, 21, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef7', 'sample_bill.sampledoc.vdef7', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vmemo', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2F', null, 1, null, null, null, 'N', 1, 15, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vmemo', 'sample_bill.sampledoc.vmemo', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef6', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2H', null, 1, null, null, null, 'N', 0, 20, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef6', 'sample_bill.sampledoc.vdef6', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'ts', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2I', null, 1, null, null, null, 'N', 0, 25, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'ts', 'sample_bill.sampledoc.ts', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vsampleno', null, 'N', 1, 'Y', null, 0, 'N', 1, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2J', null, 1, null, null, null, 'N', 1, 7, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vsampleno', 'sample_bill.sampledoc.vsampleno', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, '存货名称', null, 0, 0, null, -1, null, -1, 'vdef1', null, 'N', 1, 'Y', null, 0, 'N', 1, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2K', null, 1, null, null, null, 'N', 1, 5, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef1', 'sample_bill.sampledoc.vdef1', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'pk_sample', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2L', null, 1, null, null, null, 'N', 0, 1, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_sample', 'sample_bill.sampledoc.pk_sample', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'pk_user', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2M', null, 1, null, null, null, 'N', 0, 14, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_user', 'sample_bill.sampledoc.pk_user', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef5', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2N', null, 1, null, null, null, 'N', 0, 19, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef5', 'sample_bill.sampledoc.vdef5', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef2', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2O', null, 1, null, null, null, 'N', 0, 16, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef2', 'sample_bill.sampledoc.vdef2', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef3', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2P', null, 1, null, null, null, 'N', 0, 17, 'sampledoc', '样品档案', 0, '2013-09-08 09:49:53', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef3', 'sample_bill.sampledoc.vdef3', null);

--样品档案查询模板
insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001SXYR', 'C0010115', '原辅料取样查询', 'C0010115', '@@@@', null, '2013-07-10 11:18:24', '0f33b437-d2a3-48df-8169-2fa8b6dc47e2');

insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001SXYZ', 'C0010116', '中控取样查询', 'C0010116', '@@@@', null, '2013-07-10 11:20:08', '0f33b437-d2a3-48df-8169-2fa8b6dc47e2');

insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001SXZ6', 'C0010117', '产品取样查询', 'C0010117', '@@@@', null, '2013-07-10 11:20:33', '0f33b437-d2a3-48df-8169-2fa8b6dc47e2');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 0, 0, null, 0, 'vorderbill', '采购订单号', '1003ZF1000000001SXYS', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 4, 4, 0, null, 0, 'burgent', '是否加急', '1003ZF1000000001SXYW', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 6, 0, null, 0, 'vmemo', '备注', '1003ZF1000000001SXYY', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 1, 0, null, 0, 'vsampleno', '检验批次', '1003ZF1000000001SXYT', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 2, 0, null, 0, 'ddate', '日期', '1003ZF1000000001SXYU', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'between@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('操作员', 5, 6, 0, null, 0, 'pk_user', '录入员', '1003ZF1000000001SYQI', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 11:18:24', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('存货档案', 5, 3, 0, null, 0, 'pk_invmandoc', '存货', '1003ZF1000000001SXYV', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('操作员', 5, 5, 0, null, 0, 'pk_user', '录入员', '1003ZF1000000001SYQJ', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 11:20:08', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 4, 0, 0, null, 0, 'burgent', '是否加急', '1003ZF1000000001SXZ0', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('存货档案', 5, 1, 0, null, 0, 'pk_invmandoc', '存货主键', '1003ZF1000000001SXZ1', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 2, 0, null, 0, 'ddate', '日期', '1003ZF1000000001SXZ2', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'between@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 4, 0, null, 0, 'vsampleno', '检验批次', '1003ZF1000000001SXZ4', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 5, 0, null, 0, 'vmemo', '备注', '1003ZF1000000001SXZ5', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 0, 0, null, 0, 'ddate', '日期', '1003ZF1000000001SXZ7', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'between@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('存货档案', 5, 2, 0, null, 0, 'pk_invmandoc', '存货主键', '1003ZF1000000001SXZ9', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 3, 0, null, 0, 'vmemo', '备注', '1003ZF1000000001SXZA', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 4, 4, 0, null, 0, 'burgent', '是否加急', '1003ZF1000000001SXZB', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 5, 0, null, 0, 'vsampleno', '检验批次', '1003ZF1000000001SXZC', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '等于@大于@大于等于@小于@小于等于@相似@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

--质检报表、混料、质检单弹出 报表模板
insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C002041000', '供应商质量分析', 'C0020410', '@@@@', '0001ZF1000000001NVVN', null, null, null, '2013-07-11 13:14:45');

insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C00201010100', '质检数据', 'C002010101', '@@@@', '0001ZF1000000001ODM5', null, null, null, '2013-07-25 15:55:50');

insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C002042000', '主原料检验数据', 'C0020420', '@@@@', '1009ZF1000000002RKVV', null, null, null, '2013-07-13 18:46:09');

insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C002043000', '混料计算工具', 'C0020430', '@@@@', '1009ZF1000000002RN71', null, null, null, '2013-07-15 10:30:09');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustcode', '供应商编码', 0, '供应商编码', 80, 0, 0, null, 'Y', 'N', 'N', null, 1, 'C0020410', null, null, '@@@@', '0001ZF1000000001NVVO', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustname', '供应商名称', 0, '供应商名称', 80, 0, 0, null, 'Y', 'N', 'N', null, 2, 'C0020410', null, null, '@@@@', '0001ZF1000000001NVVP', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvclcode', '存货分类编码', 0, '存货分类编码', 80, 0, 0, null, 'Y', 'N', 'N', null, 3, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TR', '0001ZF1000000001NVVN', 1, null, null, 0, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvclname', '存货分类名称', 0, '存货分类名称', 80, 0, 0, null, 'Y', 'N', 'N', null, 4, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TS', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdoccode', '存货编码', 0, '存货编码', 80, 0, 0, null, 'Y', 'N', 'N', null, 5, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TT', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdocname', '存货名称', 0, '存货名称', 80, 0, 0, null, 'Y', 'N', 'N', null, 6, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TU', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvspec', '规格', 0, '规格', 80, 0, 0, null, 'Y', 'N', 'N', null, 7, 'C0020410', null, null, '@@@@', '1009ZF1000000002RZJM', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vmonth', '月度', 0, '月度', 80, 0, 0, null, 'Y', 'N', 'N', null, 9, 'C0020410', null, null, '@@@@', '1003ZF1000000001T2LK', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninamount', '来料数量', 0, '来料数量', 80, 2, 0, null, 'Y', 'N', 'N', null, 10, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TV', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nincount', '来料次数', 0, '来料次数', 80, 1, 0, null, 'Y', 'N', 'N', null, 11, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TW', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nsamplecount', '检验批次数', 0, '检验批次数', 80, 1, 0, null, 'Y', 'N', 'N', null, 12, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TX', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninpassamount', '来料合格数量', 0, '来料合格数量', 80, 2, 0, null, 'Y', 'N', 'N', null, 13, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U4', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninpasscount', '来料合格次数', 0, '来料合格次数', 80, 1, 0, null, 'Y', 'N', 'N', null, 14, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TY', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'namountpassratio', '数量合格率', 0, '数量合格率', 80, 2, 0, null, 'Y', 'N', 'N', null, 15, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TZ', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ncountpassratio', '次数合格率', 0, '次数合格率', 80, 2, 0, null, 'Y', 'N', 'N', null, 16, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U0', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'namountorder', '数量合格排名', 0, '数量合格排名', 80, 1, 0, null, 'Y', 'N', 'N', null, 17, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U1', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ncountorder', '次数合格排名', 0, '次数合格排名', 80, 1, 0, null, 'Y', 'N', 'N', null, 18, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U2', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvunit', '单位', 0, '单位', 80, 0, 0, null, 'Y', 'N', 'N', null, 8, 'C0020410', null, null, '@@@@', '0001AB1000000001NS0B', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-09-05 09:52:27', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vsampleno', '质检批次', 0, '质检批次', 80, 0, 0, null, 'Y', 'N', 'N', null, 1, 'C002010101', null, null, '@@@@', '0001ZF1000000001ODM6', '0001ZF1000000001ODM5', 1, null, null, 1, '2013-07-25 15:55:50', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vorderbillcode', '采购单号', 0, '采购单号', 80, 0, 0, null, 'Y', 'N', 'N', null, 1, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVW', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vstockbatch', '入库（生产）批次', 0, '入库（生产）批次', 80, 0, 0, null, 'Y', 'N', 'N', null, 2, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVX', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustcode', '供应商编码', 0, '供应商编码', 80, 0, 0, null, 'Y', 'N', 'N', null, 3, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVY', '1009ZF1000000002RKVV', 1, null, null, 0, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustname', '供应商名称', 0, '供应商名称', 80, 0, 0, null, 'Y', 'N', 'N', null, 4, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVZ', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdoccode', '存货编码', 0, '存货编码', 80, 0, 0, null, 'Y', 'N', 'N', null, 5, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW0', '1009ZF1000000002RKVV', 1, null, null, 0, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdocname', '存货名称', 0, '存货名称', 80, 0, 0, null, 'Y', 'N', 'N', null, 6, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW1', '1009ZF1000000002RKVV', 1, null, null, 0, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'dcheck', '检测日期', 0, '检测日期', 80, 0, 0, null, 'Y', 'N', 'N', null, 7, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW2', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vsamplecode', '检验批次', 0, '检验批次', 80, 0, 0, null, 'Y', 'N', 'N', null, 8, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW3', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vchargepsn', '检验员', 0, '检验员', 80, 0, 0, null, 'Y', 'N', 'N', null, 9, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW4', '1009ZF1000000002RKVV', 1, null, null, 0, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vprocessname', '处理方式', 0, '处理方式', 80, 0, 0, null, 'Y', 'N', 'N', null, 10, 'C0020420', null, null, '@@@@', '0001AB1000000001NS09', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninnum', '入库数量', 0, '入库数量', 80, 2, 0, null, 'Y', 'N', 'N', null, 11, 'C0020420', null, null, '@@@@', '0001AB1000000001NS0A', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nstocknum', '库存数量', 0, '库存数量', 80, 2, 0, null, 'Y', 'N', 'N', null, 12, 'C0020420', null, null, '@@@@', '0001AB1000000001NS1R', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'bselect', '选择', 0, '选择', 80, 4, 0, null, 'Y', 'N', 'N', null, 13, 'C0020420', null, null, '@@@@', '0001ZF1000000001O2TA', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-09-05 09:13:02', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'dindate', '入库日期', 0, '入库日期', 80, 3, 0, null, 'Y', 'N', 'N', null, 5, 'C0020430', null, null, '@@@@', '0001AB1000000001NUBO', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustname', '供应商', 0, '供应商', 80, 0, 0, null, 'Y', 'N', 'N', null, 3, 'C0020430', null, null, '@@@@', '0001AB1000000001NUBP', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nmaxusenum', '最大使用量', 0, '最大使用量', 80, 2, 0, null, 'Y', 'N', 'N', null, 8, 'C0020430', null, null, '@@@@', '0001AB1000000001NUBQ', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nunitusenum', '单位使用量', 0, '单位使用量', 80, 2, 0, null, 'Y', 'N', 'N', null, 9, 'C0020430', null, null, '@@@@', '0001AB1000000001NUBR', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'bselect', '选择', 0, '选择', 80, 4, 0, null, 'Y', 'N', 'N', null, 1, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN7A', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdocname', '存货名称', 0, '存货名称', 80, 0, 0, null, 'Y', 'N', 'N', null, 2, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN72', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vstock', '仓库', 0, '仓库', 80, 0, 0, null, 'Y', 'N', 'N', null, 4, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN7B', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vbatchcode', '入库批次', 0, '入库批次', 80, 0, 0, null, 'Y', 'N', 'N', null, 6, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN73', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nstocknum', '库存数量', 0, '库存数量', 80, 2, 0, null, 'Y', 'N', 'N', null, 7, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN74', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nusenum', '总使用量', 0, '总使用量', 80, 2, 0, null, 'Y', 'N', 'N', null, 10, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN75', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nremainnum', '剩余数量', 0, '剩余数量', 80, 2, 0, null, 'Y', 'N', 'N', null, 11, 'C0020430', null, null, '@@@@', '0001AB1000000001NR8K', '1009ZF1000000002RN71', 1, null, null, 1, '2013-09-02 11:08:44', null, null, 'N');


--质检报表查询模板
insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001T11T', 'C0020410', '供应商质量分析', 'C0020410', '@@@@', null, '2013-07-12 09:02:58', null);

insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1009ZF1000000002RKVN', 'C0020420', '主原料检验数据', 'C0020420', '@@@@', null, '2013-07-14 08:13:17', null);

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('供应商档案', 5, 0, 0, null, null, 'gzcg_qcrp_checkbill_v.cvendormangid', '供应商', '1003ZF1000000001T11U', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('存货分类', 5, 2, 0, null, null, 'gzcg_qcrp_checkbill_v.pk_invcl', '存货分类', '1003ZF1000000001T11V', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-08-30 16:50:01', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('存货档案', 5, 3, 0, null, null, 'gzcg_qcrp_checkbill_v.cmangid', '存货', '1003ZF1000000001T11W', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-08-30 16:50:01', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 4, 0, null, null, 'gzcg_qcrp_checkbill_v.daccountdate', '入库时间', '1003ZF1000000001T11X', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-08-30 16:50:01', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 5, 0, null, null, 'gzcg_qcrp_checkbill_v.dpraydate', '取样时间', '1003ZF1000000001T11Y', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-08-30 16:50:01', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 6, 0, null, null, 'gzcg_qcrp_checkbill_v.dreportdate', '报告时间', '1003ZF1000000001T11Z', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-08-30 16:50:01', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('地区分类', 5, 1, 0, null, null, 'bd_cubasdoc.pk_areacl', '供应商分类', '0001AB1000000001NS0D', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-08-30 17:17:42', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('供应商档案', 5, 0, 0, null, null, 'gzcg_qcrp_checkbill_v.cvendormangid', '供应商', '1009ZF1000000002RKVO', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-13 17:36:06', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('SX, =null,合格=Y,不合格=N', 6, 2, 0, null, null, 'gzcg_qcrp_checkbill_v.bqualified', '是否合格', '1009ZF1000000002RKVP', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-08-30 16:51:39', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 3, 0, null, null, 'gzcg_qcrp_checkbill_v.daccountdate', '入库时间', '1009ZF1000000002RKVQ', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-08-30 16:51:39', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 4, 0, null, null, 'gzcg_qcrp_checkbill_v.dpraydate', '取样时间', '1009ZF1000000002RKVR', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-08-30 16:51:39', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 5, 0, null, null, 'gzcg_qcrp_checkbill_v.dreportdate', '报告时间', '1009ZF1000000002RKVS', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-08-30 16:51:39', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 6, 0, null, null, 'qc_checkbill_b2.vsamplecode', '检验批次', '1009ZF1000000002RKVT', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-08-30 16:51:39', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('地区分类', 5, 1, 0, null, null, 'bd_cubasdoc.pk_areacl', '供应商分类', '0001AB1000000001NS0E', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '等于@大于@大于等于@小于@小于等于@包含@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-08-30 17:17:19', null, null, null, null, null, 'N');

--存货管理档案自定义项
insert into bd_defdoclist (DOCISLEVFLAG, DOCLISTCODE, DOCLISTNAME, DOCLISTSYSTYPE, DR, ISCORPCANCHG, PK_DEFDOCLIST, TS)
values ('N', 'CG901', '质检分析类别', 2, 0, 'N', '0001ZF1000000001O92G', '2013-07-22 16:32:16');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('001', '主原料', 1, 0, '0001', '0001ZF1000000001O92R', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '主原料');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('002', '主辅料', 1, 0, '0001', '0001ZF1000000001O92S', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '主辅料');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('003', '半成品', 1, 0, '0001', '0001ZF1000000001O92T', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '半成品');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('004', '成品', 1, 0, '0001', '0001ZF1000000001O92U', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '成品');

insert into bd_defdef (DEFCODE, DEFNAME, DIGITNUM, DR, LENGTHNUM, PK_BDINFO, PK_DEFDEF, PK_DEFDOCLIST, TS, TYPE)
values ('CG901', '质检分析类别', null, 0, 100, '0001ZF1000000001O92H', '0001ZF1000000001O92W', '0001ZF1000000001O92G', '2013-07-22 16:34:34', '统计');

insert into bd_bdinfo (ACCESSCLASS, BASEDOCTABLENAME, BASEDOCTABLEPKNAME, BDCODE, BDNAME, BDTYPE, BUDGETCONST, CLASSCHEME, CODEFIELDNAME, CORPFIELDNAME, DR, FATHERFIELDNAME, FUNCCODE, ISDEF, ISINCLUDEGROUPDATA, ISPARA_LEVSCHEME, ISSELFREF, NAMEFIELDNAME, ORGBOOKFIELDNAME, ORGTYPECODE, PK_BDINFO, PK_CORP, PK_DEFDEF, REFNODENAME, REFSYSTEM, RESERVED1, RESERVED2, SELFREFCLASS, TABLENAME, TABLEPKNAME, TS, CODERULEGETTER)
values (null, null, null, 'DCG901', '质检分析类别', null, '0001ZF1000000001O92G', null, 'doccode', 'pk_corp', 0, null, null, 'Y', 'Y', null, null, 'docname', null, '1                   ', '0001ZF1000000001O92H', '0001', '0001ZF1000000001O92G', null, 'gl', null, null, 'nc.ui.bd.def.DefaultDefdocRefModel', 'bd_defdoc', 'pk_defdoc', '2013-07-22 16:32:16', null);

update bd_defquote set PK_DEFDEF='0001ZF1000000001O92W' where FIELDNAME='def3' and PK_CORP is null and PK_DEFUSED='sm000000000000000005';
--insert into bd_defquote (DR, FIELDNAME, PK_CORP, PK_DEFDEF, PK_DEFQUOTE, PK_DEFUSED, TS)
--values (0, 'def3', null, '0001ZF1000000001O92W', '00015734403916289929', 'sm000000000000000005', '2013-07-22 16:37:29');
--混料单据与检测数据汇总模板
insert into pub_billtemplet (BILL_TEMPLETCAPTION, BILL_TEMPLETNAME, DR, MODEL_TYPE, NODECODE, OPTIONS, PK_BILLTEMPLET, PK_BILLTYPECODE, PK_CORP, RESID, SHAREFLAG, TS, VALIDATEFORMULA, METADATACLASS)
values ('混料', 'SYSTEM', 0, null, 'C0020430', null, '0001ZF1000000001O1BJ', 'C0020430', '@@@@', null, 'N', '2013-07-15 11:21:34', null, null);

insert into pub_billtemplet (BILL_TEMPLETCAPTION, BILL_TEMPLETNAME, DR, MODEL_TYPE, NODECODE, OPTIONS, PK_BILLTEMPLET, PK_BILLTYPECODE, PK_CORP, RESID, SHAREFLAG, TS, VALIDATEFORMULA, METADATACLASS)
values ('分析数据汇总', 'SYSTEM', 0, null, 'C0020420', null, '0001AB1000000001NS0X', 'C0020420', '@@@@', null, 'N', '2013-08-31 15:18:58', null, null);

insert into pub_billtemplet_t (BASETAB, DR, MIXINDEX, PK_BILLTEMPLET, PK_BILLTEMPLET_T, POS, POSITION, RESID, TABCODE, TABINDEX, TABNAME, TS, METADATACLASS, METADATAPATH, VDEF1, VDEF2, VDEF3)
values (null, 0, null, '0001ZF1000000001O1BJ', '1009ZF1000000002RN78', 1, 1, null, 'table', 0, '表体', '2013-07-15 11:21:34', null, null, null, null, null);

insert into pub_billtemplet_t (BASETAB, DR, MIXINDEX, PK_BILLTEMPLET, PK_BILLTEMPLET_T, POS, POSITION, RESID, TABCODE, TABINDEX, TABNAME, TS, METADATACLASS, METADATAPATH, VDEF1, VDEF2, VDEF3)
values (null, 0, null, '0001AB1000000001NS0X', '0001AB1000000001NTJZ', 1, 1, null, 'table', 0, 'table', '2013-08-31 15:18:58', null, null, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, 'ccheckitemid', null, 0, 1, null, -1, null, 20, 'ccheckitemid', 0, 'N', 1, 'N', 'vcheckitem->getColValue(qc_checkitem, ccheckitemname, ccheckitemid, ccheckitemid);', 0, 'N', 0, null, '0001ZF1000000001O1BJ', '1009ZF1000000002RN77', '@@@@', 1, null, null, null, 'N', 0, 6, 'table', '表体', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '混料标准', null, 0, 1, null, -1, null, 20, 'vrequirevalue', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BK', null, 1, null, null, null, 'N', 1, 4, 'table', '表体', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 60, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 4, '选择', null, 0, 1, null, -1, null, 20, 'bselect', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BL', null, 1, null, null, null, 'N', 1, 1, 'table', '表体', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 20, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 2, '混料结果', null, 0, 0, null, -1, null, 20, 'vcomputevalue', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BM', null, 1, null, null, null, 'N', 1, 5, 'table', '表体', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 60, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '检验项目', null, 0, 0, null, -1, null, 20, 'vcheckitem', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BN', null, 1, null, null, null, 'N', 1, 2, 'table', '表体', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '企业标准', null, 0, 0, null, -1, null, 20, 'vstandardvalue', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BO', null, 1, null, null, null, 'N', 1, 3, 'table', '表体', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 60, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '存货', null, 0, 1, null, -1, null, 20, 'vinvname', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001AB1000000001NS0X', '0001AB1000000001NS0Y', null, 1, null, null, null, 'N', 1, 3, 'table', 'table', 0, '2013-08-31 15:18:58', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 2, '入库数量', null, 0, 1, null, -1, null, 20, 'ninnum', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001AB1000000001NS0X', '0001AB1000000001NS0Z', null, 1, '2', null, null, 'N', 1, 4, 'table', 'table', 0, '2013-08-31 15:18:58', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '时间段', null, 0, 1, null, -1, null, 20, 'vtimesec', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001AB1000000001NS0X', '0001AB1000000001NS10', null, 1, null, null, null, 'N', 1, 1, 'table', 'table', 0, '2013-08-31 15:18:58', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '供应商', null, 0, 1, null, -1, null, 20, 'vcustdesc', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001AB1000000001NS0X', '0001AB1000000001NS11', null, 1, null, null, null, 'N', 1, 2, 'table', 'table', 0, '2013-08-31 15:18:58', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 2, '库存数量', null, 0, 1, null, -1, null, 20, 'nstocknum', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001AB1000000001NS0X', '0001AB1000000001NS12', null, 1, '2', null, null, 'N', 1, 5, 'table', 'table', 0, '2013-08-31 15:18:58', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

--混料打印模板
insert into pub_print_dataitem (DR, IDATATYPE, ITYPE, PK_CORP, PK_VARITEM, PREPARE1, RESID, TS, USERDEFFLAG, VNODECODE, VTABLECODE, VTABLENAME, VVAREXPRESS, VVARNAME)
values (0, null, null, '@@@@', 'C0020430000000000002', null, null, '2006-07-03 17:30:52', null, 'C0020430', null, null, 'vmainware', '主仓库');

insert into pub_print_dataitem (DR, IDATATYPE, ITYPE, PK_CORP, PK_VARITEM, PREPARE1, RESID, TS, USERDEFFLAG, VNODECODE, VTABLECODE, VTABLENAME, VVAREXPRESS, VVARNAME)
values (0, null, null, '@@@@', 'C0020430000000000003', null, null, '2006-07-03 17:30:52', null, 'C0020430', null, null, 'vmaininvl', '主物料');

insert into pub_print_dataitem (DR, IDATATYPE, ITYPE, PK_CORP, PK_VARITEM, PREPARE1, RESID, TS, USERDEFFLAG, VNODECODE, VTABLECODE, VTABLENAME, VVAREXPRESS, VVARNAME)
values (0, null, null, '@@@@', 'C0020430000000000004', null, null, '2006-07-03 17:30:52', null, 'C0020430', null, null, 'vbatchno', '批次号');

insert into pub_print_dataitem (DR, IDATATYPE, ITYPE, PK_CORP, PK_VARITEM, PREPARE1, RESID, TS, USERDEFFLAG, VNODECODE, VTABLECODE, VTABLENAME, VVAREXPRESS, VVARNAME)
values (0, null, null, '@@@@', 'C0020430000000000005', null, null, '2006-07-03 17:30:52', null, 'C0020430', null, null, 'vcustname', '客户名');

insert into pub_print_dataitem (DR, IDATATYPE, ITYPE, PK_CORP, PK_VARITEM, PREPARE1, RESID, TS, USERDEFFLAG, VNODECODE, VTABLECODE, VTABLENAME, VVAREXPRESS, VVARNAME)
values (0, null, null, '@@@@', 'C0020430000000000006', null, null, '2006-07-03 17:30:52', null, 'C0020430', null, null, 'nusernum', '使用数量');

insert into pub_print_template (BDIRECTOR, BDISPAGENUM, BDISTOTALPAGENUM, BILLSPACE, BNORMALCOLOR, CTEMPLATEID, DR, EXTENDATTR, FFONTSTYLE, FPAGINATION, IBOTMARGIN, IBREAKPOSITION, IFONTSIZE, IGRIDCOLOR, ILEFTMARGIN, IPAGEHEIGHT, IPAGELOCATE, IPAGEWIDTH, IRIGHTMARGIN, ISCALE, ITOPMARGIN, MODEL_TYPE, MODELHEIGHT, MODELWIDTH, PK_CORP, PREPARE1, PREPARE2, TS, VDEFAULTPRINTER, VFONTNAME, VLEFTNOTE, VMIDNOTE, VNODECODE, VRIGHTNOTE, VTEMPLATECODE, VTEMPLATENAME)
values ('Y', 'N', 'N', null, 'N', '0001ZF1000000001O3HR', 0, '<nc.vo.pub.print.PrintTemplateExtVO>
  <isBindUp>false</isBindUp>
  <zdline__position>0.0</zdline__position>
  <pagehead__position>0.0</pagehead__position>
  <pagetail__position>0.0</pagetail__position>
  <pagenumber__position>0.0</pagenumber__position>
  <m__withFullPageNumber>false</m__withFullPageNumber>
  <baseLineWeight>0.65</baseLineWeight>
  <initPageNo>1</initPageNo>
</nc.vo.pub.print.PrintTemplateExtVO>', 0, 0, 20, 0, 9, -4144960, 20, 842, '21', 596, 20, 100, 20, null, null, null, '0001', '000000001000', 'false', '2013-09-06 08:56:20', null, 'SimSun', null, null, 'C0020430', null, 'C0020430', '混料通知单');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '000000000000', '0001ZF1000000001O91F', '1000000', '0110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 54, -16777216, -16777216, 0, 0, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', '稀土原料混料通知单', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '000000000001', '0001ZF1000000001O91Y', '0', '0110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 54, -16777216, -16777216, 0, 0, -16777216, 160, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', '稀土原料混料通知单', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '000001000001', '0001ZF1000000001O91G', '1000000', '0110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '1000', 0, -1, -16777216, -16777216, 12, 54, -16777216, -16777216, 80, 0, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', '稀土原料混料通知单', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '001000001000', '0001ZF1000000001O91H', '0', '1110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 54, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', '主仓库', 'vmainware');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '001001001001', '0001ZF1000000001O91I', '0', '0110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '1000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 54, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', null, null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '002000002000', '0001ZF1000000001O91J', '0', '1210', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 74, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', '主物料', 'vmaininvl');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '002001002001', '0001ZF1000000001O91K', '0', '0010', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '1100', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 74, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '）原料，按以下比列进行混料。', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '003000003000', '0001ZF1000000001O91L', '1003000', '0110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 94, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '批号', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '003000004000', '0001ZF1000000001O91X', '0', '0110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 40, -16777216, -16777216, 0, 94, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '批号', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '003001003001', '0001ZF1000000001O91M', '0', '1110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '1000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 94, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '批次号', 'vbatchno');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '004000004000', '0001ZF1000000001O91N', '1003000', '0110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 114, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '批号', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '004001004001', '0001ZF1000000001O91O', '0', '1110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '1000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 114, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '客户名', 'vcustname');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '005000005000', '0001ZF1000000001O91P', '0', '0110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 134, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '比例', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '005001005001', '0001ZF1000000001O91Q', '0', '1110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '1000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 134, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '使用数量', 'nusernum');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '006000006000', '0001ZF1000000001O91R', '0', '0110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 154, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '混后批号：', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '006001006001', '0001ZF1000000001O91S', '0', '0110', '10', '00', '11111111', '0001ZF1000000001O3HR', 0, 0, '1000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 154, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', null, null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '007000007000', '0001ZF1000000001O91T', '0', '0110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 174, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', '注：', null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '007001007001', '0001ZF1000000001O91U', '0', '4210', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '1100', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 174, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '用户', 'User');

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '008000008000', '0001ZF1000000001O91V', '0', '0110', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '0000', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 0, 194, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', 'SimSun', null, null);

insert into pub_print_cell (BUNDERLINE, CCELLCODE, CCELLID, CCOMBINEPROPERTY, CCONTENTPROPERTY, CISFROZEN, CISHEADTAIL, CLINEPROPERTY, CTEMPLATEID, DR, FFONTSTYLE, FIXEDSTYLE, FSHADOWTYPE, IBGCOLOR, IBOTLINECOLOR, IFGCOLOR, IFONRSIZE, IHEIGHT, ILEFTLINECOLOR, IRIGHTCOLOR, ISTARTX, ISTARTY, ITOPLINECOLOR, IWIDTH, OPTIONS, PREPARE1, PREPARE2, TABLE_CODE, TS, VFONTNAME, VTEXT, VVAR)
values ('0', '008001008001', '0001ZF1000000001O91W', '0', '4210', '10', '00', '00000000', '0001ZF1000000001O3HR', 0, 0, '1100', 0, -1, -16777216, -16777216, 12, 20, -16777216, -16777216, 80, 194, -16777216, 80, '<nc.vo.pub.print.PrintCellExtVO>
  <height__decimal>0.0</height__decimal>
  <textDirection>0</textDirection>
  <lineSpaceBetween>0</lineSpaceBetween>
  <formatStr>General</formatStr>
</nc.vo.pub.print.PrintCellExtVO>', null, null, null, '2013-09-06 08:56:20', '宋体', '日期', 'DATE');

--默认模板
insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0010115', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7E', null, 'Y', '1003ZF1000000001SXYR', 1, '2013-08-28 12:05:31');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0010116', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7F', null, 'Y', '1003ZF1000000001SXYZ', 1, '2013-08-28 12:05:44');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0010117', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7G', null, 'Y', '1003ZF1000000001SXZ6', 1, '2013-08-28 12:05:59');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0020410', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7H', null, 'Y', '0001ZF1000000001NVVN', 2, '2013-08-28 12:06:54');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0020410', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7I', null, 'Y', '1003ZF1000000001T11T', 1, '2013-08-28 12:06:54');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0020420', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7J', null, 'Y', '1009ZF1000000002RKVV', 2, '2013-08-28 12:07:25');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0020420', null, null, null, null, null, null, '@@@@', null, '@@@@AA10000000000G7K', null, 'Y', '1009ZF1000000002RKVN', 1, '2013-08-28 12:07:25');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0020430', null, null, null, null, null, null, '@@@@', null, '@@@@ZF10000000000HQX', null, 'Y', '1009ZF1000000002RN71', 2, '2013-09-05 13:28:25');

insert into pub_systemplate (DR, FUNNODE, ISCOMM, NODEKEY, OPERATOR, OPERATOR_TYPE, ORGTYPECODE, PK_BUSITYPE, PK_CORP, PK_ORG, PK_SYSTEMPLATE, SYSFLAG, TEMPLATEFLAG, TEMPLATEID, TEMPSTYLE, TS)
values (0, 'C0020430', null, null, null, null, null, null, '@@@@', null, '@@@@ZF10000000000HQY', null, 'Y', '0001ZF1000000001O3HR', 3, '2013-09-05 13:28:25');

--单据号规则
insert into pub_billcode_rule (BILLCODESHORTNAME, CONTROLPARA, DAY, DR, ISAUTOFILL, ISCHECK, ISHAVESHORTNAME, ISPRESERVE, LASTSN, MONTH, OBJECT1, OBJECT2, PK_BILLCODERULE, PK_BILLTYPECODE, PK_CORP, SNNUM, SNRESETFLAG, TS, YEAR)
values (null, 'N', null, 0, 'Y', 'Y', 'N', 'N', '0000', null, null, null, '0001AB1000000001NKAJ', '36LL', '0001', 7, 0, '2013-09-11 18:33:45', null);

insert into pub_billcode_rule (BILLCODESHORTNAME, CONTROLPARA, DAY, DR, ISAUTOFILL, ISCHECK, ISHAVESHORTNAME, ISPRESERVE, LASTSN, MONTH, OBJECT1, OBJECT2, PK_BILLCODERULE, PK_BILLTYPECODE, PK_CORP, SNNUM, SNRESETFLAG, TS, YEAR)
values (null, 'N', null, 0, 'Y', 'Y', 'N', 'N', '0000', null, null, null, '0001AB1000000001NKAK', '36LM', '0001', 7, 0, '2013-09-11 18:33:51', null);

insert into pub_billcode_rule (BILLCODESHORTNAME, CONTROLPARA, DAY, DR, ISAUTOFILL, ISCHECK, ISHAVESHORTNAME, ISPRESERVE, LASTSN, MONTH, OBJECT1, OBJECT2, PK_BILLCODERULE, PK_BILLTYPECODE, PK_CORP, SNNUM, SNRESETFLAG, TS, YEAR)
values (null, 'N', null, 0, 'Y', 'Y', 'N', 'N', '0000', null, null, null, '0001AB1000000001NKAL', '36ZA', '0001', 7, 0, '2013-09-11 18:33:48', null);
