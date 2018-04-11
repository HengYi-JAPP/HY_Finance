import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../../api/budget.service';

@Component({
  selector: 'app-all-company',
  templateUrl: './all-company.component.html',
  styleUrls: ['./all-company.component.css']
})
export class AllCompanyComponent {
  tableData: any[] = [];
  // sums: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 50; // 默认每页显示10条记录
  _year: '';
  _month: '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  constructor(private budgetService: BudgetService) {
    this.findList({
      year: this.getYear(),
      month: this.getMonth(),
    });
  }
  _allChecked = false;
  _indeterminate = false;
  _displayData = [];
  getkeys(item) {
    let array: any[];
    array = Object.keys(item);
    if (array[array.length - 1] === 'checked') {
      array.splice(array.length - 1, 1);
    }
    return array;
  }
  _displayDataChange($event) {
    this._displayData = $event;
    this._refreshStatus();
  }

  _refreshStatus() {
    const allChecked = this._displayData.every(value => value.checked === true);
    const allUnChecked = this._displayData.every(value => !value.checked);
    this._allChecked = allChecked;
    this._indeterminate = (!allChecked) && (!allUnChecked);
    this.changeList();
  }

  _checkAll(value) {
    if (value) {
      this._displayData.forEach(data => {
        data.checked = true;
      });
    } else {
      this._displayData.forEach(data => {
        data.checked = false;
      });
    }
    this._refreshStatus();
  }
  // 获取数据列表
  findList(param) {
    const params = {
      pageIndex: this._current,
      pageCount: this._pageSize,
      year: param.year,
      month: param.month,
      company: param.company,
      product: param.product,
      // workshop: param.workshop,
      // productLine: param.productLine,
      // spec: param.spec,
    };
    this._year = param.year;
    this._month = param.month;
    this._company = param.company;
    this._product = param.product;
    this._workshop = param.workshop;
    this._productLine = param.productLine;
    this._spec = param.spec;
    this.budgetService.getAllCompanyData(params).subscribe(
      data => {
        this._total = data.page.total;
        this.tableData = data.data;
        this._loading = false;
      }
    );
  }
  changeList() {
    this._loading = true;
    const params = {
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
    this.budgetService.getAllCompanyData(params).subscribe(
      data => {
        this._total = data.page.total;
        this.tableData = data.data;
        this._loading = false;
      }
    );
  }
  // 获取当前年份
  getYear() {
    if (new Date().getMonth() === 0) {
      return new Date().getFullYear() - 1;
    } else {
      return new Date().getFullYear();
    }
  }
  // 获取当前月份的上一个月份
  getMonth() {
    if (new Date().getMonth() === 0) {
      return 12;
    } else {
      return new Date().getMonth();
    }
  }
}
