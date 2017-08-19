package com.vk.oms.repository;

import com.vk.oms.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractUserRepository<T extends User> extends CrudRepository<T, Long> {

    T findByUsername(String username);
}
