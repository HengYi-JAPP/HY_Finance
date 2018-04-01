"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var home_component_1 = require("../component/home/home.component");
var budget_component_1 = require("../component/total-amount/budget/budget.component");
var result_component_1 = require("../component/total-amount/result/result.component");
var budget_component_2 = require("../component/unit-cost/budget/budget.component");
var overview_component_1 = require("../component/unit-cost/overview/overview.component");
var result_component_2 = require("../component/unit-cost/result/one-company-all-product/result.component");
var all_company_component_1 = require("../component/unit-cost/result/all-company/all-company.component");
var error_component_1 = require("../component/error/error.component");
var appRoutes = [
    { path: 'Home', component: home_component_1.HomeComponent },
    { path: 'TotalAmountBudget', component: budget_component_1.BudgetComponent, data: { PageName: '总金额预算成本和实际成本详情' } },
    { path: 'TotalAmountResult', component: result_component_1.ResultComponent, data: { PageName: '总金额预算成本和实际成本对比分析结果' } },
    { path: 'UnitCostBudget', component: budget_component_2.UnitBudgetComponent, data: { PageName: '单位预算成本和实际成本详情' } },
    { path: 'UnitCostOverview', component: overview_component_1.UnitOverviewComponent, data: { PageName: '单位预算成本和实际成本概览页面' } },
    { path: 'UnitCostResult', component: all_company_component_1.AllCompanyComponent, data: { PageName: '单位预算成本和实际成本对比分析结果' } },
    { path: 'OneCompany', component: result_component_2.UnitResultComponent },
    { path: '', component: home_component_1.HomeComponent },
    { path: '**', component: error_component_1.ErrorComponent } // 如果都找不到其他的页面就会跳转到错误页面
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        core_1.NgModule({
            imports: [
                router_1.RouterModule.forRoot(appRoutes, { enableTracing: true } // 只在调试时使用
                )
            ],
            exports: [
                router_1.RouterModule
            ]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());
exports.AppRoutingModule = AppRoutingModule;
