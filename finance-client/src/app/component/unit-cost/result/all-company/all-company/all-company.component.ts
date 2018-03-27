import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-all-company',
  templateUrl: './all-company.component.html',
  styleUrls: ['./all-company.component.css']
})
export class AllCompanyComponent implements OnInit {
  tableData: any[] = [];
  sums: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  colsName: any[] = ['公司', '产品' , '实际单位成本①（实际单价*实际单耗*实际结构）', '考核维度实际单位成本②（预算单价*实际单耗*实际结构）',
    '考核维度预算单位成本③（预算单价*预算单耗*实际结构）', '目标预算单位成本④（预算单价*预算单耗*预算结构）', '单价变动影响单位成本②-①',
    '单耗变动影响单位成本③-②', '结构变动影响单位成本④-③', '总体差异④-①' ];
  constructor() { }

  ngOnInit() {
  }
  findList() {}
}
