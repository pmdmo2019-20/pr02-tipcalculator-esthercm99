package es.iessaladillo.pedrojoya.tipcalculator.ui.main

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import es.iessaladillo.pedrojoya.tipcalculator.R

class MainActivity : AppCompatActivity() {

    // TODO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputBill()
    }

    private fun inputBill() {
        val clickText = ActivityCompat.requireViewById<EditText>(this, R.id.txtBill)
        clickText.setSelection(0)
    }
}
