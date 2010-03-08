package com.fieldexpert.fbapi4j;

public final class Fbapi4j {

	private Fbapi4j() {
		// cannot construct
	}

	public static final String EMAIL = "email";

	public static final String PASSWORD = "password";

	public static final String API_XML = "api.xml";

	public static final String TOKEN = "token";

	public static final String CMD = "cmd";

	public static final String LOGON = "logon";

	public static final String LOGOFF = "logoff";

	public static final String NEW = "new";

	public static final String EDIT = "edit";

	public static final String ASSIGN = "assign";

	public static final String SEARCH = "search";

	public static final String QUERY = "q";

	public static final String REACTIVATE = "reactivate";

	public static final String REOPEN = "reopen";

	public static final String IX_BUG = "ixBug"; // (omitted for cmd=new)

	public static final String IX_BUG_PARENT = "ixBugParent"; // - Make this case a subcase of another case

	public static final String IX_BUG_EVENT = "ixBugEvent"; // (omitted for cmd=new - optional - if supplied, and this is not equal to the latest bug event for the case, you will receive error code 9 back to show that you were working with a "stale" view of the case).

	public static final String S_TAGS = "sTags"; //- A comma-separated list of tags to include in the case

	public static final String S_TITLE = "sTitle";

	public static final String S_STATUS = "sStatus";

	public static final String IX_PROJECT = "ixProject";

	public static final String S_PROJECT = "sProject";

	public static final String IX_AREA = "ixArea";

	public static final String S_AREA = "sArea";

	public static final String IX_FIXFOR = "ixFixFor";

	public static final String IX_CATEGORY = "ixCategory";

	public static final String S_CATEGORY = "sCategory";

	public static final String IX_PERSON_ASSIGNED_TO = "ixPersonAssignedTo";

	public static final String S_PERSON_ASSIGNED_TO = "sPersonAssignedTo";

	public static final String IX_PRIORITY = "ixPriority";

	public static final String S_PRIORITY = "sPriority";

	public static final String DT_DUE = "dtDue";

	public static final String HRS_CURR_EST = "hrsCurrEst";

	public static final String HRS_ELAPSED_EXTRA = "hrsElapsedExtra"; // This sets additional non-timesheet time on a case. (i.e. if there was an hour long time interval for the case and you set hrsElapsedExtra to 2, then the total hrsElapsed would be 3)

	public static final String N_FILE_COUNT = "nFileCount"; // The number of files being uploaded

	public static final String FILE = "File";

	public static final String S_VERSION = "sVersion";

	public static final String S_COMPUTER = "sComputer";

	public static final String S_CUSTOMER_EMAIL = "sCustomerEmail"; // - only the API lets you set this

	public static final String IX_MAILBOX = "ixMailbox"; // - if you set sCustomerEmail, you'll want to set this too... otherwise you won't be able to reply to this case

	public static final String S_SCOUT_DESCRIPTION = "sScoutDescription"; // (used only for cmd=new) if you set this, and FogBugz finds a case with this sScoutDescription, it will append to that case unless fScoutStopReporting is true for that case, and then it will do nothing.

	public static final String S_SCOUT_MESSAGE = "sScoutMessage"; // - the message you are supposed to display to users for this case

	public static final String F_SCOUT_STOP_REPORTING = "fScoutStopReporting"; // - set this to 1 if you don't want FogBugz to record any more of these types of cases

	public static final String S_EVENT = "sEvent"; // - text description of the bugevent

	public static final String COLS = "cols"; //- the columns you want returned about this case
}
