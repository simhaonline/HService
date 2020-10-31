import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {UserShortDto} from "../../dto/view-models/user-short-dto";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = '/api/users';

  constructor(private http:HttpClient) { }

  getProjectLeads():Observable<UserShortDto[]>{
    return this.http.get<UserShortDto[]>(this.url + '/project-leads')
  }

  getUserShortDtosByProjectId(projectId:number):Observable<UserShortDto[]>{
    return this.http.get<UserShortDto[]>(this.url + '/executors/' + projectId);
  }
}
