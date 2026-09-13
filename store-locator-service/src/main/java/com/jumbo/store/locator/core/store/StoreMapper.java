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

public class StoreMapper {

    public static StoreDto toStoreDto(StoreEntity entity) {
        return StoreDto.builder()
                .showWarningMessage(entity.isShowWarningMessage())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .street(entity.getStreet())
                .street2(entity.getStreet2())
                .street3(entity.getStreet3())
                .addressName(entity.getAddressName())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .complexNumber(entity.getComplexNumber())
                .todayOpen(entity.getTodayOpen())
                .locationType(entity.getLocationType())
                .collectionPoint(entity.isCollectionPoint())
                .sapStoreId(entity.getSapStoreId())
                .todayClose(entity.getTodayClose())
                .build();
    }
}
