import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoreService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': 'http://localhost:8080'
    })
  };

  constructor(private httpClient: HttpClient) { }

  public getGrid(query: string): Observable<any> {
    return this.httpClient.post("http://127.0.0.1:8080/get", query, {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*'
      })
    });
  }

}
