/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (c) 2026 Jumbo Supermarkten B.V.
 * All rights reserved.
 *
 * This source code is part of the Jumbo Store Locator demo application.
 * Unauthorized copying or distribution is prohibited.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 */

package com.jumbo.store.locator.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

import static com.jumbo.store.locator.db.SeedStoresHelper.requireText;
import static com.jumbo.store.locator.db.SeedStoresHelper.setNullableString;

/**
 * Flyway Java migration that creates the {@code stores} table and seeds it
 * with the initial store dataset bundled at {@code stores.json} on the classpath.
 *
 * <p>This migration combines schema creation and data seeding into a single step
 * so that a fresh database (e.g. the in-memory H2 instance used in local/dev
 * profiles) is fully populated the moment the application starts.
 *
 * <p>Each {@code stores.json} entry is mapped to a row using {@link SeedHelper}
 * to distinguish required fields (which fail fast if missing) from optional
 * fields (which are inserted as {@code NULL} when absent).
 *
 * <p>Because Flyway records this migration as applied once it succeeds,
 * re-running the application against a persistent database will not
 * re-create the table or re-insert the seed data.
 *
 * @see SeedHelper
 */
public class V1__SeedStores extends BaseJavaMigration {

    /**
     * DDL statement that creates the {@code stores} table, with column
     * nullability mirroring the constraints declared on {@code StoreEntity}.
     */
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE stores (
                id                    UUID PRIMARY KEY,
                show_warning_message  BOOLEAN NOT NULL DEFAULT FALSE,
                city                  VARCHAR(255) NOT NULL,
                postal_code           VARCHAR(255) NOT NULL,
                street                VARCHAR(255) NOT NULL,
                street2               VARCHAR(255),
                street3               VARCHAR(255),
                address_name          VARCHAR(255),
                longitude             DOUBLE PRECISION NOT NULL,
                latitude              DOUBLE PRECISION NOT NULL,
                complex_number        VARCHAR(255) NOT NULL,
                today_open            VARCHAR(255),
                location_type         VARCHAR(255),
                collection_point      BOOLEAN NOT NULL DEFAULT FALSE,
                sap_store_id          VARCHAR(255),
                today_close           VARCHAR(255)
            )
            """;

    /**
     * Parameterized insert statement used to seed each store row parsed
     * from {@code stores.json}. Column order matches the positional
     * parameter indices set in {@link #migrate(Context)}.
     */
    private static final String INSERT_SQL = """
            INSERT INTO stores (
                id, show_warning_message, city, postal_code, street, street2, street3,
                address_name, longitude, latitude, complex_number, today_open,
                location_type, collection_point, sap_store_id, today_close
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    /**
     * Executes the migration: creates the {@code stores} table, then reads
     * {@code stores.json} from the classpath and batch-inserts every entry
     * found under its {@code "stores"} array.
     *
     * <p>Each store is assigned a freshly generated {@link UUID} as its
     * primary key, since {@code StoreEntity} generates IDs via
     * {@code GenerationType.UUID} on the Hibernate side rather than a
     * database sequence or identity column.
     *
     * @param context the Flyway migration context, providing the active
     *                JDBC {@link java.sql.Connection} to run against
     * @throws Exception if the table creation fails, {@code stores.json}
     *                    cannot be read or parsed, or a required field is
     *                    missing from any store entry (see
     *                    {@link SeedHelper#requireText(JsonNode, String)})
     */
    @Override
    public void migrate(Context context) throws Exception {
        try (Statement stmt = context.getConnection().createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        }

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = new ClassPathResource("stores.json").getInputStream()) {
            JsonNode stores = mapper.readTree(is).get("stores");

            try (PreparedStatement ps = context.getConnection().prepareStatement(INSERT_SQL)) {
                for (JsonNode store : stores) {
                    ps.setObject(1, UUID.randomUUID());
                    ps.setBoolean(2, store.path("showWarningMessage").asBoolean(false));
                    ps.setString(3, requireText(store, "city"));
                    ps.setString(4, requireText(store, "postalCode"));
                    ps.setString(5, requireText(store, "street"));
                    setNullableString(ps, 6, store, "street2");
                    setNullableString(ps, 7, store, "street3");
                    setNullableString(ps, 8, store, "addressName");
                    ps.setDouble(9, store.get("longitude").asDouble());
                    ps.setDouble(10, store.get("latitude").asDouble());
                    ps.setString(11, requireText(store, "complexNumber"));
                    setNullableString(ps, 12, store, "todayOpen");
                    setNullableString(ps, 13, store, "locationType");
                    ps.setBoolean(14, store.path("collectionPoint").asBoolean(false));
                    setNullableString(ps, 15, store, "sapStoreID");
                    setNullableString(ps, 16, store, "todayClose");
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (Exception e) {
                throw new RuntimeException("Failed to insert stores", e);
            }
        }
    }
}