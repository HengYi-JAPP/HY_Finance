import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';

const url = global.baseUrl + '/FinanceBudgetController';
@Injectable()
export class BudgetService {
  constructor(private response: GetResponseService) {}
  /* 从后台获取详细列表数据*/
  getDetailData(param ): Observable<any> {
    return this.response.appPost(param, url + '/getDetailData');
  }
  /* 从后台获取成本项大类的方法*/
  getCostItem(param): Observable<any> {
    return this.response.appPost(param, url + '/getCostItem');
  }
  /* 从后台获取字典列表，供选择框使用*/
  getDictionary(param): Observable<any> {
    return this.response.appPost( param, url + '/getDictionary');
  }
  /* 从后台获取详细比对结果 */
  getResultData(param): Observable<any> {
    return this.response.appPost(param, url + '/getResultData');
  }
  /* 获取公司维度的对比结果*/
  getAllCompanyData(param): Observable<any> {
    return this.response.appPost(param, url + '/getAllCompanyData');
  }
  /* 导出Excel */
  exportExcel(downloadUrl, param): Observable<any> {
    return this.response.appPost(param, downloadUrl);
    // return this.response.appPost2(param, url + '/exportExcel');
  }
  // 获取详情均值
  getSumDetail(param): Observable<any> {
    return this.response.appPost(param, url + '/getSumDetail');
  }
  // 获取新增规格
  getNewlyIncreased(param): Observable<any> {
    return this.response.appPost(param, url + '/getNewlyIncreased');
  }
}
