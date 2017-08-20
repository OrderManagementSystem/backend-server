package com.vk.oms.controller;

import com.vk.oms.controller.exceptions.BadRequestException;
import com.vk.oms.model.Customer;
import com.vk.oms.model.Order;
import com.vk.oms.model.Performer;
import com.vk.oms.repository.OrderRepository;
import com.vk.oms.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.vk.oms.util.ControllerUtils.assertFound;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Возвращает заказ с id, указанным в качестве path variable
     */
    @GetMapping("/orders/{id:\\d+}")
    public Order getOrder(@PathVariable("id") Order order) {
        assertFound(order);
        return order;
    }

    /**
     * Возвращает список новых (со статусом IN_PROGRESS) заказов, отсортированный по дате добавления, от новых к старым
     */
    @GetMapping("/orders")
    public List<Order> getOrders(@PageableDefault(sort = {"createdDate"}) Pageable pageable) {
        return orderRepository
                .findAll((root, query, cb) -> cb.equal(root.get("status"), Order.Status.WAITING), pageable)
                .getContent();
    }

    /**
     * Возвращает список всех заказов заказчика с указанным id. Метод доступен только этому заказчику.
     */
    @GetMapping("/customer/{id:\\d+}/orders")
    @PreAuthorize("hasRole('CUSTOMER') and #customer == loggedUser")
    public List<Order> getCustomerOrders(@PathVariable("id") Customer customer,
                                         @PageableDefault(sort = {"status", "createdDate"}) Pageable pageable) {
        assertFound(customer);
        return orderRepository
                .findAll((root, query, cb) -> cb.equal(root.get("customer"), customer), pageable)
                .getContent();
    }

    /**
     * Возвращает список всех заказов исполнителя с указанным id. Метод доступен только этому исполнителю.
     */
    @GetMapping("/performers/{id:\\d+}/orders")
    @PreAuthorize("hasRole('PERFORMER') and #performer == loggedUser")
    public List<Order> getPerformerOrders(@PathVariable("id") Performer performer,
                                          @PageableDefault(sort = {"status", "createdDate"}) Pageable pageable) {
        assertFound(performer);
        return orderRepository
                .findAll((root, query, cb) -> cb.equal(root.get("performer"), performer), pageable)
                .getContent();
    }

    /**
     * Создает новый заказ с указанным описание и стоимостью заказа. Метод доступен только заказчикам.
     */
    @PostMapping("/orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Order publishOrder(@RequestBody OrderDto orderDto) {
        return orderRepository.save(new Order(
                SecurityUtils.getLoggedCustomer(), orderDto.description, orderDto.price));
    }

    /**
     * Метод взятия заказа. Изменяет статус заказа с WAITING на IN_PROGRESS.
     */
    @PreAuthorize("hasRole('PERFORMER')")
    @PatchMapping("/orders/{id:\\d+}/take")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void takeOrder(@PathVariable("id") Order order) {
        assertFound(order);

        try {
            order.takeOrder(SecurityUtils.getLoggedPerformer());
        } catch (IllegalStateException e) {
            throw new BadRequestException(e);
        }

        orderRepository.save(order);
    }

    /**
     * Метод отправки заказа на проверку заказчику. Изменяет статус заказа с IN_PROGRESS на READY.
     * Метод доступен только заказчику, взявшему этот заказ.
     */
    @PatchMapping("/orders/{id:\\d+}/pass")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PERFORMER') and #order?.performer == loggedUser")
    public void pass(@PathVariable("id") Order order) {
        assertFound(order);

        try {
            order.markAsReady();
        } catch (IllegalStateException e) {
            throw new BadRequestException(e);
        }

        orderRepository.save(order);
    }

    /**
     * Помечает заказ, как выполненный, изменяя статус заказа с READY на COMPLETED. Переводит оплату за заказ со счёта
     * заказчика на счёт исполнителя.
     */
    @PatchMapping("/orders/{id:\\d+}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER') and #order?.customer == loggedUser")
    public void accept(@PathVariable("id") Order order) {
        assertFound(order);

        try {
            order.accept();
        } catch (IllegalStateException e) {
            throw new BadRequestException(e);
        }

        orderRepository.save(order);
    }

    private class OrderDto {
        public String description;
        public BigDecimal price;
    }
}
