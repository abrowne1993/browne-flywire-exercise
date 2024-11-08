import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Employee } from '../model/employee';
import { GetEmployeeResponseDto } from '../model/get-employee-response-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlywireService {

  private backendUrl: string;

  constructor(private http: HttpClient) {
    this.backendUrl = 'http://localhost:8080/'
  }

  public getActiveEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.backendUrl + 'getActiveEmployees');
  }

  public getEmployee(id: number): Observable<GetEmployeeResponseDto> {
    let params = new HttpParams().set('id', id);
    return this.http.get<GetEmployeeResponseDto>(this.backendUrl + 'getEmployee', {params: params});
  }

  public getEmployeesByHireDateRange(startDate: string, endDate:string): Observable<Employee[]> {
    let params = new HttpParams().set('startDate', startDate);
    params = params.append('endDate', endDate);
    return this.http.get<Employee[]>(this.backendUrl + 'getEmployeesByHireDateRange', {params: params});
  }

  public addEmployee(employee: Employee): Observable<any> {
    const headers = { 'content-type': 'application/json'}  
    const body = JSON.stringify(employee);
    return this.http.post(this.backendUrl + 'addEmployee', body, {'headers': headers});
  }

  public deactivateEmployee(id: number): Observable<any> {
    let params = new HttpParams().set('id', id);
    return this.http.put(this.backendUrl + 'deactivateEmployee', {params: params});
  }
}
