/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (c) 2026 Jumbo Supermarkten B.V.
 * All rights reserved.
 *
 * This source code is part of the Jumbo Store Locator demo application.
 * Unauthorized copying or distribution is prohibited.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 */

package com.jumbo.store.locator.core.geo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoDistanceServiceImplTest {

    private final GeoDistanceService geoDistanceService = new GeoDistanceServiceImpl();

    @Test
    void calculateDistanceInKmReturnsZeroForSameCoordinates() {
        GeoCoordinates coordinates = new GeoCoordinates(52.3676, 4.9041);

        double distance = geoDistanceService.calculateDistanceInKm(coordinates, coordinates);

        assertEquals(0.0, distance, 0.000001);
    }

    @Test
    void calculateDistanceInKmReturnsExpectedDistanceBetweenAmsterdamAndRotterdam() {
        GeoCoordinates amsterdam = new GeoCoordinates(52.3676, 4.9041);
        GeoCoordinates rotterdam = new GeoCoordinates(51.9244, 4.4777);

        double distance = geoDistanceService.calculateDistanceInKm(amsterdam, rotterdam);

        assertEquals(57.0, distance, 0.5);
    }

    @Test
    void calculateDistanceInKmIsSymmetric() {
        GeoCoordinates eindhoven = new GeoCoordinates(51.4416, 5.4697);
        GeoCoordinates utrecht = new GeoCoordinates(52.0907, 5.1214);

        double distanceFromEindhoven = geoDistanceService.calculateDistanceInKm(eindhoven, utrecht);
        double distanceFromUtrecht = geoDistanceService.calculateDistanceInKm(utrecht, eindhoven);

        assertEquals(distanceFromEindhoven, distanceFromUtrecht, 0.000001);
    }

    @Test
    void calculateDistanceInKmForAntipodalPoints() {
        GeoCoordinates point1 = new GeoCoordinates(0.0, 0.0);
        GeoCoordinates point2 = new GeoCoordinates(0.0, 180.0);

        double distance = geoDistanceService.calculateDistanceInKm(point1, point2);

        // Half of earth circumference
        assertEquals(20015.0, distance, 50.0);
    }

    @Test
    void calculateDistanceInKmBetweenPoles() {
        GeoCoordinates northPole = new GeoCoordinates(90.0, 0.0);
        GeoCoordinates southPole = new GeoCoordinates(-90.0, 0.0);

        double distance = geoDistanceService.calculateDistanceInKm(northPole, southPole);

        assertEquals(20015.0, distance, 50.0);
    }
}
