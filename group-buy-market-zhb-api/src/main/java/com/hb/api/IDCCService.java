package com.hb.api;

import com.hb.api.response.Response;

/**
 * 动态配置中心
 */
public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);
}
