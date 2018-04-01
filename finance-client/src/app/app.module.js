"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var platform_browser_1 = require("@angular/platform-browser");
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var animations_1 = require("@angular/platform-browser/animations");
var common_1 = require("@angular/common");
var http_1 = require("@angular/common/http");
var app_routing_module_1 = require("./router/app-routing.module");
var app_component_1 = require("./app.component");
var element_angular_module_1 = require("element-angular/release/element-angular.module");
var ng_zorro_antd_1 = require("ng-zorro-antd");
var home_component_1 = require("./component/home/home.component");
var budget_component_1 = require("./component/total-amount/budget/budget.component");
var result_component_1 = require("./component/total-amount/result/result.component");
var budget_component_2 = require("./component/unit-cost/budget/budget.component");
var overview_component_1 = require("./component/unit-cost/overview/overview.component");
var result_component_2 = require("./component/unit-cost/result/one-company-all-product/result.component");
var error_component_1 = require("./component/error/error.component");
var select_component_1 = require("./component/shared-component/select/select.component");
var table_component_1 = require("./component/shared-component/table/table.component");
var budget_service_1 = require("./api/budget.service");
var overview_service_1 = require("./api/overview.service");
var result_service_1 = require("./api/result.service");
var all_company_component_1 = require("./component/unit-cost/result/all-company/all-company.component");
var getResponse_service_1 = require("./api/getResponse.service");
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            declarations: [
                app_component_1.AppComponent,
                home_component_1.HomeComponent,
                budget_component_1.BudgetComponent,
                result_component_1.ResultComponent,
                budget_component_2.UnitBudgetComponent,
                result_component_2.UnitResultComponent,
                error_component_1.ErrorComponent,
                overview_component_1.UnitOverviewComponent,
                select_component_1.SelectComponent,
                table_component_1.TableComponent,
                all_company_component_1.AllCompanyComponent
            ],
            imports: [
                platform_browser_1.BrowserModule,
                animations_1.BrowserAnimationsModule,
                forms_1.FormsModule,
                element_angular_module_1.ElModule.forRoot(),
                common_1.CommonModule,
                app_routing_module_1.AppRoutingModule,
                http_1.HttpClientModule,
                ng_zorro_antd_1.NgZorroAntdModule.forRoot() // 由于elementUI的表格组件有bug，故引入该库
            ],
            providers: [
                budget_service_1.BudgetService,
                overview_service_1.OverviewService,
                result_service_1.ResultService,
                getResponse_service_1.GetResponseService
            ],
            bootstrap: [app_component_1.AppComponent]
        })
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
