package ride.happyy.user.net;

public interface ResponseCallback<T> {
    void onSuccess(T data);
    void onError(Throwable th);
}
