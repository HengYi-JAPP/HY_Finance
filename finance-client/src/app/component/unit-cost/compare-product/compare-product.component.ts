import { Component, OnInit } from '@angular/core';
import {SapBudgetService} from '../../../api/sapBudget.service';

@Component({
  selector: 'app-compare-product',
  templateUrl: './compare-product.component.html',
  styleUrls: ['./compare-product.component.css']
})
export class CompareProductComponent implements OnInit {
  tableData: any[] = [];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 50; // 默认每页显示10条记录
  _startMonth = '';
  _endMonth = '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  _displayData = [];
  constructor(private sapBudgetService: SapBudgetService) { }

  ngOnInit() {
    this.findList({
      startMonth: this.getYear() + '-' + this.getMonth(),
      endMonth: this.getYear() + '-' + this.getMonth()
    });
  }
  _displayDataChange($event) {
    this._displayData = $event;
    this._refreshStatus();
  }

  _refreshStatus() {
    this.changeList();
  }
  getkeys(item) {
    let array: any[];
    array = Object.keys(item);
    if (array[array.length - 1] === 'checked') {
      array.splice(array.length - 1, 1);
    }
    return array;
  }
  findList(param) {
    const params = {
      pageIndex: this._current,
      pageCount: this._pageSize,
      startMonth: param.startMonth,
      endMonth: param.endMonth,
      company: param.company,
      product: param.product
    };
    this._startMonth = param.startMonth;
    this._endMonth = param.endMonth;
    this._product = param.product;
    this._workshop = param.workshop;
    this._productLine = param.productLine;
    this._spec = param.spec;
    this.sapBudgetService.getCompareProduct(params).subscribe(
      data => {
        // if (data.page !== null) {
        //   this._total = data.page.total;
        //   this.tableData = data.data;
        // }
        this.tableData = data.data;
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
      startMonth: this._startMonth,
      endMonth: this._endMonth,
      company: this._company,
      product: this._product,
      workshop: this._workshop,
      productLine: this._productLine,
      spec: this._spec,
    };
    this.tableData.splice(0, this.tableData.length);
    this.sapBudgetService.getCompareProduct(params).subscribe(
      data => {
        // if (data.page !== null) {
        //   this._total = data.page.total;
        //   this.tableData = data.data;
        // }
        this.tableData = data.data;
        this._loading = false;
      },
      error2 => {
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
