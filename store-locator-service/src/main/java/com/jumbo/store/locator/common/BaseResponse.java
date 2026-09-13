/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (c) 2026 Jumbo Supermarkten B.V.
 * All rights reserved.
 *
 * This source code is part of the Jumbo Store Locator demo application.
 * Unauthorized copying or distribution is prohibited.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 */

package com.jumbo.store.locator.common;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * A generic wrapper for REST API responses within the Jumbo Store Locator Service.
 *
 * <p>This class provides a consistent structure for successful responses returned
 * by controllers and services. It includes a success flag,a human‑readable message,
 * an optional payload, and an automatically generated timestamp indicating when
 * the response was created.</p>
 *
 *
 * @param <T> The type of the response payload.
 */

@Value
@Builder
public class BaseResponse<T> {

    boolean success;
    String message;
    T data;

    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
}