import { Component, OnInit } from '@angular/core';
import {MaterialManageService} from '../../../api/materialManage.service';

@Component({
  selector: 'app-material-manage',
  templateUrl: './material-manage.component.html',
  styleUrls: ['./material-manage.component.css']
})
export class MaterialManageComponent implements OnInit {
  tableData: any[] = [];
  _loading = true;
  _total = 1; // 默认总记录数为1
  _current = 1; // 默认当前页为1
  _pageSize = 50; // 默认每页显示10条记录
  _startMonth = '';
  _endMonth = '';
  _company = '';
  _product = '';
  _displayData = [];
  constructor(private materialManageService: MaterialManageService) { }

  ngOnInit() {
    this.findList();
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
  //  获取物料匹配关系表信息
  findList() {
    const params = {};
    this.materialManageService.getMatchedMaterial(params).subscribe(
      data => {
        if (data.page !== null) {
          this._total = data.page.total;
          this.tableData = data.data;
        }
        this.tableData = data.data;
        this._loading = false;
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  // 获取未匹配上的物料匹配关系信息，从而可以方便用户自主添加物料匹配关系
  findUnmatchedList() {
    const params = {};
   this.materialManageService.getUnmatchedMaterial(params).subscribe(
     data => {
       if (data.page !== null) {
         this._total = data.page.total;
         this.tableData = data.data;
       }
       this.tableData = data.data;
       this._loading = false;
     },
     error2 => {
       this._loading = false;
     }
   );
  }
  // 修改物料匹配关系
  updateMaterialList() {
    const params = {};
    this.materialManageService.updateMatchedMaterial(params).subscribe(
      data => {
        this.findList();
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  addMaterialList() {
    const params = {};
    this.materialManageService.addMatchedMaterial(params).subscribe(
      data => {
        this.findList();
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  // 改变分页时执行的方法
  changeList() {
  }
}
