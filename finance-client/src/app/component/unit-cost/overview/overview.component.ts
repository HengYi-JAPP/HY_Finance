import { Component, OnInit } from '@angular/core';
import {SelectComponent} from '../../shared-component/select/select.component';
import {BudgetService} from '../../../api/budget.service';
@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css'],
  providers: [SelectComponent]
})
export class UnitOverviewComponent {
  tableData: any[] = [];
  stageType = 'stage'; // 是否分阶段标志，默认是分阶段的
  dimension = 'dimension'; // 是否是考核维度的，默认是考核维度的
  sums: any[] = [];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 10; // 默认每页显示10条记录
  // _year = '';
  // _month = '';
  _startMonth = '';
  _endMonth = '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  _allChecked = false;
  _indeterminate = false;
  _displayData = [];
  // 构造函数中放入测试数据
  constructor(private budgetService: BudgetService) {
    this.findList({
      // year: this.getYear(),
      // month: this.getMonth(),
      startMonth: this.getYear() + '-' + this.getMonth(),
      endMonth: this.getYear() + '-' + this.getMonth()
    });
  }
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
      stageType: this.stageType,
      dimension: this.dimension,
      // year: param.year,
      // month: param.month,
      startMonth: param.startMonth,
      endMonth: param.endMonth,
      company: param.company,
      product: param.product,
      workshop: param.workshop,
      productLine: param.productLine,
      spec: param.spec
    };
    // this._year = param.year;
    // this._month = param.month;
    this._startMonth = param.startMonth;
    this._endMonth = param.endMonth;
    this._company = param.company;
    this._product = param.product;
    this._workshop = param.workshop;
    this._productLine = param.productLine;
    this._spec = param.spec;
    this.budgetService.getCostItem(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data;
        }
        this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  changeList() {
    this._loading = true;
    const params = {
      pageIndex: this._current,
      pageCount: this._pageSize,
      stageType: this.stageType,
      dimension: this.dimension,
      // year: this._year,
      // month: this._month,
      startMonth: this._startMonth,
      endMonth: this._endMonth,
      company: this._company,
      product: this._product,
      workshop: this._workshop,
      productLine: this._productLine,
      spec: this._spec
    };
    this.tableData.splice(0, this.tableData.length);
    this.budgetService.getCostItem(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data;
        }
          this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  getTrStyle(data) {
    return {
      'background-color': data['type'] === '实际' ? '#7edef1' : data['type1'] === '预算' ? '#4bbee6' : '#329297'
    };
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
