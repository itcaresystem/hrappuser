package ride.happyy.user.model

import com.google.android.gms.maps.model.LatLng


class SuccessBean : BaseBean() {

    var tripID: String = ""
    var driverPhoto: String = ""
    var driverName: String = ""
    var fare: String = ""
    var date: Int = 0
    var time: Int = 0
    var source: String = ""
    var destination: String = ""
    var sourceLatitude: String = ""
    var sourceLongitude: String = ""
    var destinationLatitude: String = ""
    var destinationLongitude: String = ""

    var sourceLatLng: LatLng?= null
        get() {
            this.sourceLatLng = LatLng(dSourceLatitude, dSourceLongitude)
            return field
        }
    var destinationLatLng: LatLng?= null
        get() {
            this.destinationLatLng = LatLng(dDestinationLatitude, dDestinationLongitude)
            return field
        }


    val dSourceLatitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(sourceLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dSourceLongitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(sourceLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dDestinationLatitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(destinationLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dDestinationLongitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(destinationLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }
}
