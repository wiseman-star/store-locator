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

import com.jumbo.store.locator.common.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling store lookup operations.
 *
 * <p>
 * This controller exposes endpoints that allow clients to retrieve the closest Jumbo stores
 * based on geographical coordinates. It delegates business logic to {@link StoreService}
 * and returns results wrapped in a standardized {@link BaseResponse}.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/stores")
public class StoreController implements StoreApi {

    private final StoreService storeService;

    @Override
    @PostMapping("/closest")
    public ResponseEntity<BaseResponse<StoreResponse>> findClosestStores(
            @RequestBody @Valid final StoreRequest request
    ) {
        log.info("Received request to find {} nearest stores to this position. Latitude: {}, Longitude: {}",
                request.getNumberOfStores(),
                request.getLatitude(),
                request.getLongitude()
        );

        StoreResponse closestStores = storeService.findClosestStores(request);

        return ResponseEntity.ok(BaseResponse.<StoreResponse>builder()
                .message("Stores found successfully")
                .data(closestStores)
                .success(true)
                .build());
    }

}
