import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../../api/budget.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-all-company',
  templateUrl: './all-company.component.html',
  styleUrls: ['./all-company.component.css']
})
export class AllCompanyComponent implements OnInit {
  tableData: any[] = [];
  // sums: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 50; // 默认每页显示10条记录
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
    this.findList({
      year: this.getYear(),
      month: this.getMonth(),
    });
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        if (params.keys.length === 0) {
          return [];
        }
          this._year = params.get('year');
          this._month = params.get('month');
          this._company = params.get('company');
          this._product = params.get('product');
          this.findList({
            year: this._year,
            month: this._month,
            // company: this._company,
            // product: this._product
          });
          return [];
        }
      )
    ).subscribe();
  }
  ngOnInit() {
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
      },
      error2 => {
      }
    );
    this._loading = false;
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
      },
      error2 => {
        this._loading = false;
      }
    );
    // this._loading = false;
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
