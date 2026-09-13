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

import com.jumbo.store.locator.common.BaseErrorResponse;
import com.jumbo.store.locator.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * API contract for store lookup operations.
 * <p>
 * This interface defines the REST contract for retrieving the closest Jumbo stores
 * based on geographical coordinates. Implementations of this interface expose the
 * endpoint and delegate business logic to the underlying {@code StoreService}.
 * </p>
 */
@Tag(
        name = "Stores",
        description = "Operations for retrieving Jumbo store information based on geolocation."
)
public interface StoreApi {

     /**
      * Finds the closest stores to the location specified in the request.
      *
      * @param request the input containing latitude, longitude, and maximum number of stores to return
      * @return a response entity containing the closest stores wrapped in {@link BaseResponse}
      */
     @Operation(
             summary = "Find closest stores",
             description = "Returns the nearest Jumbo stores based on the provided latitude and longitude.",
             requestBody = @RequestBody(
                     required = true,
                     description = "Coordinates and maximum number of stores to return",
                     content = @Content(
                             schema = @Schema(implementation = StoreRequest.class),
                             mediaType = MediaType.APPLICATION_JSON_VALUE
                     )
             ),
             responses = {
                     @ApiResponse(
                             responseCode = "200",
                             description = "Closest stores retrieved successfully"
                     ),
                     @ApiResponse(
                             responseCode = "400",
                             description = "Invalid request payload",
                             content = @Content(
                                     schema = @Schema(implementation = BaseErrorResponse.class),
                                     mediaType = MediaType.APPLICATION_JSON_VALUE
                             )
                     ),
                     @ApiResponse(
                             responseCode = "404",
                             description = "Store not found",
                             content = @Content(
                                     schema = @Schema(implementation = BaseErrorResponse.class),
                                     mediaType = MediaType.APPLICATION_JSON_VALUE
                             )
                     ),
                     @ApiResponse(
                             responseCode = "500",
                             description = "Unexpected server error",
                             content = @Content(
                                     schema = @Schema(implementation = BaseErrorResponse.class),
                                     mediaType = MediaType.APPLICATION_JSON_VALUE
                             )
                     )
             }
     )
     ResponseEntity<BaseResponse<StoreResponse>> findClosestStores(StoreRequest request);
}
