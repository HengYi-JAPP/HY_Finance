import { Component, OnInit } from '@angular/core';
import {BudgetService} from '../../../../api/budget.service';

@Component({
  selector: 'app-budget-manage',
  templateUrl: './budget-manage.component.html',
  styleUrls: ['./budget-manage.component.css']
})
export class BudgetManageComponent implements OnInit {
  _startMonth: '';
  _endMonth: '';
  _company='';
  _product='';
  tableData=[];
  _loading: '';
  _total='';
  _pageSize='';
  _current = '';
  _displayData = [];
  constructor(private budgetService:BudgetService) {
  }

  ngOnInit() {}
  findList() {
  }
  //根据key获取值
  getkeys(item) {
    let array: any[];
    array = Object.keys(item);
    if (array[array.length - 1] === 'checked') {
      array.splice(array.length - 1, 1);
    }
    return array;
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
  _displayDataChange($event) {
    this._displayData = $event;
    this._refreshStatus();
  }

  _refreshStatus() {
    this.changeList();
  }
  changeList() {

  }

}
