<?php
include "connect.php";
$tensp = $_POST['tensp'];
$giasp = $_POST['giasp'];
$mota = $_POST['mota'];
$hinhanh = $_POST['hinhanh'];
$loai = $_POST['loai'];

// check data
$query = 'SELECT * FROM `sanphammoi` WHERE `tensp` = "'.$tensp.'"';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);
if ($numrow > 0) {
	$arr = [
		'success' => true,
		'message' => "Sap pham da ton tai"
	];
} else {
	// insert data
	$query = 'INSERT INTO `sanphammoi`(`tensp`, `giasp`, `hinhanh`, `mota`, `loai`) VALUES ("'.$tensp.'", "'.$giasp.'", "'.$hinhanh.'", "'.$mota.'", "'.$loai.'")';
	$data = mysqli_query($conn, $query);
	if ($data == true) {
		$arr = [
			'success' => true,
			'message' => "thanh cong"
		];
	}else{
		$arr = [
			'success' => false,
			'message' => " khong thanh cong"
		];
	}
}
print_r(json_encode($arr));

?>