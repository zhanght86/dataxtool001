package com;



import com.dataxmanagement.DataxManagement;
import com.job.JobManagement;

public class Test {
	public static void main(String[] args) {
		JobManagement jobManagement=new JobManagement();
		System.out.println(jobManagement.findAllJobNames());
	}
}