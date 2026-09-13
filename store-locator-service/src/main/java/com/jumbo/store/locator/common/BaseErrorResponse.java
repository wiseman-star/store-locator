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
 * Standardized error response model used across the Jumbo Store Locator API.
 * <p>
 * This class provides a consistent structure for representing error conditions
 * returned by REST endpoints. It includes a human-readable message, an optional
 * machine-readable error code, and a timestamp indicating when the error occurred.
 * </p>
 *
 * <p>
 * Typical usage includes global exception handling, validation errors, and
 * domain-specific failures. The {@code timestamp} field is automatically
 * initialized to the current time when the response is built.
 * </p>
 *
 * <p>Field descriptions:</p>
 * <ul>
 *     <li><b>message</b> – A descriptive explanation of the error.</li>
 *     <li><b>errorCode</b> – Optional code used for programmatic error handling.</li>
 *     <li><b>timestamp</b> – The moment the error response was created.</li>
 * </ul>
 */
@Value
@Builder
public class BaseErrorResponse {

    String message;
    String errorCode;

    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
}
