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
import lombok.Value;

import java.util.List;

@Value
@Builder
public class StoreResponse {
    List<StoreDto> stores;
}
