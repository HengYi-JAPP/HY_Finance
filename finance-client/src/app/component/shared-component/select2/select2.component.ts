import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BudgetService} from '../../../api/budget.service';

@Component({
  selector: 'app-select2',
  templateUrl: './select2.component.html'
})
export class Select2Component implements OnInit{
  @Output() FindList = new EventEmitter();
  @Input() year: string;
  @Input() month: string;
  @Input() company: string;
  @Input() product: string;
  years: any[] = [];
  months: any[] = [
    {value: '1', label: '1月'},
    {value: '2', label: '2月'},
    {value: '3', label: '3月'},
    {value: '4', label: '4月'},
    {value: '5', label: '5月'},
    {value: '6', label: '6月'},
    {value: '7', label: '7月'},
    {value: '8', label: '8月'},
    {value: '9', label: '9月'},
    {value: '10', label: '10月'},
    {value: '11', label: '11月'},
    {value: '12', label: '12月'},
    ];
  companys: any[] = [];
  products: any[] = [];
  productLines: any[] = [];
  workshops: any[] = [];
  specs: any[] = [];
  productLine: string;
  spec: string;
  workshop: string;
  // 构造函数中放入测试数据
  constructor(private budgetService: BudgetService) {
    // 年份选择框可以选择16年到当前年份
    const currentYear = new Date().getFullYear();
    for ( let i = 2016; i <= currentYear; i++) {
      const obj = {value: i + '', label: i + '年'};
      this.years.push(obj);
    }
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
    this.year = new Date().getFullYear() + '';
    this.month = new Date().getMonth() + '';
  }
  // 获取时间的方法
  handle(time: number): void {
  }
  changeCompany() {}
  changeProduct() {
    this.workshops = [];
    this.productLines = [];
    this.specs = [];
    const param = {
      year: this.year,
      month: this.month,
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
  findList() {
    const obj = {
      year: this.year,
      month: this.month,
      company: this.company,
      product: this.product,
      // workshop: this.workshop,
      // productLine: this.productLine,
      // spec: this.spec
    };
    this.FindList.emit(obj);
  }
}
