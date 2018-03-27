import { Component, OnInit } from '@angular/core';
import {ResultService} from '../../../../api/result.service';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class UnitResultComponent {
  tableData: any[] = [];
  sums: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  // sums: number[] = [1, 2, 3];
  // colsName: any[] = ['公司', '产品', '生产线', '规格', '实际单位成本①（实际单价*实际单耗*实际结构）', '考核维度实际单位成本②（预算单价*实际单耗*实际结构）',
  //   '考核维度预算单位成本③（预算单价*预算单耗*实际结构）', '目标预算单位成本④（预算单价*预算单耗*预算结构）', '单价变动影响单位成本②-①',
  //   '单耗变动影响单位成本③-②', '结构变动影响单位成本④-③', '总体差异④-①' ];
  constructor(private resultService: ResultService) {
    this.tableData.push({
      company: '高新',
      product: 'POY',
      productLine: '1线',
      spec: '192dtex/96f',
      fact: '1000',
      checkFact: '10000',
      checkBudget: '1000',
      budget: '2000',
      priceEffect: '9',
      consumerEffect: '10',
      structureEffect: '11',
      totalDifference: '12'
    });
  }
  _allChecked = false;
  _indeterminate = false;
  _displayData = [];
  getkeys(item) {
    let array: any[];
    // console.log(item);
    // console.log(Object.keys(item));
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
