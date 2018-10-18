package ride.happyy.user.model

class CarBean : BaseBean(), Comparable<CarBean> {

    var carID: String = ""
    var carsAvailable: String = ""
    var carName: String = ""
    var carImage: String = ""
    var minTime: String = ""
    var minFare: String = ""
    var maxSize: String = ""

    //vehicles Info
    var id: String = ""
    var vehicle_type: String = ""
    var driver_id: String = ""
    var phone: String = ""
    var is_verified: Boolean = false
    var is_active: Boolean = false
    var is_online: Boolean = false
    var trip_status: Boolean = false
    var on_trip: Boolean = false
    var service_type: String = ""
    var is_bike_service: Boolean = false
    var is_cng_service: Boolean = false
    var is_car_service: Boolean = false
    var is_ambulance_service: Boolean = false
    var is_car_premio_service: Boolean = false
    var is_car_noah_service: Boolean = false
    var is_car_hiace_service: Boolean = false

    var vehicle_lat: Double =23.00000
    var vehicle_lon: Double =90.00000







    override fun compareTo(other: CarBean): Int {
        val id = Integer.parseInt(carID)
        val tripID = Integer.parseInt(other.carID)
        return if (id == tripID) {
            0
        } else if (id > tripID) {
            1
        } else
            -1
    }
}
