//package com.hengyi.job;
//
//import com.hengyi.bean.FinanceSapDataInsertBean;
//import com.hengyi.bean.ProductMatchBean;
//import com.hengyi.bean.SapDataBean;
//import com.hengyi.bean.SapDataMonthBean;
//import com.hengyi.mapper.FinanceDataMapper;
//import com.hengyi.sapmapper.SapDataMapper;
//import com.hengyi.util.DateUtil;
//import com.hengyi.util.MathUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//
///**
// * @author: QZM
// * @Description:基础数据同步定时任务
// * @Date: 2017/10/31 10:15
// */
//@Component(value = "sapDataTaskImpl")
//public class SapDataTaskImpl implements SapDataTask {
//    @Autowired
//    private SapDataMapper sapDataMapper;
//    @Autowired
//    private FinanceDataMapper financeDataMapper;
//    ArrayList<SapDataBean> datalist = new ArrayList<SapDataBean>();
//    ArrayList<String> companylist = new ArrayList<String>();
//    ArrayList<ProductMatchBean> productmatchlist = new ArrayList<>();
//
//    /**
//     * @param
//     * @return
//     * @Transactional(rollbackFor = Exception.class)
//     * 从SAP定期同步数据到本系统Mysql数据库,将物料描述分解成生产线、规格、产品、纱种等信息
//     */
//    @Override
//    //@Scheduled(fixedRate = 1000*30)
//    @Scheduled(cron = "0 33 3 * * ?")
//    public void getsapdata() {
//        SapDataMonthBean sapDataMonthBean = DateUtil.getsapdatamonthbeannow();
//        financeDataMapper.deleteFinanceSapDatabymonth(sapDataMonthBean);//清空原有数据，重新同步
//        //查找公司并放入集合
//        companylist = financeDataMapper.selectallcompany();
//        productmatchlist = financeDataMapper.selectproductmatch();
//        for (String company : companylist) {
//            //对每个公司进行操作
//            sapDataMonthBean.setCompany(company);
//            datalist = sapDataMapper.selectsapdatabycompany(sapDataMonthBean);
//            for (SapDataBean sapDataBean : datalist) {
//                String productName = "";//产品
//                String productSpecifications = "";//规格
//                String productBatchNumber = "";//批号
//                String productGrade = "";//等级
//                String productLine = "";//生产线
//                String productYarn = "";//纱种
//                String[] productMatch= new String[10]; //生产线实际匹配关系  例如C1/C2/C3/C4 存在List中为  C1 C2 C3 C4 4个元素
//                if (sapDataBean.getSapMaterialDescribe() != null) {
//                    String[] str = sapDataBean.getSapMaterialDescribe().split("-");
//
//                    if (str.length >= 4) {
//                        productName = str[0];
//                        productSpecifications = str[1];
//                        productBatchNumber = str[2];
//                        productGrade = str[3];
//                        if (str.length == 6) {
//                            productYarn = str[5];
//                        }
//                    }else{
//                        continue;
//                    }
//                    for (ProductMatchBean productMatchBean : productmatchlist) {
//                        if (productMatchBean.getProductMaterialMatch() == null || productMatchBean.getProductMaterialMatch().equals("")) {
//                            break;
//                        }
//                        if(productMatchBean.getProductSpecificationsMatch()==null){
//                            productMatchBean.setProductSpecificationsMatch("");
//                        }
//                        productMatchBean.setProductMaterialMatch(productMatchBean.getProductMaterialMatch().replaceAll("×", "\\\\S*"));
//                        String[] specificationsMatchArray = productMatchBean.getProductSpecificationsMatch().split("&");
//                        for (int i = 0; i < specificationsMatchArray.length; i++) {
//                            specificationsMatchArray[i] = specificationsMatchArray[i].replaceAll("×", "\\\\S*");
//                            specificationsMatchArray[i] = specificationsMatchArray[i].replaceAll("\\*", "\\\\*");
//                        }
//                        if (productBatchNumber != null
//                                && productBatchNumber.matches(productMatchBean.getProductMaterialMatch())) {
//                            if (productMatchBean.getProductMaterialYarn() != null && !productMatchBean.getProductMaterialYarn().equals("")) {
//                                productYarn = productMatchBean.getProductMaterialYarn();
//                                break;
//                            } else {
//                                for (int i = 0; i < specificationsMatchArray.length; i++) {
//                                    if (productSpecifications.matches(specificationsMatchArray[i])) {
//                                        productYarn = productMatchBean.getProductSpecificationsYarn();
//                                        break;
//                                    }
//                                }
//                            }
//                            productMatch = productMatchBean.getProductMatch().split("/");
//                        }
//                    }
//                    if (sapDataBean.getCostMaterialDescribe()==null&&sapDataBean.getCostId()!=null){
//                        sapDataBean.setCostQuantity(sapDataBean.getMoney());
//                    }else{
//                        if (sapDataBean.getCostMaterialDescribe()!=null&&sapDataBean.getCostMaterialDescribe().matches("\\S*单瓦\\S*")){
//                            sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getCostQuantity(),new BigDecimal(2)));
//                        }
//                    }
//                    sapDataBean.setOrderProductQuantity(MathUtil.divide(sapDataBean.getOrderProductQuantity(),new BigDecimal(productMatch.length)));
//                    sapDataBean.setMoney(MathUtil.divide(sapDataBean.getMoney(),new BigDecimal(productMatch.length)));
//                    sapDataBean.setCostQuantity(MathUtil.divide(sapDataBean.getCostQuantity(),new BigDecimal(productMatch.length)));
//                    for (int i=0;i<productMatch.length;i++){
//                        productLine=productMatch[i];
//                        FinanceSapDataInsertBean financeSapDataInsertBean = new FinanceSapDataInsertBean(sapDataBean, productName, productSpecifications, productBatchNumber, productGrade, productLine, productYarn);
//                        financeDataMapper.insertsapdata(financeSapDataInsertBean);
//                    }
//                }
//            }
//        }
//
//        System.out.println("同步完成了现在时间是" + DateFormat.getTimeInstance().format(new Date()));
//    }
//
//
//}
