/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.spi.connector;

import io.airlift.units.Duration;
import io.prestosql.spi.security.ConnectorIdentity;
import io.prestosql.spi.type.TimeZoneKey;

import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;

public interface ConnectorSession
{
    String getQueryId();

    Optional<String> getSource();

    default String getUser()
    {
        return getIdentity().getUser();
    }

    ConnectorIdentity getIdentity();

    TimeZoneKey getTimeZoneKey();

    Locale getLocale();

    Optional<String> getTraceToken();

    long getStartTime();

    boolean isLegacyTimestamp();

    <T> T getProperty(String name, Class<T> type);

    //for cbg supporting the hive view we need the catalog
    default Optional<String> getCatalog()
    {
        return Optional.empty();
    }

    //for hive supporting ACID tables.
    default OptionalInt getTaskId()
    {
        return OptionalInt.empty();
    }

    //for hive supporting ACID tables.
    default OptionalInt getDriverId()
    {
        return OptionalInt.empty();
    }

    default int getTaskWriterCount()
    {
        return 1;
    }

    default Duration getDynamicFilteringWaitTime()
    {
        return new Duration(0, TimeUnit.MILLISECONDS);
    }

    default boolean isHeuristicIndexFilterEnabled()
    {
        return true;
    }
}
