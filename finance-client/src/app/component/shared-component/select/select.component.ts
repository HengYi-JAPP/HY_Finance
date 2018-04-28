import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BudgetService} from '../../../api/budget.service';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html'
})
export class SelectComponent implements OnInit {

  @Output() FindList = new EventEmitter();
  @Input() startMonth: string; // 开始月份
  @Input() endMonth: string; // 结束月份
  // @Input() year: string;
  // @Input() month: string;
  @Input() company: string;
  @Input() product: string;
  // years: any[] = [];
  // months: any[] = [
  //   {value: '1', label: '1月'},
  //   {value: '2', label: '2月'},
  //   {value: '3', label: '3月'},
  //   {value: '4', label: '4月'},
  //   {value: '5', label: '5月'},
  //   {value: '6', label: '6月'},
  //   {value: '7', label: '7月'},
  //   {value: '8', label: '8月'},
  //   {value: '9', label: '9月'},
  //   {value: '10', label: '10月'},
  //   {value: '11', label: '11月'},
  //   {value: '12', label: '12月'},
  //   ];
  companys: any[] = [];
  products: any[] = [];
  productLines: any[] = [];
  workshops: any[] = [];
  specs: any[] = [];
  // year: number;
  // month: number;
  // company: string;
  // product: string;
  productLine: string;
  spec: string;
  workshop: string;
  constructor(private budgetService: BudgetService) {
    // 年份选择框可以选择16年到当前年份
    const currentYear = new Date().getFullYear();
    // for ( let i = 2016; i <= currentYear; i++) {
    //   const obj = {value: i + '', label: i + '年'};
    //   this.years.push(obj);
    // }
    this.budgetService.getDictionary({}).subscribe(
      data => {
        data.data[0].forEach((item, index) => {
          if (item.dictType === '公司') {
            const obj = {value: '', label: ''};
            obj.value = item.dictKey;
            obj.label = item.dictValue;
            this.companys.push(obj);
          }
          if (item.dictType === '产品') {
            const obj = {value: '', label: ''};
            obj.value = item.dictKey;
            obj.label = item.dictValue;
            this.products.push(obj);
          }
        });
      }
    );
  }
  ngOnInit() {
    // this.year = this.getYear() + '';
    // this.month = this.getMonth() + '';
    this.startMonth = this.getYear() + '-' + this.getMonth();
    this.endMonth = this.getYear() + '-' + this.getMonth();
    this.changeProduct();
  }
  // 获取时间的方法
  handle(time: number): void {
  }
  changeCompany() {}
  // 当产品改变时触发事件
  changeProduct() {
    this.workshops = [];
    this.productLines = [];
    this.specs = [];
    const param = {
      // year: this.year,
      // month: this.month,
      startMonth: new Date(this.startMonth).getFullYear() + '-' + (new Date(this.startMonth).getMonth() + 1),
      endMonth: new Date(this.endMonth).getFullYear() + '-' + (new Date(this.endMonth).getMonth() + 1),
      company: this.company,
      product: this.product
    };
    this.budgetService.getDictionary(param).subscribe(
      data => {
        if (data.data[1] !== null) {
          data.data[1].forEach((item, index) => {
            const obj = {value: '', label: ''};
            obj.value = item.workshop;
            obj.label = item.workshop;
            this.workshops.push(obj);
          });
        }
      }
    );
  }
  // 当车间改变时触发事件
  changeWorkshop() {
    this.productLines = [];
    this.specs = [];
    const param = {
      // year: this.year,
      // month: this.month,
      startMonth: new Date(this.startMonth).getFullYear() + '-' + (new Date(this.startMonth).getMonth() + 1),
      endMonth: new Date(this.endMonth).getFullYear() + '-' + (new Date(this.endMonth).getMonth() + 1),
      company: this.company,
      product: this.product,
      workshop: this.workshop
    };
    this.budgetService.getDictionary(param).subscribe(
      data => {
        if (data.data[2] !== null) {
          data.data[2].forEach((item, index) => {
            const obj = {value: '', label: ''};
            obj.value = item.line;
            obj.label = item.line;
            this.productLines.push(obj);
          });
        }
      }
    );
  }
  // 当生产线改变时触发事件
  changeProductLine() {
    this.specs = [];
    const param = {
      // year: this.year,
      // month: this.month,
      startMonth: new Date(this.startMonth).getFullYear() + '-' + (new Date(this.startMonth).getMonth() + 1),
      endMonth: new Date(this.endMonth).getFullYear() + '-' + (new Date(this.endMonth).getMonth() + 1),
      company: this.company,
      product: this.product,
      workshop: this.workshop,
      productLine: this.productLine
    };
    this.budgetService.getDictionary(param).subscribe(
      data => {
        if (data.data[3] !== null) {
          data.data[3].forEach((item, index) => {
            const obj = {value: '', label: ''};
            obj.value = item.spec;
            obj.label = item.spec;
            this.specs.push(obj);
          });
        }
      }
    );
  }
  findList() {
    // console.log(new Date(this.startMonth).getMonth());
    // console.log(this.endMonth + '');
    const obj = {
      // year: this.year,
      // month: this.month,
      startMonth: new Date(this.startMonth).getFullYear() + '-' + (new Date(this.startMonth).getMonth() + 1),
      endMonth: new Date(this.endMonth).getFullYear() + '-' + (new Date(this.endMonth).getMonth() + 1),
      company: this.company,
      product: this.product,
      workshop: this.workshop,
      productLine: this.productLine,
      spec: this.spec
    };
    this.FindList.emit(obj);
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
