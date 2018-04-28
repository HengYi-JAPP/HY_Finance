import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../api/budget.service';
import * as global from '../../../../app/global';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})
export class UnitBudgetComponent {
  tableData: any[] = [];
  priceORconsumer = 'price';
  sums: any[] = [];
  // // 预算
  // sums2: any[] = [];
  // // 差异
  // sums3: any[] = [];
  uploadUrl: string;
  _allChecked = false;
  _indeterminate = false;
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 10; // 默认每页显示10条记录
  _displayData = [];
  _fact = true;
  _budget = true;
  // _year: '';
  // _month: '';
  _startMonth: '';
  _endMonth: '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  constructor(private budgetService: BudgetService) {
    this.uploadUrl = global.baseUrl + '/FinanceBudgetController/importBudgetData';
    this.findList({
      // year: this.getYear(),
      // month: this.getMonth(),
      startMonth: this.getYear() + '-' + this.getMonth(),
      endMonth: this.getYear() + '-' + this.getMonth()
    });
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
    this._loading = true;
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
      priceOrconsumer: this.priceORconsumer
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
    this.tableData.splice(0, this.tableData.length);
    this.budgetService.getDetailData(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data[0];
          this.sums = data.data[1];
        }
        this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
    // this._loading = false;
  }
  // 添加style
  getTrStyle(data) {
    return {
      'background-color': data['type'] === '实际' ? '#7edef1' : data['type1'] === '预算' ? '#4bbee6' : '#329297'
      // 'display': (data['type'] === '实际' && this._fact) || (data['type'] === '预算' && this._budget) ? '' : 'none'
    };
  }
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
      priceOrconsumer: this.priceORconsumer,
      type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无')
    };
    this.tableData.splice(0, this.tableData.length);
    this.budgetService.getDetailData(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data[0];
        }
        this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
    // this._loading = false;
  }
  // 导出Excel方法
  exportExcel() {
    // window.location.href = global.baseUrl + '/FinanceBudgetController/exportExcel';
    // window.open(global.baseUrl + '/FinanceBudgetController/exportExcel');
    const param = {};
    // const url = global.baseUrl + '/FinanceBudgetController/exportExcel';
    this.budgetService.exportExcel(param).subscribe(
      data => {
        // Blob转化为链接
        const link = document.createElement('a');
        link.setAttribute('href', window.URL.createObjectURL(data));
        link.setAttribute('download', 'filename.json');
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    );
  }
  // 计算均值的方法
  sumCheck() {
    const params = {
      // year: this._year,
      // month: this._month,
      startMonth: this._startMonth,
      endMonth: this._endMonth,
      company: this._company,
      product: this._product,
      workshop: this._workshop,
      productLine: this._productLine,
      spec: this._spec,
      priceOrconsumer: 'checkUnitPrice',
      type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无')
    };
    if (this.sums != null) {
      this.sums.splice(0, this.sums.length);
    }
    this.budgetService.getSumDetail(params).subscribe(
      data => {
          this.sums = data.data;
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

