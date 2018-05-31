package ride.happyy.user.model

class TripListBean : BaseBean() {

    var pagination: PaginationBean = PaginationBean()
    var trips: List<TripBean> = ArrayList()

}
