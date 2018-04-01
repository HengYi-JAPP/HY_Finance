"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var SelectComponent = /** @class */ (function () {
    // 构造函数中放入测试数据
    function SelectComponent(budgetService) {
        var _this = this;
        this.budgetService = budgetService;
        this.FindList = new core_1.EventEmitter();
        this.years = [];
        this.months = [
            { value: 1, label: '1月' },
            { value: 2, label: '2月' },
            { value: 3, label: '3月' },
            { value: 4, label: '4月' },
            { value: 5, label: '5月' },
            { value: 6, label: '6月' },
            { value: 7, label: '7月' },
            { value: 8, label: '8月' },
            { value: 9, label: '9月' },
            { value: 10, label: '10月' },
            { value: 11, label: '11月' },
            { value: 12, label: '12月' },
        ];
        this.companys = [];
        this.products = [];
        this.productLines = [];
        this.workshops = [];
        this.specs = [];
        // 年份选择框可以选择16年到当前年份
        var currentYear = new Date().getFullYear();
        for (var i = 2016; i <= currentYear; i++) {
            var obj = { value: i, label: i + '年' };
            this.years.push(obj);
        }
        this.budgetService.getDictionary().subscribe(function (data) {
            console.log(data);
            data.data.forEach(function (item, index) {
                if (item.dictType === '公司') {
                    var obj = { value: '', label: '' };
                    obj.value = item.dictKey;
                    obj.label = item.dictValue;
                    _this.companys.push(obj);
                }
                if (item.dictType === '产品') {
                    var obj = { value: '', label: '' };
                    obj.value = item.dictKey;
                    obj.label = item.dictValue;
                    _this.products.push(obj);
                }
            });
        });
    }
    // 获取时间的方法
    SelectComponent.prototype.handle = function (time) {
    };
    SelectComponent.prototype.changeCompany = function () { };
    SelectComponent.prototype.changeProduct = function () { };
    SelectComponent.prototype.changeProductLine = function () { };
    SelectComponent.prototype.changeSpec = function () { };
    SelectComponent.prototype.findList = function () {
        var obj = {
            year: this.year,
            month: this.month,
            company: this.company,
            product: this.product,
            workshop: this.workshop,
            productLine: this.productLine,
            spec: this.spec
        };
        this.FindList.emit(obj);
    };
    __decorate([
        core_1.Output()
    ], SelectComponent.prototype, "FindList", void 0);
    SelectComponent = __decorate([
        core_1.Component({
            selector: 'app-select',
            templateUrl: './select.component.html'
        })
    ], SelectComponent);
    return SelectComponent;
}());
exports.SelectComponent = SelectComponent;
