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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();

        List<Customer> customers = Arrays.asList(
                new Customer("Jon Snow", "pass", BigDecimal.valueOf(15_000)),
                new Customer("Daenerys Targaryen", "pass", BigDecimal.valueOf(17_000)),
                new Customer("Catelyn Stark", "pass", BigDecimal.valueOf(20_000)),
                new Customer("Cersei Lannister", "pass", BigDecimal.valueOf(1_000_000)),
                new Customer("Meera Reed", "pass", BigDecimal.valueOf(500_000)),
                new Customer("Tycho Nestoris", "pass", BigDecimal.valueOf(1_000_000)));

        List<Performer> performers = Arrays.asList(
                new Performer("Brienne of Tarth", "pass", BigDecimal.valueOf(500)),
                new Performer("Tyrion Lannister", "pass", BigDecimal.valueOf(500)),
                new Performer("Sansa Stark", "pass", BigDecimal.valueOf(500)));

        List<Order> orders = Arrays.asList(
                new Order(customers.get(2), "Protect Sansa and Arya Stark", BigDecimal.valueOf(1000)),
                new Order(customers.get(1), "Serve the true queen", BigDecimal.valueOf(100.25)),
                new Order(customers.get(0), "Rule the Winterfell", BigDecimal.valueOf(100)),
                new Order(customers.get(3), "Kill the dragons", BigDecimal.valueOf(1_000)),
                new Order(customers.get(0), "Protect the Westeros from the Night King", BigDecimal.valueOf(500)),
                new Order(customers.get(4), "Hold the door", BigDecimal.valueOf(500)),
                new Order(customers.get(1), "Kill the Cersei", BigDecimal.valueOf(500)),
                new Order(customers.get(5), "Pay off a debt to the Iron Bank of Braavos", BigDecimal.valueOf(500_000)),
                new Order(customers.get(1), "Recover from the Greyscale", BigDecimal.valueOf(200)),
                new Order(customers.get(1), "Recover from the Greyscale", BigDecimal.valueOf(200)),
                new Order(customers.get(1), "Serve the true queen", BigDecimal.valueOf(100.25)),
                new Order(customers.get(0), "Become the Meister", BigDecimal.valueOf(100)));

        orders.get(0).takeOrder(performers.get(0));
        orders.get(1).takeOrder(performers.get(1));
        orders.get(2).takeOrder(performers.get(2));


        List<Order> testOrders = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            testOrders.add(new Order(customers.get(random.nextInt(customers.size())),
                    "Description for test order " + (i + 1),
                    BigDecimal.valueOf(random.nextInt(10) + 1)));
        }
        for (int i = 0; i < 128; i++) {
            testOrders.get(i).takeOrder(performers.get(random.nextInt(performers.size())));
        }
        for (int i = 0; i < 64; i++) {
            testOrders.get(i).markAsReady();
        }
        for (int i = 0; i < 32; i++) {
            testOrders.get(i).accept();
        }

        customerRepository.save(customers);
        performerRepository.save(performers);
        orderRepository.save(testOrders);
        orderRepository.save(orders);
    }
}
