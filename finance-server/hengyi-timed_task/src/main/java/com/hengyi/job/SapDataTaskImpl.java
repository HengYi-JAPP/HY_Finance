package com.hengyi.job;

import com.hengyi.bean.*;
import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.sapmapper.SapDataMapper;
import com.hengyi.util.DateUtil;
import com.hengyi.util.MathUtil;
import com.hengyi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author: QZM
 * @Description:基础数据同步定时任务
 * @Date: 2017/10/31 10:15
 */
@Component(value = "sapDataTaskImpl")
public class SapDataTaskImpl implements SapDataTask {
    @Autowired
    private SapDataMapper sapDataMapper;
    @Autowired
    private FinanceDataMapper financeDataMapper;
    ArrayList<SapDataBean> datalist = new ArrayList<SapDataBean>();
    ArrayList<String> companylist = new ArrayList<String>();
    ArrayList<ProductMatchBean> productmatchlist = new ArrayList<>();

    /**
     * @param
     * @return
     * @Transactional(rollbackFor = Exception.class)
     * 从SAP定期同步数据到本系统Mysql数据库,将物料描述分解成生产线、规格、产品、纱种等信息
     */
    @Override
    //@Scheduled(fixedRate = 1000*30)
    @Scheduled(cron = "00 00 03 01 * ?")
    public void getsapdata() {
        System.out.println("同步开始");
        //获得当前时间的年份与上月月份
        SapDataMonthBean sapDataMonthBean = DateUtil.getsapdatamonthbeannow();
//        sapDataMonthBean.setMonth(new BigDecimal(2));
        //查找所有公司并放入集合
        companylist = financeDataMapper.selectallcompany();
        //将生产线匹配关系放入集合
        productmatchlist = financeDataMapper.selectproductmatch();
        //集合存放物料匹配关系
        ArrayList<MaterialMatchBean> materialmatchlist = new ArrayList<MaterialMatchBean>();
        //查询物料匹配关系放入集合
        materialmatchlist = financeDataMapper.selectmaterialmatch();
        //查询价格表所有数据放入集合
        ArrayList<MaterialPriceBean> priceBeanArrayList=financeDataMapper.selectpricelist();

        //清空原有数据，重新同步
        financeDataMapper.deleteFinanceSapDatabymonth(sapDataMonthBean);
        for (String company : companylist) {
            //对每个公司进行操作
            sapDataMonthBean.setCompany(company);
            //根据公司搜索miniData 的数据
            datalist = sapDataMapper.selectsapdatabycompany(sapDataMonthBean);
            //遍历搜索结果并每次遍历插入一条数据到Mysql的FinanceSapData表
            for (SapDataBean sapDataBean : datalist) {
                String productName = "";//产品名称
                String productSpecifications = "";//规格
                String productBatchNumber = "";//批号
                String productGrade = "";//等级
                String productLine = "";//生产线
                String productYarn = "";//纱种
                String[] productMatch= new String[1]; //生产线实际匹配关系  例如C1/C2/C3/C4 存在List中为  C1 C2 C3 C4 4个元素
                //如果SAP产品物料描述不为空，则用-分割物料描述得到  产品名、规格、批号、等级、生产线、纱种
                if (sapDataBean.getSapMaterialDescribe() != null) {
                    String[] str = sapDataBean.getSapMaterialDescribe().split("-");
                    if (str.length >= 4) {
                        productName = str[0];
                        if(str[0].equals("切片")&&str[1].equals(" ")){
                            productSpecifications="半消光";
                        }
                        else{
                            productSpecifications = str[1];
                        }
                        productBatchNumber = str[2];
                        productGrade = str[3];
                        if (str.length == 6) {
                            productYarn = str[5];
                        }
                    }else{
                        //如果物料描述分割后长度小于4
                        continue;
                    }
                    //对生产线进行匹配
                    //针对    13\S*   与    13\S*B   等同为13开头的引入matched变量     根据他的长度来判断该条数据是属于哪个匹配关系的
                    String matched=null;
                    for (ProductMatchBean productMatchBean : productmatchlist) {
                        if (matched!=null&&matched.length()>productMatchBean.getProductMaterialMatch().replaceAll("×", "\\\\S*").length()){
                            continue;
                        }
                        if (productMatchBean.getProductMaterialMatch() == null || productMatchBean.getProductMaterialMatch().equals("")) {
                           continue;
                        }
                        if(productMatchBean.getProductSpecificationsMatch()==null){
                            productMatchBean.setProductSpecificationsMatch("");
                        }
                        productMatchBean.setProductMaterialMatch(productMatchBean.getProductMaterialMatch().replaceAll("×", "\\\\S*"));
                        String[] specificationsMatchArray = productMatchBean.getProductSpecificationsMatch().split("&");
                        for (int i = 0; i < specificationsMatchArray.length; i++) {
                            specificationsMatchArray[i] = specificationsMatchArray[i].replaceAll("×", "\\\\S*");
                            specificationsMatchArray[i] = specificationsMatchArray[i].replaceAll("\\*", "\\\\*");
                        }
                        if (productBatchNumber != null
                                && productBatchNumber.matches(productMatchBean.getProductMaterialMatch())) {
                            if (productMatchBean.getProductMaterialYarn() != null && !productMatchBean.getProductMaterialYarn().equals("")) {
                                productYarn = productMatchBean.getProductMaterialYarn();
                            } else {
                                for (int i = 0; i < specificationsMatchArray.length; i++) {
                                    if (productSpecifications.matches(specificationsMatchArray[i])) {
                                        productYarn = productMatchBean.getProductSpecificationsYarn();
                                        break;
                                    }
                                }
                            }
                            productMatch = productMatchBean.getProductMatch().split("/");
                            matched=productMatchBean.getProductMaterialMatch();
                        }
                    }
                      //如果属于其他包装物或者纺丝其他辅料
                    for (MaterialMatchBean materialMatchBean :materialmatchlist){
                        if (StringUtil.equals(sapDataBean.getCostMaterialId(),"00000000"+materialMatchBean.getMaterialId())
                                &&(StringUtil.equals(materialMatchBean.getMaterialName(),"其他包装物")||StringUtil.equals(materialMatchBean.getMaterialName(),"纺丝其他辅料"))){
                            BigDecimal bigDecimal=sapDataBean.getMoney();     //将其设置为值传递而非引用传递
                            sapDataBean.setCostQuantity(bigDecimal);
                        }
                    }
                    //如果属于工资、水电等费用，则将其的总耗设为他的总金额，令其单价固定为1
                    if (sapDataBean.getCostMaterialDescribe()==null&&sapDataBean.getCostId()!=null){
                        BigDecimal bigDecimal=sapDataBean.getMoney();     //将其设置为值传递而非引用传递
                        sapDataBean.setCostQuantity(bigDecimal);
                    }else{
                        //如果该成本项物料是单瓦的纸箱，则总耗除以2
                        if (sapDataBean.getCostMaterialDescribe()!=null&&sapDataBean.getCostMaterialDescribe().replaceAll(" ", "").matches("\\S*单瓦\\S*")){
                            sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getCostQuantity(),new BigDecimal(2)));
                        }
                        if (sapDataBean.getCostMaterialDescribe()!=null&&sapDataBean.getCostMaterialDescribe().replaceAll(" ", "").matches("\\S*双瓦\\S*")){
                            sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getCostQuantity(),new BigDecimal(2)));
                        }
                    }
                    //遍历单价表中的单价，如果有提供单价，根据单价与金额来计算总耗
                    for (MaterialPriceBean materialPriceBean:priceBeanArrayList){
                        if ((StringUtil.equals("00000000"+materialPriceBean.getMaterialId(),sapDataBean.getCostMaterialId())
                                ||StringUtil.equals(materialPriceBean.getCostId(),sapDataBean.getCostId()))
                                &&StringUtil.equals(sapDataBean.getCompany(),materialPriceBean.getCompany())
                                ){
                            if (materialPriceBean.getProduct() !=null) {
                               if ( StringUtil.equals(productName,materialPriceBean.getProduct())) {
                                   sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getMoney(),materialPriceBean.getPrice()));
                               }
                            }else{
                                sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getMoney(),materialPriceBean.getPrice()));
                            }
                        }
                    }

                    //根据生产线匹配关系，分摊他的产量、金额、总耗 到4条生产线
                    sapDataBean.setOrderProductQuantity(MathUtil.divide(sapDataBean.getOrderProductQuantity(),new BigDecimal(productMatch.length)));
                    sapDataBean.setMoney(MathUtil.divide(sapDataBean.getMoney(),new BigDecimal(productMatch.length)));
                    sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getCostQuantity(),new BigDecimal(productMatch.length)));
                    for (int i=0;i<productMatch.length;i++){
                        productLine=productMatch[i];
                        FinanceSapDataInsertBean financeSapDataInsertBean = new FinanceSapDataInsertBean(sapDataBean, productName, productSpecifications, productBatchNumber, productGrade, productLine, productYarn);
                    financeDataMapper.insertsapdata(financeSapDataInsertBean);
                    }
                }
            }
        }
        System.out.println("同步结束");
    }


}
