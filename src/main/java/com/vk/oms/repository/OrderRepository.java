package com.vk.oms.repository;

import com.vk.oms.model.Customer;
import com.vk.oms.model.Order;
import com.vk.oms.model.Performer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findByCustomer(Customer customer, Pageable pageable);

    Page<Order> findByPerformer(Performer performer, Pageable pageable);
}
