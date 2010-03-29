package com.fieldexpert.fbapi4j;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fieldexpert.fbapi4j.common.Attachment;

public class DefaultCaseBuilder {

	private String project;
	private String area;

	public DefaultCaseBuilder(String project, String area) {
		this.project = project;
		this.area = area;
	}

	public Case build(Throwable t, boolean attachStackTrace) {
		StackTraceElement firstCause = getFirstCause(t).getStackTrace()[0];
		String className = firstCause.getClassName();
		String exceptionName = firstCause.getClass().getName();
		String fileName = firstCause.getFileName();
		int lineNumber = firstCause.getLineNumber();

		String title = exceptionName + " " + className + ":" + fileName + ":" + lineNumber;
		StringBuilder description = new StringBuilder();
		description.append(exceptionName + " \n");
		description.append(className + "\n");
		description.append(fileName + ":" + lineNumber + "\n");
		description.append("\n");
		
		StringWriter stackTraceWriter = new StringWriter();
		PrintWriter pw = new PrintWriter(stackTraceWriter);
		t.printStackTrace(pw);
		
		if (!attachStackTrace) {
			description.append(stackTraceWriter.toString());
		}
		
		Case fbCase = new Case(getProject(), getArea(), title, description.toString());
		
		if (attachStackTrace) {
			Attachment attachment = new Attachment(exceptionName + ".txt","text/plain",stackTraceWriter.toString());
			fbCase.attach(attachment);
		}
		
		return fbCase;
	}
	
	public Case build(Throwable t) {
		return build(t, true);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	private static Throwable getFirstCause(Throwable e) {
		Throwable t = e;
		while (t.getCause() != null) {
			t = t.getCause();
		}
		return t;
	}

}
