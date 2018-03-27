import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';

const url = global.baseUrl + '/FinanceBudgetController';
@Injectable()
export class BudgetService {
  constructor(private response: GetResponseService) {}
  /* 从后台获取详细列表数据*/
  getDetailData(param): Observable<any> {
    return this.response.appPost(param, url + '/getDetailData');
  }
  /* 从后台获取*/
  getDictionary(): Observable<any> {
    return this.response.appGet( url + '/getDictionary');
  }

}
