package com.respawn.repositories;

import com.respawn.entities.GenericModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository <E extends GenericModel> extends JpaRepository<E, Long> {
}