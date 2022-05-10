package org.redisson;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public abstract class BaseTest {

    protected static RedissonClient redisson;


    /**
     * 在所有测试用例运行之前
     * 1. 初始化Redis-Server实例,即开启一个Redis服务端实例
     * 2. 建立redisson客户端实例
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @BeforeAll
    public static void beforeClass() throws IOException, InterruptedException {
        RedisRunner.startDefaultRedisServerInstance();
        redisson = createInstance();
    }

    /**
     * 在所有测试用例执行完成之后
     *
     * @throws InterruptedException
     */
    @AfterAll
    public static void afterClass() throws InterruptedException {
        redisson.shutdown();
        RedisRunner.shutDownDefaultRedisServerInstance();
    }

    @BeforeEach
    public void before() throws IOException, InterruptedException {
        if (flushBetweenTests()) {
            redisson.getKeys().flushall();
        }
    }

    public static Config createConfig() {
//        String redisAddress = System.getProperty("redisAddress");
//        if (redisAddress == null) {
//            redisAddress = "127.0.0.1:6379";
//        }
        Config config = new Config();
//        config.setCodec(new MsgPackJacksonCodec());
//        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26379", "127.0.0
//        .1:26389");
//        config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001", "127.0.0.1:7000");
        config.useSingleServer().setAddress(RedisRunner.getDefaultRedisServerBindAddressAndPort());
//        .setPassword("mypass1");
//        config.useMasterSlaveConnection()
//        .setMasterAddress("127.0.0.1:6379")
//        .addSlaveAddress("127.0.0.1:6399")
//        .addSlaveAddress("127.0.0.1:6389");
        return config;
    }

    public static RedissonClient createInstance() {
        Config config = createConfig();
        return Redisson.create(config);
    }

    protected boolean flushBetweenTests() {
        return true;
    }
}
