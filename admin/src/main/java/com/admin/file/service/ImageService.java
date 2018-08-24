package com.admin.file.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.admin.file.vo.ImageLinkVO;
import com.admin.to.ProductTO;

public interface ImageService {
	ImageLinkVO readImageFromURL(ImageLinkVO imageLinkVO);
	void getNoFileFoundImage(HttpServletResponse response);
	void setProductImage(List<ImageLinkVO> imageLinkRsVOList, ProductTO productTO);
	boolean uploadFileToDisk(ImageLinkVO img);
}
