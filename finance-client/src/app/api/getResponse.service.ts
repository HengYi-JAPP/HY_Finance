/* http公用服务*/
import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { HttpHeaders} from '@angular/common/http';
import * as global from '../global';
import {Observable} from 'rxjs/Observable';
import {catchError, retry} from 'rxjs/operators';
import {ErrorObservable} from 'rxjs/observable/ErrorObservable';
// 设置请求头
const  httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'my-auth-token'
  })
};
export interface Config {
  heroesUrl: string;
  textfile: string;
}
@Injectable()
export class GetResponseService {
  constructor(private http: HttpClient) {}
  // 发送get请求
  appGet (url: string): Observable<any> {
    return this.http.get<any>(url, httpOptions).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }
  // 发送post请求
  appPost (param: any, url: string): Observable<any> {
    return this.http.post<any>(url, param, httpOptions).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }
    // 发送delete请求
    appDelete (url: string): Observable<any> {
      return this.http.delete<any>(url, httpOptions).pipe(
        retry(1),
        catchError(this.handleError)
      );
  }
  // 发送一个更新的请求
  // 发送delete请求
  appUpdate (param: any, url: string): Observable<any> {
    return this.http.put<any>(url, param, httpOptions).pipe(
      retry(1),
      catchError(this.handleError)
    );
  }
  // 错误处理方法
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an ErrorObservable with a user-facing error message
    return new ErrorObservable(
      'Something bad happened; please try again later.');
  }
}
