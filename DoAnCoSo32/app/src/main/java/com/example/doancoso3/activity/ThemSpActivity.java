package com.example.doancoso3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.doancoso3.R;
import com.example.doancoso3.model.SanPhamMoi;
import com.example.doancoso3.retrofit.ApiBanHang;
import com.example.doancoso3.retrofit.RetrofitClient;
import com.example.doancoso3.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemSpActivity extends AppCompatActivity {
Spinner spinner;
Toolbar toolbar;
EditText tensp, giasp, hinhanh, mota;
AppCompatButton btnthemsp;
ApiBanHang apiBanHang;
CompositeDisposable compositeDisposable = new CompositeDisposable();
int loai=0;
SanPhamMoi sanPhamSua;
boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(R.layout.activity_them_sp);
        initView();
        initControl();
        initData();
        Intent intent = getIntent();
        sanPhamSua = (SanPhamMoi) intent.getSerializableExtra("sua");
        if (sanPhamSua == null) {
            // them moi
            flag = false;
        }else {
            // edit
            flag = true;
            btnthemsp.setText("Edit san pham");
            // show data khi click 1 sp
            giasp.setText(sanPhamSua.getGiasp());
            mota.setText(sanPhamSua.getMota());
            tensp.setText(sanPhamSua.getTensp());
            hinhanh.setText(sanPhamSua.getHinhanh());
            spinner.setSelection(sanPhamSua.getLoai());

        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnthemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false) {
                    themSp();
                }else {

                    suaSanPham();
                }
            }

            private void suaSanPham() {
                String ten_sp = tensp.getText().toString().trim();
                String gia_sp = giasp.getText().toString().trim();
                String hinh_anh = hinhanh.getText().toString().trim();
                String mo_ta = mota.getText().toString().trim();
                if (TextUtils.isEmpty(ten_sp) || TextUtils.isEmpty(gia_sp) || TextUtils.isEmpty(hinh_anh) || TextUtils.isEmpty(mo_ta) || loai ==0) {
                    Toast.makeText(getApplicationContext(), "Hay nhap đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    compositeDisposable.add(apiBanHang.updateSp(ten_sp, gia_sp, hinh_anh, mo_ta, loai, sanPhamSua.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if (messageModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));
                }

            }
        });

    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui long chon danh muc san pham");
        stringList.add("Loai 1");
        stringList.add("Loai 2");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void themSp() {
        String ten_sp = tensp.getText().toString().trim();
        String gia_sp = giasp.getText().toString().trim();
        String hinh_anh = hinhanh.getText().toString().trim();
        String mo_ta = mota.getText().toString().trim();
        if (TextUtils.isEmpty(ten_sp) || TextUtils.isEmpty(gia_sp) || TextUtils.isEmpty(hinh_anh) || TextUtils.isEmpty(mo_ta) || loai ==0) {
            Toast.makeText(getApplicationContext(), "Hay nhap đầy đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiBanHang.themSp(ten_sp, gia_sp, hinh_anh, mo_ta, (loai))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }
    private void initView() {
        spinner = findViewById(R.id.spinner_loai);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        tensp = findViewById(R.id.tensp);
        giasp = findViewById(R.id.giasp);
        hinhanh = findViewById(R.id.hinhanh);
        mota = findViewById(R.id.mota);
        btnthemsp = findViewById(R.id.btnthemsp);
        toolbar = findViewById(R.id.toobar);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}