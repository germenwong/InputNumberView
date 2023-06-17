package com.hgm.inputnumber.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.hgm.inputnumber.R

/**
 * @author：  HGM
 * @date：  2023-06-16 21:31
 */
//1、继承自一个LinearLayout/RelativeLayout
class InputNumberView : LinearLayout {

      private lateinit var btnMinus: View
      private lateinit var btnPlus: View
      private lateinit var etValue: EditText
      private var currentValue = 0
      private var maxValue: Int = 0
      private var minValue: Int = 0
      private var step: Int = 0
      private var defaultValue: Int = 1
      private var disable: Boolean = false
      private var valueSize: Float = 0f
      private var btnBackground: Int = 0


      constructor(context: Context) : this(
            context,
            null
      )

      constructor(context: Context, attrs: AttributeSet?) : this(
            context,
            attrs,
            0
      )

      //统一入口后，用this来调用，确保它进入第三个方法
      constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
      ) {
            //2、定义和获取相关属性
            initAttrs(context, attrs)
            //3、把其他的子View载入
            initView(context)
            //4、处理事件和数据
            setUpEvent()
      }

      private fun initAttrs(context: Context, attrs: AttributeSet?) {
            //从TypedArray中读取属性值
            val a = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView)
            maxValue = a.getInt(R.styleable.InputNumberView_maxValue, 50)
            minValue = a.getInt(R.styleable.InputNumberView_minValue, 0)
            step = a.getInt(R.styleable.InputNumberView_step, 1)
            defaultValue = a.getInt(R.styleable.InputNumberView_defaultValue, 1)
            disable = a.getBoolean(R.styleable.InputNumberView_disable, false)
            valueSize = a.getDimension(R.styleable.InputNumberView_valueSize, 0f)
            btnBackground = a.getResourceId(R.styleable.InputNumberView_btnBackground, -1)
            //回收一下
            a.recycle()
      }


      private fun initView(context: Context) {
            //载入布局
            val view = LayoutInflater.from(context).inflate(R.layout.view_input_number, this, false)
            addView(view)
            //获取控件
            btnMinus = this.findViewById(R.id.btn_minus)
            btnPlus = this.findViewById(R.id.btn_plus)
            etValue = this.findViewById(R.id.et_value)
            //初始化控件值
            initValue()
      }

      private fun setUpEvent() {
            btnMinus.setOnClickListener {
                  btnPlus.isEnabled = true
                  currentValue -= step
                  if (currentValue <= minValue) {
                        currentValue = minValue
                        btnMinus.isEnabled = false
                        if (onNumberChangeListener != null) {
                              onNumberChangeListener?.onNumberMin(currentValue)
                        }
                  }
                  updateValue()
            }

            btnPlus.setOnClickListener {
                  btnMinus.isEnabled = true
                  currentValue += step
                  if (currentValue >= maxValue) {
                        currentValue = maxValue
                        btnPlus.isEnabled = false
                        if (onNumberChangeListener != null) {
                              onNumberChangeListener?.onNumberMax(currentValue)
                        }
                  }
                  updateValue()
            }
      }


      private fun updateValue() {
            etValue.setText(currentValue.toString())
            if (onNumberChangeListener != null) {
                  onNumberChangeListener?.onNumberChange(currentValue)
            }
      }

      private fun initValue() {
            btnMinus.isEnabled = !disable
            btnPlus.isEnabled = !disable
            etValue.setText(defaultValue.toString())
      }


      //暴露接口给外部使用
      interface OnNumberChangeListener {
            fun onNumberChange(value: Int)
            fun onNumberMax(value: Int)
            fun onNumberMin(value: Int)
      }

      private var onNumberChangeListener: OnNumberChangeListener? = null

      fun setOnNumberChangeListener(listener: OnNumberChangeListener) {
            this.onNumberChangeListener = listener
      }
}