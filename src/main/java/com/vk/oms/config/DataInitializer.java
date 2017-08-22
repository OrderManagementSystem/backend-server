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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        Customer customer1 = new Customer("Jon Snow", "pass", BigDecimal.valueOf(1_000));
        Customer customer2 = new Customer("Daenerys Targaryen", "pass", BigDecimal.valueOf(1_000));
        Customer customer3 = new Customer("Catelyn Stark", "pass", BigDecimal.valueOf(1_000));
        Customer customer4 = new Customer("Cersei Lannister", "pass", BigDecimal.valueOf(1_000_000));
        Customer customer5 = new Customer("Meera Reed", "pass", BigDecimal.valueOf(500));
        Customer customer6 = new Customer("Tycho Nestoris", "pass", BigDecimal.valueOf(1_000_000));

        Performer performer1 = new Performer("Brienne of Tarth", "pass", BigDecimal.valueOf(500));
        Performer performer2 = new Performer("Tyrion Lannister", "pass", BigDecimal.valueOf(500));
        Performer performer3 = new Performer("Sansa Stark", "pass", BigDecimal.valueOf(500));

        Order order1 = new Order(customer3, "Protect Sansa and Arya Stark", BigDecimal.valueOf(1000));
        Order order2 = new Order(customer2, "Serve the true queen", BigDecimal.valueOf(100.25));
        Order order3 = new Order(customer1, "Rule the Winterfell", BigDecimal.valueOf(100));
        Order order4 = new Order(customer4, "Kill the dragons", BigDecimal.valueOf(1_000));
        Order order5 = new Order(customer1, "Protect the Westeros from the Night King", BigDecimal.valueOf(500));
        Order order6 = new Order(customer5, "Hold the door", BigDecimal.valueOf(500));
        Order order7 = new Order(customer2, "Kill the Cersei", BigDecimal.valueOf(500));
        Order order8 = new Order(customer6, "Pay off a debt to the Iron Bank of Braavos", BigDecimal.valueOf(500_000));
        Order order9 = new Order(customer2, "Recover from the Greyscale", BigDecimal.valueOf(200));
        Order order10 = new Order(customer2, "Serve the true queen", BigDecimal.valueOf(100.25));
        Order order11 = new Order(customer1, "Become the Meister", BigDecimal.valueOf(100));

        order1.takeOrder(performer1);
        order2.takeOrder(performer2);
        order3.takeOrder(performer3);

        customerRepository.save(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6));
        performerRepository.save(Arrays.asList(performer1, performer2, performer3));
        orderRepository.save(Arrays.asList(order1, order2, order3, order4, order5, order6, order7, order8, order9,
                order10, order11));

        List<Order> testOrders = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            testOrders.add(new Order(customer1, "Description for test order " + i, BigDecimal.ONE));
        }
        orderRepository.save(testOrders);
    }
}
