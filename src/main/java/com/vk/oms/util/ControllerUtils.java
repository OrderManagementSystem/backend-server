package com.vk.oms.util;

import com.vk.oms.controller.exceptions.ResourceNotFoundException;

public final class ControllerUtils {

    private ControllerUtils() {
    }

    public static void assertFound(Object object) {
        if (object == null) {
            throw new ResourceNotFoundException();
        }
    }
}
