import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

// const url = global.baseUrl + '/FinanceBudgetController';
@Injectable()
export class ExcelServiceService {
  constructor(private http: HttpClient) {}
  /* 导出Excel数据 */
  exportExcel(downloadUrl: string, params?: any): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
    const responseType = 'blob';
    // let httpParams: HttpParams = new HttpParams();
    // if (params) {
    //   for (const p in params) {
    //     if (params[p]) {
    //       httpParams = httpParams.set(p, params[p]);
    //     }
    //   }
    // }
    return this.http.post( downloadUrl, { 'headers': headers, 'responseType': responseType, 'params': params })
      .map(res => {
        return res;
      });
  }
}
