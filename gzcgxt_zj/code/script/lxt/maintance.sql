--������
drop table GZCG_BD_SAMPLEDOC; 
create table GZCG_BD_SAMPLEDOC
(
  burgent      CHAR(1),
  ddate        CHAR(10),
  dr           NUMBER(10) default 0,
  pk_corp      CHAR(20),
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

drop table QC_CGHZBG_B;
drop table QC_CGHZBG_H;
create table QC_CGHZBG_H
(
  approve      CHAR(20),
  approvedate  CHAR(10),
  approvenote  VARCHAR2(50),
  bill_no      VARCHAR2(50),
  billstatus   NUMBER(38),
  billtype     CHAR(20),
  dr           NUMBER(10) default 0,
  modifydate   CHAR(10),
  modifyid     CHAR(20),
  operatordate CHAR(10),
  operatorid   CHAR(20),
  pk_cghzbg_h  CHAR(20) not null,
  pk_corp      CHAR(20),
  ts           CHAR(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')
)
;
alter table QC_CGHZBG_H
  add constraint PK_QC_CGHZBG_H primary key (PK_CGHZBG_H);

create table QC_CGHZBG_B
(
  cghzbg_array CHAR(20),
  dr           NUMBER(10) default 0,
  jcpici       VARCHAR2(50),
  pk_cghzbg_b  CHAR(20) not null,
  ts           CHAR(19) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  ypname       CHAR(20),
  zdy1         VARCHAR2(50),
  zdy2         VARCHAR2(50),
  zdy3         VARCHAR2(50),
  zdy4         VARCHAR2(50),
  zdy5         VARCHAR2(50),
  zdy6         VARCHAR2(50),
  zdy7         VARCHAR2(50)
)
;
alter table QC_CGHZBG_B
  add constraint PK_QC_CGHZBG_B primary key (PK_CGHZBG_B);
alter table QC_CGHZBG_B
  add constraint FK_CGHZBG_B_ARRAY899328 foreign key (CGHZBG_ARRAY)
  references QC_CGHZBG_H (PK_CGHZBG_H);


--�����ʼ쵥����������ͼ
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

--���ܽڵ�ע��
insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NS0N', 'nc.ui.gzcg.report.CustomerAnalysisUI', 'C0020410', null, null, 0, 'C0020410', null, 4, '��Ӧ����������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6L', 'nc.ui.gzcg.report.MaterialAnalysisUI', 'C0020411', null, null, 0, 'C0020411', null, 4, '��ԭ����������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6M', 'nc.ui.gzcg.report.AssistMaterialAnalysisUI', 'C0020412', null, null, 0, 'C0020412', null, 4, '������������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6N', 'nc.ui.gzcg.report.SemiProductAnalysisUI', 'C0020413', null, null, 0, 'C0020413', null, 4, '���Ʒ��������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NY6O', 'nc.ui.gzcg.report.ProductAnalysisUI', 'C0020414', null, null, 0, 'C0020414', null, 4, '��Ʒ��������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYC', 'nc.ui.gzcg.report.MaterialStatisticsUI', 'C0020420', null, null, 0, 'C0020420', null, 4, '��ԭ�ϼ�������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYD', 'nc.ui.gzcg.report.AssistMaterialStatisticsUI', 'C0020421', null, null, 0, 'C0020421', null, 4, '���ϼ�������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYE', 'nc.ui.gzcg.report.SemiProductStatisticsUI', 'C0020422', null, null, 0, 'C0020422', null, 4, '���Ʒ��������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NYYF', 'nc.ui.gzcg.report.ProductStatisticsUI', 'C0020423', null, null, 0, 'C0020423', null, 4, '��Ʒ��������', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001O1BC', 'nc.ui.gzcg.mixturetool.MixtureToolUI', 'C0020430', null, null, 0, 'C0020430', null, 4, '���ϼ��㹤��', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0020400000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NIR0', 'nc.ui.gzcg.bd.MaterialSampleUI', 'C0010115', null, null, 0, 'C0010115', null, 4, 'ԭ����ȡ��', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0010100000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NOX8', 'nc.ui.gzcg.bd.SemiProductSampleUI', 'C0010116', null, null, 0, 'C0010116', null, 4, '�п�ȡ��', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0010100000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

insert into sm_funcregister (CFUNID, CLASS_NAME, DISP_CODE, DR, DS_NAME, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, GROUP_FLAG, HELP_NAME, ISBUTTONPOWER, ISHASPARA, ISNEEDBUTTONLOG, MODULETYPE, ORGTYPECODE, PARENT_ID, PK_CORP, RESID, SHORTCUTCODE, SUBSYSTEM_ID, SYSTEM_FLAG, TS, KEYFUNC)
values ('0001ZF1000000001NOX9', 'nc.ui.gzcg.bd.ProductSampleUI', 'C0010117', null, null, 0, 'C0010117', null, 4, '��Ʒȡ��', 0, 4, null, 'N', 'N', 'N', 'N', '1', 'C0010100000000000000', null, null, null, 'C0000000000000000000', 1, '2013-07-16 08:37:17', null);

--����ע��
insert into sm_butnregister (CFUNID, CLASS_NAME, DR, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, ORGTYPECODE, PARENT_ID, RESID, TS)
values ('0001ZF1000000001O919', '��������', 0, 0, 'C002010190', null, 5, '��������', 2, '1', 'C0020101000000000000', null, '2013-07-22 14:13:27');

insert into sm_butnregister (CFUNID, CLASS_NAME, DR, FORBID_FLAG, FUN_CODE, FUN_DESC, FUN_LEVEL, FUN_NAME, FUN_PROPERTY, ORGTYPECODE, PARENT_ID, RESID, TS)
values ('0001ZF1000000001O91A', '�������', 0, 0, 'C002010191', null, 5, '�������', 2, '1', 'C0020101000000000000', null, '2013-07-22 14:13:48');

--��Ʒ��������ģ��
insert into pub_billtemplet (BILL_TEMPLETCAPTION, BILL_TEMPLETNAME, DR, MODEL_TYPE, NODECODE, OPTIONS, PK_BILLTEMPLET, PK_BILLTYPECODE, PK_CORP, RESID, SHAREFLAG, TS, VALIDATEFORMULA, METADATACLASS)
values ('��Ʒ����', 'SYSTEM', 0, null, null, null, '0001ZF1000000001NL2C', 'C0010115', '@@@@', null, 'N', '2013-07-25 14:57:49', null, 'sample_bill.sampledoc');

insert into pub_billtemplet_t (BASETAB, DR, MIXINDEX, PK_BILLTEMPLET, PK_BILLTEMPLET_T, POS, POSITION, RESID, TABCODE, TABINDEX, TABNAME, TS, METADATACLASS, METADATAPATH, VDEF1, VDEF2, VDEF3)
values (null, 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001OCUB', 1, 1, null, 'sampledoc', 0, '��Ʒ����', '2013-07-25 14:57:49', 'sample_bill.sampledoc', null, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 5, '�������', null, 0, 1, null, -1, 'pk_invmandoc', 20, 'vinvmancode', 0, 'N', 1, 'Y', null, 0, 'N', 1, null, '0001ZF1000000001NL2C', '1003ZF1000000001SUVM', '@@@@', 1, '�������', null, null, 'N', 1, 7, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vsamplenofar', 0, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001OCUA', '@@@@', 1, null, null, null, 'N', 1, 5, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vsamplenofar', 'sample_bill.sampledoc.vsamplenofar', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef10', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2Q', null, 1, null, null, null, 'N', 0, 24, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef10', 'sample_bill.sampledoc.vdef10', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'ddate', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2R', null, 1, null, null, null, 'N', 0, 12, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'ddate', 'sample_bill.sampledoc.ddate', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef9', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2S', null, 1, null, null, null, 'N', 0, 23, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef9', 'sample_bill.sampledoc.vdef9', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'pk_corp', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2T', null, 1, null, null, null, 'N', 0, 2, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_corp', 'sample_bill.sampledoc.pk_corp', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef8', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2U', null, 1, null, null, null, 'N', 0, 22, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef8', 'sample_bill.sampledoc.vdef8', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vstockbatch', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2V', null, 1, null, null, null, 'N', 1, 10, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vstockbatch', 'sample_bill.sampledoc.vstockbatch', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vorderbill', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2X', null, 1, null, null, null, 'N', 1, 9, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vorderbill', 'sample_bill.sampledoc.vorderbill', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef4', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2Y', null, 1, null, null, null, 'N', 0, 18, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef4', 'sample_bill.sampledoc.vdef4', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'burgent', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2Z', null, 1, null, null, null, 'N', 1, 11, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'burgent', 'sample_bill.sampledoc.burgent', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, 'pk_invbas', null, 0, 0, null, -1, null, 20, 'pk_invbas', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL31', null, 1, null, null, null, 'N', 0, 26, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '�������', null, 0, 0, null, -1, null, 20, 'vinvmanname', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2W', null, 1, null, null, null, 'N', 1, 8, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, '���PK', null, 0, 0, 'pk_invbas->getColValue(bd_invmandoc, pk_invbasdoc, pk_invmandoc, pk_invmandoc);vinvmanname->getColValue(bd_invbasdoc, invname, pk_invbasdoc, pk_invbas);', -1, null, -1, 'pk_invmandoc', null, 'N', 1, 'N', 'pk_invbas->getColValue(bd_invmandoc, pk_invbasdoc, pk_invmandoc, pk_invmandoc);vinvmanname->getColValue(bd_invbasdoc, invname, pk_invbasdoc, pk_invbas);vinvmancode->getColValue(bd_invbasdoc, invcode, pk_invbasdoc, pk_invbas);', 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2G', null, 1, null, null, null, 'N', 0, 6, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_invmandoc', 'sample_bill.sampledoc.pk_invmandoc', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vqctype', null, 'N', 1, 'Y', null, 0, 'N', 1, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2D', null, 1, null, null, null, 'N', 1, 3, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vqctype', 'sample_bill.sampledoc.vqctype', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef7', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2E', null, 1, null, null, null, 'N', 0, 21, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef7', 'sample_bill.sampledoc.vdef7', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vmemo', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2F', null, 1, null, null, null, 'N', 1, 14, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vmemo', 'sample_bill.sampledoc.vmemo', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef6', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2H', null, 1, null, null, null, 'N', 0, 20, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef6', 'sample_bill.sampledoc.vdef6', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'ts', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2I', null, 1, null, null, null, 'N', 0, 25, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'ts', 'sample_bill.sampledoc.ts', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vsampleno', null, 'N', 1, 'Y', null, 0, 'N', 1, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2J', null, 1, null, null, null, 'N', 1, 4, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vsampleno', 'sample_bill.sampledoc.vsampleno', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef1', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2K', null, 1, null, null, null, 'N', 0, 15, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef1', 'sample_bill.sampledoc.vdef1', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'pk_sample', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2L', null, 1, null, null, null, 'N', 0, 1, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_sample', 'sample_bill.sampledoc.pk_sample', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'pk_user', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2M', null, 1, null, null, null, 'N', 0, 13, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'pk_user', 'sample_bill.sampledoc.pk_user', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef5', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2N', null, 1, null, null, null, 'N', 0, 19, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef5', 'sample_bill.sampledoc.vdef5', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef2', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2O', null, 1, null, null, null, 'N', 0, 16, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef2', 'sample_bill.sampledoc.vdef2', null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, -1, null, null, 0, 1, null, -1, null, -1, 'vdef3', null, 'N', 1, 'N', null, 0, 'N', 0, null, '0001ZF1000000001NL2C', '0001ZF1000000001NL2P', null, 1, null, null, null, 'N', 0, 17, 'sampledoc', '��Ʒ����', 0, '2013-07-25 14:57:49', 'N', null, null, null, 1, 1, 'N', 1, null, 100, 'vdef3', 'sample_bill.sampledoc.vdef3', null);

--��Ʒ������ѯģ��
insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001SXYR', 'C0010115', 'ԭ����ȡ����ѯ', 'C0010115', '@@@@', null, '2013-07-10 11:18:24', '0f33b437-d2a3-48df-8169-2fa8b6dc47e2');

insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001SXYZ', 'C0010116', '�п�ȡ����ѯ', 'C0010116', '@@@@', null, '2013-07-10 11:20:08', '0f33b437-d2a3-48df-8169-2fa8b6dc47e2');

insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001SXZ6', 'C0010117', '��Ʒȡ����ѯ', 'C0010117', '@@@@', null, '2013-07-10 11:20:33', '0f33b437-d2a3-48df-8169-2fa8b6dc47e2');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 0, 0, null, 0, 'vorderbill', '�ɹ�������', '1003ZF1000000001SXYS', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 4, 4, 0, null, 0, 'burgent', '�Ƿ�Ӽ�', '1003ZF1000000001SXYW', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 6, 0, null, 0, 'vmemo', '��ע', '1003ZF1000000001SXYY', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 1, 0, null, 0, 'vsampleno', '��������', '1003ZF1000000001SXYT', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 2, 0, null, 0, 'ddate', '����', '1003ZF1000000001SXYU', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'between@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('����Ա', 5, 6, 0, null, 0, 'pk_user', '¼��Ա', '1003ZF1000000001SYQI', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 11:18:24', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('�������', 5, 3, 0, null, 0, 'pk_invmandoc', '���', '1003ZF1000000001SXYV', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYR', null, 2, null, null, '2013-07-10 10:54:38', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('����Ա', 5, 5, 0, null, 0, 'pk_user', '¼��Ա', '1003ZF1000000001SYQJ', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 11:20:08', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 4, 0, 0, null, 0, 'burgent', '�Ƿ�Ӽ�', '1003ZF1000000001SXZ0', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('�������', 5, 1, 0, null, 0, 'pk_invmandoc', '�������', '1003ZF1000000001SXZ1', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 2, 0, null, 0, 'ddate', '����', '1003ZF1000000001SXZ2', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'between@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 4, 0, null, 0, 'vsampleno', '��������', '1003ZF1000000001SXZ4', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 5, 0, null, 0, 'vmemo', '��ע', '1003ZF1000000001SXZ5', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXYZ', null, 2, null, null, '2013-07-10 10:55:37', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 0, 0, null, 0, 'ddate', '����', '1003ZF1000000001SXZ7', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'between@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('�������', 5, 2, 0, null, 0, 'pk_invmandoc', '�������', '1003ZF1000000001SXZ9', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@between@like@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 3, 0, null, 0, 'vmemo', '��ע', '1003ZF1000000001SXZA', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 4, 4, 0, null, 0, 'burgent', '�Ƿ�Ӽ�', '1003ZF1000000001SXZB', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 5, 0, null, 0, 'vsampleno', '��������', '1003ZF1000000001SXZC', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, 'like@=@>@>=@<@<=@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001SXZ6', null, 2, null, null, '2013-07-10 10:56:22', null, null, null, null, null, 'N');

--�ʼ챨�����ϡ��ʼ쵥���� ����ģ��
insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C002041000', '��Ӧ����������', 'C0020410', '@@@@', '0001ZF1000000001NVVN', null, null, null, '2013-07-11 13:14:45');

insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C00201010100', '�ʼ�����', 'C002010101', '@@@@', '0001ZF1000000001ODM5', null, null, null, '2013-07-25 15:55:50');

insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C002042000', '��ԭ�ϼ�������', 'C0020420', '@@@@', '1009ZF1000000002RKVV', null, null, null, '2013-07-13 18:46:09');

insert into pub_report_templet (DESCRIBE, DR, NODE_CODE, NODE_NAME, PARENT_CODE, PK_CORP, PK_TEMPLET, PRINTINFO, RESID, SUBHEAD, TS)
values (null, 0, 'C002043000', '���ϼ��㹤��', 'C0020430', '@@@@', '1009ZF1000000002RN71', null, null, null, '2013-07-15 10:30:09');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustcode', '��Ӧ�̱���', 0, '��Ӧ�̱���', 80, 0, 0, null, 'Y', 'N', 'N', null, 1, 'C0020410', null, null, '@@@@', '0001ZF1000000001NVVO', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustname', '��Ӧ������', 0, '��Ӧ������', 80, 0, 0, null, 'Y', 'N', 'N', null, 2, 'C0020410', null, null, '@@@@', '0001ZF1000000001NVVP', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvclcode', '����������', 0, '����������', 80, 0, 0, null, 'Y', 'N', 'N', null, 3, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TR', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvclname', '�����������', 0, '�����������', 80, 0, 0, null, 'Y', 'N', 'N', null, 4, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TS', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdoccode', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 5, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TT', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdocname', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 6, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TU', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvspec', '���', 0, '���', 80, 0, 0, null, 'Y', 'N', 'N', null, 7, 'C0020410', null, null, '@@@@', '1009ZF1000000002RZJM', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vmonth', '�¶�', 0, '�¶�', 80, 0, 0, null, 'Y', 'N', 'N', null, 8, 'C0020410', null, null, '@@@@', '1003ZF1000000001T2LK', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninamount', '��������', 0, '��������', 80, 2, 0, null, 'Y', 'N', 'N', null, 9, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TV', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nincount', '���ϴ���', 0, '���ϴ���', 80, 1, 0, null, 'Y', 'N', 'N', null, 10, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TW', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nsamplecount', 'ȡ������', 0, 'ȡ������', 80, 1, 0, null, 'Y', 'N', 'N', null, 11, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TX', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninpassamount', '���Ϻϸ�����', 0, '���Ϻϸ�����', 80, 2, 0, null, 'Y', 'N', 'N', null, 12, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U4', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ninpasscount', '���Ϻϸ����', 0, '���Ϻϸ����', 80, 1, 0, null, 'Y', 'N', 'N', null, 13, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TY', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'namountpassratio', '�����ϸ���', 0, '�����ϸ���', 80, 2, 0, null, 'Y', 'N', 'N', null, 14, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1TZ', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ncountpassratio', '�����ϸ���', 0, '�����ϸ���', 80, 2, 0, null, 'Y', 'N', 'N', null, 15, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U0', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'namountorder', '�����ϸ�����', 0, '�����ϸ�����', 80, 1, 0, null, 'Y', 'N', 'N', null, 16, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U1', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'ncountorder', '�����ϸ�����', 0, '�����ϸ�����', 80, 1, 0, null, 'Y', 'N', 'N', null, 17, 'C0020410', null, null, '@@@@', '1003ZF1000000001T1U2', '0001ZF1000000001NVVN', 1, null, null, 1, '2013-07-26 10:59:41', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vsampleno', '�ʼ�����', 0, '�ʼ�����', 80, 0, 0, null, 'Y', 'N', 'N', null, 1, 'C002010101', null, null, '@@@@', '0001ZF1000000001ODM6', '0001ZF1000000001ODM5', 1, null, null, 1, '2013-07-25 15:55:50', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vorderbillcode', '�ɹ�����', 0, '�ɹ�����', 80, 0, 0, null, 'Y', 'N', 'N', null, 1, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVW', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vstockbatch', '��⣨����������', 0, '��⣨����������', 80, 0, 0, null, 'Y', 'N', 'N', null, 2, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVX', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustcode', '��Ӧ�̱���', 0, '��Ӧ�̱���', 80, 0, 0, null, 'Y', 'N', 'N', null, 3, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVY', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vcustname', '��Ӧ������', 0, '��Ӧ������', 80, 0, 0, null, 'Y', 'N', 'N', null, 4, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKVZ', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdoccode', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 5, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW0', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdocname', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 6, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW1', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'dcheck', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 7, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW2', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vsamplecode', '��������', 0, '��������', 80, 0, 0, null, 'Y', 'N', 'N', null, 8, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW3', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vchargepsn', '����Ա', 0, '����Ա', 80, 0, 0, null, 'Y', 'N', 'N', null, 9, 'C0020420', null, null, '@@@@', '1009ZF1000000002RKW4', '1009ZF1000000002RKVV', 1, null, null, 1, '2013-07-14 14:17:24', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'bselect', 'ѡ��', 0, 'ѡ��', 80, 4, 0, null, 'Y', 'N', 'N', null, 1, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN7A', '1009ZF1000000002RN71', 1, null, null, 1, '2013-07-15 11:57:09', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vinvdocname', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 2, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN72', '1009ZF1000000002RN71', 1, null, null, 1, '2013-07-15 11:57:09', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vstock', '�ֿ�', 0, '�ֿ�', 80, 0, 0, null, 'Y', 'N', 'N', null, 3, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN7B', '1009ZF1000000002RN71', 1, null, null, 1, '2013-07-15 11:57:09', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'vbatchcode', '�������', 0, '�������', 80, 0, 0, null, 'Y', 'N', 'N', null, 4, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN73', '1009ZF1000000002RN71', 1, null, null, 1, '2013-07-15 11:57:09', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nstocknum', '�������', 0, '�������', 80, 2, 0, null, 'Y', 'N', 'N', null, 5, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN74', '1009ZF1000000002RN71', 1, null, null, 1, '2013-07-15 11:57:09', null, null, 'N');

insert into pub_report_model (COL_EXPRESSIONS, COLUMN_CODE, COLUMN_SYSTEM, COLUMN_TYPE, COLUMN_USER, COLUMN_WIDTH, DATA_TYPE, DR, GROUP_ORDER, IF_DEFAULT, IF_MUST, IF_NO, IF_SUM, ITEM_ORDER, NODE_CODE, ORDER_ORDER, ORDER_TYPE, PK_CORP, PK_MODEL, PK_TEMPLET, REPORT_POS, RESID_SYSTEM, RESID_USER, SELECT_TYPE, TS, USERDEFFLAG, IDCOLNAME, IS_THMARK)
values (null, 'nusenum', 'ʹ������', 0, 'ʹ������', 80, 2, 0, null, 'Y', 'N', 'N', null, 6, 'C0020430', null, null, '@@@@', '1009ZF1000000002RN75', '1009ZF1000000002RN71', 1, null, null, 1, '2013-07-15 11:57:09', null, null, 'N');

--�ʼ챨���ѯģ��
insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1003ZF1000000001T11T', 'C0020410', '��Ӧ����������', 'C0020410', '@@@@', null, '2013-07-12 09:02:58', null);

insert into pub_query_templet (DESCRIBE, DR, FIXCONDITION, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, RESID, TS, METACLASS)
values (null, null, null, '1009ZF1000000002RKVN', 'C0020420', '��ԭ�ϼ�������', 'C0020420', '@@@@', null, '2013-07-14 08:13:17', null);

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('��Ӧ�̵���', 5, 0, 0, null, null, 'gzcg_qcrp_checkbill_v.cvendormangid', '��Ӧ��', '1003ZF1000000001T11U', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('�������', 5, 1, 0, null, null, 'gzcg_qcrp_checkbill_v.pk_invcl', '�������', '1003ZF1000000001T11V', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('�������', 5, 2, 0, null, null, 'gzcg_qcrp_checkbill_v.cmangid', '���', '1003ZF1000000001T11W', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 3, 0, null, null, 'gzcg_qcrp_checkbill_v.daccountdate', '���ʱ��', '1003ZF1000000001T11X', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 4, 0, null, null, 'gzcg_qcrp_checkbill_v.dpraydate', 'ȡ��ʱ��', '1003ZF1000000001T11Y', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 5, 0, null, null, 'gzcg_qcrp_checkbill_v.dreportdate', '����ʱ��', '1003ZF1000000001T11Z', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1003ZF1000000001T11T', null, 2, null, null, '2013-07-12 09:02:58', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('��Ӧ�̵���', 5, 0, 0, null, null, 'gzcg_qcrp_checkbill_v.cvendormangid', '��Ӧ��', '1009ZF1000000002RKVO', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-13 17:36:06', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('SX, =null,�ϸ�=Y,���ϸ�=N', 6, 1, 0, null, null, 'gzcg_qcrp_checkbill_v.bqualified', '�Ƿ�ϸ�', '1009ZF1000000002RKVP', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-14 08:13:17', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 2, 0, null, null, 'gzcg_qcrp_checkbill_v.daccountdate', '���ʱ��', '1009ZF1000000002RKVQ', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-13 17:36:06', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 3, 0, null, null, 'gzcg_qcrp_checkbill_v.dpraydate', 'ȡ��ʱ��', '1009ZF1000000002RKVR', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-13 17:36:06', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 3, 4, 0, null, null, 'gzcg_qcrp_checkbill_v.dreportdate', '����ʱ��', '1009ZF1000000002RKVS', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-13 17:36:06', null, null, null, null, null, 'N');

insert into pub_query_condition (CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, DISP_VALUE, DR, FIELD_CODE, FIELD_NAME, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_DESC, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, ISCONDITION, MAX_LENGTH, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RESID, RETURN_TYPE, TABLE_CODE, TABLE_NAME, TS, USERDEFFLAG, VALUE, GUIDELINE, INSTRUMENTSQL, PRERESTRICT, ADDISNULL4POWER)
values ('-99', 0, 5, 0, null, 0, 'qc_checkbill_b2.vsamplecode', '��������', '1009ZF1000000002RKVT', 'Y', 'N', 'Y', null, 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', null, '=@>@>=@<@<=@like@', '����@����@���ڵ���@С��@С�ڵ���@����@', 0, '@@@@', '1009ZF1000000002RKVN', null, 2, null, null, '2013-07-13 17:28:49', null, null, null, null, null, 'N');

--����������Զ�����
insert into bd_defdoclist (DOCISLEVFLAG, DOCLISTCODE, DOCLISTNAME, DOCLISTSYSTYPE, DR, ISCORPCANCHG, PK_DEFDOCLIST, TS)
values ('N', 'CG901', '�ʼ�������', 2, 0, 'N', '0001ZF1000000001O92G', '2013-07-22 16:32:16');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('001', '��ԭ��', 1, 0, '0001', '0001ZF1000000001O92R', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '��ԭ��');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('002', '������', 1, 0, '0001', '0001ZF1000000001O92S', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '������');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('003', '���Ʒ', 1, 0, '0001', '0001ZF1000000001O92T', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '���Ʒ');

insert into bd_defdoc (DOCCODE, DOCNAME, DOCSYSTYPE, DR, PK_CORP, PK_DEFDOC, PK_DEFDOC1, PK_DEFDOCLIST, SEALFLAG, TS, MEMO, SHORTNAME)
values ('004', '��Ʒ', 1, 0, '0001', '0001ZF1000000001O92U', null, '0001ZF1000000001O92G', 'N', '2013-07-22 16:33:54', null, '��Ʒ');

insert into bd_defdef (DEFCODE, DEFNAME, DIGITNUM, DR, LENGTHNUM, PK_BDINFO, PK_DEFDEF, PK_DEFDOCLIST, TS, TYPE)
values ('CG901', '�ʼ�������', null, 0, 100, '0001ZF1000000001O92H', '0001ZF1000000001O92W', '0001ZF1000000001O92G', '2013-07-22 16:34:34', 'ͳ��');

update bd_defquote set PK_DEFDEF='0001ZF1000000001O92W' where FIELDNAME='def3' and PK_CORP is null and PK_DEFUSED='sm000000000000000005';
--insert into bd_defquote (DR, FIELDNAME, PK_CORP, PK_DEFDEF, PK_DEFQUOTE, PK_DEFUSED, TS)
--values (0, 'def3', null, '0001ZF1000000001O92W', '00015734403916289929', 'sm000000000000000005', '2013-07-22 16:37:29');
--���ϵ���ģ��
insert into pub_billtemplet (BILL_TEMPLETCAPTION, BILL_TEMPLETNAME, DR, MODEL_TYPE, NODECODE, OPTIONS, PK_BILLTEMPLET, PK_BILLTYPECODE, PK_CORP, RESID, SHAREFLAG, TS, VALIDATEFORMULA, METADATACLASS)
values ('����', 'SYSTEM', 0, null, 'C0020430', null, '0001ZF1000000001O1BJ', 'C0020430', '@@@@', null, 'N', '2013-07-15 11:21:34', null, null);

insert into pub_billtemplet_t (BASETAB, DR, MIXINDEX, PK_BILLTEMPLET, PK_BILLTEMPLET_T, POS, POSITION, RESID, TABCODE, TABINDEX, TABNAME, TS, METADATACLASS, METADATAPATH, VDEF1, VDEF2, VDEF3)
values (null, 0, null, '0001ZF1000000001O1BJ', '1009ZF1000000002RN78', 1, 1, null, 'table', 0, '����', '2013-07-15 11:21:34', null, null, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, 'ccheckitemid', null, 0, 1, null, -1, null, 20, 'ccheckitemid', 0, 'N', 1, 'N', 'vcheckitem->getColValue(qc_checkitem, ccheckitemname, ccheckitemid, ccheckitemid);', 0, 'N', 0, null, '0001ZF1000000001O1BJ', '1009ZF1000000002RN77', '@@@@', 1, null, null, null, 'N', 0, 6, 'table', '����', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '���ϱ�׼', null, 0, 1, null, -1, null, 20, 'vrequirevalue', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BK', null, 1, null, null, null, 'N', 1, 4, 'table', '����', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 60, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 4, 'ѡ��', null, 0, 1, null, -1, null, 20, 'bselect', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BL', null, 1, null, null, null, 'N', 1, 1, 'table', '����', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 20, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 2, '���Ͻ��', null, 0, 0, null, -1, null, 20, 'vcomputevalue', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BM', null, 1, null, null, null, 'N', 1, 5, 'table', '����', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 60, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '������Ŀ', null, 0, 0, null, -1, null, 20, 'vcheckitem', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BN', null, 1, null, null, null, 'N', 1, 2, 'table', '����', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 100, null, null, null);

insert into pub_billtemplet_b (CARDFLAG, DATATYPE, DEFAULTSHOWNAME, DEFAULTVALUE, DR, EDITFLAG, EDITFORMULA, FOREGROUND, IDCOLNAME, INPUTLENGTH, ITEMKEY, ITEMTYPE, LEAFFLAG, LISTFLAG, LISTSHOWFLAG, LOADFORMULA, LOCKFLAG, NEWLINEFLAG, NULLFLAG, OPTIONS, PK_BILLTEMPLET, PK_BILLTEMPLET_B, PK_CORP, POS, REFTYPE, RESID, RESID_TABNAME, REVISEFLAG, SHOWFLAG, SHOWORDER, TABLE_CODE, TABLE_NAME, TOTALFLAG, TS, USERDEFFLAG, USERDEFINE1, USERDEFINE2, USERDEFINE3, USEREDITFLAG, USERFLAG, USERREVISEFLAG, USERSHOWFLAG, VALIDATEFORMULA, WIDTH, METADATAPATH, METADATAPROPERTY, METADATARELATION)
values (1, 0, '��ҵ��׼', null, 0, 0, null, -1, null, 20, 'vstandardvalue', null, 'N', 1, 'Y', null, 0, 'N', 0, null, '0001ZF1000000001O1BJ', '0001ZF1000000001O1BO', null, 1, null, null, null, 'N', 1, 3, 'table', '����', 0, '2013-07-15 11:21:34', 'N', null, null, null, 1, 1, 'N', 1, null, 60, null, null, null);

