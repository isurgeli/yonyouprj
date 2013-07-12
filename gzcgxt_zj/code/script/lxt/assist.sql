select distinct qc_checkbill.ccheckbillid £¬count(qc_checkbill_b1.norder) over(partition by qc_checkbill.ccheckbillid) nsamplecount
  from qc_checkbill, qc_checkbill_b1
 where qc_checkbill.ccheckbillid(+) = qc_checkbill_b1.ccheckbillid
   and (qc_checkbill_b1.csourcebilltypecode = '23' or --4970
       qc_checkbill_b1.csourcebilltypecode = '23')
   and qc_checkbill_b1.norder = 0
   and nvl(qc_checkbill_b1.dr, 0) = 0
   and nvl(qc_checkbill.dr, 0) = 0
 order by (count(qc_checkbill_b1.norder) over(partition by qc_checkbill.ccheckbillid)) 

select qc_checkbill.ccheckbillid from qc_checkbill where nvl(qc_checkbill.dr, 0) = 0

