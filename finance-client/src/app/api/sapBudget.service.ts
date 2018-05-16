import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';
const url = global.baseUrl + '/SapFinanceBudgetController';
@Injectable()
export class SapBudgetService {
  constructor(private response: GetResponseService) {}
  // 获取产量的页面
  getCompareProduct(param): Observable<any> {
    return this.response.appPost(param, url + '/getCompareProduct');
  }
}
