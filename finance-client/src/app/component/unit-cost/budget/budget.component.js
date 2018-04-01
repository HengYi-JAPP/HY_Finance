"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var global = require("../../../../app/global");
var UnitBudgetComponent = /** @class */ (function () {
    function UnitBudgetComponent(budgetService) {
        this.budgetService = budgetService;
        this.tableData = [];
        this.priceORconsumer = 'price';
        this.sums = [];
        this._allChecked = false;
        this._indeterminate = false;
        this._loading = true;
        this._total = 1; // 默认总记录数为1
        this._current = 1; // 默认当前页为1
        this._pageSize = 10; // 默认每页显示10条记录
        this._displayData = [];
        this._fact = true;
        this._budget = true;
        this._company = '';
        this._product = '';
        this._workshop = '';
        this._productLine = '';
        this._spec = '';
        this.uploadUrl = global.baseUrl + '/FinanceBudgetController/importBudgetData';
        this.findList({});
    }
    UnitBudgetComponent.prototype._displayDataChange = function ($event) {
        this._displayData = $event;
        this._refreshStatus();
    };
    UnitBudgetComponent.prototype._refreshStatus = function () {
        var allChecked = this._displayData.every(function (value) { return value.checked === true; });
        var allUnChecked = this._displayData.every(function (value) { return !value.checked; });
        this._allChecked = allChecked;
        this._indeterminate = (!allChecked) && (!allUnChecked);
        this.changeList();
    };
    UnitBudgetComponent.prototype._checkAll = function (value) {
        if (value) {
            this._displayData.forEach(function (data) {
                data.checked = true;
            });
        }
        else {
            this._displayData.forEach(function (data) {
                data.checked = false;
            });
        }
        this._refreshStatus();
    };
    UnitBudgetComponent.prototype.getkeys = function (item) {
        var array;
        array = Object.keys(item);
        if (array[array.length - 1] === 'checked') {
            array.splice(array.length - 1, 1);
        }
        return array;
    };
    // 获取数据列表
    UnitBudgetComponent.prototype.findList = function (param) {
        var _this = this;
        this._loading = true;
        var params = {
            pageIndex: this._current,
            pageCount: this._pageSize,
            year: param.year,
            month: param.month,
            company: param.company,
            product: param.product,
            workshop: param.workshop,
            productLine: param.productLine,
            spec: param.spec,
            priceOrconsumer: this.priceORconsumer
        };
        this._year = param.year;
        this._month = param.month;
        this._company = param.company;
        this._product = param.product;
        this._workshop = param.workshop;
        this._productLine = param.productLine;
        this._spec = param.spec;
        this.tableData.splice(0, this.tableData.length);
        this.budgetService.getDetailData(params).subscribe(function (data) {
            console.log(data.data);
            _this._total = data.page.total;
            _this.tableData = data.data;
            _this._loading = false;
        });
    };
    // 添加style
    UnitBudgetComponent.prototype.getTrStyle = function (data) {
        return {
            'background-color': data['type'] === '实际' ? '#58B7FF' : data['type1'] === '预算' ? '#FFFF00 ' : '#00FF00'
            // 'display': (data['type'] === '实际' && this._fact) || (data['type'] === '预算' && this._budget) ? '' : 'none'
        };
    };
    UnitBudgetComponent.prototype.changeList = function () {
        var _this = this;
        this._loading = true;
        var params = {
            pageIndex: this._current,
            pageCount: this._pageSize,
            year: this._year,
            month: this._month,
            company: this._company,
            product: this._product,
            workshop: this._workshop,
            productLine: this._productLine,
            spec: this._spec,
            priceOrconsumer: this.priceORconsumer,
            type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '')
        };
        this.tableData.splice(0, this.tableData.length);
        this.budgetService.getDetailData(params).subscribe(function (data) {
            _this._total = data.page.total;
            _this.tableData = data.data;
            _this._loading = false;
        });
    };
    UnitBudgetComponent = __decorate([
        core_1.Component({
            selector: 'app-budget',
            templateUrl: './budget.component.html',
            styleUrls: ['./budget.component.css']
        })
    ], UnitBudgetComponent);
    return UnitBudgetComponent;
}());
exports.UnitBudgetComponent = UnitBudgetComponent;
