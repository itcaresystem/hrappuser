package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.List;

import ride.happyy.user.model.UserBean;
import ride.happyy.user.net.invokers.EditProfileInvoker;

public class EditProfileTask extends AsyncTask<String, Integer, UserBean> {

    private final List<String> fileList;
    private EditProfileTaskListener editProfileTaskListener;

    private JSONObject postData;

    public EditProfileTask(JSONObject postData, List<String> fileList) {
        super();
        this.postData = postData;
        this.fileList = fileList;
    }

    @Override
    protected UserBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        EditProfileInvoker editProfileInvoker = new EditProfileInvoker(null, postData);
        return editProfileInvoker.invokeEditProfileWS();
    }

    @Override
    protected void onPostExecute(UserBean result) {
        super.onPostExecute(result);
        if (result != null)
            editProfileTaskListener.dataDownloadedSuccessfully(result);
        else
            editProfileTaskListener.dataDownloadFailed();
    }

    public static interface EditProfileTaskListener {

        void dataDownloadedSuccessfully(UserBean userBean);

        void dataDownloadFailed();
    }

    public EditProfileTaskListener getEditProfileTaskListener() {
        return editProfileTaskListener;
    }

    public void setEditProfileTaskListener(EditProfileTaskListener editProfileTaskListener) {
        this.editProfileTaskListener = editProfileTaskListener;
    }
}

