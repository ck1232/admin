package com.admin.to;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
@Embeddable
public class ProductImageLinkRsId implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManyToOne
	private ProductTO productTO;
	@ManyToOne
	private FileLinkTO fileLinkTO;
	public ProductTO getProductTO() {
		return productTO;
	}
	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}
	public FileLinkTO getFileLinkTO() {
		return fileLinkTO;
	}
	public void setFileLinkTO(FileLinkTO fileLinkTO) {
		this.fileLinkTO = fileLinkTO;
	}
	
	

}
