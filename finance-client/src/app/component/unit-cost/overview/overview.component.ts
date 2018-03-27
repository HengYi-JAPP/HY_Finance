import { Component, OnInit } from '@angular/core';
import {ResultService} from '../../../api/result.service';
import {SelectComponent} from '../../shared-component/select/select.component';
@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css'],
  providers: [SelectComponent]
})
export class UnitOverviewComponent {
  tableData: any[] = [];
  sums: any[] = [];
  // 构造函数中放入测试数据
  constructor(private resultService: ResultService) {
    this.tableData.push(
      {
        company: '1',
        product: '2',
        productLine: '3',
        spec: '4',
        jz1: '1',
        jz2: '1',
        jz3: '1',
        jz4: '1',
        jz5: '1',
        jz6: '1',
        jz7: '1',
        jz8: '1',
        fs1: '1',
        fs2: '1',
        fs3: '1',
        fs4: '1',
        fs5: '1',
        fs6: '1',
        fs7: '1',
        fs8: '1',
      }
    );
  }
  _allChecked = false;
  _indeterminate = false;
  _displayData = [];
  getkeys(item) {
    let array: any[];
    console.log(item);
    console.log(Object.keys(item));
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
  findList() {
    this.resultService.getData();
  }
}
