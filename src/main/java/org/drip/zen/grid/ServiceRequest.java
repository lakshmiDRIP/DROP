
package org.drip.zen.grid;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class ServiceRequest {
	private UUID _id = null;
	private Date _timeStamp = null;
	private String _type = "";
	private String _body = "";

	public static ServiceRequest CreateFromString (
		String request)
	{
		String[] requestFields = request.split ("@");

		return new ServiceRequest (
			UUID.fromString (requestFields[0]),
			Date.from (Instant.ofEpochMilli (Long.parseLong (requestFields[1]))),
			requestFields[2],
			requestFields[3]
		);
	}

	public static ServiceRequest Create (
		String type,
		String body)
	{
		return new ServiceRequest (
			UUID.randomUUID(),
			new Date(),
			type,
			body
		);
	}

	public ServiceRequest (
		UUID id,
		Date timeStamp,
		String type,
		String body)
	{
		_id = id;
		_type = type;
		_body = body;
		_timeStamp = timeStamp;
	}

	public UUID id()
	{
		return _id;
	}

	public Date timeStamp()
	{
		return _timeStamp;
	}

	public String type()
	{
		return _type;
	}

	public String body()
	{
		return _body;
	}

	public String display()
	{
		return _id.toString() + "|" + _timeStamp.toString() + "|" + _type + "|" + _body;
	}

	public String toString()
	{
		return _id.toString() + "@" + _timeStamp.toInstant().toEpochMilli() + "@" + _type + "@" + _body;
	}

	public static void main (
		String[] input)
	{
		String serviceType = "SSRN";
		String serviceInput = "JQP";

		ServiceRequest sr = ServiceRequest.Create (
			serviceType,
			serviceInput
		);

		System.out.println (sr.display());

		String serviceRequestString = sr.toString();

		System.out.println (serviceRequestString);

		ServiceRequest srUnpack = ServiceRequest.CreateFromString (serviceRequestString);

		System.out.println (srUnpack.display());
	}
}
