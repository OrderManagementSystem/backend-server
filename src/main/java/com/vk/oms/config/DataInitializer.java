package com.vk.oms.config;

import com.vk.oms.model.Customer;
import com.vk.oms.model.Order;
import com.vk.oms.model.Performer;
import com.vk.oms.repository.CustomerRepository;
import com.vk.oms.repository.OrderRepository;
import com.vk.oms.repository.PerformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PerformerRepository performerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("customer", "pass");
        Performer performer = new Performer("performer", "pass");
        Order order = new Order(customer, "Сделать что-то 1", new BigDecimal("123.50"));
        Order order2 = new Order(customer, "Сделать что-то 2", new BigDecimal("23"));

        customerRepository.save(customer);
        performerRepository.save(performer);
        orderRepository.save(Arrays.asList(order, order2));
    }
}
