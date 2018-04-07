package com.admin.file.vo;

import java.io.Serializable;

import com.admin.helper.GeneralUtils;
import com.admin.helper.vo.BaseVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageLinkVO extends BaseVO implements Serializable {
	private Long imageLinkRsId;

    private String refType;

    private Long refId;

    private Long imageLinkId;
    
    private String imagePath;

    private Integer sequence;
    
    private String contentType;
    
    //non db fields
    private long size;
    
    private String fileName;
    
    private byte[] bytes;
    
    private boolean removeInd;
    
    private String displayPath;
    
    private static final long serialVersionUID = 1L;

    public Long getImageLinkRsId() {
        return imageLinkRsId;
    }

    public void setImageLinkRsId(Long imageLinkRsId) {
        this.imageLinkRsId = imageLinkRsId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType == null ? null : refType.trim();
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public Long getImageLinkId() {
        return imageLinkId;
    }

    public void setImageLinkId(Long imageLinkId) {
        this.imageLinkId = imageLinkId;
    }
    
    public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public boolean isRemoveInd() {
		return removeInd;
	}

	public void setRemoveInd(boolean removeInd) {
		this.removeInd = removeInd;
	}

	public String getDisplayPath() {
		if(imagePath == null){
			displayPath = "/JJ/development/images/No-image-found.jpg";
    	}else{
    		displayPath = GeneralUtils.IMAGE_PATH + imagePath;
    	}
		return displayPath;
	}

	public void setDisplayPath(String displayPath) {
		this.displayPath = displayPath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", imageLinkRsId=").append(imageLinkRsId);
        sb.append(", refType=").append(refType);
        sb.append(", refId=").append(refId);
        sb.append(", imageLinkId=").append(imageLinkId);
        sb.append(", imagePath=").append(imagePath);
        sb.append(", sequence=").append(sequence);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}
