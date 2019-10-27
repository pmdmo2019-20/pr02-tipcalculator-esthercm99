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

class MainActivity : AppCompatActivity() {
    private var tipCalculator: TipCalculator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tipCalculator = TipCalculator(R.string.defaultMoney.toFloat(), R.string.defaultTipPer.toFloat(), R.string.defaultDinners)

        setupViews()
        btnResetTip()
        btnResetDiners()
    }
    private fun setupViews() {
        changeViewTip(txtBill, getString(R.string.defaultMoney))
        changeViewTip(txtPercentage, getString(R.string.defaultTipPer))
        changeViewTip(txtDiners, getString(R.string.defaultDinners))
    }

    // CHANGES
    private fun changeViewTip(editText: EditText, default: String) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                changeCommaByPoint(editText)
                if(editText.text.toString().isNotEmpty()) {
                    checkIfPointStart()
                    checkValuesText()
                } else {
                   editText.setText(default)
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

    // CHECKS
    private fun checkIfPointStart() {
        val bill = txtBill.text.toString()
        val percentage = txtPercentage.text.toString()
        val diner = txtDiners.text.toString()

        if (bill.startsWith(getString(R.string.point))) {
            txtBill.setText(R.string.defaultMoney)
        }
        if (percentage.startsWith(getString(R.string.point))) {
            txtPercentage.setText(R.string.defaultTipPer)
        }
        if (diner.startsWith(getString(R.string.point))) {
            txtDiners.setText(R.string.defaultDinners)
        }
    }
    private fun checkValuesText() {
        val bill = txtBill.text.toString()
        val percentage = txtPercentage.text.toString()
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
                txtPercentage.setText(getString(R.string.defaultTipPer))
            } else if (percentage.toFloat() > 100.00f) {
                txtPercentage.setText(getString(R.string.defaultMaxTipPer))
            }

            tipCalculator = TipCalculator(txtBill.text.toString().toFloat(), txtPercentage.text.toString().toFloat(), txtDiners.text.toString().toInt())
        }
    }

    // CHANGE VALUES
    private fun changeValuesResult() {
        txtTip.setText(String.format(getString(R.string.numberFormat), tipCalculator?.calculateTip()))
        txtTotal.setText(String.format(getString(R.string.numberFormat), tipCalculator?.calculateTotal()))
        txtPerDiner.setText(String.format(getString(R.string.numberFormat),tipCalculator?.calculatePerDiner()))
        txtPerDinerRounded.setText(String.format(getString(R.string.numberFormat), tipCalculator?.calculatePerDinerRounded()))

        changeCommaByPoint(txtTip)
        changeCommaByPoint(txtTotal)
        changeCommaByPoint(txtPerDiner)
        changeCommaByPoint(txtPerDinerRounded)
    }

    // BOTONES DE RESET
    private fun btnResetTip() {
        val btnReset = ActivityCompat.requireViewById<Button>(this, R.id.btnResetTip)
        btnReset.setOnClickListener {resetBillTip()}
    }
    private fun btnResetDiners() {
        val btnReset = ActivityCompat.requireViewById<Button>(this, R.id.btnResetDiners)
        btnReset.setOnClickListener {resetDiner()}
    }

    private fun resetDiner() {
        val defDinner = getString(R.string.defaultDinners)
        val defMoney = getString(R.string.defaultMoney)

        txtDiners.requestFocus()

        txtDiners.setText(defDinner)
        txtPerDiner.setText(defMoney)
        txtPerDinerRounded.setText(defMoney)
    }
    private fun resetBillTip() {
        val defMoney = getString(R.string.defaultMoney)
        val defTip = getString(R.string.defaultTipPer)

        txtBill.requestFocus()

        txtBill.setText(defMoney)
        txtPercentage.setText(defTip)
        txtTip.setText(defMoney)
        txtTotal.setText(defMoney)
    }
}