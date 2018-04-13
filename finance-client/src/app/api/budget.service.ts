import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';

const url = global.baseUrl + '/FinanceBudgetController';
@Injectable()
export class BudgetService {
  constructor(private response: GetResponseService) {}
  /* 从后台获取详细列表数据*/
  getDetailData(param, ): Observable<any> {
    return this.response.appPost(param, url + '/getDetailData');
  }
  /* 从后台获取*/
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
  exportExcel(param): Observable<any> {
    return this.response.appPost2(param, url + '/exportExcel');
  }
}
