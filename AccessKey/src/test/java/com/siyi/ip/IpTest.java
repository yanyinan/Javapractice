package com.siyi.ip;

import com.siyi.accesskey.utils.IpAddr;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-24  22:35
 * @Description: TODO
 * @Version: 1.0
 */

public class IpTest {

    @Test
    void test() {
        //通过方法获取本机ip


        Map<String, Object> ipRegion = IpAddr.getIpRegion("36.44.163.109");
        System.out.println(ipRegion.get("城市"));
    }
}
