package es.iessaladillo.pedrojoya.tipcalculator.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import es.iessaladillo.pedrojoya.tipcalculator.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetBtnBillTip()
        resetBtnAll()
    }
    
    // .setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,1)}) Para filtrar decimales.

    // BOTONES DE RESET
    private fun resetBtnBillTip() {
        val btnReset = ActivityCompat.requireViewById<Button>(this, R.id.btnResetBill)
        btnReset.setOnClickListener { r ->  resetBillTip()}
    }
    private fun resetBtnAll() {
        val btnReset = ActivityCompat.requireViewById<Button>(this, R.id.btnResetAll)
        btnReset.setOnClickListener { r ->  resetAll()}
    }

    private fun resetAll() {
        resetBill()
        resetTipPer()
        resetDinner()
    }
    private fun resetBillTip() {
        resetBill()
        resetTipPer()
    }

    private fun resetBill() {
        val billText = ActivityCompat.requireViewById<EditText>(this, R.id.txtBill)
        billText.setText("0.00")
    }
    private fun resetTipPer() {
        val tipPerText = ActivityCompat.requireViewById<EditText>(this, R.id.txtTipPer)
        tipPerText.setText("10.00")
    }
    private fun resetDinner() {
        val dinnerText = ActivityCompat.requireViewById<EditText>(this, R.id.txtDinners)
        dinnerText.setText("1")
    }
}
