import {Component, Input, OnInit} from '@angular/core';
@Component({
  selector: 'app-table',
  templateUrl: './table.component.html'
})
export class TableComponent {
  @Input() tableData: any[];
  @Input() cols1: any[] = [{colName: '', rowSpan: 2}];
  @Input() cols2: any[] = [{colName: '', rowSpan: 2}];
  @Input() cols3: any[] = [{colName: '', rowSpan: 2}];
  @Input() colsName: any[];
  @Input() sums: number [];
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
}
