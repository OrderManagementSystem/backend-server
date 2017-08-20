package com.vk.oms.util;

import com.vk.oms.model.User;

import java.math.BigDecimal;

public final class MoneyService {

    private MoneyService() {
    }

    public static void transferMoney(User from, User to, BigDecimal money) {
        if (from.getMoney().compareTo(money) == -1) {
            throw new IllegalStateException(
                    String.format("Insufficient funds in the account. You have: %s. You need: %s",
                            from.getMoney(), money));
        }

        from.setMoney(from.getMoney().subtract(money));
        to.setMoney(to.getMoney().add(money));
    }
}
