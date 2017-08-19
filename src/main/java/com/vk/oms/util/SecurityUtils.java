package com.vk.oms.util;

import com.vk.oms.model.Customer;
import com.vk.oms.model.Performer;
import com.vk.oms.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static User getLoggedUser() {
        User user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (User.class.isInstance(principal)) {
                user = (User) principal;
            }
        }

        return user;
    }

    public static Customer getLoggedCustomer() {
        User user = getLoggedUser();
        return Customer.class.isInstance(user) ? (Customer) getLoggedUser() : null;
    }

    public static Performer getLoggedPerformer() {
        User user = getLoggedUser();
        return Performer.class.isInstance(user) ? (Performer) getLoggedUser() : null;
    }

    public static boolean isAuthenticated() {
        return getLoggedUser() != null;
    }

    public static boolean isCustomer() {
        return getLoggedCustomer() != null;
    }

    public static boolean isPerformer() {
        return getLoggedPerformer() != null;
    }
}
