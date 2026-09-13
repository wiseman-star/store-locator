/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (c) 2026 Jumbo Supermarkten B.V.
 * All rights reserved.
 *
 * This source code is part of the Jumbo Store Locator demo application.
 * Unauthorized copying or distribution is prohibited.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 */

package com.jumbo.store.locator.exception;

public class NoStoresFoundException extends RuntimeException {

    public NoStoresFoundException(String message, Throwable e) {
        super(message, e);
    }

    public NoStoresFoundException() {
        super("No stores found");
    }
}
