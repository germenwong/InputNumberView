package com.hgm.inputnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hgm.inputnumber.databinding.ActivityMainBinding
import com.hgm.inputnumber.widget.InputNumberView

class MainActivity : AppCompatActivity(), InputNumberView.OnNumberChangeListener {

      private val binding: ActivityMainBinding by lazy {
            ActivityMainBinding.inflate(layoutInflater)
      }
      private val TAG = "MainActivity"

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)

            binding.inputNumber.setOnNumberChangeListener(this)
      }

      override fun onNumberChange(value: Int) {
            Log.d(TAG, "当前值为：$value ")
      }

      override fun onNumberMax(value: Int) {
            Toast.makeText(this, "已经达到最大值", Toast.LENGTH_SHORT).show()
      }

      override fun onNumberMin(value: Int) {
            Toast.makeText(this, "已经达到最小值", Toast.LENGTH_SHORT).show()
      }


}