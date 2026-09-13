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

import com.jumbo.store.locator.core.geo.GeoCoordinates;
import com.jumbo.store.locator.core.geo.GeoDistanceService;
import com.jumbo.store.locator.exception.NoStoresFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final GeoDistanceService geoDistanceService;

    @Override
    public StoreResponse findClosestStores(final StoreRequest request) {
        log.info("Finding closest stores to this location. Latitude: {}, Longitude: {}",
                request.getLatitude(),
                request.getLongitude()
        );

        List<StoreEntity> allStores = storeRepository.findAll(); //findAll() for demo purposes
        if (allStores.isEmpty()) {
            throw new NoStoresFoundException();
        }

        GeoCoordinates givenLocation = new GeoCoordinates(request.getLatitude(), request.getLongitude());
        List<StoreDto> closestStores = allStores.stream()
                .sorted(Comparator.comparingDouble(
                        store -> geoDistanceService.calculateDistanceInKm(
                                givenLocation,
                                new GeoCoordinates(store.getLatitude(), store.getLongitude())
                        )
                ))
                .limit(request.getNumberOfStores())
                .map(StoreMapper::toStoreDto)
                .toList();

        log.info("Found {} closest stores", closestStores.size());

        return StoreResponse.builder()
                .stores(closestStores)
                .build();
    }
}
