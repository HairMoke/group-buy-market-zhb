package com.hb.infrastructure.dcc;

import com.hb.types.annotations.DCCValue;
import com.hb.types.common.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 动态配置服务
 */
@Service
public class DCCService {

    /**
     * 降级开关，0关闭，1开启
     */
    @DCCValue("downgradeSwitch:0")
    private String downgradeSwitch;

    /**
     * 切量开关(0是全部关闭，100是全部开放)
     */
    @DCCValue("cutRange:100")
    private String cutRange;

    @DCCValue("scBlacklist:s02c02")
    private String scBlacklist;

    public boolean isDowngradeSwitch() {
        return "1".equals(downgradeSwitch);
    }


    public boolean isCutRange(String userId) {
        // 计算哈希码的绝对值
        int hashCode = Math.abs(userId.hashCode());

        // 获取最后两位
        int lastTwoDigits = hashCode % 100;

        // 判断是否在切量范围内
        if(lastTwoDigits <= Integer.parseInt(cutRange)) {
            return true;
        }
        return false;
    }

    /**
     * 判断黑名单拦截渠道，true 拦截、false 放行
     */
    public boolean isSCBlackIntercept(String source, String channel) {
        List<String> list = Arrays.asList(scBlacklist.split(Constants.SPLIT));
        return list.contains(source+channel);
    }
}
