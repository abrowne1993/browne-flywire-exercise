import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FlywireService } from '../../../service/flywire.service';
import { Employee } from '../../../model/employee';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-form.component.html',
  styleUrl: './employee-form.component.scss'
})
export class EmployeeFormComponent {
  
  employee: Employee;

  constructor(
    private route: ActivatedRoute, 
      private router: Router, 
        private flywireService: FlywireService) {
    this.employee = new Employee();
    this.employee.active = true;
    this.employee.directReports = [];
  }

  onSubmit() {
    this.flywireService.addEmployee(this.employee).subscribe(result => this.goToEmployeeList());
  }

  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }
}
