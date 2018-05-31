package ride.happyy.user.model


import java.util.ArrayList

class FeedbackBean : BaseBean() {

    var successBean: String = ""
    var rating: Float = 0.toFloat()
    var badFeedBackList = ArrayList<String>()
    var goodFeedBackList = ArrayList<String>()
    var badFeedBackType: String = ""
    var goodFeedBackTYpe: String = ""
    var feedBack: String = ""
}
