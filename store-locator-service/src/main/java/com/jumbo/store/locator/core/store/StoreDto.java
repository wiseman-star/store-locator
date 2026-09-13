/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (c) 2026 Jumbo Supermarkten B.V.
 * All rights reserved.
 *
 * This source code is part of the Jumbo Store Locator demo application.
 * Unauthorized copying or distribution is prohibited.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 */

package com.jumbo.store.locator.core.store;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object representing store information exposed through the API layer.
 * This DTO is intentionally decoupled from persistence concerns.
 */
@Data
@Builder
public class StoreDto {

    private boolean showWarningMessage;
    private String city;
    private String postalCode;
    private String street;
    private String street2;
    private String street3;
    private String addressName;
    private double longitude;
    private double latitude;
    private String complexNumber;
    private String todayOpen;
    private String locationType;
    private boolean collectionPoint;
    private String sapStoreId;
    private String todayClose;
}
