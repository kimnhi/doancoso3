<?php
include "connect.php";
$search = $_POST['search'];

 $query = "SELECT * FROM sanphammoi WHERE (LOWER (loai) LIKE '%$search%' OR LOWER (tensp) LIKE '%$search%' OR LOWER (giasp) LIKE '%$search%')";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}
if (!empty($result)) {
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		'result' => $result
	];
}
print_r(json_encode($arr));

?>