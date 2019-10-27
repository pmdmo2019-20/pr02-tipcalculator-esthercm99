package es.iessaladillo.pedrojoya.tipcalculator.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import es.iessaladillo.pedrojoya.tipcalculator.R
import es.iessaladillo.pedrojoya.tipcalculator.model.TipCalculator
import kotlinx.android.synthetic.main.activity_main.*
import es.iessaladillo.pedrojoya.tipcalculator.model.DecimalDigitsInputFilter
import android.text.InputFilter

class MainActivity : AppCompatActivity() {
    private var tipCalculator: TipCalculator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tipCalculator = TipCalculator(R.string.defaultMoney.toFloat(), R.string.defaultTipPer.toFloat(), R.string.defaultDinners.toInt())

        setupViews()
        resetBtnBillTip()
        resetBtnAll()
    }

    private fun setupViews() {
        val txtBill = findViewById<EditText>(R.id.txtBill)
        val txtTipPer = findViewById<EditText>(R.id.txtTipPer)
        val txtDiner = findViewById<EditText>(R.id.txtDiners)

        txtBill.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(30, 2))
        txtTipPer.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(3, 2))

        changeViewTip(txtBill)
        changeViewTip(txtTipPer)
        changeViewTip(txtDiner)
    }

    private fun changeViewTip(editText: EditText) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                changeCommaByPoint(editText)
                if(editText.text.toString().isNotEmpty()) {
                    checkIfPointStart()
                    checkValuesText()
                } else {
                    return
                }
            }
            override fun afterTextChanged(s: Editable) {
                if(editText.text.toString().isNotEmpty()) {
                    changeValuesResult()
                }
            }
        })
    }

    private fun changeCommaByPoint(editText: EditText) {
        if (editText.text.toString().contains(",")) {
            editText.setText(editText.text.toString().replace(",", "."))
        }
    }
    private fun checkValuesText() {
        val bill = txtBill.text.toString()
        val percentage = txtTipPer.text.toString()
        val diner = txtDiners.text.toString()

        if (bill.isNotEmpty() && percentage.isNotEmpty() && diner.isNotEmpty()) {
            // Se comprueba si los valores son negativos o se exceden:
            if (diner.toInt() < 1) {
                txtDiners.setText(getString(R.string.defaultDinners))
            }

            if (bill.toFloat() < 0.00f) {
                txtBill.setText(getString(R.string.defaultMoney))
            }

            if (percentage.toFloat() < 0.00f) {
                txtTipPer.setText(getString(R.string.defaultTipPer))
            } else if (percentage.toFloat() > 100.00f) {
                txtTipPer.setText(getString(R.string.defaultMaxTipPer))
            }

            tipCalculator = TipCalculator(txtBill.text.toString().toFloat(), txtTipPer.text.toString().toFloat(), txtDiners.text.toString().toInt())
        }
    }
    private fun changeValuesResult() {
        txtTip.setText(String.format("%.2f", tipCalculator?.calculateTip()))
        txtTotalBill.setText(String.format("%.2f", tipCalculator?.calculateTotal()))
        txtPerDinner.setText(String.format("%.2f",tipCalculator?.calculatePerDiner()))
        txtRounded.setText(String.format("%.2f", tipCalculator?.calculatePerDinerRounded()))

        changeCommaByPoint(txtTip)
        changeCommaByPoint(txtTotalBill)
        changeCommaByPoint(txtPerDinner)
        changeCommaByPoint(txtRounded)
    }

    private fun checkIfPointStart() {
        val bill = txtBill.text.toString()
        val percentage = txtTipPer.text.toString()
        val diner = txtDiners.text.toString()

        if (bill.startsWith(".")) {
            txtBill.setText(R.string.defaultMoney)
        }
        if (percentage.startsWith(".")) {
            txtTipPer.setText(R.string.defaultTipPer)
        }
        if (diner.startsWith(".")) {
            txtDiners.setText(R.string.defaultDinners)
        }
    }

    // BOTONES DE RESET
    private fun resetBtnBillTip() {
        val btnReset = ActivityCompat.requireViewById<Button>(this, R.id.btnResetBill)
        btnReset.setOnClickListener { r -> resetBillTip()}
    }
    private fun resetBtnAll() {
        val btnReset = ActivityCompat.requireViewById<Button>(this, R.id.btnResetAll)
        btnReset.setOnClickListener {r -> resetAll()}
    }

    private fun resetAll() {
        val defDinner = getString(R.string.defaultDinners)
        val defMoney = getString(R.string.defaultMoney)
        resetBillTip()
        txtDiners.setText(defDinner)
        txtPerDinner.setText(defMoney)
        txtRounded.setText(defMoney)
    }
    private fun resetBillTip() {
        val defMoney = getString(R.string.defaultMoney)
        val defTip = getString(R.string.defaultTipPer)
        txtBill.setText(defMoney)
        txtTipPer.setText(defTip)
        txtTip.setText(defMoney)
        txtTotalBill.setText(defMoney)
    }
}