import { Component, OnInit } from '@angular/core';
import {ProductlineManageService} from '../../../api/productlineManage.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';


@Component({
  selector: 'app-productline-manage',
  templateUrl: './productline-manage.component.html',
  styleUrls: ['./productline-manage.component.css']
})
export class ProductlineManageComponent implements OnInit {

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
  matchCondition = 'matched';
  isVisible = false;
  validateForm: FormGroup;
  controlArray = [];
  id = '';
  product_line = '';
  product_match = '';
  product_material_match = '';
  product_material_yarn = '';
  company = '';
  product_specifications_match = '';
  product_specifications_yarn = '';
  _allChecked = false;
  _indeterminate = false;
  _disabledButton = true;
  _checkedNumber = 0;
  _operating = false;
  _dataSet = [];
  _operateType = '';
  _refreshStatus2() {
    const allChecked = this._displayData.every(value => value.checked === true);
    const allUnChecked = this._displayData.every(value => !value.checked);
    this._allChecked = allChecked;
    this._indeterminate = (!allChecked) && (!allUnChecked);
    this._disabledButton = !this._displayData.some(value => value.checked);
    this._checkedNumber = this._displayData.filter(value => value.checked).length;
  }
  _checkAll(value) {
    if (value) {
      console.log(value);
      this._displayData.forEach(tableData => {
        tableData.checked = true;
      });
    } else {
      this._displayData.forEach(tableData => {
        tableData.checked = false;
      });
    }
    this._refreshStatus2();
  }
  _operateData() {
    /*console.log( this._displayData);*/
    const object = this._displayData;
    this._operating = true;
    for ( let i = 0; i < object.length; i++) {
      /*console.log(object[i]);*/
      if (object[i].checked) {
        /*console.log('checked:---');
        console.log(object[i]);*/
        this.deleteProductlineList(object[i]);
      }
    }
    this.changeList();
    setTimeout(_ => {
      this._displayData.forEach(value => value.checked = false);
      this._refreshStatus2();
      this._operating = false;
    }, 1000);
  }

