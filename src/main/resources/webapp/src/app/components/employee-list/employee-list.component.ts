import { Component, OnInit } from '@angular/core';
import { Employee } from '../../../model/employee';
import { FlywireService } from '../../../service/flywire.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.scss'
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[] = [];

  constructor(private flywireService: FlywireService) {}

  ngOnInit() {
    this.flywireService.getActiveEmployees().subscribe(data => {
      this.employees = data;
    })
  }
}
