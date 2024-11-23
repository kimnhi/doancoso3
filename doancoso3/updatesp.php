<?php
include "connect.php";
$tensp = $_POST['tensp'];
$giasp = $_POST['giasp'];
$mota = $_POST['mota'];
$hinhanh = $_POST['hinhanh'];
$loai = $_POST['loai'];
$id = $_POST['id'];

	// insert data
	$query = 'UPDATE `sanphammoi` SET `tensp`="'.$tensp.'", `giasp`="'.$giasp.'", `hinhanh`="'.$hinhanh.'", `mota`="'.$mota.'", `loai`="'.$loai.'" WHERE `id`="'.$id.'"';
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
print_r(json_encode($arr));

?>