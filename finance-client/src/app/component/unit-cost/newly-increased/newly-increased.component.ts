import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../api/budget.service';

@Component({
  selector: 'app-newly-increased',
  templateUrl: './newly-increased.component.html',
  styleUrls: ['./newly-increased.component.css']
})
export class NewlyIncreasedComponent implements OnInit {
  _startMonth = '';
  _endMonth = '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  _total = 1;
  _current = 1;
  _pageSize = 20;
  _loading = true;
  tableData = [];
  constructor(private budgetService: BudgetService) {
    this.findList({
      // year: this.getYear(),
      // month: this.getMonth(),
      startMonth: this.getYear() + '-' + this.getMonth(),
      endMonth: this.getYear() + '-' + this.getMonth()
    });
  }

  ngOnInit() {
  }

  _displayDataChange($event) {
    this._refreshStatus();
  }

  _refreshStatus() {
    this.changeList();
  }
  // 获取主键
  getkeys(item) {
    let array: any[];
    array = Object.keys(item);
    if (array[array.length - 1] === 'checked') {
      array.splice(array.length - 1, 1);
    }
    return array;
  }
  // 获取数据列表
  findList(param) {
    const params = {
      pageIndex: this._current,
      pageCount: this._pageSize,
      // year: param.year,
      // month: param.month,
      startMonth: param.startMonth,
      endMonth: param.endMonth,
      company: param.company,
      product: param.product,
      workshop: param.workshop,
      productLine: param.productLine,
      spec: param.spec,
    };
    // this._year = param.year;
    // this._month = param.month;
    this._startMonth = param.startMonth,
      this._endMonth = param.endMonth,
      this._company = param.company;
    this._product = param.product;
    this._workshop = param.workshop;
    this._productLine = param.productLine;
    this._spec = param.spec;
    this.budgetService.getNewlyIncreased(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data;
        }
        this._loading = false;
      }
    );
  }
  // 改变列表
  changeList() {
    this._loading = true;
    const params = {
      pageIndex: this._current,
      pageCount: this._pageSize,
      // year: this._year,
      // month: this._month,
      startMonth: this._startMonth,
      endMonth: this._endMonth,
      company: this._company,
      product: this._product,
      workshop: this._workshop,
      productLine: this._productLine,
      spec: this._spec,
    };
    this.tableData.splice(0, this.tableData.length);
    this.budgetService.getNewlyIncreased(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data;
        }
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
