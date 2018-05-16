import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from '../component/home/home.component';
import {BudgetComponent} from '../component/total-amount/budget/budget.component';
import {ResultComponent} from '../component/total-amount/result/result.component';
import {UnitBudgetComponent} from '../component/unit-cost/budget/budget.component';
import {UnitOverviewComponent} from '../component/unit-cost/overview/overview.component';
import {UnitResultComponent} from '../component/unit-cost/result/one-company-all-product/result.component';
import {AllCompanyComponent} from '../component/unit-cost/result/all-company/all-company.component';
import {NewlyIncreasedComponent} from '../component/unit-cost/newly-increased/newly-increased.component';
import {ProductlineManageComponent} from '../component/dictionary/productline-manage/productline-manage.component';
import {BudgetManageComponent} from '../component/unit-cost/budget/budget-manage/budget-manage.component';
import {CompareProductComponent} from '../component/unit-cost/compare-product/compare-product.component';
import {ErrorComponent} from '../component/error/error.component';
import {MaterialManageComponent} from '../component/dictionary/material-manage/material-manage.component';
const appRoutes: Routes = [
  { path: 'Home', component: HomeComponent}, // 跳转到主页面的路由
  { path: 'TotalAmountBudget', component: BudgetComponent, data: {PageName: '总金额预算成本和实际成本详情'}}, // 跳转到总金额预算界面
  { path: 'TotalAmountResult', component: ResultComponent, data: {PageName: '总金额预算成本和实际成本对比分析结果'}}, // 跳转到总金额对比分析界面
  { path: 'UnitCostBudget', component: UnitBudgetComponent, data: {PageName: '单位预算成本和实际成本详情'}}, // 跳转到单价预算界面
  { path: 'UnitCostOverview', component: UnitOverviewComponent, data: {PageName: '单位预算成本和实际成本概览页面'}},
  { path: 'UnitCostResult', component: AllCompanyComponent, data: {PageName: '单位预算成本和实际成本对比分析结果'}}, // 跳转到单价对比分析界面
  { path: 'OneCompany', component: UnitResultComponent}, // 跳转到单个公司的页面
  { path: 'NewlyIncreased', component: NewlyIncreasedComponent}, // 跳转到新增规格页面
  { path: 'ProductLineMange', component: ProductlineManageComponent}, // 跳转到生产线匹配关系页面
  { path: 'budgetManage', component: BudgetManageComponent}, // 跳转到预算数据维护页面
  { path: 'CompareProduct', component: CompareProductComponent}, // 跳转到产量查询页面
  { path: 'MaterialManage', component: MaterialManageComponent}, // 跳转到查询物料管理的页面
  { path: '', component: HomeComponent}, // 默认跳转页面
  { path: '**', component: ErrorComponent} // 如果都找不到其他的页面就会跳转到错误页面
];
@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {useHash: true}
      // { enableTracing: true } // 只在调试时使用
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
