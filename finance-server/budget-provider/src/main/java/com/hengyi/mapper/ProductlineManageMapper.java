package com.hengyi.mapper;


import com.hengyi.domain.ProductlineMatchDomain;
import com.hengyi.util.QueryDtoBase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductlineManageMapper {
    /***
     * 获取为匹配到的生产线信息的记录数
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
     * @return
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
     * 删除生产线匹配关系
     * @param productlineMatchDomain
     */
    void deleteMatchedProductline(ProductlineMatchDomain productlineMatchDomain);

    /***
     * 当成功新增生产线匹配关系之后，会把未匹配的生产线标识为已经匹配上，即把删除标志del_flag改为1
     * @param productlineMatchDomain
     */
    void updateUnmatchedProductline(ProductlineMatchDomain productlineMatchDomain);
}
