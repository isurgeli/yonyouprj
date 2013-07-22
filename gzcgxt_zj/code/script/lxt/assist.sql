select distinct qc_checkbill.ccheckbillid ，count(qc_checkbill_b1.norder) over(partition by qc_checkbill.ccheckbillid) nsamplecount
  from qc_checkbill, qc_checkbill_b1
 where qc_checkbill.ccheckbillid(+) = qc_checkbill_b1.ccheckbillid
   and (qc_checkbill_b1.csourcebilltypecode = '23' or --4970
       qc_checkbill_b1.csourcebilltypecode = '23')
   and qc_checkbill_b1.norder = 0
   and nvl(qc_checkbill_b1.dr, 0) = 0
   and nvl(qc_checkbill.dr, 0) = 0
 order by (count(qc_checkbill_b1.norder) over(partition by qc_checkbill.ccheckbillid)) 

select qc_checkbill.ccheckbillid from qc_checkbill where nvl(qc_checkbill.dr, 0) = 0

--初始化存货质检分析属性
update bd_invmandoc
   set bd_invmandoc.def3 =
       (select case gzzg_tmp_invtype.checktype
                 when 'M' then
                  '0001ZF1000000001O92R'
                 when 'AM' then
                  '0001ZF1000000001O92S'
                 when 'SP' then
                  '0001ZF1000000001O92T'
                 when 'P' then
                  '0001ZF1000000001O92U'
               end
          from bd_invbasdoc, gzzg_tmp_invtype
         where gzzg_tmp_invtype.invcode = bd_invbasdoc.invcode
           and bd_invmandoc.pk_corp = gzzg_tmp_invtype.pk_corp
           and bd_invbasdoc.pk_invbasdoc = bd_invmandoc.pk_invbasdoc)
 where exists
 (select gzzg_tmp_invtype.checktype
          from gzzg_tmp_invtype, bd_invbasdoc
         where gzzg_tmp_invtype.invcode = bd_invbasdoc.invcode
           and bd_invmandoc.pk_corp = gzzg_tmp_invtype.pk_corp
           and bd_invbasdoc.pk_invbasdoc = bd_invmandoc.pk_invbasdoc)


