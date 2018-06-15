package com.admin.cheque.vo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.cheque.vo.ChequeVO;
import com.admin.dao.ChequeDAO;
import com.admin.helper.GeneralUtils;
import com.admin.to.ChequeTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ChequeConverter {
	private ChequeDAO chequeDAO;
	
	@Autowired
	public ChequeConverter(ChequeDAO chequeDAO) {
		this.chequeDAO = chequeDAO;
	}

	public List<ChequeTO> convertToChequeTOList(List<ChequeVO> voList) {
		List<ChequeTO> toList = new ArrayList<ChequeTO>();
		if(voList != null && !voList.isEmpty()){
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "chequeId", false);
			Map<Long, ChequeTO> chequeTOMap = new HashMap<Long, ChequeTO>();
			if(!idList.isEmpty()){
				List<ChequeTO> chequeTOList = chequeDAO.findByChequeIdIn(idList);
				chequeTOMap = GeneralUtils.convertListToLongMap(chequeTOList, "chequeId");
			}
			for(ChequeVO vo : voList){
				ChequeTO to = new ChequeTO();
				ChequeTO dbTO = chequeTOMap.get(vo.getChequeId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setChequeNum(vo.getChequeNum());
				to.setChequeDate(GeneralUtils.convertStringToDate(vo.getChequeDateString(), "dd/MM/yyyy"));
				to.setChequeAmt(vo.getChequeAmt());
				to.setDebitDate(vo.getDebitDate());
				to.setRemarks(vo.getRemarks());
				to.setBounceChequeInd(vo.getBounceChequeInd());
				toList.add(to);
			}
		}
		return toList;
	}
	
	public List<ChequeVO> convertToChequeVOList(List<ChequeTO> toList) {
		List<ChequeVO> voList = new ArrayList<ChequeVO>();
		if(toList != null && !toList.isEmpty()) {
			for(ChequeTO to : toList) {
				ChequeVO vo = new ChequeVO();
				vo.setBounceChequeInd(to.getBounceChequeInd());
				if(to.getBounceDate() != null) {
					vo.setBounceDate(to.getBounceDate());
					vo.setBounceDateString(GeneralUtils.convertDateToString(to.getBounceDate(), "dd/MM/yyyy"));
				}
				vo.setChequeAmt(to.getChequeAmt());
				vo.setChequeAmtString("$"+to.getChequeAmt());
				vo.setChequeDate(to.getChequeDate());
				vo.setChequeDateString(GeneralUtils.convertDateToString(to.getChequeDate(), "dd/MM/yyyy"));
				vo.setChequeId(to.getChequeId());
				vo.setChequeNum(to.getChequeNum());
				if(to.getDebitDate() != null) {
					vo.setDebitDate(to.getDebitDate());
					vo.setDebitDateString(GeneralUtils.convertDateToString(to.getDebitDate(), "dd/MM/yyyy"));
				}
				vo.setRemarks(to.getRemarks());
				voList.add(vo);
			}
		}
		return voList;
	}
}
