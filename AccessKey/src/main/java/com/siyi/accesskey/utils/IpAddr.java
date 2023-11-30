package com.siyi.accesskey.utils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddr {
    private static Searcher searcher;

    public static boolean checkIp(String ipAddress) {
        String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    /**
     * 在服务启动时，将 ip2region 加载到内存中
     */
    @PostConstruct
    private static void initIp2Region() {
        try {
            InputStream inputStream = new ClassPathResource("/ipdb/ip2region.xdb").getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            searcher = Searcher.newWithBuffer(bytes);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * * 获取 ip 所属地址     *
     * * @param ip ip
     * * @return
     */
    public static Map<String,Object> getIpRegion(String ip) {
        boolean isIp = checkIp(ip);
        if (isIp) {
            initIp2Region();
            Map<String,Object> resp =new HashMap<>();
            try {

                // searchIpInfo 的数据格式：国家|区域|省份|城市|ISPxx
                String searchIpInfo = searcher.search(ip);
                String[] splitIpInfo = searchIpInfo.split("\\|");
                if (splitIpInfo.length > 0) {
                    if ("中国".equals(splitIpInfo[0])) {
                        // 国内属地返回省份
                        resp.put("国家",splitIpInfo[0]);
                        resp.put("省份",splitIpInfo[2]);
                        resp.put("城市",splitIpInfo[3]);
                        resp.put("运营商",splitIpInfo[4]);

                        return resp;
                    } else if ("0".equals(splitIpInfo[0])) {
                        if ("内网IP".equals(splitIpInfo[4])) {
                            // 内网 IP
                            resp.put("内网 IP",splitIpInfo[0]);
                            return resp;
                        } else {
                            resp.put("status","ERROR");
                            return resp;
                        }
                    } else {
                        // 国外属地返回国家
                        resp.put("国家",splitIpInfo[0]);
                        return resp;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.put("status","ERROR");
            return resp;
//            return "";
        } else {
            throw new IllegalArgumentException("非法的IP地址");
        }
    }
}