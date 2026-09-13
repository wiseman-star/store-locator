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

/**
 * Application service responsible for store lookup operations within the Jumbo Store Locator domain.
 * <p>
 * This service defines the contract for retrieving store information based on geospatial queries,
 * such as finding the closest stores to a given location. Implementations of this interface
 * encapsulate the orchestration between domain logic (distance calculation), persistence,
 * and DTO mapping.
 * </p>
 *
 * <p>
 * The primary use case supported by this service is determining the nearest Jumbo stores
 * relative to a user-provided coordinate, constrained by a maximum number of results.
 * </p>
 */
public interface StoreService {

    /**
     * Finds the closest stores to the location specified in the request.
     *
     * <p>
     * Implementations typically:
     * <ul>
     *     <li>convert the request coordinates into domain value objects</li>
     *     <li>retrieve store entities from the database</li>
     *     <li>calculate distances using domain geospatial logic</li>
     *     <li>sort and limit results</li>
     *     <li>map entities to DTOs for API consumption</li>
     * </ul>
     * </p>
     *
     * @param request the input containing latitude, longitude, and maximum number of stores to return
     * @return a response object containing the closest stores in DTO form
     */
    StoreResponse findClosestStores(StoreRequest request);
}
