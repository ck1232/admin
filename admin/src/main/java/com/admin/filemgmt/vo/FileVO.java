package com.admin.filemgmt.vo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileVO {
	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	private File file;
	private String fileName;
	private Date lastModified;
	private String lastModifiedString;
	private int hashCode;
	
	public String getFileName() {
		return fileName;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public String getLastModifiedString() {
		return lastModifiedString;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = new Date(lastModified);
		this.lastModifiedString = df.format(this.lastModified);
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((lastModified == null) ? 0 : lastModified.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileVO other = (FileVO) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		return true;
	}
}
