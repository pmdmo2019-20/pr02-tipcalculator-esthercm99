package es.iessaladillo.pedrojoya.tipcalculator.model

import kotlin.math.round

// TipCalculator class. Its constructor receives bill, percentage and diners
class TipCalculator(
    private var bill: Float = 0.0f,
    private var percentage: Float = 10.0f,
    private var diners: Int = 1
) {

    init {
        if(bill < 0.0f || percentage < 0.0 || diners < 0) {
            throw IllegalArgumentException()
        }
    }

    fun calculateTip(): Float {
        return bill * (percentage.div(100))
    }

    fun calculateTotal(): Float {
        return bill + calculateTip()
    }

    fun calculatePerDiner(): Float {
        return calculateTotal().div(diners)
    }

    fun calculatePerDinerRounded(): Float {
        return round(calculatePerDiner())
    }
}