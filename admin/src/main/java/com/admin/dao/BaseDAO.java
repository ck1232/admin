package com.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.admin.to.BaseTO;

@NoRepositoryBean
public interface BaseDAO<T extends BaseTO> extends JpaRepository<T, Long> {
	List<T> findByDeleteInd(String deleteInd);
}
