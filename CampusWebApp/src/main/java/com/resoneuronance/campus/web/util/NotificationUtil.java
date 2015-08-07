package com.resoneuronance.campus.web.util;

import java.io.IOException;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.resoneuronance.campus.web.domain.StudentRegID;

public class NotificationUtil implements Constants {
	
	private static void notify(String msg,String regId) throws IOException {
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY, msg).build();
		Result result = sender.send(message, regId, 1);
		System.out.println("*********After Notification ************ RegID used : + " + regId  + " and MessageId :" + result);
	}
	
	public static void notifyStudents(List<StudentRegID> regIds, String msg) {
		for(StudentRegID regID:regIds) {
			try {
				notify(msg, regID.getRegId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
