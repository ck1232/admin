package com.admin.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
@Entity
@DynamicUpdate
@Table(name = "file_link")
public class FileLinkTO extends BaseTO{
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_link_id", nullable=false)
	private Long fileLinkId;
	
	@Column(name = "file_path", nullable=false)
    private String filePath;
	
	@Column(name = "content_type", nullable=false)
	private String contentType;

	public Long getFileLinkId() {
		return fileLinkId;
	}

	public void setFileLinkId(Long fileLinkId) {
		this.fileLinkId = fileLinkId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
