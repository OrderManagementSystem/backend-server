package com.vk.oms.config;

import com.vk.oms.model.Customer;
import com.vk.oms.model.Order;
import com.vk.oms.model.Performer;
import com.vk.oms.repository.CustomerRepository;
import com.vk.oms.repository.OrderRepository;
import com.vk.oms.repository.PerformerRepository;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@Profile("development")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PerformerRepository performerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Bean
    ServletRegistrationBean h2ServletRegistrationBean() {
        ServletRegistrationBean registrationBean =
                new ServletRegistrationBean(new WebServlet());

        registrationBean.addUrlMappings("/h2/*");

        return registrationBean;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("Иванов И.И.", "pass", new BigDecimal("1000"));
        Performer performer = new Performer("performer", "pass", new BigDecimal("500"));

        Order order = new Order(customer, "Сделать что-то 1", new BigDecimal("123.50"));
        Order order2 = new Order(customer, "Сделать что-то 2", new BigDecimal("22"));
        Order order3 = new Order(customer, "Сделать что-то 3", new BigDecimal("45.155"));
        Order order4 = new Order(customer, "Сделать что-то 4", new BigDecimal("120"));

        order4.takeOrder(performer);

        customerRepository.save(customer);
        performerRepository.save(performer);
        orderRepository.save(Arrays.asList(order, order2, order3, order4));
    }
}
