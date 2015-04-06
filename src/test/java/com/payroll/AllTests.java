package com.payroll;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EmployeeTest.class, PayrollTest.class, UnionTest.class, ModifiersTest.class})
public class AllTests {

}
