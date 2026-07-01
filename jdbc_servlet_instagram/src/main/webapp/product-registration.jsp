<!DOCTYPE html>
<html>
<head>
<title>Product Registration</title>
</head>
<body bgcolor="YELLOW">

	<center>

		<form action="saveproduct" method="post">

			<table border="15" cellpadding="10" cellspacing="15" width="450"
				bgcolor="PURPLE">

				<tr bgcolor="BLUE">
					<th colspan="2"><font color="white" size="5">Product
							Registration</font></th>
				</tr>

				<tr bgcolor="BLACK">
					<td><font color="RED" size="3"><b>Product ID</b></font></td>
					<td><input type="number" name="id" required></td>
				</tr>

				<tr bgcolor="BLACK">
					<td><font color="RED" size="3"><b>Product Name</b></font></td>
					<td><input type="text" name="name" required></td>
				</tr>

				<tr bgcolor="BLACK">
					<td><font color="RED" size="3"><b>Product Color</b></font></td>
					<td><input type="text" name="color" required></td>
				</tr>

				<tr bgcolor="BLACK">
					<td><font color="RED" size="3"><b>Product Price</b></font></td>
					<td><input type="number" step="0.01" name="price" required></td>
				</tr>

				<tr bgcolor="BLACK">
					<td><font color="RED" size="3"><b>Manufacturing
								Date</b></font></td>
					<td><input type="date" name="mfd" required></td>
				</tr>

				<tr bgcolor="BLACK">
					<td><font color="RED" size="3"><b>Quantity</b></font></td>
					<td><input type="number" name="quantity" required></td>
				</tr>

				<tr bgcolor="BLACK">
					<td colspan="2" align="center"><input type="submit"
						value="Register Product"> &nbsp;&nbsp; <input type="reset"
						value="Clear"></td>
				</tr>

			</table>

		</form>

	</center>

</body>
</html>