  constructor(private productlineManageService: ProductlineManageService, private fb: FormBuilder, private message: NzMessageService) { }
  ngOnInit() {
    this.validateForm = this.fb.group({});

    for (let i = 0; i < 10; i++) {
      this.controlArray.push({ index: i, show: i < 6 });
      this.validateForm.addControl(`field${i}`, new FormControl());
    }
    this.findList();
  }
  _displayDataChange($event) {
    this._displayData = $event;
    this._refreshStatus2();
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
  //  获取生产线匹配关系表信息
  findList() {
    const params = {
      pageIndex: this._current,
      pageCount: this._pageSize
    };
    // 查询已经匹配上的生产线匹配关系
    if ('matched' === this.matchCondition) {
      this. productlineManageService. getMatchedProductline(params).subscribe(
        data => {
          if (data.page !== null) {
            this._total = data.page.total;
            this.tableData = data.data;
          }
          this.tableData = data.data;
          this._displayDataChange(this.tableData);
          this._loading = false;
        },
        error2 => {
          this._loading = false;
        }
      );
    } else if ('unmatched' === this.matchCondition) { // 获取未匹配上的生产线匹配关系信息，从而可以方便用户自主添加生产线匹配关系
      this. productlineManageService.getUnmatchedProductline(params).subscribe(
        data => {
          if (data.page != null) {
            this._total = data.page.total;
            this.tableData = data.data;
          }
          this.tableData = data.data;
          this._displayDataChange(this.tableData);
          this._loading = false;
        },
        error2 => {
          this._loading = false;
        }
      );
    }
  }
  // 修改生产线匹配关系
  updateMaterialList() {
    const params = {};
    this. productlineManageService.updateMatchedProductline(params).subscribe(
      data => {
        this.findList();
      },
      error2 => {
        this._loading = false;
      }
    );
  }
  addProductlineList() {
    this._operateType = 'add';
    this.product_line = '';
    this.product_match = '';
    this.product_material_match = '';
    this.product_material_yarn = '';
    this.company = '';
    this.product_specifications_match = '';
    this.product_specifications_yarn = '';
    this.showModal();
    // const params = {};
    // this.materialManageService.addMatchedMaterial(params).subscribe(
    //   data => {
    //     this.findList();
    //   },
    //   error2 => {
    //     this._loading = false;
    //   }
    // );
  }
  createMessage2 = (type, text) => {
    this.message.create(type, `删除${text}`, { nzDuration: 5000 });
  }
  updateProductlineList(datas) {
    this._operateType = 'update';
    this.id = datas.id;
    this.product_line = datas.productLine;
    this.product_match = datas.productMatch;
    this.product_material_match = datas.productMaterialMatch;
    this.product_material_yarn = datas.productMaterialYarn;
    this.company = datas.company;
    this.product_specifications_match = datas.productSpecificationsMatch;
    this.product_specifications_yarn = datas.productSpecificationsYarn;
    this.showModal();
    // const params = {};
    // this.materialManageService.addMatchedMaterial(params).subscribe(
    //   data => {
    //     this.findList();
    //   },
    //   error2 => {
    //     this._loading = false;
    //   }
    // );
  }
  deleteProductlineList(datas) {
    /*console.log(datas);*/
    const params = {
      id: datas.id,
      productLine: datas.productLine,
      productMatch: datas.productMatch,
      productMaterialMatch: datas.productMaterialMatch,
      productMaterialYarn: datas.productMaterialYarn,
      company: datas.company,
      productSpecificationsMatch: datas.productSpecificationsMatch,
      productSpecificationsYarn: datas.productSpecificationsYarn
    };
    /*console.log(params);*/
    this.productlineManageService.deleteMatchedProductline(params).subscribe(
      data => {
        if (data.data) {
          this.createMessage2('success', '成功');
          this.findList();
        }
      },
      error2 => {
        this.createMessage2('error', '失败');
      }
    );
  }
  // 改变分页时执行的方法
  changeList() {
    this.findList();
  }
  // 弹出窗口
  showModal() {
    this.isVisible = true;
  }
  createMessage = (type, text) => {
    this.message.create(type, `操作${text}`, { nzDuration: 5000 });
  }
  createMessage3 = (type, text) => {
    this.message.create(type, `操作失败，必填项不能为空`, { nzDuration: 5000 });
  }
  // 点击OK后窗口关闭
  handleOk() {
    console.log('operateType:---');
    console.log(this._operateType);
    /* console.log(this.product_line)*/
    const params = {
      id: this.id,
      productLine: this.product_line,
      productMatch: this.product_match,
      productMaterialMatch: this.product_material_match,
      productMaterialYarn: this.product_material_yarn,
      company: this.company,
      productSpecificationsMatch: this.product_specifications_match,
      productSpecificationsYarn: this.product_specifications_yarn
    };
    if (this._operateType === 'add') {
      // 增加生产线匹配关系
      if (this.product_line === '' || this.product_match === '' || this.product_material_match === '' || this.company === '') {
        this.createMessage3('error', '失败');
        this.isVisible = false;
      } else {
        this.productlineManageService.addMatchedProductline(params).subscribe(
          data => {
            if (data.data) {
              this.createMessage('success', '成功');
              this.findList();
            }
          },
          error2 => {
            this.createMessage('error', '失败');
          }
        );
      }
      this.isVisible = false;
    }
    if (this._operateType === 'update') {
      // 修改生产线匹配关系
      if (this.product_line === '' || this.product_match === '' || this.product_material_match === '' || this.company === '') {
        this.createMessage3('error', '失败');
        this.isVisible = false;
      } else {
        this.productlineManageService.updateMatchedProductline(params).subscribe(
          data => {
            if (data.data) {
              this.createMessage('success', '成功');
              this.findList();
            }
          },
          error2 => {
            this.createMessage('error', '失败');
          }
        );
      }
      this.isVisible = false;
    } else {
      this.isVisible = false;
    }
  }
  // 点击取消后窗口关闭
  handleCancel() {
    this.isVisible = false;
  }
}
