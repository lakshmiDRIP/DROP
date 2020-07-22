
package org.drip.sample.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;

public class ErrorLogger {
	static class MessageRecord {
		private String _text = "";
		private Date _dateTime = null;
		private String _className = "";

		public MessageRecord (final Date dateTime, final String className, final String text) {
			_dateTime = dateTime;
			_className = className;
			_text = text;
		}

		public Date dateTime() {
			return _dateTime;
		}

		public String className() {
			return _className;
		}

		public String text() {
			return _text;
		}

		@SuppressWarnings ("unchecked") public JSONObject toJSON() {
			JSONObject jsonParameters = new JSONObject();

			jsonParameters.put ("timestamp", _dateTime.toString());

			jsonParameters.put ("className", _className);

			jsonParameters.put ("message", _text);

			return jsonParameters;
		}
	}

	static class ErrorMessageRecord extends MessageRecord {
		private List<MessageRecord> _messageRecordList = null;

		public ErrorMessageRecord (final MessageRecord messageRecord) {
			super (messageRecord.dateTime(), messageRecord.className(), messageRecord.text());
		}

		public boolean addMessageRecord (final MessageRecord messageRecord) {
			if (null == messageRecord) return false;

			if (null == _messageRecordList) _messageRecordList = new ArrayList<MessageRecord>();

			_messageRecordList.add (messageRecord);

			return true;
		}

		@SuppressWarnings ("unchecked") public JSONObject toJSON() {
			JSONObject jsonParameters = super.toJSON();

			if (null == _messageRecordList) return jsonParameters;

			JSONArray jsonMessageRecordArray = new JSONArray();

			for (MessageRecord messageRecord : _messageRecordList)
				jsonMessageRecordArray.add (messageRecord.toJSON());

			jsonParameters.put ("previousLogs", jsonMessageRecordArray);

			return jsonParameters;
		}
	}

	private static final MessageRecord GenerateRecord (final String line)
	{
		int timeSeparator = line.indexOf (' ', line.indexOf (' ') + 1);

		int classNameSeparator = line.indexOf (' ', timeSeparator + 1);

		try {
			return new MessageRecord (
				new SimpleDateFormat ("yyyy-mm-dd hh:mm:ss").parse (line.substring (0, timeSeparator)),
				line.substring (timeSeparator + 1, classNameSeparator),
				line.substring (classNameSeparator + 1)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final boolean IsErrorInText (final String text) {
		return text.contains ("error") || text.contains ("eror");
	}

	private static final boolean ProcessMessage (final String line, final Deque<MessageRecord> deque)
		throws Exception
	{
		MessageRecord messageRecord = GenerateRecord (line);

		if (null == messageRecord) return false;

		while (!deque.isEmpty()) {
			MessageRecord firstMessageRecord = deque.peekFirst();

			if (messageRecord.dateTime().getTime() - firstMessageRecord.dateTime().getTime() > 5000)
				deque.removeFirst();
			else
				break;
		}

		deque.addLast (messageRecord);

		return IsErrorInText (messageRecord.text().toLowerCase());
	}

	@SuppressWarnings ("unchecked") public static final void main (final String[] argumentArray)
		throws Exception
	{
		if (1 >= argumentArray.length) {
			System.out.println ("Usage => java ErrorLogger \"<<LogFileFullPath>>\" StartingLineNumber");

			System.exit (37);
		}

		int lineCount = 0;
		String line = null;
		String fileName = argumentArray[0];

		int startLine = Integer.parseInt (argumentArray[1]);

		Deque<MessageRecord> deque = new ArrayDeque<MessageRecord>();

		BufferedReader bufferedReader = new BufferedReader (new FileReader (fileName));

		List<ErrorMessageRecord> errorMessageRecordList = new ArrayList<ErrorMessageRecord>();

		while (null != (line = bufferedReader.readLine())) {
			if (lineCount++ < startLine) continue;

			if (ProcessMessage (line, deque)) {
				ErrorMessageRecord errorMessageRecord = new ErrorMessageRecord (deque.getLast());

				while (!deque.isEmpty()) errorMessageRecord.addMessageRecord (deque.pollFirst());

				errorMessageRecordList.add (errorMessageRecord);
			}
		}

		JSONObject jsonResult = new JSONObject();

		jsonResult.put ("errorCount", errorMessageRecordList.size());

		JSONArray jsonErrorMessageRecordArray = new JSONArray();

		for (ErrorMessageRecord errorMessageRecord : errorMessageRecordList)
			jsonErrorMessageRecordArray.add (errorMessageRecord.toJSON());

		jsonResult.put ("errors", jsonErrorMessageRecordArray);

		/* jsonResult.put ("dequeMessageCount", deque.size());

		JSONArray jsonDequeMessageRecordArray = new JSONArray();

		while (!deque.isEmpty()) jsonDequeMessageRecordArray.add (deque.pollFirst().toJSON());

		jsonResult.put ("dequeMessages", jsonDequeMessageRecordArray); */

		System.out.println (jsonResult);

		bufferedReader.close();
	}
}
