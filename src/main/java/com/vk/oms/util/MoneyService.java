package com.vk.oms.util;

import com.vk.oms.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public final class MoneyService {

    public static final BigDecimal feePercentage = BigDecimal.valueOf(0.05);

    private MoneyService() {
    }

    @Transactional
    public static void transferMoney(User from, User to, BigDecimal money) {
        BigDecimal payment = money.multiply(feePercentage).add(money); // money + 5% money

        if (from.getMoney().compareTo(payment) == -1) {
            throw new IllegalStateException(
                    String.format("Insufficient funds in the account. You have: %s. You need: %s",
                            from.getMoney(), money));
        }


        from.setMoney(from.getMoney().subtract(payment));   // у заказчика вычтут цену заказа + 5% комиссии
        to.setMoney(to.getMoney().add(money));              // исполнителю начислят только цену заказа
    }
}
