package com.admin.file.utils;

import java.util.Comparator;

import com.admin.file.vo.FileMetaVO;

public class ImageSequenceCompare implements Comparator<FileMetaVO>{

	@Override
	public int compare(FileMetaVO o1, FileMetaVO o2) {
		if(o1.getSequence() != null && o2.getSequence() != null){
			return o1.getSequence().compareTo(o2.getSequence());
		}else if(o1.getSequence() != null){
			return 1;
		}else if(o2.getSequence() != null){
			return -1;
		}
		return 0;
	}

}
