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
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StoreServiceImplTest {

    private final StoreRepository storeRepository = mock(StoreRepository.class);
    private final GeoDistanceService geoDistanceService = mock(GeoDistanceService.class);
    private final StoreService storeService = new StoreServiceImpl(storeRepository, geoDistanceService);

    @Test
    void findClosestStoresReturnsStoresSortedByDistanceAndLimitedByRequest() {
        StoreEntity farthestStore = store("Amsterdam", "1000 AA", "Damrak", 52.3676, 4.9041);
        StoreEntity closestStore = store("Utrecht", "3500 AA", "Oudegracht", 52.0907, 5.1214);
        StoreEntity secondClosestStore = store("Rotterdam", "3000 AA", "Coolsingel", 51.9244, 4.4777);
        StoreRequest request = StoreRequest.builder()
                .latitude(52.0)
                .longitude(5.0)
                .numberOfStores(2)
                .build();

        when(storeRepository.findAll()).thenReturn(List.of(farthestStore, closestStore, secondClosestStore));
        when(geoDistanceService.calculateDistanceInKm(
                new GeoCoordinates(52.0, 5.0),
                new GeoCoordinates(52.3676, 4.9041)
        )).thenReturn(30.0);
        when(geoDistanceService.calculateDistanceInKm(
                new GeoCoordinates(52.0, 5.0),
                new GeoCoordinates(52.0907, 5.1214)
        )).thenReturn(10.0);
        when(geoDistanceService.calculateDistanceInKm(
                new GeoCoordinates(52.0, 5.0),
                new GeoCoordinates(51.9244, 4.4777)
        )).thenReturn(20.0);

        StoreResponse response = storeService.findClosestStores(request);

        assertEquals(2, response.getStores().size());
        assertEquals("Utrecht", response.getStores().get(0).getCity());
        assertEquals("Rotterdam", response.getStores().get(1).getCity());
        assertEquals("3500 AA", response.getStores().get(0).getPostalCode());
        assertEquals("Coolsingel", response.getStores().get(1).getStreet());
        verify(storeRepository).findAll();
    }

    @Test
    void findClosestStoresThrowsNoStoresFoundExceptionWhenRepositoryIsEmpty() {
        StoreRequest request = StoreRequest.builder()
                .latitude(52.0)
                .longitude(5.0)
                .numberOfStores(2)
                .build();
        when(storeRepository.findAll()).thenReturn(List.of());

        assertThrows(NoStoresFoundException.class, () -> storeService.findClosestStores(request));

        verify(storeRepository).findAll();
        verify(geoDistanceService, never()).calculateDistanceInKm(
                any(),
                any()
        );
    }

    @Test
    void findClosestStoresReturnsAllAvailableStoresWhenRequestedMoreThanTotal() {
        StoreEntity store1 = store("Utrecht", "3500 AA", "Oudegracht", 52.0907, 5.1214);
        StoreRequest request = StoreRequest.builder()
                .latitude(52.0)
                .longitude(5.0)
                .numberOfStores(10)
                .build();

        when(storeRepository.findAll()).thenReturn(List.of(store1));
        when(geoDistanceService.calculateDistanceInKm(any(), any())).thenReturn(10.0);

        StoreResponse response = storeService.findClosestStores(request);

        assertEquals(1, response.getStores().size());
        assertEquals("Utrecht", response.getStores().get(0).getCity());
        verify(storeRepository).findAll();
    }

    private static StoreEntity store(String city, String postalCode, String street, double latitude, double longitude) {
        StoreEntity store = new StoreEntity();
        store.setCity(city);
        store.setPostalCode(postalCode);
        store.setStreet(street);
        store.setLatitude(latitude);
        store.setLongitude(longitude);
        store.setComplexNumber("1");
        return store;
    }
}
