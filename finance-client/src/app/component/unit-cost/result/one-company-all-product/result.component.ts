import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../../api/budget.service';
import {ActivatedRoute, ParamMap, Router, Params} from '@angular/router';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class UnitResultComponent {

  tableData: any[] = [];
  sums: any[] = [];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 20; // 默认每页显示10条记录
  _year = '';
  _month = '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  _allChecked = false;
  _indeterminate = false;
  _displayData = [];
  constructor(private budgetService: BudgetService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.queryParams.subscribe((params: Params) => {
      this._year = params['year'];
      this._month = params['month'];
      this._company = params['company'];
      this._product = params['product'];
      this.findList({
        year: params['year'],
        month: params['month'],
        company: params['company'],
        product: params['product']
      });
      let array:  any[];
      array = Object.keys(params);
      array.forEach((item, i) => {
        this.sums.push(params[item]);
        if (item === 'product') {
          this.sums.push('全部生产线');
          this.sums.push('全部规格');
        }
      });
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
    this.budgetService.getResultData(params).subscribe(
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
      year: this._year,
      month: this._month,
      company: this._company,
      product: this._product,
      workshop: this._workshop,
      productLine: this._productLine,
      spec: this._spec,
    };
    this.tableData.splice(0, this.tableData.length);
    this.budgetService.getResultData(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data;
        }
        this._loading = false;
      }
    );
  }
  // 返回
  goBack() {
    this.router.navigate(['/UnitCostResult',
      {year: this._year, month: this._month, company: this._company, product: this._product}]);
  }
}
