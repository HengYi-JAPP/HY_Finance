"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var UnitResultComponent = /** @class */ (function () {
    function UnitResultComponent(budgetService, route, router) {
        var _this = this;
        this.budgetService = budgetService;
        this.route = route;
        this.router = router;
        this.tableData = [];
        this.sums = [];
        this._loading = true;
        this._total = 1; // 默认总记录数为1
        this._current = 1; // 默认当前页为1
        this._pageSize = 10; // 默认每页显示10条记录
        this._company = '';
        this._product = '';
        this._workshop = '';
        this._productLine = '';
        this._spec = '';
        this._allChecked = false;
        this._indeterminate = false;
        this._displayData = [];
        this.route.queryParams.subscribe(function (params) {
            _this.findList({ company: params['company'] });
            var array;
            array = Object.keys(params);
            array.forEach(function (item, i) {
                _this.sums.push(params[item]);
                if (item === 'product') {
                    _this.sums.push('全部生产线');
                    _this.sums.push('全部规格');
                }
            });
        });
    }
    UnitResultComponent.prototype.getkeys = function (item) {
        var array;
        array = Object.keys(item);
        if (array[array.length - 1] === 'checked') {
            array.splice(array.length - 1, 1);
        }
        return array;
    };
    UnitResultComponent.prototype._displayDataChange = function ($event) {
        this._displayData = $event;
        this._refreshStatus();
    };
    UnitResultComponent.prototype._refreshStatus = function () {
        var allChecked = this._displayData.every(function (value) { return value.checked === true; });
        var allUnChecked = this._displayData.every(function (value) { return !value.checked; });
        this._allChecked = allChecked;
        this._indeterminate = (!allChecked) && (!allUnChecked);
        this.changeList();
    };
    UnitResultComponent.prototype._checkAll = function (value) {
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
    // 获取数据列表
    UnitResultComponent.prototype.findList = function (param) {
        var _this = this;
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
        };
        this._year = param.year;
        this._month = param.month;
        this._company = param.company;
        this._product = param.product;
        this._workshop = param.workshop;
        this._productLine = param.productLine;
        this._spec = param.spec;
        this.budgetService.getResultData(params).subscribe(function (data) {
            _this._total = data.page.total;
            _this.tableData = data.data;
            _this._loading = false;
        });
    };
    // 改变列表
    UnitResultComponent.prototype.changeList = function () {
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
        };
        this.tableData.splice(0, this.tableData.length);
        this.budgetService.getAllCompanyData(params).subscribe(function (data) {
            _this._total = data.page.total;
            _this.tableData = data.data;
            _this._loading = false;
        });
    };
    // 返回
    UnitResultComponent.prototype.goBack = function () {
        this.router.navigate(['/UnitCostResult']);
    };
    UnitResultComponent = __decorate([
        core_1.Component({
            selector: 'app-result',
            templateUrl: './result.component.html',
            styleUrls: ['./result.component.css']
        })
    ], UnitResultComponent);
    return UnitResultComponent;
}());
exports.UnitResultComponent = UnitResultComponent;
