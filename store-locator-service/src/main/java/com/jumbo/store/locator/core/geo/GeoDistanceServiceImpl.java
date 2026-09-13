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

import org.springframework.stereotype.Service;

import static com.jumbo.store.locator.constant.Defaults.EARTH_RADIUS_KM;


@Service
public class GeoDistanceServiceImpl implements GeoDistanceService {

    @Override
    public double calculateDistanceInKm(GeoCoordinates pointA, GeoCoordinates pointB) {
        double latDistance = Math.toRadians(pointB.latitude() - pointA.latitude());
        double lonDistance = Math.toRadians(pointB.longitude() - pointA.longitude());

        double haversine = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pointA.latitude())) * Math.cos(Math.toRadians(pointB.latitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));

        return EARTH_RADIUS_KM * c;
    }

}
