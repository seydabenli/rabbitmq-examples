package com.example.dlq.config;

public class Employee {

  private String empName;

  private String empId;

  private int salary;

  public String getEmpName() {

    return empName;

  }

  public void setEmpName(String empName) {

    this.empName = empName;

  }

  public String getEmpId() {

    return empId;

  }

  public void setEmpId(String empId) {

    this.empId = empId;

  }

  public int getSalary() {

    return salary;

  }

  public void setSalary(int salary) {

    this.salary = salary;

  }

  @Override

  public String toString() {

    return "Employee [empName=" + empName + ", empId=" + empId + ", salary=" + salary + "]";

  }

}
