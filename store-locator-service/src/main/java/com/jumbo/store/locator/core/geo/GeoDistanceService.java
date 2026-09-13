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

/**
 * Domain service responsible for calculating geographical distances between two coordinate points.
 * <p>
 * This service uses the Haversine formula to compute the great-circle distance between two locations
 * on Earth, based on their latitude and longitude. The result is expressed in kilometers.
 * </p>
 *
 * <p>
 * As a domain service, {@code DistanceService} encapsulates geospatial logic that is part of the
 * core domain model and is used by higher-level components such as store lookup or routing logic.
 * </p>
 */
public interface GeoDistanceService {

    /**
     * Calculates the distance in kilometers between two geographical points using the Haversine formula.
     *
     * @param pointA the starting coordinate (latitude and longitude)
     * @param pointB the destination coordinate (latitude and longitude)
     * @return the distance between the two points in kilometers
     *
     * @see GeoCoordinates
     */
    double calculateDistanceInKm(GeoCoordinates pointA, GeoCoordinates pointB);
}
