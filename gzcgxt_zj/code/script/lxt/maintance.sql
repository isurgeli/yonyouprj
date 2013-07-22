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

