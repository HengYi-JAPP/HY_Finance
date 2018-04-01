"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var global = require("../global");
var url = global.baseUrl + '/FinanceBudgetController';
var BudgetService = /** @class */ (function () {
    function BudgetService(response) {
        this.response = response;
    }
    /* 从后台获取详细列表数据*/
    BudgetService.prototype.getDetailData = function (param) {
        return this.response.appPost(param, url + '/getDetailData');
    };
    /* 从后台获取*/
    BudgetService.prototype.getDictionary = function () {
        return this.response.appGet(url + '/getDictionary');
    };
    /* 从后台获取详细比对结果 */
    BudgetService.prototype.getResultData = function (param) {
        return this.response.appPost(param, url + '/getResultData');
    };
    /* 获取公司维度的对比结果*/
    BudgetService.prototype.getAllCompanyData = function (param) {
        return this.response.appPost(param, url + '/getAllCompanyData');
    };
    BudgetService = __decorate([
        core_1.Injectable()
    ], BudgetService);
    return BudgetService;
}());
exports.BudgetService = BudgetService;
