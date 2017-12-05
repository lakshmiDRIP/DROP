
package org.drip.zen.grid;

public class ServiceProxy {
	private int _iServerPort = -1;
	private java.net.Socket _socket = null;
	private java.lang.String _strServerIPAddress = "";

	public ServiceProxy (
		final java.lang.String strServerIPAddress,
		final int iServerPort)
		throws java.lang.Exception
	{
		_socket = new java.net.Socket (_strServerIPAddress = strServerIPAddress, _iServerPort = iServerPort);

		System.out.println ("[Client] => The Client connected to " + _strServerIPAddress + "/" + _iServerPort);
	}

	public int serverPort()
	{
		return _iServerPort;
	}

	public java.lang.String serverIPAddress()
	{
		return _strServerIPAddress;
	}

	public boolean request (
		final java.lang.String strMessage)
	{
		try {
			java.io.OutputStream outputStream = _socket.getOutputStream();

			java.io.PrintWriter pw = new java.io.PrintWriter (outputStream, true);

	    	pw.write (strMessage + "\n");

	    	pw.flush();

	    	java.io.InputStream inputStream = _socket.getInputStream();

	    	java.io.InputStreamReader inputReader = new java.io.InputStreamReader (inputStream);

	    	java.io.BufferedReader bufferedReader = new java.io.BufferedReader (inputReader);

			java.lang.String response = bufferedReader.readLine();

	    	System.out.println ("[Client] => " + response);

			return false;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public static final void main (
		final java.lang.String[] astrInput)
		throws java.lang.Exception
	{
		String server = "127.0.0.1";
		int listenerPort = 9090;
		String sampleMessage = "Hi How are you doing?";

		ServiceProxy sc = new ServiceProxy (server, listenerPort);

		sc.request (sampleMessage);
	}
}
