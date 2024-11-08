import { Employee } from "./employee";

export class GetEmployeeResponseDto {
    employee: Employee = new Employee;
    directHires: string[] = [];
}
