import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../api/budget.service';
import {ExcelServiceService} from '../../../api/excelService.service';
import * as global from '../../../../app/global';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})
export class UnitBudgetComponent {
  isVisible= false;
  tableData: any[] = [];
  priceORconsumer = 'price';
  sums: any[] = [];
  uploadUrl: string;
  downloadUrl: string;
  _allChecked = false;
  _indeterminate = false;
  _displayData = [];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 10; // 默认每页显示10条记录
  _fact = true;
  _budget = true;
  _startMonth: '';
  _endMonth: '';
  _company = '';
  _product = '';
  _workshop = '';
  _productLine = '';
  _spec = '';
  showFlag = false;
  operate = '编辑';
  constructor(private budgetService: BudgetService,
              private excelService: ExcelServiceService) {
    // 声明导入Excel的URL(即上传文件)
    this.uploadUrl = global.baseUrl + '/FinanceBudgetController/importBudgetData';
    // 声明导出excel的URL(即导出文件)
    this.downloadUrl = global.baseUrl + '/FinanceBudgetController/exportBudgetData';
    this.findList({
      startMonth: this.getYear() + '-' + this.getMonth(),
      endMonth: this.getYear() + '-' + this.getMonth()
    });
  }
  //刷新数据时调用的方法
  _displayDataChange($event) {
    this._displayData = $event;
  }
//改变选中状态时触发的方法
  _refreshStatus() {
    const allChecked = this._displayData.every(value => value.checked === true);
    const allUnChecked = this._displayData.every(value => !value.checked);
    this._allChecked = allChecked;
    this._indeterminate = (!allChecked) && (!allUnChecked);
  }
  //全选的方法
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
  // 根据主键获取对象
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
      startMonth: param.startMonth,
      endMonth: param.endMonth,
      company: param.company,
      product: param.product,
      workshop: param.workshop,
      productLine: param.productLine,
      spec: param.spec,
      priceOrconsumer: this.priceORconsumer,
      type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无')
    };
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
          console.log(111);
          console.log(this.tableData);
          console.log(111)
          console.log(222)
          console.log(data.data[0]);
          console.log(222)
          this._displayData = this.tableData;
          this.sums = data.data[1];
        }
        this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  // 添加style
  getTrStyle(data) {
    return {
      'background-color': data['type'] === '实际' ? 'white' : data['type1'] === '预算' ? 'white' : '#87e8de'
    };
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
      priceOrconsumer: this.priceORconsumer,
      type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无')
    };
    this.tableData.splice(0, this.tableData.length);
    this.budgetService.getDetailData(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data[0];
          this._allChecked = false;
          this._displayData = this.tableData;
        }
        this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  // 导出Excel方法
  exportExcel() {
    const params = {
      startMonth: this._startMonth,
      endMonth: this._endMonth,
      company: this._company,
      product: this._product,
      workshop: this._workshop,
      productLine: this._productLine,
      spec: this._productLine,
      priceOrconsumer: this.priceORconsumer,
      type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无')
    };
    const array = Object.keys(params);
    let param = '?id=null';
    array.forEach((item, i) => {
      if (params[array[i]]) {
        param = param + '&' + array[i] + '=' + params[array[i]];
      }
    });
    const a = window.open(this.downloadUrl + param);
    a.document.execCommand('SaveAs');
  }
  // 计算均值的方法
  sumCheck() {
    this._loading = true;
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
      priceOrconsumer: this.priceORconsumer,
      type: this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无')
    };
    if (this.sums != null) {
      this.sums.splice(0, this.sums.length);
    }
    this.budgetService.getSumDetail(params).subscribe(
      data => {
          this.sums = data.data;
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
  //编辑方法，点击编辑之后可以对成本项详情数据进行修改（只能修改预算数据）
  edit() {
    if ( this._fact ? (this._budget ? '' : '实际') : (this._budget ? '预算' : '无') === '预算') {
      if (this.showFlag){//点击保存之后
        console.log(this.tableData);
        this._loading = true
        this.budgetService.updateBudgetDetail(this.tableData).subscribe(
          data =>{
            this._loading = true
          },
          error2 => {
            this._loading = false
          }
          );
        this.showFlag = false
        this.operate = '编辑'
      }else {//点击编辑之后执行的方法
        this.showFlag = true
        this.operate = '保存'
        this._loading = false
      }
    }
  }
  //增加新增规格的方法
  add() {
    // this.isVisible = true;
  }
  //点击取消后触发的方法
  handleCancel() {
    this.isVisible = false;
  }
  //点击确定之后触发的方法
  handleOk() {
    this.isVisible = false;
  }

}

