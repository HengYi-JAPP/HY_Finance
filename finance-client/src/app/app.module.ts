import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './router/app-routing.module';
import { AppComponent } from './app.component';
import { ElModule } from 'element-angular/release/element-angular.module';
import {NgZorroAntdModule} from 'ng-zorro-antd';
import { HomeComponent} from './component/home/home.component';
import { BudgetComponent } from './component/total-amount/budget/budget.component';
import { ResultComponent } from './component/total-amount/result/result.component';
import {UnitBudgetComponent} from './component/unit-cost/budget/budget.component';
import {UnitOverviewComponent} from './component/unit-cost/overview/overview.component';
import {UnitResultComponent} from './component/unit-cost/result/one-company-all-product/result.component';
import { ErrorComponent } from './component/error/error.component';
import {SelectComponent} from './component/shared-component/select/select.component';
import {Select2Component} from './component/shared-component/select2/select2.component';
import {TableComponent} from './component/shared-component/table/table.component';

import {BudgetService} from './api/budget.service';
import {ExcelServiceService} from './api/excelService.service';
import {SapBudgetService} from './api/sapBudget.service';
import {MaterialManageService} from './api/materialManage.service';

import { AllCompanyComponent } from './component/unit-cost/result/all-company/all-company.component';
import {GetResponseService} from './api/getResponse.service';
import { NewlyIncreasedComponent } from './component/unit-cost/newly-increased/newly-increased.component';
import { ProductlineManageComponent } from './component/dictionary/productline-manage/productline-manage.component';
import { MaterialComponent } from './component/unit-cost/overview/material/material.component';
import { BudgetManageComponent } from './component/unit-cost/budget/budget-manage/budget-manage.component';
import { CompareProductComponent } from './component/unit-cost/compare-product/compare-product.component';
import { MaterialManageComponent } from './component/dictionary/material-manage/material-manage.component';
import { DialogComponent } from './component/shared-component/dialog/dialog.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BudgetComponent,
    ResultComponent,
    UnitBudgetComponent,
    UnitResultComponent,
    ErrorComponent,
    UnitOverviewComponent,
    SelectComponent,
    Select2Component,
    TableComponent,
    AllCompanyComponent,
    NewlyIncreasedComponent,
    ProductlineManageComponent,
    MaterialComponent,
    BudgetManageComponent,
    CompareProductComponent,
    MaterialManageComponent,
    DialogComponent
  ],
  imports: [
    BrowserModule, // 在浏览器上运行所需的模块
    BrowserAnimationsModule, // 动画所需模块
    FormsModule,
    ReactiveFormsModule,
    ElModule.forRoot(), // 引入element-UI
    CommonModule, // 使用*ngFor,*ngIf所需模块
    AppRoutingModule, // 引入路由模块
    HttpClientModule, // 与服务端交互所需模块
    NgZorroAntdModule.forRoot()// 由于elementUI的表格组件有bug，故引入该库
  ],
  providers: [
    BudgetService,
    ExcelServiceService,
    // OverviewService,
    // ResultService,
    GetResponseService,
    SapBudgetService,
    MaterialManageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
