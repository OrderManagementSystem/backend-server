package com.vk.oms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Заказ
 */
@Entity
@Table(name = "`order`")
public class Order extends BaseEntity {

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne
    private Performer performer;

    private String description;

    private BigDecimal price;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Enumerated
    @JsonProperty
    private Status status = Status.WAITING;

    public Order() {
    }

    public Order(Customer customer, String description, BigDecimal price) {
        this.customer = customer;
        this.description = description;
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Performer getPerformer() {
        return performer;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @PrePersist
    private void setCreatedDate() {
        createdDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @JsonIgnore
    public boolean isWaiting() {
        return status.equals(Status.WAITING);
    }

    @JsonIgnore
    public boolean isInProgress() {
        return status.equals(Status.IN_PROGRESS);
    }

    @JsonIgnore
    public boolean isReady() {
        return status.equals(Status.READY);
    }

    @JsonIgnore
    public boolean isCompleted() {
        return status.equals(Status.COMPLETED);
    }

    public void takeOrder(Performer performer) {
        if (!isWaiting()) {
            throw new IllegalStateException(
                    String.format("Order must be in waiting state to take it! Current state: %s", status));
        }

        this.status = Status.IN_PROGRESS;
        this.performer = performer;
    }

    public void markAsReady() {
        if (!isInProgress()) {
            throw new IllegalStateException(
                    String.format("Order must be in progress state to mark it as ready! Current state: %s", status));
        }

        this.status = Status.READY;
    }

    public void accept() {
        if (!isReady()) {
            throw new IllegalStateException(
                    String.format("Order must be ready state to pay for it! Current state: %s", status));
        }

        this.status = Status.COMPLETED;
    }

    public enum Status {

        /**
         * Заказ размещен заказчиком и ожидает, пока кто-нибудь возьмется его выполнять
         */
        WAITING,

        /**
         * Исполнитель взялся взялся за выполнение заказа
         */
        IN_PROGRESS,

        /**
         * Заказ выполнен, ожидает оплаты заказчком
         */
        READY,

        /**
         * Заказ выполнен и оплачен
         */
        COMPLETED
    }
}
