package com.hengyi.service;

import com.hengyi.domain.ProductlineMatchDomain;
import com.hengyi.mapper.ProductlineManageMapper;
import com.hengyi.util.QueryDtoBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductlineManageServiceImpl implements  ProductlineManageService {
    @Autowired
    private ProductlineManageMapper productlineManageMapper;

    /***
     * 获取未匹配上的生产线匹配关系
     * @return
     */
    @Override
    public Long getUnmatchedProductlineCount() {
        return productlineManageMapper.getUnmatchedProductlineCount();
    }

    /***
     * 获取已经匹配上的生产线匹配关系信息
     * @return
     */
    @Override
    public List<ProductlineMatchDomain> getUnmatchedProductline(QueryDtoBase queryDtoBase) {
        return productlineManageMapper.getUnmatchedProductline(queryDtoBase);
    }

    @Override
    public Long getMatchedProductlineCount() {
        return productlineManageMapper.getMatchedProductlineCount();
    }

    @Override
    public List<ProductlineMatchDomain> getMatchedProductline(QueryDtoBase queryDtoBase) {
        return productlineManageMapper.getMatchedProductline(queryDtoBase);
    }

    /***
     * 增加生产线匹配关系
     * @param productlineMatchDomain
     */
    @Override
    public void addMatchedProductline(ProductlineMatchDomain productlineMatchDomain) {
        //新添加生产线匹配关系
       productlineManageMapper.addMatchedProductline(productlineMatchDomain);
        //当新增生产线匹配关系成功的时候需要将为匹配生产线匹配关系表改为已经匹配上，即把状态改为1
       //productlineManageMapper.updateUnmatchedProductline(productlineMatchDomain);
    }

    /***
     * 修改生产线匹配关系
     * @param productlineMatchDomain
     */
    @Override
    public void updateMatchedProductline(ProductlineMatchDomain productlineMatchDomain) {
    productlineManageMapper.updateMatchedProductline(productlineMatchDomain);
    }

    /***
     * 删除生产线匹配关系
     * @param productlineMatchDomain
     */
    @Override
    public void deleteMatchedProductline(ProductlineMatchDomain productlineMatchDomain) {
    productlineManageMapper.deleteMatchedProductline(productlineMatchDomain);
    }
}
