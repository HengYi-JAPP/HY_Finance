package com.hengyi.service;

import com.hengyi.domain.ProductlineMatchDomain;
import com.hengyi.util.QueryDtoBase;

import java.util.List;

public interface ProductlineManageService {
    /***
     * 获取未匹配到生产线记录数，从而便于进行分页
     * @return
     */
    Long getUnmatchedProductlineCount();
    /***
     * 获取未匹配到的生产线信息
     * @return
     */
    List<ProductlineMatchDomain> getUnmatchedProductline(QueryDtoBase queryDtoBase);

    /***
     * 获取已经匹配上的生产线匹配关系记录数
     */
    Long getMatchedProductlineCount();

    /***
     * 获取已经有的匹配关系
     * @return
     */
    List<ProductlineMatchDomain> getMatchedProductline(QueryDtoBase queryDtoBase);

    /***
     * 添加生产线匹配关系
     * @param productlineMatchDomain
     */
    void addMatchedProductline(ProductlineMatchDomain productlineMatchDomain);

    /***
     * 修改生产线匹配关系
     * @param productlineMatchDomain
     */
    void updateMatchedProductline(ProductlineMatchDomain productlineMatchDomain);

    /***
     * 删除匹配关系
     * @param productlineMatchDomain
     */
    void deleteMatchedProductline(ProductlineMatchDomain productlineMatchDomain);
}

