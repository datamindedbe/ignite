/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.examples.datagrid.store.auto;

import org.apache.ignite.*;
import org.apache.ignite.cache.store.jdbc.*;
import org.apache.ignite.configuration.*;
import org.apache.ignite.examples.*;
import org.apache.ignite.examples.datagrid.store.*;
import org.apache.ignite.transactions.*;

import java.util.*;

/**
 * Example of {@link CacheJdbcPojoStore} implementation that uses JDBC
 * transaction with cache transactions and maps {@link Long} to {@link Person}.
 * <p>
 * To run this example your should start {@link H2Startup} first.
 * <p>
 * Remote nodes should always be started with special configuration file which
 * enables P2P class loading: {@code 'ignite.{sh|bat} examples/config/example-ignite.xml'}.
 * <p>
 * Alternatively you can run {@link ExampleNodeStartup} in another JVM which will
 * start node with {@code examples/config/example-ignite.xml} configuration.
 */
public class CacheAutoStoreExample {
    /** Global person ID to use across entire example. */
    private static final Long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());

    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If example execution failed.
     */
    public static void main(String[] args) throws IgniteException {
        // To start ignite with desired configuration uncomment the appropriate line.
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            System.out.println();
            System.out.println(">>> Cache auto store example started.");

            CacheConfiguration<Long, Person> cacheCfg = CacheConfig.jdbcPojoStoreCache();

            try (IgniteCache<Long, Person> cache = ignite.createCache(cacheCfg)) {
                try (Transaction tx = ignite.transactions().txStart()) {
                    Person val = cache.get(id);

                    System.out.println("Read value: " + val);

                    val = cache.getAndPut(id, new Person(id, "Isaac", "Newton"));

                    System.out.println("Overwrote old value: " + val);

                    val = cache.get(id);

                    System.out.println("Read value: " + val);

                    tx.commit();
                }

                System.out.println("Read value after commit: " + cache.get(id));
            }
        }
    }
}
