<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Test</h1>

	<form action="/dummyURL" method="post">
		<input type="submit" value="Hello" />
	</form>



	<h1>Add User</h1>

	<form action="/createUser" method="post" enctype="multipart/form-data">
		Email ID <input type="text" name="emailID"><br /> GCM ID <input
			type="text" name="gcmID"> <br /> Mobile Number <input
			type="text" name="mobileNumber"><br /> Display Name <input
			type="text" name="displayName"><br /> Profile Pic <input
			type="file" name="profilePic"><br /> <input type="submit">
	</form>

	<h1>Update User</h1>
	<form action="/updateUser" method="post" enctype="multipart/form-data">
		User ID <input type="text" name="userID"><br /> Email ID <input
			type="text" name="emailID"><br /> GCM ID <input type="text"
			name="gcmID"> <br /> Mobile Number <input type="text"
			name="mobileNumber"><br /> Display Name <input type="text"
			name="displayName"><br /> Profile Pic <input type="file"
			name="profilePic"><br /> <input type="submit">
	</form>

	<h1>Send Message Packet to User</h1>

	<form action="/sendMessage" method="post">
		From ID <input type="text" name="fromUserID"><br /> To ID <input
			type="text" name="toUserID"> <br /> Message <input
			type="text" name="message"><br /> <input type="submit">
	</form>



	<h1>Send File Packet to User</h1>

	<form action="/createFilePacket" method="post"
		enctype="multipart/form-data">
		From ID <input type="text" name="fromUserID"><br /> To ID <input
			type="text" name="toUserID"> <br /> Package Name <input
			type="text" name="pakageName"> <br /> App Version Name <input
			type="text" name="appVersionName"><br /> App Version Code <input
			type="text" name="versionCode"><br /> Tags <input
			type="text" name="tags"><br /> Time Stamp <input type="text"
			name="timeStamp"><br /> APK File <input type="file"
			name="file"><br /> <input type="submit">
	</form>

	<br>
	<h1>Update GCM ID</h1>
	<form action="/updateGCMID" method="post">
		User ID <input type="text" name="userID"><br /> GCM ID <input
			type="text" name="gcmID"> <input type="submit">
	</form>

	<br>

	<h3>Fetch File</h3>
	http://localhost:8889/fetchFile?path=CrossApp/User4/ProfilePic/6886505.jpg

</body>
</html>