package com.admin.product.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.ProductTagsDAO;
import com.admin.helper.GeneralUtils;
import com.admin.product.vo.ProductTagsVO;
import com.admin.to.ProductTO;
import com.admin.to.ProductTagsTO;
@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ProductTagsService {
	private ProductTagsDAO productTagsDAO;
	
	@Autowired
	public ProductTagsService(ProductTagsDAO productTagsDAO) {
		this.productTagsDAO = productTagsDAO;
	}
	
	public void setProductTagsTO(List<ProductTagsVO> productTagsVOList, ProductTO newProductTO){
		List<ProductTagsTO> toList = convertToProductTagsTO(productTagsVOList);
		toList = setExisitingProductTags(toList);
		newProductTO.setProductTagsTOList(toList);
	}
	
	private List<ProductTagsTO> setExisitingProductTags(List<ProductTagsTO> toList){
		//chek if product tags exists
		List<ProductTagsTO> newList = new ArrayList<ProductTagsTO>();
		if(toList != null && !toList.isEmpty()){
			//get all active tags
			List<ProductTagsTO> existTOList = productTagsDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
			Map<String, ProductTagsTO> toMap = GeneralUtils.convertListToStringMap(existTOList, "name");
			
			for(ProductTagsTO to : toList){
				ProductTagsTO dbTO = toMap.get(to.getName());
				ProductTagsTO productTagsTO = to;
				if(dbTO != null){
					productTagsTO = dbTO;
				}
				newList.add(productTagsTO);
			}
		}
		return newList;
	}

	private static List<ProductTagsTO> convertToProductTagsTO(List<ProductTagsVO> newTagsList) {
		List<ProductTagsTO> toList = new ArrayList<ProductTagsTO>();
		if(newTagsList != null && !newTagsList.isEmpty()){
			for(ProductTagsVO vo : newTagsList){
				ProductTagsTO to = new ProductTagsTO();
				to.setName(vo.getName());
				to.setTagsId(vo.getTagsId());
				to.setDeleteInd(GeneralUtils.NOT_DELETED);
				toList.add(to);
			}
		}
		return toList;
	}
	
	public static List<ProductTagsVO> convertToProductTagsVOList(List<ProductTagsTO> toList, Long productId){
		List<ProductTagsVO> voList = new ArrayList<ProductTagsVO>();
		if(toList != null && !toList.isEmpty()){
			for(ProductTagsTO to : toList){
				ProductTagsVO vo = new ProductTagsVO();
				vo.setTagsId(to.getTagsId());
				vo.setName(to.getName());
				vo.setProductId(productId);
				GeneralUtils.copyFromTO(vo, to);
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public static List<ProductTagsVO> convertToProductTagsVOList(List<String> tagsList){
		List<ProductTagsVO> productTagsVOList = new ArrayList<ProductTagsVO>();
		if(tagsList != null && !tagsList.isEmpty()){
			//remvoe duplicates
			Set<String> set = new HashSet<String>(tagsList);
			tagsList = new ArrayList<String>(set);
			for(String tagsString : tagsList){
				if(tagsString != null && !tagsString.trim().isEmpty()){
					ProductTagsVO vo = new ProductTagsVO();
					vo.setName(tagsString);
					productTagsVOList.add(vo);
				}
			}
		}
		return productTagsVOList;
	}
}
