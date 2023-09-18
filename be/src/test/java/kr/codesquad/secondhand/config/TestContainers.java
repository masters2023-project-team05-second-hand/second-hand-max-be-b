package kr.codesquad.secondhand.config;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

public class TestContainers {

    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.34")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static final GenericContainer REDIS_CACHE_CONTAINER = new GenericContainer("redis:7.2.1")
            .withExposedPorts(6379);

    static {
        MY_SQL_CONTAINER.start();
        REDIS_CACHE_CONTAINER.start();

        // yml 작성 시, indent 주의
        System.setProperty("redis.host", REDIS_CACHE_CONTAINER.getHost());
        System.setProperty("redis.port", REDIS_CACHE_CONTAINER.getMappedPort(6379).toString());
    }
}
