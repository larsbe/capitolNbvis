package de.unimuenster.wi.wfm.util.rest;

import de.unimuenster.wi.wfm.sharedLib.rest.RestHelper;
import de.unimuenster.wi.wfm.util.Constants;

public class REST extends RestHelper {

	

	public static void SendMessageToCapitol(String msg) {
		RestHelper.SendMessage(Constants.CAMUNDA_REST_CAPITOL, msg);
	}

}
